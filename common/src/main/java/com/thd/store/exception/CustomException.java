package com.thd.store.exception;

import com.thd.store.dto.BaseResponse;
import com.thd.store.util.SystemMessage;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author DatNuclear 17/01/2024
 * @project store-movie
 */
@RestControllerAdvice
@Slf4j
public class CustomException extends ResponseEntityExceptionHandler {
    @Resource
    private MessageSource messageSource;

    @ExceptionHandler({CredentialsExpiredException.class, BadCredentialsException.class,
            AccessDeniedException.class, AuthenticationException.class,
            UsernameNotFoundException.class, SignatureException.class, ExpiredJwtException.class})
    public ResponseEntity<BaseResponse> handleSecurityException(Exception exception) {
        BaseResponse errorDetail = BaseResponse.builder()
                .timestamp(LocalDateTime.now())
                .build();
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        log.error(exception.getMessage());
        if (exception instanceof CredentialsExpiredException) {
            errorDetail.setMessage(messageSource.getMessage(SystemMessage.USER_CREDENTIALS_IS_EXPIRED, null, LocaleContextHolder.getLocale()));
        }
        if (exception instanceof LockedException) {
            errorDetail.setMessage(messageSource.getMessage(SystemMessage.USER_IS_LOCKED, null, LocaleContextHolder.getLocale()));
        }
        if (exception instanceof AccountExpiredException) {
            errorDetail.setMessage(messageSource.getMessage(SystemMessage.USER_IS_EXPIRED, null, LocaleContextHolder.getLocale()));
        }
        if (exception instanceof DisabledException) {
            errorDetail.setMessage(messageSource.getMessage(SystemMessage.USER_IS_DISABLE, null, LocaleContextHolder.getLocale()));
        }
        if (exception instanceof BadCredentialsException) {
            httpStatus = HttpStatus.BAD_REQUEST;
            errorDetail.setMessage(messageSource.getMessage(SystemMessage.USER_BAD_CREDENTIALS, null, LocaleContextHolder.getLocale()));
        }
        if (exception instanceof AccessDeniedException) {
            httpStatus = HttpStatus.FORBIDDEN;
            errorDetail.setMessage(messageSource.getMessage(SystemMessage.ACCESS_DENIED, null, LocaleContextHolder.getLocale()));
        }
        if (exception instanceof ExpiredJwtException) {
            errorDetail.setMessage(messageSource.getMessage(SystemMessage.JWT_IS_EXPIRED, null, LocaleContextHolder.getLocale()));
        }
        if (exception instanceof SignatureException) {
            errorDetail.setMessage(messageSource.getMessage(SystemMessage.TOKEN_INVALID, null, LocaleContextHolder.getLocale()));
        }
        errorDetail.setCode(httpStatus.value());
        errorDetail.setStatus(httpStatus.name());
        return ResponseEntity.status(httpStatus).body(errorDetail);
    }

    @ExceptionHandler({Exception.class,StoreException.class})
    public final ResponseEntity<BaseResponse> handleAllExceptions(Exception ex, WebRequest request) {
        log.error(ex.getMessage());
        BaseResponse error = BaseResponse.builder()
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
