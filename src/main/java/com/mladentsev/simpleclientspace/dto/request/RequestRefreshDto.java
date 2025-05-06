package com.mladentsev.simpleclientspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO класс, необходимой для обновления пары JWT токенов.
 * <p>Используется для отправки запроса на сервер с Refresh Token для обновления пары токенов.</p>
 */
@NoArgsConstructor      // Создает конструктор без аргументов.
@AllArgsConstructor     // Создает конструктор со всеми полями класса.
public class RequestRefreshDto {

    /**
     * Refresh Token пользователя.
     *   <li>Обязательно для заполнения.</li>
     */
    @NotBlank(message = "Параметр 'refreshToken' является обязательным для запроса.")
    private String refreshToken;

    /**
     * Получает значение Refresh Token пользователя.
     * @return строковое представление токена пользователя
     */
    public @NotBlank(message = "Параметр 'refreshToken' является обязательным для запроса.") String getRefreshToken() {
        return refreshToken;
    }

}
