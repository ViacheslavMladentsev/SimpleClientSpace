package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.dto.request.RequestLoginDto;
import com.mladentsev.simpleclientspace.dto.response.ResponseLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.nio.file.AccessDeniedException;

public interface IAuthenticationService {

    ResponseLoginDto authenticate(RequestLoginDto request);
    ResponseLoginDto refreshToken(HttpServletRequest request, HttpServletResponse response) throws AccessDeniedException;

}
