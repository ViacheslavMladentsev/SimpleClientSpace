package com.mladentsev.taskmanagementsystem.models;


import jakarta.persistence.*;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "lsat_name", length = 20)
    private String lastName;

    @Column(name = "email")
    private String email;

    @OneToOne(mappedBy = "user")
    private Account account;

}
