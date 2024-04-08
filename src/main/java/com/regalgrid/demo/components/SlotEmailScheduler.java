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

            String htmlBody = "<body style=\"font-family: Arial, sans-serif; color: #333; background-color: #f8f8f8; padding: 20px; border-radius: 10px;\">"
        + "<div style=\"margin-bottom: 20px;\">"
        + "<h2 style=\"font-size: 24px; margin-bottom: 10px;\">Charging Port Activation</h2>"
        + "<p style=\"font-size: 16px;\">Click the button below to activate charging port:</p>"
        + "<button style=\"display: inline-block; background-color: #007bff; color: #fff; text-decoration: none; padding: 10px 20px; border: none; border-radius: 5px; cursor: pointer;\">"
        + "<a href=\"http://localhost:4200/activate-port?id=" + slot.getId() + "\" style=\"text-decoration: none; color: inherit;\">Activate Charging Port</a>"
        + "</button>"
        + "</div>"
        + "<p style=\"font-size: 16px;\">Thank you for choosing us!</p>"
        + "<p style=\"font-style: italic; font-size: 14px; margin-bottom: 0;\">Regards,<br/>Regal-grid</p>"
        + "<img src=\"cid:logo\" alt=\"Regal-grid logo\" style=\"max-width: 50%; height: 50%; display: block; margin: 20px auto;\"> "
        + "</body>";

            Email email = Email.builder()
            .toEmail(userEmail)
            .subject(subject)
            .body(htmlBody)
            .build();
            emailService.sendEmail(email);
            System.out.println("charger activation mail send to " + userEmail);
        }
    }
    
}
