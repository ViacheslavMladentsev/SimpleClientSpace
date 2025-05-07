package com.mladentsev.simpleclientspace.handler;

import com.mladentsev.simpleclientspace.repositories.IAccountRepository;
import com.mladentsev.simpleclientspace.services.IJwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * Пользовательская реализация обработчика выхода (logout),
 * обеспечивающая дополнительный механизм отслеживания состояния пользователя.
 * <p>Реализует интерфейс {@link LogoutHandler}. Предназначен для обработки события выхода пользователя из системы
 * путем модификации статуса аккаунта в хранилище данных.</p>
 */
@Component
public class CustomLogoutHandler implements LogoutHandler {

    /**
     * Репозиторий аккаунтов для работы с данными учетных записей.
     */
    private final IAccountRepository iAccountRepository;

    /**
     * Сервис для работы с JWT-токенами.
     */
    private final IJwtService iJwtService;

    /**
     * Конструктор обработчика выхода с инъекцией зависимостей.
     *
     * @param iAccountRepository Репозиторий для работы с учетными записями
     * @param iJwtService        Сервис для работы с JWT-токенами
     */
    @Autowired
    public CustomLogoutHandler(IAccountRepository iAccountRepository, IJwtService iJwtService) {
        this.iAccountRepository = iAccountRepository;
        this.iJwtService = iJwtService;
    }

    /**
     * Выполнение действий при выходе пользователя из системы.
     *
     * <p>Реализует стандартный интерфейс {@link LogoutHandler}. Процесс включает следующие шаги:</p>
     * <ol>
     *     <li>Извлечение токена из заголовка Authorization.</li>
     *     <li>Извлечение имени пользователя из токена с помощью JWT-сервисов.</li>
     *     <li>Изменение статуса аккаунта (установка флага isLogout=true).</li>
     * </ol>
     *
     * @param request        объект запроса HTTP
     * @param response       объект ответа HTTP
     * @param authentication объект аутентификации текущего пользователя
     */
    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        iAccountRepository.updateIsLogout(iAccountRepository.findByLogin(iJwtService.extractUsername(token)).get().getId(), true);

    }
}
