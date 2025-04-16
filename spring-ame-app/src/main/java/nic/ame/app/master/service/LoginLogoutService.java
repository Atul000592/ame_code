package nic.ame.app.master.service;


import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.master.model.LoginLogoutCredentials;


public interface LoginLogoutService {
 
	
 LoginLogoutCredentials	loginStatus(String userName,HttpServletRequest httpServletRequest );
 LoginLogoutCredentials	logoutStatus(String userName,HttpServletRequest httpServletRequest );
	
}
