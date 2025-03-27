package com.mladentsev.taskmanagementsystem.repositories;


import com.mladentsev.taskmanagementsystem.enums.ERoles;
import com.mladentsev.taskmanagementsystem.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IRoleRepositories extends JpaRepository<Role, Long> {
    List<Role> findAll();
    Role findByRole(ERoles eRoles);
}
