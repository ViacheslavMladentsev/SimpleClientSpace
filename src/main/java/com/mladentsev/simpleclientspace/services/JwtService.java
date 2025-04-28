package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.models.Account;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {

    String generateAccessToken(String login);
    String generateRefreshToken(String login);
    boolean isValid(String token, UserDetails user);
    boolean isValidRefresh(String token, Account account);
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolver);

}
