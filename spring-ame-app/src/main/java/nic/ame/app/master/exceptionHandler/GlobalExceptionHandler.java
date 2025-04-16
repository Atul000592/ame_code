package nic.ame.app.master.exceptionHandler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.security.service.HttpRequestUtil;

@ControllerAdvice
public class GlobalExceptionHandler {
	  public GlobalExceptionHandler(HttpRequestUtil httpRequestUtil) {
		super();
		this.httpRequestUtil = httpRequestUtil;
	}


	private final HttpRequestUtil httpRequestUtil;
	
	  
	  
	Logger logger=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	
	  @ExceptionHandler(Exception.class) 
	  public ModelAndView handleException(Exception ex, Model model) {
	  
	  System.err.println("Exception caught: " + ex.getMessage()); ModelAndView
	  modelAndView = new ModelAndView();
	  
	  modelAndView.setViewName("redirect:/sign-out"); 
	  return modelAndView; 
	  }
	  
	 
	  
	 
    
 
    
    
}
