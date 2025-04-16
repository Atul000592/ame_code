package nic.ame.app.board.member.controller;



import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.Investigation;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_2_impl;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.constant.CommonConstant;


@Controller
@EnableTransactionManagement
public class ExamAssesssmentFunctionalController {
	
	private Logger logger=LogManager.getLogger(BoardMemberController.class);
	
	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;
	
	@Autowired
	private AmeAssessmentServicePart_2_impl ameAssessmentServicePart_2_impl;
	
	@Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;
	
	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	
	
	
	

	/*----------------------------phychological-assessment-save-update---------------------------------------------------------- */	
	
	
	
	
	/*----------------------------assesment-general-examination-one---------------------------------------------------------- */	
	/*----------------------------save-assessment-cns-cnm-reflexes--------------------------------------------------------- */	
	

	/*----------------------------assessment-physical-abdomen--and-Respiratory-save-update---------------------------------------------------------- */	
	
	// Investigation data save and update Action mapping
		@RequestMapping(path = "/assessment-investigation-save-update",method = RequestMethod.POST)
		public String saveAssessmentInvestigation(RedirectAttributes redirectAttributes ,@ModelAttribute Investigation investigation,
				@RequestParam("ameId")String ameId,@RequestParam("forcepersonalId")String forcepersonalId) {
			//EyeFactor eyefactor=new EyeFactor();
		     String mssge =null;
			Optional<Investigation> optional=ameAssessmentServicePart_2_impl.getInvestigationByAmeId(ameId);  
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
			 return "redirect:/form-index-page";
		}
	
	
	
	// Hearing data save and update action mapping
	// Appendages data save and update mapping
	
	
	// Eye factor data save and update Action mapping
	//=========================================add-test-to-user===========================================//
	
	@RequestMapping(path = "/add-test-to-user",method = RequestMethod.POST)
	public ResponseEntity<?> addTestToUser(@RequestBody Map<String, String>[] data,Model model) {
		System.out.println(data.length);
	
		
		for (Map<String, String> map : data) {
			CheckUpList checkUpList=new CheckUpList();
			String ameId=map.get("ameId");
			checkUpList.setAmeId(map.get("ameId"));
			checkUpList.setTestName(map.get("testName"));
			
		       boolean result= ameAssessmentDisplayService.saveCheckUpList(checkUpList);
		       
		       if(result) {
		    	AmeApplicationFlowStatus ameApplicationFlowStatus=ameApplicationFlowStatusService.getApplicationFlowDataByAmeId(ameId);
				ameApplicationFlowStatus.setCheckUpListFlag(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG)));
				Boolean res=ameApplicationFlowStatusService.updateApplicationFlowStatus(ameApplicationFlowStatus);
		       }
		       
		}
             return ResponseEntity.status(HttpStatus.OK).body(data);
	}
	
	//======================================================save-gynecology-Factor====================================//
	
	
	

}
