package nic.ame.app.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InsertForcePersonalData {
	
	
	
	
	@Autowired
	private SaveForcePersonalAndLoginService forcePersonalAndLoginService;
	
	
	
	@PostMapping("/insertForcePersonal")
	public ResponseEntity<?> insertForcePersonalData(@RequestBody ForcePersonalDto  forcePersonalDto){
		ForcePersonalDto dto = null;
		if(forcePersonalDto.getForcePersonal()==null && forcePersonalDto.getPassword()==null) {
			dto=forcePersonalAndLoginService.saveNewForcePersonalWithLoginDetails(forcePersonalDto);
			if(dto!=null) {
				return new ResponseEntity<>(HttpStatus.CREATED).ok(dto);
			}
			
			}
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(dto);	
	}
	

}
