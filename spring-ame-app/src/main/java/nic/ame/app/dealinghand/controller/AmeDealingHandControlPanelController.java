package nic.ame.app.dealinghand.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.ama.dto.DealingHandDto;
import nic.ame.app.ama.service.AmaDealingHandService;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.Investigation;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_2_impl;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.LoginUserDetails;

@Controller
public class AmeDealingHandControlPanelController {

	Logger logger=LogManager.getLogger(AmeDealingHandControlPanelController.class);

	@Autowired
	private ForcePersonnelRepository forcePersonelRepository;
	

	
	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
	
	@Autowired
	private AmeAssessmentServicePart_2_impl ameAssessmentServicePart_2_impl;
	
	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;
	
	
	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private AmaDealingHandService amaDealingHandService; 
	
	@Autowired
	private AmeMasterStatusService ameMasterStatusService;
	
	@Autowired
	ForcePersonnelRepository forcePersonnelRepository;
	
	@RequestMapping(path = "/show-declaration-to-controller-dh",method = RequestMethod.POST)
	public String showdeclarationtocontrollerdh(HttpSession httpSession,Model model,@RequestParam("boardId") String boardId) {
		String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String roleName=(String) httpSession.getAttribute("roleName");
		logger.info( ">>>>>>roleName>>>>>"+roleName+">>>>>>>>ForcePersonalId>>>>>>>>"+forcepersonalId);
		if (forcepersonalId == null) {

			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......Invalid request or session expired");
			return "bootstrap_medical_temp/index";
		}
		String candidateGazettedNonGazettedFlag = (String) httpSession
				.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
		
		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		 
		Optional<ForcePersonnel> optional = forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
		String unit = optional.get().getUnit();
		Integer forceNo=optional.get().getForceNo();

		logger.info("Current Unit:-"+unit);
		
		
		
		//List<AmeDeclarationIndividualModel> declarationIndividualDetails =ameDeclarationRepository.findDataForDealingHand(unit,forceNo);
		
	List<DealingHandDto>  ameApplicationFlowStatusDtos=amaDealingHandService.listOfDeclarationCompletePendingForApproval(boardId);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
		if (ameApplicationFlowStatusDtos.isEmpty()) {
			model.addAttribute("message", "HAVE NO RECORD TO DISPALY...! ");
			return"dealinghand/show-declaration-to-controller-dh";

		}
		model.addAttribute("declarationDetails", ameApplicationFlowStatusDtos);
		
		
		return"dealinghand/show-declaration-to-controller-dh";
	}
	
	//------------- Saving PhysicalMeasurement data into database------------------//
	
		@RequestMapping(path = "/physical-measurement-dh" ,method = RequestMethod.POST)
		public String savePhysicalMeasurement(@ModelAttribute PhysicalMeasurement physicalMeasurement, 
				@RequestParam("forcepersonalId") String candidateForcePersonalId,HttpSession httpSession
				,@RequestParam String ameId,Model model ,RedirectAttributes redirectAttributes) {
			
			
		//	String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
			
			String candidateGazettedNonGazettedFlag = (String) httpSession
					.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		
			Optional<PhysicalMeasurement>optional=ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(ameId);
			   if(!optional.isPresent()) {
				   ameAssessmentServicePart_2.savePhysicalMeasurement(physicalMeasurement,ameId);
				   logger.info(">>>>>>>>>>>>>>> Save First Time Physical Measurement");
			   }else {
				   ameAssessmentServicePart_2.updatePhysicalMeasurement(ameId, physicalMeasurement);
				   logger.info(">>>>>>>>>>>>>>>  Physical Measurement Updated Successfully");

			   } 
			

			
			model.addAttribute("forcepersonalId", candidateForcePersonalId);
			model.addAttribute("ameId", ameId);
			 
			
			
			redirectAttributes.addFlashAttribute("forcepersonalId", candidateForcePersonalId);
		    redirectAttributes.addFlashAttribute("ameId", ameId);
			
			
			return"redirect:/form-index-page-dh";
		}

		/*----------------------------assesment-general-examination-one-save---------------------------------------------------------- */	

		
		

		//=======================phychological-assessment-save-update-dh=======================//
		//==============================// Hearing data save and update action mapping==========================//
				/*----------------------------save-assessment-cns-cnm-reflexes-dh--------------------------------------------------------- */	
			     /*----------------------------assessment-physical-abdomen--and-Respiratory-save-update-dh---------------------------------------------------------- */	

	 	
		
