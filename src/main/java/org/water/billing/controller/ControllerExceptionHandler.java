package org.water.billing.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.water.billing.MyException;

@ControllerAdvice
public class ControllerExceptionHandler {
	private Logger logger = Logger.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(value = org.water.billing.MyException.class)
	public ModelAndView myExceptionHandler(HttpServletRequest request,MyException e) throws Exception {
		printExceptionStack(request,e);
		ModelAndView view = new ModelAndView();
		view.addObject("message",e.getMessage());
		view.setViewName("error");
		
		return view;
	}
	
	@ExceptionHandler(value = ConstraintViolationException.class)
	public ModelAndView violationExceptionHandler(HttpServletRequest request,ConstraintViolationException e) throws Exception {
		printExceptionStack(request,e);
		
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage() + "\n");
        }
		
		ModelAndView view = new ModelAndView();
		view.addObject("message",strBuilder.toString());
		view.setViewName("error");
		
		return view;
	}
	
	@ExceptionHandler(value = Exception.class)
	public ModelAndView commonExceptionHandler(HttpServletRequest request,Exception e) throws Exception {
		printExceptionStack(request,e);
		ModelAndView view = new ModelAndView();
		view.addObject("message",e.getMessage());
		view.setViewName("error");
		
		return view;
	}
	
	
	private void printExceptionStack(HttpServletRequest request,Exception e) {
		logger.error(request.getRequestURL());
		logger.error(e.getMessage(), e);
	}
}
