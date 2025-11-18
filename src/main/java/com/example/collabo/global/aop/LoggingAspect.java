package com.example.collabo.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(com.example.collabo.domain..*Controller)")
    public void controllerMethods() {}

    @Pointcut("within(com.example.collabo.domain..*Service)")
    public void serviceMethods() {}

    @Around("controllerMethods()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes)
            RequestContextHolder.currentRequestAttributes()).getRequest();

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.info("===== [{}] {} - {}.{} 시작 =====",
            method, uri, className, methodName);

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            log.info("===== [{}] {} - 완료 ({}ms) =====",
                method, uri, executionTime);

            return result;
        } catch (Exception e) {
            log.error("===== [{}] {} - 실패: {} =====",
                method, uri, e.getMessage());
            throw e;
        }
    }

    @Around("serviceMethods()")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        log.debug("[Service] {}.{} 호출", className, methodName);

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            log.debug("[Service] {}.{} 완료 ({}ms)",
                className, methodName, executionTime);

            return result;
        } catch (Exception e) {
            log.error("[Service] {}.{} 실패: {}",
                className, methodName, e.getMessage());
            throw e;
        }
    }
}

