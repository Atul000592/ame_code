package nic.ame.app.ama.controller;

import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.ApprovingForwardingAuthority;
import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.ama.service.SubordinateService_2;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.AmeAppointmentService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;

@Controller
public class AmaAmeDeclarationController {
	
	Logger logger = LogManager.getLogger(AmaAmeDeclarationController.class);
	
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;

	
	@Autowired
	private AmeAppointmentService ameAppointmentService;
	
	@Autowired
	private SubordinateService_2 subordinateService_2;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
	
	@Autowired
	private MedicalBoardIndividualMappingService medicalBoardIndividualMappingService;
	
	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	
	//==============================show-declaration-to-medical-subordinate=======================//
	
	@RequestMapping(path = "/show-declaration-to-controller-ma", method = RequestMethod.GET)
	public String showdeclarationtocontroller(Model model,HttpServletRequest httpServletRequest) {
		HttpSession httpSession=httpServletRequest.getSession();

		if(httpSession==null) {
			model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
	      	  return "bootstrap_medical_temp/index";
		}
		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		 
		 String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
    	 
    	 
		String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String roleName=(String) httpSession.getAttribute("roleName").toString().toLowerCase();
		
		logger.info( ">>>>>>roleName>>>>>"+roleName+">>>>>>>>ForcePersonalId>>>>>>>>"+forcepersonalId);
		Optional<ForcePersonnelDto> optional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
		String unit = optional.get().getUnit();

		logger.info(">>>>>>>" + unit);
		if (forcepersonalId == null) {
            httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));

		 List<AmeDeclarationIndividualModel> declarationIndividualDetails=subordinateService_2.getDeclarationListForAMANew(forcepersonalId, roleName);
		
		
		if (declarationIndividualDetails.isEmpty()) {
			model.addAttribute("message", "NO RECORD FOUND........! ");
			return "medical-sub-ordinate/show-declaration-to-controller-ma";
		}
		model.addAttribute("declarationDetails", declarationIndividualDetails);
		return "medical-sub-ordinate/show-declaration-to-controller-ma";
	}

	
	
		 
			
		
		
			//===================================display-total-application-completed=============================================//
			
			@RequestMapping(path = "/total-application-completed-ma" ,method = RequestMethod.GET)
			public String displayTotalDeclarationRequestsCompleted(Model model,HttpServletRequest httpServletRequest) {
               
				HttpSession httpSession=httpServletRequest.getSession(false);
				
				if(httpSession==null) {
					model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			      	  return "bootstrap_medical_temp/index";
				}
				String forcepersonalId=(String) httpSession.getAttribute("forcepersonalId");
				logger.info("controlling force personalId :"+forcepersonalId);
				Optional<ForcePersonnelDto> optional=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
				String unit= optional.get().getUnit();
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				 
				 String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
		    	 
		    	 
				logger.info(">>>>Display Controller page>>>"+unit);
				if(forcepersonalId==null) {
					
					model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
		      	  return "bootstrap_medical_temp/index";
				}
			            // List<AmeDeclarationIndividualDetails> declarationIndividualDetails=ameDeclarationIndividualDetailsRepo.findAllByFinalDeclaration();
				          List<AmeDeclarationIndividualModel> declarationIndividualDetails=ameDeclarationRepository.findByFinalDeclarationApproved(unit);
				         
				          if(declarationIndividualDetails.isEmpty()) {
		                     model.addAttribute("message","HAVE NO RECORD TO DISPLAY ");
			            	return"BoardMember/show-declaration-to-controller";
			            }
				     model.addAttribute("declarationDetails",declarationIndividualDetails);
				   

				return"medical-sub-ordinate/display-total-application-completed-ma";
			}
			
			
			@RequestMapping(path = "/force-personal-board-detail", method = RequestMethod.POST)
			public String getDetailsofAllForcePersonalByBoardId(String boardId, String forcePersonalId, Model model,
					HttpServletRequest httpServletRequest) {
				List<MedicalBoardIndividualMapping> boardIndividualMappings = medicalBoardIndividualMappingService
						.getAllForcePersonalByBoardId(boardId);
				HttpSession httpSession = httpServletRequest.getSession(false);

				
				
				if (httpSession == null) {
					model.addAttribute("errorMsg",
							"INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				
                httpSession.setAttribute("boardId", boardId);
				String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
				model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

				String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

				String forcepersonalIdSession = (String) httpSession.getAttribute("forcepersonalId");
				String roleName = (String) httpSession.getAttribute("roleName").toString().toLowerCase();

				logger.info(
						">>>>>>roleName>>>>>" + roleName + ">>>>>>>>ForcePersonalId>>>>>>>>" + forcepersonalIdSession);
				Optional<ForcePersonnelDto> optional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalIdSession);
                String unit = optional.get().getUnit();

				logger.info(">>>>>>>" + unit);
				if (forcepersonalIdSession == null) {
					httpSession.invalidate();
					model.addAttribute("errorMsg",
							"INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalIdSession));
				model.addAttribute("boardIndividualMappings", boardIndividualMappings);
				return "medical-sub-ordinate/board-personal-details";
		
			
}
}
