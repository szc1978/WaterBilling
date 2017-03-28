package org.water.billing.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest request,Exception e) throws Exception {
		ModelAndView view = new ModelAndView();
		view.addObject("exception",e);
		view.addObject("url",request.getRequestURL());
		view.setViewName("error");
		return view;
	}
}
