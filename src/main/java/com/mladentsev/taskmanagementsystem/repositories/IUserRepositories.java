package com.mladentsev.taskmanagementsystem.repositories;


import com.mladentsev.taskmanagementsystem.models.User;

import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepositories extends JpaRepository<User, Long> {}
