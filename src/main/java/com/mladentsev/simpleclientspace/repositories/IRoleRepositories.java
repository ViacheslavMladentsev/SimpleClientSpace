package com.mladentsev.simpleclientspace.repositories;


import com.mladentsev.simpleclientspace.enums.ERoles;
import com.mladentsev.simpleclientspace.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface IRoleRepositories extends JpaRepository<Role, Long> {
    List<Role> findAll();
    Role findByRole(ERoles eRoles);
}
