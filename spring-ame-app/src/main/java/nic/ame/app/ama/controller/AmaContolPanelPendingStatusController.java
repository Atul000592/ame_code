package nic.ame.app.ama.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.ref.entity.RefDropDownRange;
import java.util.Date;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.ama.dto.MedicalRequestDto;
import nic.ame.app.board.member.repository.AbdomenRepo;
import nic.ame.app.board.member.repository.AppendagesRepo;
import nic.ame.app.board.member.repository.CentralNervousSystemRepo;
import nic.ame.app.board.member.repository.CranialNervesMeningealSignRepo;
import nic.ame.app.board.member.repository.EyeFactorRepo;
import nic.ame.app.board.member.repository.GeneralExaminationRepo;
import nic.ame.app.board.member.repository.GynaeAndObsFemaleRepo;
import nic.ame.app.board.member.repository.HearingRepo;
import nic.ame.app.board.member.repository.PsychologicalAssessmentAsLaidDownRepo;
import nic.ame.app.board.member.repository.ReflexesRepo;
import nic.ame.app.board.member.repository.RespiratorySystemRepo;
import nic.ame.app.board.member.repository.SensoryRepo;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.InvestigationDto;
import nic.ame.app.master.dto.PlaceAndDateDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.AmePendingStatus;
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
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeDetails;
import nic.ame.app.master.model.ColumnIdentifier;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.ref.entity.CategoryTypeMaster;
import nic.ame.app.master.ref.entity.RefAppendagesType;

import nic.ame.app.master.ref.entity.RefDropDownRange;
import nic.ame.app.master.ref.entity.RefGFactor;

import nic.ame.app.master.ref.entity.RefPhysicalStatus;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDetailsRepository;
import nic.ame.app.master.repository.AmePendingStatusRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.OthersTestRepository;
import nic.ame.app.master.ref.entity.CategoryShapeMaster;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.AmeFormDropDownService;
import nic.ame.app.master.service.AmeShapeCategoryDurationService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;
import nic.ame.constant.CommonConstant;
import nic.ame.constant.RegexValidation;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.RegexPatternHanlder;

@Controller
public class AmaContolPanelPendingStatusController {
	
	
	private Logger logger = LogManager.getLogger(AmaControlPanelController.class);
	@Autowired
	private AmeDropDownService ameDropDownService;

	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;

	

	@Autowired
	private AmeAssesmentServicePart_1 assesmentPart1Service;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private AgeCalculatorService ageCalculatorService;

	
	@Autowired
	private MapUriToUserService mapUriToUserService;
	

	

	@Autowired
	private ForcePersonnelRepository forcePersonnelRespository;

	
	
	
	@Autowired 
	private AmePendingStatusRepository amePendingStatusRepository;
	
	@Autowired
	private  AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;

	// ==============================ame-pending-status-index-page==========================//
	
		@RequestMapping(path = "ame-pending-status-index-page-ama", method = RequestMethod.POST)
		public String displayPenddingStatusRemark(Model model,
				@RequestParam("forcepersonalId") String candidateforcepersonalId, HttpSession httpSession,
				@RequestParam("ameId") String ameId) {

			model.addAttribute("forcepersonalId", candidateforcepersonalId);
			model.addAttribute("ameId", ameId);
	        
			int rCode = (int) httpSession.getAttribute("rCodeMedical");
			logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>In Ame Pending stuts Remark");
			String uri = mapUriToUserService.getUriForAmePendingStatusAma(rCode);
			
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
			//AmePendingStatus amePendingStatus1=amePendingStatusRepository.findByAmeId(ameId);
				//Optional<PhysicalMeasurement> optional2 = ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(ameId);
				
				Optional<AmePendingStatus> optional2 = ameAssessmentServicePart_2.getAmePendingStatusRemarkByAmeId(ameId);	
			
			AmePendingStatus amePendingStatus=new AmePendingStatus();
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
				amePendingStatus = optional2.get();
				//model.addAttribute("physicalMeasurement", physicalMeasurement);
				model.addAttribute("amePendingStatus", amePendingStatus);
			
			} else {
				//model.addAttribute("physicalMeasurement", physicalMeasurement);
				model.addAttribute("amePendingStatus", amePendingStatus);

			}
			
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
			model.addAttribute("candidateDetails", forcePersonal);
			model.addAttribute("age", age);
			model.addAttribute("doj", dateOfJoining);
			model.addAttribute("candidateForcePersonnelId", candidateforcepersonalId);
			//model.addAttribute("amePendingStatus1", amePendingStatus1);

