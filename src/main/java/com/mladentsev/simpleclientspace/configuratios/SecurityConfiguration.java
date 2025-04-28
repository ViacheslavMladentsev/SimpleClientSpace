package com.mladentsev.simpleclientspace.configuratios;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String ENDPOINT_SIGN_UP = "/api/v1/signup";

    private static final String ENDPOINT_SIGN_IN = "/api/v1/signin";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(ENDPOINT_SIGN_UP).permitAll().
                        requestMatchers(ENDPOINT_SIGN_IN).permitAll()
                        .anyRequest()
                        .authenticated())
//                .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
//                .authenticationProvider(authenticationProvider())
//                .addFilterBefore(
//                        authenticationJwtTokenFilter(),
//                        UsernamePasswordAuthenticationFilter.class)
        ;



//        http.csrf(AbstractHttpConfigurer::disable);
////        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        http.authorizeHttpRequests(auth -> auth.requestMatchers("/api/v1/signup").permitAll()
//                .requestMatchers("/api/v1/signin").permitAll()
//                .anyRequest().authenticated()
//        );
////        http.addFilterBefore(new JwtRequestFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
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
