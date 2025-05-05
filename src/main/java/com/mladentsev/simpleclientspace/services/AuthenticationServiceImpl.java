package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.dto.request.RequestLoginDto;
import com.mladentsev.simpleclientspace.dto.response.ResponseLoginDto;
import com.mladentsev.simpleclientspace.repositories.IAccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final IAccountRepository iAccountRepository;

    private final IJwtService iJwtServiceImpl;

    private final AuthenticationManager authenticationManager;

    private final AccountUserDetailServiceImpl accountUserDetailService;

    @Autowired
    public AuthenticationServiceImpl(IAccountRepository iAccountRepository,
                                     IJwtService iJwtServiceImpl,
                                     AuthenticationManager authenticationManager,
                                     AccountUserDetailServiceImpl accountUserDetailService) {
        this.iAccountRepository = iAccountRepository;
        this.iJwtServiceImpl = iJwtServiceImpl;
        this.authenticationManager = authenticationManager;
        this.accountUserDetailService = accountUserDetailService;
    }

    @Override
    public ResponseLoginDto authenticate(RequestLoginDto requestLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLoginDto.getLogin(),
                        requestLoginDto.getPassword()));
        iAccountRepository.updateIsLogout(iAccountRepository.findByLogin(requestLoginDto.getLogin()).get().getId(), false);

        String accessToken = iJwtServiceImpl.generateAccessToken(requestLoginDto.getLogin());
        String refreshToken = iJwtServiceImpl.generateRefreshToken(requestLoginDto.getLogin());

        return new ResponseLoginDto(accessToken, refreshToken);
    }

    @Override
    public ResponseLoginDto refreshToken(HttpServletRequest request, HttpServletResponse response) throws AccessDeniedException {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AccessDeniedException("Неавторизованный пользователь");
        }

        String token = authorizationHeader.substring(7);
        String login = iJwtServiceImpl.extractUsername(token);

        UserDetails userDetails = accountUserDetailService.loadUserByUsername(login);

        if (iJwtServiceImpl.isValidToken(token, userDetails)) {

            String accessToken = iJwtServiceImpl.generateAccessToken(userDetails.getUsername());
            String refreshToken = iJwtServiceImpl.generateRefreshToken(userDetails.getUsername());

            return new ResponseLoginDto(accessToken, refreshToken);

        }

        return null; //todo ТУТ КОСТЫЛЬ!!!!
    }
}