			return uri;
		}

		
		
		
		//=========  Saving Method of pending status remarks of Ame 18-03-2025 =======
		@PostMapping("save-ame-pending-status")
		@ResponseBody
		public ResponseEntity<?> saveAmePendingStatus(@RequestBody Map<String, Object> data, HttpSession httpSession,
				HttpServletRequest request) {

			System.out.println("Recieved Data: " + data);
			Map<String, Object> response = new HashMap<>();
			Map<String, String> errors = new HashMap<>();
			try {

				ObjectMapper objectMapper = new ObjectMapper();
				
				AmePendingStatus amePendingStatus=objectMapper.convertValue(data.get("data"),
						AmePendingStatus.class);
				
				String gender = (String) data.get("gender");

				String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");
				String candidateForcePersonnelId = objectMapper.convertValue(data.get("candidateForcePersonnelId"),
						String.class);

				Optional<ForcePersonnel> candidateforcePersonnelDetailsOptional = forcePersonnelRespository
						.getByForcePersonnelId(candidateForcePersonnelId);
				ForcePersonnel candidateforcePersonnelDetails = candidateforcePersonnelDetailsOptional.get();
			

				
				Optional<AmeApplicationFlowStatus> ameFlow=ameApplicationFlowStatusRepo.findByAmeId(amePendingStatus.getAmeId());
				Optional<AmePendingStatus> optional2 = ameAssessmentServicePart_2.getAmePendingStatusRemarkByAmeId(amePendingStatus.getAmeId());	
				if (optional2.isPresent()) {
					AmePendingStatus amePendingStatusDb = optional2.get();
					amePendingStatusDb.setRemark(amePendingStatus.getRemark());
					amePendingStatusRepository.save(amePendingStatusDb);
					response.put("isValid", true);
					response.put("message", "Data updated successfully!");
					 
					return ResponseEntity.ok(response);
				}


				else
				{
							amePendingStatus.setCreatedOn(Calendar.getInstance().getTime());
							amePendingStatus.setBoardId(ameFlow.get().getBoardId());
							amePendingStatus.setAmeId(amePendingStatus.getAmeId());
							amePendingStatus.setCandidateForcePersonalId(candidateForcePersonnelId);
							amePendingStatus.setForcePersonalId(loggedInUserForcepersonnelId);
							amePendingStatus.setForceNo(candidateforcePersonnelDetails.getForceNo());
							amePendingStatus.setRemark(amePendingStatus.getRemark());
						//	amePendingStatus.setUnitNo(candidateforcePersonnelDetails.getUnit());
						
						
						

						amePendingStatusRepository.save(amePendingStatus);

						response.put("isValid", true);
						response.put("message", "Data updated successfully!");
						return ResponseEntity.ok(response);
						//logger.info(">>>>>>>>>>>>>>> Physical Measurement Updated Successfully");

					
				}
				

			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
						"Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");

			}

		}
		
		
		
		
		
		/*
		 * @RequestMapping(path = "modal-pending-status-remark-ama", method =
		 * RequestMethod.POST, produces = "application/json") public @ResponseBody
		 * ResponseEntity<AmePendingStatus> displayModelPendingStatus(@RequestBody
		 * MedicalRequestDto medicalRequestDto, HttpSession httpSession) {
		 * 
		 * String ameID = medicalRequestDto.getAmeId(); String forcepersonalID =
		 * medicalRequestDto.getForcepersonalId();
		 * 
		 * Optional<AmePendingStatus> optional2 =
		 * ameAssessmentServicePart_2.getAmePendingStatusRemarkByAmeId(ameID);
		 * AmePendingStatus amePendingStatusDb = optional2.orElse(new
		 * AmePendingStatus()); // Avoid NullPointerException if not found
		 * 
		 * // Assuming the AmePendingStatus object has several fields that we want to
		 * return return new ResponseEntity<>(amePendingStatusDb, HttpStatus.OK); }
		 */
		
		
		
		@RequestMapping(path="/modal-pending-status-remark-ama",method = RequestMethod.POST)
		public String getToFadorCAPFUserDashBoard(Model model,HttpSession httpSession ,@ModelAttribute("medicalRequestDto")MedicalRequestDto medicalRequestDto ) {
		   String loginForcePersonal=(String) httpSession.getAttribute("LoginForcePersonalId");
		   
		  
			 //  String loginForceName=(String) httpSession.getAttribute("loginForceName");
			//	 String userType=(String)httpSession.getAttribute("userType");
				//  model.addAttribute("loginForceName", loginForceName);
			//	  model.addAttribute("userType", userType);
		   
		   
			int rCode = (int) httpSession.getAttribute("rCodeMedical");
			logger.info("RCode>>>>>>>>>>>>>>>>>>"+rCode+">>In Ame Pending stuts Remark");
			//String uri = mapUriToUserService.getUriForAmePendingStatusAma(rCode);
			
			String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
			model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

			String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
			model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);

			String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		   
		   
				  String ameID = medicalRequestDto.getAmeId();
				  String forcePersonalID = medicalRequestDto.getForcepersonalId();
					ForcePersonnelDto forcePersonal = loginUserDetails.getCandicateForcePersonalId(forcePersonalID);

					Date dob = forcePersonal.getDob();
					int age = ageCalculatorService.getAge(dob);
					Date doj = forcePersonal.getJoiningDate();
					String dateOfJoining = ageCalculatorService.calculateYearsMothsandDays(doj);
					logger.info(">>>>>>>>>>>>>>>>>>>>>> age>>>>>>>>>" + age);
					logger.info(">>>>>>>>>>>>>>>>>>>>>> date of joining >>>>>>>>>" + dateOfJoining);

				    Optional<AmePendingStatus> optional2 = ameAssessmentServicePart_2.getAmePendingStatusRemarkByAmeId(ameID);
				    AmePendingStatus amePendingStatusDb = optional2.orElse(new AmePendingStatus()); // Avoid NullPointerException if not found

			  	   
			  	     model.addAttribute("amePendingStatusDb", amePendingStatusDb);
			  	   model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
					model.addAttribute("candidateDetails", forcePersonal);
					model.addAttribute("age", age);
					model.addAttribute("doj", dateOfJoining);
			  	  //   int rCode=(int) httpSession.getAttribute("rCode");
			  	 //  model.addAttribute("rCode",rCode);
			  	// model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonal.trim()));
				//	model.addAttribute("loginForcePersonalId", loginForcePersonal);
			return"admin-template/admin-ame-pending-remark";
		}
		

}
