package com.regalgrid.demo.security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.regalgrid.demo.security.dto.AuthenticationRequest;
import com.regalgrid.demo.security.dto.AuthenticationResponse;
import com.regalgrid.demo.security.dto.RegisterRequest;
import com.regalgrid.demo.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody RegisterRequest request){
            return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
        @RequestBody AuthenticationRequest request){
            System.out.println("authenticate :" +request);
            return authenticationService.authenticate(request);
    }   

    @PutMapping("updatepassword/{userId}/{newpassword}")
    public ResponseEntity<String> updatePassword(@PathVariable String newpassword, @PathVariable String userId) {
        String response = authenticationService.updatePassword(newpassword, userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
