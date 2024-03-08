package com.regalgrid.demo.service;

import java.time.LocalDateTime;
import java.util.List;

import com.regalgrid.demo.model.Slot;

public interface SlotService {

    String bookSlot(Slot slot);

    List<Slot> getAllBookedSlots(LocalDateTime date, String chargingStationId);

    List<Slot> getAllAvailableSlots(LocalDateTime date, String chargingStationId);

    List<Slot> getAllAvailableSlotsByDate(LocalDateTime date, String chargingStationId);

    List<Slot> getAllBookedSlotsByChargingStationId(String chargingStationId);

    List<Slot> getAllByUsername(String username);
    
}