package com.mladentsev.simpleclientspace.configuratios;


import com.mladentsev.simpleclientspace.filters.JwtFilter;
import com.mladentsev.simpleclientspace.handler.CustomAuthenticationEntryPoint;
import com.mladentsev.simpleclientspace.handler.CustomAccessDeniedHandler;
import com.mladentsev.simpleclientspace.handler.CustomLogoutHandler;
import com.mladentsev.simpleclientspace.services.AccountUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * Класс конфигурации безопасности Spring Security.
 * <p>
 * Производит настройку фильтров, провайдера аутентификации, шифрования паролей и политики безопасности.
 * Предназначен для конфигурации точек входа (/signin, /signup, /logout, /test) и предоставления защиты соответствующих ресурсов.
 *
 * @author Младенцев Вячеслав
 * @version 1.0
 * @since 2025-05-05
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * URL регистрации новых пользователей.
     */
    private static final String ENDPOINT_SIGN_UP = "/api/v1/signup";

    /**
     * URL аутентификации пользователей.
     */
    private static final String ENDPOINT_SIGN_IN = "/api/v1/signin";

    /**
     * URL выхода пользователей.
     */
    private static final String ENDPOINT_LOGOUT = "/api/v1/logout";

    /**
     * URL для теста авторизованного пользователя для ролей ROLE_MANAGER и ROLE_ADMIN.
     */
    private static final String ENDPOINT_TEST_MANAGER = "/api/v1/test_manager";

    /**
     * URL для теста авторизованного пользователя для роли ROLE_ADMIN.
     */
    private static final String ENDPOINT_TEST_ADMIN = "/api/v1/test_admin";

    /**
     * Фильтр обработки JWT токена.
     */
    private final JwtFilter jwtFilter;

    /**
     * Сервис загрузки информации о пользователе.
     */
    private final AccountUserDetailServiceImpl accountUserDetailService;

    /**
     * Обработчик несанкционированного доступа (отсутствие необходимых ролей).
     */
    private final CustomAccessDeniedHandler accessDeniedHandler;

    /**
     * Обработчик события выхода пользователя.
     */
    private final CustomLogoutHandler customLogoutHandler;

    /**
     * Обработчик события аутентификации пользователя с недопустимыми учетными данными.
     */
    private final CustomAuthenticationEntryPoint unauthorizedHandler;

    /**
     * Конструктор для настройки конфигурации безопасности.
     *
     * @param jwtFilter               фильтр для проверки JWT-токенов
     * @param accountUserDetailService сервис для загрузки информации о пользователе
     * @param accessDeniedHandler     обработчик для случая, когда доступ запрещён
     * @param customLogoutHandler     обработчик для очистки контекста безопасности при выходе
     * @param unauthorizedHandler     обработчик для некорректных попыток авторизации
     */
    @Autowired
    public SecurityConfiguration(JwtFilter jwtFilter,
                                 AccountUserDetailServiceImpl accountUserDetailService,
                                 CustomAccessDeniedHandler accessDeniedHandler,
                                 CustomLogoutHandler customLogoutHandler,
                                 CustomAuthenticationEntryPoint unauthorizedHandler) {
        this.jwtFilter = jwtFilter;
        this.accountUserDetailService = accountUserDetailService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.customLogoutHandler = customLogoutHandler;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    /**
     * Формирует и настраивает цепочку фильтрации запросов.
     * Включает отключение CSRF-защиты, политику сессий без состояний, фильтрацию JWT-токенов и настройку обработки выхода из системы.
     *
     * @param http инстанс для построения правил безопасности
     * @return сконфигурированную цепочку фильтров
     * @throws Exception если возникла ошибка при построении конфигурации
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(ENDPOINT_SIGN_UP, ENDPOINT_SIGN_IN).permitAll()
                        .requestMatchers(ENDPOINT_TEST_MANAGER).hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(ENDPOINT_TEST_ADMIN).hasAuthority("ROLE_ADMIN")
                        .anyRequest()
                        .authenticated())
                .userDetailsService(accountUserDetailService)
                .exceptionHandling(e -> {
                    e.accessDeniedHandler(accessDeniedHandler);
                    e.authenticationEntryPoint(unauthorizedHandler);
                })
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(log -> {
                    log.logoutUrl(ENDPOINT_LOGOUT);
                    log.addLogoutHandler(customLogoutHandler);
                    log.logoutSuccessHandler((request, response, authentication) ->
                            SecurityContextHolder.clearContext());
                })
        ;
        return http.build();
    }

    /**
     * Настраивает сильный алгоритм шифрования паролей.
     *
     * @return Encoder, использующий алгоритм Bcrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настраивает менеджер аутентификации.
     *
     * @param authConfig поставщик конфигурации аутентификации
     * @return Менеджер аутентификации
     * @throws Exception если возникли проблемы при создании менеджера аутентификации
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
