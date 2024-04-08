package com.regalgrid.demo.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.regalgrid.demo.repository.UserRepository;
import com.regalgrid.demo.security.dto.AuthenticationRequest;
import com.regalgrid.demo.security.dto.AuthenticationResponse;
import com.regalgrid.demo.security.dto.RegisterRequest;
import com.regalgrid.demo.security.service.AuthenticationService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    @Autowired
    UserRepository userRepository;

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
                try {
                    return authenticationService.register(request);   
                } catch (Exception e) {
                    e.printStackTrace();
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return authenticationService.authenticate(request);
    }

    @PutMapping("updatepassword/{userName}/{newpassword}")
    public ResponseEntity<String> updatePassword(@PathVariable String newpassword, @PathVariable String userName) {
        try {
            String response = authenticationService.updatePassword(newpassword, userName);
            if (response.equals("true")) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getEmailByUserName/{username}")
    public ResponseEntity<String> getemailByUsername(
            @PathVariable String username) {
        try {
            String response = authenticationService.getEmailByUserName(username);
            if (response.equals("error")) {
                return new ResponseEntity<>("user name not found", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("unknown error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("isusernameExist/{username}")
    public ResponseEntity<Boolean> isUsernameExist(@PathVariable String username) {
        try {
            boolean response = userRepository.existsByUserName(username);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("isEmailExist/{email}")
    public ResponseEntity<Boolean> isEmailExist(@PathVariable String email) {
        try {
            boolean response = userRepository.existsByEmailId(email);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("isMobilenumberExist/{mobileNumber}")
    public ResponseEntity<Boolean> isMobileNumberExist(@PathVariable String mobileNumber) {
        try {
            boolean response = userRepository.existsByMobileNumber(mobileNumber);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
