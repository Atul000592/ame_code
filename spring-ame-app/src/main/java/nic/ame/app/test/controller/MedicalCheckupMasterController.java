package nic.ame.app.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nic.ame.app.board.member.repository.MedicalCheckUpMasterRepo;
import nic.ame.app.master.medical.model.MedicalCheckUpMaster;

@RestController
public class MedicalCheckupMasterController {

	@Autowired
	private MedicalCheckUpMasterRepo checkUpMasterRepo;
	
	@RequestMapping(path = "saveMedicalCheckupMaster",method = RequestMethod.POST)
	ResponseEntity<?> saveMedicalCheckupMaster(@RequestBody MedicalCheckUpMaster medicalCheckUpMaster){
		checkUpMasterRepo.save(medicalCheckUpMaster);
          return ResponseEntity.status(HttpStatus.CREATED).body(medicalCheckUpMaster);
	}
	
	
	
}
