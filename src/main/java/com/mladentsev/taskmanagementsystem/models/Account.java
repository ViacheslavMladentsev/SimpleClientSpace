package com.mladentsev.taskmanagementsystem.models;


import jakarta.persistence.*;

import lombok.*;

import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {

    @Column(name = "login", length = 20, nullable = false, unique = true, updatable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "accounts_roles",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "accounts_id"))
    private Set<Role> roles;

}
