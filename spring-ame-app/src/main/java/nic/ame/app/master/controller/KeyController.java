package nic.ame.app.master.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@Controller
public class KeyController {

	
	
	   @GetMapping("/getKey")
	    public ResponseEntity<?> getKey() throws Exception {
		   KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		    keyGen.init(256); // for AES-256
		    SecretKey secretKey = keyGen.generateKey();
		    String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		    System.out.println("Generated Key: " + base64Key); // Debugging log
	     
	        return ResponseEntity.status(HttpStatus.OK).body(base64Key);
	    }
	
	
	@GetMapping("/test-page")
	public String testPage() {
		System.out.println("DAWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		return"UserMenu/test-submit";
	}
}
