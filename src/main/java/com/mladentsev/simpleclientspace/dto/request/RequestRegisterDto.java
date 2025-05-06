package com.mladentsev.simpleclientspace.dto.request;


import com.mladentsev.simpleclientspace.enums.ERoles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO класс представляющий набор информации о пользователя, необходимой для регистрации.
 * <p>Используется для отправки запроса на сервер с параметрами регистрации.</p>
 */
@NoArgsConstructor      // Создает конструктор без аргументов.
@AllArgsConstructor     // Создает конструктор для всех полей класса.
public class RequestRegisterDto {

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
     * Имя пользователя.
     * <ul>
     *   <li>Обязательно для заполнения.</li>
     *   <li>Минимальная длина — 3 символа.</li>
     *   <li>Максимальная длина — 20 символов.</li>
     * </ul>
     */
    @NotBlank(message = "Имя является обязательным для запроса.")
    @Size(min = 3, max = 20, message = "Имя должно содержать не менее 3 и не более 20 символов.")
    private String firstName;

    /**
     * Фамилия пользователя.
     * <ul>
     *   <li>Обязательно для заполнения.</li>
     *   <li>Минимальная длина — 3 символа.</li>
     *   <li>Максимальная длина — 20 символов.</li>
     * </ul>
     */
    @NotBlank(message = "Фамилия является обязательным для запроса.")
    @Size(min = 3, max = 20, message = "Фамилия должно содержать не менее 3 и не более 20 символов.")
    private String lastName;

    /**
     * E-mail пользователя.
     * <ul>
     *   <li>Обязательно для заполнения.</li>
     * </ul>
     */
    @NotBlank(message = "Электронная почта является обязательным для запроса.")
    @Email(message = "Необходимо ввести корректный адрес электронной почты.")
    private String email;

    /**
     * Логин пользователя.
     * <ul>
     *   <li>Обязательно для заполнения.</li>
     * </ul>
     */
    @NotNull(message = "Список ролей являются обязательным полем")
    private Set<ERoles> roles;

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

    /**
     * Получает значение имени пользователя.
     * @return строковое представление имени пользователя
     */
    public @NotBlank(message = "Имя является обязательным для запроса.")
            @Size(min = 3, max = 20, message = "Имя должно содержать не менее 3 и не более 20 символов.")
            String getFirstName() {
        return firstName;
    }

    /**
     * Получает значение фамилии пользователя.
     * @return строковое представление фамилии пользователя
     */
    public @NotBlank(message = "Фамилия является обязательным для запроса.")
            @Size(min = 3, max = 20, message = "Фамилия должно содержать не менее 3 и не более 20 символов.")
            String getLastName() {
        return lastName;
    }

    /**
     * Получает значение e-mail пользователя.
     * @return строковое представление e-mail пользователя
     */
    public @NotBlank(message = "Электронная почта является обязательным для запроса.")
            @Email(message = "Необходимо ввести корректный адрес электронной почты.")
            String getEmail() {
        return email;
    }

    /**
     * Возвращает набор ролей текущего пользователя.
     *
     * <p>Это обязательное поле, которое определяет роли пользователя,
     * такие как администратор, менеджер или обычный пользователь.</p>
     *
     * @return Набор ролей пользователя
     */
    public @NotNull(message = "Список ролей являются обязательным полем") Set<ERoles> getRoles() {
        return roles;
    }

}
