package nic.ame.app.dealinghand.controller;

import java.util.Date;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
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
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_1_impl;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_2_impl;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.PhysicalMeasurmentService;

@Controller
public class AmeDealingHandDisplayContoller {
	
	Logger logger=LogManager.getLogger(AmeDealingHandDisplayContoller.class);

	@Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;
	
	@Autowired
	private AmeAssessmentServicePart_1_impl ameAssessmentServicePart_1_impl;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;
	
	@Autowired
	private PhysicalMeasurmentService physicalMeasurmentService;
	
	@Autowired
	private AgeCalculatorService ageCalculatorService;
	@Autowired
	ForcePersonnelService forcePersonnelService;
	

	
	@Autowired
    private AmeAssessmentServicePart_2_impl assessmentServicePart_2_impl;
	
	//==========================display Report AME============================================//
	
		@RequestMapping(path = "/dealing-hand-display-report" ,method = RequestMethod.POST)
		public String DisplayAmeReport(@RequestParam("ameId") String ameId,Model model,
				@RequestParam("forcepersonalId") String forcePersonalId,HttpSession httpSession) {
			
			MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
			

	    	 String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				
			examDtoRequest=ameAssessmentDisplayService.getMedExamDtoRequestData(ameId);
			
			Optional<ForcePersonnelDto> forcePersonal=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);
		   
			model.addAttribute("examDtoRequest", examDtoRequest);
		    model.addAttribute("forcePersonal", forcePersonal.get());
			
