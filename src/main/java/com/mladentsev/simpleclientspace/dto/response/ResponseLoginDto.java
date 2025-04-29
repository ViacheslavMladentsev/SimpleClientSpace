package com.mladentsev.simpleclientspace.dto.response;


public class ResponseLoginDto {

    private String accessToken;
    private String refreshToken;

    public ResponseLoginDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

}
