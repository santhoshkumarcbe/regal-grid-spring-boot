package com.regalgrid.demo.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.model.Slot;
import com.regalgrid.demo.repository.ChargingStationRepository;
import com.regalgrid.demo.repository.SlotRepository;

@Service
public class SlotServiceImpl implements SlotService {

    @Autowired
    SlotRepository slotRepository;

    @Autowired 
    ChargingStationRepository chargingStationRepository;

    @Autowired
    Esp32ServiceImpl esp32Service;

    @Override
    public String bookSlot(Slot slot) {
        
        List<Slot> availableSlots = getAllAvailableSlots(slot.getDate(), slot.getChargingStationId());
        for (Slot availableSlot : availableSlots) {
            LocalDateTime availableStartTime = availableSlot.getStartTime();
            LocalDateTime availableEndTime = availableStartTime.plus(availableSlot.getDuration());

            LocalDateTime bookingStartTime = slot.getStartTime();
            LocalDateTime bookingEndTime = bookingStartTime.plus(slot.getDuration());

           if (bookingStartTime.isEqual(availableStartTime) || bookingStartTime.isAfter(availableStartTime)
            && ( bookingEndTime.isBefore(availableEndTime) || bookingEndTime.isEqual(availableEndTime) ))  {
                slotRepository.save(slot);
                return "created";
           } 
        }

        return "Check slot availability";

    }

    public List<Slot> getAllBookedSlots(LocalDateTime date, String chargingStationId) {
        boolean isAvailable = chargingStationRepository.existsById(chargingStationId);
        if (!isAvailable) {
            throw new Error("Charging Station id not available");
        }
        LocalDateTime startOfDay = date;
        LocalDateTime endOfDay = LocalDateTime.of(date.toLocalDate(), LocalTime.MAX);
        // return slotRepository.findByDateBetweenOrderByStartTimeAsc(startOfDay, endOfDay );
        return slotRepository.findByChargingStationIdAndStartTimeBetweenOrderByStartTimeAsc(chargingStationId,startOfDay, endOfDay);
    }

    public List<Slot> getAllAvailableSlots(LocalDateTime date, String chargingStationId) {

        System.out.println("localDate : " + date );
        
        List<Slot> bookedSlots = getAllBookedSlots(date, chargingStationId);

        bookedSlots.forEach(bookedSlot -> System.out.println("bookedSlot : " + bookedSlot));

        List<Slot> availableSlots = new ArrayList<>();

        LocalDateTime startDateTime = date;
        LocalDateTime endDateTime = LocalDateTime.of(date.toLocalDate(), LocalTime.MAX);

        LocalDateTime currentSlotStart = startDateTime;

        for (Slot bookedSlot : bookedSlots) {
            LocalDateTime bookedStartTime = bookedSlot.getStartTime();
            LocalDateTime bookedEndTime = bookedStartTime.plus(bookedSlot.getDuration());

            // If there's a gap between the current time slot and the next booked time slot, add it as available
            if (currentSlotStart.isBefore(bookedStartTime)) {
                LocalDateTime slotEnd = bookedStartTime;
                Duration duration = Duration.between(currentSlotStart,slotEnd);
                if (currentSlotStart.plus(duration).isBefore(slotEnd) || currentSlotStart.plus(duration).isEqual(slotEnd)) {
                    availableSlots.add(new Slot(currentSlotStart, duration));
                    currentSlotStart = currentSlotStart.plus(duration);
                }
            }
            if (currentSlotStart.isBefore(bookedEndTime)) {
                currentSlotStart = bookedEndTime; 
            }
        }

        // If there's a gap between the last booked time slot and the end of the day, add it as available
        if (currentSlotStart.isBefore(endDateTime)) {
            LocalDateTime slotEnd = endDateTime;
            Duration duration = Duration.between(currentSlotStart, slotEnd);
            if (currentSlotStart.plus(duration).isBefore(slotEnd) || currentSlotStart.plus(duration).isEqual(slotEnd)) {
                availableSlots.add(new Slot(currentSlotStart, duration));
                currentSlotStart = currentSlotStart.plus(duration);
            }
        }

        return availableSlots;
    }

    @Override
    public List<Slot> getAllAvailableSlotsByDate(LocalDateTime date, String chargingStationId) {
        List<Slot> bookedSlots = getAllBookedSlots(date, chargingStationId);
        List<Slot> allSlots = getAllAvailableSlots(date, chargingStationId);
        allSlots.addAll(bookedSlots);
        Collections.sort(allSlots, Comparator.comparing(Slot::getStartTime));
        return allSlots;
    }

    @Override
    public List<Slot> getAllBookedSlotsByChargingStationId(String chargingStationId) {
        return slotRepository.findAllByChargingStationId(chargingStationId);
    }

    @Override
    public List<Slot> getAllByUsername(String username) {
        return slotRepository.findAllByBookedBy(username);
    }

    @Override
    public List<Slot> slotExpireByCurrentTime(LocalDateTime date) {
        List<Slot> slots = slotRepository.findAll();
        for(Slot slot:slots){
            LocalDateTime startTime = slot.getStartTime();
            LocalDateTime endTime = startTime.plus(slot.getDuration());

            if (date.isAfter(endTime) || date.equals(endTime)) {
                slot.setExpired(true);
                slot.setId(slot.getId());
                slotRepository.save(slot);
            }
        }
        return slotRepository.findAll();
    }

    @Override
    public Slot findSlotById(String id) {
        return slotRepository.findById(id).orElse(null);
    }

    @Override
    public Slot setSlotExpired(String slotId) {
        Slot slot = slotRepository.findById(slotId).orElse(null);
        if (slot == null) {
            System.out.println("slot not found");
            return slot;
        }

        if (slot.isExpired()) {
            System.out.println("slot already expired");
            return null;
        }
        
        slot.setExpired(true);
        slot.setId(slot.getId());
        slotRepository.save(slot);
        LocalDateTime currentTime = LocalDateTime.now();

        Duration extraDuration = Duration.between(slot.getStartTime(), currentTime);
        Duration duration = slot.getDuration();
        long milliseconds = duration.toSeconds() * 1000;

        if (slot.getStartTime().isBefore(currentTime)) {
            long extraMilliSeconds = extraDuration.toSeconds() * 1000;
            milliseconds = milliseconds - extraMilliSeconds;
            if (milliseconds <= 0) {
                milliseconds = 0;
                return slot;
            }
        }


        esp32Service.turnOnEsp32(slot.getChargingStationId(), milliseconds);

        System.out.println("Relay turned on");

        return slot;
    }

}