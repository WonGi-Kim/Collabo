package com.example.collabo.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class ExceptionLoggingAspect {

    @AfterThrowing(
        pointcut = "within(com.example.collabo.domain..*)",
        throwing = "exception"
    )
    public void logException(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.error("[예외 발생] {}.{} - 타입: {}, 메시지: {}",
            className,
            methodName,
            exception.getClass().getSimpleName(),
            exception.getMessage()
        );

        if (args != null && args.length > 0) {
            log.error("[예외 파라미터] {}", Arrays.toString(args));
        }

        // 스택 트레이스는 DEBUG 레벨에서만 출력
        if (log.isDebugEnabled()) {
            log.debug("[예외 상세]", exception);
        }
    }
}

