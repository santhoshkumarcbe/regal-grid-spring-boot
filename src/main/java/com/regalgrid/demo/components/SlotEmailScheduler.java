package com.regalgrid.demo.components;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.regalgrid.demo.dto.Email;
import com.regalgrid.demo.model.Slot;
import com.regalgrid.demo.repository.ChargingStationRepository;
import com.regalgrid.demo.repository.SlotRepository;
import com.regalgrid.demo.security.service.AuthenticationService;
import com.regalgrid.demo.service.EmailService;

@Component
public class SlotEmailScheduler {
    @Autowired
    SlotRepository slotRepository;

    @Autowired
    EmailService emailService;

    @Autowired 
    ChargingStationRepository chargingStationRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Scheduled(fixedRate = 60000) // run every minute
    public void sendScheduledEmails() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime oneMinuteLater = currentDateTime.plusMinutes(1); // One minute later
        List<Slot> upcomingSlots = slotRepository.findByStartTimeBetween(currentDateTime, oneMinuteLater);
        System.out.println("upcomingSlots " + upcomingSlots);

        for (Slot slot : upcomingSlots) {
            String userEmail = authenticationService.getEmailByUserName(slot.getBookedBy());
            String subject = "Charging port activation ";
            String body = "chaging port activation, click the link to activate charging port : " + "http://localhost:4200/activate-port?id="  +slot.getId();
            Email email = Email.builder()
            .toEmail(userEmail)
            .subject(subject)
            .body(body)
            .build();
            emailService.sendEmail(email);
            System.out.println("charger activation mail send to " + userEmail);
        }
    }
    
}
