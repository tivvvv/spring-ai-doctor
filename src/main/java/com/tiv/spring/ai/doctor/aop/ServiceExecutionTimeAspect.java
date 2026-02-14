package com.tiv.spring.ai.doctor.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 服务执行时间切面
 */
@Slf4j
@Aspect
@Component
public class ServiceExecutionTimeAspect {

    @Around("execution(* com.tiv.spring.ai.doctor.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object proceed = joinPoint.proceed();
        String pointCut = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();

        stopWatch.stop();
        long executeTime = stopWatch.getTotalTimeMillis();
        log.debug("[方法执行耗时]: {} took {} ms, args: {}", pointCut, executeTime, joinPoint.getArgs());
        return proceed;
    }

}
