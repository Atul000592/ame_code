package nic.ame.app.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.Login;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.LoginRepository;

@RestController
public class TestController {

	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private BCryptPasswordEncoder   bCryptPasswordEncoder;
	
	@RequestMapping(path = "/save-force-personal", method = RequestMethod.POST)
	public ResponseEntity<?> saveForcePersonalDetails(@RequestBody ForcePersonnel forcePersonal){
		
		
		forcePersonalRepository.save(forcePersonal);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(forcePersonal);
	}
	
	
	@RequestMapping(path = "/save-login",method = RequestMethod.POST)
	public ResponseEntity<?> saveLoginDetails(@RequestBody Login login){
		
		login.setPassword(bCryptPasswordEncoder.encode(login.getPassword()));
		loginRepository.save(login);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(login);
	}
	
	
	
	
}
