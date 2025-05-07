package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.dto.request.RequestLoginDto;
import com.mladentsev.simpleclientspace.dto.response.ResponseLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.file.AccessDeniedException;

/**
 * Интерфейс сервисов аутентификации пользователей.
 * Определяет методы для входа и обновления токенов.
 */
public interface IAuthenticationService {

    /**
     * Аутентификация пользователя по предоставленным данным.
     *
     * @param request объект RequestLoginDto, содержащий логин и пароль пользователя
     * @return объект ResponseLoginDto с результатом аутентификации (токены и статус)
     */
    ResponseLoginDto authenticate(RequestLoginDto request);

    /**
     * Обновление токенов (access и refresh) по существующему refresh токену.
     *
     * @param request  HTTP-запрос, содержащий заголовок с refresh-токеном
     * @param response HTTP-ответ, куда будут записаны новые токены
     * @throws AccessDeniedException если обновление невозможно или возникли проблемы безопасности
     * @return обновленные данные аутентификации в виде объекта ResponseLoginDto
     */
    ResponseLoginDto refreshToken(HttpServletRequest request, HttpServletResponse response) throws AccessDeniedException;

}
