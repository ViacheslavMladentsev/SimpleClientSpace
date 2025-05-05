package com.mladentsev.simpleclientspace.controllers;


import com.mladentsev.simpleclientspace.dto.request.RequestRegisterDto;

import com.mladentsev.simpleclientspace.exceptions.UserExistsException;
import com.mladentsev.simpleclientspace.services.IRegistrationService;
import com.mladentsev.simpleclientspace.services.RegistrationServiceImpl;

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

    private final IRegistrationService iAccountService;

    @Autowired
    public RegisterController(RegistrationServiceImpl accountService) {
        this.iAccountService = accountService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestRegisterDto requestRegisterDto) {
        try {
            iAccountService.signUp(requestRegisterDto);
            return ResponseEntity.ok().body("Пользователь успешно создан");
        } catch (UserExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
