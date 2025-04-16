package nic.ame.app.master.service;

import nic.ame.app.master.dto.ForcePersonnelDto;

import nic.ame.app.master.model.Login;


public interface LoginUserDetails {
	
	public ForcePersonnelDto getLoginUserDetails(String forcePersonalId);
	
	public ForcePersonnelDto getCandicateForcePersonalId(String candidateForcePersonalId);
	
	
	public Login loginDeatils(String userName,String password);

	public Login getUserNameByUserName(String userName);
	

   boolean  updateByUserName(String newPassword,String changedPassword1,String changedPassword2,String userName);

	
}
