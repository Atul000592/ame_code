package nic.ame.app.dealinghand.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.service.AmeAssesmentServicePart_1;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.constant.RegexValidation;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.RegexPatternHanlder;

@Controller
public class DealingHandValidationAndSaveController {
	
	

	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;

	@Autowired
	private  AmeAssessmentServicePart_2 ameAssessmentServicePart_2 ;



	
//	=============================================Psychology Validation=============================================================
	@PostMapping("validate-and-save-psychology-dealing-hand")
	@ResponseBody
	public ResponseEntity<?> validatePsychology(@RequestBody String data,HttpSession httpSession,HttpServletRequest request) {
		
		System.out.println("Recieved Data: " + data);
     	Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {
			
			ObjectMapper objectMapper=new ObjectMapper();
			PsychologicalAssessmentAsLaidDown psychologicalAssessmentAsLaidDown=objectMapper.readValue(data,PsychologicalAssessmentAsLaidDown.class);
            System.out.println("Psychology Object: "+psychologicalAssessmentAsLaidDown);
            String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
			
        	
            
			
            // ===============================Psychiatric Illness History=====================================================
			  
            if(psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory().isBlank() ||psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory().isEmpty()) {
            	errors.put("psychiatricIllnessHistory", RegexValidation.EMPTY_FIELD_MESSAGE);
            }
            
            
			if (!psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory().isBlank()
					|| !psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory().isEmpty()) {

				if (!RegexPatternHanlder
						.isValidString(psychologicalAssessmentAsLaidDown.getPsychiatricIllnessHistory())) {
					errors.put("psychiatricIllnessHistory", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
			}
            
            	
            
            
			 
			
			//  ===============================wrongDecisionPublicCastigation================
			  
			  if (psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation().isBlank() ||
					  psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation().isEmpty()) {
			  errors.put("wrongDecisionPublicCastigation",RegexValidation.EMPTY_FIELD_MESSAGE); }
			  
				if (!psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation().isBlank()
						|| !psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation().isEmpty()) {

					if (!RegexPatternHanlder
							.isValidString(psychologicalAssessmentAsLaidDown.getWrong_decision_public_castigation())) {
						errors.put("wrongDecisionPublicCastigation", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			  
			//  ===============================alcholicDrugAbuseHistory======================
			  
			  if (psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history().isBlank() || 
					  psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history().isEmpty())
			  { errors.put("alcholicDrugAbuseHistory", RegexValidation.EMPTY_FIELD_MESSAGE); }
			  
				
				if (!psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history().isBlank()
						|| !psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history().isEmpty()) {

					if (!RegexPatternHanlder
							.isValidString(psychologicalAssessmentAsLaidDown.getAlcholic_drug_abuse_history())) {
						errors.put("alcholicDrugAbuseHistory", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			  
		//	  ===============================injuryInfectiveMetabolicEncephalopathyHistory=====================================================
			  
			  if (psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history().isBlank() ||
					  psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history().isEmpty()) {
			  errors.put("injuryInfectiveMetabolicEncephalopathyHistory",
					  RegexValidation.EMPTY_FIELD_MESSAGE); }
			  
				if (!psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history().isBlank()
						|| !psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history().isEmpty()) {

					if (!RegexPatternHanlder
							.isValidString(psychologicalAssessmentAsLaidDown.getInjury_infective_metabolic_encephalopathy_history())) {
						errors.put("injuryInfectiveMetabolicEncephalopathyHistory", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			  //===============================objectivePsychometricScale=====================================================
			  
			  if(psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale().isBlank()||
				 psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale().isEmpty()) {
				  
				  errors.put("objectivePsychometricScale",RegexValidation.EMPTY_FIELD_MESSAGE);
				  
			  }
			  
			  
			  
			  if(!psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale().isBlank()||
						 !psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale().isEmpty()) {
			  
			 
			  if (!RegexPatternHanlder.isValidString(psychologicalAssessmentAsLaidDown.getObjective_psychometric_scale())) {
                  errors.put("objectivePsychometricScale",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				  
				  }
			  }
				
			  
			  
				

		//==================================================================================================================================

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				
				Optional<PsychologicalAssessmentAsLaidDown> optional=assesmentPart1Service.assessmentAsLaidDown(psychologicalAssessmentAsLaidDown.getAmeId());  
				if(optional.get().getAmeId()!=null) {
					PsychologicalAssessmentAsLaidDown	psychologicalAssessmentAsLaidDownOptionalData = optional.get();
					
					psychologicalAssessmentAsLaidDown.setLastModifiedOn(Calendar.getInstance().getTime());
					psychologicalAssessmentAsLaidDown.setLastModifiedBy(loggedInUserForcepersonnelId);
					psychologicalAssessmentAsLaidDown.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					
					psychologicalAssessmentAsLaidDown.setCategory(psychologicalAssessmentAsLaidDownOptionalData.getCategory());
					psychologicalAssessmentAsLaidDown.setType(psychologicalAssessmentAsLaidDownOptionalData.getType());
					psychologicalAssessmentAsLaidDown.setDuration(psychologicalAssessmentAsLaidDownOptionalData.getDuration());
				boolean result=assesmentPart1Service.saveAndUpdatePsychologicalAssessmentDealingHand(psychologicalAssessmentAsLaidDown );
				response.put("isValid", true);
				response.put("message","Data updated successfully!");
				return ResponseEntity.ok(response);
				
				
				
				}else {
					
					psychologicalAssessmentAsLaidDown.setLastModifiedOn(Calendar.getInstance().getTime());
					psychologicalAssessmentAsLaidDown.setLastModifiedBy(loggedInUserForcepersonnelId);
					psychologicalAssessmentAsLaidDown.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					
					psychologicalAssessmentAsLaidDown.setCategory(null);
					psychologicalAssessmentAsLaidDown.setType(null);
					psychologicalAssessmentAsLaidDown.setDuration(null);
				
					Boolean resBoolean=assesmentPart1Service.saveAndUpdatePsychologicalAssessmentDealingHand(psychologicalAssessmentAsLaidDown);
					response.put("isValid", true);
					response.put("message","Data saved successfully!");
					return ResponseEntity.ok(response);
					
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");
			
		}

		

	}
	
	@PostMapping("validate-and-save-hearing-dealing-hand")
	@ResponseBody
	public ResponseEntity<?> validateHearing(@RequestBody String data,HttpSession httpSession,HttpServletRequest request) {

		System.out.println("Recieved Data: " + data);
		
		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
		

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Hearing hearing = objectMapper.readValue(data, Hearing.class);
			System.out.println(hearing);

			if (hearing.getNormal_in_both_ears().isBlank() || hearing.getNormal_in_both_ears().isEmpty()
					|| hearing.getNormal_in_both_ears() == null) {

				errors.put("normalInBothEars",RegexValidation.EMPTY_FIELD_MESSAGE);

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

				errors.put("moderateDefectInOneEar",RegexValidation.EMPTY_FIELD_MESSAGE);

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

				errors.put("audiometry",RegexValidation.EMPTY_FIELD_MESSAGE);

			}

			if (!String.valueOf(hearing.getAudiometry()).isBlank()
					|| !String.valueOf(hearing.getAudiometry()).isEmpty()) {
				if (!RegexPatternHanlder.isValidString(String.valueOf(hearing.getAudiometry()))) {
					{
						errors.put("audiometry", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			}
			
			if (!hearing.getAny_other_combination().isBlank() ||
				!hearing.getAny_other_combination().isEmpty()) 
			{
		      if(!RegexPatternHanlder.isValidString(hearing.getAny_other_combination())) {
				  errors.put("anyOtherCombination",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				  }
			}
//		-----------------------------------------------------------------------------------------------------------------------   //	

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				
				
				  
				Optional<Hearing> hearingOptional=assesmentPart1Service.getHearing(hearing.getAmeId());  
				if(!hearingOptional.isEmpty()) {
					
				Hearing	hearingOptionalData = hearingOptional.get();
				
				hearing.setLastModifiedOn(Calendar.getInstance().getTime());
				hearing.setLastModifiedBy(loggedInUserForcepersonnelId);
				hearing.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
				
				
				hearing.sethNo(hearingOptionalData.gethNo());
				hearing.sethType(hearingOptionalData.gethType());
				hearing.sethDuration(hearingOptionalData.gethDuration());
				boolean result=assesmentPart1Service.saveAndUpdateHearingDealingHand(hearing);
				response.put("isValid", true);
				response.put("message","Data updated successfully!");
				return ResponseEntity.ok(response);
				
				
				}else {
				
					hearing.setLastModifiedOn(Calendar.getInstance().getTime());
					hearing.setLastModifiedBy(loggedInUserForcepersonnelId);
					hearing.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					
					
					hearing.sethNo(null);
					hearing.sethType(null);
					hearing.sethDuration(null);
					Boolean result=assesmentPart1Service.saveAndUpdateHearingDealingHand(hearing);
					response.put("isValid", true);
					response.put("message","Data saved successfully!");
					return ResponseEntity.ok(response);
				  }

		
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");
			
		}

		

	}
	
	
	@PostMapping("validate-and-save-appendages-dealing-hand")
	@ResponseBody
	public ResponseEntity<?> validateAppendages(@RequestBody String data,HttpSession httpSession,HttpServletRequest request) {

		System.out.println("Recieved Data: " + data);
		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
		
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			ObjectMapper mapper = new ObjectMapper();
			Appendages appendages = mapper.readValue(data, Appendages.class);

//	===========================================Upper Limb=======================================================================

			if (appendages.getAnyLossInfirmityDetailsUpperRightLimb().isBlank()
					|| appendages.getAnyLossInfirmityDetailsUpperRightLimb().isEmpty()) {
				errors.put("anyLossInfirmityDetailsUpperRightLimb", RegexValidation.EMPTY_FIELD_MESSAGE);
			}
			
              if (!appendages.getAnyLossInfirmityDetailsUpperRightLimb().isBlank()
					|| !appendages.getAnyLossInfirmityDetailsUpperRightLimb().isEmpty()){
            	  
            	  if (!RegexPatternHanlder.isValidString(appendages.getAnyLossInfirmityDetailsUpperRightLimb())) {
  					{
  						errors.put("anyLossInfirmityDetailsUpperRightLimb", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
  					}
  				}
				}

              
              
              
			if (appendages.getAnyLossInfirmityDetailsUpperLeftLimb().isBlank()
					||appendages.getAnyLossInfirmityDetailsUpperLeftLimb().isEmpty()){
				      errors.put("anyLossInfirmityDetailsUpperLeftLimb",RegexValidation.EMPTY_FIELD_MESSAGE);

					}
			
			if (!appendages.getAnyLossInfirmityDetailsUpperLeftLimb().isBlank()
					|| !appendages.getAnyLossInfirmityDetailsUpperLeftLimb().isEmpty()) {
					if(!RegexPatternHanlder.isValidString(appendages.getAnyLossInfirmityDetailsUpperLeftLimb())) {
				errors.put("anyLossInfirmityDetailsUpperLeftLimb",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
			}}

//============================================Lower Limb========================================================================


					if (appendages.getAnyLossInfirmityDetailsLowerRightLimb().isBlank()
							|| appendages.getAnyLossInfirmityDetailsLowerRightLimb().isEmpty()) {
						errors.put("anyLossInfirmityDetailsLowerRightLimb", RegexValidation.EMPTY_FIELD_MESSAGE);
						
					}
					
					
					if (!appendages.getAnyLossInfirmityDetailsLowerRightLimb().isBlank()
							||! appendages.getAnyLossInfirmityDetailsLowerRightLimb().isEmpty()) {
							if(!RegexPatternHanlder.isValidString(appendages.getAnyLossInfirmityDetailsLowerRightLimb())) {
						errors.put("anyLossInfirmityDetailsLowerRightLimb",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}}

					if (appendages.getAnyLossInfirmityDetailsLowerLeftLimb().isBlank()
							|| appendages.getAnyLossInfirmityDetailsLowerLeftLimb().isEmpty()) {
						errors.put("anyLossInfirmityDetailsLowerLeftLimb", RegexValidation.EMPTY_FIELD_MESSAGE);
						
					}
					
					if (!appendages.getAnyLossInfirmityDetailsLowerLeftLimb().isBlank()
							||! appendages.getAnyLossInfirmityDetailsLowerLeftLimb().isEmpty()) {
							if(!RegexPatternHanlder.isValidString(appendages.getAnyLossInfirmityDetailsLowerLeftLimb())) {
						errors.put("anyLossInfirmityDetailsLowerLeftLimb",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}}
					
					
			

//=============================================Spine============================================================================				  

					if (appendages.getSpine().isBlank() || appendages.getSpine().isEmpty() || appendages.getSpine() == null) {
						 errors.put("spine", RegexValidation.EMPTY_FIELD_MESSAGE);
					}
					
					if (!appendages.getSpine().isBlank()
							||! appendages.getSpine().isEmpty()) {
							if(!RegexPatternHanlder.isValidString(appendages.getSpine())) {
						errors.put("anyLossInfirmityDetailsLowerLeftLimb",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}}
			


			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				
				Optional<Appendages> appendagesOptional=assesmentPart1Service.getAppendages(appendages.getAmeId());  
				if(!appendagesOptional.isEmpty()) {
					Appendages appendagesOptionalData =	appendagesOptional.get();
					
					appendages.setLastModifiedOn(Calendar.getInstance().getTime());
					appendages.setLastModifiedBy(loggedInUserForcepersonnelId);
					appendages.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					
					
					appendages.setUpperLimbShape(appendagesOptionalData.getUpperLimbShape());
					appendages.setUpperLimbType(appendagesOptionalData.getUpperLimbType());
					appendages.setUpperLimbDuration(appendagesOptionalData.getUpperLimbDuration());
					
					appendages.setLowerLimbShape(appendagesOptionalData.getLowerLimbShape());
					appendages.setLowerLimbType(appendagesOptionalData.getLowerLimbType());
					appendages.setLowerLimbDuration(appendagesOptionalData.getLowerLimbDuration());
					
					appendages.setSpineShape(appendagesOptionalData.getSpineShape());
					appendages.setSpineType(appendagesOptionalData.getSpineType());
					appendages.setSpineDuration(appendagesOptionalData.getSpineDuration());
					
			    Boolean result=assesmentPart1Service.saveAndUpdateAppendagesDealingHand(appendages );
				response.put("isValid", true);
				response.put("message","Data updated successfully!");
				return ResponseEntity.ok(response);
				
				}else {
					appendages.setLastModifiedOn(Calendar.getInstance().getTime());
					appendages.setLastModifiedBy(loggedInUserForcepersonnelId);
					appendages.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					
					appendages.setUpperLimbShape(null);
					appendages.setUpperLimbType(null);
					appendages.setUpperLimbDuration(null);
					
					appendages.setLowerLimbShape(null);
					appendages.setLowerLimbType(null);
					appendages.setLowerLimbDuration(null);
					
					appendages.setSpineShape(null);
					appendages.setSpineType(null);
					appendages.setSpineDuration(null);
					
					Boolean result=assesmentPart1Service.saveAndUpdateAppendagesDealingHand(appendages);
					response.put("isValid", true);
					response.put("message","Data saved successfully!");
					return ResponseEntity.ok(response);
				}

		
			}

		
	}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");
			
		}

	

	}
	
	
	
//	============================================Validate and save Eye=============================================================

	@PostMapping("validate-and-save-eye-dealing-hand")
	@ResponseBody
	public ResponseEntity<?> validateAndSaveEye(@RequestBody String data,HttpSession httpSession,HttpServletRequest request) {
		
		System.out.println("Recieved Data: Central >>>>>" + data);
		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			EyeFactor eyeFactor=objectMapper.readValue(data, EyeFactor.class);
			
			
//			------------------------------------Distant Vision---------------------------------------------------
			
			
			
			if (eyeFactor.getDistant_vision().equals("without-glasses")) {

				eyeFactor.setDistant_vision_rt_wg(null);
				eyeFactor.setDistant_vision_lt_wg(null);

				if (eyeFactor.getDistant_vision_rt_wog().isBlank() || eyeFactor.getDistant_vision_rt_wog().isEmpty()) {
					errors.put("distant_vision_rt_wog", RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				if (!eyeFactor.getDistant_vision_rt_wog().isBlank()|| !eyeFactor.getDistant_vision_rt_wog().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getDistant_vision_rt_wog())) {
						errors.put("distant_vision_rt_wog",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

				if (eyeFactor.getDistant_vision_lt_wog().isBlank() || eyeFactor.getDistant_vision_lt_wog().isEmpty()) {
					errors.put("distant_vision_lt_wog", RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				if (!eyeFactor.getDistant_vision_lt_wog().isBlank()|| !eyeFactor.getDistant_vision_lt_wog().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getDistant_vision_lt_wog())) {
						errors.put("distant_vision_lt_wog",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

			}
				
			if (eyeFactor.getDistant_vision().equals("with-glasses")) {

				eyeFactor.setDistant_vision_rt_wog(null);
				eyeFactor.setDistant_vision_lt_wog(null);

				if (eyeFactor.getDistant_vision_rt_wg().isBlank() || eyeFactor.getDistant_vision_rt_wg().isEmpty()) {
					errors.put("distant_vision_rt_wg",RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				if (!eyeFactor.getDistant_vision_rt_wg().isBlank()|| !eyeFactor.getDistant_vision_rt_wg().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getDistant_vision_rt_wg())) {
						errors.put("distant_vision_rt_wg",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

				if (eyeFactor.getDistant_vision_lt_wg().isBlank() || eyeFactor.getDistant_vision_rt_wg().isEmpty()) {
					errors.put("distant_vision_lt_wg", RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				if (!eyeFactor.getDistant_vision_lt_wg().isBlank()|| !eyeFactor.getDistant_vision_lt_wg().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getDistant_vision_lt_wg())) {
						errors.put("distant_vision_lt_wg",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

			}

//				------------------------------------ Near Vision ---------------------------------------------------
			 
//				----------------------------------- Without-Glasses ------------------------------------------------
				if (eyeFactor.getNear_vision().equals("without-glasses")) {
					
					eyeFactor.setNear_vision_rt_wg(null);
					eyeFactor.setNear_vision_lt_wg(null);

					if (eyeFactor.getNear_vision_rt_wog().isBlank() || eyeFactor.getNear_vision_rt_wog().isEmpty()) {
						errors.put("near_vision_rt_wog",RegexValidation.EMPTY_FIELD_MESSAGE);
					}
					
					if (!eyeFactor.getNear_vision_rt_wog().isBlank()|| !eyeFactor.getNear_vision_rt_wog().isBlank()) {
						if (!RegexPatternHanlder.isValidString(eyeFactor.getNear_vision_rt_wog())) {
							errors.put("near_vision_rt_wog",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}

					if (eyeFactor.getNear_vision_lt_wog().isBlank() || eyeFactor.getNear_vision_lt_wog().isEmpty()) {
						errors.put("near_vision_lt_wog",RegexValidation.EMPTY_FIELD_MESSAGE);
					}
					
					if (!eyeFactor.getNear_vision_lt_wog().isBlank()|| !eyeFactor.getNear_vision_lt_wog().isBlank()) {
						if (!RegexPatternHanlder.isValidString(eyeFactor.getNear_vision_lt_wog())) {
							errors.put("near_vision_lt_wog",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}

				} 
				
//				----------------------------------- With-Glasses ------------------------------------------------

				
				if (eyeFactor.getNear_vision().equals("with-glasses")) {
					
					eyeFactor.setNear_vision_rt_wog(null);
					eyeFactor.setNear_vision_lt_wog(null);

					if (eyeFactor.getNear_vision_rt_wg().isBlank() || eyeFactor.getNear_vision_rt_wg().isEmpty()) {
						errors.put("near_vision_rt_wg",RegexValidation.EMPTY_FIELD_MESSAGE);
					}
					
					if (!eyeFactor.getNear_vision_rt_wg().isBlank()|| !eyeFactor.getNear_vision_rt_wg().isBlank()) {
						if (!RegexPatternHanlder.isValidString(eyeFactor.getNear_vision_rt_wg())) {
							errors.put("near_vision_rt_wg",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}

					if (eyeFactor.getNear_vision_lt_wg().isBlank() || eyeFactor.getNear_vision_lt_wg().isEmpty()) {
						errors.put("near_vision_lt_wg",RegexValidation.EMPTY_FIELD_MESSAGE);
					}
					
					if (!eyeFactor.getNear_vision_rt_wg().isBlank()|| !eyeFactor.getNear_vision_rt_wg().isBlank()) {
						if (!RegexPatternHanlder.isValidString(eyeFactor.getNear_vision_rt_wg())) {
							errors.put("near_vision_rt_wg",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}

				} 
			 
			
//				--------------------------------------------------------------------------------------------------
			 
			 
				  
				  if( eyeFactor.getColor_vision_rt().isBlank()||
						  eyeFactor.getColor_vision_rt().isEmpty()) 
				  {
				  errors.put("color_vision_rt",RegexValidation.EMPTY_FIELD_MESSAGE); 
				  }
				  
					
					if (!eyeFactor.getColor_vision_rt().isBlank()|| !eyeFactor.getColor_vision_rt().isBlank()) {
						if (!RegexPatternHanlder.isValidString(eyeFactor.getColor_vision_rt())) {
							errors.put("color_vision_rt",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}
				  
			  if( eyeFactor.getColor_vision_lt().isBlank()||
					  eyeFactor.getColor_vision_lt().isEmpty()) 
				  {
				  errors.put("color_vision_lt",RegexValidation.EMPTY_FIELD_MESSAGE); 
				  }
			  
				
				if (!eyeFactor.getColor_vision_lt().isBlank()|| !eyeFactor.getColor_vision_lt().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getColor_vision_lt())) {
						errors.put("color_vision_lt",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			  
			  if( eyeFactor.getField_of_vision_rt().isBlank()||
					  eyeFactor.getField_of_vision_rt().isEmpty()) 
				  {
				  errors.put("field_of_vision_rt",RegexValidation.EMPTY_FIELD_MESSAGE); 
				  }
			  
				
				if (!eyeFactor.getField_of_vision_rt().isBlank()|| !eyeFactor.getField_of_vision_rt().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getField_of_vision_rt())) {
						errors.put("field_of_vision_rt",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			  
			  if(eyeFactor.getField_of_vision_lt().isBlank()||
					  eyeFactor.getField_of_vision_lt().isEmpty()) 
				  {
				  errors.put("field_of_vision_lt",RegexValidation.EMPTY_FIELD_MESSAGE); 
				  }
			  
				
				if (!eyeFactor.getField_of_vision_lt().isBlank()|| !eyeFactor.getField_of_vision_lt().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getField_of_vision_lt())) {
						errors.put("field_of_vision_lt",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			  
			 
			  
				
				if (!eyeFactor.getAny_other_pathology_rt().isBlank()|| !eyeFactor.getAny_other_pathology_rt().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getAny_other_pathology_rt())) {
						errors.put("any_other_pathology_rt",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			  
			  
			  
				
				if (!eyeFactor.getAny_other_pathology_rt().isBlank()|| !eyeFactor.getAny_other_pathology_rt().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getAny_other_pathology_rt())) {
						errors.put("any_other_pathology_lt",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			  
				 if(eyeFactor.getIol_rt().isBlank()||
					  eyeFactor.getIol_rt().isEmpty()) 
				  {
				  errors.put("iol_rt",RegexValidation.EMPTY_FIELD_MESSAGE); 
				  }
				 
					
					if (!eyeFactor.getIol_rt().isBlank()|| !eyeFactor.getIol_rt().isBlank()) {
						if (!RegexPatternHanlder.isValidString(eyeFactor.getIol_rt())) {
							errors.put("iol_rt",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}
					
			  if(eyeFactor.getIol_lt().isBlank()||
					  eyeFactor.getIol_lt().isEmpty()) 
				  {
				  errors.put("iol_lt",RegexValidation.EMPTY_FIELD_MESSAGE); 
				  }
			  
				
				if (!eyeFactor.getIol_lt().isBlank()|| !eyeFactor.getIol_lt().isBlank()) {
					if (!RegexPatternHanlder.isValidString(eyeFactor.getIol_lt())) {
						errors.put("iol_lt",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
				  
	
                 if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				
				Optional<EyeFactor> eyeFactorOptional=assesmentPart1Service.getEyeFactor(eyeFactor.getAmeId());  
				if(!eyeFactorOptional.isEmpty()) {
					
				EyeFactor eyeFactorOptionalData = eyeFactorOptional.get();
				
				eyeFactor.setLastModifiedOn(Calendar.getInstance().getTime());
				eyeFactor.setLastModifiedBy(loggedInUserForcepersonnelId);
				eyeFactor.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

				
				eyeFactor.setEyeCategory(eyeFactorOptionalData.getEyeCategory());
				eyeFactor.setEyeType(eyeFactorOptionalData.getEyeType());
				eyeFactor.setEyeDuration(eyeFactorOptionalData.getEyeDuration());
				
				boolean result = assesmentPart1Service.saveAndUpdateEyeFactorDealingHand(eyeFactor );
				response.put("isValid", true);
				response.put("message","Data updated successfully!");
				return ResponseEntity.ok(response);
				}
				else {
					
					eyeFactor.setLastModifiedOn(Calendar.getInstance().getTime());
					eyeFactor.setLastModifiedBy(loggedInUserForcepersonnelId);
					eyeFactor.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
	
					
					eyeFactor.setEyeCategory(null);
					eyeFactor.setEyeType(null);
					eyeFactor.setEyeDuration(null);
					
					Boolean result=assesmentPart1Service.saveAndUpdateEyeFactorDealingHand(eyeFactor);
					response.put("isValid", true);
					response.put("message","Data saved successfully!");
					return ResponseEntity.ok(response);
				}

			
			}

		}

		catch (Exception e) {
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");
		}

	}
	
	

	
	
	

	@PostMapping("validate-and-save-respiratory-and-abdomen-dealing-hand")
	@ResponseBody
	public ResponseEntity<?> validateAndSaveAbdomen(@RequestBody Map<String,Object> data,HttpSession httpSession,HttpServletRequest request) {
		
		System.out.println("Recieved Data: Central >>>>>" + data);
		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

		
		
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		try {

		ObjectMapper objectMapper = new ObjectMapper();
	    RespiratorySystem respiratorySystem=objectMapper.convertValue(data.get("data1"),RespiratorySystem.class);
		Abdomen abdomen=objectMapper.convertValue(data.get("data2"),Abdomen.class);
			
			
		if( respiratorySystem.getAdventitiousSounds().isBlank()||
				respiratorySystem.getAdventitiousSounds().isEmpty()
		    	) {
				errors.put("adventitiousSounds",RegexValidation.EMPTY_FIELD_MESSAGE);
			}
			
			 if (!respiratorySystem.getAdventitiousSounds().isBlank()
						|| !respiratorySystem.getAdventitiousSounds().isEmpty()) {
					if (!RegexPatternHanlder.isValidString(respiratorySystem.getAdventitiousSounds())) {
						errors.put("adventitiousSounds",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			         
			
			if( respiratorySystem.getChestDeformityAny().isBlank()||
					respiratorySystem.getChestDeformityAny().isEmpty()) {
					errors.put("chestDeformityAny",RegexValidation.EMPTY_FIELD_MESSAGE);
				}
			
			 if (!respiratorySystem.getChestDeformityAny().isBlank()
						|| !respiratorySystem.getChestDeformityAny().isEmpty()) {
					if (!RegexPatternHanlder.isValidString(respiratorySystem.getChestDeformityAny())) {
						errors.put("chestDeformityAny",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			
			
			if( respiratorySystem.getPercussion().isBlank()||
					respiratorySystem.getPercussion().isEmpty()
					) {
					errors.put("percussion",RegexValidation.EMPTY_FIELD_MESSAGE);
				}
			
			 if (!respiratorySystem.getPercussion().isBlank()
						|| !respiratorySystem.getPercussion().isEmpty()) {
					if (!RegexPatternHanlder.isValidString(respiratorySystem.getPercussion())) {
						errors.put("percussion",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			if( respiratorySystem.getBreathSounds().isBlank()||
					respiratorySystem.getBreathSounds().isEmpty()) {
					errors.put("breathSounds",RegexValidation.EMPTY_FIELD_MESSAGE);
				}
			
			 if (!respiratorySystem.getBreathSounds().isBlank()
						|| !respiratorySystem.getBreathSounds().isEmpty()) {
					if (!RegexPatternHanlder.isValidString(respiratorySystem.getBreathSounds())) {
						errors.put("chestDeformityAny",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
			
		
		
		
//			------------------------------------abdomen---------------------------------------------------
			
			
			
				
				if (abdomen.getPilesFissure().isBlank() || abdomen.getPilesFissure().isEmpty()) {
					errors.put("pilesFissure", RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				 if (!abdomen.getPilesFissure().isBlank()
							|| !abdomen.getPilesFissure().isEmpty()) {
						if (!RegexPatternHanlder.isValidString(abdomen.getPilesFissure())) {
							errors.put("pilesFissure",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}

				if (abdomen.getFitsula().isBlank() || abdomen.getFitsula().isEmpty()) {
					errors.put("fitsula", RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				 if (!abdomen.getFitsula().isBlank()|| !abdomen.getFitsula().isEmpty()) {
						if (!RegexPatternHanlder.isValidString(abdomen.getFitsula())) {
							errors.put("fitsula",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}
				
				if (abdomen.getProlapseRectum().isBlank() || abdomen.getProlapseRectum().isEmpty()) {
					errors.put("prolapseRectum",RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				 if (!abdomen.getProlapseRectum().isBlank()
							|| !abdomen.getProlapseRectum().isEmpty()) {
						if (!RegexPatternHanlder.isValidString(abdomen.getProlapseRectum())) {
							errors.put("prolapseRectum",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}
				
				if (abdomen.getAnyMassPalpable().isBlank() || abdomen.getAnyMassPalpable().isEmpty()) {
					errors.put("anyMassPalpable",RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				 if (!abdomen.getAnyMassPalpable().isBlank()|| !abdomen.getAnyMassPalpable().isEmpty()) {
						if (!RegexPatternHanlder.isValidString(abdomen.getAnyMassPalpable())) {
							errors.put("anyMassPalpable",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}
				
				if (abdomen.getAnyOtherAbnormality().isBlank() || abdomen.getAnyOtherAbnormality().isEmpty()) {
					errors.put("anyOtherAbnormality",RegexValidation.EMPTY_FIELD_MESSAGE);
				} 
				
				 if (!abdomen.getAnyOtherAbnormality().isBlank()|| !abdomen.getAnyOtherAbnormality().isEmpty()) {
						if (!RegexPatternHanlder.isValidString(abdomen.getAnyOtherAbnormality())) {
							errors.put("anyOtherAbnormality",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
					}
				  
		  
                 if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				Optional<Abdomen> abdomenOptional=ameAssessmentServicePart_2.abdomenByAmeId(abdomen.getAmeId());  
		 		Optional<RespiratorySystem> respiratorySystemOptional=ameAssessmentServicePart_2.respiratorySystemByAmeId(respiratorySystem.getAmeId());
		 		
		 		
		 		Abdomen abdomenOptionalData=new Abdomen();
		 	    RespiratorySystem respiratorySystemOptionalData= new RespiratorySystem();
		           if(!abdomenOptional.isEmpty()&&!respiratorySystemOptional.isEmpty()) {
		        	   respiratorySystem.setLastModifiedOn(Calendar.getInstance().getTime());
			  			respiratorySystem.setLastModifiedBy(loggedInUserForcepersonnelId);
			  			respiratorySystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

			  			abdomen.setLastModifiedOn(Calendar.getInstance().getTime());
			  			abdomen.setLastModifiedBy(loggedInUserForcepersonnelId);
			  			abdomen.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

		         	
		        	abdomenOptionalData=abdomenOptional.get();
		  			respiratorySystemOptionalData=respiratorySystemOptional.get();
		  			
		  			abdomen.setpNo(abdomenOptionalData.getpNo());
		  			abdomen.setpType(abdomenOptionalData.getpType());
		  			abdomen.setpDuration(abdomenOptionalData.getpDuration());
			 		 
		  			
		  			
		  		  Boolean result=ameAssessmentServicePart_2.saveAndUpdateRespiratoryAndAbdomenDealingHand(respiratorySystem, abdomen);
		 		  response.put("isValid", true);
				  response.put("message","Data updated successfully!");
					return ResponseEntity.ok(response);  
		           }
		       
		           else {
		        	   
		        	   respiratorySystem.setLastModifiedOn(Calendar.getInstance().getTime());
			  			respiratorySystem.setLastModifiedBy(loggedInUserForcepersonnelId);
			  			respiratorySystem.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

			  			abdomen.setLastModifiedOn(Calendar.getInstance().getTime());
			  			abdomen.setLastModifiedBy(loggedInUserForcepersonnelId);
			  			abdomen.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));


		        	   abdomen.setpNo(null);
		        	   abdomen.setpType(null);
		        	   abdomen.setpDuration(null);
				 		 
			  			
		 			
		 		      Boolean result=ameAssessmentServicePart_2.saveAndUpdateRespiratoryAndAbdomenDealingHand(respiratorySystem, abdomen);
		 		      response.put("isValid", true);
					  response.put("message","Data saved successfully!");
						return ResponseEntity.ok(response);
		           }

			}

		}

		catch (Exception e) {
		
			e.printStackTrace();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");
		}

	}
	
	
	//=================================================== Gynaecology =================================================================	
		@PostMapping("validate-and-save-gynaecology-dealing-hand")
		@ResponseBody
		public ResponseEntity<?> validateGynaecology(@RequestBody String data,HttpSession httpSession,HttpServletRequest request) {

			System.out.println("Recieved Data: " + data);
			String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

			 
			Map<String, Object> response = new HashMap<>();
			Map<String, String> errors = new HashMap<>();
			try {

				ObjectMapper mapper = new ObjectMapper();
				GynaeAndObsFemale gynaeAndObsFemale = mapper.readValue(data, GynaeAndObsFemale.class);
				System.out.println(gynaeAndObsFemale);
				 Date lastModifiedDate = Date.valueOf(LocalDate.now());
				 LocalDate currentDate =LocalDate.now();
						

				if (gynaeAndObsFemale.getLmp()==null) {
					errors.put("lmp", RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				
				
				
				if (gynaeAndObsFemale.getMenstrualHistory().isBlank() || gynaeAndObsFemale.getMenstrualHistory().isEmpty()) {
					errors.put("menstrualHistory",RegexValidation.EMPTY_FIELD_MESSAGE);	
				}
				
				if (!gynaeAndObsFemale.getMenstrualHistory().isBlank()
						|| !gynaeAndObsFemale.getMenstrualHistory().isEmpty()) {
					if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getMenstrualHistory())) {
						errors.put("menstrualHistory", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
				
				
				

				
				
				
				if(gynaeAndObsFemale.getVaginalDischarge().isEmpty()||
						   gynaeAndObsFemale.getVaginalDischarge().isBlank())
						{
					errors.put("vaginalDischarge",RegexValidation.EMPTY_FIELD_MESSAGE);
				    
						}
				
				if(!gynaeAndObsFemale.getVaginalDischarge().isEmpty()||
				   !gynaeAndObsFemale.getVaginalDischarge().isBlank())
				{
					if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getVaginalDischarge())) {
					errors.put("vaginalDischarge",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				    }
				}
				
				
				if(gynaeAndObsFemale.getUvProlapse().isBlank()||
						   gynaeAndObsFemale.getUvProlapse().isBlank()) {
					errors.put("uvProlapse", RegexValidation.EMPTY_FIELD_MESSAGE);
				}

				if(!gynaeAndObsFemale.getUvProlapse().isBlank()||
				   !gynaeAndObsFemale.getUvProlapse().isBlank()) {
					
					if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getUvProlapse())
							) {
							errors.put("uvProlapse",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
						}
				}
				
				
				
				if(gynaeAndObsFemale.getUsgAbdomen().isBlank()||
						   gynaeAndObsFemale.getUsgAbdomen().isEmpty()) {
					errors.put("usgAbdomen", RegexValidation.EMPTY_FIELD_MESSAGE);
					
				}
				
				
				
				
				
				
				if(!gynaeAndObsFemale.getUsgAbdomen().isBlank()||
				   !gynaeAndObsFemale.getUsgAbdomen().isEmpty()) {
					if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getUsgAbdomen())
							) {
						errors.put("usgAbdomen",  RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}
				
				
				if(gynaeAndObsFemale.getOtherAilment().isBlank()||
						   gynaeAndObsFemale.getOtherAilment().isEmpty()) {
					errors.put("otherAilment",RegexValidation.EMPTY_FIELD_MESSAGE);
				
				}
				
				
				
				
				if(!gynaeAndObsFemale.getOtherAilment().isBlank()||
				   !gynaeAndObsFemale.getOtherAilment().isEmpty())
				{if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getOtherAilment())
						) {
					errors.put("otherAilment", RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
				}
	}
				
				

				if(gynaeAndObsFemale.getObstetricsHistory().isBlank()||
				   gynaeAndObsFemale.getObstetricsHistory().isEmpty()) {
					errors.put("obstetricsHistory", RegexValidation.EMPTY_FIELD_MESSAGE);
				}
				
				
				if(!gynaeAndObsFemale.getObstetricsHistory().isBlank()||
				   !gynaeAndObsFemale.getObstetricsHistory().isEmpty()) {
					if (!RegexPatternHanlder.isValidString(gynaeAndObsFemale.getObstetricsHistory())
							) {
						errors.put("obstetricsHistory",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
					}
				}

				/*
				 * if (gynaeAndObsFemale.getDateOfLastConfinement()==null) {
				 * errors.put("dateOfLastConfinement", RegexValidation.EMPTY_FIELD_MESSAGE);
				 * 
				 * }
				 * 
				 * else { LocalDate inputDate =
				 * gynaeAndObsFemale.getDateOfLastConfinement().toLocalDate();
				 * 
				 * if (inputDate.isAfter(currentDate)) {
				 * 
				 * errors.put("dateOfLastConfinement", "* date should be in past or present"); }
				 * }
				 */


	
				if (!errors.isEmpty()) {
					response.put("isValid", false);
					response.put("errors", errors);
					return ResponseEntity.ok(response);

				} else {
					Optional<GynaeAndObsFemale> gynaeAndObsFemaleOptional=ameAssessmentServicePart_2.gynaeByAmeId(gynaeAndObsFemale.getAmeId());
					
					if(gynaeAndObsFemaleOptional.isEmpty()) {

						gynaeAndObsFemale.setLastModifiedOn(lastModifiedDate);
						
						gynaeAndObsFemale.setLastModifiedBy(loggedInUserForcepersonnelId);
	                    gynaeAndObsFemale.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					
						gynaeAndObsFemale.setgFactorCategory(null);
						gynaeAndObsFemale.setgFactorType(null);
						gynaeAndObsFemale.setgFactorType(null);
						
						Boolean resultSave= ameAssessmentServicePart_2.saveAndUpdateGynaeAndObsFemaleDealingHand(gynaeAndObsFemale);
						response.put("isValid", true);
						response.put("message","Data saved successfully!");
						return ResponseEntity.ok(response);
						//logger.info(">>>>>>>>>>> saveGynaeAndObsFemale >>>>>>>"+reBoolean);
					}else {
						GynaeAndObsFemale gynaeAndObsFemaleOptionalData = gynaeAndObsFemaleOptional.get();
						
						gynaeAndObsFemale.setLastModifiedOn(lastModifiedDate);
	                    gynaeAndObsFemale.setLastModifiedBy(loggedInUserForcepersonnelId);
	                    gynaeAndObsFemale.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					
						gynaeAndObsFemale.setgFactorCategory(gynaeAndObsFemaleOptionalData.getgFactorCategory());
						gynaeAndObsFemale.setgFactorType(gynaeAndObsFemaleOptionalData.getgFactorType());
						gynaeAndObsFemale.setgFactorDuration(gynaeAndObsFemaleOptionalData.getgFactorDuration());
						
						boolean resultUpdate=ameAssessmentServicePart_2.saveAndUpdateGynaeAndObsFemaleDealingHand(gynaeAndObsFemale);
						response.put("isValid", true);
						response.put("message","Data updated successfully!");
						return ResponseEntity.ok(response);
						//logger.info("Result......"+result);
					}
					
				}

			}

			catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");
			}

		

		}

	

	
	
}
