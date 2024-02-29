package com.regalgrid.demo.security.service;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import com.regalgrid.demo.model.User;

import io.jsonwebtoken.Claims;

public interface JwtService {
    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(User user);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, User userDetails);
}
