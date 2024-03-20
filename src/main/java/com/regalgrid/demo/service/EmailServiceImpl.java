package com.regalgrid.demo.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.dto.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String sendEmail(Email email) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true indicates multipart message

            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);

            ClassPathResource image = new ClassPathResource("static/images/logo.png");

            helper.addInline("logo", image, "image/png");

            mailSender.send(mimeMessage);
            System.out.println("Mail Sent successfully...");
            return "Mail Sent successfully...";
        } catch (MessagingException e) {
            System.out.println("Failed to send mail: " + e.getMessage());
            return "Failed to send mail: " + e.getMessage();

        }

    }

}
