package nic.ame.app.board.member.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.MedicalCheckUpMaster;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_1_impl;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;

@Controller
public class ExamAssessmentDisplayController {
	
	private Logger logger=LogManager.getLogger(BoardMemberController.class);

	@Autowired
	private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
	
	
	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
	
	
    @Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;
	
	@Autowired
	private AmeAssessmentServicePart_1_impl ameAssessmentServicePart_1_impl;
	

	
	
	//==========================display Report AME============================================//
	
	@RequestMapping(path = "/board-member-display-report" ,method = RequestMethod.POST)
	public String DisplayAmeReport(@RequestParam("ameId") String ameId,Model model,@RequestParam("forcepersonalId") String forcePersonalId) {
		
		MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
		
		examDtoRequest=ameAssessmentDisplayService.getMedExamDtoRequestData(ameId);
		
		ForcePersonnel forcePersonal=ameAssessmentServicePart_1_impl.getforceForcePersonal(forcePersonalId);
	   
		model.addAttribute("examDtoRequest", examDtoRequest);
	    model.addAttribute("forcePersonal", forcePersonal);
		
		return"BoardMember/display-final-filled-application";
	}
	
	
	
	
	
	//======================================display-application-under-process=====================================================//
	
	@RequestMapping(path = "/application-under-process" ,method = RequestMethod.GET)
	public String displayDeclarationRequestsUnderProcess(Model model,HttpSession httpSession) {
		
		//---------getting forcepersonalId from session----------------//
		String forcepersonalId=(String) httpSession.getAttribute("forcepersonalId");
		
		System.out.println("display application under process  force personalId :"+forcepersonalId);
		
		//---------getting force personal details from session----------------//
		
		  Optional<ForcePersonnel>
		  optional=forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
		  String unit= optional.get().getUnit();
		 
		
		if(forcepersonalId==null) {
			logger.info("invalid user or No valid user present");
			model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
      	  return "bootstrap_medical_temp/index";
		}
		boolean result=ameAssessmentServicePart_2.getDataExistStatus();
		
		if(!result) {
			
			
			model.addAttribute("message", "NO Date exist to display ..........!");
			return"medical-sub-ordinate/display-application-under-process";
		}
		
		
	            // List<AmeDeclarationIndividualDetails> declarationIndividualDetails=ameDeclarationIndividualDetailsRepo.findAllByFinalDeclaration();
	            List<AmeDeclarationIndividualModel> declarationIndividualDetails=ameDeclarationRepository.findByFinalDeclarationApproved(unit);
    	        List<AmeDeclarationIndividualModel>  ameMasterStatusList=ameAssessmentDisplayService.displayApplicationUnderProcess();
		          
		          
		          if(declarationIndividualDetails.isEmpty()) {
                     model.addAttribute("message","HAVE NO RECORD TO DISPALY ");
	            	return"medical-sub-ordinate/show-declaration-to-controller";
	            }
		     model.addAttribute("declarationDetails",declarationIndividualDetails);
		     model.addAttribute("ameMasterStatusList",ameMasterStatusList);
		     
		   

		return"medical-sub-ordinate/display-application-under-process";
	}
	
	
	
//===================================display-total-application-completed=============================================//
	
	@RequestMapping(path = "/total-application-completed" ,method = RequestMethod.GET)
	public String displayTotalDeclarationRequestsCompleted(Model model,HttpSession httpSession) {
		
		String forcepersonalId=(String) httpSession.getAttribute("forcepersonalId");
		System.out.println("controlling force personalId :"+forcepersonalId);
		Optional<ForcePersonnel> optional=forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
		String unit= optional.get().getUnit();
		
		   logger.info(">>>>Display Controller page>>>"+unit);
		if(forcepersonalId==null) {
			
			model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
      	  return "bootstrap_medical_temp/index";
		}
	            // List<AmeDeclarationIndividualDetails> declarationIndividualDetails=ameDeclarationIndividualDetailsRepo.findAllByFinalDeclaration();
		          List<AmeDeclarationIndividualModel> declarationIndividualDetails=ameDeclarationRepository.findByFinalDeclarationApproved(unit);
		         
		          if(declarationIndividualDetails.isEmpty()) {
                     model.addAttribute("message","HAVE NO RECORD TO DISPALY ");
	            	return"BoardMember/show-declaration-to-controller";
	            }
		     model.addAttribute("declarationDetails",declarationIndividualDetails);
		   

		return"BoardMember/display-total-application-completed";
	}
	
	
	@RequestMapping(path = "display-filled-application")
	public String displayFilledApplication(String ameId,String forcepersonalId,Model model)
	
	
	
	{
		return "BoardMember/display-filled-application";
	}
	
	//=================================Medical CheckUp List Display page=======================================//
	
	
	@RequestMapping(path = "/medical-checkup-master",method = RequestMethod.GET)
	public String medicalCheckUpListDisplayPage(Model model,String ameId,String CandidateforcePersonalId) {
		List<MedicalCheckUpMaster> medicalMaster = ameAssessmentDisplayService.checkUpMasters();
		model.addAttribute("medicalMaster", medicalMaster);
		model.addAttribute("ameId",ameId);
		model.addAttribute("CandidateforcePersonalId",CandidateforcePersonalId);
		return "BoardMember/medical-check-up-master-page";
		
	}
	
}
