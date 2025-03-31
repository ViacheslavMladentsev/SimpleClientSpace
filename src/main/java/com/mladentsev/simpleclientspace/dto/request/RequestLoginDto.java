package com.mladentsev.simpleclientspace.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RequestLoginDto {

    @NotBlank(message = "Параметр 'login' является обязательным для запроса.")
    @Size(min = 3, max = 20, message = "Значение параметра 'login' должно содержать не менее 6 и не более 20 символов.")
    private String login;

    @NotBlank(message = "Параметр 'password' является обязательным для запроса.")
    @Size(min = 8, message = "Значение параметра 'password' должно содержать не менее 8 символов.")
    private String password;


    public @NotBlank(message = "Параметр 'login' является обязательным для запроса.")
            @Size(min = 3, max = 20, message = "Значение параметра 'login' должно содержать не менее 6 и не более 20 символов.")
    String getLogin() {
        return login;
    }

    public void setLogin(@NotBlank(message = "Параметр 'login' является обязательным для запроса.")
                         @Size(min = 3, max = 20, message = "Значение параметра 'login' должно содержать не менее 6 и не более 20 символов.")
                         String login) {
        this.login = login;
    }

    public @NotBlank(message = "Параметр 'password' является обязательным для запроса.")
            @Size(min = 8, message = "Значение параметра 'password' должно содержать не менее 8 символов.")
    String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Параметр 'password' является обязательным для запроса.")
                            @Size(min = 8, message = "Значение параметра 'password' должно содержать не менее 8 символов.")
                            String password) {
        this.password = password;
    }

}
