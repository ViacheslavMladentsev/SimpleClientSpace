package com.mladentsev.simpleclientspace.repositories;


import com.mladentsev.simpleclientspace.models.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс репозитория для работы с сущностью User.
 * <p>Используется для выполнения стандартных CRUD-операций над моделью User.</p>
 */
public interface IUserRepository extends JpaRepository<User, Long> {}

