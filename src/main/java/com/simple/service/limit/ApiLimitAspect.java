package com.simple.service.limit;

import com.google.common.util.concurrent.RateLimiter;
import com.simple.dao.domain.SkGoods;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class ApiLimitAspect {
    private Logger logger = LoggerFactory.getLogger(ApiLimitAspect.class);

	RateLimiter rateLimiter = RateLimiter.create(10);

	@Pointcut("@annotation(com.simple.service.limit.ApiLimit)")
	public void apiLimit() {
	}

	@Around("apiLimit()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		if(!rateLimiter.tryAcquire(1, 10, TimeUnit.MILLISECONDS)){
			return SkGoods.builder().build();
		}
		return joinPoint.proceed();
	}


}
