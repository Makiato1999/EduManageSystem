package com.easybytes.easyschool.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
    @Around("execution(* com.easybytes.easyschool..*.*(..))")
    //  @Around 它允许你在目标方法执行 前后 执行自定义逻辑
    // 这里的表达式execution(* com.easybytes.easyschool..*.*(..))
    // 指的是com.easybytes.easyschool包及其子包中所有类的所有方法。星号*代表匹配所有返回类型，..代表匹配任意参数列表。
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(joinPoint.getSignature().toString() + " method execution start");
        // joinPoint.getSignature().toString()提供了被调用方法的签名。

        Instant start = Instant.now();
        Object returnObj = joinPoint.proceed();
        // 调用proceed()方法来执行目标方法，并获取执行结果。这个调用是实际执行被拦截方法的地方。

        Instant finish = Instant.now();
        long timeElapsed  = Duration.between(start, finish).toMillis();
        log.info("Time took to execute "+ joinPoint.getSignature().toString()+ " method is : "+timeElapsed);
        log.info(joinPoint.getSignature().toString() + " method execution end");
        return  returnObj;
    }

    @AfterThrowing(value = "execution(* com.easybytes.easyschool.*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error(joinPoint.getSignature()+" An exception happened due to : "+ex.getMessage());
    }
}
