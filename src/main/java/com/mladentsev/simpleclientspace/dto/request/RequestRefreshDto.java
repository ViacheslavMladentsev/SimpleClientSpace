package com.mladentsev.simpleclientspace.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RequestRefreshDto {

    @NotBlank(message = "Параметр 'refreshToken' является обязательным для запроса.")
    private String refreshToken;

    public @NotBlank(message = "Параметр 'refreshToken' является обязательным для запроса.") String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(@NotBlank(message = "Параметр 'refreshToken' является обязательным для запроса.")
                                String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
