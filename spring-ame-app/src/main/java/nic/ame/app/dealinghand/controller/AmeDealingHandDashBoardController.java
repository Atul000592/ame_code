package nic.ame.app.dealinghand.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.ama.dto.DealingHandDto;
import nic.ame.app.ama.service.AmaDealingHandService;
import nic.ame.app.dealinghand.service.DealingHandService;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.EyeVisonScaleMaster;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.Investigation;
import nic.ame.app.master.medical.model.NearVisionScaleMaster;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.medical.service.AmeDropDownService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_2_impl;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.ref.entity.RefAppendagesType;
import nic.ame.app.master.ref.entity.CategoryTypeMaster;
import nic.ame.app.master.ref.entity.RefDropDownRange;
import nic.ame.app.master.ref.entity.RefEyeFactor;
import nic.ame.app.master.ref.entity.RefGFactor;

import nic.ame.app.master.ref.entity.RefPsychologicalDuration;
import nic.ame.app.master.ref.entity.RefPsychologicalStatus;
import nic.ame.app.master.ref.entity.RefPsychologicalType;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.constant.CommonConstant;

@Controller
public class AmeDealingHandDashBoardController {

	
	Logger logger=LogManager.getLogger(AmeDealingHandDashBoardController.class);
	
	@Autowired
    private AmeDropDownService ameDropDownService;	
	
	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
	
	@Autowired
	private AmeAssessmentServicePart_2_impl assessmentServicePart_2_impl;
	
	@Autowired
	private AmeAssesmentServicePart_1  assesmentPart1Service;
	
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;
	
	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
	
	@Autowired
	private AgeCalculatorService ageCalculatorService;
	
	@Autowired
	private AmeMasterStatusService ameMasterStatusService;
	
	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
	
	@Autowired
	private RankRepo rankRepo;
	
	@Autowired
	private DealingHandService dealingHandService;
	
	@Autowired
	private AmaDealingHandService amaDealingHandService;
	
	private final AmeParametersRepository ameParametersRepository;
	
