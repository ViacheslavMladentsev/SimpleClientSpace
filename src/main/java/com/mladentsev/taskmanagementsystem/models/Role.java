package com.mladentsev.taskmanagementsystem.models;

import com.mladentsev.taskmanagementsystem.enums.ERoles;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "roles")
public class Role  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "roles", length = 15, nullable = false)
    private ERoles role;

    @ManyToMany(mappedBy = "roles")
    private Set<Account> accounts;

}
