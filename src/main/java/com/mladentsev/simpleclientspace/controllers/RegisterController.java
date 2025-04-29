package com.mladentsev.simpleclientspace.controllers;


import com.mladentsev.simpleclientspace.dto.request.RequestRegisterDto;

import com.mladentsev.simpleclientspace.exception.InvalidRequestParametrsException;
import com.mladentsev.simpleclientspace.exception.UserExistsException;
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

    private final RegistrationServiceImpl accountService;

    @Autowired
    public RegisterController(RegistrationServiceImpl accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody RequestRegisterDto requestRegisterDto) {
        try {
            accountService.signUp(requestRegisterDto);
            return ResponseEntity.ok().body("Пользователь успешно создан");
        } catch (InvalidRequestParametrsException | UserExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
