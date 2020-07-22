package com.mycompany.orderapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Around("@annotation(LogInputAndExecutionTime) || execution(public * com.mycompany.orderapi.rest.OrderController.*(..))")
    public Object logInputAndExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long t = System.currentTimeMillis();
        log.info("=> {} :: args: {}", pjp.getSignature().toShortString(), pjp.getArgs());

        Object retVal = pjp.proceed();

        log.info("<= {} :: Execution Time: {}ms", pjp.getSignature().toShortString(), System.currentTimeMillis() - t);
        return retVal;
    }

}