			return"dealinghand/display-final-filled-application-dh";
		}
		
		@RequestMapping(path="/appendages-view-dh",method = RequestMethod.POST)
		public String appendagesViewDh(@RequestParam("ameId") String ameId,Model model,
				@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
				HttpServletRequest servletRequest) {
			
			HttpSession httpSession=servletRequest.getSession(false);
			if(httpSession==null) {
                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}

	    	 String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
			logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
			
			Appendages appendages=new Appendages();
			Optional<Appendages> optional=assesmentPart1Service.getAppendages(ameId);
			if(!optional.isEmpty()) {
				appendages=optional.get();
				model.addAttribute("appendages",appendages);
			}else {
				model.addAttribute("appendages",appendages);
			}
			
			//============================ SideBar and NavBar info===============================//
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
	   		
			model.addAttribute("ameId", ameId);
			model.addAttribute("forcepersonalId", candidateForcePersonalId);
			
			return"dealinghand/view/appendages-view-dh";
		}
		
		
		
		@RequestMapping(path = "/cns-reflexes-sensory-system--view-dh",method = RequestMethod.POST)
        public String cnsReflexesSensorySystemViewDh(@RequestParam("ameId") String ameId,Model model,
				@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
				HttpServletRequest servletRequest) {
			
			HttpSession httpSession=servletRequest.getSession(false);
			if(httpSession==null) {
                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}

	    	 String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
			logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
			
			Optional<CentralNervousSystem> centralNervousSystemOptional=assessmentServicePart_2_impl.centralNervousSystemByAmeId(ameId);
			Optional<CranialNervesMeningealSign> cranialNervesMeningealSignOptional=assessmentServicePart_2_impl.cranialNervesMeningealSignByAmeId(ameId);
			Optional<SensorySystem> sensorySystemOptional=assessmentServicePart_2_impl.sensorySystemByAmeId(ameId);
			Optional<Reflexes> reflexesOptional=assessmentServicePart_2_impl.reflexesByAmeId(ameId);
			MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
			CentralNervousSystem centralNervousSystem=new CentralNervousSystem();
			CranialNervesMeningealSign cranialNervesMeningealSign=new CranialNervesMeningealSign();
			Reflexes reflexes=new Reflexes();
			SensorySystem sensorySystem=new SensorySystem();
			
			
			if (!reflexesOptional.isEmpty()) {
				reflexes = reflexesOptional.get();
				examDtoRequest.setReflexes(reflexes);
			} else {
				examDtoRequest.setReflexes(reflexes);
			}
			if (!sensorySystemOptional.isEmpty()) {
				sensorySystem = sensorySystemOptional.get();
				examDtoRequest.setSensorysystem(sensorySystem);

			} else {
				examDtoRequest.setSensorysystem(sensorySystem);
			}
			if (!cranialNervesMeningealSignOptional.isEmpty()) {
				cranialNervesMeningealSign = cranialNervesMeningealSignOptional.get();
				examDtoRequest.setCranialNervesMeningealSign(cranialNervesMeningealSign);
			} else {
				examDtoRequest.setCranialNervesMeningealSign(cranialNervesMeningealSign);

			}
			if (!centralNervousSystemOptional.isEmpty()) {
				centralNervousSystem = centralNervousSystemOptional.get();
				examDtoRequest.setCns(centralNervousSystem);

			} else {
				examDtoRequest.setCns(centralNervousSystem);
			}
			
			
			
        	  model.addAttribute("examDtoRequest",examDtoRequest);
		
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
	   		
			model.addAttribute("ameId", ameId);
			model.addAttribute("forcepersonalId", candidateForcePersonalId);
			
			return"dealinghand/view/cns-reflexes-sensory-system--view-dh";
		}
		
		@RequestMapping(path = "/physical-measurement-index-page-view-dh",method = RequestMethod.POST)
		public String physicalMeasurementIndexPageViewDh(@RequestParam("ameId") String ameId,Model model,
				@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
				HttpServletRequest servletRequest) {
			HttpSession httpSession=servletRequest.getSession(false);
			if(httpSession==null) {
                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}

	    	 String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				
		          PhysicalMeasurement physicalMeasurement=physicalMeasurmentService.getPhysicalMeasurementData(ameId);
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
			logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
			ForcePersonnelDto forcePersonalCandidate=loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
			Date dob=forcePersonalCandidate.getDob();
			Date doj=forcePersonalCandidate.getJoiningDate();
			int age=ageCalculatorService.getAge(dob);
			String dateOfJoining=ageCalculatorService.calculateYearsMothsandDays(doj);
			System.out.println("DateofJoining>>>>>>>>>>>>>>>>>>>>>>>>>>>"+doj);
			model.addAttribute("physicalMeasurement",physicalMeasurement);
			model.addAttribute("loginUserDetails",loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
			model.addAttribute("age",age);
			model.addAttribute("doj", dateOfJoining);
	   		model.addAttribute("candidateDetails", forcePersonalCandidate);
	   		PlaceAndDateDto placeAndDateDto=assesmentPart1Service.getAmeDeclarationIndividualModel(ameId);
		    model.addAttribute("placeAndDateDto",placeAndDateDto);
	   		
			model.addAttribute("ameId", ameId);
			model.addAttribute("forcepersonalId", candidateForcePersonalId);
			
			return"dealinghand/view/physical-measurement-index-page-view-dh";
		}
		
	
		
		
		
		
		
		
		
		
		
		
		//=========================== General-Examination-view-dh==================================//
		
		
		
		//=================abdomen-save-View-dh============================//
		@RequestMapping(path = "/abdomen-save-View-dh",method = RequestMethod.POST)
        public String abdomenSaveViewDh(@RequestParam("ameId") String ameId,Model model,
				@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
				HttpServletRequest servletRequest) {

			HttpSession httpSession=servletRequest.getSession(false);
			if(httpSession==null) {
                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
			

	    	 String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				
			MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
			Optional<Abdomen> abdomenOptional = assessmentServicePart_2_impl.abdomenByAmeId(ameId);
			Optional<RespiratorySystem> reOptional=assessmentServicePart_2_impl.respiratorySystemByAmeId(ameId);
			Abdomen abdomen=new Abdomen();
			RespiratorySystem respiratorySystem=new RespiratorySystem();
			abdomen=abdomenOptional.get();
			respiratorySystem=reOptional.get();
			
			    String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
				logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
				model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
		   		examDtoRequest.setAbdomen(abdomen);
				examDtoRequest.setRespiratorySystem(respiratorySystem);
				model.addAttribute("examDtoRequest", examDtoRequest);
		   		
		   		model.addAttribute("ameId", ameId);
				model.addAttribute("forcepersonalId", candidateForcePersonalId);
			  
			
			return"dealinghand/view/abdomen-save-View-dh";
		}
		
		@RequestMapping(path = "/assessment-gynecology-Factor-view-dh",method = RequestMethod.POST)
		public String assessmentGynaeAndObsViewDh(@RequestParam("ameId") String ameId,Model model,
				@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
				HttpServletRequest servletRequest) {
			HttpSession httpSession=servletRequest.getSession(false);
			if(httpSession==null) {
                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
			

	    	 String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				
			
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
			logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
	   	    Optional<GynaeAndObsFemale> optional=assessmentServicePart_2_impl.gynaeByAmeId(ameId);
	     	
	     	GynaeAndObsFemale gynaeAndObsFemaledata=new GynaeAndObsFemale();
  	        if(optional.isPresent()) {
  		   
  	        	gynaeAndObsFemaledata=optional.get();
  		   model.addAttribute("gynaeAndObsFemaledata", gynaeAndObsFemaledata);
           }
  	       else {
  		     model.addAttribute("gynaeAndObsFemaledata", gynaeAndObsFemaledata);
  		     }
     	 
    	  
	   		
			model.addAttribute("ameId", ameId);
			model.addAttribute("forcepersonalId", candidateForcePersonalId);
			
			return "dealinghand/view/gynecology-obs-female-view-dh";
			
		}
		
		
}
