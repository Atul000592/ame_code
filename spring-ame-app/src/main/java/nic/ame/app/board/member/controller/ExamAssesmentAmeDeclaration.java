package nic.ame.app.board.member.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
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
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.medical.service.AmeDropDownService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_2_impl;

import nic.ame.app.master.ref.entity.RefAppendagesType;
import nic.ame.app.master.ref.entity.CategoryTypeMaster;
import nic.ame.app.master.ref.entity.RefDropDownRange;
import nic.ame.app.master.ref.entity.RefGFactor;
import nic.ame.app.master.ref.entity.CategoryShapeMaster;
import nic.ame.app.master.service.AmeShapeCategoryDurationService;
import nic.ame.constant.CommonConstant;


@Controller
public class ExamAssesmentAmeDeclaration {
	

	
	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;
	
	@Autowired
	private AmeAssessmentServicePart_2_impl assessmentServicePart_2_impl;
	
	@Autowired
	private AmeDropDownService ameDropDownService;
	
	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
	
	@Autowired
	private AmeShapeCategoryDurationService ameShapeCategoryDurationService;

	
	
	
	
	
	
	//=========================================phychological-assessment-as-laid-down======================================//
	

	
	
	
	//=============================================/assessment-General-Examination-1=============================================//
	
	
	@RequestMapping(path = "/assessment-General-Examination-1",method = RequestMethod.POST)
	public String assismentgeneralexamination1(Model model,HttpSession httpSession,@RequestParam("forcepersonalId") String forcepersonalId,@RequestParam ("ameId") String ameId){
        
		GeneralExamination generalExamination=new GeneralExamination();
		RefDropDownRange bpRange=ameDropDownService.getDropDownRanges("bp");
		RefDropDownRange respiration=ameDropDownService.getDropDownRanges("respiration");
		
           if(forcepersonalId!=null) {
        	   Optional<GeneralExamination> optional=assessmentServicePart_2_impl.getGeneralExaminationByAmeId(ameId);
        
        	   if(optional.isPresent()) {
        		   
        		   generalExamination=optional.get();
        		   model.addAttribute("generalExamination", generalExamination);
        		   
        	   }else {
        		   model.addAttribute("generalExamination", generalExamination);
        	   }
        
			
		}
        model.addAttribute("ameId", ameId);
   	    model.addAttribute("forcepersonalId",forcepersonalId);
   	    model.addAttribute("bpRange", bpRange);
   	 model.addAttribute("respiration", respiration);
   	 

		return"BoardMember/physical-general-examination-1";
	}
	
	
	
	
 
