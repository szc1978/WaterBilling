package org.water.billing.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {
	private Logger logger = Logger.getLogger(ControllerExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request,Exception e) throws Exception {
		ModelAndView view = new ModelAndView();
		view.addObject("exception",e);
		view.setViewName("error");
		logger.error(request.getRequestURL());
		logger.error(e.getMessage(), e);
		return view;
	}
}
