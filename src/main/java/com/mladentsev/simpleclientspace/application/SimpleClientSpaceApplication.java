package com.mladentsev.simpleclientspace.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.mladentsev.simpleclientspace.models")
@ComponentScan(basePackages = {"com.mladentsev.simpleclientspace"})
@EnableJpaRepositories(basePackages = "com.mladentsev.simpleclientspace.repositories")
public class SimpleClientSpaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleClientSpaceApplication.class, args);
	}

}
