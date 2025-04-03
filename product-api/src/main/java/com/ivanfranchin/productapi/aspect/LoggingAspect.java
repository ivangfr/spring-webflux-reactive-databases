package com.ivanfranchin.productapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Around("execution(public * com.ivanfranchin.productapi.product.ProductController.*(..))")
    public Object logInputAndExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long t = System.currentTimeMillis();
        log.info("=> {} :: args: {}", pjp.getSignature().toShortString(), pjp.getArgs());

        Object retVal = pjp.proceed();

        log.info("<= {} :: Execution Time: {}ms", pjp.getSignature().toShortString(), System.currentTimeMillis() - t);
        return retVal;
    }
}
