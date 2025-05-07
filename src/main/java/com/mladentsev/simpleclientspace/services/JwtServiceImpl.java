package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.repositories.IAccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * Сервис для работы с JWT-токенами.
 * Содержит методы для генерации, проверки и декодирования токенов.
 */
@Service
public class JwtServiceImpl implements IJwtService {

    /**
     * Шифровальная секретная фраза для подписи токенов.
     */
    @Value("${security.jwt.secret_key}")
    private String secretKey;

    /**
     * Время жизни Access Token'а (в миллисекундах).
     */
    @Value("${security.jwt.access_token_expiration}")
    private long accessTokenExpiration;

    /**
     * Время жизни Refresh Token'а (в миллисекундах).
     */
    @Value("${security.jwt.refresh_token_expiration}")
    private long refreshTokenExpiration;

    /**
     * Репозиторий для работы с аккаунтами пользователей.
     */
    private final IAccountRepository iAccountRepository;

    /**
     * Конструктор с внедрением репозитория аккаунтов.
     *
     * @param iAccountRepository репозиторий для работы с сущностью Account
     */
    @Autowired
    public JwtServiceImpl(IAccountRepository iAccountRepository) {
        this.iAccountRepository = iAccountRepository;
    }

    /**
     * Генерация Access Token'а для конкретного пользователя.
     *
     * @param login логин пользователя
     * @return сгенерированный Access Token
     */
    @Override
    public String generateAccessToken(String login) {
        return generateToken(login, accessTokenExpiration);
    }

    /**
     * Генерация Refresh Token'а для конкретного пользователя.
     *
     * @param login логин пользователя
     * @return сгенерированный Refresh Token
     */
    @Override
    public String generateRefreshToken(String login) {
        return generateToken(login, refreshTokenExpiration);
    }

    /**
     * Получение ключа шифрования на основе base64-кодированной строки.
     *
     * @return ключ для подписания токенов
     */
    private SecretKey getSigningKey() {

        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Общий метод для генерации токена.
     *
     * @param login       логин пользователя
     * @param expiryTime  время жизни токена в миллисекундах
     * @return подписанный JWT-токен
     */
    private String generateToken(String login, long expiryTime) {
        JwtBuilder builder = Jwts.builder()
                .subject(login)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiryTime))
                .signWith(getSigningKey());

        return builder.compact();
    }

    /**
     * Извлечение имени пользователя из токена.
     *
     * @param token токен для анализа
     * @return имя пользователя (субъект токена)
     */
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Универсальный метод для извлечения любого утверждения (claim) из токена.
     *
     * @param <T>      ожидаемый тип результата
     * @param token    токен для анализа
     * @param resolver функция для разрешения нужного claim'a
     * @return извлечённое утверждение
     */
    @Override
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /**
     * Полное извлечение всех претензий (claims) из токена.
     *
     * @param token токен для анализа
     * @return все претензии токена
     */
    private Claims extractAllClaims(String token) {

        JwtParserBuilder parser = Jwts.parser();
        parser.verifyWith(getSigningKey());

        return parser.build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Проверка действительности токена.
     *
     * @param token       токен для проверки
     * @param userDetails детальные данные пользователя
     * @return true, если токен действителен и совпадает с текущими данными пользователя
     */
    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {

        String login = extractUsername(token);

        boolean isValidToken = iAccountRepository.findByLogin(login)
                .map(t -> !t.isLogout()).orElse(false);

        return login.equals(userDetails.getUsername())
                && isAccessTokenExpired(token)
                && isValidToken;
    }

    /**
     * Проверка истечения срока действия токена.
     *
     * @param token токен для проверки
     * @return true, если токен ещё действует
     */
    private boolean isAccessTokenExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }

    /**
     * Извлечение даты окончания срока действия токена.
     *
     * @param token токен для анализа
     * @return дата окончания срока действия токена
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
