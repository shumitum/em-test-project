package com.effectivemobile.testproject.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.SignatureException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.effectivemobile.testproject.utility.Constant.DATE_TIME_PATTERN;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException exc, HttpServletRequest request) {
        log.warn("Получен статус 400 BAD_REQUEST {}", exc.getMessage());
        return ErrorResponse.builder()
                .message(String.format("Field: [%s] %s", Objects.requireNonNull(exc.getFieldError()).getField(),
                        Objects.requireNonNull(exc.getFieldError()).getDefaultMessage()))
                .status(HttpStatus.BAD_REQUEST.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException exc, HttpServletRequest request) {
        log.warn("Получен статус 400 BAD_REQUEST {}", exc.getMessage());
        return ErrorResponse.builder()
                .message(exc.getMessage())
                .status(HttpStatus.BAD_REQUEST.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleIllegalArgumentException(final IllegalArgumentException exc, HttpServletRequest request) {
        log.warn("Получен статус 403 FORBIDDEN {}", exc.getMessage());
        return ErrorResponse.builder()
                .message(exc.getMessage())
                .status(HttpStatus.FORBIDDEN.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleSignatureException(final SignatureException exc, HttpServletRequest request) {
        log.warn("Получен статус 401 UNAUTHORIZED {}", exc.getMessage());
        return ErrorResponse.builder()
                .message(exc.getMessage())
                .status(HttpStatus.UNAUTHORIZED.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoSuchElementException(final NoSuchElementException exc, HttpServletRequest request) {
        log.warn("Получен статус 404 NOT FOUND {}", exc.getMessage());
        return ErrorResponse.builder()
                .message(exc.getMessage())
                .status(HttpStatus.NOT_FOUND.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(final HttpMessageNotReadableException exc, HttpServletRequest request) {
        log.warn("Получен статус 400 BAD REQUEST {}", exc.getMessage());
        return ErrorResponse.builder()
                .message(exc.getMessage())
                .status(HttpStatus.BAD_REQUEST.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(final Exception exc, HttpServletRequest request) {
        log.warn("Получен статус 500 INTERNAL SERVER ERROR {}", exc.getMessage());
        return ErrorResponse.builder()
                .message(exc.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)))
                .build();
    }
}