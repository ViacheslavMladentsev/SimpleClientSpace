package com.mladentsev.simpleclientspace.repositories;


import com.mladentsev.simpleclientspace.models.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findById(Long id);
    Optional<Account> findByLogin(String login);
    boolean existsByLogin(String login);

    @Modifying
    @Transactional
    @Query(value="UPDATE Account acc SET acc.isLogout=:newValue WHERE acc.id=:accountId")
    int updateIsLogout(@Param("accountId") long accountId, @Param("newValue") boolean newValue);
}
