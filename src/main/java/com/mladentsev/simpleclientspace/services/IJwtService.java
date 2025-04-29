package com.mladentsev.simpleclientspace.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface IJwtService {

    String generateAccessToken(String login);
    String generateRefreshToken(String login);
    boolean isValidToken(String token, UserDetails userDetails);
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolver);

}
