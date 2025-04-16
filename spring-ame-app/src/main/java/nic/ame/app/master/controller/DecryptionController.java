package nic.ame.app.master.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nic.ame.master.util.AESUtil;

@Controller
public class DecryptionController {

	
	 @PostMapping("/submit")
	    public String submit(@RequestParam("encryptedPassword") String encryptedPassword,@RequestParam("keyId") String keyId) {
	        String key = keyId; // Replace with your actual key
	        try {
	        	System.out.println(">>>>>>>>>>>"+encryptedPassword+"key>>>>>>>>"+key);
	        	
	            String decryptedPassword = AESUtil.decrypt(encryptedPassword, key);
	            // Process the decrypted password as needed
	            return "Decrypted Password: " + decryptedPassword;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Decryption failed";
	        }
	    }
	 
	 
	 
}
