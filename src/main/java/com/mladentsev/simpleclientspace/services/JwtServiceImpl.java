package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.models.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.secret_key}")
    private String secretKey;

    @Value("${security.jwt.access_token_expiration}")
    private long accessTokenExpiration;

    @Value("${security.jwt.refresh_token_expiration}")
    private long refreshTokenExpiration;


    @Override
    public String generateAccessToken(String login) {
        return generateToken(login, accessTokenExpiration);
    }

    @Override
    public String generateRefreshToken(String login) {
        return generateToken(login, refreshTokenExpiration);
    }

    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateToken(String login, long expiryTime) {
        JwtBuilder builder = Jwts.builder()
                .subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiryTime))
                .signWith(getSigningKey());

        return builder.compact();
    }


    @Override
    public boolean isValid(String token, UserDetails user) {
        return false;
    }

    @Override
    public boolean isValidRefresh(String token, Account account) {
        return false;
    }

    @Override
    public String extractUsername(String token) {
        return "";
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return null;
    }
}
