package com.mladentsev.simpleclientspace.controllers;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

/**
 * Контроллер, для тестирования защищенных ресурсов и авторизированных пользователей в зависимости от ролей.
 * Предоставляет несколько методов для проверки прав доступа
 * <ol>
 *     <li>для любого аутентифицированного пользователя ({@link #testUser})
 *     <li>для аутентифицированного пользователя с ролью ROLE_ADMIN или ROLE_MANAGER ({@link #testManager()})
 *     <li>для аутентифицированного пользователя с ролью ROLE_ADMIN ({@link #testAdmin()})
 * </ol>
 */
@RequestMapping("api/v1")
@RestController
public class TestAuthController {

    /**
     * Защищеный ресурс для всех аутентифицированных пользователей.
     * @return сообщение об успешном доступе в случае успеха, ошибку доступа в противном случае.
     */
    @GetMapping("/test_user")
    public ResponseEntity<Object> testUser() {
        return ResponseEntity.ok().body("Аутентифицированный пользователь успешно получил защищенный ресурс доступный для всех");
    }

    /**
     * Защищеный ресурс для пользователей имеющих роль ROLE_MANAGER или ROLE_ADMIN.
     * @return сообщение об успешном доступе в случае успеха, ошибку доступа в противном случае.
     */
    @GetMapping("/test_manager")
    public ResponseEntity<Object> testManager() {
        return ResponseEntity.ok().body("Аутентифицированный пользователь успешно получил защищенный ресурс доступный для роли Admin и Manager");
    }

    /**
     * Защищеный ресурс для пользователей имеющих роль ROLE_ADMIN.
     * @return сообщение об успешном доступе в случае успеха, ошибку доступа в противном случае.
     */
    @GetMapping("/test_admin")
    public ResponseEntity<Object> testAdmin() {
        return ResponseEntity.ok().body("Аутентифицированный пользователь успешно получил защищенный ресурс доступный для роли Admin");
    }

}


