package com.example.dividend.exception;

import com.example.dividend.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.dividend.type.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScrapException.class)
    public ErrorResponse handleAccountException(ScrapException e) {
        log.error("'{}':'{}'", e.getErrorCode(), e.getErrorMessage());

        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage(),e.getStatusCode().value());
    }

    @ExceptionHandler(SecurityException.class)
    public ErrorResponse handleAccountException(SecurityException e) {
        log.error("'{}':'{}'", e.getErrorCode(), e.getErrorMessage());

        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage(),e.getStatusCode().value());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException is occurred.", e);

        return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getDescription(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException is occurred.", e);

        return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getDescription(),HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        log.error("AccessDeniedException is occurred", e);

        return new ErrorResponse(ACCESS_DENIED
                , ACCESS_DENIED.getDescription(),HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ErrorResponse handleRedisConnectionFailureException(RedisConnectionFailureException e) {
        log.error("RedisConnectionFailureException is occurred");
        return new ErrorResponse(FAIL_TO_CONNECT_REDIS_SERVER,
                FAIL_TO_CONNECT_REDIS_SERVER.getDescription(),HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.error("Exception is occurred.", e);

        return new ErrorResponse(
                INTERNAL_SERVER_ERROR,
                INTERNAL_SERVER_ERROR.getDescription(),HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }
}