package com.thd.store.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.store.dto.BaseResponse;
import com.thd.store.util.SystemMessage;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

import static com.thd.store.util.SystemMessage.FORBIDDEN;


/**
 * @author DatNuclear 6/6/2023
 * @project NuclearShop
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {
    @Resource
    private MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        if (httpServletResponse.getStatus() == HttpStatus.OK.value()) {
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            BaseResponse errorResponse = BaseResponse.builder()
                    .status(HttpStatus.UNAUTHORIZED.name())
                    .code(HttpStatus.UNAUTHORIZED.value())
                    .message(messageSource.getMessage(SystemMessage.UNAUTHORIZED, null, LocaleContextHolder.getLocale()))
                    .build();
            OutputStream outputStream = httpServletResponse.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(outputStream, errorResponse);
            outputStream.flush();
        }
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(403);
        BaseResponse errorResponse = BaseResponse.builder()
                .status(HttpStatus.FORBIDDEN.name())
                .code(HttpStatus.UNAUTHORIZED.value())
                .message(messageSource.getMessage(FORBIDDEN,null, LocaleContextHolder.getLocale()))
                .build();
        OutputStream outputStream = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(outputStream, errorResponse);
        outputStream.flush();
    }

}
