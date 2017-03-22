package org.water.billing;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class WebLogAspect {
	private Logger logger = Logger.getLogger(WebLogAspect.class);

	@Pointcut("execution(public * org.water.billing.controller..*.*(..))")
    public void webLog(){}
	
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //logger.info("URL : " + request.getRequestURL().toString());
        //logger.info("HTTP_METHOD : " + request.getMethod());
        //logger.info("IP : " + request.getRemoteAddr());
        //logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }
    
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        //logger.info("RESPONSE : " + ret);
    }
}
