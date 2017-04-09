package org.water.billing;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.water.billing.annotation.OpAnnotation;
import org.water.billing.consts.OpTypeEnum;
import org.water.billing.entity.admin.OperationHistory;
import org.water.billing.entity.admin.SysUser;
import org.water.billing.service.admin.OperationHistoryService;

@Aspect
@Component
public class WebLogAspect {
	
	@Autowired
	OperationHistoryService opHistoryService;
	
	@Pointcut("execution(public * org.water.billing.controller..*.*(..))")  
	public void aApplogic() {}  
	  
	@AfterReturning(value = "aApplogic() && @annotation(annotation) &&args(object,..) ", argNames = "annotation,object")  
	public void interceptorApplogic(OpAnnotation annotation, Object object) throws Throwable  {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		String ip = request.getRemoteAddr();
		
		String content = user.getChineseName() + "访问模块[" + annotation.moduleName() + "->" + annotation.option() + "]";
		OperationHistory op = new OperationHistory(user.getName(),user.getChineseName(),annotation.moduleName(),annotation.option(),ip,content,OpTypeEnum.OPERATION.getId());
		opHistoryService.save(op);
	}
	 
	@AfterThrowing(value = "aApplogic() && @annotation(annotation) &&args(object,..) ", argNames = "annotation,object")  
	public void interceptorExceptionApplogic(OpAnnotation annotation, Object object) throws Throwable  {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		SecurityContext securityContext = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		SysUser user = (SysUser) securityContext.getAuthentication().getPrincipal();
		String ip = request.getRemoteAddr();
		
		String content = user.getChineseName() + "访问模块[" + annotation.moduleName() + "->" + annotation.option() + "]";
		OperationHistory op = new OperationHistory(user.getName(),user.getChineseName(),annotation.moduleName(),annotation.option(),ip,content,OpTypeEnum.EXCEPTION.getId());
		opHistoryService.save(op);
	}
	 
	 

	/*@Pointcut("execution(public * org.water.billing.controller..*.*(..))")
    public void webLog(){}
	
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie[] it = request.getCookies();
        for(Cookie c : it) {
        	System.out.println(c.getName() + ":" + c.getValue());
        }
        
        Enumeration<String> it1 = request.getHeaderNames();
        while(it1.hasMoreElements())
        	System.out.println(it1.nextElement());
        
        //logger.info("Session:"+request.getSession().getValueNames());
        String msg = "";
        msg += request.getRemoteAddr();
        msg += "," + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        msg += "," + Arrays.toString(joinPoint.getArgs());
        logger.info("AOP:" + msg);
    }
    
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        //logger.info("RESPONSE : " + ret);
    }*/
}
