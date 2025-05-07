package com.mladentsev.simpleclientspace.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Пользовательская точка входа для обработки попыток несанкционированного доступа.
 *
 * <p>Реализует интерфейс {@link AuthenticationEntryPoint}, определяющий поведение при попытке обращения
 * к защищенному ресурсу без необходимой авторизации.</p>
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Реакция на попытку несанкционированного доступа.
     * <p>Метод формирует JSON-сообщение об ошибке и отправляет его обратно клиенту с кодом HTTP-статуса 401 (Unauthorized).</p>
     *
     * @param request        Объект запроса HTTP.
     * @param response       Объект ответа HTTP.
     * @param authException  Исключение, вызвавшее ошибку авторизации.
     * @throws IOException   В случае ошибки при записи ответа.
     * @throws ServletException В случае ошибки при работе с сервлетом.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.err.println(authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
