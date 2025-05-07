package com.mladentsev.simpleclientspace.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

/**
 * Интерфейс JWT-сервисов для работы с токенами аутентификации.
 */
public interface IJwtService {

    /**
     * Генерирует Access Token для указанного пользователя.
     *
     * @param login логин пользователя
     * @return строка с валидным Access Token'ом
     */
    String generateAccessToken(String login);

    /**
     * Генерирует Refresh Token для указанного пользователя.
     *
     * @param login логин пользователя
     * @return строка с валидным Refresh Token'ом
     */
    String generateRefreshToken(String login);

    /**
     * Проверяет действительность токена.
     *
     * @param token       сам токен для проверки
     * @param userDetails объект с деталями текущего пользователя
     * @return true, если токен действителен, иначе false
     */
    boolean isValidToken(String token, UserDetails userDetails);

    /**
     * Извлекает имя пользователя из JWT-токена.
     *
     * @param token токен для обработки
     * @return имя пользователя (login)
     */
    String extractUsername(String token);

    /**
     * Извлекает произвольное утверждение (claim) из токена.
     *
     * @param <T>      Тип возвращаемого утверждения
     * @param token    токен для анализа
     * @param resolver функциональный интерфейс для преобразования claims в нужный тип
     * @return значение соответствующего claim
     */
    <T> T extractClaim(String token, Function<Claims, T> resolver);

}
