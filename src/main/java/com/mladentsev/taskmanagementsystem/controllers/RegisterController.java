package com.mladentsev.taskmanagementsystem.controllers;


import com.mladentsev.taskmanagementsystem.dto.RequestRegisterDto;

import com.mladentsev.taskmanagementsystem.exception.InvalidRequestParametrsException;
import com.mladentsev.taskmanagementsystem.exception.UserExistsException;
import com.mladentsev.taskmanagementsystem.services.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class RegisterController {

    private final RegistrationService accountService;

    @Autowired
    public RegisterController(RegistrationService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestRegisterDto requestRegisterDto) {
        try {
            accountService.signUp(requestRegisterDto);
            return ResponseEntity.ok().body("Пользователь успешно создан");
        } catch (InvalidRequestParametrsException | UserExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
