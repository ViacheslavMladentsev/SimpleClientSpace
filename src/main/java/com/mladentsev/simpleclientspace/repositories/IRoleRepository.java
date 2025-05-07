package com.mladentsev.simpleclientspace.repositories;


import com.mladentsev.simpleclientspace.enums.ERoles;
import com.mladentsev.simpleclientspace.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Интерфейс репозитория для работы с сущностью Role.
 * <p>Позволяет осуществлять CRUD-операции над моделью Role и дополнительные специализированные запросы.</p>
 */
public interface IRoleRepository extends JpaRepository<Role, Long> {

    /**
     * Получает список всех существующих ролей.
     *
     * @return список всех ролей
     */
    List<Role> findAll();

    /**
     * Находит роль по значению перечислимого типа ERoles.
     *
     * @param eRoles Значение перечислимого типа роли
     * @return Роль, соответствующая указанному значению
     */
    Role findByRole(ERoles eRoles);

}
