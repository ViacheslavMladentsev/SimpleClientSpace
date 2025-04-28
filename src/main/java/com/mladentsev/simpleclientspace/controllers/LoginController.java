package com.mladentsev.simpleclientspace.controllers;


import com.mladentsev.simpleclientspace.dto.request.RequestLoginDto;

import com.mladentsev.simpleclientspace.dto.response.ResponseLoginDto;
import com.mladentsev.simpleclientspace.services.JwtService;
import com.mladentsev.simpleclientspace.services.JwtServiceImpl;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("api/v1")
@RestController
public class LoginController {

    private final AuthenticationManager authenticationManager;

    private JwtServiceImpl jwtService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager, JwtServiceImpl jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Valid @RequestBody RequestLoginDto requestLoginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestLoginDto.getLogin(),
                            requestLoginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + authentication);
            String jwt = jwtService.generateAccessToken(requestLoginDto.getLogin());
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + jwt);

            ResponseLoginDto responseLoginDto = new ResponseLoginDto(jwt);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>" + responseLoginDto);

            return ResponseEntity.ok().body(responseLoginDto);
//            return ResponseEntity.ok().body("Аутентификация прошла успешно.");
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

}
