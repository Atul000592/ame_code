package nic.ame.app.test.controller;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.transaction.Transactional;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.Login;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.LoginRepository;

@Service
@EnableTransactionManagement
public class SaveForcePersonalAndLoginService {

	Logger logger=LoggerFactory.getLogger(SaveForcePersonalAndLoginService.class);
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Transactional
	public ForcePersonalDto saveNewForcePersonalWithLoginDetails(ForcePersonalDto forcePersonalDto) {

		ForcePersonnel forcePersonal = forcePersonalRepository.save(forcePersonalDto.getForcePersonal());
		
		logger.info("forcePersonal Saved....");
		
		if (forcePersonal != null) {
			
			Login login =new Login();
			//================encoding password=========================//
			login.setFirstLoginFlag("N");
			login.setForcePersonalId(forcePersonal.getForcePersonalId());
			login.setLocked(1);
			StringBuffer  buffer=new StringBuffer();
			buffer.append(forcePersonal.getForceNo()).append(forcePersonal.getForceId().trim());
			login.setUserName(buffer.toString());
			login.setUserStatus(1);
			logger.info("Login Password create......");
			String password=bCryptPasswordEncoder.encode(forcePersonalDto.getPassword());
			login.setPassword(password);
		   
			
			Login loginSave = loginRepository.save(login);
			
			if (loginSave != null) {
				return forcePersonalDto;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	
	
}
