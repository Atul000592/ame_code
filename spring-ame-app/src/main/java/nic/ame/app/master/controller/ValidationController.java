package nic.ame.app.master.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.board.member.repository.AbdomenRepo;
import nic.ame.app.board.member.repository.AppendagesRepo;
import nic.ame.app.board.member.repository.CentralNervousSystemRepo;
import nic.ame.app.board.member.repository.CranialNervesMeningealSignRepo;
import nic.ame.app.board.member.repository.EyeFactorRepo;
import nic.ame.app.board.member.repository.GeneralExaminationRepo;
import nic.ame.app.board.member.repository.GynaeAndObsFemaleRepo;
import nic.ame.app.board.member.repository.HearingRepo;
import nic.ame.app.board.member.repository.PhysicalMeasurmentRepo;
import nic.ame.app.board.member.repository.PsychologicalAssessmentAsLaidDownRepo;
import nic.ame.app.board.member.repository.ReflexesRepo;
import nic.ame.app.board.member.repository.RespiratorySystemRepo;
import nic.ame.app.board.member.repository.SensoryRepo;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.AmeDetails;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeDetailsRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;
import nic.ame.constant.CommonConstant;
import nic.ame.constant.RegexValidation;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.RegexPatternHanlder;

@Controller
public class ValidationController {

	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;

	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;

	@Autowired
	private ForcePersonnelRepository forcePersonnelRespository;

	@Autowired
	private AmeDetailsRepository ameDetailsRepository;

	@Autowired
	private AgeCalculatorService ageCalculatorService;
	
	@Autowired
	private AmeDeclarationIndividualModelRepoUser ameDeclarationIndividualModelRepoUser;
	
	@Autowired
	private PsychologicalAssessmentAsLaidDownRepo psychologicalAssessmentAsLaidDownRepository;
	
	@Autowired
	private HearingRepo hearingRepository;
	
	@Autowired
	private AppendagesRepo appendagesRepository;
	
	@Autowired
	private EyeFactorRepo eyeFactorRepository;
	
	@Autowired
    private GynaeAndObsFemaleRepo gynaeAndObsFemaleRepository;
	
	@Autowired
	private GeneralExaminationRepo generalExaminationRepository;
	
	@Autowired
	private CentralNervousSystemRepo centralNervousSystemRepository;
	
	@Autowired
	private CranialNervesMeningealSignRepo cranialNervesMeningealSignRepository;
	
	@Autowired
	private ReflexesRepo reflexesRepository;
	
	@Autowired
	private SensoryRepo sensorySystemRepository;
	
	@Autowired
	private AbdomenRepo abdomenRepository;
	
	@Autowired
	private RespiratorySystemRepo respiratorySystemRepository;
	
	
	
	
	
