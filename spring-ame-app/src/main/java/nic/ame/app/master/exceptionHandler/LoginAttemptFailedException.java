package nic.ame.app.master.exceptionHandler;

import org.springframework.security.core.AuthenticationException;

public class LoginAttemptFailedException extends AuthenticationException  {

	
	
	public LoginAttemptFailedException(String message) {
       
		super(message);
       }
	
	

}