	@Autowired
	ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	
	public AmeDealingHandDashBoardController(AmeParametersRepository ameParametersRepository) {
		super();
		this.ameParametersRepository = ameParametersRepository;
	}

	
	@RequestMapping("/fill-ame-medical-test")
	public String getToFormIndexPage(@RequestParam("forcepersonalId") String candidateforcepersonalId,@RequestParam("ameId") String ameId,Model model,HttpSession  httpSession ) {
		String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
		
		if(forcePersonalId==null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		
		
		ForcePersonnelDto candidateForcepersonalData=loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId);
		String candidateGazettedNonGazettedFlag= rankRepo.getGazettedNonGazettedFlag(candidateForcepersonalData.getRank().trim());
		
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
		httpSession.setAttribute("candidateGazettedNonGazettedFlag",candidateGazettedNonGazettedFlag);
		
		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
		
		ameMasterStatus=ameMasterStatusService.getAmeMasterStatus(ameId);
		model.addAttribute("ameMasterStatus", ameMasterStatus);
		model.addAttribute("candidateDetails",candidateForcepersonalData );
	
		return"dealinghand/form-index-page-dh";
	}
	
	
	//=========================View-filled-application-dh===================================//
	@RequestMapping(path = "/view-filled-application-dh",method = RequestMethod.GET)
	public String showdeclarationtocontrollerdh(HttpSession httpSession,Model model) {
		String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String roleName=(String) httpSession.getAttribute("roleName");
		logger.info( ">>>>>>roleName>>>>>"+roleName+">>>>>>>>ForcePersonalId>>>>>>>>"+forcepersonalId);
		Optional<ForcePersonnelDto> optional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
		String unit = optional.get().getUnit();
		Integer forceNo=optional.get().getForceNo();

		logger.info("Current Unit:-"+unit);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
		
		if (forcepersonalId == null) {

			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
		
		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		
		List<AmeDeclarationIndividualModel> declarationIndividualDetails =ameDeclarationRepository.findDataForDealingHand(forceNo,unit);
		List<AmeApplicationFlowStatusDto> ameApplicationFlowStatusDtos=ameApplicationFlowStatusService.ameListOfCandidateForDealingHand(unit, forceNo);
		
		if (declarationIndividualDetails.isEmpty()) {
			model.addAttribute("message", "HAVE NO RECORD TO DISPALY...! ");
			return"dealinghand/show-declaration-to-controller-dh";

		}
		model.addAttribute("declarationDetails", ameApplicationFlowStatusDtos);
		
		
		return"dealinghand/display-list-of-ame-from-filled-pdf-format";
	}
	
	
	
	
	
	//==============================physical-measurement-index-page==========================//
	@RequestMapping(path = "physical-measurement-index-page-dh",method = RequestMethod.POST)
    public String displayPhysicalMeasurement(Model model,@RequestParam("forcepersonalId")String candidateForcepersonalId,@RequestParam ("ameId") String ameId,HttpSession httpSession) {
		model.addAttribute("forcepersonalId",candidateForcepersonalId);
		model.addAttribute("ameId",ameId);
		String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		if (forcepersonalId == null) {

			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		
		String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
	    RefDropDownRange chestRange=ameDropDownService.getDropDownRanges("chestExCo");
	    RefDropDownRange abdominGrithRange=ameDropDownService.getDropDownRanges("abdominalgirth");
	    RefDropDownRange trasTroChantericGirth=ameDropDownService.getDropDownRanges("trasTroChantericGirth");
	    Optional<PhysicalMeasurement> optional2=ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(ameId);
	    PhysicalMeasurement physicalMeasurement=new PhysicalMeasurement();
	    PlaceAndDateDto placeAndDateDto=assesmentPart1Service.getAmeDeclarationIndividualModel(ameId);
	    model.addAttribute("placeAndDateDto",placeAndDateDto);
		if(!optional2.isEmpty()) {
			physicalMeasurement=optional2.get();
			model.addAttribute("physicalMeasurement",physicalMeasurement);
		}else {
			model.addAttribute("physicalMeasurement",physicalMeasurement);

		}
		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		
		ForcePersonnelDto forcePersonal=loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId);
		
		Date dob=forcePersonal.getDob();
		Date doj=forcePersonal.getJoiningDate();
		int age=ageCalculatorService.getAge(dob);
		 String dateOfJoining =ageCalculatorService.calculateYearsMothsandDays(doj);
	
		logger.info(">>>>>>>>>>>>>>>>>>>>>>"+age);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
		model.addAttribute("candidateDetails",forcePersonal );
		model.addAttribute("chestRange", chestRange);
		model.addAttribute("abdominGrithRange", abdominGrithRange);
		model.addAttribute("age", age);
		model.addAttribute("doj",dateOfJoining );
		
		model.addAttribute("trasTroChantericGirth", trasTroChantericGirth);
		
		return"dealinghand/physical-measurement-dh";
	}
	
	
	//=============================================/assessment-General-Examination-1=============================================//
	
	
		@RequestMapping(path = "/assessment-General-Examination-dh-1",method = RequestMethod.POST)
		public String assismentgeneralexamination1(Model model,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId,@RequestParam ("ameId") String ameId){
	        
			GeneralExamination generalExamination=new GeneralExamination();
			RefDropDownRange bpRange=ameDropDownService.getDropDownRanges("bp");
			RefDropDownRange respiration=ameDropDownService.getDropDownRanges("respiration");
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");	
			if (forcePersonalId == null) {

				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
	           if(candidateForcepersonalId!=null) {
	        	   Optional<GeneralExamination> optional=assessmentServicePart_2_impl.getGeneralExaminationByAmeId(ameId);
	        
	        	   if(optional.isPresent()) {
	        		   
	        		   generalExamination=optional.get();
	        		   model.addAttribute("generalExamination", generalExamination);
	        		   
	        	   }else {
	        		   model.addAttribute("generalExamination", generalExamination);
	        	   }
               }
	        String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
	   		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
	   		
	   		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
	    	 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);   
	   		
	           model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
	        model.addAttribute("ameId", ameId);
	   	    model.addAttribute("forcepersonalId",candidateForcepersonalId);
	   	    model.addAttribute("bpRange", bpRange);
	   	    model.addAttribute("respiration", respiration);
	   	 

			return"dealinghand/physical-general-examination-dh-1";
		}
		

		//=========================================phychological-assessment-as-laid-down-dh======================================//
		

		@RequestMapping(path = "/phychological-assessment-as-laid-down-dh",method = RequestMethod.POST)
		public String assessmentpart1(Model model,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId,@RequestParam String ameId){
		   
			
		      PsychologicalAssessmentAsLaidDown phychologicalAssessmentAsLaidDown=new PsychologicalAssessmentAsLaidDown();
		      String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
		      if (forcePersonalId == null) {

					model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
					return "bootstrap_medical_temp/index";
				}
		    ///  List<RefPsychologicalType> psychologicalTypesList=ameDropDownService.getRefPsychologicalTypes();
		      model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		   		
		   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
		      
		    if(candidateForcepersonalId!=null) {
		    	
		    	 Optional<PsychologicalAssessmentAsLaidDown> optional=assesmentPart1Service.assessmentAsLaidDown(ameId);
	             if(optional.isPresent()) {
	            	 phychologicalAssessmentAsLaidDown=optional.get();
	            	 model.addAttribute("phychologicalAssessmentAsLaidDown", phychologicalAssessmentAsLaidDown);
	 
	             }else {
	            	 model.addAttribute("phychologicalAssessmentAsLaidDown", phychologicalAssessmentAsLaidDown);
	             }
		    	 
	           /// List<RefPsychologicalDuration> psychologicalDurations=ameDropDownService.getRefPsychologicalDuration();
	           ///  List<RefPsychologicalStatus> psychologicalStatus=ameDropDownService.getRefPsychologicalStatus();
	             
	             String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
	     		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
	  
	     		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				 
	            model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		   		
		   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
		    	/// model.addAttribute("psychologicalTypesList", psychologicalTypesList);
		    ///	model.addAttribute("psychologicalDurations", psychologicalDurations);
		    ///	model.addAttribute("psychologicalStatus", psychologicalStatus);
		        model.addAttribute("ameId", ameId);
	    	    model.addAttribute("forcepersonalId", candidateForcepersonalId);
	    	    }
	             return"dealinghand/psychological-as-laid-down";
		}
		
		//=================Hearing data fill action mapping=========================//
		
		@RequestMapping(path = "/assessment-hearing-dhm",method = RequestMethod.POST)
		public String assessmentHearing(Model model,@ModelAttribute MedExamDtoRequest examDtoRequest,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId
		,@RequestParam ("ameId") String ameId){
			//GeneralExamination generalExamination=new GeneralExamination();
			
			RefDropDownRange hearingDurations=ameDropDownService.getDropDownRanges("duration");
		//-->	List<RefHearingStatus> hearingStatus=ameDropDownService.getRefHearingStatus();
			
			
			Hearing hearing = new Hearing();
		///	List<RefHearingType> refHearingFactorlist = assesmentPart1Service.getHearingFactorList();
	  	   List<CategoryTypeMaster> categoryStatusTypes=ameDropDownService.categoryStatusTypes();
	  	   String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
	  	 if (forcePersonalId == null) {

				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
	        if(candidateForcepersonalId!=null) {
	     	   Optional<Hearing> optional=assesmentPart1Service.getHearing(ameId);
	      	///  model.addAttribute("refHearingFactorlist", refHearingFactorlist);
	     	   if(optional.isPresent()) {
	     		   
	     		  hearing=optional.get();
	     		  model.addAttribute("hearing", hearing);
	     		 
	     		   
	     	   }else {
	     		  model.addAttribute("hearing", hearing);
	     		  
	     		 
	     	   }
	     
			}
	        String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
	        
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
	           
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
	        model.addAttribute("ameId", ameId);
		    model.addAttribute("forcepersonalId",candidateForcepersonalId);
		//-->    model.addAttribute("refHearingstatuslist", hearingStatus);
		    model.addAttribute("refHearingDurationlist", hearingDurations);
		    model.addAttribute("categoryStatusTypes", categoryStatusTypes);
		    

			return"dealinghand/hearing-dhm";
		}
		
		
		//========================================/assessment-CNS-CNM-reflex-sensory-dh===================================
		@RequestMapping(path = "/assessment-CNS-CNM-reflex-sensory-dh",method = RequestMethod.POST)
		public String assesmentCentralNervousSystemCranialNervesMeningeal(Model model,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId,@RequestParam ("ameId") String ameId){
			
			MedExamDtoRequest examDtoRequest =new MedExamDtoRequest();
			
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			if (forcePersonalId == null) {

				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
	          if(candidateForcepersonalId!=null) {
	        	  
				Optional<CentralNervousSystem> centralNervousSystemOptional=assessmentServicePart_2_impl.centralNervousSystemByAmeId(ameId);
				Optional<CranialNervesMeningealSign> cranialNervesMeningealSignOptional=assessmentServicePart_2_impl.cranialNervesMeningealSignByAmeId(ameId);
				Optional<SensorySystem> sensorySystemOptional=assessmentServicePart_2_impl.sensorySystemByAmeId(ameId);
				Optional<Reflexes> reflexesOptional=assessmentServicePart_2_impl.reflexesByAmeId(ameId);
				
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
			}
	          
	           model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	           
				String candidateGazettedNonGazettedFlag = (String) httpSession
						.getAttribute("candidateGazettedNonGazettedFlag");
				model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
				
				String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
				 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
				
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
	          model.addAttribute("ameId", ameId);
	     	  model.addAttribute("forcepersonalId",candidateForcepersonalId);
			return"dealinghand/cns-reflexes-sensory-system-dh";
		}
		
		//=========================================assessment-abdomen-and-respiratory-system-update-dh==================================================//
		
		@RequestMapping(path = "/assesment-abdomen-save-update-dh", method = RequestMethod.POST)
		public String assesmentAbdomenSaveUpdate(Model model,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId, String ameId) {

			Abdomen abdomen = new Abdomen();
			Optional<Abdomen> abdomenOptional = assessmentServicePart_2_impl.abdomenByAmeId(ameId);
			RespiratorySystem respiratorySystem=new RespiratorySystem();
			MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
			//List<RefPhysicalType> refPhysicalTypesList=ameDropDownService.getRefPhysicalTypes();
			Optional<RespiratorySystem> reOptional=assessmentServicePart_2_impl.respiratorySystemByAmeId(ameId);

			RefDropDownRange refPhysicalDurationsList=ameDropDownService.getDropDownRanges("duration");
			//List<RefPhysicalStatus> refPhysicalStatusList=ameDropDownService.getRefPhysicalStatus();
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			if (forcePersonalId == null) {

				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
			if (candidateForcepersonalId != null) {
				if (abdomenOptional.isEmpty()&&reOptional.isEmpty()) {
					/*
					 * examDtoRequest.setAbdomen(abdomen);
					 * examDtoRequest.setRespiratorySystem(respiratorySystem);
					 */
					model.addAttribute("examDtoRequest", examDtoRequest);
					
				}
				
				
				else {
					
					abdomen = abdomenOptional.get();
					respiratorySystem=reOptional.get();
					examDtoRequest.setAbdomen(abdomen);
					examDtoRequest.setRespiratorySystem(respiratorySystem);
					model.addAttribute("examDtoRequest", examDtoRequest);

				}
			}
			String candidateGazettedNonGazettedFlag = (String) httpSession
					.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag); 
			
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			
	        model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
			model.addAttribute("ameId", ameId);
	   	    model.addAttribute("forcepersonalId",candidateForcepersonalId);
			/*
			 * model.addAttribute("refPhysicalTypesList",refPhysicalTypesList);
			 * model.addAttribute("refPhysicalDurationsList", refPhysicalDurationsList);
			 * model.addAttribute("refPhysicalStatusList", refPhysicalStatusList);
			 */
			return "dealinghand/physical-abdomen-dh";
		   }
		
		// =================Investigation data fill action mapping==============================//
		@RequestMapping(path = "/assessment-investigation-dh",method = RequestMethod.POST)
		public String assessmentInvestigation(Model model,@ModelAttribute MedExamDtoRequest examDtoRequest,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId,@RequestParam ("ameId") String ameId){
			
			
			Investigation investigation = new Investigation();
			//RefDropDownRange duration=ameDropDownService.getDropDownRanges("duration");
			RefDropDownRange bloodSugarRange=ameDropDownService.getDropDownRanges("sugar");
			RefDropDownRange hbRange=ameDropDownService.getDropDownRanges("hb");
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			if (forcePersonalId == null) {

				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
			//List<RefAppendagesType> refAppendageslist = assesmentPart1Service.getAppendagesFactorList();
	        if(candidateForcepersonalId!=null) {
	     	   Optional<Investigation> optional=assessmentServicePart_2_impl.getInvestigationByAmeId(ameId);
	     	 // model.addAttribute("refAppendageslist", refAppendageslist);
	     	   if(optional.isPresent()) {
	     		   
	     		  investigation=optional.get();
	     		   model.addAttribute("investigation", investigation);
	     		 
	     		   
	     	   }else {
	     		  model.addAttribute("investigation", investigation);
	     		 
	     	   }
	     
				
			}
	        String candidateGazettedNonGazettedFlag = (String) httpSession
					.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
	        
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			 
	        model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
	        model.addAttribute("ameId", ameId);
		    model.addAttribute("forcepersonalId",candidateForcepersonalId);
		   // model.addAttribute("durationList", duration);
		    model.addAttribute("bloodSugarRange", bloodSugarRange);
		    model.addAttribute("hbRange", hbRange);

			return"dealinghand/physical-investigation-dh";
		}
		
		
		
		
		
		
		
		// =====================Appendages data fill action mapping-dh===============================//
		@RequestMapping(path = "/assessment-appendages-dh",method = RequestMethod.POST)
		public String assessmentAppendages(Model model,@ModelAttribute MedExamDtoRequest examDtoRequest,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId,@RequestParam ("ameId") String ameId){

			
			
			Appendages appendages = new Appendages();
			List<RefAppendagesType> refAppendagesUpperlist = ameDropDownService.getRefAppendagesUpperTypes();
			List<RefAppendagesType> refAppendagesLowerlist=ameDropDownService.getRefAppendagesLowertype();
			List<CategoryTypeMaster> categoryStatusTypes=ameDropDownService.categoryStatusTypes();
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			if (forcePersonalId == null) {

				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
			if(candidateForcepersonalId!=null)
			{
	     	   Optional<Appendages> optional=assesmentPart1Service.getAppendages(ameId);
	     	  model.addAttribute("refAppendageslist", refAppendagesUpperlist);
	     	  model.addAttribute("refAppendagesLowerlist", refAppendagesLowerlist);
	     	  model.addAttribute("categoryStatusTypes", categoryStatusTypes);
	     	   if(optional.isPresent()) {
	     		   
	     		  appendages=optional.get();
	     		   model.addAttribute("appendages", appendages);
	     		 
	     		   
	     	   }else {
	     		  model.addAttribute("appendages", appendages);
	     		 
	     	   }
	     
				
			}
			String candidateGazettedNonGazettedFlag = (String) httpSession
					.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
	        model.addAttribute("ameId", ameId);
		    model.addAttribute("forcepersonalId",candidateForcepersonalId);
		  

			return"dealinghand/appendages-dh";
		}
		
		
		
		
		
		// =============Eye Factor fill action mapping=============================//
		@RequestMapping(path = "/assessment-Eye-Factor-dh",method = RequestMethod.POST)
		public String assessmentEyeFactor(Model model,@ModelAttribute MedExamDtoRequest examDtoRequest,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId,@RequestParam ("ameId") String ameId){
			//GeneralExamination generalExamination=new GeneralExamination();
			
			EyeFactor eyeFactor = new EyeFactor();
		///	List<RefEyeFactor> refEyeFactorlist = assesmentPart1Service.getEyeFactorList();
			RefDropDownRange eyeDurationList=ameDropDownService.getDropDownRanges("duration");
	    	
	//-->		List<RefEyeStatus> refEyeStatusList=ameDropDownService.getRefEyeStatus();
			
	    	List<EyeVisonScaleMaster> eyeVisonScaleMasters=ameDropDownService.getEyeVisonScaleMasters();
			
	    	List<NearVisionScaleMaster> nearVisionScaleMasters=ameDropDownService.getNearVisionScaleMasters();
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			if (forcePersonalId == null) {

				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
	        if(candidateForcepersonalId!=null) {
	     	   Optional<EyeFactor> optional=assesmentPart1Service.getEyeFactor(ameId);
	     ///	    model.addAttribute("refEyeFactorlist", refEyeFactorlist);
	     	   if(optional.isPresent()) {
	     		   
	     		   eyeFactor=optional.get();
	     		   model.addAttribute("eyeFactor", eyeFactor);
               }else {
	     		  model.addAttribute("eyeFactor", eyeFactor);
	     		 
	     	   }
               }
	        String candidateGazettedNonGazettedFlag = (String) httpSession
					.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			 
	           model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
	        model.addAttribute("eyeDurationList", eyeDurationList);
//-->	        model.addAttribute("refEyeStatusList", refEyeStatusList);
	        model.addAttribute("ameId", ameId);
		    model.addAttribute("forcepersonalId",candidateForcepersonalId);
		    model.addAttribute("nearVisionScaleMasters", nearVisionScaleMasters);
		    model.addAttribute("eyeVisonScaleMasters", eyeVisonScaleMasters);

			return"dealinghand/eye-factor-dh";
		}
		
		
		//====================================Gynae And Obs(in Case of female)===================================//
		
		@RequestMapping(path = "/assessment-gynecology-Factor-dh",method = RequestMethod.POST)
		public String assessmentGynaeAndObs(Model model,HttpSession httpSession,@RequestParam("forcepersonalId") String candidateForcepersonalId,@RequestParam ("ameId") String ameId ) {
			
			List<RefGFactor> gFactors=ameDropDownService.getGFactors();
			List<CategoryTypeMaster> categoryStatusTypes=ameDropDownService.categoryStatusTypes();
			Optional<GynaeAndObsFemale> gynaeAndObsFemales=assessmentServicePart_2_impl.gynaeByAmeId(ameId);
			RefDropDownRange downRange=ameDropDownService.getDropDownRanges("duration");
			GynaeAndObsFemale gynaeAndObsFemaledata=new GynaeAndObsFemale();
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			if (forcePersonalId == null) {

				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
			if(gynaeAndObsFemales.isPresent()) {
				gynaeAndObsFemaledata=gynaeAndObsFemales.get();
				model.addAttribute("gynaeAndObsFemaledata", gynaeAndObsFemaledata);
			}else {
				model.addAttribute("gynaeAndObsFemaledata", gynaeAndObsFemaledata);
			}
			 
			String candidateGazettedNonGazettedFlag = (String) httpSession
					.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
	         
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	   		
	   		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));
			model.addAttribute("categoryStatusTypes", categoryStatusTypes);
			model.addAttribute("gFactors", gFactors);
			model.addAttribute("ameId", ameId);
			model.addAttribute("forcepersonalId", candidateForcepersonalId);
			model.addAttribute("downRange",downRange);
			
			return"dealinghand/gynecology-obs-female-dh";
		}
		
		
		@RequestMapping(path = "/back-to-index-page-dh",method = RequestMethod.POST)
		public String backToFromIndexPageDH(Model model,HttpServletRequest request ,
				@RequestParam("forcepersonalId") String candidateforcepersonalId,
				@RequestParam("ameId") String ameId,HttpSession httpSession) {
			/*
			 * Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
			 * if (inputFlashMap != null) { forcepersonalId = (String)
			 * inputFlashMap.get("forcepersonalId"); ameId
			 * =(String)inputFlashMap.get("ameId"); }
			 */
			ForcePersonnelDto candidateForcePersonal=loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId);
			String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
			
			model.addAttribute("candidateDetails",candidateForcePersonal );
			
			AmeMasterStatus ameMasterStatus=new AmeMasterStatus();
			 ameMasterStatus=ameMasterStatusService.getAmeMasterStatus(ameId);
			model.addAttribute("ameMasterStatus", ameMasterStatus);
			
			String candidateGazettedNonGazettedFlag = (String) httpSession
					.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			
			String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
			 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			
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
		
		
		
		
		
	//========================dealing hand dash board by boardId======================================//
		
		
		
		@RequestMapping(value = { "dealing-to-board-dashboard"}, method = RequestMethod.POST)
      public String goToBoardMemberDashboard(HttpSession session, Model model,
    		  @RequestParam(value="boardId") String boardId ) {
	
   
			
	String gazettedNonGazettedFlag = (String) session.getAttribute("gazettedNonGazettedFlag");
	model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
	
	//===getting login in forcePersonnel=========================//
	String forcepersonalId = (String) session.getAttribute("forcepersonalId");
	
	Optional<ForcePersonnelDto> forceOptional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
	
	int checkupListPending;
	int checkupListCompleted;
	Integer pendingAmeFormCheckPending;
	Integer fileuploadPending;
	
	MedicalBoardDetailDto boardDetailDto=new MedicalBoardDetailDto();
	
	session.setAttribute("boardId", boardId.trim());
	//session.setAttribute("rCodeMedical",Integer.parseInt(rCode.trim()));
	
	//====display board details to the login in user=================//
	boardDetailDto=medicalBoardMemberService.findBoardDetailsByBoardId(boardId);
	if(boardDetailDto.getBoardId()!=null) {
		//boardDetailDto=medicalBoardMemberService.findBoardDetailsByBoardId(boardId);
		model.addAttribute("boardDetails",boardDetailDto );
	}else {
	
		model.addAttribute("boardDetails", boardDetailDto);
	}
	
	String roleName="ama"; 
	pendingAmeFormCheckPending= amaDeclarationCountService.getAMADeclarationPendingListCount(boardId);
	String pendingCountValue;
	if(pendingAmeFormCheckPending==null) {
		pendingCountValue="0";
	}else {
		pendingCountValue=String.valueOf(pendingAmeFormCheckPending);
	}

	Integer appointmentCompleted=amaDeclarationCountService.findDataForAMAAppointmentCount(boardId);
    String appointmentCompletedValue;
	if(appointmentCompleted==null) {
		appointmentCompletedValue="0";
	}else {
		appointmentCompletedValue=String.valueOf(appointmentCompleted);
	}
	
	String totalCompletedAma="0";
	Optional<ForcePersonnel> optional= forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
	int forceNo=optional.get().getForceNo();
	String unit=optional.get().getUnit();
	Integer totalCompletedCount=amaDeclarationCountService.findTotalDeclarationUnderProcessByBoardId(boardId);
	if(totalCompletedCount!=null) {
		totalCompletedAma=String.valueOf(totalCompletedCount);
	}
	
	
	
	
	
	int  AmeDeclarationFormUploadPendingCount=ameApplicationFlowStatusService.ameDeclarationFormUploadCompletedPending(boardId);
	int AMEDeclarationFormUploadCompletedCount = ameApplicationFlowStatusService
			.ameDeclarationFormUploadCompleted(boardId);
	int AMECheckupListPendingCount=ameApplicationFlowStatusService.ameCheckupListProvidedPending(boardId);
	int AMECheckupListProvidedCount = ameApplicationFlowStatusService
			.ameCheckupListProvided(boardId);
    model.addAttribute("AmeDeclarationFormUploadPendingCount",AmeDeclarationFormUploadPendingCount);
	model.addAttribute("AMEDeclarationFormUploadCompletedCount",AMEDeclarationFormUploadCompletedCount);
    model.addAttribute("AMECheckupListPendingCount",AMECheckupListPendingCount);
	model.addAttribute("AMECheckupListProvidedCount",AMECheckupListProvidedCount);
	
     
	List<UserRoleDto> userRoleDtos= (List<UserRoleDto>) session.getAttribute("roleDtosList");
    model.addAttribute("roleDtosList", userRoleDtos);
	
	
	
	session.setAttribute("forcepersonalId", forcepersonalId);
	session.setAttribute("roleName",roleName.trim().toUpperCase());
	model.addAttribute("pendingcountama", pendingCountValue);
	model.addAttribute("appointmentcompletedama",appointmentCompletedValue);
	model.addAttribute("totalCompletedAma", totalCompletedAma);
	model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
	model.addAttribute("boardId",boardId);
	model.addAttribute("boardCount",medicalBoardIndividualMappingRepo.boardCount(forcepersonalId,Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG))));
      
			return "dealinghand/dashboard-dh";
			
			
			
			
			
			
			
			
}
		
		
		
		

	
		@GetMapping("dealing-hand-dashboard")
        public String goBackToDealingHandDashBoard(HttpSession session, Model model) {
			
			String gazettedNonGazettedFlag = (String) session.getAttribute("gazettedNonGazettedFlag");
			model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
			String forcepersonalId = (String) session.getAttribute("forcepersonalId");
			
			Optional<ForcePersonnelDto> forceOptional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
			
			MedicalBoardDetailDto boardDetailDto=new MedicalBoardDetailDto();
			//logger.info("BoardId >>>>>>>>>>>>>>>>>"+boardIdInSession);
			String boardIdInSession = (String) session.getAttribute("boardId");
			if(boardIdInSession==null) {
				session.setAttribute("boardId", boardIdInSession);
			}
			String boardId=boardIdInSession;
			Integer pendingAmeFormCheckPending;
			//====display board details to the login in user=================//
			boardDetailDto=medicalBoardMemberService.findBoardDetailsByBoardId(boardId);
			if(boardDetailDto.getBoardId()!=null) {
				//boardDetailDto=medicalBoardMemberService.findBoardDetailsByBoardId(boardId);
				model.addAttribute("boardDetails",boardDetailDto );
			}else {
			
				model.addAttribute("boardDetails", boardDetailDto);
			}
			
			String roleName="ama"; 
			pendingAmeFormCheckPending= amaDeclarationCountService.getAMADeclarationPendingListCount(boardId);
			String pendingCountValue;
			if(pendingAmeFormCheckPending==null) {
				pendingCountValue="0";
			}else {
				pendingCountValue=String.valueOf(pendingAmeFormCheckPending);
			}

			Integer appointmentCompleted=amaDeclarationCountService.findDataForAMAAppointmentCount(boardId);
		    String appointmentCompletedValue;
			if(appointmentCompleted==null) {
				appointmentCompletedValue="0";
			}else {
				appointmentCompletedValue=String.valueOf(appointmentCompleted);
			}
			
			String totalCompletedAma="0";
			Optional<ForcePersonnel> optional= forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
			int forceNo=optional.get().getForceNo();
			String unit=optional.get().getUnit();
			Integer totalCompletedCount=amaDeclarationCountService.findTotalDeclarationUnderProcessByBoardId(boardId);
			if(totalCompletedCount!=null) {
				totalCompletedAma=String.valueOf(totalCompletedCount);
			}
			
			
			
			
			
			int  AmeDeclarationFormUploadPendingCount=ameApplicationFlowStatusService.ameDeclarationFormUploadCompletedPending(boardId);
			int AMEDeclarationFormUploadCompletedCount = ameApplicationFlowStatusService
					.ameDeclarationFormUploadCompleted(boardId);
			int AMECheckupListPendingCount=ameApplicationFlowStatusService.ameCheckupListProvidedPending(boardId);
			int AMECheckupListProvidedCount = ameApplicationFlowStatusService
					.ameCheckupListProvided(boardId);
		    model.addAttribute("AmeDeclarationFormUploadPendingCount",AmeDeclarationFormUploadPendingCount);
			model.addAttribute("AMEDeclarationFormUploadCompletedCount",AMEDeclarationFormUploadCompletedCount);
		    model.addAttribute("AMECheckupListPendingCount",AMECheckupListPendingCount);
			model.addAttribute("AMECheckupListProvidedCount",AMECheckupListProvidedCount);
			
		     
			List<UserRoleDto> userRoleDtos= (List<UserRoleDto>) session.getAttribute("roleDtosList");
		    model.addAttribute("roleDtosList", userRoleDtos);
			
			
			
			session.setAttribute("forcepersonalId", forcepersonalId);
			session.setAttribute("roleName",roleName.trim().toUpperCase());
			model.addAttribute("pendingcountama", pendingCountValue);
			model.addAttribute("appointmentcompletedama",appointmentCompletedValue);
			model.addAttribute("totalCompletedAma", totalCompletedAma);
			model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
			model.addAttribute("boardId",boardId);
			model.addAttribute("boardCount",medicalBoardIndividualMappingRepo.boardCount(forcepersonalId,Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG))));
		      
			return "dealinghand/dashboard-dh";
    
		}
		
		
		
		
		@RequestMapping(path = "/ame-declaration-checked-approved-dh",method = RequestMethod.POST)
		public String ameDeclarationCheckedApproved(
				@RequestParam("forcepersonalId") String candidateForcepersonalId,
				@RequestParam("ameId") String ameId,
				@RequestParam("remark") String remark,Model model,HttpServletRequest servletRequest) {
			
		      	String URI = null;
			

			HttpSession httpSession = servletRequest.getSession(false);
			if (httpSession == null) {
				model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
				return "bootstrap_medical_temp/index";
			}
			
		    int rCode= (int) httpSession.getAttribute("rCodeMedical");
		    logger.info("rCode>>>>>>>>>"+rCode);
			
			//URI=mapUriToUserService.getUriAfterAMEDeclarationDataCheck(rCode);
			//logger.info("URI>>>>>>>>>"+URI);
			String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
			String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
			model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		
			logger.info(">>> forcepersonalId >>>" + candidateForcepersonalId + ">>> ameId >>>" + ameId);

		
			Optional<ForcePersonnel> optional = forcePersonalRepository.getByForcePersonnelId(candidateForcepersonalId);
			if (!optional.isPresent()) {
				model.addAttribute("error-message",
						"sorry for inconvenience....Our Team is working on to solve the issue.");
				return "bootstrap_medical_temp/error-page";

			}
			
			model.addAttribute("forcepersonalId", candidateForcepersonalId);
			model.addAttribute("ameId", ameId);
			boolean result = ameAssessmentServicePart_2.updateAmeStatus(ameId,remark);

			if (result) {
				logger.info("Appointment given Sucessfully >>> by Dealing hand" + result);
				}

		
			return "redirect:/dealing-hand-dashboard";

		}
		
		
		
		
		
		
		
		
		
	
		
		
		
		
}
