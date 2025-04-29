package com.mladentsev.simpleclientspace.controllers;


import com.mladentsev.simpleclientspace.dto.request.RequestLoginDto;

import com.mladentsev.simpleclientspace.dto.request.RequestRefreshDto;
import com.mladentsev.simpleclientspace.services.IAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;


@RequestMapping("api/v1")
@RestController
public class AuthenticationController {

    private IAuthenticationService iAuthenticationService;

    @Autowired
    public AuthenticationController(IAuthenticationService authenticationService) {
        this.iAuthenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody RequestLoginDto requestLoginDto) {
        try {
            return ResponseEntity.ok(iAuthenticationService.authenticate(requestLoginDto));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(iAuthenticationService.refreshToken(request, response));

        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

}
