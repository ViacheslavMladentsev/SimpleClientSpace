package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.dto.request.RequestRegisterDto;

public interface IRegistrationService {
    void signUp(RequestRegisterDto requestRegisterDto);
}
