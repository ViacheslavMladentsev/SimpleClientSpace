package com.mladentsev.taskmanagementsystem.models;


import jakarta.persistence.*;

import lombok.*;


@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "lsat_name", length = 20)
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "user")
    private Account account;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
