package com.mladentsev.simpleclientspace.repositories;


import com.mladentsev.simpleclientspace.models.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<User, Long> {}

