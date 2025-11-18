package com.example.collabo.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class PerformanceAspect {

    private static final long SLOW_THRESHOLD_MS = 3000; // 3초

    @Around("execution(* com.example.collabo.domain..*Service.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            if (executionTime > SLOW_THRESHOLD_MS) {
                log.warn("[느린 쿼리 감지] {}.{} - 실행시간: {}ms (임계값: {}ms)",
                    className,
                    methodName,
                    executionTime,
                    SLOW_THRESHOLD_MS
                );
            } else if (executionTime > 1000) {
                log.info("[성능 모니터링] {}.{} - 실행시간: {}ms",
                    className,
                    methodName,
                    executionTime
                );
            }

            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("[성능 모니터링 - 예외] {}.{} - 실행시간: {}ms, 예외: {}",
                className,
                methodName,
                executionTime,
                e.getMessage()
            );
            throw e;
        }
    }

    @Around("execution(* com.example.collabo.domain..*Repository.*(..))")
    public Object monitorRepositoryPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - startTime;

            if (executionTime > 1000) {
                log.warn("[느린 DB 쿼리] {}.{} - 실행시간: {}ms",
                    className,
                    methodName,
                    executionTime
                );
            }

            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            log.error("[DB 쿼리 실패] {}.{} - 실행시간: {}ms, 예외: {}",
                className,
                methodName,
                executionTime,
                e.getMessage()
            );
            throw e;
        }
    }
}

