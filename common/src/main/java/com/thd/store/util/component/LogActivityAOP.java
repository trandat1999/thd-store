package com.thd.store.util.component;

import com.thd.store.util.ConstUtil;
import com.thd.store.util.anotation.LogActivity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author DatNuclear 22/01/2024
 * @project store-movie
 */
@Aspect
@Component
@Slf4j
public class LogActivityAOP {
    @Around("@annotation(com.thd.store.util.anotation.LogActivity)")
    public Object logUsername(ProceedingJoinPoint call) throws Throwable {
        Object[] args = call.getArgs();
        MethodSignature signature = (MethodSignature) call.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        StringBuilder params = new StringBuilder();
        assert args.length == parameterAnnotations.length;
        for(int i = 0; i < args.length; i++){
            for (Annotation annotation : parameterAnnotations[i]) {
                if (!(annotation instanceof PathVariable)){
                    continue;
                }
                PathVariable pathVariable = (PathVariable) annotation;
                params.append(pathVariable.value()).append(ConstUtil.EQUAL).append(ConstUtil.SPACE).append(args[i]);
            }
        }
        LogActivity logUsername = method.getAnnotation(LogActivity.class);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info(logUsername.m() +ConstUtil.SPACE + params +" by username: {}",username);
        return call.proceed();
    }
}
