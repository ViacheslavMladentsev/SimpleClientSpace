package com.mladentsev.simpleclientspace.services;


import com.mladentsev.simpleclientspace.dto.request.RequestRegisterDto;
import com.mladentsev.simpleclientspace.exception.UserExistsException;
import com.mladentsev.simpleclientspace.models.Account;
import com.mladentsev.simpleclientspace.models.Role;
import com.mladentsev.simpleclientspace.models.User;
import com.mladentsev.simpleclientspace.repositories.IAccountRepositories;
import com.mladentsev.simpleclientspace.repositories.IRoleRepositories;
import com.mladentsev.simpleclientspace.repositories.IUserRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class RegistrationService {

    private final PasswordEncoder encoder;

    private final IAccountRepositories iAccountRepositories;

    private final IUserRepositories iUserRepositories;

    private final IRoleRepositories iRoleRepositories;

    @Autowired
    public RegistrationService(PasswordEncoder encoder, IAccountRepositories iAccountRepositories, IUserRepositories iUserRepositories, IRoleRepositories iRoleRepositories) {
        this.encoder = encoder;
        this.iAccountRepositories = iAccountRepositories;
        this.iUserRepositories = iUserRepositories;
        this.iRoleRepositories = iRoleRepositories;
    }

    public void signUp(RequestRegisterDto requestRegisterDto) {
        if (iAccountRepositories.existsByLogin(requestRegisterDto.getLogin())) {
            throw new UserExistsException("Пользователь с логином '" + requestRegisterDto.getLogin() + "' уже существует");
        }

        User user = new User();
        user.setFirstName(requestRegisterDto.getFirstName());
        user.setLastName(requestRegisterDto.getLastName());
        user.setEmail(requestRegisterDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user = iUserRepositories.save(user);

        List<Role> roles = requestRegisterDto.getRoles().stream().map(
                func -> iRoleRepositories.findByRole(func))
                .collect(Collectors.toList());

        Account account = new Account();
        account.setLogin(requestRegisterDto.getLogin());
        account.setPassword(encoder.encode(requestRegisterDto.getPassword()));
        account.setActive(true);
        account.setUser(user);
        account.setRoles(roles);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        iAccountRepositories.save(account);

    }
    
}
