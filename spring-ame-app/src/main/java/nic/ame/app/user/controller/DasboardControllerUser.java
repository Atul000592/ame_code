package nic.ame.app.user.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.dto.AmeDeclarationIndividualDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.model.AmeDeclarationDtoResponse;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.user.dto.AmeFinalResultCandidate;
import nic.ame.app.user.service.UserService;

@Controller

public class DasboardControllerUser {
	
	private Logger logger=LoggerFactory.getLogger(DasboardControllerUser.class);


	@Autowired
	private AmeDeclarationRepository declarationRepository;
	
   @Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private UserService userService;
	
	
	@Autowired
	LoginUserDetails loginUserDetails;
	
	@Autowired
	private AgeCalculatorService ageCalculatorService;
	
	@Autowired
	ForcePersonnelService forcePersonnelService;


	
	
	@RequestMapping("/pending-request-user")
	public String  getPendingRequestPage( Model model,HttpServletRequest servletRequest) {
		
		HttpSession httpSession = servletRequest.getSession(false);
		if (httpSession == null) {
			model.addAttribute("errorMsg",
					"INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST SESSION EXPIRED......");
			return "bootstrap_medical_temp/index";
		}
		String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");

		logger.debug(">>>>>>>forcepersonalId>>>>>" + forcepersonalId);

		if (forcepersonalId == null) {

			return "bootstrap_medical_temp/error-page";
		} else {

			AmeDeclarationIndividualModel optional = declarationRepository.findByForcePersonalId(forcepersonalId);
			System.out.println(optional.getAmeId());

			Optional<ForcePersonnelDto> optional2 = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
			ForcePersonnelDto forcePersonal;

			if (optional2.isPresent()) {

				forcePersonal = optional2.get();

				AmeDeclarationDtoResponse dtoResponses = new AmeDeclarationDtoResponse();
				dtoResponses.setName(forcePersonal.getName());
				dtoResponses.setRank(forcePersonal.getRank());
				dtoResponses.setDesignation(forcePersonal.getDesignation());
				dtoResponses.setAmeId(optional.getAmeId());
				dtoResponses.setForcePersonalId(forcepersonalId);
				dtoResponses.setDeclarationdate(String.valueOf(optional.getDeclarationDate()));
				dtoResponses.setUnit(optional.getCurent_new_unit());

		 if(optional.getAmeId()!=null) {
			 model.addAttribute("dataList",dtoResponses);
		 }else {
			 model.addAttribute("message", "NO DATA FOUND BY THIS NAME");
		 }
			
		 }	
			return "UserMenu/pending-request-user";}
         }
	
		@GetMapping(path = "user-declaration-request")
		public String getAllUserDeclaration(Model model,HttpServletRequest servletRequest,HttpSession httpSession) {
		       HttpSession session=servletRequest.getSession(false);
		       if(session==null) {
		    	   model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST SESSION EXPIRED......");
				return "bootstrap_medical_temp/index";
		       }
		       
		       String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		       
			String forcePersonalId=(String) session.getAttribute("forcepersonalId");
			// Optional<ForcePersonal> forcePersonal=forcePersonalServiceImpl.findByForcePersonalId(forcePersonalId.trim()) ;
			 model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
			 List<AmeDeclarationIndividualDto> individualModelsList= userService.showUserAllDeclaration(forcePersonalId);
			 model.addAttribute("individualModelsList", individualModelsList);
			
			 List<UserRoleDto> roleDtosList	= (List<UserRoleDto>) session.getAttribute("roleDtosList");
			 model.addAttribute("roleDtosList", roleDtosList);
			 
			return "UserMenu/show-ame-declaration-user";
		}
	
		@GetMapping(path = "user-ame-result")
		public String userAmeResult(Model model, HttpSession httpSession) {
			List<UserRoleDto> roleDtosList	= (List<UserRoleDto>) httpSession.getAttribute("roleDtosList");

			model.addAttribute("roleDtosList", roleDtosList);

			if (httpSession == null) {
				model.addAttribute("errorMsg",
						"INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST SESSION EXPIRED......");
				logger.info("session...invalidated...........!");
				return "redirect:/login-page";
			}
			String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

			if (forcePersonalId == null) {
				model.addAttribute("errorMsg",
						"INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST SESSION EXPIRED......");
				logger.info("Invalid User Login..........!");
				return "redirect:/login-page";
			}
			 List<AmeFinalResultCandidate> ameFinalResultCandidates =new ArrayList<>(); 
			 if(userService.getAmeResultForCandidateForcePersonalId(forcePersonalId).size()>0) {
				 ameFinalResultCandidates = userService.getAmeResultForCandidateForcePersonalId(forcePersonalId); 
			 }
			 
			 model.addAttribute("ameFinalResultCandidates", ameFinalResultCandidates);
			
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

			return "UserMenu/user-ame-result";
		}

}

