package com.mladentsev.simpleclientspace.repositories;


import com.mladentsev.simpleclientspace.models.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Интерфейс репозитория для работы с сущностью Account.
 * <p>Используется для взаимодействия с базой данных и выполнения CRUD-операций над моделью Account.</p>
 */
public interface IAccountRepository extends JpaRepository<Account, Long> {

    /**
     * Находит аккаунт по уникальному идентификатору.
     *
     * @param id идентификатор аккаунта
     * @return необязательная сущность аккаунта (Optional)
     */
    Optional<Account> findById(Long id);

    /**
     * Находит аккаунт по логину пользователя.
     *
     * @param login логин пользователя
     * @return необязательная сущность аккаунта (Optional)
     */
    Optional<Account> findByLogin(String login);

    /**
     * Проверяет существование аккаунта с указанным логином.
     *
     * @param login логин пользователя
     * @return true, если аккаунт найден, иначе false
     */
    boolean existsByLogin(String login);

    /**
     * Обновляет признак выхода из системы для указанного аккаунта.
     * <p>Выполняет SQL-запрос на уровне базы данных для изменения значения isLogout.</p>
     *
     * @param accountId идентификатор аккаунта
     * @param newValue новое значение признака выхода
     * @return количество затронутых записей (обычно 1 или 0)
     */
    @Modifying
    @Transactional
    @Query(value="UPDATE Account acc SET acc.isLogout=:newValue WHERE acc.id=:accountId")
    int updateIsLogout(@Param("accountId") long accountId, @Param("newValue") boolean newValue);

}
