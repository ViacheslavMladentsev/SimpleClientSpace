package com.mladentsev.simpleclientspace.exceptionhandler;


import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик исключений приложения.
 * <p>Предназначен для обработки распространенных ошибок HTTP-запросов и предоставления полезных сообщений пользователям.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обработчик исключения {@link HttpMessageNotReadableException}, возникающего при ошибке десериализации JSON-данных.
     * <p>Возвращает сообщение о некорректном формате переданных данных.</p>
     *
     * @param ex исключение, связанное с проблемой чтения данных
     * @return Сообщение об ошибке
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleDeserializationError(HttpMessageNotReadableException ex) {
        return ex.getMessage() + "\nНекорректный формат тела запроса.";
    }

    /**
     * Обработчик исключения {@link MethodArgumentNotValidException}, возникающего при проверке валидности передаваемых данных.
     * <p>Формирует карту ошибок с указанием неверных полей и соответствующих сообщений об ошибках.</p>
     *
     * @param ex исключение, связанное с нарушением правил проверки
     * @return Карта с ошибками формата {имя ошибки - сообщение об ошибке}
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
