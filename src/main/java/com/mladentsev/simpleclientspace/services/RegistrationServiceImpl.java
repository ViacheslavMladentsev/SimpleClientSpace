package com.mladentsev.simpleclientspace.services;


import com.mladentsev.simpleclientspace.dto.request.RequestRegisterDto;

import com.mladentsev.simpleclientspace.models.Account;
import com.mladentsev.simpleclientspace.models.Role;
import com.mladentsev.simpleclientspace.models.User;

import com.mladentsev.simpleclientspace.repositories.IAccountRepository;
import com.mladentsev.simpleclientspace.repositories.IRoleRepository;
import com.mladentsev.simpleclientspace.repositories.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса регистрации новых пользователей.
 */
@Service
public class RegistrationServiceImpl implements IRegistrationService {
    /**
     * Компонент Spring Security для шифрования паролей перед сохранением в базе данных
     */
    private final PasswordEncoder encoder;

    /**
     *Репозиторий для операций с объектами Account (аккаунтами пользователей)
     */
    private final IAccountRepository iAccountRepositories;
    /**
     * Репозиторий для операций с объектами User (персонализированными данными пользователей)
     */
    private final IUserRepository iUserRepositories;

    /**
     * Репозиторий для операций с объектами Role (ролями пользователей)
     */
    private final IRoleRepository iRoleRepositories;

    /**
     * Конструктор класса, инициализирующий зависимости.
     *
     * @param encoder               компонент Spring Security для хеширования пароля
     * @param iAccountRepositories           репозиторий для операций над аккаунтами
     * @param iUserRepositories              репозиторий для операций над пользователями
     * @param iRoleRepositories              репозиторий для операций над ролями
     */
    @Autowired
    public RegistrationServiceImpl(PasswordEncoder encoder, IAccountRepository iAccountRepositories, IUserRepository iUserRepositories, IRoleRepository iRoleRepositories) {
        this.encoder = encoder;
        this.iAccountRepositories = iAccountRepositories;
        this.iUserRepositories = iUserRepositories;
        this.iRoleRepositories = iRoleRepositories;
    }

    /**
     * Регистрация нового пользователя.
     * Если аккаунт с указанным логином уже существует, выбрасывается исключение {@link UsernameNotFoundException}.
     *
     * @param requestRegisterDto объект DTO, содержащий данные регистрации (логин, пароль, ФИО, роли)
     */
    public void signUp(RequestRegisterDto requestRegisterDto) {
        if (iAccountRepositories.existsByLogin(requestRegisterDto.getLogin())) {
            throw new UsernameNotFoundException("Пользователь с логином '" + requestRegisterDto.getLogin() + "' уже существует");
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
        account.setLogout(true);
        account.setUser(user);
        account.setRoles(roles);
        account.setCreatedAt(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        iAccountRepositories.save(account);

    }
    
}
