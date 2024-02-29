package com.regalgrid.demo.security.dto;

import com.regalgrid.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class AuthenticationResponse {
    String token;
    User user;
}
