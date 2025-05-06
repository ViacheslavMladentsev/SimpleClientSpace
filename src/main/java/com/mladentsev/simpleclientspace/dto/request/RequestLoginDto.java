package com.mladentsev.simpleclientspace.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * DTO класс представляющий логин и пароль, необходимые для входа.
 * <p>Используется для отправки запроса на сервер с параметрами входа.</p>
 */
@NoArgsConstructor      // Создает конструктор без аргументов.
@AllArgsConstructor     // Создает конструктор для всех полей класса.
public class RequestLoginDto {

    /**
     * Логин пользователя.
     * <ul>
     *   <li>Обязательно для заполнения.</li>
     *   <li>Минимальная длина — 3 символа.</li>
     *   <li>Максимальная длина — 20 символов.</li>
     * </ul>
     */
    @NotBlank(message = "Параметр 'login' является обязательным для запроса.")
    @Size(min = 3, max = 20, message = "Значение параметра 'login' должно содержать не менее 6 и не более 20 символов.")
    private String login;

    /**
     * Пароль пользователя.
     * <ul>
     *   <li>Обязательно для заполнения.</li>
     *   <li>Минимальная длина — 3 символа.</li>
     * </ul>
     */
    @NotBlank(message = "Параметр 'password' является обязательным для запроса.")
    @Size(min = 8, message = "Значение параметра 'password' должно содержать не менее 8 символов.")
    private String password;

    /**
     * Получает значение логина пользователя.
     * @return строковое представление пароля пользователя
     */
    public @NotBlank(message = "Параметр 'login' является обязательным для запроса.")
            @Size(min = 3, max = 20, message = "Значение параметра 'login' должно содержать не менее 6 и не более 20 символов.")
    String getLogin() {
        return login;
    }

    /**
     * Получает значение пароля пользователя.
     * @return строковое представление пароля пользователя
     */
    public @NotBlank(message = "Параметр 'password' является обязательным для запроса.")
            @Size(min = 8, message = "Значение параметра 'password' должно содержать не менее 8 символов.")
    String getPassword() {
        return password;
    }

}
