package com.mladentsev.simpleclientspace.dto.response;


public class ResponseLoginDto {

    private String access_token;

    public ResponseLoginDto(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

}
