package com.mladentsev.simpleclientspace.models;


import com.mladentsev.simpleclientspace.enums.ERoles;

import jakarta.persistence.*;

import lombok.*;

import java.util.Set;

/**
 * Модель роли пользователя.
 *
 * <p>Представляет собой сущность "roles" в базе данных, содержащую информацию о ролях пользователей.</p>
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
public class Role  {

    /**
     * Уникальный идентификатор сущности.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип роли пользователя.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "roles", length = 15, nullable = false)
    private ERoles role;

    /**
     * Множество аккаунтов, ассоциированных с данной ролью.
     */
    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts;

    /**
     * Возвращает идентификатор роли.
     * @return Идентификатор роли.
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает идентификатор роли.
     * @param id Новый идентификатор роли.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает тип роли пользователя.
     * @return Тип роли пользователя.
     */
    public ERoles getRole() {
        return role;
    }

    /**
     * Устанавливает тип роли пользователя.
     * @param role Новый тип роли пользователя.
     */
    public void setRole(ERoles role) {
        this.role = role;
    }

    /**
     * Возвращает множество аккаунтов, ассоциированных с данной ролью.
     * @return Множество аккаунтов.
     */
    public Set<Account> getAccounts() {
        return accounts;
    }

    /**
     * Устанавливает множество аккаунтов, ассоциированных с данной ролью.
     * @param accounts Новое множество аккаунтов
     */
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

}
