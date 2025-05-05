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

/**
 * Контроллер, ответственный за регистрацию пользователя.
 * Предоставляет один метод для регистрации ({@link #signUp}).
 */
@RequestMapping("/api/v1")
@RestController
public class RegisterController {

    /**
     * Сервис регистрации, используемый для выполнения процедуры регистрации пользователя.
     */
    private final IRegistrationService iAccountService;

    /**
     * Конструктор контроллера, инициализирующий сервис аутентификации.
     *
     * @param iAccountService сервис регистрации, необходимый для выполнения регистрации пользователей.
     */
    @Autowired
    public RegisterController(RegistrationServiceImpl iAccountService) {
        this.iAccountService = iAccountService;
    }

    /**
     * Регистрация пользователя по данным формы входа.
     * Если введённые данные верны, возвращаются статус 200 и сообщение об успехе регистрации.
     *
     * @param requestRegisterDto данные формы входа, содержащие информацию о пользователе.
     * @return сообщение об успешной регистрации, ошибку авторизации в противном случае.
     */
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
