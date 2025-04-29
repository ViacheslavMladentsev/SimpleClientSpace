package com.mladentsev.simpleclientspace.handler;

import com.mladentsev.simpleclientspace.repositories.IAccountRepository;
import com.mladentsev.simpleclientspace.services.IJwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final IAccountRepository iAccountRepository;

    private final IJwtService iJwtService;

    @Autowired
    public CustomLogoutHandler(IAccountRepository iAccountRepository, IJwtService iJwtService) {
        this.iAccountRepository = iAccountRepository;
        this.iJwtService = iJwtService;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        iAccountRepository.updateIsLogout(iAccountRepository.findByLogin(iJwtService.extractUsername(token)).get().getId(), true);

    }
}