	//========================================/assessment-CNS-CNM-reflex-sensory===================================
	@RequestMapping(path = "/assessment-CNS-CNM-reflex-sensory",method = RequestMethod.POST)
	public String assesmentCentralNervousSystemCranialNervesMeningeal(Model model,HttpSession httpSession,@RequestParam("forcepersonalId") String forcepersonalId,@RequestParam ("ameId") String ameId){
		
		MedExamDtoRequest examDtoRequest =new MedExamDtoRequest();
		
		
          if(forcepersonalId!=null) {
        	  
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
          model.addAttribute("ameId", ameId);
     	  model.addAttribute("forcepersonalId",forcepersonalId);
		return"BoardMember/cns-reflexes-sensory-system";
	}
	
	
	
	
//=========================================assessment-abdomen-and-respiratory-system-update==================================================//
	
	@RequestMapping(path = "/assesment-abdomen-save-update", method = RequestMethod.POST)
	public String assesmentAbdomenSaveUpdate(Model model, HttpSession httpSession,
			@RequestParam("forcepersonalId") String forcepersonalId, String ameId) {

		Abdomen abdomen = new Abdomen();
		Optional<Abdomen> abdomenOptional = assessmentServicePart_2_impl.abdomenByAmeId(ameId);
		RespiratorySystem respiratorySystem=new RespiratorySystem();
		MedExamDtoRequest examDtoRequest=new MedExamDtoRequest();
		//List<RefPhysicalType> refPhysicalTypesList=ameDropDownService.getRefPhysicalTypes();
		Optional<RespiratorySystem> reOptional=assessmentServicePart_2_impl.respiratorySystemByAmeId(ameId);

		//RefDropDownRange refPhysicalDurationsList=ameDropDownService.getDropDownRanges("duration");
		//List<RefPhysicalStatus> refPhysicalStatusList=ameDropDownService.getRefPhysicalStatus();
		if (forcepersonalId != null) {
			if (abdomenOptional.isEmpty()&&reOptional.isEmpty()) {
				
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
		
		List<CategoryShapeMaster> refPhysicalTypesList = ameShapeCategoryDurationService.getAmeShapeType(CommonConstant.SHAPE_TYPE_CODE_FOR_PHYSICAL);
		List<CategoryTypeMaster> refPhysicalStatusList = ameDropDownService.categoryStatusTypes();
		List<DropDownDto> refPhysicalDurationsList = ameShapeCategoryDurationService.getAmeDuration();
		
		model.addAttribute("ameId", ameId);
   	    model.addAttribute("forcepersonalId",forcepersonalId);
   	    model.addAttribute("refPhysicalTypesList",refPhysicalTypesList);
   	    model.addAttribute("refPhysicalDurationsList", refPhysicalDurationsList);
   	    model.addAttribute("refPhysicalStatusList", refPhysicalStatusList);

		return "BoardMember/physical-abdomen";
	   }
	
	
	// Hearing data fill action mapping
	
	@RequestMapping(path = "/assessment-hearing",method = RequestMethod.POST)
	public String assessmentHearing(Model model,@ModelAttribute MedExamDtoRequest examDtoRequest,HttpSession httpSession,@RequestParam("forcepersonalId")
	String forcepersonalId,@RequestParam ("ameId") String ameId){
		//GeneralExamination generalExamination=new GeneralExamination();
		
		RefDropDownRange hearingDurations=ameDropDownService.getDropDownRanges("duration");
		List<CategoryTypeMaster> hearingStatus=ameDropDownService.categoryStatusTypes();
		
		
		Hearing hearing = new Hearing();
		///List<RefHearingType> refHearingFactorlist = assesmentPart1Service.getHearingFactorList();
  	   List<CategoryTypeMaster> categoryStatusTypes=ameDropDownService.categoryStatusTypes();

        if(forcepersonalId!=null) {
     	   Optional<Hearing> optional=assesmentPart1Service.getHearing(ameId);
      ///	  model.addAttribute("refHearingFactorlist", refHearingFactorlist);
     	   if(optional.isPresent()) {
     		   
     		  hearing=optional.get();
     		   model.addAttribute("hearing", hearing);
     		 
     		   
     	   }else {
     		  model.addAttribute("hearing", hearing);
     		 
     	   }
     
			
		}
        model.addAttribute("ameId", ameId);
	    model.addAttribute("forcepersonalId",forcepersonalId);
	    model.addAttribute("refHearingstatuslist", hearingStatus);
	    model.addAttribute("refHearingDurationlist", hearingDurations);
	    model.addAttribute("categoryStatusTypes", categoryStatusTypes);
	    

		return"BoardMember/hearing";
	}
	
	
	
	// =================Investigation data fill action mapping==============================//
	@PostMapping("/assessment-investigation")
	public String assessmentInvestigation(Model model,@ModelAttribute MedExamDtoRequest examDtoRequest,HttpSession httpSession,@RequestParam("forcepersonalId")
	String forcepersonalId,@RequestParam ("ameId") String ameId){
		
		
		Investigation investigation = new Investigation();
		RefDropDownRange duration=ameDropDownService.getDropDownRanges("duration");
		RefDropDownRange bloodSugarRange=ameDropDownService.getDropDownRanges("sugar");
		RefDropDownRange hbRange=ameDropDownService.getDropDownRanges("hb");
		//List<RefAppendagesType> refAppendageslist = assesmentPart1Service.getAppendagesFactorList();
        if(forcepersonalId!=null) {
     	   Optional<Investigation> optional=assessmentServicePart_2_impl.getInvestigationByAmeId(ameId);
     	 // model.addAttribute("refAppendageslist", refAppendageslist);
     	   if(optional.isPresent()) {
     		   
     		  investigation=optional.get();
     		   model.addAttribute("investigation", investigation);
     		 
     		   
     	   }else {
     		  model.addAttribute("investigation", investigation);
     		 
     	   }
     
			
		}
        model.addAttribute("ameId", ameId);
	    model.addAttribute("forcepersonalId",forcepersonalId);
	   // model.addAttribute("durationList", duration);
	    model.addAttribute("bloodSugarRange", bloodSugarRange);
	    model.addAttribute("hbRange", hbRange);

		return"BoardMember/physical-investigation";  
	}
	
 // =====================Appendages data fill action mapping===============================//
	@PostMapping("/assessment-appendages")
	public String assessmentAppendages(Model model,@ModelAttribute MedExamDtoRequest examDtoRequest,HttpSession httpSession,@RequestParam("forcepersonalId")
	String forcepersonalId,@RequestParam ("ameId") String ameId){

		
		List<DropDownDto> appendagesDurations=ameShapeCategoryDurationService.getAmeDuration();
		List<CategoryTypeMaster> appendagesStatus=ameDropDownService.categoryStatusTypes();
		
		Appendages appendages = new Appendages();
		List<RefAppendagesType> refAppendagesUpperlist = ameDropDownService.getRefAppendagesUpperTypes();
		List<RefAppendagesType> refAppendagesLowerlist=ameDropDownService.getRefAppendagesLowertype();
		List<CategoryTypeMaster> categoryStatusTypes=ameDropDownService.categoryStatusTypes();
        
		if(forcepersonalId!=null)
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
        
        model.addAttribute("ameId", ameId);
	    model.addAttribute("forcepersonalId",forcepersonalId);
	    model.addAttribute("refAppendagesStatus",appendagesStatus);
	    model.addAttribute("refAppendagesDurationList", appendagesDurations);


		return"BoardMember/appendages";
	}
	
	
	
	
	
	// =============Eye Factor fill action mapping=============================//
	@RequestMapping(path = "/assessment-Eye-Factor",method = RequestMethod.POST)
	public String assessmentEyeFactor(Model model,@ModelAttribute MedExamDtoRequest examDtoRequest,HttpSession httpSession,@RequestParam("forcepersonalId")
	String forcepersonalId,@RequestParam ("ameId") String ameId){
		//GeneralExamination generalExamination=new GeneralExamination();
		
		EyeFactor eyeFactor = new EyeFactor();
	///	List<RefEyeFactor> refEyeFactorlist = assesmentPart1Service.getEyeFactorList();
		RefDropDownRange eyeDurationList=ameDropDownService.getDropDownRanges("duration");
		List<CategoryTypeMaster> refEyeStatusList=ameDropDownService.categoryStatusTypes();
		List<EyeVisonScaleMaster> eyeVisonScaleMasters=ameDropDownService.getEyeVisonScaleMasters();
		List<NearVisionScaleMaster> nearVisionScaleMasters=ameDropDownService.getNearVisionScaleMasters();
		
        if(forcepersonalId!=null) {
     	   Optional<EyeFactor> optional=assesmentPart1Service.getEyeFactor(ameId);
     	///  model.addAttribute("refEyeFactorlist", refEyeFactorlist);
     	   if(optional.isPresent()) {
     		   
     		   eyeFactor=optional.get();
     		   model.addAttribute("eyeFactor", eyeFactor);
     		 
     		   
     	   }else {
     		  model.addAttribute("eyeFactor", eyeFactor);
     		 
     	   }
     
			
		}
        
        model.addAttribute("eyeDurationList", eyeDurationList);
        model.addAttribute("refEyeStatusList", refEyeStatusList);
        model.addAttribute("ameId", ameId);
	    model.addAttribute("forcepersonalId",forcepersonalId);
	    model.addAttribute("nearVisionScaleMasters", nearVisionScaleMasters);
	    model.addAttribute("eyeVisonScaleMasters", eyeVisonScaleMasters);

		return"BoardMember/eye-factor";
	}
	
	
	//====================================Gynae And Obs(in Case of female===================================//
	
	@RequestMapping(path = "/assessment-gynecology-Factor",method = RequestMethod.POST)
	public String assessmentGynaeAndObs(Model model,@RequestParam("forcepersonalId")String forcepersonalId,@RequestParam ("ameId") String ameId ) {
		
		List<RefGFactor> gFactors=ameDropDownService.getGFactors();
		List<CategoryTypeMaster> categoryStatusTypes=ameDropDownService.categoryStatusTypes();
		Optional<GynaeAndObsFemale> gynaeAndObsFemales=assessmentServicePart_2_impl.gynaeByAmeId(ameId);
		RefDropDownRange downRange=ameDropDownService.getDropDownRanges("duration");
		GynaeAndObsFemale gynaeAndObsFemaledata=new GynaeAndObsFemale();
		if(gynaeAndObsFemales.isPresent()) {
			gynaeAndObsFemaledata=gynaeAndObsFemales.get();
			model.addAttribute("gynaeAndObsFemaledata", gynaeAndObsFemaledata);
		}else {
			model.addAttribute("gynaeAndObsFemaledata", gynaeAndObsFemaledata);
		}
		
		model.addAttribute("categoryStatusTypes", categoryStatusTypes);
		model.addAttribute("gFactors", gFactors);
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", forcepersonalId);
		model.addAttribute("downRange",downRange);
		
		return"BoardMember/gynecology-obs-female";
	}
	
	@RequestMapping(path = "physical-measurement-index-page",method = RequestMethod.POST)
    public String displayPhysicalMeasurement(Model model,@RequestParam("forcepersonalId")String forcepersonalId,@RequestParam ("ameId") String ameId) {
		model.addAttribute("forcepersonalId",forcepersonalId);
		model.addAttribute("ameId",ameId);
		
	    RefDropDownRange chestRange=ameDropDownService.getDropDownRanges("chestExCo");
	    RefDropDownRange abdominGrithRange=ameDropDownService.getDropDownRanges("abdominalgirth");
	    RefDropDownRange trasTroChantericGirth=ameDropDownService.getDropDownRanges("trasTroChantericGirth");
	    Optional<PhysicalMeasurement> optional2=ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(ameId);
	    PhysicalMeasurement physicalMeasurement=new PhysicalMeasurement();
		if(!optional2.isEmpty()) {
			physicalMeasurement=optional2.get();
			model.addAttribute("physicalMeasurement",physicalMeasurement);
		}else {
			model.addAttribute("physicalMeasurement",physicalMeasurement);

		}
		model.addAttribute("chestRange", chestRange);
		model.addAttribute("abdominGrithRange", abdominGrithRange);
		model.addAttribute("trasTroChantericGirth", trasTroChantericGirth);
		
		return"BoardMember/physical-measurement";
	}
	
	
}
