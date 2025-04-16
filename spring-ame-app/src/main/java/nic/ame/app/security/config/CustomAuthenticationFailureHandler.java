package nic.ame.app.security.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nic.ame.app.master.exceptionHandler.LoginAttemptFailedException;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler  {

	Logger logger=LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);
	
	
	
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, 
    		AuthenticationException exception) throws IOException, ServletException {
        String errorMessage = "Invalid username or password";
        
        if (exception instanceof LoginAttemptFailedException) {
            errorMessage = exception.getMessage();
            if(errorMessage==null) {
            	errorMessage="login attempt failed .....";
            }
        }
        logger.info("CustomAuthenticationFailureHandler >>>>>>> time :"+LocalDateTime.now());
        request.getSession().setAttribute("errorMsg", errorMessage);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        redirectAttributes.addFlashAttribute("errorMsg", errorMessage);
        response.sendRedirect("/initial-login");
    }

}
