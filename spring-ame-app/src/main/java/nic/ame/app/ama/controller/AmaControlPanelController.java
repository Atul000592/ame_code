package nic.ame.app.ama.controller;

import java.util.Date;

import java.util.List;

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
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.InvestigationDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.EyeVisonScaleMaster;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.NearVisionScaleMaster;
import nic.ame.app.master.medical.model.Others;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.medical.service.AmeDropDownService;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_2_impl;
import nic.ame.app.master.model.ColumnIdentifier;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.ref.entity.CategoryTypeMaster;
import nic.ame.app.master.ref.entity.RefAppendagesType;

import nic.ame.app.master.ref.entity.RefDropDownRange;
import nic.ame.app.master.ref.entity.RefGFactor;

import nic.ame.app.master.ref.entity.RefPhysicalStatus;
import nic.ame.app.master.repository.OthersTestRepository;
import nic.ame.app.master.ref.entity.CategoryShapeMaster;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.AmeFormDropDownService;
import nic.ame.app.master.service.AmeShapeCategoryDurationService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.constant.CommonConstant;

@Controller
public class AmaControlPanelController {

	private Logger logger = LogManager.getLogger(AmaControlPanelController.class);
	@Autowired
	private AmeDropDownService ameDropDownService;

	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;

	@Autowired
	private AmeAssessmentServicePart_2_impl assessmentServicePart_2_impl;

	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private AgeCalculatorService ageCalculatorService;

	@Autowired
	private AmeFormDropDownService ameFormDropDownService;

	@Autowired
	private AmeShapeCategoryDurationService ameShapeCategoryDurationService;

	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;

	@Autowired
	private InvestigationMasterService InvestigationMasterService;
	
	@Autowired
	private MapUriToUserService mapUriToUserService;
	
	@Autowired
	private InvestigationMasterService investigationMasterService;

	
	/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	// ==============================physical-measurement-index-page==========================//
	
	@RequestMapping(path = "physical-measurement-index-page-ama", method = RequestMethod.POST)
	public String displayPhysicalMeasurement(Model model,
			@RequestParam("forcepersonalId") String candidateforcepersonalId, HttpSession httpSession,
			@RequestParam("ameId") String ameId) {

		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("ameId", ameId);
        
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
		String uri = mapUriToUserService.getUriForPhysiacalMesurmentFormToUser(rCode);
		
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}

