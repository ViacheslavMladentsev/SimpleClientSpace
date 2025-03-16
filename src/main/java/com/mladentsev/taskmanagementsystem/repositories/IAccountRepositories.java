package com.mladentsev.taskmanagementsystem.repositories;


import com.mladentsev.taskmanagementsystem.models.Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IAccountRepositories extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
    Optional<Account> findByLogin(String login);
    boolean existsByLogin(String login);
}
