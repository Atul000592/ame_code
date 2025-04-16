package nic.ame.app.master.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.Login;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;

@Service
public class LoginUserDetailsImpl implements LoginUserDetails{

	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	//==================================forcePersonalId================================//
	@Override
	public ForcePersonnelDto getLoginUserDetails(String forcePersonalId) {
		
		Optional<ForcePersonnelDto> ForcePersonnel= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);
		if(!ForcePersonnel.isEmpty()) {
			return ForcePersonnel.get();
		}
		return null;
	}

	
	//=====================candidateForcePersonalId============================//
	@Override
	public ForcePersonnelDto getCandicateForcePersonalId(String candidateForcePersonalId) {
		Optional<ForcePersonnelDto> forcePersonal= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(candidateForcePersonalId);
		if(!forcePersonal.isEmpty()) {
			return forcePersonal.get();
		}
		return null;
	}
	
	
	/*
	 * @Override public ForcePersonnelDto getCandicateForcePersonnelId(String
	 * candidateForcePersonalId) { Optional<ForcePersonnelDto> forcePersonal=
	 * forcePersonalRepository.getByForcePersonalId(candidateForcePersonalId);
	 * if(!forcePersonal.isEmpty()) { return forcePersonal.get(); } return null; }
	 */


	@Override
	public Login loginDeatils(String userName ,String password) {
	   Login login=new Login();
		Optional<Login> loginOptional=loginRepository.findByUserNameAndPassword(userName,password );
		if(loginOptional.isEmpty()) {
			return login;
		}
		else {
		   login=loginOptional.get();
			return login;
		}
		
	}


	@Override
	public Login getUserNameByUserName(String userName) {
		 Login login=new Login();
		 Optional<Login> loginOptional=loginRepository.findByUserName(userName);
			if(loginOptional.isEmpty()) {
				return login;
			}
			else {
			   login=loginOptional.get();
				return login;
			}
	
	}


	@Override
	public boolean updateByUserName(String newPassword, String changedPassword1, String changedPassword2,
			String userName) {
		// TODO Auto-generated method stub
		return false;
	}

}
