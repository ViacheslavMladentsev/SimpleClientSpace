package com.mladentsev.simpleclientspace.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Пользовательская реализация обработчика отказа в доступе.
 *
 * <p>Реализует интерфейс {@link AccessDeniedHandler}. Используется для обработки случаев,
 * когда пользователь попытался обратиться к ресурсу, на который у него отсутствуют необходимые права доступа.</p>
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Основная логика обработки попытки доступа к запрещённому ресурсу.
     *
     * <p>Метод создает JSON-сообщение об отказе в доступе и отправляет его клиенту с кодом HTTP-статуса 403 (Forbidden).</p>
     *
     * @param request              Объект запроса HTTP.
     * @param response             Объект ответа HTTP.
     * @param accessDeniedException Исключение, вызванное отказом в доступе.
     * @throws IOException           В случае ошибки при формировании ответа.
     * @throws ServletException      В случае ошибки при работе с сервлетом.
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        System.err.println(accessDeniedException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_FORBIDDEN);
        body.put("error", "Forbidden");
        body.put("message", accessDeniedException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }

}