	@PostMapping("validate-and-save-physical-measurement")
	@ResponseBody
	public ResponseEntity<?> validatePhysicalMeasurement(@RequestBody Map<String, Object> data, HttpSession httpSession,
			HttpServletRequest request) {

		System.out.println("Recieved Data: " + data);
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			PhysicalMeasurement physicalMeasurement = objectMapper.convertValue(data.get("data"),
					PhysicalMeasurement.class);
			String gender = (String) data.get("gender");

			String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");
			String candidateForcePersonnelId = objectMapper.convertValue(data.get("candidateForcePersonnelId"),
					String.class);

			Optional<ForcePersonnel> candidateforcePersonnelDetailsOptional = forcePersonnelRespository
					.getByForcePersonnelId(candidateForcePersonnelId);
			ForcePersonnel candidateforcePersonnelDetails = candidateforcePersonnelDetailsOptional.get();
			AmeDetails ameDetails = new AmeDetails();

//			===============================Height=====================================================	  

			if (String.valueOf(physicalMeasurement.getHeight()).isBlank()
					|| String.valueOf(physicalMeasurement.getHeight()).isEmpty()
					|| physicalMeasurement.getHeight() <= 0) {
				errors.put("height", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (RegexPatternHanlder.isValidString(String.valueOf(physicalMeasurement.getHeight()))) {
				errors.put("height", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

			}

//		     ===============================Weight=====================================================

			if (String.valueOf(physicalMeasurement.getWeight()).isBlank()
					|| String.valueOf(physicalMeasurement.getWeight()).isEmpty()
					|| physicalMeasurement.getWeight() <= 0) {
				errors.put("weight", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (RegexPatternHanlder.isValidString(String.valueOf(physicalMeasurement.getWeight()))) {
				errors.put("weight", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

			}
//		    ====================================BMI================================================

			if (String.valueOf(physicalMeasurement.getBmi()) == null || physicalMeasurement.getBmi() <= 0) {
				errors.put("bmi", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (RegexPatternHanlder.isValidString(String.valueOf(physicalMeasurement.getBmi()))) {
				errors.put("bmi", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

			}

//		     ====================================Chest Expanded and unexpanded================================================

			if (!gender.equalsIgnoreCase("Female")) {
				if (String.valueOf(physicalMeasurement.getChestExpanded()).isBlank()
						|| String.valueOf(physicalMeasurement.getChestExpanded()).isEmpty()
						|| physicalMeasurement.getChestExpanded() <= 0) {
					errors.put("chestExpanded", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (RegexPatternHanlder.isValidString(String.valueOf(physicalMeasurement.getChestExpanded()))) {
					errors.put("chestExpanded", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

				}

				if (String.valueOf(physicalMeasurement.getChestUnexpanded()).isBlank()
						|| String.valueOf(physicalMeasurement.getChestUnexpanded()).isEmpty()
						|| physicalMeasurement.getChestUnexpanded() <= 0) {
					errors.put("chestUnexpanded", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (RegexPatternHanlder.isValidString(String.valueOf(physicalMeasurement.getChestUnexpanded()))) {
					errors.put("chestUnexpanded", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

				}
			}

//		    ====================================Abdominal Girth================================================

			if (String.valueOf(physicalMeasurement.getAbdominalGirth()).isBlank()
					|| String.valueOf(physicalMeasurement.getAbdominalGirth()).isEmpty()
					|| physicalMeasurement.getAbdominalGirth() <= 0) {
				errors.put("abdominalGirth", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (RegexPatternHanlder.isValidString(String.valueOf(physicalMeasurement.getAbdominalGirth()))) {
				errors.put("abdominalGirth", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

			}

//			====================================Trans-TroChanteric girth=======================================

			if (String.valueOf(physicalMeasurement.getTrasTroChantericGirth()).isBlank()
					|| String.valueOf(physicalMeasurement.getTrasTroChantericGirth()).isEmpty()
					|| physicalMeasurement.getTrasTroChantericGirth() <= 0) {
				errors.put("trasTroChantericGirth", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (RegexPatternHanlder.isValidString(String.valueOf(physicalMeasurement.getTrasTroChantericGirth()))) {
				errors.put("trasTroChantericGirth", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

			}
//			============================================Ratio AT ==============================================

			if (String.valueOf(physicalMeasurement.getRatioAT()).isBlank()
					|| String.valueOf(physicalMeasurement.getRatioAT()).isEmpty()
					|| physicalMeasurement.getRatioAT() <= 0) {
				errors.put("ratioAT", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
//==================================================================================================================================
			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				Optional<PhysicalMeasurement> physicalMeasurementOptional = ameAssessmentServicePart_2
						.getPhysicalMeasurmentByAmeId(physicalMeasurement.getAmeId());
				if (!physicalMeasurementOptional.isPresent()) {

					physicalMeasurement.setLastModifiedOn(Calendar.getInstance().getTime());
					physicalMeasurement.setLastModifiedBy(loggedInUserForcepersonnelId);
					physicalMeasurement.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					ameAssessmentServicePart_2.savePhysicalMeasurement(physicalMeasurement,
							physicalMeasurement.getAmeId());
					int age = ageCalculatorService.getAge(candidateforcePersonnelDetails.getDob());
					String totalService = ageCalculatorService
							.calculateYearsMothsandDays(candidateforcePersonnelDetails.getJoiningDate());

					ameDetails.setAmeId(physicalMeasurement.getAmeId());
					ameDetails.setForcePersonalId(candidateForcePersonnelId);
					ameDetails.setForceId(candidateforcePersonnelDetails.getForceId());
					ameDetails.setName(candidateforcePersonnelDetails.getName());
					ameDetails.setForceNo(candidateforcePersonnelDetails.getForceNo());
					ameDetails.setUnit(candidateforcePersonnelDetails.getUnit());
					ameDetails.setRank(candidateforcePersonnelDetails.getRank());
					ameDetails.setDesignation(candidateforcePersonnelDetails.getDesignation());
					ameDetails.setGazettedNonGazettedFlag(candidateforcePersonnelDetails.getGazettedNonGazettedFlag());
					ameDetails.setJoiningDate(candidateforcePersonnelDetails.getJoiningDate());
					ameDetails.setDob(candidateforcePersonnelDetails.getDob());
					ameDetails.setGender(candidateforcePersonnelDetails.getGender());
					ameDetails.setLastAmeDate(candidateforcePersonnelDetails.getLastAmeDate());
					ameDetails.setAmePlace(candidateforcePersonnelDetails.getAmePlace());
					ameDetails.setTotalService(totalService);
					ameDetails.setLastModifiedOn(Calendar.getInstance().getTime());
					ameDetails.setLastModifiedBy(loggedInUserForcepersonnelId);
					ameDetails.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					ameDetails.setAttachedUnit(candidateforcePersonnelDetails.getAttachUnit());

					ameDetailsRepository.save(ameDetails);

					response.put("isValid", true);
					response.put("message", "Data saved successfully!");
					return ResponseEntity.ok(response);

				} else {

					physicalMeasurement.setLastModifiedOn(Calendar.getInstance().getTime());
					physicalMeasurement.setLastModifiedBy(loggedInUserForcepersonnelId);
					physicalMeasurement.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					ameAssessmentServicePart_2.updatePhysicalMeasurement(physicalMeasurement.getAmeId(),
							physicalMeasurement);

					String totalService = ageCalculatorService
							.calculateYearsMothsandDays(candidateforcePersonnelDetails.getJoiningDate());

					ameDetails.setAmeId(physicalMeasurement.getAmeId());
					ameDetails.setForcePersonalId(candidateForcePersonnelId);
					ameDetails.setForceId(candidateforcePersonnelDetails.getForceId());
					ameDetails.setName(candidateforcePersonnelDetails.getName());
					ameDetails.setForceNo(candidateforcePersonnelDetails.getForceNo());
					ameDetails.setUnit(candidateforcePersonnelDetails.getUnit());
					ameDetails.setRank(candidateforcePersonnelDetails.getRank());
					ameDetails.setDesignation(candidateforcePersonnelDetails.getDesignation());
					ameDetails.setGazettedNonGazettedFlag(candidateforcePersonnelDetails.getGazettedNonGazettedFlag());
					ameDetails.setJoiningDate(candidateforcePersonnelDetails.getJoiningDate());
					ameDetails.setDob(candidateforcePersonnelDetails.getDob());
					ameDetails.setGender(candidateforcePersonnelDetails.getGender());
					ameDetails.setLastAmeDate(candidateforcePersonnelDetails.getLastAmeDate());
					ameDetails.setAmePlace(candidateforcePersonnelDetails.getAmePlace());
					ameDetails.setTotalService(totalService);
					ameDetails.setLastModifiedOn(Calendar.getInstance().getTime());
					ameDetails.setLastModifiedBy(loggedInUserForcepersonnelId);
					ameDetails.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					ameDetailsRepository.save(ameDetails);

					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					return ResponseEntity.ok(response);
					// logger.info(">>>>>>>>>>>>>>> Physical Measurement Updated Successfully");

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");

		}

	}

//	=============================================Psychology Validation=============================================================
	@PostMapping("validate-and-save-psychology")
	@ResponseBody
	public ResponseEntity<?> validatePsychology(@RequestBody Map<String, Object> data, HttpSession httpSession,
			HttpServletRequest request) {

		System.out.println("Recieved Data: " + data);
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			PsychologicalAssessmentAsLaidDown psychologicalAssessmentAsLaidDown = objectMapper
					.convertValue(data.get("data"), PsychologicalAssessmentAsLaidDown.class);
			System.out.println("Psychology Object: " + psychologicalAssessmentAsLaidDown);

			String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

			// ===============================Psychiatric Illness
			// History=====================================================

			if (psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory().isBlank()
					|| psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory().isEmpty()) {
				errors.put("psychiatricIllnessHistory", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory().isBlank()
					|| !psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory().isEmpty()) {

				if (!RegexPatternHanlder
						.isValidString(psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory())) {
					errors.put("psychiatricIllnessHistory", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			// ===============================wrongDecisionPublicCastigation================

			if (psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation().isBlank()
					|| psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation().isEmpty()) {
				errors.put("wrongDecisionPublicCastigation", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation().isBlank()
					|| !psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation().isEmpty()) {

				if (!RegexPatternHanlder
						.isValidString(psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation())) {
					errors.put("wrongDecisionPublicCastigation", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			// ===============================alcholicDrugAbuseHistory======================

			if (psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history().isBlank()
					|| psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history().isEmpty()) {
				errors.put("alcholicDrugAbuseHistory", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history().isBlank()
					|| !psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history().isEmpty()) {

				if (!RegexPatternHanlder
						.isValidString(psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history())) {
					errors.put("alcholicDrugAbuseHistory", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			// ===============================injuryInfectiveMetabolicEncephalopathyHistory=====================================================

			if (psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history().isBlank()
					|| psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history()
							.isEmpty()) {
				errors.put("injuryInfectiveMetabolicEncephalopathyHistory", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history().isBlank()
					|| !psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history()
							.isEmpty()) {

				if (!RegexPatternHanlder.isValidString(
						psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history())) {
					errors.put("injuryInfectiveMetabolicEncephalopathyHistory",
							RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}
			// ===============================objectivePsychometricScale=====================================================

			if (psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale().isBlank()
					|| psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale().isEmpty()) {

				errors.put("objectivePsychometricScale", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale().isBlank()
					|| !psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale().isEmpty()) {

				if (!RegexPatternHanlder
						.isValidString(psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale())) {
					errors.put("objectivePsychometricScale", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

				}
			}

			//-----------------------------------------------------Categorization-----------------------------------------------------//
			if (!psychologicalAssessmentAsLaidDown.getCategory().isBlank() || !psychologicalAssessmentAsLaidDown.getCategory().isEmpty()) {
				if (!psychologicalAssessmentAsLaidDown.getCategory().equals("S-1")) {

					if (!String.valueOf(psychologicalAssessmentAsLaidDown.getType()).isBlank()
							|| !String.valueOf(psychologicalAssessmentAsLaidDown.getType()).isEmpty()) {

						if (!psychologicalAssessmentAsLaidDown.getDuration().isBlank() || !psychologicalAssessmentAsLaidDown.getDuration().isEmpty()) {

							if (!RegexPatternHanlder.isValidString(String.valueOf(psychologicalAssessmentAsLaidDown.getType()))) {
								errors.put("pTypeId", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
							}
							if (!RegexPatternHanlder.isValidString(psychologicalAssessmentAsLaidDown.getDuration())) {
								errors.put("pDurationId", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

							}

							if (psychologicalAssessmentAsLaidDown.getType().equals("Permanent")) {
								if (!psychologicalAssessmentAsLaidDown.getDuration().equals("P-2")) {
									errors.put("pDurationId", "Please select valid duration!");
								}
							}
							if (psychologicalAssessmentAsLaidDown.getType().equals("Temporary")) {
								if (psychologicalAssessmentAsLaidDown.getDuration().equals("P-2")) {
									errors.put("pDurationId", "Please select valid duration!");
								}
							}

						}
					}

					if (psychologicalAssessmentAsLaidDown.getType().isBlank() || psychologicalAssessmentAsLaidDown.getType().isEmpty() ) {
						errors.put("pTypeId", RegexValidation.EMPTY_FIELD_MESSAGE);
					}

					if (psychologicalAssessmentAsLaidDown.getDuration().isBlank() || psychologicalAssessmentAsLaidDown.getDuration().isEmpty()
							|| psychologicalAssessmentAsLaidDown.getDuration() == null) {
						errors.put("pDurationId", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
				}
			}
			else if(!psychologicalAssessmentAsLaidDown.getCategory().isBlank() || !psychologicalAssessmentAsLaidDown.getCategory().isEmpty()) {
				if (psychologicalAssessmentAsLaidDown.getCategory().equals("S-1")) {
					if (!psychologicalAssessmentAsLaidDown.getType().isBlank() || !psychologicalAssessmentAsLaidDown.getType().isEmpty() ) {
						errors.put("pTypeId", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}

					if (!psychologicalAssessmentAsLaidDown.getDuration().isBlank() || !psychologicalAssessmentAsLaidDown.getDuration().isEmpty()) {
						errors.put("pDurationId", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}
				}
			}
			else {
				errors.put("pCat", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
				

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				Optional<PsychologicalAssessmentAsLaidDown> optional = assesmentPart1Service
						.assessmentAsLaidDown(psychologicalAssessmentAsLaidDown.getAmeId());
				if (optional.get().getAmeId() != null) {

					psychologicalAssessmentAsLaidDown.setLastModifiedOn(Calendar.getInstance().getTime());
					psychologicalAssessmentAsLaidDown.setLastModifiedBy(loggedInUserForcepersonnelId);
					psychologicalAssessmentAsLaidDown
							.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean result = assesmentPart1Service
							.saveAndUpdatePsychologicalAssessment(psychologicalAssessmentAsLaidDown);
					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					return ResponseEntity.ok(response);

					// logger.info("Result>>>>>>>>>>>"+result);

				} else {

					psychologicalAssessmentAsLaidDown.setLastModifiedOn(Calendar.getInstance().getTime());
					psychologicalAssessmentAsLaidDown.setLastModifiedBy(loggedInUserForcepersonnelId);
					psychologicalAssessmentAsLaidDown
							.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					Boolean resBoolean = assesmentPart1Service
							.saveAndUpdatePsychologicalAssessment(psychologicalAssessmentAsLaidDown);
					response.put("isValid", true);
					response.put("message", "Data saved successfully!");
					return ResponseEntity.ok(response);
					// logger.info("resBoolean >>>>>>>>>>> "+resBoolean);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");

		}

	}

//	=============================================Hearing Validation, Save and Update=============================================================
	@PostMapping("validate-and-save-hearing")
	@ResponseBody
	public ResponseEntity<?> validateHearing(@RequestBody Map<String, Object> data, HttpSession httpSession,
			HttpServletRequest request) {

		System.out.println("Recieved Data: " + data);

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Hearing hearing = objectMapper.convertValue(data.get("data"), Hearing.class);
			String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

			if (hearing.getNormal_in_both_ears().isBlank() || hearing.getNormal_in_both_ears().isEmpty()
					|| hearing.getNormal_in_both_ears() == null) {

				errors.put("normalInBothEars", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!String.valueOf(hearing.getNormal_in_both_ears()).isBlank()
					|| !String.valueOf(hearing.getNormal_in_both_ears()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(hearing.getNormal_in_both_ears()))) {
					{
						errors.put("normalInBothEars", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (hearing.getAuroscopy().isBlank() || hearing.getAuroscopy().isEmpty()
					|| hearing.getAuroscopy() == null) {

				errors.put("auroscopy", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!String.valueOf(hearing.getAuroscopy()).isBlank()
					|| !String.valueOf(hearing.getAuroscopy()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(hearing.getAuroscopy()))) {
					{
						errors.put("auroscopy", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (hearing.getModerate_defect_in_one_ear().isBlank() || hearing.getModerate_defect_in_one_ear().isEmpty()
					|| hearing.getModerate_defect_in_one_ear() == null) {

				errors.put("moderateDefectInOneEar", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!String.valueOf(hearing.getModerate_defect_in_one_ear()).isBlank()
					|| !String.valueOf(hearing.getModerate_defect_in_one_ear()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(hearing.getModerate_defect_in_one_ear()))) {
					{
						errors.put("moderateDefectInOneEar", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (hearing.getRennie_test().isBlank() || hearing.getRennie_test().isEmpty()
					|| hearing.getRennie_test() == null) {

				errors.put("rennieTest", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (hearing.getPartial_defect_in_both_ears().isBlank() || hearing.getPartial_defect_in_both_ears().isEmpty()
					|| hearing.getPartial_defect_in_both_ears() == null) {

				errors.put("partialDefectInBothEars", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!String.valueOf(hearing.getPartial_defect_in_both_ears()).isBlank()
					|| !String.valueOf(hearing.getPartial_defect_in_both_ears()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(hearing.getPartial_defect_in_both_ears()))) {
					{
						errors.put("partialDefectInBothEars", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (hearing.getWeber_test().isBlank() || hearing.getWeber_test().isEmpty()
					|| hearing.getWeber_test() == null) {

				errors.put("weberTest", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!String.valueOf(hearing.getWeber_test()).isBlank()
					|| !String.valueOf(hearing.getWeber_test()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(hearing.getWeber_test()))) {
					{
						errors.put("weberTest", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (hearing.getAudiometry().isBlank() || hearing.getAudiometry().isEmpty()
					|| hearing.getAudiometry() == null) {

				errors.put("audiometry", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!String.valueOf(hearing.getAudiometry()).isBlank()
					|| !String.valueOf(hearing.getAudiometry()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(hearing.getAudiometry()))) {
					{
						errors.put("audiometry", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (!hearing.getAny_other_combination().isBlank() || !hearing.getAny_other_combination().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(hearing.getAny_other_combination())) {
					errors.put("anyOtherCombination", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}
//-----------------------------------------------------Categorization-----------------------------------------------------//
			if (!hearing.gethNo().isBlank() || !hearing.gethNo().isEmpty()) {
				if (!hearing.gethNo().equals("H-1")) {

					if (!String.valueOf(hearing.gethType()).isBlank()
							|| !String.valueOf(hearing.gethType()).isEmpty()) {

						if (!hearing.gethDuration().isBlank() || !hearing.gethDuration().isEmpty()) {

							if (!RegexPatternHanlder.isValidString(String.valueOf(hearing.gethType()))) {
								errors.put("hType", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
							}
							if (!RegexPatternHanlder.isValidString(hearing.gethDuration())) {
								errors.put("hDuration", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

							}

							if (hearing.gethType().equals("Permanent")) {
								if (!hearing.gethDuration().equals("P-2")) {
									errors.put("hDuration", "Please select valid duration!");
								}
							}
							if (hearing.gethType().equals("Temporary")) {
								if (hearing.gethDuration().equals("P-2")) {
									errors.put("hDuration", "Please select valid duration!");
								}
							}

						}
					}

					if (hearing.gethType().isBlank() || hearing.gethType().isEmpty() ) {
						errors.put("hType", RegexValidation.EMPTY_FIELD_MESSAGE);
					}

					if (hearing.gethDuration().isBlank() || hearing.gethDuration().isEmpty()
							|| hearing.gethDuration() == null) {
						errors.put("hDuration", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
				}
			}
			else if(!hearing.gethNo().isBlank() || !hearing.gethNo().isEmpty()) {
				if (hearing.gethNo().equals("H-1")) {
					if (!hearing.gethType().isBlank() || !hearing.gethType().isEmpty() ) {
						errors.put("hType", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}

					if (!hearing.gethDuration().isBlank() || !hearing.gethDuration().isEmpty()) {
						errors.put("hDuration", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}
				}
			}
			else {
				errors.put("hNo", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
				

//		-----------------------------------------------------------------------------------------------------------------------   //	

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				Optional<Hearing> optional = assesmentPart1Service.getHearing(hearing.getAmeId());
				if (!optional.isEmpty()) {

					hearing.setLastModifiedOn(Calendar.getInstance().getTime());
					hearing.setLastModifiedBy(loggedInUserForcepersonnelId);
					hearing.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean result = assesmentPart1Service.saveAndUpdateHearing(hearing);
					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					return ResponseEntity.ok(response);

				} else {

					hearing.setLastModifiedOn(Calendar.getInstance().getTime());
					hearing.setLastModifiedBy(loggedInUserForcepersonnelId);
					hearing.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					Boolean resBoolean = assesmentPart1Service.saveAndUpdateHearing(hearing);
					response.put("isValid", true);
					response.put("message", "Data saved successfully!");
					return ResponseEntity.ok(response);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");

		}

	}
//	==================================================Appendages============================================

	@PostMapping("validate-and-save-appendages")
	@ResponseBody
	public ResponseEntity<?> validateAppendages(@RequestBody Map<String, Object> data, HttpSession httpSession,
			HttpServletRequest request) {

		System.out.println("Recieved Data: " + data);
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			ObjectMapper mapper = new ObjectMapper();
			Appendages appendages = mapper.convertValue(data.get("data"), Appendages.class);

			String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

//			===========================================Upper Limb=======================================================================

			if (appendages.getAnyLossInfirmityDetailsUpperRightLimb().isBlank()
					|| appendages.getAnyLossInfirmityDetailsUpperRightLimb().isEmpty()) {
				errors.put("anyLossInfirmityDetailsUpperRightLimb", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!appendages.getAnyLossInfirmityDetailsUpperRightLimb().isBlank()
					|| !appendages.getAnyLossInfirmityDetailsUpperRightLimb().isEmpty()) {

				if (!RegexPatternHanlder.isValidString(appendages.getAnyLossInfirmityDetailsUpperRightLimb())) {
					{
						errors.put("anyLossInfirmityDetailsUpperRightLimb",
								RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (appendages.getAnyLossInfirmityDetailsUpperLeftLimb().isBlank()
					|| appendages.getAnyLossInfirmityDetailsUpperLeftLimb().isEmpty()) {
				errors.put("anyLossInfirmityDetailsUpperLeftLimb", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!appendages.getAnyLossInfirmityDetailsUpperLeftLimb().isBlank()
					|| !appendages.getAnyLossInfirmityDetailsUpperLeftLimb().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(appendages.getAnyLossInfirmityDetailsUpperLeftLimb())) {
					errors.put("anyLossInfirmityDetailsUpperLeftLimb", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			//-----------------------------------------------------Categorization-----------------------------------------------------//
			if (!appendages.getUpperLimbShape().isBlank() || !appendages.getUpperLimbShape().isEmpty()) {
				if (!appendages.getUpperLimbShape().equals("A-1(U)")) {

					if (!String.valueOf(appendages.getUpperLimbType()).isBlank()
							|| !String.valueOf(appendages.getUpperLimbType()).isEmpty()) {

						if (!appendages.getUpperLimbDuration().isBlank() || !appendages.getUpperLimbDuration().isEmpty()) {

							if (!RegexPatternHanlder.isValidString(String.valueOf(appendages.getUpperLimbType()))) {
								errors.put("upperLimbType", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
							}
							if (!RegexPatternHanlder.isValidString(appendages.getUpperLimbDuration())) {
								errors.put("upperLimbDuration", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

							}

							if (appendages.getUpperLimbType().equals("Permanent")) {
								if (!appendages.getUpperLimbDuration().equals("P-2")) {
									errors.put("upperLimbDuration", "Please select valid duration!");
								}
							}
							if (appendages.getUpperLimbType().equals("Temporary")) {
								if (appendages.getUpperLimbDuration().equals("P-2")) {
									errors.put("upperLimbDuration", "Please select valid duration!");
								}
							}

						}
					}

					if (appendages.getUpperLimbType().isBlank() || appendages.getUpperLimbType().isEmpty() ) {
						errors.put("upperLimbType", RegexValidation.EMPTY_FIELD_MESSAGE);
					}

					if (appendages.getUpperLimbDuration().isBlank() || appendages.getUpperLimbDuration().isEmpty()
							) {
						errors.put("upperLimbDuration", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
				}
			}
			else if(!appendages.getUpperLimbShape().isBlank() || !appendages.getUpperLimbShape().isEmpty()) {
				if (appendages.getUpperLimbShape().equals("A-1(U)")) {
					if (!appendages.getUpperLimbType().isBlank() || !appendages.getUpperLimbType().isEmpty() ) {
						errors.put("upperLimbType", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}

					if (!appendages.getUpperLimbDuration().isBlank() || !appendages.getUpperLimbDuration().isEmpty()) {
						errors.put("upperLimbDuration", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}
				}
			}
			else {
				errors.put("upperLimbShape", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
				

//		---------------------------------------------------Lower Limb--------------------------------------------------------------------   //	

	

			if (appendages.getAnyLossInfirmityDetailsLowerRightLimb().isBlank()
					|| appendages.getAnyLossInfirmityDetailsLowerRightLimb().isEmpty()) {
				errors.put("anyLossInfirmityDetailsLowerRightLimb", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!appendages.getAnyLossInfirmityDetailsLowerRightLimb().isBlank()
					|| !appendages.getAnyLossInfirmityDetailsLowerRightLimb().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(appendages.getAnyLossInfirmityDetailsLowerRightLimb())) {
					errors.put("anyLossInfirmityDetailsLowerRightLimb", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (appendages.getAnyLossInfirmityDetailsLowerLeftLimb().isBlank()
					|| appendages.getAnyLossInfirmityDetailsLowerLeftLimb().isEmpty()) {
				errors.put("anyLossInfirmityDetailsLowerLeftLimb", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!appendages.getAnyLossInfirmityDetailsLowerLeftLimb().isBlank()
					|| !appendages.getAnyLossInfirmityDetailsLowerLeftLimb().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(appendages.getAnyLossInfirmityDetailsLowerLeftLimb())) {
					errors.put("anyLossInfirmityDetailsLowerLeftLimb", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}
			//-----------------------------------------------------Categorization-----------------------------------------------------//
			if (!appendages.getLowerLimbShape().isBlank() || !appendages.getLowerLimbShape().isEmpty()) {
				if (!appendages.getLowerLimbShape().equals("A-1(L)")) {

					if (!String.valueOf(appendages.getLowerLimbType()).isBlank()
							|| !String.valueOf(appendages.getLowerLimbType()).isEmpty()) {

						if (!appendages.getLowerLimbDuration().isBlank() || !appendages.getLowerLimbDuration().isEmpty()) {

							if (!RegexPatternHanlder.isValidString(String.valueOf(appendages.getLowerLimbType()))) {
								errors.put("lowerLimbType", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
							}
							if (!RegexPatternHanlder.isValidString(appendages.getLowerLimbDuration())) {
								errors.put("lowerLimbDuration", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

							}

							if (appendages.getLowerLimbType().equals("Permanent")) {
								if (!appendages.getLowerLimbDuration().equals("P-2")) {
									errors.put("lowerLimbDuration", "Please select valid duration!");
								}
							}
							if (appendages.getLowerLimbType().equals("Temporary")) {
								if (appendages.getLowerLimbDuration().equals("P-2")) {
									errors.put("lowerLimbDuration", "Please select valid duration!");
								}
							}

						}
					}

					if (appendages.getLowerLimbType().isBlank() || appendages.getLowerLimbType().isEmpty() ) {
						errors.put("lowerLimbType", RegexValidation.EMPTY_FIELD_MESSAGE);
					}

					if (appendages.getLowerLimbDuration().isBlank() || appendages.getLowerLimbDuration().isEmpty()
							) {
						errors.put("lowerLimbDuration", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
				}
			}
			else if(!appendages.getLowerLimbShape().isBlank() || !appendages.getLowerLimbShape().isEmpty()) {
				if (appendages.getLowerLimbShape().equals("A-1(L)")) {
					if (!appendages.getLowerLimbType().isBlank() || !appendages.getLowerLimbType().isEmpty() ) {
						errors.put("lowerLimbType", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}

					if (!appendages.getLowerLimbDuration().isBlank() || !appendages.getLowerLimbDuration().isEmpty()) {
						errors.put("lowerLimbDuration", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}
				}
			}
			else {
				errors.put("lowerLimbShape", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
				



			// =============================================Spine============================================================================

			if (appendages.getSpine().isBlank() || appendages.getSpine().isEmpty() || appendages.getSpine() == null) {
				errors.put("spine", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!appendages.getSpine().isBlank() || !appendages.getSpine().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(appendages.getSpine())) {
					errors.put("anyLossInfirmityDetailsLowerLeftLimb", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (appendages.getSpineShape().isBlank() || appendages.getSpineShape().isEmpty()
					|| appendages.getSpineShape() == null) {
				errors.put("spineShape", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			//-----------------------------------------------------Categorization-----------------------------------------------------//
			if (!appendages.getSpineShape().isBlank() || !appendages.getSpineShape().isEmpty()) {
				if (!appendages.getSpineShape().equals("A-1(S)")) {

					if (!String.valueOf(appendages.getSpineType()).isBlank()
							|| !String.valueOf(appendages.getSpineType()).isEmpty()) {

						if (!appendages.getSpineDuration().isBlank() || !appendages.getSpineDuration().isEmpty()) {

							if (!RegexPatternHanlder.isValidString(String.valueOf(appendages.getSpineType()))) {
								errors.put("spineType", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
							}
							if (!RegexPatternHanlder.isValidString(appendages.getSpineDuration())) {
								errors.put("spineDuration", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

							}

							if (appendages.getSpineType().equals("Permanent")) {
								if (!appendages.getSpineDuration().equals("P-2")) {
									errors.put("spineDuration", "Please select valid duration!");
								}
							}
							if (appendages.getSpineType().equals("Temporary")) {
								if (appendages.getSpineDuration().equals("P-2")) {
									errors.put("spineDuration", "Please select valid duration!");
								}
							}

						}
					}

					if (appendages.getSpineType().isBlank() || appendages.getSpineType().isEmpty() ) {
						errors.put("spineType", RegexValidation.EMPTY_FIELD_MESSAGE);
					}

					if (appendages.getSpineDuration().isBlank() || appendages.getSpineDuration().isEmpty()
							) {
						errors.put("spineDuration", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
				}
			}
			else if(!appendages.getSpineShape().isBlank() || !appendages.getSpineShape().isEmpty()) {
				if (appendages.getSpineShape().equals("A-1(S)")) {
					if (!appendages.getSpineType().isBlank() || !appendages.getSpineType().isEmpty() ) {
						errors.put("spineType", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}

					if (!appendages.getSpineDuration().isBlank() || !appendages.getSpineDuration().isEmpty()) {
						errors.put("spineDuration", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}
				}
			}
			else {
				errors.put("spineShape", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
				




			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				Optional<Appendages> optional = assesmentPart1Service.getAppendages(appendages.getAmeId());
				if (!optional.isEmpty()) {
					appendages.setLastModifiedOn(Calendar.getInstance().getTime());
					appendages.setLastModifiedBy(loggedInUserForcepersonnelId);
					appendages.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					Boolean result = assesmentPart1Service.saveAndUpdateAppendages(appendages);
					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					return ResponseEntity.ok(response);

				} else {

					appendages.setLastModifiedOn(Calendar.getInstance().getTime());
					appendages.setLastModifiedBy(loggedInUserForcepersonnelId);
					appendages.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					Boolean resBoolean = assesmentPart1Service.saveAndUpdateAppendages(appendages);
					response.put("isValid", true);
					response.put("message", "Data saved successfully!");
					return ResponseEntity.ok(response);
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");

		}

	}

//=================================================== Gynaecology =================================================================	
	@PostMapping("validate-and-save-gynaecology")
	@ResponseBody
	public ResponseEntity<?> validateGynaecology(@RequestBody String data, HttpSession httpSession,
			HttpServletRequest request) {
		String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

		System.out.println("Recieved Data: " + data);
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			ObjectMapper mapper = new ObjectMapper();
			GynaeAndObsFemale gynaeAndObsFemale = mapper.readValue(data, GynaeAndObsFemale.class);
			System.out.println(gynaeAndObsFemale);
			Date lastModifiedDate = Date.valueOf(LocalDate.now());
			LocalDate currentDate = LocalDate.now();
			// Date inputDate = gynaeAndObsFemale.getDateOfLastConfinement();
			// Date currentDate = new Date();

			// System.out.println("input date: " + inputDate + "\n" + "current Date: " +
			// currentDate);

			if (gynaeAndObsFemale.getLmp() == null) {
				errors.put("lmp", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (gynaeAndObsFemale.getMenstrualHistory().isBlank()
					|| gynaeAndObsFemale.getMenstrualHistory().isEmpty()) {
				errors.put("menstrualHistory", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!gynaeAndObsFemale.getMenstrualHistory().isBlank()
					|| !gynaeAndObsFemale.getMenstrualHistory().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getMenstrualHistory())) {
					errors.put("menstrualHistory", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (gynaeAndObsFemale.getVaginalDischarge().isEmpty()
					|| gynaeAndObsFemale.getVaginalDischarge().isBlank()) {
				errors.put("vaginalDischarge", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!gynaeAndObsFemale.getVaginalDischarge().isEmpty()
					|| !gynaeAndObsFemale.getVaginalDischarge().isBlank()) {
				if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getVaginalDischarge())) {
					errors.put("vaginalDischarge", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (gynaeAndObsFemale.getUvProlapse().isBlank() || gynaeAndObsFemale.getUvProlapse().isBlank()) {
				errors.put("uvProlapse", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!gynaeAndObsFemale.getUvProlapse().isBlank() || !gynaeAndObsFemale.getUvProlapse().isBlank()) {

				if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getUvProlapse())) {
					errors.put("uvProlapse", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (gynaeAndObsFemale.getUsgAbdomen().isBlank() || gynaeAndObsFemale.getUsgAbdomen().isEmpty()) {
				errors.put("usgAbdomen", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!gynaeAndObsFemale.getUsgAbdomen().isBlank() || !gynaeAndObsFemale.getUsgAbdomen().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getUsgAbdomen())) {
					errors.put("usgAbdomen", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (gynaeAndObsFemale.getOtherAilment().isBlank() || gynaeAndObsFemale.getOtherAilment().isEmpty()) {
				errors.put("otherAilment", RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!gynaeAndObsFemale.getOtherAilment().isBlank() || !gynaeAndObsFemale.getOtherAilment().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getOtherAilment())) {
					errors.put("otherAilment", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (gynaeAndObsFemale.getObstetricsHistory().isBlank()
					|| gynaeAndObsFemale.getObstetricsHistory().isEmpty()) {
				errors.put("obstetricsHistory", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!gynaeAndObsFemale.getObstetricsHistory().isBlank()
					|| !gynaeAndObsFemale.getObstetricsHistory().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getObstetricsHistory())) {
					errors.put("obstetricsHistory", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			/*
			 * if (gynaeAndObsFemale.getDateOfLastConfinement() == null) {
			 * errors.put("dateOfLastConfinement", RegexValidation.EMPTY_FIELD_MESSAGE); }
			 * 
			 * else { LocalDate inputDate =
			 * gynaeAndObsFemale.getDateOfLastConfinement().toLocalDate();
			 * 
			 * if (inputDate.isAfter(currentDate)) {
			 * 
			 * errors.put("dateOfLastConfinement", "* date should be in past or present"); }
			 * }
			 */

			//-----------------------------------------------------Categorization-----------------------------------------------------//
			if (!gynaeAndObsFemale.getgFactorCategory().isBlank() || !gynaeAndObsFemale.getgFactorCategory().isEmpty()) {
				if (!gynaeAndObsFemale.getgFactorCategory().equals("G-1")) {

					if (!String.valueOf(gynaeAndObsFemale.getgFactorType()).isBlank()
							|| !String.valueOf(gynaeAndObsFemale.getgFactorType()).isEmpty()) {

						if (!gynaeAndObsFemale.getgFactorDuration().isBlank() || !gynaeAndObsFemale.getgFactorDuration().isEmpty()) {

							if (!RegexPatternHanlder.isValidString(String.valueOf(gynaeAndObsFemale.getgFactorType()))) {
								errors.put("gFactorType", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
							}
							if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getgFactorDuration())) {
								errors.put("gFactorDuration", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

							}

							if (gynaeAndObsFemale.getgFactorType().equals("Permanent")) {
								if (!gynaeAndObsFemale.getgFactorDuration().equals("P-2")) {
									errors.put("gFactorDuration", "Please select valid duration!");
								}
							}
							if (gynaeAndObsFemale.getgFactorType().equals("Temporary")) {
								if (gynaeAndObsFemale.getgFactorDuration().equals("P-2")) {
									errors.put("gFactorDuration", "Please select valid duration!");
								}
							}

						}
					}

					if (gynaeAndObsFemale.getgFactorType().isBlank() || gynaeAndObsFemale.getgFactorType().isEmpty() ) {
						errors.put("gFactorType", RegexValidation.EMPTY_FIELD_MESSAGE);
					}

					if (gynaeAndObsFemale.getgFactorDuration().isBlank() || gynaeAndObsFemale.getgFactorDuration().isEmpty()
							|| gynaeAndObsFemale.getgFactorDuration() == null) {
						errors.put("gFactorDuration", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
				}
			}
			else if(!gynaeAndObsFemale.getgFactorCategory().isBlank() || !gynaeAndObsFemale.getgFactorCategory().isEmpty()) {
				if (gynaeAndObsFemale.getgFactorCategory().equals("G-1")) {
					if (!gynaeAndObsFemale.getgFactorType().isBlank() || !gynaeAndObsFemale.getgFactorType().isEmpty() ) {
						errors.put("gFactorType", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}

					if (!gynaeAndObsFemale.getgFactorDuration().isBlank() || !gynaeAndObsFemale.getgFactorDuration().isEmpty()) {
						errors.put("gFactorDuration", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}
				}
			}
			else {
				errors.put("gFactorShape", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
				

//		-----------------------------------------------------------------------------------------------------------------------   //	
			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				Optional<GynaeAndObsFemale> optional = ameAssessmentServicePart_2
						.gynaeByAmeId(gynaeAndObsFemale.getAmeId());

				if (optional.isEmpty()) {

					gynaeAndObsFemale.setLastModifiedOn(lastModifiedDate);
					gynaeAndObsFemale.setLastModifiedBy(loggedInUserForcepersonnelId);
					gynaeAndObsFemale.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					Boolean resultSave = ameAssessmentServicePart_2.saveAndUpdateGynaeAndObsFemale(gynaeAndObsFemale);
					response.put("isValid", true);
					response.put("message", "Data saved successfully!");
					return ResponseEntity.ok(response);
					// logger.info(">>>>>>>>>>> saveGynaeAndObsFemale >>>>>>>"+reBoolean);
				} else {

					gynaeAndObsFemale.setLastModifiedOn(lastModifiedDate);
					gynaeAndObsFemale.setLastModifiedBy(loggedInUserForcepersonnelId);
					gynaeAndObsFemale.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean resultUpdate = ameAssessmentServicePart_2.saveAndUpdateGynaeAndObsFemale(gynaeAndObsFemale);
					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					return ResponseEntity.ok(response);
					// logger.info("Result......"+result);
				}
				// response.put("isValid", true);
				// return ResponseEntity.ok(response);
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");
		}

		// return null;

	}

//	==========================================Physical General Examination==========================================================
	@PostMapping("validate-and-save-physical-general-examination")
	@ResponseBody
	public ResponseEntity<?> validatePhysicalGeneralExamination(@RequestBody Map<String, Object> data,
			HttpSession httpSession, HttpServletRequest request) {

		System.out.println("Recieved Data: " + data);
		String digitOnlyRegex = "[-+]?[0-9]*\\.?[0-9]+";
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

			ObjectMapper mapper = new ObjectMapper();
			GeneralExamination generalExamination = mapper.convertValue(data.get("data"), GeneralExamination.class);
			String gender = (String) data.get("gender");
			System.out.println(generalExamination);
			System.out.println(String.valueOf(generalExamination.getDistanceCoveredInMin() + " "
					+ String.valueOf(generalExamination.getDistanceCoveredInMin()).getClass().getSimpleName()));

			if (String.valueOf(generalExamination.getDistanceCoveredInMin()).isBlank()
					|| String.valueOf(generalExamination.getDistanceCoveredInMin()).isEmpty()) {
				errors.put("distanceCoveredInMin", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getDistanceCoveredInMin()).isBlank()
					|| !String.valueOf(generalExamination.getDistanceCoveredInMin()).isEmpty()) {
				if (!String.valueOf(generalExamination.getDistanceCoveredInMin()).matches(digitOnlyRegex)
						|| generalExamination.getDistanceCoveredInMin() <= 0) {
					errors.put("distanceCoveredInMin", "* Only digits are allowed");
				}
			}
			if (gender.equalsIgnoreCase("Female")) {

				if (generalExamination.getBreastExamination().isBlank()
						|| generalExamination.getBreastExamination().isEmpty()) {
					errors.put("breastExamination", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (!generalExamination.getBreastExamination().isBlank()
						|| !generalExamination.getBreastExamination().isEmpty()) {
					if (!RegexPatternHanlder.isValidString(generalExamination.getBreastExamination())) {
						errors.put("breastExamination", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}
			if (generalExamination.getBodyBuild().isBlank() || generalExamination.getBodyBuild().isEmpty()
					|| generalExamination.getBodyBuild() == null) {
				errors.put("bodyBuild", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (String.valueOf(generalExamination.getBpSystolic()).isBlank()
					|| String.valueOf(generalExamination.getBpSystolic()).isEmpty()
					|| generalExamination.getBpSystolic() == null) {
				errors.put("bpSystolic", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getBpSystolic()).isBlank()
					|| !String.valueOf(generalExamination.getBpSystolic()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getBpSystolic()))
						|| String.valueOf(generalExamination.getBpSystolic()) == null) {
					errors.put("bpSystolic", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (String.valueOf(generalExamination.getBpDiastolic()).isBlank()
					|| String.valueOf(generalExamination.getBpDiastolic()).isEmpty()
					|| generalExamination.getBpDiastolic() == null) {
				errors.put("bpDiastolic", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getBpDiastolic()).isBlank()
					|| !String.valueOf(generalExamination.getBpDiastolic()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getBpDiastolic()))) {
					{
						errors.put("bpDiastolic", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getTounge().isBlank() || generalExamination.getTounge().isEmpty()

					|| generalExamination.getTounge() == null) {
				errors.put("tounge", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getTounge()).isBlank()
					|| !String.valueOf(generalExamination.getTounge()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getTounge()))) {
					{
						errors.put("tounge", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getPulseRate().isBlank() || generalExamination.getPulseRate().isEmpty()

					|| generalExamination.getPulseRate() == null) {
				errors.put("pulseRate", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getPulseRate()).isBlank()
					|| !String.valueOf(generalExamination.getPulseRate()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getPulseRate()))) {
					{
						errors.put("pulseRate", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getAnaemia().isBlank() || generalExamination.getAnaemia().isEmpty()

					|| generalExamination.getAnaemia() == null) {
				errors.put("anaemia", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getAnaemia()).isBlank()
					|| !String.valueOf(generalExamination.getAnaemia()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getAnaemia()))) {
					{
						errors.put("anaemia", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getTemperature().isBlank() || generalExamination.getTemperature().isEmpty()
					|| generalExamination.getTemperature() == null) {
				errors.put("temperature", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getTemperature()).isBlank()
					|| !String.valueOf(generalExamination.getTemperature()).isEmpty()) {
				if (!String.valueOf(generalExamination.getTemperature()).matches(digitOnlyRegex)) {
					{
						errors.put("temperature", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getCyanosis().isBlank() || generalExamination.getCyanosis().isEmpty()

					|| generalExamination.getCyanosis() == null) {
				errors.put("cyanosis", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getCyanosis()).isBlank()
					|| !String.valueOf(generalExamination.getCyanosis()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getCyanosis()))) {
					{
						errors.put("cyanosis", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getIcterus().isBlank() || generalExamination.getIcterus().isEmpty()

					|| generalExamination.getIcterus() == null) {
				errors.put("icterus", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getIcterus()).isBlank()
					|| !String.valueOf(generalExamination.getIcterus()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getIcterus()))) {
					{
						errors.put("icterus", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getRespiration().isBlank() || generalExamination.getRespiration().isEmpty()

					|| generalExamination.getRespiration() == null) {
				errors.put("respiration", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getRespiration()).isBlank()
					|| !String.valueOf(generalExamination.getRespiration()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getRespiration()))) {
					{
						errors.put("respiration", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getOedema().isBlank() || generalExamination.getOedema().isEmpty()

					|| generalExamination.getOedema() == null) {
				errors.put("oedema", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getOedema()).isBlank()
					|| !String.valueOf(generalExamination.getOedema()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getOedema()))) {
					{
						errors.put("oedema", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getClubbing().isBlank() || generalExamination.getClubbing().isEmpty()
					|| generalExamination.getClubbing().matches(digitOnlyRegex)
					|| generalExamination.getClubbing() == null) {
				errors.put("clubbing", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getClubbing()).isBlank()
					|| !String.valueOf(generalExamination.getClubbing()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getClubbing()))) {
					{
						errors.put("clubbing", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getKoilonychia().isBlank() || generalExamination.getKoilonychia().isEmpty()
					|| generalExamination.getKoilonychia() == null) {
				errors.put("koilonychia", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getKoilonychia()).isBlank()
					|| !String.valueOf(generalExamination.getKoilonychia()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getKoilonychia()))) {
					{
						errors.put("koilonychia", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getLymphGlandsPalpable().isBlank()
					|| generalExamination.getLymphGlandsPalpable().isEmpty()
					|| generalExamination.getLymphGlandsPalpable() == null) {
				errors.put("lymphGlandsPalpable", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getLymphGlandsPalpable()).isBlank()
					|| !String.valueOf(generalExamination.getLymphGlandsPalpable()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getLymphGlandsPalpable()))) {
					{
						errors.put("lymphGlandsPalpable", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getTonsils().isBlank() || generalExamination.getTonsils().isEmpty()
					|| generalExamination.getTonsils() == null) {
				errors.put("tonsils", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getTonsils()).isBlank()
					|| !String.valueOf(generalExamination.getTonsils()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getTonsils()))) {
					{
						errors.put("tonsils", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getJvp().isBlank() || generalExamination.getJvp().isEmpty()
					|| generalExamination.getJvp() == null) {
				errors.put("jvp", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getJvp()).isBlank()
					|| !String.valueOf(generalExamination.getJvp()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getJvp()))) {
					{
						errors.put("jvp", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getTeethDenture().isBlank() || generalExamination.getTeethDenture().isEmpty()
					|| generalExamination.getTeethDenture() == null) {
				errors.put("teethDenture", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getTeethDenture()).isBlank()
					|| !String.valueOf(generalExamination.getTeethDenture()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getTeethDenture()))) {
					{
						errors.put("teethDenture", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getThyroid().isBlank() || generalExamination.getThyroid().isEmpty()
					|| generalExamination.getThyroid() == null) {
				errors.put("thyroid", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getThyroid()).isBlank()
					|| !String.valueOf(generalExamination.getThyroid()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getThyroid()))) {
					{
						errors.put("thyroid", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getThroat().isBlank() || generalExamination.getThroat().isEmpty()
					|| generalExamination.getThroat() == null) {
				errors.put("throat", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getThroat()).isBlank()
					|| !String.valueOf(generalExamination.getThroat()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getThroat()))) {
					{
						errors.put("throat", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getSpleen().isBlank() || generalExamination.getSpleen().isEmpty()
					|| generalExamination.getSpleen() == null) {
				errors.put("spleen", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getSpleen()).isBlank()
					|| !String.valueOf(generalExamination.getSpleen()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getSpleen()))) {
					{
						errors.put("spleen", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getLiver().isBlank() || generalExamination.getLiver().isEmpty()
					|| generalExamination.getLiver() == null) {
				errors.put("liver", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getLiver()).isBlank()
					|| !String.valueOf(generalExamination.getLiver()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getLiver()))) {
					{
						errors.put("liver", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getS1().isBlank() || generalExamination.getS1().isEmpty()
					|| generalExamination.getS1() == null) {
				errors.put("s1", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getS1()).isBlank()
					|| !String.valueOf(generalExamination.getS1()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getS1()))) {
					{
						errors.put("s1", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (generalExamination.getS2().isBlank() || generalExamination.getS2().isEmpty()
					|| generalExamination.getS2() == null) {
				errors.put("s2", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!String.valueOf(generalExamination.getS2()).isBlank()
					|| !String.valueOf(generalExamination.getS2()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(generalExamination.getS2()))) {
					{
						errors.put("s2", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				Optional<GeneralExamination> optional = ameAssessmentServicePart_2
						.getGeneralExaminationByAmeId(generalExamination.getAmeId());

				if (!optional.isEmpty()) {

					generalExamination.setLastModifiedOn(Calendar.getInstance().getTime());
					generalExamination.setLastModifiedBy(loggedInUserForcepersonnelId);
					generalExamination.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					Boolean result = ameAssessmentServicePart_2.saveAndUpdateGeneralExamination(generalExamination);

					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					return ResponseEntity.ok(response);

				} else {

					generalExamination.setLastModifiedOn(Calendar.getInstance().getTime());
					generalExamination.setLastModifiedBy(loggedInUserForcepersonnelId);
					generalExamination.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					Boolean resBoolean = ameAssessmentServicePart_2.saveAndUpdateGeneralExamination(generalExamination);
					response.put("isValid", true);
					response.put("message", "Data saved successfully!");
					return ResponseEntity.ok(response);

				}

			}

		}

		catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error Occured! Please try again, if the error persists contact the developement team!");
		}

	}

//===================================================CNS===========================================================================	

	@PostMapping("validate-cns-and-cranial-nerves")
	@ResponseBody
	public ResponseEntity<?> validateCns(@RequestBody Map<String, Object> data) {

		String specialCharacterRegex = "^(?!.*--)(?!.*\\s{2})(?!.*[^a-zA-Z0-9\\s-])(?!.*[^-]*-.*-)[a-zA-Z0-9]+([\\s-][a-zA-Z0-9]+)*$";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			CentralNervousSystem centralNervousSystem = objectMapper.convertValue(data.get("cnsData"),
					CentralNervousSystem.class);
			CranialNervesMeningealSign cranialNervesMeningealSign = objectMapper
					.convertValue(data.get("cranialNervesData"), CranialNervesMeningealSign.class);

			String ameId = centralNervousSystem.getAmeId();

			if (centralNervousSystem.getMemoryRecentRemote().isBlank()
					|| centralNervousSystem.getMemoryRecentRemote().isEmpty()
					|| !centralNervousSystem.getMemoryRecentRemote().matches(specialCharacterRegex)) {
				errors.put("memoryRecentRemote", "* Invalid input!");
			}

			if (centralNervousSystem.getMemoryRecentRemote().isBlank()
					|| centralNervousSystem.getMemoryRecentRemote().isEmpty()
					|| !centralNervousSystem.getMemoryRecentRemote().matches(specialCharacterRegex)) {
				errors.put("memoryRecentRemote", "* Invalid input!");
			}
			if (centralNervousSystem.getIntelligence().isBlank() || centralNervousSystem.getIntelligence().isEmpty()
					|| !centralNervousSystem.getIntelligence().matches(specialCharacterRegex)) {
				errors.put("intelligence", "* Invalid input!");
			}
			if (centralNervousSystem.getPersonality().isBlank() || centralNervousSystem.getPersonality().isEmpty()
					|| !centralNervousSystem.getPersonality().matches(specialCharacterRegex)) {
				errors.put("personality", "* Invalid input!");
			}
			if (centralNervousSystem.getOrientation().isBlank() || centralNervousSystem.getOrientation().isEmpty()
					|| !centralNervousSystem.getOrientation().matches(specialCharacterRegex)) {
				errors.put("orientation", "* Invalid input!");
			}

			// ============================Cranial
			// Validation==========================================//
			if (cranialNervesMeningealSign.getCranialNerves().isBlank()
					|| cranialNervesMeningealSign.getCranialNerves().isEmpty()
					|| !cranialNervesMeningealSign.getCranialNerves().matches(specialCharacterRegex)) {
				errors.put("cranialNerves", "* Invalid input!");
			}

			if (!cranialNervesMeningealSign.getMeningealSignIfAny().isBlank()
					|| !cranialNervesMeningealSign.getMeningealSignIfAny().isEmpty()) {
				if (!cranialNervesMeningealSign.getMeningealSignIfAny().matches(specialCharacterRegex)) {
					errors.put("meningealSignIfAny", "* Invalid input!");
				}
			}

			if (cranialNervesMeningealSign.getNutritionOfMuscles().isBlank()
					|| cranialNervesMeningealSign.getNutritionOfMuscles().isEmpty()
					|| !cranialNervesMeningealSign.getNutritionOfMuscles().matches(specialCharacterRegex)) {
				errors.put("nutritionOfMuscles", "* Invalid input!");
			}

			if (cranialNervesMeningealSign.getWasting().isBlank() || cranialNervesMeningealSign.getWasting().isEmpty()
					|| !cranialNervesMeningealSign.getWasting().matches(specialCharacterRegex)) {
				errors.put("wasting", "* Invalid input!");
			}
			if (cranialNervesMeningealSign.getTone().isBlank() || cranialNervesMeningealSign.getTone().isEmpty()
					|| !cranialNervesMeningealSign.getTone().matches(specialCharacterRegex)) {
				errors.put("tone", "* Invalid input!");
			}
			if (cranialNervesMeningealSign.getCoordination().isBlank()
					|| cranialNervesMeningealSign.getCoordination().isEmpty()
					|| !cranialNervesMeningealSign.getCoordination().matches(specialCharacterRegex)) {
				errors.put("coordination", "* Invalid input!");
			}
			if (cranialNervesMeningealSign.getAbnormalMovementFasiculation().isBlank()
					|| cranialNervesMeningealSign.getAbnormalMovementFasiculation().isEmpty()
					|| !cranialNervesMeningealSign.getAbnormalMovementFasiculation().matches(specialCharacterRegex)) {
				errors.put("abnormalMovementFasiculation", "* Invalid input!");
			}
			if (cranialNervesMeningealSign.getPower().isBlank() || cranialNervesMeningealSign.getPower().isEmpty()) {
				errors.put("power", "* Invalid input!");
			}
			if (cranialNervesMeningealSign.getDtr().isBlank() || cranialNervesMeningealSign.getDtr().isEmpty()
					|| !cranialNervesMeningealSign.getDtr().matches(specialCharacterRegex)) {
				errors.put("dtr", "* Invalid input!");
			}

			if (centralNervousSystem.getMemoryRecentRemote().isBlank()
					|| centralNervousSystem.getMemoryRecentRemote().isEmpty()) {
				errors.put("memoryRecentRemote", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!centralNervousSystem.getMemoryRecentRemote().isBlank()
					|| !centralNervousSystem.getMemoryRecentRemote().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(centralNervousSystem.getMemoryRecentRemote()))) {
					{
						errors.put("memoryRecentRemote", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (centralNervousSystem.getIntelligence().isBlank() || centralNervousSystem.getIntelligence().isEmpty()) {
				errors.put("intelligence", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!centralNervousSystem.getIntelligence().isBlank()
					|| !centralNervousSystem.getIntelligence().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(centralNervousSystem.getIntelligence()))) {
					{
						errors.put("intelligence", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (centralNervousSystem.getPersonality().isBlank() || centralNervousSystem.getPersonality().isEmpty()) {
				errors.put("personality", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!centralNervousSystem.getPersonality().isBlank() || !centralNervousSystem.getPersonality().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(centralNervousSystem.getPersonality()))) {
					{
						errors.put("personality", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (centralNervousSystem.getOrientation().isBlank() || centralNervousSystem.getOrientation().isEmpty()) {
				errors.put("orientation", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!centralNervousSystem.getOrientation().isBlank() || !centralNervousSystem.getOrientation().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(centralNervousSystem.getOrientation()))) {
					{
						errors.put("orientation", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			// ============================Cranial
			// Validation==========================================//

			if (cranialNervesMeningealSign.getCranialNerves().isBlank()
					|| cranialNervesMeningealSign.getCranialNerves().isEmpty()) {
				errors.put("cranialNerves", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getCranialNerves().isBlank()
					|| !cranialNervesMeningealSign.getCranialNerves().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(cranialNervesMeningealSign.getCranialNerves()))) {
					{
						errors.put("cranialNerves", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (!cranialNervesMeningealSign.getMeningealSignIfAny().isBlank()
					|| !cranialNervesMeningealSign.getMeningealSignIfAny().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getMeningealSignIfAny())) {
					errors.put("meningealSignIfAny", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getNutritionOfMuscles().isBlank()
					|| cranialNervesMeningealSign.getNutritionOfMuscles().isEmpty()) {
				errors.put("nutritionOfMuscles", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getNutritionOfMuscles().isBlank()
					|| !cranialNervesMeningealSign.getNutritionOfMuscles().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getNutritionOfMuscles())) {
					errors.put("nutritionOfMuscles", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getWasting().isBlank()
					|| cranialNervesMeningealSign.getWasting().isEmpty()) {
				errors.put("wasting", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getWasting().isBlank()
					|| !cranialNervesMeningealSign.getWasting().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getWasting())) {
					errors.put("wasting", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getTone().isBlank() || cranialNervesMeningealSign.getTone().isEmpty()) {
				errors.put("tone", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getTone().isBlank() || !cranialNervesMeningealSign.getTone().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getTone())) {
					errors.put("tone", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getCoordination().isBlank()
					|| cranialNervesMeningealSign.getCoordination().isEmpty()) {
				errors.put("coordination", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getCoordination().isBlank()
					|| !cranialNervesMeningealSign.getCoordination().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getCoordination())) {
					errors.put("coordination", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getAbnormalMovementFasiculation().isBlank()
					|| cranialNervesMeningealSign.getAbnormalMovementFasiculation().isEmpty()) {
				errors.put("abnormalMovementFasiculation", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getAbnormalMovementFasiculation().isBlank()
					|| !cranialNervesMeningealSign.getAbnormalMovementFasiculation().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getAbnormalMovementFasiculation())) {
					errors.put("abnormalMovementFasiculation", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getPower().isBlank() || cranialNervesMeningealSign.getPower().isEmpty()) {
				errors.put("power", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getPower().isBlank() || !cranialNervesMeningealSign.getPower().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getPower())) {
					errors.put("power", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getDtr().isBlank() || cranialNervesMeningealSign.getDtr().isEmpty()) {
				errors.put("dtr", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getNutritionOfMuscles().isBlank()
					|| !cranialNervesMeningealSign.getNutritionOfMuscles().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getNutritionOfMuscles())) {
					errors.put("nutritionOfMuscles", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				response.put("isValid", true);
				return ResponseEntity.ok(response);
			}

		}

		catch (Exception e) {
			/* exception=String.valueOf(e); */
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured!");
		}

	}

	// ========================================validate and save
	// cns=========================================================

	@PostMapping("validate-and-save-cns-cranialNerves-reflexes-and-sensory-systems")
	@ResponseBody
	public ResponseEntity<?> validateAndSaveCnsCranialNervesReflexesAndSensorySystems(
			@RequestBody Map<String, Object> data, HttpSession httpSession, HttpServletRequest request) {

		String specialCharacterRegex = "^(?!.*--)(?!.*\\s{2})(?!.*[^a-zA-Z0-9\\s-])(?!.*[^-]*-.*-)[a-zA-Z0-9]+([\\s-][a-zA-Z0-9]+)*$";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

			ObjectMapper objectMapper = new ObjectMapper();
			CentralNervousSystem centralNervousSystem = objectMapper.convertValue(data.get("cnsData"),
					CentralNervousSystem.class);
			CranialNervesMeningealSign cranialNervesMeningealSign = objectMapper
					.convertValue(data.get("cranialNervesData"), CranialNervesMeningealSign.class);
			Reflexes reflexes = objectMapper.convertValue(data.get("reflexesData"), Reflexes.class);
			SensorySystem sensorySystem = objectMapper.convertValue(data.get("sensorySystemsData"),
					SensorySystem.class);

			String ameId = centralNervousSystem.getAmeId();

			if (centralNervousSystem.getMemoryRecentRemote().isBlank()
					|| centralNervousSystem.getMemoryRecentRemote().isEmpty()) {
				errors.put("memoryRecentRemote", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!centralNervousSystem.getMemoryRecentRemote().isBlank()
					|| !centralNervousSystem.getMemoryRecentRemote().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(centralNervousSystem.getMemoryRecentRemote()))) {
					{
						errors.put("memoryRecentRemote", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (centralNervousSystem.getIntelligence().isBlank() || centralNervousSystem.getIntelligence().isEmpty()) {
				errors.put("intelligence", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!centralNervousSystem.getIntelligence().isBlank()
					|| !centralNervousSystem.getIntelligence().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(centralNervousSystem.getIntelligence()))) {
					{
						errors.put("intelligence", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (centralNervousSystem.getPersonality().isBlank() || centralNervousSystem.getPersonality().isEmpty()) {
				errors.put("personality", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!centralNervousSystem.getPersonality().isBlank() || !centralNervousSystem.getPersonality().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(centralNervousSystem.getPersonality()))) {
					{
						errors.put("personality", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (centralNervousSystem.getOrientation().isBlank() || centralNervousSystem.getOrientation().isEmpty()) {
				errors.put("orientation", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!centralNervousSystem.getOrientation().isBlank() || !centralNervousSystem.getOrientation().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(centralNervousSystem.getOrientation()))) {
					{
						errors.put("orientation", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			// ============================Cranial
			// Validation==========================================//

			if (cranialNervesMeningealSign.getCranialNerves().isBlank()
					|| cranialNervesMeningealSign.getCranialNerves().isEmpty()) {
				errors.put("cranialNerves", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getCranialNerves().isBlank()
					|| !cranialNervesMeningealSign.getCranialNerves().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(cranialNervesMeningealSign.getCranialNerves()))) {
					{
						errors.put("cranialNerves", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}

			if (!cranialNervesMeningealSign.getMeningealSignIfAny().isBlank()
					|| !cranialNervesMeningealSign.getMeningealSignIfAny().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getMeningealSignIfAny())) {
					errors.put("meningealSignIfAny", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getNutritionOfMuscles().isBlank()
					|| cranialNervesMeningealSign.getNutritionOfMuscles().isEmpty()) {
				errors.put("nutritionOfMuscles", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getNutritionOfMuscles().isBlank()
					|| !cranialNervesMeningealSign.getNutritionOfMuscles().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getNutritionOfMuscles())) {
					errors.put("nutritionOfMuscles", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getWasting().isBlank()
					|| cranialNervesMeningealSign.getWasting().isEmpty()) {
				errors.put("wasting", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getWasting().isBlank()
					|| !cranialNervesMeningealSign.getWasting().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getWasting())) {
					errors.put("wasting", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getTone().isBlank() || cranialNervesMeningealSign.getTone().isEmpty()) {
				errors.put("tone", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getTone().isBlank() || !cranialNervesMeningealSign.getTone().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getTone())) {
					errors.put("tone", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getCoordination().isBlank()
					|| cranialNervesMeningealSign.getCoordination().isEmpty()) {
				errors.put("coordination", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getCoordination().isBlank()
					|| !cranialNervesMeningealSign.getCoordination().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getCoordination())) {
					errors.put("coordination", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getAbnormalMovementFasiculation().isBlank()
					|| cranialNervesMeningealSign.getAbnormalMovementFasiculation().isEmpty()) {
				errors.put("abnormalMovementFasiculation", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getAbnormalMovementFasiculation().isBlank()
					|| !cranialNervesMeningealSign.getAbnormalMovementFasiculation().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getAbnormalMovementFasiculation())) {
					errors.put("abnormalMovementFasiculation", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getPower().isBlank() || cranialNervesMeningealSign.getPower().isEmpty()) {
				errors.put("power", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getPower().isBlank() || !cranialNervesMeningealSign.getPower().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getPower())) {
					errors.put("power", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (cranialNervesMeningealSign.getDtr().isBlank() || cranialNervesMeningealSign.getDtr().isEmpty()) {
				errors.put("dtr", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!cranialNervesMeningealSign.getNutritionOfMuscles().isBlank()
					|| !cranialNervesMeningealSign.getNutritionOfMuscles().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(cranialNervesMeningealSign.getNutritionOfMuscles())) {
					errors.put("nutritionOfMuscles", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			// -----------------------------------------Reflexes--------------------------------------------//
			if (reflexes.getPlantar().isBlank() || reflexes.getPlantar().isEmpty()) {
				errors.put("plantar", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!reflexes.getPlantar().isBlank() || !reflexes.getPlantar().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(reflexes.getPlantar())) {
					errors.put("plantar", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (reflexes.getAbdominal().isBlank() || reflexes.getAbdominal().isEmpty()) {
				errors.put("abdominal", "* Invalid input!");
			}

			if (!reflexes.getAbdominal().isBlank() || !reflexes.getAbdominal().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(reflexes.getAbdominal())) {
					errors.put("plantar", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (reflexes.getCerebellarSign().isBlank() || reflexes.getAbdominal().isEmpty()) {
				errors.put("abdominal", "* Invalid input!");
			}

			if (!reflexes.getCerebellarSign().isBlank() || !reflexes.getCerebellarSign().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(reflexes.getCerebellarSign())) {
					errors.put("abdominal", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (reflexes.getGowerSign().isBlank() || reflexes.getGowerSign().isEmpty()) {
				errors.put("gowerSign", "* Invalid input!");
			}

			if (!reflexes.getGowerSign().isBlank() || !reflexes.getGowerSign().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(reflexes.getGowerSign())) {
					errors.put("gowerSign", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			// --------------------------------------------Sensory
			// system------------------------------------------//

			if (sensorySystem.getReflexes().isBlank() || sensorySystem.getReflexes().isEmpty()) {
				errors.put("reflexes", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!sensorySystem.getReflexes().isBlank() || !sensorySystem.getReflexes().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(sensorySystem.getReflexes())) {
					errors.put("reflexes", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (sensorySystem.getRombergSign().isBlank() || sensorySystem.getRombergSign().isEmpty()) {
				errors.put("rombergSign", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!sensorySystem.getRombergSign().isBlank() || !sensorySystem.getRombergSign().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(sensorySystem.getRombergSign())) {
					errors.put("rombergSign", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (sensorySystem.getSlrTestLimbRight().isBlank() || sensorySystem.getSlrTestLimbRight().isEmpty()) {
				errors.put("slrTestLimbRight", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!sensorySystem.getSlrTestLimbRight().isBlank() || !sensorySystem.getSlrTestLimbRight().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(sensorySystem.getSlrTestLimbRight())) {
					errors.put("slrTestLimbRight", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (sensorySystem.getSlrTestLimbLeft().isBlank() || sensorySystem.getSlrTestLimbLeft().isEmpty()) {
				errors.put("slrTestLimbLeft", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!sensorySystem.getSlrTestLimbLeft().isBlank() || !sensorySystem.getSlrTestLimbLeft().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(sensorySystem.getSlrTestLimbLeft())) {
					errors.put("slrTestLimbLeft", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (sensorySystem.getFingerNoseTest().isBlank() || sensorySystem.getFingerNoseTest().isEmpty()) {
				errors.put("fingerNoseTest", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!sensorySystem.getFingerNoseTest().isBlank() || !sensorySystem.getFingerNoseTest().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(sensorySystem.getFingerNoseTest())) {
					errors.put("fingerNoseTest", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (sensorySystem.getSkullAndBone().isBlank() || sensorySystem.getSkullAndBone().isEmpty()) {
				errors.put("skullAndBone", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!sensorySystem.getSkullAndBone().isBlank() || !sensorySystem.getSkullAndBone().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(sensorySystem.getSkullAndBone())) {
					errors.put("skullAndBone", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				Optional<SensorySystem> sensorySystemOptional = ameAssessmentServicePart_2.sensorySystemByAmeId(ameId);

				Optional<CentralNervousSystem> centralNervousSystemOptional = ameAssessmentServicePart_2
						.centralNervousSystemByAmeId(ameId);

				Optional<CranialNervesMeningealSign> cranialNervesMeningealSignOptional = ameAssessmentServicePart_2
						.cranialNervesMeningealSignByAmeId(ameId);

				Optional<Reflexes> reflexesOptional = ameAssessmentServicePart_2.reflexesByAmeId(ameId);

				if (!sensorySystemOptional.isEmpty() && !reflexesOptional.isEmpty()) {

					reflexes.setLastModifiedOn(Calendar.getInstance().getTime());
					reflexes.setLastModifiedBy(loggedInUserForcepersonnelId);
					reflexes.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					sensorySystem.setLastModifiedOn(Calendar.getInstance().getTime());
					sensorySystem.setLastModifiedBy(loggedInUserForcepersonnelId);
					sensorySystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean result = ameAssessmentServicePart_2.saveAndUpdateReflexesAndSensory(reflexes,
							sensorySystem);
					response.put("message", "Data updated successfully!");

				} else {

					reflexes.setLastModifiedOn(Calendar.getInstance().getTime());
					reflexes.setLastModifiedBy(loggedInUserForcepersonnelId);
					reflexes.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					sensorySystem.setLastModifiedOn(Calendar.getInstance().getTime());
					sensorySystem.setLastModifiedBy(loggedInUserForcepersonnelId);
					sensorySystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean resBoolean = ameAssessmentServicePart_2.saveAndUpdateReflexesAndSensory(reflexes,
							sensorySystem);
					response.put("message", "Data saved successfully!");
				}

				if (!centralNervousSystemOptional.isEmpty() && !cranialNervesMeningealSignOptional.isEmpty()) {

					centralNervousSystem.setLastModifiedOn(Calendar.getInstance().getTime());
					centralNervousSystem.setLastModifiedBy(loggedInUserForcepersonnelId);
					centralNervousSystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					cranialNervesMeningealSign.setLastModifiedOn(Calendar.getInstance().getTime());
					cranialNervesMeningealSign.setLastModifiedBy(loggedInUserForcepersonnelId);
					cranialNervesMeningealSign
							.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean result = ameAssessmentServicePart_2
							.saveAndUpdateCentralNervousSystemAndCranialNervesMeningealSign(centralNervousSystem,
									cranialNervesMeningealSign);
					response.put("message", "Data updated successfully!");
				} else {

					centralNervousSystem.setLastModifiedOn(Calendar.getInstance().getTime());
					centralNervousSystem.setLastModifiedBy(loggedInUserForcepersonnelId);
					centralNervousSystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					cranialNervesMeningealSign.setLastModifiedOn(Calendar.getInstance().getTime());
					cranialNervesMeningealSign.setLastModifiedBy(loggedInUserForcepersonnelId);
					cranialNervesMeningealSign
							.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean resBoolean = ameAssessmentServicePart_2
							.saveAndUpdateCentralNervousSystemAndCranialNervesMeningealSign(centralNervousSystem,
									cranialNervesMeningealSign);
					response.put("message", "Data saved successfully!");

				}

				response.put("isValid", true);
				return ResponseEntity.ok(response);
			}

		} catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured!");
		}

	}

	@PostMapping("validate-respiratory")
	@ResponseBody
	public ResponseEntity<?> validateRespiratoryAbdomen(@RequestBody String data) {

		System.out.println("Recieved Data: Central >>>>>" + data);

		

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			RespiratorySystem respiratorySystem = objectMapper.readValue(data, RespiratorySystem.class);

			if (respiratorySystem.getAdventitiousSounds().isBlank()
					|| respiratorySystem.getAdventitiousSounds().isEmpty()) {
				errors.put("adventitiousSounds", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!respiratorySystem.getAdventitiousSounds().isBlank()
					|| !respiratorySystem.getAdventitiousSounds().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(respiratorySystem.getAdventitiousSounds())) {
					errors.put("adventitiousSounds", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (respiratorySystem.getChestDeformityAny().isBlank()
					|| respiratorySystem.getChestDeformityAny().isEmpty()) {
				errors.put("chestDeformityAny", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!respiratorySystem.getChestDeformityAny().isBlank()
					|| !respiratorySystem.getChestDeformityAny().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(respiratorySystem.getChestDeformityAny())) {
					errors.put("chestDeformityAny", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (respiratorySystem.getPercussion().isBlank() || respiratorySystem.getPercussion().isEmpty()) {
				errors.put("percussion", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!respiratorySystem.getPercussion().isBlank() || !respiratorySystem.getPercussion().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(respiratorySystem.getPercussion())) {
					errors.put("percussion", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}
			if (respiratorySystem.getBreathSounds().isBlank() || respiratorySystem.getBreathSounds().isEmpty()) {
				errors.put("breathSounds", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!respiratorySystem.getBreathSounds().isBlank() || !respiratorySystem.getBreathSounds().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(respiratorySystem.getBreathSounds())) {
					errors.put("chestDeformityAny", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				response.put("isValid", true);
				return ResponseEntity.ok(response);
			}

		}

		catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured!");
		}

	}

	@PostMapping("validate-and-save-respiratory-and-abdomen")
	@ResponseBody
	public ResponseEntity<?> validateAndSaveAbdomen(@RequestBody Map<String, Object> data, HttpSession httpSession,
			HttpServletRequest request) {

		System.out.println("Recieved Data: Central >>>>>" + data);

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {
			String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

			ObjectMapper objectMapper = new ObjectMapper();
			RespiratorySystem respiratorySystem = objectMapper.convertValue(data.get("data1"), RespiratorySystem.class);
			Abdomen abdomen = objectMapper.convertValue(data.get("data2"), Abdomen.class);

			if (respiratorySystem.getAdventitiousSounds().isBlank()
					|| respiratorySystem.getAdventitiousSounds().isEmpty()) {
				errors.put("adventitiousSounds", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!respiratorySystem.getAdventitiousSounds().isBlank()
					|| !respiratorySystem.getAdventitiousSounds().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(respiratorySystem.getAdventitiousSounds())) {
					errors.put("adventitiousSounds", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (respiratorySystem.getChestDeformityAny().isBlank()
					|| respiratorySystem.getChestDeformityAny().isEmpty()) {
				errors.put("chestDeformityAny", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!respiratorySystem.getChestDeformityAny().isBlank()
					|| !respiratorySystem.getChestDeformityAny().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(respiratorySystem.getChestDeformityAny())) {
					errors.put("chestDeformityAny", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (respiratorySystem.getPercussion().isBlank() || respiratorySystem.getPercussion().isEmpty()) {
				errors.put("percussion", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!respiratorySystem.getPercussion().isBlank() || !respiratorySystem.getPercussion().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(respiratorySystem.getPercussion())) {
					errors.put("percussion", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}
			if (respiratorySystem.getBreathSounds().isBlank() || respiratorySystem.getBreathSounds().isEmpty()) {
				errors.put("breathSounds", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!respiratorySystem.getBreathSounds().isBlank() || !respiratorySystem.getBreathSounds().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(respiratorySystem.getBreathSounds())) {
					errors.put("breathSounds", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

//		------------------------------------abdomen---------------------------------------------------

			if (abdomen.getPilesFissure().isBlank() || abdomen.getPilesFissure().isEmpty()) {
				errors.put("pilesFissure", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!abdomen.getPilesFissure().isBlank() || !abdomen.getPilesFissure().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(abdomen.getPilesFissure())) {
					errors.put("pilesFissure", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (abdomen.getFitsula().isBlank() || abdomen.getFitsula().isEmpty()) {
				errors.put("fitsula", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!abdomen.getFitsula().isBlank() || !abdomen.getFitsula().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(abdomen.getFitsula())) {
					errors.put("fitsula", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (abdomen.getProlapseRectum().isBlank() || abdomen.getProlapseRectum().isEmpty()) {
				errors.put("prolapseRectum", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!abdomen.getProlapseRectum().isBlank() || !abdomen.getProlapseRectum().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(abdomen.getProlapseRectum())) {
					errors.put("prolapseRectum", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (abdomen.getAnyMassPalpable().isBlank() || abdomen.getAnyMassPalpable().isEmpty()) {
				errors.put("anyMassPalpable", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!abdomen.getAnyMassPalpable().isBlank() || !abdomen.getAnyMassPalpable().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(abdomen.getAnyMassPalpable())) {
					errors.put("anyMassPalpable", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (abdomen.getAnyOtherAbnormality().isBlank() || abdomen.getAnyOtherAbnormality().isEmpty()) {
				errors.put("anyOtherAbnormality", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!abdomen.getAnyOtherAbnormality().isBlank() || !abdomen.getAnyOtherAbnormality().isEmpty()) {
				if (!RegexPatternHanlder.isValidString(abdomen.getAnyOtherAbnormality())) {
					errors.put("anyOtherAbnormality", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			//-----------------------------------------------------Categorization-----------------------------------------------------//
			if (!abdomen.getpNo().isBlank() || !abdomen.getpNo().isEmpty()) {
				if (!abdomen.getpNo().equals("P-1")) {

					if (!String.valueOf(abdomen.getpType()).isBlank()
							|| !String.valueOf(abdomen.getpType()).isEmpty()) {

						if (!abdomen.getpDuration().isBlank() || !abdomen.getpDuration().isEmpty()) {

							if (!RegexPatternHanlder.isValidString(String.valueOf(abdomen.getpType()))) {
								errors.put("pType", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
							}
							if (!RegexPatternHanlder.isValidString(abdomen.getpDuration())) {
								errors.put("pDuration", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

							}

							if (abdomen.getpType().equals("Permanent")) {
								if (!abdomen.getpDuration().equals("P-2")) {
									errors.put("pDuration", "Please select valid duration!");
								}
							}
							if (abdomen.getpType().equals("Temporary")) {
								if (abdomen.getpDuration().equals("P-2")) {
									errors.put("pDuration", "Please select valid duration!");
								}
							}

						}
					}

					if (abdomen.getpType().isBlank() || abdomen.getpType().isEmpty() ) {
						errors.put("pType", RegexValidation.EMPTY_FIELD_MESSAGE);
					}

					if (abdomen.getpDuration().isBlank() || abdomen.getpDuration().isEmpty()
							|| abdomen.getpDuration() == null) {
						errors.put("pDuration", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
				}
			}
			else if(!abdomen.getpNo().isBlank() || !abdomen.getpNo().isEmpty()) {
				if (abdomen.getpNo().equals("P-1")) {
					if (!abdomen.getpType().isBlank() || !abdomen.getpType().isEmpty() ) {
						errors.put("pType", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}

					if (!abdomen.getpDuration().isBlank() || !abdomen.getpDuration().isEmpty()) {
						errors.put("pDuration", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}
				}
			}
			else {
				errors.put("pNo", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
				

//		-----------------------------------------------------------------------------------------------------------------------   //	

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				Optional<Abdomen> abdomenOptional = ameAssessmentServicePart_2.abdomenByAmeId(abdomen.getAmeId());
				Optional<RespiratorySystem> respiratorySystemOptional = ameAssessmentServicePart_2
						.respiratorySystemByAmeId(respiratorySystem.getAmeId());

				Abdomen abdomenOptionalData = new Abdomen();
				RespiratorySystem respiratorySystemOptionalData = new RespiratorySystem();
				if (!abdomenOptional.isEmpty() && !respiratorySystemOptional.isEmpty()) {
					abdomenOptionalData = abdomenOptional.get();
					respiratorySystemOptionalData = respiratorySystemOptional.get();

					respiratorySystem.setLastModifiedOn(Calendar.getInstance().getTime());
					respiratorySystem.setLastModifiedBy(loggedInUserForcepersonnelId);
					respiratorySystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					abdomen.setLastModifiedOn(Calendar.getInstance().getTime());
					abdomen.setLastModifiedBy(loggedInUserForcepersonnelId);
					abdomen.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					respiratorySystem.setLastModifiedOn(Calendar.getInstance().getTime());
					respiratorySystem.setLastModifiedBy(loggedInUserForcepersonnelId);
					respiratorySystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					abdomen.setLastModifiedOn(Calendar.getInstance().getTime());
					abdomen.setLastModifiedBy(loggedInUserForcepersonnelId);
					abdomen.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean resultRespiratorySystem = ameAssessmentServicePart_2
							.saveAndUpdateRespiratoryAndAbdomen(respiratorySystem, abdomen);

					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					return ResponseEntity.ok(response);
				}

				else {

					respiratorySystem.setLastModifiedOn(Calendar.getInstance().getTime());
					respiratorySystem.setLastModifiedBy(loggedInUserForcepersonnelId);
					respiratorySystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					abdomen.setLastModifiedOn(Calendar.getInstance().getTime());
					abdomen.setLastModifiedBy(loggedInUserForcepersonnelId);
					abdomen.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					respiratorySystem.setLastModifiedOn(Calendar.getInstance().getTime());
					respiratorySystem.setLastModifiedBy(loggedInUserForcepersonnelId);
					respiratorySystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					abdomen.setLastModifiedOn(Calendar.getInstance().getTime());
					abdomen.setLastModifiedBy(loggedInUserForcepersonnelId);
					abdomen.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					Boolean resBoolean = ameAssessmentServicePart_2
							.saveAndUpdateRespiratoryAndAbdomen(respiratorySystem, abdomen);
					response.put("isValid", true);
					response.put("message", "Data saved successfully!");
					return ResponseEntity.ok(response);
				}

			}

		}

		catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured!");
		}

	}

//	============================================Validate and save Eye=============================================================

	@PostMapping("validate-and-save-eye")
	@ResponseBody
	public ResponseEntity<?> validateAndSaveEye(@RequestBody String data, HttpSession httpSession,
			HttpServletRequest request) {

		System.out.println("Recieved Data: Central >>>>>" + data);

		String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			EyeFactor eyeFactor = objectMapper.readValue(data, EyeFactor.class);

//			------------------------------------Distant Vision---------------------------------------------------

			if (eyeFactor.getDistant_vision().equals("without-glasses")) {

				eyeFactor.setDistant_vision_rt_wg(null);
				eyeFactor.setDistant_vision_lt_wg(null);

				if (eyeFactor.getDistant_vision_rt_wog().isBlank() || eyeFactor.getDistant_vision_rt_wog().isEmpty()) {
					errors.put("distant_vision_rt_wog", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (!eyeFactor.getDistant_vision_rt_wog().isBlank()
						|| !eyeFactor.getDistant_vision_rt_wog().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getDistant_vision_rt_wog())) {
						errors.put("distant_vision_rt_wog", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

				if (eyeFactor.getDistant_vision_lt_wog().isBlank() || eyeFactor.getDistant_vision_lt_wog().isEmpty()) {
					errors.put("distant_vision_lt_wog", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (!eyeFactor.getDistant_vision_lt_wog().isBlank()
						|| !eyeFactor.getDistant_vision_lt_wog().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getDistant_vision_lt_wog())) {
						errors.put("distant_vision_lt_wog", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

			}

			if (eyeFactor.getDistant_vision().equals("with-glasses")) {

				eyeFactor.setDistant_vision_rt_wog(null);
				eyeFactor.setDistant_vision_lt_wog(null);

				if (eyeFactor.getDistant_vision_rt_wg().isBlank() || eyeFactor.getDistant_vision_rt_wg().isEmpty()) {
					errors.put("distant_vision_rt_wg", RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				if (!eyeFactor.getDistant_vision_rt_wg().isBlank() || !eyeFactor.getDistant_vision_rt_wg().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getDistant_vision_rt_wg())) {
						errors.put("distant_vision_rt_wg", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

				if (eyeFactor.getDistant_vision_lt_wg().isBlank() || eyeFactor.getDistant_vision_rt_wg().isEmpty()) {
					errors.put("distant_vision_lt_wg", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (!eyeFactor.getDistant_vision_lt_wg().isBlank() || !eyeFactor.getDistant_vision_lt_wg().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getDistant_vision_lt_wg())) {
						errors.put("distant_vision_lt_wg", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

			}

//				------------------------------------ Near Vision ---------------------------------------------------

//				----------------------------------- Without-Glasses ------------------------------------------------
			if (eyeFactor.getNear_vision().equals("without-glasses")) {

				eyeFactor.setNear_vision_rt_wg(null);
				eyeFactor.setNear_vision_lt_wg(null);

				if (eyeFactor.getNear_vision_rt_wog().isBlank() || eyeFactor.getNear_vision_rt_wog().isEmpty()) {
					errors.put("near_vision_rt_wg", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (!eyeFactor.getNear_vision_rt_wog().isBlank() || !eyeFactor.getNear_vision_rt_wog().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getNear_vision_rt_wog())) {
						errors.put("near_vision_rt_wg", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

				if (eyeFactor.getNear_vision_lt_wog().isBlank() || eyeFactor.getNear_vision_lt_wog().isEmpty()) {
					errors.put("near_vision_lt_wg", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (!eyeFactor.getNear_vision_lt_wog().isBlank() || !eyeFactor.getNear_vision_lt_wog().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getNear_vision_lt_wog())) {
						errors.put("near_vision_lt_wg", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

			}

//				----------------------------------- With-Glasses ------------------------------------------------

			if (eyeFactor.getNear_vision().equals("with-glasses")) {

				eyeFactor.setNear_vision_rt_wog(null);
				eyeFactor.setNear_vision_lt_wog(null);

				if (eyeFactor.getNear_vision_rt_wg().isBlank() || eyeFactor.getNear_vision_rt_wg().isEmpty()) {
					errors.put("near_vision_rt_wg", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (!eyeFactor.getNear_vision_rt_wg().isBlank() || !eyeFactor.getNear_vision_rt_wg().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getNear_vision_rt_wg())) {
						errors.put("near_vision_rt_wg", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

				if (eyeFactor.getNear_vision_lt_wg().isBlank() || eyeFactor.getNear_vision_lt_wg().isEmpty()) {
					errors.put("near_vision_lt_wg", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if (!eyeFactor.getNear_vision_rt_wg().isBlank() || !eyeFactor.getNear_vision_rt_wg().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getNear_vision_rt_wg())) {
						errors.put("near_vision_rt_wg", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

			}

//				--------------------------------------------------------------------------------------------------

			if (eyeFactor.getColor_vision_rt().isBlank() || eyeFactor.getColor_vision_rt().isEmpty()) {
				errors.put("color_vision_rt", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!eyeFactor.getColor_vision_rt().isBlank() || !eyeFactor.getColor_vision_rt().isBlank()) {
				if (!RegexPatternHanlder.isValidString(eyeFactor.getColor_vision_rt())) {
					errors.put("color_vision_rt", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (eyeFactor.getColor_vision_lt().isBlank() || eyeFactor.getColor_vision_lt().isEmpty()) {
				errors.put("color_vision_lt", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!eyeFactor.getColor_vision_lt().isBlank() || !eyeFactor.getColor_vision_lt().isBlank()) {
				if (!RegexPatternHanlder.isValidString(eyeFactor.getColor_vision_lt())) {
					errors.put("color_vision_lt", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (eyeFactor.getField_of_vision_rt().isBlank() || eyeFactor.getField_of_vision_rt().isEmpty()) {
				errors.put("field_of_vision_rt", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!eyeFactor.getField_of_vision_rt().isBlank() || !eyeFactor.getField_of_vision_rt().isBlank()) {
				if (!RegexPatternHanlder.isValidString(eyeFactor.getField_of_vision_rt())) {
					errors.put("field_of_vision_rt", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (eyeFactor.getField_of_vision_lt().isBlank() || eyeFactor.getField_of_vision_lt().isEmpty()) {
				errors.put("field_of_vision_lt", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!eyeFactor.getField_of_vision_lt().isBlank() || !eyeFactor.getField_of_vision_lt().isBlank()) {
				if (!RegexPatternHanlder.isValidString(eyeFactor.getField_of_vision_lt())) {
					errors.put("field_of_vision_lt", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (!eyeFactor.getAny_other_pathology_rt().isBlank() || !eyeFactor.getAny_other_pathology_rt().isBlank()) {
				if (!RegexPatternHanlder.isValidString(eyeFactor.getAny_other_pathology_rt())) {
					errors.put("any_other_pathology_rt", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (!eyeFactor.getAny_other_pathology_rt().isBlank() || !eyeFactor.getAny_other_pathology_rt().isBlank()) {
				if (!RegexPatternHanlder.isValidString(eyeFactor.getAny_other_pathology_rt())) {
					errors.put("any_other_pathology_lt", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (eyeFactor.getIol_rt().isBlank() || eyeFactor.getIol_rt().isEmpty()) {
				errors.put("iol_rt", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!eyeFactor.getIol_rt().isBlank() || !eyeFactor.getIol_rt().isBlank()) {
				if (!RegexPatternHanlder.isValidString(eyeFactor.getIol_rt())) {
					errors.put("iol_rt", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			if (eyeFactor.getIol_lt().isBlank() || eyeFactor.getIol_lt().isEmpty()) {
				errors.put("iol_lt", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!eyeFactor.getIol_lt().isBlank() || !eyeFactor.getIol_lt().isBlank()) {
				if (!RegexPatternHanlder.isValidString(eyeFactor.getIol_lt())) {
					errors.put("iol_lt", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}

			// -----------------------------------Eye
			// Categorization------------------------------------//

			//-----------------------------------------------------Categorization-----------------------------------------------------//
			if (!eyeFactor.getEyeCategory().isBlank() || !eyeFactor.getEyeCategory().isEmpty()) {
				if (!eyeFactor.getEyeCategory().equals("E-1")) {

					if (!String.valueOf(eyeFactor.getEyeType()).isBlank()
							|| !String.valueOf(eyeFactor.getEyeType()).isEmpty()) {

						if (!eyeFactor.getEyeDuration().isBlank() || !eyeFactor.getEyeDuration().isEmpty()) {

							if (!RegexPatternHanlder.isValidString(String.valueOf(eyeFactor.getEyeType()))) {
								errors.put("eyeType", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
							}
							if (!RegexPatternHanlder.isValidString(eyeFactor.getEyeDuration())) {
								errors.put("eyeDuration", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);

							}

							if (eyeFactor.getEyeType().equals("Permanent")) {
								if (!eyeFactor.getEyeDuration().equals("P-2")) {
									errors.put("eyeDuration", "Please select valid duration!");
								}
							}
							if (eyeFactor.getEyeType().equals("Temporary")) {
								if (eyeFactor.getEyeDuration().equals("P-2")) {
									errors.put("eyeDuration", "Please select valid duration!");
								}
							}

						}
					}

					if (eyeFactor.getEyeType().isBlank() || eyeFactor.getEyeType().isEmpty() ) {
						errors.put("eyeType", RegexValidation.EMPTY_FIELD_MESSAGE);
					}

					if (eyeFactor.getEyeDuration().isBlank() || eyeFactor.getEyeDuration().isEmpty()
							|| eyeFactor.getEyeDuration() == null) {
						errors.put("eyeDuration", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
				}
			}
			else if(!eyeFactor.getEyeCategory().isBlank() || !eyeFactor.getEyeCategory().isEmpty()) {
				if (eyeFactor.getEyeCategory().equals("E-1")) {
					if (!eyeFactor.getEyeType().isBlank() || !eyeFactor.getEyeType().isEmpty() ) {
						errors.put("eyeType", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}

					if (!eyeFactor.getEyeDuration().isBlank() || !eyeFactor.getEyeDuration().isEmpty()) {
						errors.put("eyeDuration", RegexValidation.ALLOW_NULL_VALUE_MESSAGE);
					}
				}
			}
			else {
				errors.put("eyeCategory", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
				

//		-----------------------------------------------------------------------------------------------------------------------   //	

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				Optional<EyeFactor> optional = assesmentPart1Service.getEyeFactor(eyeFactor.getAmeId());
				if (!optional.isEmpty()) {

					eyeFactor.setLastModifiedOn(Calendar.getInstance().getTime());
					eyeFactor.setLastModifiedBy(loggedInUserForcepersonnelId);
					eyeFactor.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					boolean result = assesmentPart1Service.saveAndUpdateEyeFactor(eyeFactor);
					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					return ResponseEntity.ok(response);
				} else {

					eyeFactor.setLastModifiedOn(Calendar.getInstance().getTime());
					eyeFactor.setLastModifiedBy(loggedInUserForcepersonnelId);
					eyeFactor.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					Boolean resBoolean = assesmentPart1Service.saveAndUpdateEyeFactor(eyeFactor);
					response.put("isValid", true);
					response.put("message", "Data saved successfully!");
					return ResponseEntity.ok(response);
				}

			}

		}

		catch (Exception e) {

			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured!");
		}

	}
	
//	-------------------- load previous data -------------------  //
	
	@PostMapping("get-force-personnel-previous-ame-forms-details")
	public ResponseEntity<?> getForcePersonnelPreviousDeclarationDetails(@RequestBody Map<String,Object> data,HttpSession httpSession){
		Map<Object,Object> response= new HashMap<>();
		Map<String, Object> errors= new HashMap<>();
		
		try {
		String formId = (String)data.get("formId");
		String candidateForcePersonnelId = (String)data.get("candidateForcePersonnelId");
			if(formId.equals("PM")) {
				
                String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
                		                                              .getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);
				
				if(!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
			   Optional<PhysicalMeasurement> physicalMeasurmentByAmeIdOptional = ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
				if(!physicalMeasurmentByAmeIdOptional.isEmpty()) {
					PhysicalMeasurement physicalMeasurementData = physicalMeasurmentByAmeIdOptional.get();
					response.put("previousPhysicalMeasurmentData", physicalMeasurementData);
					response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
				}
				else {
					errors.put("noRecordAvailable",CommonConstant.NO_RECORD_AVAILABLE);
				}
				}
				else {
					errors.put("noRecordAvailable",CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			else if (formId.equals("S")) {
				String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
						.getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);

				if (!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
					Optional<PsychologicalAssessmentAsLaidDown> psychologicalAssessmentAsLaidDownByAmeIdOptional = psychologicalAssessmentAsLaidDownRepository
							.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					if (!psychologicalAssessmentAsLaidDownByAmeIdOptional.isEmpty()) {
						PsychologicalAssessmentAsLaidDown psychologicalAssessmentAsLaidDownByAmeId = psychologicalAssessmentAsLaidDownByAmeIdOptional.get();
						response.put("previousPsychologicalAssessmentAsLaidDownByAmeIdData",psychologicalAssessmentAsLaidDownByAmeId);
						response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
					} else {
						errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
					}
				} else {
					errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			else if (formId.equals("H")) {
				String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
						.getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);

				if (!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
					Optional<Hearing> hearingByAmeIdOptional = hearingRepository
							.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					if (!hearingByAmeIdOptional.isEmpty()) {
						Hearing hearingByAmeIdData = hearingByAmeIdOptional.get();
						response.put("hearingByAmeIdData",hearingByAmeIdData);
						response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
					} else {
						errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
					}
				} else {
					errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			else if (formId.equals("A")) {
				String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
						.getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);

				if (!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
					Optional<Appendages> appendagesByAmeIdOptional = appendagesRepository
							.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					if (!appendagesByAmeIdOptional.isEmpty()) {
						Appendages appendagesByAmeIdData = appendagesByAmeIdOptional.get();
						response.put("appendagesByAmeIdData",appendagesByAmeIdData);
						response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
					} else {
						errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
					}
				} else {
					errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			else if (formId.equals("E")) {
				String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
						.getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);

				if (!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
					Optional<EyeFactor> eyeFactorByAmeIdOptional = eyeFactorRepository
							.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					if (!eyeFactorByAmeIdOptional.isEmpty()) {
						EyeFactor eyeFactorByAmeIdData = eyeFactorByAmeIdOptional.get();
						response.put("eyeFactorByAmeIdData",eyeFactorByAmeIdData);
						response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
					} else {
						errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
					}
				} else {
					errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			else if (formId.equals("G")) {
				String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
						.getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);

				if (!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
					Optional<GynaeAndObsFemale> gynaeAndObsFemaleByAmeIdOptional = gynaeAndObsFemaleRepository
							.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					if (!gynaeAndObsFemaleByAmeIdOptional.isEmpty()) {
						GynaeAndObsFemale gynaeAndObsFemaleByAmeIdData = gynaeAndObsFemaleByAmeIdOptional.get();
						response.put("gynaeAndObsFemaleByAmeIdData",gynaeAndObsFemaleByAmeIdData);
						response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
					} else {
						errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
					}
				} else {
					errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			else if (formId.equals("GE")) {
				String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
						.getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);

				if (!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
					Optional<GeneralExamination> generalExaminationByAmeIdOptional = generalExaminationRepository
							.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					if (!generalExaminationByAmeIdOptional.isEmpty()) {
						GeneralExamination generalExaminationByAmeIdData = generalExaminationByAmeIdOptional.get();
						response.put("generalExaminationByAmeIdData",generalExaminationByAmeIdData);
						response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
					} else {
						errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
					}
				} else {
					errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			else if (formId.equals("CNSCNRS")) {
				String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
						.getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);

				if (!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
					Optional<CentralNervousSystem> centralNervousSystemByAmeIdOptional = centralNervousSystemRepository.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					Optional<CranialNervesMeningealSign> cranialNervesMeningealSignByAmeIdOptional = cranialNervesMeningealSignRepository.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					Optional<Reflexes> reflexesByAmeIdOptional = reflexesRepository.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					Optional<SensorySystem> sensorySystemByAmeIdOptional = sensorySystemRepository.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					
					
					if (!centralNervousSystemByAmeIdOptional.isEmpty()||cranialNervesMeningealSignByAmeIdOptional.isEmpty()
							||reflexesByAmeIdOptional.isEmpty()||sensorySystemByAmeIdOptional.isEmpty()) {
						
					CentralNervousSystem centralNervousSystemByAmeIdData = centralNervousSystemByAmeIdOptional.get();
					CranialNervesMeningealSign cranialNervesMeningealSignByAmeIdData = cranialNervesMeningealSignByAmeIdOptional.get();
					Reflexes reflexesByAmeIdData = reflexesByAmeIdOptional.get();
					SensorySystem sensorySystemByAmeIdData = sensorySystemByAmeIdOptional.get();
						
						
					response.put("centralNervousSystemByAmeIdData",centralNervousSystemByAmeIdData);
					response.put("cranialNervesMeningealSignByAmeIdData",cranialNervesMeningealSignByAmeIdData);
			 		response.put("reflexesByAmeIdData",reflexesByAmeIdData);
					response.put("sensorySystemByAmeIdData",sensorySystemByAmeIdData);
					response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
					
					
					} else {
						errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
					}
				} else {
					errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			else if (formId.equals("P")) {
				String forcePersonnelPreviousAmeIdByForcePersonnelId = ameDeclarationIndividualModelRepoUser
						.getForcePersonnelPreviousAmeIdByForcePersonnelId(candidateForcePersonnelId);

				if (!forcePersonnelPreviousAmeIdByForcePersonnelId.isEmpty()) {
					Optional<Abdomen> abdomenByAmeIdOptional = abdomenRepository
							.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					
					Optional<RespiratorySystem> respiratorySystemAmeIdOptional = respiratorySystemRepository
							.findByAmeId(forcePersonnelPreviousAmeIdByForcePersonnelId);
					
					
					if (!abdomenByAmeIdOptional.isEmpty()||respiratorySystemAmeIdOptional.isEmpty()) {
						
						Abdomen abdomenByAmeIdData  = abdomenByAmeIdOptional.get();
						RespiratorySystem respiratorySystemAmeIdData =respiratorySystemAmeIdOptional.get();
						
						response.put("abdomenByAmeIdData",abdomenByAmeIdData);
						response.put("respiratorySystemAmeIdData",respiratorySystemAmeIdData);
						response.put("message", CommonConstant.DATA_LOADED_SUCCESSFULLY);
					} else {
						errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
					}
				} else {
					errors.put("noRecordAvailable", CommonConstant.NO_RECORD_AVAILABLE);
				}
			}
			
			
			
			
			
			
			
			
			else {
				errors.put("noRecordAvailable",CommonConstant.NO_RECORD_AVAILABLE);
			}
			
			
			if (!errors.isEmpty()) {
				
				response.put("isValid", false);
				response.put("errors", errors);
				
				return ResponseEntity.ok(response);

			} else {
				response.put("isValid", true);
				return ResponseEntity.ok(response);
				
				
				
			}

			
		}
		
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occured. Please contact the deveplopment team.");
		}
		
		
	}

}
