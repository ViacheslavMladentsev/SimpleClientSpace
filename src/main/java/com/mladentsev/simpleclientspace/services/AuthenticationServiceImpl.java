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

/**
 * Реализация сервиса аутентификации.
 * Предоставляет методы для аутентификации пользователя и обновления токенов.
 */
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    /**
     * Репозиторий для работы с аккаунтами пользователей.
     */
    private final IAccountRepository iAccountRepository;

    /**
     * Сервис для работы с JWT-токенами.
     */
    private final IJwtService iJwtService;

    /**
     * Менеджер аутентификации Spring Security.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Сервис для загрузки деталей пользователя.
     */
    private final AccountUserDetailServiceImpl accountUserDetailService;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param iAccountRepository          репозиторий аккаунтов
     * @param iJwtService                 сервис для работы с JWT-токенами
     * @param authenticationManager      менеджер аутентификации
     * @param accountUserDetailService   сервис для загрузки деталей пользователя
     */
    @Autowired
    public AuthenticationServiceImpl(IAccountRepository iAccountRepository,
                                     IJwtService iJwtService,
                                     AuthenticationManager authenticationManager,
                                     AccountUserDetailServiceImpl accountUserDetailService) {
        this.iAccountRepository = iAccountRepository;
        this.iJwtService = iJwtService;
        this.authenticationManager = authenticationManager;
        this.accountUserDetailService = accountUserDetailService;
    }

    /**
     * Авторизация пользователя с использованием указанных логина и пароля.
     *
     * @param requestLoginDto объект, содержащий логин и пароль пользователя
     * @return объект ResponseLoginDto с новыми токенами доступа и обновления
     */
    @Override
    public ResponseLoginDto authenticate(RequestLoginDto requestLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLoginDto.getLogin(),
                        requestLoginDto.getPassword()));

        iAccountRepository.updateIsLogout(iAccountRepository.findByLogin(requestLoginDto.getLogin()).get().getId(), false);

        String accessToken = iJwtService.generateAccessToken(requestLoginDto.getLogin());
        String refreshToken = iJwtService.generateRefreshToken(requestLoginDto.getLogin());

        return new ResponseLoginDto(accessToken, refreshToken);
    }

    /**
     * Обновление токенов (access и refresh) по действующему refresh-токену.
     *
     * @param request  HTTP-запрос с Authorization-заголовком
     * @param response HTTP-ответ для возвращения новых токенов
     * @return объект ResponseLoginDto с обновленными токенами
     * @throws AccessDeniedException если обновление токенов невозможно
     */
    @Override
    public ResponseLoginDto refreshToken(HttpServletRequest request, HttpServletResponse response) throws AccessDeniedException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new AccessDeniedException("Неавторизованный пользователь");
        }

        String token = authorizationHeader.substring(7);
        String login = iJwtService.extractUsername(token);

        UserDetails userDetails = accountUserDetailService.loadUserByUsername(login);

        if (iJwtService.isValidToken(token, userDetails)) {
            String accessToken = iJwtService.generateAccessToken(userDetails.getUsername());
            String refreshToken = iJwtService.generateRefreshToken(userDetails.getUsername());

            return new ResponseLoginDto(accessToken, refreshToken);
        }

        return null; // TODO: Решить проблему костылей!
    }
}