package com.mladentsev.simpleclientspace.services;

import com.mladentsev.simpleclientspace.models.Account;
import com.mladentsev.simpleclientspace.repositories.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Реализация сервиса, предоставляющего подробную информацию о пользователях для нужд Spring Security.
 * Поддерживает загрузку деталей пользователя по логину, построение списка прав доступа и проверку существования пользователя.
 */
@Service
public class AccountUserDetailServiceImpl implements UserDetailsService, IAccountService {

    /**
     * Репозиторий для работы с аккаунтами пользователей.
     */
    private final IAccountRepository iAccountRepository;

    /**
     * Конструктор с автоматической инъекцией репозитория аккаунтов.
     *
     * @param iAccountRepository репозиторий для работы с аккаунтами
     */
    @Autowired
    public AccountUserDetailServiceImpl(IAccountRepository iAccountRepository) {
        this.iAccountRepository = iAccountRepository;
    }

    /**
     * Загрузка подробной информации о пользователе по логину.
     *
     * @param username логин пользователя
     * @return объект UserDetails с информацией о пользователе
     * @throws UsernameNotFoundException если пользователь с указанным логином не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!iAccountRepository.existsByLogin(username)) {
            throw new UsernameNotFoundException("Пользователь с логином '" + username + "' не существует");
        }
        Account account = iAccountRepository.findByLogin(username).get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(account.getLogin())
                .password(account.getPassword())
                .authorities(buildAuthorities(account))
                .build();
    }

    /**
     * Сбор полномочий (прав доступа) пользователя на основе связанных с ним ролей.
     *
     * @param account объект аккаунта пользователя
     * @return коллекцию полномочий (GrantedAuthority)
     */
    private Collection<? extends GrantedAuthority> buildAuthorities(Account account) {
        return account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toSet());
    }

}
