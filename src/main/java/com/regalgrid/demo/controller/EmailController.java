package com.regalgrid.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.regalgrid.demo.dto.Email;
import com.regalgrid.demo.service.EmailService;

@CrossOrigin
@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    private EmailService emailService;
 
    @PostMapping("/mail")
    public void sendMail(@RequestBody Email email) {
        emailService.sendEmail(email);
    }
}
