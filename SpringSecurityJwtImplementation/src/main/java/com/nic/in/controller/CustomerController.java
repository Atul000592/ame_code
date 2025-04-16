package com.nic.in.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nic.in.model.Customer;
import com.nic.in.repo.CustomerRepo;
import com.nic.in.service.JwtService;

@RestController
public class CustomerController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerRepo customerRepo;
	
	
	@Autowired
	private JwtService jwtService;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody Customer customer) {
		String encodePassword = passwordEncoder.encode(customer.getPwd());
		customer.setPwd(encodePassword);
		Customer savedCustomer = this.customerRepo.save(customer);
		return new ResponseEntity<>("Customer Register Successfully", HttpStatus.OK);

	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Customer customer) {
		
		UsernamePasswordAuthenticationToken tocken = new UsernamePasswordAuthenticationToken(customer.getEmail(), customer.getPwd());
		Authentication authentication= authenticationManager.authenticate(tocken);
		if(authentication.isAuthenticated()) {
		String jwt=	jwtService.generateToken(customer.getEmail());
			return new ResponseEntity<>(jwt,HttpStatus.OK);
		}
		return new ResponseEntity<>("Invalid Login",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/hello")
	public String hello() {
		System.out.println("Hello Controller");
		return "hello";
	}
}