		//----redirecting after saving Ame report data into database-----------//
		
		@RequestMapping(path = "/form-index-page-dh",method = RequestMethod.GET)
		public String redirectToFromIndexPage(Model model,HttpServletRequest request,HttpSession httpSession) {
			  String candidateForcepersonalId = null;
			  String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			  
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			  String ameId = null;
			
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		    if (inputFlashMap != null) {
		    	candidateForcepersonalId =  (String) inputFlashMap.get("forcepersonalId");
		    	ameId =(String)inputFlashMap.get("ameId");
		    } 
		       AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		       ameMasterStatus= ameMasterStatusService.getAmeMasterStatus(ameId);
		       model.addAttribute("ameMasterStatus", ameMasterStatus);
		    
			if (candidateForcepersonalId != null && ameId != null) {
				String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));

				model.addAttribute("candidateDetails",
						loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));

				model.addAttribute("forcepersonalId", candidateForcepersonalId);
				model.addAttribute("ameId", ameId);
				return "dealinghand/form-index-page-dh";
			} 
		    else {
		    	model.addAttribute("errorMsg", "You have been sign out // Session expired.....!");
				return "bootstrap_medical_temp/index";
				
			}
			
			
		}
	//----redirecting to  form-index-page-dh without saving-----------//
		
		@RequestMapping(path = "/back-to-index-page",method = RequestMethod.POST)
		public String backToFromIndexPage(Model model,HttpServletRequest request ,
				@RequestParam("forcepersonalId") String candidateforcepersonalId,
				@RequestParam("ameId") String ameId,HttpSession httpSession) {
			/*
			 * Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			 * if (inputFlashMap != null) { forcepersonalId = (String)
			 * inputFlashMap.get("forcepersonalId"); ameId
			 * =(String)inputFlashMap.get("ameId"); }
			 * 
			 */
			String candidateGazettedNonGazettedFlag = (String) httpSession
					.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			 
			   AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		       ameMasterStatus= ameMasterStatusService.getAmeMasterStatus(ameId);
		       model.addAttribute("ameMasterStatus", ameMasterStatus);
		       
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
			model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
			
		    if(candidateforcepersonalId!=null && ameId!=null) {
			model.addAttribute("forcepersonalId", candidateforcepersonalId);
			model.addAttribute("ameId", ameId);
			
			
			return"dealinghand/form-index-page-dh";
			} 
		    else {
		    	model.addAttribute("errorMsg", "You have been sign out // Session expired.....!");
				return "bootstrap_medical_temp/index";
				
			}
			
			
		}
		
		// Investigation data save and update Action mapping
				@RequestMapping(path = "/assessment-investigation-save-update-dh",method = RequestMethod.POST)
				public String saveAssessmentInvestigation(RedirectAttributes redirectAttributes ,@ModelAttribute Investigation investigation,
						@RequestParam("ameId")String ameId,@RequestParam("forcepersonalId")String forcepersonalId,Model model,HttpSession httpSession) {
					//EyeFactor eyefactor=new EyeFactor();
				     String mssge =null;
					Optional<Investigation> optional=ameAssessmentServicePart_2_impl.getInvestigationByAmeId(ameId);  
					
					String candidateGazettedNonGazettedFlag = (String) httpSession
							.getAttribute("candidateGazettedNonGazettedFlag");
					model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			
					String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
					 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
					
					if(!optional.isEmpty()) {
						
					boolean result=ameAssessmentServicePart_2_impl.updateInvestigation(optional.get().getAmeId(),investigation );
					
					mssge ="Data updated";
					 System.out.println("Result>>>>>>>>>>>>"+result);	
					
					}else {
						Boolean resBoolean=ameAssessmentServicePart_2_impl.saveInvestigation(investigation);
						 mssge="Data save Successfully";
					   System.out.println(resBoolean);
					}
					
				     
				      redirectAttributes.addFlashAttribute("forcepersonalId", forcepersonalId);
				      redirectAttributes.addFlashAttribute("ameId", ameId);
				      redirectAttributes.addFlashAttribute("mssge",mssge);
					 return "redirect:/form-index-page-dh";
				}
			
			
			
			
			
			// Appendages data save and update mapping
			
		
			
			// Eye factor data save and update Action mapping
					
			//======================================================save-gynecology-Factor-dh====================================//
			
			
			
		
		
		
		
		

	
}
