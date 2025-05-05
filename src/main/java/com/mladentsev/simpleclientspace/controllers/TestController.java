package com.mladentsev.simpleclientspace.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1")
@RestController
public class TestController {


    @GetMapping("/test")
    public ResponseEntity<Object> signIn() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!1111");
        return ResponseEntity.ok().body("Аутентифицированный пользователь успешно получил защищенный ресурс");

    }
}