		RefDropDownRange chestRange = ameDropDownService.getDropDownRanges("chestExCo");
		RefDropDownRange abdominGrithRange = ameDropDownService.getDropDownRanges("abdominalgirth");
		RefDropDownRange trasTroChantericGirth = ameDropDownService.getDropDownRanges("trasTroChantericGirth");
		Optional<PhysicalMeasurement> optional2 = ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(ameId);
		PhysicalMeasurement physicalMeasurement = new PhysicalMeasurement();
		ForcePersonnelDto forcePersonal = loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId);

		Date dob = forcePersonal.getDob();
		int age = ageCalculatorService.getAge(dob);
		Date doj = forcePersonal.getJoiningDate();
		String dateOfJoining = ageCalculatorService.calculateYearsMothsandDays(doj);
		logger.info(">>>>>>>>>>>>>>>>>>>>>> age>>>>>>>>>" + age);
		logger.info(">>>>>>>>>>>>>>>>>>>>>> date of joining >>>>>>>>>" + dateOfJoining);
		
		PlaceAndDateDto placeAndDateDto = assesmentPart1Service.getAmeDeclarationIndividualModel(ameId);
         model.addAttribute("placeAndDateDto", placeAndDateDto);

		if (!optional2.isEmpty()) {
			physicalMeasurement = optional2.get();
			model.addAttribute("physicalMeasurement", physicalMeasurement);
		} else {
			model.addAttribute("physicalMeasurement", physicalMeasurement);

		}
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", forcePersonal);
		model.addAttribute("chestRange", chestRange);
		model.addAttribute("abdominGrithRange", abdominGrithRange);
		model.addAttribute("trasTroChantericGirth", trasTroChantericGirth);
		model.addAttribute("age", age);
		model.addAttribute("doj", dateOfJoining);
		model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);

		return uri;
	}

	/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

	// =============================================/assessment-General-Examination-1=============================================//

	@RequestMapping(path = "/assessment-General-Examination", method = RequestMethod.POST)
	public String assismentgeneralexamination1(Model model,
			@RequestParam("forcepersonalId") String candidateforcepersonalId, HttpSession httpSession,
			@RequestParam("ameId") String ameId, RedirectAttributes redirectAttributes) {

		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
		System.out.println(result);
		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			return "redirect:/declaration-requests-completed-ma";
		}
		
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
		String uri = mapUriToUserService.getUriForGeneralExaminationUser(rCode);

		GeneralExamination generalExamination = new GeneralExamination();
		RefDropDownRange bpRange = ameDropDownService.getDropDownRanges("bp");
		RefDropDownRange respiration = ameDropDownService.getDropDownRanges("respiration");
		RefDropDownRange pulseRange=ameDropDownService.getDropDownRanges("pulse");
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		if (candidateforcepersonalId != null) {
			Optional<GeneralExamination> optional = assessmentServicePart_2_impl.getGeneralExaminationByAmeId(ameId);

			ColumnIdentifier generalExaminationDropDownOptions = ameFormDropDownService.getGeneralExamination();

			if (optional.isPresent()) {

				generalExamination = optional.get();
				model.addAttribute("generalExamination", generalExamination);
				model.addAttribute("gDropDownOptions", generalExaminationDropDownOptions);

			} else {
				model.addAttribute("generalExamination", generalExamination);
				model.addAttribute("gDropDownOptions", generalExaminationDropDownOptions);
			}
		}
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("bpRange", bpRange);
		model.addAttribute("respiration", respiration);
		model.addAttribute("pulse", pulseRange);
		model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);

		return uri;
	}

	/*
	 * =============================================================================
	 * =============================================================================
	 * ===========================
	 */

	// =========================================phychological-assessment-as-laid-down-dh======================================//

	@RequestMapping(path = "psychological-assessment-as-laid-down", method = RequestMethod.POST)
	public String assessmentpart1(Model model, @RequestParam("forcepersonalId") String candidateforcepersonalId,
			HttpSession httpSession, @RequestParam String ameId, RedirectAttributes redirectAttributes) {
 
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>psychological-assessment-as-laid-down");
		String uri = mapUriToUserService.getUriForPhychologicalAssessmentAsLaidDown(rCode);
		
		
		
		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
		System.out.println(result);
		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			
			
			
			return "redirect:/declaration-requests-completed-ma";
		}
         
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		PsychologicalAssessmentAsLaidDown phychologicalAssessmentAsLaidDown = new PsychologicalAssessmentAsLaidDown();

		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		if (candidateforcepersonalId != null) {

			Optional<PsychologicalAssessmentAsLaidDown> optional = assesmentPart1Service.assessmentAsLaidDown(ameId);
			if (optional.isPresent()) {
				phychologicalAssessmentAsLaidDown = optional.get();
				model.addAttribute("phychologicalAssessmentAsLaidDown", phychologicalAssessmentAsLaidDown);

			} else {
				model.addAttribute("phychologicalAssessmentAsLaidDown", phychologicalAssessmentAsLaidDown);
			}

			List<CategoryTypeMaster> psychologicalStatus = ameDropDownService.categoryStatusTypes();
			ColumnIdentifier psychologyDropDown = ameFormDropDownService.getPsychologyDropDown();
			List<DropDownDto> psychologicalDurations = ameShapeCategoryDurationService.getAmeDuration();
//		          
			model.addAttribute("candidateDetails",
					loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
			List<CategoryShapeMaster> refpsychologicalShapesList = ameShapeCategoryDurationService
					.getAmeShapeType(CommonConstant.SHAPE_TYPE_CODE_FOR_PSYHCOLIGY);
			model.addAttribute("psychologicalTypesList", refpsychologicalShapesList);
			model.addAttribute("psychologicalDurations", psychologicalDurations);
			model.addAttribute("psychologicalStatus", psychologicalStatus);
			model.addAttribute("psychologyDropDown", psychologyDropDown);
			model.addAttribute("ameId", ameId);
			model.addAttribute("forcepersonalId", candidateforcepersonalId);
			model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);
		}
		return uri;
	}

	/*
	 * =============================================================================
	 * =============================================================================
	 * ===========================
	 */

	// =================Hearing data fill action mapping=========================//

	@RequestMapping(path = "/assessment-hearing-ama", method = RequestMethod.POST)
	public String assessmentHearing(Model model, @ModelAttribute MedExamDtoRequest examDtoRequest,
			@RequestParam("forcepersonalId") String candidateforcepersonalId, HttpSession httpSession,
			@RequestParam("ameId") String ameId, RedirectAttributes redirectAttributes) {
		// GeneralExamination generalExamination=new GeneralExamination();

		
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
		String uri = mapUriToUserService.getUriForHearing(rCode);
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+uri+">>physical Measurment");
		
		
		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
		System.out.println(result);
		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			return "redirect:/declaration-requests-completed-ma";
		}

		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		ColumnIdentifier hearingDropDown = ameFormDropDownService.getHearingDropDownOption();
		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		RefDropDownRange hearingDurations = ameDropDownService.getDropDownRanges("duration");
		// List<RefHearingStatus>
		// hearingStatus=ameDropDownService.getRefHearingStatus();

		Hearing hearing = new Hearing();

		List<CategoryShapeMaster> refHearingFactorlist = ameShapeCategoryDurationService
				.getAmeShapeType(CommonConstant.SHAPE_TYPE_CODE_FOR_HEARING);
		List<CategoryTypeMaster> categoryStatusTypes = ameDropDownService.categoryStatusTypes();

		if (candidateforcepersonalId != null) {
			Optional<Hearing> optional = assesmentPart1Service.getHearing(ameId);
			model.addAttribute("refHearingFactorlist", refHearingFactorlist);
			if (optional.isPresent()) {

				hearing = optional.get();
				model.addAttribute("hearing", hearing);

			} else {
				model.addAttribute("hearing", hearing);

			}

		}
		List<DropDownDto> refHearingDurationlist = ameShapeCategoryDurationService.getAmeDuration();
		model.addAttribute("hearingDropDown", hearingDropDown);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("refHearingstatuslist", refHearingDurationlist);
		model.addAttribute("refHearingDurationlist", hearingDurations);
		model.addAttribute("categoryStatusTypes", categoryStatusTypes);
		model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);

		return uri;
	}

	/*
	 * =============================================================================
	 * =============================================================================
	 * ===========================
	 */

	// ========================================/assessment-CNS-CNM-reflex-sensory-dh===================================
	@RequestMapping(path = "/assessment-CNS-CNM-reflex-sensory-main", method = RequestMethod.POST)
	public String assesmentCentralNervousSystemCranialNervesMeningeal(Model model,
			@RequestParam("forcepersonalId") String candidateforcepersonalId, HttpSession httpSession,
			@RequestParam("ameId") String ameId, RedirectAttributes redirectAttributes) {

		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
		System.out.println(result);
		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			return "redirect:/declaration-requests-completed-ma";
		}
		
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>CNS-CNM");
		String uri = mapUriToUserService.getUriForCNSCNMReflex(rCode);
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+uri+">>>>CNS-CNM");
		
		MedExamDtoRequest examDtoRequest = new MedExamDtoRequest();

		ColumnIdentifier cnsDropDown = ameFormDropDownService.getCNSDropDown();
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "redirect:/error";
		}

		if (candidateforcepersonalId != null) {

			Optional<CentralNervousSystem> centralNervousSystemOptional = assessmentServicePart_2_impl
					.centralNervousSystemByAmeId(ameId);
			Optional<CranialNervesMeningealSign> cranialNervesMeningealSignOptional = assessmentServicePart_2_impl
					.cranialNervesMeningealSignByAmeId(ameId);
			Optional<SensorySystem> sensorySystemOptional = assessmentServicePart_2_impl.sensorySystemByAmeId(ameId);
			Optional<Reflexes> reflexesOptional = assessmentServicePart_2_impl.reflexesByAmeId(ameId);

			CentralNervousSystem centralNervousSystem = new CentralNervousSystem();
			CranialNervesMeningealSign cranialNervesMeningealSign = new CranialNervesMeningealSign();
			Reflexes reflexes = new Reflexes();
			SensorySystem sensorySystem = new SensorySystem();

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

			model.addAttribute("examDtoRequest", examDtoRequest);
		}
		model.addAttribute("cnsDropDown", cnsDropDown);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);
		return uri;
	}

	/*
	 * =============================================================================
	 * =============================================================================
	 * ===========================
	 */

	// =========================================assessment-abdomen-and-respiratory-system-update-dh==================================================//

	@RequestMapping(path = "/assesment-abdomen-respiratory", method = RequestMethod.POST)
	public String assesmentAbdomenSaveUpdate(Model model,
			@RequestParam("forcepersonalId") String candidateforcepersonalId, HttpSession httpSession, String ameId,
			RedirectAttributes redirectAttributes) {
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
		System.out.println(result);
		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			return "redirect:/declaration-requests-completed-ma";
		}

		
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>abdomen");
		String uri = mapUriToUserService.getUriForAbdomenUser(rCode);
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+uri+">>>>abdomen");
		
		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		Abdomen abdomen = new Abdomen();
		Optional<Abdomen> abdomenOptional = assessmentServicePart_2_impl.abdomenByAmeId(ameId);
		RespiratorySystem respiratorySystem = new RespiratorySystem();
		MedExamDtoRequest examDtoRequest = new MedExamDtoRequest();
		// List<RefPhysicalType>
		// refPhysicalTypesList=ameDropDownService.getRefPhysicalTypes();

		List<CategoryShapeMaster> refPhysicalTypesList = ameShapeCategoryDurationService
				.getAmeShapeType(CommonConstant.SHAPE_TYPE_CODE_FOR_PHYSICAL);
		Optional<RespiratorySystem> reOptional = assessmentServicePart_2_impl.respiratorySystemByAmeId(ameId);

		// RefDropDownRange
		// refPhysicalDurationsList=ameDropDownService.getDropDownRanges("duration");
		List<CategoryTypeMaster> refPhysicalStatusList = ameDropDownService.categoryStatusTypes();
		if (candidateforcepersonalId != null) {
			if (abdomenOptional.isEmpty() && reOptional.isEmpty()) {

				examDtoRequest.setAbdomen(abdomen);
				examDtoRequest.setRespiratorySystem(respiratorySystem);
				model.addAttribute("examDtoRequest", examDtoRequest);

			}

			else {

				abdomen = abdomenOptional.get();
				respiratorySystem = reOptional.get();
				examDtoRequest.setAbdomen(abdomen);
				examDtoRequest.setRespiratorySystem(respiratorySystem);
				model.addAttribute("examDtoRequest", examDtoRequest);

			}
		}

		ColumnIdentifier respiratoryDropDown = ameFormDropDownService.getRespiratoryDropDown();
		ColumnIdentifier abdominSystemDropDown = ameFormDropDownService.getAbdomenDropDown();
		List<DropDownDto> refPhysicalDurationsList = ameShapeCategoryDurationService.getAmeDuration();
		model.addAttribute("respiratoryDropDown", respiratoryDropDown);
		model.addAttribute("abdominSystemDropDown", abdominSystemDropDown);
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("refPhysicalTypesList", refPhysicalTypesList);
		model.addAttribute("refPhysicalDurationsList", refPhysicalDurationsList);
		model.addAttribute("refPhysicalStatusList", refPhysicalStatusList);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
		model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);
		return uri;
	}

	/*
	 * =============================================================================
	 * =============================================================================
	 * ===========================
	 */

	// =================Investigation data fill action
	// mapping==============================//

	@RequestMapping(path = "assessment-investigation-report", method = RequestMethod.POST)
	public String assessmentInvestigation(Model model, @RequestParam("forcepersonalId") String candidateforcepersonalId,
			HttpSession httpSession, @RequestParam("ameId") String ameId, RedirectAttributes redirectAttributes) {
String ecgField=null;
String ecgFileName=null;
String ecgFilePath=null;

String usgField=null;
String usgFileName=null;
String usgFilePath=null;

String xrayField=null;
String xrayFileName=null;
String xrayFilePath=null;

		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);

		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			return "redirect:/declaration-requests-completed-ma";
		}

		
		
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>Investigation>>>>>>>>>>>");
		String uri = mapUriToUserService.getUriForInvestigationUser(rCode);
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+uri+">>>>Inveastigation");
		
		
		
		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}

		List<CheckUpList> checkUpLists = InvestigationMasterService.checkUpListsByAmeId(ameId,CommonConstant.ACTIVE_FLAG_NO);
		
		 List<CheckUpList> checkUpListsCompleted=InvestigationMasterService.findCheckUpListByAmeIdUploadFlagTrue(ameId);
		 
		 InvestigationDto investigationDto = investigationMasterService.getReportView(ameId);
         
		List<Others> otherTestList=investigationDto.getOthers();
		for (Others test : otherTestList) {
			if(test.getTestCode().equals("ecg")) {
				ecgField=test.getTestName();
				ecgFileName=test.getFileName();
				ecgFilePath=test.getPath();
				
			}
			
			else if(test.getTestCode().equals("usg")) {
				usgField=test.getTestName();
				usgFileName=test.getFileName();
				usgFilePath=test.getPath();
			}
			else if(test.getTestCode().equals("xray")) {
				xrayField=test.getTestName();
				xrayFileName=test.getFileName();
				xrayFilePath=test.getPath();
			}
		}
		model.addAttribute("checkUpLists", checkUpLists);
		
		model.addAttribute("checkUpListsCompleted", checkUpListsCompleted);

		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("investigationDto",investigationDto);
		
		model.addAttribute("ecgField", ecgField);
		model.addAttribute("ecgFileName", ecgFileName);
		model.addAttribute("ecgFilePath", ecgFilePath);
		
		model.addAttribute("usgField", usgField);
		model.addAttribute("usgFileName", usgFileName);
		model.addAttribute("usgFilePath", usgFilePath);
		
		model.addAttribute("xrayField", xrayField);
		model.addAttribute("xrayFileName", xrayFileName);
		model.addAttribute("xrayFilePath", xrayFilePath);

		return uri;
	}

	/*
	 * =============================================================================
	 * =============================================================================
	 * ===========================
	 */

	// =====================Appendages data fill action
	// mapping-dh===============================//

	@RequestMapping(path = "/assessment-appendages-ama", method = RequestMethod.POST)
	public String assessmentAppendages(Model model, @ModelAttribute MedExamDtoRequest examDtoRequest,
			@RequestParam("forcepersonalId") String candidateforcepersonalId, HttpSession httpSession,
			@RequestParam("ameId") String ameId, RedirectAttributes redirectAttributes) {

		
		

		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
		String uri = mapUriToUserService.getUriForappendages(rCode);
		logger.info("URI >>>>>>>>>>>>>>>>>> "+uri);
		
		
		
		
		
		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
		System.out.println(result);
		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			return "redirect:/declaration-requests-completed-ma";
		}
		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}

		Appendages appendages = new Appendages();

		if (candidateforcepersonalId != null) {
			Optional<Appendages> optional = assesmentPart1Service.getAppendages(ameId);

			if (optional.isPresent()) {

				appendages = optional.get();
				model.addAttribute("appendages", appendages);

			} else {
				model.addAttribute("appendages", appendages);

			}

		}
		List<CategoryShapeMaster> appendagesShape = ameShapeCategoryDurationService
				.getAmeShapeType(CommonConstant.SHAPE_TYPE_CODE_FOR_APPENDAGES);
		
		List<DropDownDto> appendagesDuration = ameShapeCategoryDurationService.getAmeDuration();
		List<CategoryTypeMaster> appendagesTypes = ameDropDownService.categoryStatusTypes();
	
		
		//=====Upper Limb Drop Down===========================//
		  List<RefAppendagesType> categoryUpper= ameDropDownService.getRefAppendagesCategory(CommonConstant.APPENDAGES_UPPER_CATEGORY);
		  List<RefAppendagesType> categoryLower= ameDropDownService.getRefAppendagesCategory(CommonConstant.APPENDAGES_LOWER_CATEGORY);
		  List<RefAppendagesType> categorySpine= ameDropDownService.getRefAppendagesCategory(CommonConstant.APPENDAGES_SPINE_CATEGORY);
		
		
		
		model.addAttribute("appendagesShape", appendagesShape);
		model.addAttribute("appendagesDuration", appendagesDuration);
		model.addAttribute("appendagesType", appendagesTypes);
		
		model.addAttribute("categoryUpper", categoryUpper);
		model.addAttribute("categoryLower", categoryLower);
		model.addAttribute("categorySpine", categorySpine);
		
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);

		return uri;
	}

	/*
	 * =============================================================================
	 * =============================================================================
	 * ===========================
	 */

	/* =============Eye Factor fill action mapping============================= */

	@RequestMapping(path = "/assessment-Eye-Factor-ama", method = RequestMethod.POST)
	public String assessmentEyeFactor(Model model, @ModelAttribute MedExamDtoRequest examDtoRequest,
			@RequestParam("forcepersonalId") String candidateforcepersonalId, HttpSession httpSession,
			@RequestParam("ameId") String ameId, RedirectAttributes redirectAttributes) {
		
		
		
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>physical Measurment");
		String uri = mapUriToUserService.getUriForEye(rCode);
		logger.info("URI >>>>>>>>>>>>>>>>>> "+uri);
		
		// GeneralExamination generalExamination=new GeneralExamination();
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
		System.out.println(result);
		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			return "redirect:/declaration-requests-completed-ma";
		}

		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		if (forcePersonalId == null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		EyeFactor eyeFactor = new EyeFactor();

		List<CategoryShapeMaster> refEyeFactorShapelist = ameShapeCategoryDurationService
				.getAmeShapeType(CommonConstant.SHAPE_TYPE_CODE_FOR_EYE);

		List<CategoryTypeMaster> refEyeStatusList = ameDropDownService.categoryStatusTypes();
		List<EyeVisonScaleMaster> eyeVisonScaleMasters = ameDropDownService.getEyeVisonScaleMasters();
		List<NearVisionScaleMaster> nearVisionScaleMasters = ameDropDownService.getNearVisionScaleMasters();

		if (candidateforcepersonalId != null) {
			Optional<EyeFactor> optional = assesmentPart1Service.getEyeFactor(ameId);
			model.addAttribute("refEyeFactorlist", refEyeFactorShapelist);
			if (optional.isPresent()) {

				eyeFactor = optional.get();
				model.addAttribute("eyeFactor", eyeFactor);
			} else {
				model.addAttribute("eyeFactor", eyeFactor);

			}
		}
		ColumnIdentifier eyeVisionDropDown = ameFormDropDownService.getEyeVisionDropDown();
		List<DropDownDto> eyeDurationList = ameShapeCategoryDurationService.getAmeDuration();
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
		model.addAttribute("eyeDurationList", eyeDurationList);
		model.addAttribute("refEyeStatusList", refEyeStatusList);
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
		model.addAttribute("nearVisionScaleMasters", nearVisionScaleMasters);
		model.addAttribute("eyeVisonScaleMasters", eyeVisonScaleMasters);
		model.addAttribute("eyeVisionDropDown", eyeVisionDropDown);
		model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);

		return uri;
	}

	/*
	 * =============================================================================
	 * =============================================================================
	 * ===========================
	 */

	/*
	 * ====================================Gynae And Obs(in Case of
	 * female)===================================
	 */
	@RequestMapping(path = "/assessment-gynecology",method = RequestMethod.POST)
	public String assessmentGynaeAndObs(Model model,@RequestParam("forcepersonalId")String candidateforcepersonalId,HttpSession httpSession,
			@RequestParam ("ameId") String ameId,RedirectAttributes redirectAttributes ) {
		
		String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		if(forcePersonalId==null) {
			httpSession.invalidate();
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		
		boolean result = ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
		System.out.println(result);
		if (!result) {
			redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
			redirectAttributes.addAttribute("ameId", ameId);
			redirectAttributes.addFlashAttribute("message",
					"Error :- Please Fill Physical Measurement Form First........!");
			return "redirect:/declaration-requests-completed-ma";
		}
		
		int rCode = (int) httpSession.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>gynecology");
		String uri = mapUriToUserService.getUriForGynecology(rCode);
		logger.info("URI >>>>>>>>>>>>>>>>>> "+uri);
		
		String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
    	 model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
		
		List<RefGFactor> gFactors=ameDropDownService.getGFactors();
		List<CategoryTypeMaster> categoryTypes=ameDropDownService.categoryStatusTypes();
		Optional<GynaeAndObsFemale> gynaeAndObsFemales=assessmentServicePart_2_impl.gynaeByAmeId(ameId);
	//	RefDropDownRange downRange=ameDropDownService.getDropDownRanges("duration");
		
		
		List<DropDownDto> gynecologyDurations = ameShapeCategoryDurationService.getAmeDuration();
		
		
		GynaeAndObsFemale gynaeAndObsFemaledata=new GynaeAndObsFemale();
		if(gynaeAndObsFemales.isPresent()) {
			gynaeAndObsFemaledata=gynaeAndObsFemales.get();
			model.addAttribute("gynaeAndObsFemaledata", gynaeAndObsFemaledata);
		}else {
			model.addAttribute("gynaeAndObsFemaledata", gynaeAndObsFemaledata);
		}
		
		ForcePersonnelDto candidateForcePersonnelDto= loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId);
		
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails",candidateForcePersonnelDto);
		model.addAttribute("categoryStatusTypes", categoryTypes);
		model.addAttribute("gFactors", gFactors);
		model.addAttribute("ameId", ameId);
		model.addAttribute("forcepersonalId", candidateforcepersonalId);
	model.addAttribute("gynecologyDurations",gynecologyDurations);
	model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);
		
		return uri;
	}
	
	
	/*
	 * @RequestMapping(path = "/assessment-gynecology-Factor-ama", method =
	 * RequestMethod.POST) public String assessmentGynaeAndObs(Model
	 * model, @RequestParam("forcepersonalId") String candidateforcepersonalId,
	 * HttpSession httpSession, @RequestParam("ameId") String ameId,
	 * RedirectAttributes redirectAttributes) {
	 * 
	 * String forcePersonalId = (String)
	 * httpSession.getAttribute("forcepersonalId"); String gazettedNonGazettedFlag =
	 * (String) httpSession.getAttribute("gazettedNonGazettedFlag");
	 * model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
	 * 
	 * boolean result =
	 * ameApplicationFlowStatusService.checkPhysicalMeasurmentcreateOrNot(ameId);
	 * System.out.println(result); if (!result) {
	 * redirectAttributes.addAttribute("forcepersonalId", candidateforcepersonalId);
	 * redirectAttributes.addAttribute("ameId", ameId);
	 * redirectAttributes.addFlashAttribute("message",
	 * "Error :- Please Fill Physical Measurement Form First........!"); return
	 * "redirect:/declaration-requests-completed-ma"; }
	 * 
	 * if (forcePersonalId == null) { httpSession.invalidate();
	 * model.addAttribute("errorMsg",
	 * "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST"); return
	 * "bootstrap_medical_temp/index"; }
	 * 
	 * String candidateGazettedNonGazettedFlag = (String)
	 * httpSession.getAttribute("candidateGazettedNonGazettedFlag");
	 * model.addAttribute("candidateGazettedNonGazettedFlag",
	 * candidateGazettedNonGazettedFlag);
	 * 
	 * List<RefGFactor> gFactors = ameDropDownService.getGFactors();
	 * List<RefCategoryStatusType> categoryStatusTypes =
	 * ameDropDownService.categoryStatusTypes(); Optional<GynaeAndObsFemale>
	 * gynaeAndObsFemales = assessmentServicePart_2_impl.gynaeByAmeId(ameId);
	 * RefDropDownRange downRange =
	 * ameDropDownService.getDropDownRanges("duration"); GynaeAndObsFemale
	 * gynaeAndObsFemaledata = new GynaeAndObsFemale(); if
	 * (gynaeAndObsFemales.isPresent()) { gynaeAndObsFemaledata =
	 * gynaeAndObsFemales.get(); model.addAttribute("gynaeAndObsFemaledata",
	 * gynaeAndObsFemaledata); } else { model.addAttribute("gynaeAndObsFemaledata",
	 * gynaeAndObsFemaledata); } model.addAttribute("loginUserDetails",
	 * loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
	 * model.addAttribute("candidateDetails",
	 * loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));
	 * model.addAttribute("categoryStatusTypes", categoryStatusTypes);
	 * model.addAttribute("gFactors", gFactors); model.addAttribute("ameId", ameId);
	 * model.addAttribute("forcepersonalId", candidateforcepersonalId);
	 * model.addAttribute("downRange", downRange);
	 * 
	 * return "medical-sub-ordinate/gynecology-obs-female-ama"; }
	 */
	
	
	

	
	
}
