package nic.ame.app.user.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nic.ame.app.board.member.repository.CheckUpListRepo;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.master.util.DateConvert;

@Controller
public class CheckListController {
	
	Logger logger=LoggerFactory.getLogger(CheckListController.class);
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private CheckUpListRepo checkUpListRepo;

	@PostMapping(path = {"get-candidate-check-up-list-by-ame-id"})
	public ResponseEntity<?> getCandidateCheckUpListByAmeId(@RequestParam("ameId") String ameId,
			@RequestParam("forcepersonalId") String forcepersonalId){
		 
		ForcePersonnelDto forcePersonal = loginUserDetails.getCandicateForcePersonalId(forcepersonalId);
		List<CheckUpList> checkUpLists= new ArrayList<>();
		checkUpLists=checkUpListRepo.findByAmeId(ameId);
		String issueDate = null;
         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        // Convert the Date to a string using the defined format
       
		if(checkUpLists.size()>0) {
			  issueDate = dateFormat.format(checkUpLists.get(0).getCreatedOn());
		}
		Map<String,Object> response=new HashMap<>();
        response.put("candidateDetails",forcePersonal);
        response.put("checkUpList",checkUpLists);
        response.put("issueDate",issueDate);
        response.put("ameId", ameId);
		logger.info("provide candidate checkUplist");
		return ResponseEntity.ok(response);
	}
	
	
}
