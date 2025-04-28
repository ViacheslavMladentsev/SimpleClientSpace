package com.mladentsev.simpleclientspace.models;


import com.mladentsev.simpleclientspace.enums.ERoles;

import jakarta.persistence.*;

import lombok.*;

import java.util.Set;


@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString(callSuper = true)
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", length = 15, nullable = false)
    private ERoles role;

    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERoles getRole() {
        return role;
    }

    public void setRole(ERoles role) {
        this.role = role;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

}
