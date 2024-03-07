package com.regalgrid.demo.security.service;

import org.springframework.http.ResponseEntity;

import com.regalgrid.demo.security.dto.AuthenticationRequest;
import com.regalgrid.demo.security.dto.AuthenticationResponse;
import com.regalgrid.demo.security.dto.RegisterRequest;

public interface AuthenticationService {
        
    ResponseEntity<AuthenticationResponse> register(RegisterRequest request);

    ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request);

    String updatePassword(String newpassword, String userId);

    String getEmailByUserName(String username);

}
