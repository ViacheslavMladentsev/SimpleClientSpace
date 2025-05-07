package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.dto.request.RequestRegisterDto;

/**
 * Интерфейс регистрации пользователей.
 * Определяет методы, необходимые для реализации процесса регистрации нового пользователя.
 */
public interface IRegistrationService {

    /**
     * Регистрации нового пользователя.
     *
     * @param requestRegisterDto объект DTO, содержащий данные для регистрации,
     *                          такие как имя пользователя, пароль, email и другие поля формы регистрации.
     */
    void signUp(RequestRegisterDto requestRegisterDto);

}
