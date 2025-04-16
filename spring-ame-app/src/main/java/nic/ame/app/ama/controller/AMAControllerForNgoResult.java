package nic.ame.app.ama.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.constant.CommonConstant;


@Controller
@RequestMapping("/NGO/Result")
public class AMAControllerForNgoResult {
	
	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;
	
	@Autowired
	private MedicalBoardIndividualMappingRepo boardIndividualMappingRepo;
	
	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	
	Logger logger=LoggerFactory.getLogger(AMAControllerForNgoResult.class);
	
	
	@GetMapping("/result-of-all-ngo")
	public String resultOfAllNgo(org.springframework.ui.Model model,HttpSession httpSession) {
		
	 String loginForcePersonnelId=(String) httpSession.getAttribute("forcepersonalId");
	List<MedicalBoardMember> BoardListToMember=medicalBoardMemberRepo.findByForcePersonalIdAndStatusCode(loginForcePersonnelId,CommonConstant.ACTIVE_FLAG_YES);
	List<ForcePersonnelDto> listOfMappedForcePersonnelToBoard=new ArrayList<>();
	
	for (MedicalBoardMember medicalBoardMember : BoardListToMember) {
		
		 List<String> forcePersonnelIds=boardIndividualMappingRepo.ListOfForcePersonalForAmeAppointment(medicalBoardMember.getBoardId());
		 for (String fid : forcePersonnelIds) {
			 
			 Optional<ForcePersonnelDto> optional =forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(fid);
			 if(optional.isPresent()) {
				 listOfMappedForcePersonnelToBoard.add(optional.get());
			 }
		}
	}
	 
	
	model.addAttribute("ForcePersonnelList", listOfMappedForcePersonnelToBoard);
	model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonnelId));
		
		return "medical-sub-ordinate/NGO/result";
	}
	
	
	
	
	

}
