package com.mladentsev.simpleclientspace.repositories;


import com.mladentsev.simpleclientspace.models.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepositories extends JpaRepository<User, Long> {}

