package com.mladentsev.simpleclientspace.models;


import jakarta.persistence.*;

import lombok.*;

import java.util.List;

/**
 * Модель учетной записи пользователя.
 *
 * <p>Представляет собой таблицу "accounts" в базе данных и содержит информацию о пользователях системы, такую как логин, пароль, активность и роли.</p>
 */
@Entity
@Table(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Account extends BaseEntity {

    /**
     * Логин пользователя.
     *
     * <ul>
     *   <li>Длина: максимум 20 символов.</li>
     *   <li>Длина: обязательный для заполенния.</li>
     *   <li>Уникален среди всех учётных записей.</li>
     *   <li>Не изменяется после создания.</li>
     * </ul>
     */
    @Column(name = "login", length = 20, nullable = false, unique = true, updatable = false)
    private String login;

    /**
     * Логин пользователя.
     * <li>Длина: обязательный для заполенния.</li>
     */
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_logout")
    private Boolean isLogout;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "accounts_roles",
            joinColumns = @JoinColumn(name = "accounts_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Role> roles;

    /**
     * Получает логин пользователя.
     * @return строковое представление
     */
    public String getLogin() {
        return login;
    }

    /**
     * Устанавливает логин пользователя.
     * @param login новый логин пользователя.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Получает пароль пользователя.
     * @return строковое представление.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.ьл
     * @param password новый пароль пользователя.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Проверяет, активна ли данная роль.
     * @return true, если роль активна, иначе false.
     */
    public Boolean isActive() {
        return isActive;
    }

    /**
     * Устанавливает активность пользователя.
     * @param active новый состояние активности пользователя.
     */
    public void setActive(Boolean active) {
        isActive = active;
    }

    /**
     * Проверяет, вход пользователя.
     * @return true, если пользователь вошел, иначе false.
     */
    public Boolean isLogout() {
        return isLogout;
    }

    /**
     * Устанавливает статус входа пользователя.
     * @param logout новое состояние входа пользователя.
     */
    public void setLogout(Boolean logout) {
        isLogout = logout;
    }

    /**
     * Возвращает пользователя, связанный с аккаунтом.
     * @return объект пользователя.
     */
    public User getUser() {
        return user;
    }

    /**
     * Устанавливает нового пользователя для аккаунта.
     * @param user новый пользователь.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Возвращает список ролей, назначенных аккаунту.
     * @return список ролей
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Устанавливает список ролей пользователя для аккаунта.
     * @param roles новый пользователь.
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}
