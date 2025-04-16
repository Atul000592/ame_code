package nic.ame.app.test.controller;

import java.net.http.HttpRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class SaveVeryLargeObject {
	
    Logger logger =LoggerFactory.getLogger(SaveVeryLargeObject.class);
    
    @Autowired
    private MyObjectService myObjectService;
	
	@GetMapping("/save-very-large")
	public void savevery(HttpServletRequest httpRequest) {
		MyObject myObject=new MyObject(29, "Ajay", 343322, "IT");
		HttpSession httpSession=httpRequest.getSession();
		   // httpSession.getId();
		 try {
			myObjectService.saveObject((MyObject) httpSession);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@GetMapping("/get-very-large")
	public void getsavevery(HttpRequest httpRequest) {
		 try {
			 long id=2;
			HttpSession myObject= (HttpSession) myObjectService.getObject(id);
		logger.info(">>>>>>"+myObject.getId()+"logion forcepersonnnelId "+myObject.getAttribute("forcepersonalId"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
