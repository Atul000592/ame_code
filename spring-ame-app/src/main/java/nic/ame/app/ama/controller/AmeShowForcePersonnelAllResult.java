package nic.ame.app.ama.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.user.dto.AmeFinalResultCandidate;
import nic.ame.app.user.service.UserService;

@Controller
public class AmeShowForcePersonnelAllResult {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private AgeCalculatorService ageCalculatorService;
	
	
	Logger logger =LoggerFactory.getLogger(AmeShowForcePersonnelAllResult.class);
	
	
	@PostMapping("view-all-ame-result-for-candidate")
	public ModelAndView showForcePersonnelAllresultToAma(@RequestParam("candidateForcepersonalId") String forcePersonalId,
			Model model,HttpSession httpSession) {
		

		
		if (forcePersonalId == null) {
			model.addAttribute("errorMsg",
					"INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST SESSION EXPIRED......");
			logger.info("Invalid User Login..........!");
			return new ModelAndView("redirect:/initial-login");
		}
		int rCodeMedical=(int)httpSession.getAttribute("rCodeMedical");
		 List<AmeFinalResultCandidate> ameFinalResultCandidates =new ArrayList<>(); 
		 String returnUrl=null;
		if(rCodeMedical==3||rCodeMedical==4||rCodeMedical==2) {
			List<AmeFinalResultCandidate> ameResultList=userService.getAmeResultForCandidateForcePersonalIdGo(forcePersonalId);
			 if(ameResultList.size()!=0) {
				 ameFinalResultCandidates = userService.getAmeResultForCandidateForcePersonalIdGo(forcePersonalId); 
			 }
			 model.addAttribute("ameFinalResultCandidates", ameFinalResultCandidates);
			 if(rCodeMedical==3||rCodeMedical==4)
				 returnUrl="medical-bm/view/report/all-previous-ame-results";
			 if(rCodeMedical==2)
				 returnUrl="medical-po/view/report/all-previous-ame-results";
				 
			
		}else {
			 if(userService.getAmeResultForCandidateForcePersonalId(forcePersonalId).size()>0) {
				 ameFinalResultCandidates = userService.getAmeResultForCandidateForcePersonalId(forcePersonalId); 
			 }
			 
			 model.addAttribute("ameFinalResultCandidates", ameFinalResultCandidates);
			 returnUrl="medical-sub-ordinate/NGO/candidate-ame-results";
		}
		
		
		
		 model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		 ForcePersonnelDto forcePersonal = loginUserDetails.getCandicateForcePersonalId(forcePersonalId);
		 model.addAttribute("candidateForcePersonalId", forcePersonalId);
		 
    
		model.addAttribute("candidateDetails", forcePersonal);
		Date dob = forcePersonal.getDob();
		int age = ageCalculatorService.getAge(dob);
		Date doj = forcePersonal.getJoiningDate();
		String dateOfJoining = ageCalculatorService.calculateYearsMothsandDays(doj);
		model.addAttribute("age", age);
		model.addAttribute("doj", dateOfJoining);
		
		return new ModelAndView(returnUrl);
		

	}

}
