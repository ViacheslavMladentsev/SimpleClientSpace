package com.mladentsev.taskmanagementsystem.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.mladentsev.taskmanagementsystem.models")
@ComponentScan(basePackages = {"com.mladentsev.taskmanagementsystem"})
@EnableJpaRepositories(basePackages = "com.mladentsev.taskmanagementsystem.repositories")
public class TaskManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSystemApplication.class, args);
	}

}
