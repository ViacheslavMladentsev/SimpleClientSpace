package com.mladentsev.simpleclientspace.filters;

import com.mladentsev.simpleclientspace.services.AccountUserDetailServiceImpl;
import com.mladentsev.simpleclientspace.services.IJwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Фильтр JWT-аутентификации, обрабатывающий запросы, защищённые токеном.
 * <p>Проверяет заголовок Authorization и извлекает из него JWT-токен.
 * Затем производит проверку действительности токена и добавляет соответствующую аутентификационную запись в Spring Security Context.</p>
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    /**
     * Интерфейс службы JWT для работы с токенами.
     */
    private final IJwtService iJwtService;

    /**
     * Сервис загрузки данных о пользователе.
     */
    private final AccountUserDetailServiceImpl accountUserDetailService;

    /**
     * Конструктор фильтра с зависимостью на службу JWT и службу пользователя.
     *
     * @param jwtService          интерфейс для работы с JWT-токенами
     * @param accountUserDetailService реализация сервиса загрузки данных пользователя
     */
    public JwtFilter(IJwtService jwtService, AccountUserDetailServiceImpl accountUserDetailService) {
        this.iJwtService = jwtService;
        this.accountUserDetailService = accountUserDetailService;
    }

    /**
     * Основной метод фильтрации запроса.
     *
     * <ol>
     *   <li>Получает заголовок Authorization из запроса.</li>
     *   <li>Проверяет правильность формирования заголовка (начинается с Bearer).</li>
     *   <li>Извлекает токен и пытается извлечь имя пользователя из токена.</li>
     *   <li>Производит загрузку данных пользователя по извлечённому имени.</li>
     *   <li>Проверяет действительность токена и устанавливает аутентификацию, если всё успешно.</li>
     * </ol>
     *
     * @param request объект запроса
     * @param response объект ответа
     * @param filterChain цепочка фильтров
     * @throws ServletException если произошла ошибка в обработке сервлета
     * @throws IOException если возникла проблема ввода-вывода
     */
    @Override
    protected void doFilterInternal (
                                    @NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
                                    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = iJwtService.extractUsername(token);

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = accountUserDetailService.loadUserByUsername(username);
            if(iJwtService.isValidToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}
