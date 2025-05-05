package com.mladentsev.simpleclientspace.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Основной класс приложения Spring Boot.
 * <p>
 * Запускает приложение, осуществляет конфигурацию контекста Spring и организует контейнер Bean’ов.
 * Класс активирует сканирование аннотаций и автоматическую конфигурацию компонентов приложения.
 * Через этот класс начинается выполнение программы, выполняется инициализация и запуск контейнера Spring.
 *
 * @author Младенцев Вячеслав
 * @version 1.0
 * @since 2025-05-05
 */
@SpringBootApplication
@EntityScan("com.mladentsev.simpleclientspace.models")
@ComponentScan(basePackages = {"com.mladentsev.simpleclientspace"})
@EnableJpaRepositories(basePackages = "com.mladentsev.simpleclientspace.repositories")
public class SimpleClientSpaceApplication {

	/**
	 * Главная точка входа в приложение.
	 * <p>
	 * При запуске этого метода происходит следующая цепочка действий:
	 * <ol>
	 *     <li>Происходит регистрация Spring контекста и подготовка окружения.</li>
	 *     <li>Выполняется поиск и инициализация компонентов (бининг).</li>
	 *     <li>Производится загрузка профилей, считывание свойств и установка портов.</li>
	 *     <li>Запускается встроенный Tomcat-сервер.</li>
	 * </ol>
	 *
	 * @param args массив аргументов командной строки (не используется).
	 */
	public static void main(String[] args) {
		SpringApplication.run(SimpleClientSpaceApplication.class, args);
	}

}
