package nic.ame.app.ama.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.repository.MedicalBoardRepo;

import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.ama.service.SubordinateService_3;
import nic.ame.app.board.member.repository.CheckUpListRepo;
import nic.ame.app.master.dto.AmeDetailsDto;
import nic.ame.app.master.dto.AmeFinalDetailDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmeMasterStatus;
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
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.service.AmeFinalDetailService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_1_impl;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_2_impl;


import nic.ame.app.master.model.ForcePersonnel;


import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.AmeDetailsService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.app.master.service.PhysicalMeasurmentService;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class AmaViewController {
	

	

    @Autowired
	private MapUriToUserService mapUriToUserService;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;


	
	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;
    @Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;
	
	@Autowired
	private AmeAssessmentServicePart_1_impl ameAssessmentServicePart_1_impl;
	

	@Autowired
	private LoginUserDetails loginUserDetails;
	

	
	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;
	



	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;
	
	@Autowired
	private PhysicalMeasurmentService physicalMeasurmentService;
	
	@Autowired
	private AgeCalculatorService ageCalculatorService;

	
	@Autowired
    private AmeAssessmentServicePart_2_impl assessmentServicePart_2_impl;
	

	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired
	private AmeMasterStatusService ameMasterStatusService; 
	
	@Autowired
	private InvestigationMasterService investigationMasterService;
	
	@Autowired
	private AmeFinalDetailService ameFinalDetailService;
	

	Logger logger=LogManager.getLogger(AmaViewController.class);

	@SuppressWarnings("unused")
	private ForcePersonnel forcePersonal;
	
	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private AmeDetailsService ameDetailsService;
	
	@Autowired
	private CheckUpListRepo checkUpListRepo;
	

	
		
		
	//==========================display Report AME============================================//
		
		@RequestMapping(path = "/medical-subordinate-member-display-report-ma" ,method = RequestMethod.POST)
		public String DisplayAmeReport(@RequestParam("ameId") String ameId,Model model,
				@RequestParam("forcepersonalId") String forcePersonalId,HttpSession httpSession) {
			boolean goToFlag=true;
			String loginForcePersonalId=(String) httpSession.getAttribute("forcepersonalId");

			ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails
					.getCandicateForcePersonalId(forcePersonalId);
			Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);

			if (ameDetailsDtoOptional.isPresent()) {
				model.addAttribute("forcePersonal", ameDetailsDtoOptional.get());
			}

			else {
				model.addAttribute("forcePersonal", ameCandidateForcePersonnelDto);
			}
			MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			ForcePersonnelDto loginUserDetailsData=loginUserDetails.getLoginUserDetails(loginForcePersonalId);
			AmeMasterStatus ameMasterStatus=ameMasterStatusService.getAmeMasterStatus(ameId);
			Optional<ForcePersonnelDto> forcePersonal=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);
			examDtoRequest=ameAssessmentDisplayService.getMedExamDtoRequestData(ameId);
			Map<String, Map<String,String>>  investigationReportDtosList=investigationMasterService.findInvestigationReportByAmeId(ameId);
			
			
			investigationMasterService.findAllInvestigationReportByAmeId(ameId);
			InvestigationFinalReportDto investigationFinalReportDto= investigationMasterService.findAllInvestigationReportByAmeId(ameId); 
			
			
			model.addAttribute("investigationReportDtosList",investigationReportDtosList);
			
			model.addAttribute("ameId",ameId);
			
            model.addAttribute("examDtoRequest", examDtoRequest);
            
		    model.addAttribute("forcePersonal", forcePersonal.get());
		    
		    model.addAttribute("forcepersonalId",forcePersonalId);
		    
		  
		    
		    model.addAttribute("ameMasterStatus", ameMasterStatus);
		    
		    model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		    
            model.addAttribute("loginUserDetails", loginUserDetailsData);
            
			model.addAttribute("goToFlag", goToFlag);
			
			
			
			model.addAttribute("investigationFinalReportDto", investigationFinalReportDto);
			
			return"medical-sub-ordinate/display-final-filled-application-ma";
		}
		
		
		
		
		@RequestMapping(path = "display-ame-report" ,method = RequestMethod.POST)
		public String DisplayFinalAmeReport(@RequestParam("ameId") String ameId,Model model,@RequestParam("forcepersonalId") String forcePersonalId,HttpSession httpSession) {
			boolean goToFlag=false;
			MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			ForcePersonnelDto loginUserDetailsData=loginUserDetails.getLoginUserDetails(forcePersonalId);
			AmeMasterStatus ameMasterStatus=ameMasterStatusService.getAmeMasterStatus(ameId);
	//		ForcePersonnelDto forcePersonnelDto=ameAssessmentServicePart_1_impl.getforceForcePersonal(forcePersonalId);
			Optional<ForcePersonnelDto> forcePersonnelDto=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);
			
			examDtoRequest=ameAssessmentDisplayService.getMedExamDtoRequestData(ameId);
			Map<String, Map<String,String>>  investigationReportDtosList=investigationMasterService.findInvestigationReportByAmeId(ameId);
			model.addAttribute("investigationReportDtosList",investigationReportDtosList);
			model.addAttribute("ameId",ameId);
            model.addAttribute("examDtoRequest", examDtoRequest);
		    model.addAttribute("forcePersonal", forcePersonnelDto.get());
		    model.addAttribute("forcepersonalId",forcePersonalId);
		    model.addAttribute("ameId", ameId);
		    model.addAttribute("ameMasterStatus", ameMasterStatus);
		    model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
            model.addAttribute("loginUserDetails", loginUserDetailsData);
			model.addAttribute("goToFlag", goToFlag);
			
			investigationMasterService.findAllInvestigationReportByAmeId(ameId);
			InvestigationFinalReportDto investigationFinalReportDto= investigationMasterService.findAllInvestigationReportByAmeId(ameId);
			
			model.addAttribute("investigationFinalReportDto", investigationFinalReportDto);
			
			return"medical-sub-ordinate/display-ame-report";
		}
		
		
		
		
		
		
	
		
		
		@RequestMapping(value= {"/medical-sub-ordinate-dashboard-ma","/home-page"},method = RequestMethod.GET)
		public String goToMedicalAttendentDashboard(HttpSession httpSession,Model model) {
			String roleName="AMA"; 
			
			String boardId = (String) httpSession.getAttribute("boardId");
		    
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
		    
	        model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	        
	        Integer pendingCount= amaDeclarationCountService.getAMADeclarationPendingListCount(boardId);
	        
			String pendingCountValue;
			
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			
			model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			 
			
			String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
	    	
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
	    	
	 		MedicalBoard boardOptional= medicalBoardRepo.findByBoardId(boardId);
	 		
	 		MedicalBoardDetailDto  boardDetailDto=new MedicalBoardDetailDto();
	 		if(boardOptional.getBoardId()!=null) {
	 			boardDetailDto=medicalBoardMemberService.findBoardDetailsByBoardId(boardId);
	 			model.addAttribute("boardDetails",boardDetailDto );
	 		}else {
	 		
	 			model.addAttribute("boardDetails", boardDetailDto);
	 		}
			if(pendingCount==null) {
				pendingCountValue="0";
			}else {
				pendingCountValue=String.valueOf(pendingCount);
			}
		
			Integer appointmentCompleted=amaDeclarationCountService.findDataForAMAAppointmentCount(boardId);
		   String appointmentCompletedValue;
			if(appointmentCompleted==null) {
				appointmentCompletedValue="0";
			}else {
				appointmentCompletedValue=String.valueOf(appointmentCompleted);
			}
			
			String totalCompletedAma="0";
			Optional<ForcePersonnel> optional= forcePersonnelRepository.getByForcePersonnelId(forcePersonalId);
			int forceNo=optional.get().getForceNo();
			String unit=optional.get().getUnit();
			Integer totalCompletedCount=amaDeclarationCountService.findTotalDeclarationUnderProcessByBoardId(boardId);
			if(totalCompletedCount!=null) {
				totalCompletedAma=String.valueOf(totalCompletedCount);
			}
			
	   		model.addAttribute("pendingcountama", pendingCountValue);
			model.addAttribute("appointmentcompletedama",appointmentCompletedValue);
			model.addAttribute("totalCompletedAma", totalCompletedAma);
            
			return "medical-sub-ordinate/dashboard-ma";
			
		}
		
		//===================================================================View Controller Ama============================================================//
		

		
	
			@RequestMapping(path="appendages-view")
			public String appendagesViewDh(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				
				 ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				 Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);
				        
				 
					if (ameDetailsDtoOptional.isPresent()) {
						model.addAttribute("candidateDetails", ameDetailsDtoOptional.get());
					}

					else {
						model.addAttribute("candidateDetails", ameCandidateForcePersonnelDto);
					}
						  
				
				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				} String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				
				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
				String uri = mapUriToUserService.getUriForAppendagesViewForm(rCode);
				
				
				
				
				
				model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				
				String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
				logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
				String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
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
		   	//	model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
		   		
				model.addAttribute("ameId", ameId);
				model.addAttribute("forcepersonalId", candidateForcePersonalId);
				
				return uri;
			}
			
			
			
			@RequestMapping(path = "/cns-reflexes-sensory-system--view")
	        public String cnsReflexesSensorySystemViewDh(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				 Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);
				        
				 
				 if(ameDetailsDtoOptional.isPresent()) { 
							  model.addAttribute("candidateDetails",ameDetailsDtoOptional.get()); }
						  
						  else { model.addAttribute("candidateDetails", ameCandidateForcePersonnelDto);
						  }
				
				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				} String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				
				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
				String uri = mapUriToUserService.getUriForCNSReflexesViewForm(rCode);
				
				
				
				
				
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				
				String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
				logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
				String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
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
				
				return uri;
			}
			
			
			
			
			
			
			
			
			
			
			@RequestMapping(path = "/physical-measurement-index-page-view-ama")
			public String physicalMeasurementIndexPageViewDh(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);
				 
				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				
				
				
				
				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
				String uri = mapUriToUserService.getUriForPhysicalMeasurementViewForm(rCode);
				
				        
				 
				if (ameDetailsDtoOptional.isPresent()) {
					model.addAttribute("candidateDetails", ameDetailsDtoOptional.get());
				}

				else {
					model.addAttribute("candidateDetails", ameCandidateForcePersonnelDto);
				}
						  
				
				
				
				
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				PhysicalMeasurement physicalMeasurement=physicalMeasurmentService.getPhysicalMeasurementData(ameId);
				String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				ForcePersonnelDto forcePersonalCandidate=loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 Date dob=forcePersonalCandidate.getDob();
				int age=ageCalculatorService.getAge(dob);
				Date doj=forcePersonalCandidate.getJoiningDate();
				String dateOfJoining=ageCalculatorService.calculateYearsMothsandDays(doj);
				PlaceAndDateDto placeAndDateDto=assesmentPart1Service.getAmeDeclarationIndividualModel(ameId);
			    
				logger.info(">>>>>>>>>>>>>>>>>>>>>> age>>>>>>>>>"+age);
				logger.info(">>>>>>>>>>>>>>>>>>>>>> date of joining >>>>>>>>>"+dateOfJoining);
				
			
				logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
				logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
				
				model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				model.addAttribute("physicalMeasurement",physicalMeasurement);
				model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		   	  //model.addAttribute("candidateDetails",forcePersonalCandidate );
		   		model.addAttribute("age", age);
		   		model.addAttribute("doj", dateOfJoining);
		   		model.addAttribute("placeAndDateDto",placeAndDateDto);
		   		model.addAttribute("ameId", ameId);
				model.addAttribute("forcepersonalId", candidateForcePersonalId);
				
				return uri;
			}
			
			
			
			
			
			
			
			@RequestMapping(path = "/psychology-as-laid-down-view")
			public String phychologicalAsLaidDownViewDh(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				
				 ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				 Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);
				        
				 
				
						  
				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				
				 if(ameDetailsDtoOptional.isPresent()) { 
					  model.addAttribute("candidateDetails",ameDetailsDtoOptional.get()); }
				  
				  else { model.addAttribute("candidateDetails", ameCandidateForcePersonnelDto);
				  }
				
				

				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
				String uri = mapUriToUserService.getUriForPsychologyViewForm(rCode);
				
				
				String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				 String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				PsychologicalAssessmentAsLaidDown phychologicalAssessmentAsLaidDown=new PsychologicalAssessmentAsLaidDown();
		    	 Optional<PsychologicalAssessmentAsLaidDown> optional=assesmentPart1Service.assessmentAsLaidDown(ameId);
	             if(optional.isPresent()) {
	            	 phychologicalAssessmentAsLaidDown=optional.get();
	            	 model.addAttribute("phychologicalAssessmentAsLaidDown", phychologicalAssessmentAsLaidDown);
	 
	             }else {
	            	 model.addAttribute("phychologicalAssessmentAsLaidDown", phychologicalAssessmentAsLaidDown);
	             }
				
				String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
				logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
				model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		  // 	model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
		   		
				model.addAttribute("ameId", ameId);
				model.addAttribute("forcepersonalId", candidateForcePersonalId);
				
				return uri;
				
			}
			
			
			
			@RequestMapping(path = "/assessment-hearing-view")
			public String assessmentHearingViewDh(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) { 
				
				 ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				 Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);
				   
				 HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				
				 if(ameDetailsDtoOptional.isPresent()) { 
					  model.addAttribute("candidateDetails",ameDetailsDtoOptional.get()); }
				  
				  else { model.addAttribute("candidateDetails", ameCandidateForcePersonnelDto);
				  }

				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>Hearing ");
				String uri = mapUriToUserService.getUriForHearingViewForm(rCode);
				
				
				
				 String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				 String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
				logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
				model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		   	//	model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
		   		Hearing hearing=new Hearing();
		   		Optional<Hearing> optional=assesmentPart1Service.getHearing(ameId);
	     	 
	    	   if(optional.isPresent()) {
	    		   
	    		  hearing=optional.get();
	    		  model.addAttribute("hearing", hearing);
	    		 
	    		   
	    	   }else {
	    		  model.addAttribute("hearing", hearing);
	    		  
	    		 
	    	   }
		   		
				model.addAttribute("ameId", ameId);
				model.addAttribute("forcepersonalId", candidateForcePersonalId);
				
				return uri;
				
			}
			
			
			
			
			@RequestMapping(path = "/eye-factor-view")
			public String eyeFactorViewDh(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				
				 ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				 Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);
				        
				 
				 if(ameDetailsDtoOptional.isPresent()) { 
							  model.addAttribute("candidateDetails",ameDetailsDtoOptional.get()); }
						  
						  else { model.addAttribute("candidateDetails", ameCandidateForcePersonnelDto);
						  }
						  
				
				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				
				
				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>EYE FACTOR");
				String uri = mapUriToUserService.getUriForEyeFactotrViewForm(rCode);
				
				
				
				 String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
				logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
				model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		   	//	model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
		   	    Optional<EyeFactor> optional=assesmentPart1Service.getEyeFactor(ameId);
		     	EyeFactor eyeFactor=new EyeFactor();
		     	String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
	  	        if(optional.isPresent()) {
	  		   
	  		   eyeFactor=optional.get();
	  		   model.addAttribute("eyeFactor", eyeFactor);
	           }
	  	       else {
	  		     model.addAttribute("eyeFactor", eyeFactor);
	  		     }
	     	 
	    	  
		   		
				model.addAttribute("ameId", ameId);
				model.addAttribute("forcepersonalId", candidateForcePersonalId);
				
				return uri;
				
			}
			
			
			
			//=========================== General-Examination-view-ama==================================//
			
			@RequestMapping(path = "general-examination-view")
			public String generalExaminationView(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				
				ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				 Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);
				        
				 
				 if(ameDetailsDtoOptional.isPresent()) { 
							  model.addAttribute("candidateDetails",ameDetailsDtoOptional.get()); }
						  
						  else { model.addAttribute("candidateDetails", ameCandidateForcePersonnelDto);
						  }
				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				
				

				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>General Examination ");
				String uri = mapUriToUserService.getUriForGeneralExaminationViewForm(rCode);
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+uri+">>General Examination ");

				
				
				 String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				GeneralExamination generalExamination=new GeneralExamination();
				 Optional<GeneralExamination> optional=assessmentServicePart_2_impl.getGeneralExaminationByAmeId(ameId);
				 String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
	      	   if(optional.isPresent()) {
	      		   
	      		   generalExamination=optional.get();
	      		   model.addAttribute("generalExamination", generalExamination);
	      		   
	      	   }else {
	      		   model.addAttribute("generalExamination", generalExamination);
	      	   }
				
				String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
				logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
				logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
				model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		   	//	model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
		   		
		   		model.addAttribute("ameId", ameId);
				model.addAttribute("forcepersonalId", candidateForcePersonalId);
				
				return uri;
				
			}
			
			
			@RequestMapping(path = "/assessment-investigation-view-ama")
			public String assessmentInvestigationViewDh(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				

				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				
				String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				 String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				   // GeneralExamination generalExamination=new GeneralExamination();
				   // Optional<GeneralExamination> optional=assessmentServicePart_2_impl.getGeneralExaminationByAmeId(ameId);
				    Optional<Investigation> optional2=assessmentServicePart_2_impl.getInvestigationByAmeId(ameId);
			     	 // model.addAttribute("refAppendageslist", refAppendageslist);
			     	 Investigation investigation=new Investigation(); 
				    if(optional2.isPresent()) {
			     		   
			     		  investigation=optional2.get();
			     		   model.addAttribute("investigation", investigation);
			     		 
			     		   
			     	   }else {
			     		  model.addAttribute("investigation", investigation);
			     		 
			     	   }
				 
				    String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
					logger.info("ForcePersonalId>>>>>>>>>>>>>>>>>>"+forcePersonalId);
					logger.info("Candidate Force Personal Id>>>>>>>>>>>>>>>>>>"+candidateForcePersonalId);
					model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
			   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId));
			   		
			   		model.addAttribute("ameId", ameId);
					model.addAttribute("forcepersonalId", candidateForcePersonalId);
				
				return"medical-sub-ordinate/view/investigation-view-ama";
			}
			
			
			//=================abdomen-save-View-ama============================//
			@RequestMapping(path = "abdomen-respiratory-view")
	        public String abdomenSaveViewDh(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				
				ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(candidateForcePersonalId);
				Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);
				        
				 
					if (ameDetailsDtoOptional.isPresent()) {
						model.addAttribute("candidateDetails", ameDetailsDtoOptional.get());
					}

					else {
						model.addAttribute("candidateDetails", ameCandidateForcePersonnelDto);
					}

				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}

				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>General Examination ");
				String uri = mapUriToUserService.getUriForAbdominAndRespiratoryViewForm(rCode);
				
				
				
				String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
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
				  
				
				return uri;
			}
			
			// Gynecological view Data

			@RequestMapping(path = "assessment-gynecology-view")
			public String assessmentGynaeAndObsView(@RequestParam("ameId") String ameId,Model model,
					@RequestParam("candidateForcepersonalId") String candidateForcePersonalId,
					HttpServletRequest servletRequest) {
				HttpSession httpSession=servletRequest.getSession(false);
				if(httpSession==null) {
	                model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
				
				
				int rCode = (int) httpSession.getAttribute("rCodeMedical");
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>gynecology Examination ");
				String uri = mapUriToUserService.getUriForgynecologyViewForm(rCode);
				logger.info("RCode>>>>>>>>>>>>>>>>>>"+uri+">>gynecology view ");
				
				
				String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
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
				
				return uri;
				
			}
			
			
			
		@PostMapping(path = "final-ame-report-view")
		public String finalAmeReportView(@RequestParam("forcepersonalId") String fpId,@RequestParam("ameId") String ameId,Model model){
			
			logger.info(">>>>fpid "+fpId+">>>>>>>>>>ameId "+ameId);
		
			//=========================declaration form details====================//
			AmeFinalDetailDto ameFinalDetailDto =ameFinalDetailService.ameFinalDeclarationMap(fpId, ameId);
			
			
			 model.addAttribute("amePlace", ameFinalDetailDto.getAmePlace());
			 model.addAttribute("details",ameFinalDetailDto.getAmeDeclarationIndividualDetails());
			 model.addAttribute("individualModel",ameFinalDetailDto.getAmeDeclarationIndividualModel());
		     model.addAttribute("AmeId", ameId);
		     model.addAttribute("appointmentDate",ameFinalDetailDto.getDate());
			
			
			//===============================ame final report ================================//
			AmeFinalDetailDto ameFinalFilledReport =ameFinalDetailService.ameFinalFilledReportDisplay(fpId, ameId);
			
			
			
	        model.addAttribute("investigationReportDtosList",ameFinalFilledReport.getInvestigationFinalReportDto());
			
			model.addAttribute("ameId",ameId);
			
            model.addAttribute("examDtoRequest", ameFinalFilledReport.getExamDtoRequest());
            
		    model.addAttribute("forcePersonal", ameFinalFilledReport.getForcePersonnelDto());
		    
		    model.addAttribute("forcepersonalId",fpId);
		    
            model.addAttribute("ameMasterStatus", ameFinalFilledReport.getAmeMasterStatus());
			
            
            logger.info(ameFinalDetailDto+">>>>>>>>>>>>>>"+ameFinalFilledReport);
			
			return "medical-sub-ordinate/view/ame-final-report-view";
			
		}
			
		@PostMapping("view-investigation-reports-pdf")
		public  String viewInvestigationReportsPdf(
				@RequestParam("candidateForcePersonnelId") String candidateForcePersonnelId,
				@RequestParam("ameId") String ameId,
				
				HttpSession httpSession,Model model) {
			   
			List<CheckUpList> checkUpLists= checkUpListRepo.findByAmeId(ameId);
			String loginForcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			ForcePersonnelDto loginUserDetailsData=loginUserDetails.getLoginUserDetails(loginForcePersonalId);
			model.addAttribute("loginUserDetails", loginUserDetailsData);
			model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcePersonnelId));
			model.addAttribute("checkUpLists",checkUpLists);
			model.addAttribute("candidateForcePersonnelId", candidateForcePersonnelId);
			model.addAttribute("ameId", ameId);
			return "medical-sub-ordinate/view/investigation-reports/view-investigation-reports-pdf";
		}
		 
	
}
