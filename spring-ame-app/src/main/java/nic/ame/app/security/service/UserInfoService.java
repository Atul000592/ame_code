package nic.ame.app.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nic.ame.app.master.model.Login;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.repository.UserRoleRepo;


@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private UserRoleRepo userRoleRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Login> user = loginRepository.findByUserName(username);
		List<UserRole> roleList = userRoleRepo.findByUserName(username);
        
		
		Login login = new Login();
		login = user.get();

		login.setUserRoles(roleList);

		UserInfoServiceMapping infoServiceMapping = new UserInfoServiceMapping(login);
		return infoServiceMapping;
	}
	
	
}
