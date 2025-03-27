package com.mladentsev.taskmanagementsystem.services;

import com.mladentsev.taskmanagementsystem.dto.RequestRegisterDto;
import com.mladentsev.taskmanagementsystem.enums.ERoles;
import com.mladentsev.taskmanagementsystem.exception.UserExistsException;
import com.mladentsev.taskmanagementsystem.models.Account;
import com.mladentsev.taskmanagementsystem.models.Role;
import com.mladentsev.taskmanagementsystem.models.User;
import com.mladentsev.taskmanagementsystem.repositories.IAccountRepositories;
import com.mladentsev.taskmanagementsystem.repositories.IRoleRepositories;
import com.mladentsev.taskmanagementsystem.repositories.IUserRepositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AccountService {

    private PasswordEncoder encoder;

    private final IAccountRepositories iAccountRepositories;

    private final IUserRepositories iUserRepositories;

    private final IRoleRepositories iRoleRepositories;

    @Autowired
    public AccountService(PasswordEncoder encoder, IAccountRepositories iAccountRepositories, IUserRepositories iUserRepositories, IRoleRepositories iRoleRepositories) {
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
