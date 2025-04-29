package com.mladentsev.simpleclientspace.configuratios;


import com.mladentsev.simpleclientspace.filters.JwtFilter;
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


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String ENDPOINT_SIGN_UP = "/api/v1/signup";

    private static final String ENDPOINT_SIGN_IN = "/api/v1/signin";

    private static final String ENDPOINT_LOGOUT = "/api/v1/logout";

    private static final String ENDPOINT_TEST = "/api/v1/test";

    private final JwtFilter jwtFIlter;

    private final AccountUserDetailServiceImpl accountUserDetailService;

    private final CustomAccessDeniedHandler accessDeniedHandler;

    private final CustomLogoutHandler customLogoutHandler;

    @Autowired
    public SecurityConfiguration(JwtFilter jwtFIlter,
                                 AccountUserDetailServiceImpl accountUserDetailService,
                                 CustomAccessDeniedHandler accessDeniedHandler,
                                 CustomLogoutHandler customLogoutHandler) {
        this.jwtFIlter = jwtFIlter;
        this.accountUserDetailService = accountUserDetailService;
        this.accessDeniedHandler = accessDeniedHandler;
        this.customLogoutHandler = customLogoutHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(ENDPOINT_SIGN_UP, ENDPOINT_SIGN_IN).permitAll()
                        .requestMatchers(ENDPOINT_TEST).hasAuthority("ROLE_ADMIN")
                        .anyRequest()
                        .authenticated())
                .userDetailsService(accountUserDetailService)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtFIlter, UsernamePasswordAuthenticationFilter.class)
                .logout(log -> {
                    log.logoutUrl(ENDPOINT_LOGOUT);
                    log.addLogoutHandler(customLogoutHandler);
                    log.logoutSuccessHandler((request, response, authentication) ->
                            SecurityContextHolder.clearContext());
                })
        ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

}
