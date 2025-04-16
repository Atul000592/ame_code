package nic.ame.app.master.serviceImpl;

import java.util.Calendar;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.master.model.LoginLogoutCredentials;
import nic.ame.app.master.repository.LoginLogoutCredentialsRepository;
import nic.ame.app.master.service.LoginLogoutService;
import nic.ame.master.util.GetIpAddressClient;

@Service
public class LoginLogoutServiceImpl implements LoginLogoutService{

	Logger logger=LoggerFactory.getLogger(LoginLogoutCredentials.class);
	
	@Autowired
	private LoginLogoutCredentialsRepository loginLogoutCredentialsRepository;
	
	
	@Override
	public LoginLogoutCredentials loginStatus(String userName,HttpServletRequest httpServletRequest) {
		logger.info("LoginLogoutCredentials status update............");
		
		LoginLogoutCredentials loginLogoutCredentials=new LoginLogoutCredentials();
		if(httpServletRequest.equals(null)) {
			loginLogoutCredentials.setIpAddress("NO VALUE PRESENT.....!");
		}else {
			loginLogoutCredentials.setIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(httpServletRequest));
		}
		
		loginLogoutCredentials.setHttpSessionValue(String.valueOf(httpServletRequest.getSession(false).getId()));
		loginLogoutCredentials.setLoginTime(Calendar.getInstance().getTime());
		loginLogoutCredentials.setUserName(userName);
	    
		return	loginLogoutCredentialsRepository.save(loginLogoutCredentials);
		
		
	}


	@Override
	public LoginLogoutCredentials logoutStatus(String userName, HttpServletRequest httpServletRequest) {
		String currentSession= null;
		LoginLogoutCredentials credentials=new LoginLogoutCredentials();
		if(httpServletRequest.getSession(false)!=null) {
			currentSession=String.valueOf(httpServletRequest.getSession(false).getId());
			Optional<LoginLogoutCredentials> loginLogoutCredentials=loginLogoutCredentialsRepository.findByHttpSessionValue(currentSession);
			if(loginLogoutCredentials.isEmpty()) {
				return null;
			}else {
				credentials=loginLogoutCredentials.get();
				credentials.setLogoutTime(Calendar.getInstance().getTime());
				return loginLogoutCredentialsRepository.save(credentials);
				
			}
		}else
		{
			return null;
		}
		
		
		
			
		
		
	
	}
	
	
	
	
	

}
