package com.sprint4us.demo.dao;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CountryLanguageDAOLoggingAspect {

	private final static Logger logger = LoggerFactory
			.getLogger(CountryLanguageDAOLoggingAspect.class);

	@Pointcut("execution(* com.sprint4us.demo.dao..*.*(..))")
	public void methodPointcut() {

	}

	@Before("methodPointcut()")
	public void logBefore(JoinPoint joinPoint) {

		logger.debug("{} | IN  | {}", joinPoint.getSignature().getName(),
				joinPoint.getArgs());
	}

	@AfterReturning(pointcut = "methodPointcut()", returning = "retVal")
	public void logAfter(JoinPoint joinPoint, Object retVal) {

		logger.debug("{} | OUT | {}", joinPoint.getSignature().getName(),
				retVal);
	}
}