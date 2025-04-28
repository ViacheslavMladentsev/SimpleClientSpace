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

@Service
public class AccountUserDetailServiceImpl implements UserDetailsService, AccountService {

    private final IAccountRepository iAccountRepository;

    @Autowired
    public AccountUserDetailServiceImpl(IAccountRepository iAccountRepository) {
        this.iAccountRepository = iAccountRepository;
    }

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


    private Collection<? extends GrantedAuthority> buildAuthorities(Account account) {
        return account.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                .collect(Collectors.toSet());
    }

}
