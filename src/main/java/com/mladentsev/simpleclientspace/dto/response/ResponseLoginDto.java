package com.mladentsev.simpleclientspace.dto.response;

/**
 * DTO класс представления результатов успешной аутентификации пользователя.
 * <p>Используется для возврата токенов авторизации клиенту после успешного входа.</p>
 */
public class ResponseLoginDto {

    /**
     * Токен доступа, используемый для последующих запросов API.
     */
    private String accessToken;

    /**
     * Обновляемый токен, применяемый для обновления истекшего токена доступа.
     */
    private String refreshToken;

    /**
     * Конструктор класса с передачей токенов авторизации.
     *
     * @param accessToken токен доступа
     * @param refreshToken обновляемый токен
     */
    public ResponseLoginDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
