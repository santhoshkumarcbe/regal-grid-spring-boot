package com.regalgrid.demo.security.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String emailId;
    
    private String fullName;
    
    private String mobileNumber;
    
    private String passwordHash;
    
    private String userName;

    private String userRole;

    private ArrayList<String> authorities;

}
