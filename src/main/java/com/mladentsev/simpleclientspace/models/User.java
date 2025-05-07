package com.mladentsev.simpleclientspace.models;


import jakarta.persistence.*;

import lombok.*;

/**
 * Модель пользователя.
 * <p>Представляет собой сущность "users" в базе данных и содержит основную информацию о пользователях системы.</p>
 */
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity {

    /**
     * Имя пользователя.
     */
    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Column(name = "lsat_name", length = 20)
    private String lastName;

    /**
     * Электронная почта пользователя.
     */
    @Column(name = "email")
    private String email;

    /**
     * Ассоциированный аккаунт пользователя.
     */
    @OneToOne(mappedBy = "user")
    private Account account;

    /**
     * Возвращает имя пользователя.
     * @return имя пользователя.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Устанавливает имя пользователя.
     * @param firstName новое имя пользователя.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Возвращает фамилию пользователя.
     * @return фамилия пользователя
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Устанавливает фамилию пользователя.
     * @param lastName новая фамилия пользователя
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Возвращает электронную почту пользователя.
     * @return электронная почта пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Устанавливает электронную почту пользователя.
     * @param email новая электронная почта пользователя
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Возвращает ассоциированный аккаунт пользователя.
     * @return аккаунт пользователя
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Устанавливает ассоциированный аккаунт пользователя.
     * @param account новый аккаунт пользователя
     */
    public void setAccount(Account account) {
        this.account = account;
    }

}
