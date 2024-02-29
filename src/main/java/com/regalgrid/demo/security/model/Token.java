package com.regalgrid.demo.security.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.regalgrid.demo.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Token {
    private String id;

    private String token;

    private TokenType tokenType;

    private boolean expired;

    private boolean revoked;

    private User user;
}
