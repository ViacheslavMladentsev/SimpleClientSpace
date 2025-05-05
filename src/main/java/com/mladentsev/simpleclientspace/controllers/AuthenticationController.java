package com.mladentsev.simpleclientspace.controllers;


import com.mladentsev.simpleclientspace.dto.request.RequestLoginDto;

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

/**
 * Контроллер, ответственный за аутентификацию.
 * Предоставляет два главных метода: аутентификацию ({@link #signIn}) и обновление токена ({@link #refreshToken}).
 */
@RequestMapping("api/v1")
@RestController
public class AuthenticationController {

    /**
     * Сервис аутентификации, используемый для выполнения процедур аутентификации и обновления токенов.
     */
    private final IAuthenticationService iAuthenticationService;

    /**
     * Конструктор контроллера, инициализирующий сервис аутентификации.
     *
     * @param iAuthenticationService сервис аутентификации, необходимый для выполнения аутентификации и обновления токенов.
     */
    @Autowired
    public AuthenticationController(IAuthenticationService iAuthenticationService) {
        this.iAuthenticationService = iAuthenticationService;
    }

    /**
     * Аутентификация и авторизация пользователя по данным формы входа.
     * Если введённые данные верны, возвращаются access token и refresh token аутентификации.
     *
     * @param requestLoginDto данные формы входа, содержащие имя пользователя и пароль.
     * @return ResponseLoginDto.class содержащий access token и refresh token аутентификации в случае успеха,/b
     * ошибку авторизации в противном случае.
     */
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

    /**
     * Обновление пары access token и refresh token, с помощью refresh token, если access token не валиден.
     * Если введённые данные верны, возвращаются access token и refresh token аутентификации.
     *
     * @param request запрос HTTP с информацией о старом токене.
     * @param response ответ HTTP, куда помещается новый токен.
     * @return ResponseLoginDto.class содержащий обновленную пару access token и refresh token аутентификации в случае успеха,/b
     * ошибку в противном случае.
     */
    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            return ResponseEntity.ok(iAuthenticationService.refreshToken(request, response));

        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

}
