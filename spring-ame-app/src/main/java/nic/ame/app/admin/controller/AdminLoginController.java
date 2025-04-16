package nic.ame.app.admin.controller;

import java.security.Principal;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import nic.ame.app.master.model.Login;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;

@Controller
public class AdminLoginController {

	@Autowired
	private LoginUserDetails loginUserDetails;
	
	 
	@Autowired
	UserRoleService userRoleService;
	
	
	Logger logger=LogManager.getLogger(AdminLoginController.class);
	
	@RequestMapping("/admin")
	public String adminLoginPage() {
		logger.info("login -admin -page");
		return"admin-template/admin-login";
	}
	
	@RequestMapping("/admin-home")
	public String adminHomePage(Principal principal,Model model) {
		
		String userName=principal.getName();
		if(userName==null) {
			  model.addAttribute("errorMsg", "Invalid user with worng Email or password");
		      return "admin-template/admin-login";
		}
		Login login= loginUserDetails.getUserNameByUserName(userName);
		 

		if (!login.getForcePersonalId().equals(null)) {
			
			Set<String> userRoles= userRoleService.getRoleByforcePersonalId(login.getForcePersonalId());
			 if(userRoles.contains("admin")) {
				 return"admin-template/dashboard";
			 
			 }
			 else {
				 model.addAttribute("errorMsg", "Invalid user with worng Email or password");
			      return "admin-template/admin-login";
			 }
		}else {
			 model.addAttribute("errorMsg", "Invalid user with worng Email or password");
		      return "admin-template/admin-login";
		}
		
	}
	
}
