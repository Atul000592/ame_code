package nic.ame.app.master.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;
import java.util.stream.Collectors;
import nic.ame.app.master.medical.model.ViralMakers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.RefMedicalExamType;
import nic.ame.app.admin.repository.RefMedicalExamTypeRepo;
import nic.ame.app.admin.repository.RefRoleMedicalRepo;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.board.member.repository.CheckUpListRepo;
import nic.ame.app.board.member.repository.MedicalCheckUpMasterRepo;
import nic.ame.app.esign.StringConstants;
import nic.ame.app.master.dto.AmeDetailsDto;
import nic.ame.app.master.dto.AmeFinalDetailDto;
import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.InvestigationDto;
import nic.ame.app.master.dto.RowData;
import nic.ame.app.master.medical.dto.InvestigationTestMastersForSubTestDto;
import nic.ame.app.master.medical.dto.SubTestDto;
import nic.ame.app.master.medical.model.AmeFinalReportDetails;
import nic.ame.app.master.medical.model.AmeFinalReportFileDir;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.AmeReviewCandidatesList;
import nic.ame.app.master.medical.model.BloodSugar;
import nic.ame.app.master.medical.model.BloodSugarF;
import nic.ame.app.master.medical.model.BloodSugarHbA1c;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.BloodSugarRandom;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.model.CompleteBloodCount;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.InvestigationReport;
import nic.ame.app.master.medical.model.KidneyFunctionTest;
import nic.ame.app.master.medical.model.Lipid;
import nic.ame.app.master.medical.model.LiverFunctionTest;
import nic.ame.app.master.medical.model.MedicalCheckUpMaster;
import nic.ame.app.master.medical.model.OtherTest;
import nic.ame.app.master.medical.model.OtherTestReportParameters;
import nic.ame.app.master.medical.model.Others;
import nic.ame.app.master.medical.model.ThyroidProfile;
import nic.ame.app.master.medical.model.UrineTestPhysicalMicroscopic;
import nic.ame.app.master.medical.service.AmeFinalDetailService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_1_impl;
import nic.ame.app.master.model.AlertAndNotification;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ApplicationStateDescription;
import nic.ame.app.master.model.FinalCategoryRemarkTemp;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.go.AmeFinalReportDetailsGo;
import nic.ame.app.master.model.go.AmeFinalReportFileDirGo;
import nic.ame.app.master.model.go.repository.AmeFinalReportDetailsGoRepository;
import nic.ame.app.master.model.go.repository.AmeFinalReportFileDirGoRepository;
import nic.ame.app.master.ref.entity.RefInvestigationSubTestMaster;
import nic.ame.app.master.ref.entity.RefInvestigationTestMaster;
import nic.ame.app.master.ref.entity.repo.RefInvestigationSubTestMasterRepo;
import nic.ame.app.master.repository.AlertAndNotificationRepository;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.AmeFinalReportDetailsRepository;
import nic.ame.app.master.repository.AmeFinalReportFileDirRepository;
import nic.ame.app.master.repository.AmeReviewCandidatesListRepository;
import nic.ame.app.master.repository.ApplicationStateDescriptionRepository;
import nic.ame.app.master.repository.BloodSugarFRepository;
import nic.ame.app.master.repository.BloodSugarHbA1cRepository;
import nic.ame.app.master.repository.BloodSugarPPRepository;
import nic.ame.app.master.repository.BloodSugarRandomRepository;
import nic.ame.app.master.repository.BloodSugarRepository;
import nic.ame.app.master.repository.CompleteBloodCountCBCRepository;
import nic.ame.app.master.repository.FinalCategoryRemarkTempRepo;
import nic.ame.app.master.repository.KidneyFunctionTestKFTRepository;
import nic.ame.app.master.repository.LipidRepository;
import nic.ame.app.master.repository.LiverFunctionTestRepository;
import nic.ame.app.master.repository.OthersTestRepository;
import nic.ame.app.master.repository.ThyroidProfileRepository;
import nic.ame.app.master.repository.UrineTestPhysicalMicroscopicRespository;
import nic.ame.app.master.repository.ViralMakersRepository;
import nic.ame.app.master.service.AmeDetailsService;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.app.sms.SMSConfigurationConstantsAME;
import nic.ame.app.sms.SMSTemplateDto;
import nic.ame.app.sms.SMSTemplateService;
import nic.ame.app.sms.SmsResponse;
import nic.ame.constant.CommonConstant;
import nic.ame.constant.RegexValidation;
import nic.ame.master.util.CreateAlertAndNotification;
import nic.ame.master.util.CreateFileDirectory;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.UserDateUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class InvestigationMasterController {

      private Logger logger=LoggerFactory.getLogger(InvestigationMasterController.class);
	@Autowired
	private InvestigationMasterService investigationMasterService;

	@Autowired
	private ForcePersonnelService forcePersonalService;
	@Autowired
	private CheckUpListRepo checkUpListRepo;

	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;

	@Autowired
	private RefInvestigationSubTestMasterRepo investigationSubTestMasterRepo;

	@Autowired
	private AmeMasterStatusService ameMasterStatusService;

	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;

	@Autowired
	private AmeFinalDetailService ameFinalDetailService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private MapUriToUserService mapUriToUserService;

	@Autowired
	private BloodSugarRepository bloodSugarRepository;

	@Autowired
	private KidneyFunctionTestKFTRepository kidneyFunctionTestKFTRepository;

	@Autowired
	private LiverFunctionTestRepository liverFunctionTestRepository;

	@Autowired
	private LipidRepository lipidRepository;

	@Autowired
	private CompleteBloodCountCBCRepository completeBloodCountCBCRepository;

	@Autowired
	private ViralMakersRepository viralMakersRepository;

	@Autowired
	private OthersTestRepository othersTestRepository;

	@Autowired
	private UrineTestPhysicalMicroscopicRespository urineTestPhysicalMicroscopicRespository;

	@Autowired
	private ThyroidProfileRepository thyroidProfileRepository;

	@Autowired
	private AmeAssessmentServicePart_1_impl ameAssessmentServicePart_1_impl;
	
	@Autowired
	private AmeFinalReportFileDirRepository ameFinalReportFileDirRepository;
	
	@Autowired
	AmeDeclarationRepository ameDeclarationRepository;
	
	@Autowired
	ForcePersonnelService forcePersonnelService;
	
	@Autowired
	AmeDetailsService ameDetailsService;
	
	@Autowired
	private RefRoleMedicalRepo refRoleMedicalRepo; 
	@Autowired
	private BloodSugarPPRepository bloodSugarPPRepository;
	@Autowired
	private BloodSugarFRepository bloodSugarfRepository;
	
	@Autowired
	private BloodSugarRandomRepository bloodSugarRandomRepository;
	@Autowired
	BloodSugarHbA1cRepository bloodSugarHbA1cRepository;
	
	@Autowired
	private SMSTemplateService smsTemplateService;
	
	@Autowired
	private AmeReviewCandidatesListRepository ameReviewCandidatesListRepository;
	
	@Autowired 
	private AmeFinalReportDetailsRepository ameFinalReportDetailsRepository;
	
	@Autowired
	private FinalCategoryRemarkTempRepo finalCategoryRemarkTempRepo;
	
	@Autowired
	private  ApplicationStateDescriptionRepository applicationStateDescriptionRepository;
	
	@Autowired
	private  RefMedicalExamTypeRepo medicalExamTypeRepo;
	
	@Autowired
	private AlertAndNotificationRepository alertAndNotificationRepository;
	
	@Autowired
	private  CreateAlertAndNotification createAlertAndNotification;
	
	
	@Autowired
	private MedicalCheckUpMasterRepo medicalCheckUpMasterRepo;
	
	@Autowired
	private AmeFinalReportFileDirGoRepository ameFinalReportFileDirGoRepository;
	
	@Autowired
	private AmeFinalReportDetailsGoRepository ameFinalReportDetailsGoRepository;

	
	/*
	 * ================================upload-investigation-final-report-form=======
	 * ===============
	 */
	@PostMapping("upload-investigation-final-report-form")
	public ResponseEntity<?> uploadInvestigationFinalReportForm(@RequestParam("testCode") String testCode,
			@RequestParam("ameId") String ameId) {
		Map<String, Object> map = new HashMap<>();
		List<RefInvestigationTestMaster> investigationTestMasters = new ArrayList<>();
		investigationTestMasters = investigationMasterService.refInvestigationTestMastersListByTestCode(testCode);

		List<RefInvestigationTestMaster> investigationTestMastersForSubTestList = new ArrayList<>();
		investigationTestMastersForSubTestList = investigationTestMasters.stream()
				.filter(s -> Integer.parseInt(s.getSubtestFlag()) == 1).collect(Collectors.toList());
		List<InvestigationTestMastersForSubTestDto> testMastersForSubTestDtosList = new ArrayList<>();

		if (investigationTestMastersForSubTestList.size() > 0) {
			for (RefInvestigationTestMaster refInvestigationTestMaster : investigationTestMastersForSubTestList) {

				InvestigationTestMastersForSubTestDto investigationTestMastersForSubTestDto = new InvestigationTestMastersForSubTestDto();

				List<RefInvestigationSubTestMaster> investigationSubTestMaster = investigationSubTestMasterRepo
						.findBySubTestCode(refInvestigationTestMaster.getSubTestCode());

				investigationTestMastersForSubTestDto.setTestCode(refInvestigationTestMaster.getTestCode());
				investigationTestMastersForSubTestDto.setTestName(refInvestigationTestMaster.getTestName());
				List<SubTestDto> subTestDtoList = new ArrayList<>();

				for (RefInvestigationSubTestMaster investigationSubTestMaste1r : investigationSubTestMaster) {

					SubTestDto subTestDto = new SubTestDto();
					subTestDto.setTestCode(investigationSubTestMaste1r.getSubtestcodeName());
					subTestDto.setTestName(investigationSubTestMaste1r.getTestName());
					subTestDtoList.add(subTestDto);
					investigationTestMastersForSubTestDto.setSubTestDtos(subTestDtoList);
				}
				testMastersForSubTestDtosList.add(investigationTestMastersForSubTestDto);

			}
			System.out.println("================================================================================");
			testMastersForSubTestDtosList.forEach(System.out::println);
			map.put("testMastersForSubTestDtosList", testMastersForSubTestDtosList);

		} else {
			map.put("testMastersForSubTestDtosList", testMastersForSubTestDtosList);
		}

		List<RefInvestigationTestMaster> investigationTestMastersMain = new ArrayList<>();
		investigationTestMastersMain = investigationTestMasters.stream()
				.filter(s -> Integer.parseInt(s.getSubtestFlag()) == 0).collect(Collectors.toList());
		map.put("investigationTestMasters", investigationTestMastersMain);

		map.put("ameId", ameId);
		map.put("testCode", testCode.trim());
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	/*
	 * ================================upload-investigation-final-report-form=======
	 * ===============
	 */
	@PostMapping("upload-investigation-final-report-form-final")
	public ResponseEntity<?> uploadInvestigationFinalReportFormFinal(@RequestParam("file") MultipartFile file,
			@RequestParam("jsonData") String jsonData, @RequestParam("ameId") String ameId,
			@RequestParam("testCodeId") String testCodeId) throws IOException {

		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);

		List<InvestigationReport> investigationReportsList = new ArrayList<>();

		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				InvestigationReport investigationReport = new InvestigationReport();
				String key = entry.getKey();
				String value = entry.getValue();
				investigationReport.setSubTestCode(key.trim());
				investigationReport.setValue(value.trim());
				investigationReport.setTestCode(testCodeId);
				investigationReport.setAmeId(ameId.trim());
				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);
				investigationReportsList.add(investigationReport);
			}

		}
		FileUploadDto fileUploadDto = new FileUploadDto();
		fileUploadDto.setFileContent(file.getBytes());
		fileUploadDto.setFileName(file.getOriginalFilename());
		fileUploadDto.setFileSize(String.valueOf(file.getSize()));
		fileUploadDto.setFileType(file.getContentType());

		boolean result = investigationMasterService.saveInvestigationReport(investigationReportsList, fileUploadDto,
				ameId, testCodeId.trim());

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PostMapping("save-test")
	@ResponseBody
	public ResponseEntity<?> submitData(@RequestBody String data) {

		Map<String, Object> response = new HashMap<>();
		try {

			// Deserialize JSON data into CheckUpList object
			ObjectMapper objectMapper = new ObjectMapper();
			CheckUpList checkUpList = objectMapper.readValue(data, CheckUpList.class);

			System.out.println("data received: " + data);
			// Access properties and do whatever you need with them
			String ameId = checkUpList.getAmeId();
			String testName = checkUpList.getTestName();
			String testCode = checkUpList.getTestCode().trim();
			/*
			 * String candidateForcePersonalId = checkUpList.getCandidateForcePersonalId();
			 */
			List<CheckUpList> checkUpLists = checkUpListRepo.findByAmeId(ameId);

			if (checkUpLists != null && !checkUpLists.isEmpty()) {
				boolean found = false;
				for (CheckUpList checkUpListItr : checkUpLists) {
					if (checkUpListItr.getTestCode().equalsIgnoreCase(testCode)) {
						found = true;
						break;
						/*
						 * response.put("responseMessage","Test Already Added To The List");
						 * response.put("responseItemList",checkUpLists); response.put("ameId",ameId);
						 */
						/* response.put("candidateForcePersonalId",candidateForcePersonalId); */

					}
				}

				if (!found) {
					checkUpList.setTestCode(testCode);
					checkUpListRepo.save(checkUpList);
					response.put("responseMessage", "Test Successfully Added To The List");
					response.put("data", data);
					response.put("ameId", ameId);
				} else {
					response.put("responseMessage", "Test Already Added To The List");
					response.put("ameId", ameId);
				}

			}

			else {
				checkUpList.setTestCode(testCode);
				checkUpListRepo.save(checkUpList);
				response.put("responseMessage", "Test Successfully Added To The List");
				response.put("data", data);
				response.put("ameId", ameId);
			} /* response.put("candidateForcePersonalId",candidateForcePersonalId); */

		} catch (Exception e) {
			e.printStackTrace();

		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

	}

	@PostMapping("print-investigation-report")
	public String printInvestigationReport(@RequestParam("ameId") String ameId, Model model) {
		Map<String, Map<String, String>> investigationReportDtosList = investigationMasterService
				.findInvestigationReportByAmeId(ameId);
		model.addAttribute("investigationReportDtosList", investigationReportDtosList);
		model.addAttribute("ameId", ameId);

		return "medical-sub-ordinate/print-investigation-report-page";

	}

	@PostMapping("final-submit-checkup-list")
	@Transactional
	public ResponseEntity<?> finalSubmit(@RequestBody List<RowData> checkedRows,HttpSession httpSession,HttpServletRequest request) {

		 int rCode= (int) httpSession.getAttribute("rCodeMedical");
		 logger.info("rCode>>>>>>>>>"+rCode);
	     String LoginforcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");
	     String ipAddress=GetIpAddressClient.getIpAddressFromHeaderClient(request);

		 List<CheckUpList> checkUpLists=new ArrayList<>();
         for (RowData rowData : checkedRows) {
        	 CheckUpList checkUpList=new CheckUpList();
        	 checkUpList.setAmeId(rowData.getAmeId());
        	 checkUpList.setTestCode(rowData.getCode());
        	 checkUpList.setTestName(rowData.getName());
        	 checkUpList.setUploadFlag(0);
        	 checkUpLists.add(checkUpList);
         }
		 
             investigationMasterService.saveCheckUpListForCandidate(checkUpLists,LoginforcepersonnelId,ipAddress);
         
			Optional<String> getRoleCode = refRoleMedicalRepo.findRoleNameById(rCode);

			String uri = null;

			String roleCode;

			if (!getRoleCode.isEmpty()) {
				roleCode = getRoleCode.get();
				if(roleCode.equals("mb1")||roleCode.equals("mb2"))
					roleCode="bm";
				if(roleCode.equals("po"))
					roleCode="bm";
					
			} else {
				roleCode = "na";
			}
			
			
		 
			if(roleCode.equalsIgnoreCase("dhm"))
				 uri ="dealing-hand-dashboard";
			 else if (roleCode.equalsIgnoreCase("ama"))
				uri="display-pending-requests-upload-checkup";
			 else if(roleCode.equals("bm"))
				 uri="display-pending-requests-upload-checkup";
		 
			
			
			
			Map<String, String> response=new HashMap<>();
			response.put("uri", uri);
			response.put("message","Redirection to main page");
		 
			
			return ResponseEntity.status(HttpStatus.OK).body(response);
		 

	}

	@PostMapping("final-details-submit")

	public ResponseEntity<?> finalDetailsSubmit(@RequestBody String ameId) {

		try {
			System.out.println("Recieved Data: " + ameId);

			AmeMasterStatus ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);
			List<FinalCategoryRemarkTemp> finalCategoryRemarkTemp = finalCategoryRemarkTempRepo.findByAmeId(ameId);
			String candidateForcePersonalId = ameMasterStatus.getForcePersonalId();
			Optional<ForcePersonnelDto> candidateForcepersonalDetailsOptional = forcePersonalService.getForcePersonnelDetailsByForcePersonnelId(candidateForcePersonalId);
			String gender = candidateForcepersonalDetailsOptional.get().getGender();
			List<CheckUpList> checkUpListItems = checkUpListRepo.findCheckUpListByAmeId(ameId,CommonConstant.ACTIVE_FLAG_NO);
			List<String> testNameList = checkUpListItems.stream().filter(i -> i.getUploadFlag() == 0)
					.map(CheckUpList::getTestName).collect(Collectors.toList());

			Map<String, Object> response = new HashMap<>();
			Map<String, Object> errors = new HashMap<>();
			List<String> categoryRemarkList= new ArrayList<>();
//		  =========================TestName=====================================================
			String testNameListConcat = String.join(",", testNameList);
			if (testNameListConcat.length() > 0) {
				errors.put("testNameListConcat", testNameListConcat);
				logger.info(testNameListConcat);
			}
//		  ========================Psychology=====================================================	  
			if (ameMasterStatus.getPsycological_shape() == null || ameMasterStatus.getPsycological_shape().isEmpty()
					|| ameMasterStatus.getPhychologicalAssessmentAsLaidDown() == null
					|| ameMasterStatus.getPhychologicalAssessmentAsLaidDown().isEmpty()) {
				errors.put("psychology", "Psychology is not filled!");
				logger.info("psychology");
			} 
			else {

				String psychologyShape[] = ameMasterStatus.getPsycological_shape().split("/");
				String psychologyCategory = psychologyShape[0];
			
				if (!psychologyCategory.equals("S-1")) {
					if(finalCategoryRemarkTemp.stream().noneMatch(i->i.getCategoryType().equalsIgnoreCase("S"))){
						categoryRemarkList.add("PSYCHOLOGY category");
						logger.info("PSYCHOLOGY category");
						//errors.put("categoryType", "Please give remark for psychology category.");
					}

					
				}
			}

//		  ========================Hearing=====================================================	  
			if (ameMasterStatus.getHearingShape() == null || ameMasterStatus.getHearingShape().isEmpty()
					|| ameMasterStatus.getHearing() == null || ameMasterStatus.getHearing().isEmpty()) {
				errors.put("hearing", "Hearing is not filled!");
				logger.info("Hearing is not filled!");

			} 
			else {
				String HearingShape[] = ameMasterStatus.getHearingShape().split("/");
				String hearingCategory = HearingShape[0];
				System.out.println("hearingCategory: " + hearingCategory);
				if (!hearingCategory.equals("H-1")) {
					if(finalCategoryRemarkTemp.stream().noneMatch(i->i.getCategoryType().equalsIgnoreCase("H"))){
						categoryRemarkList.add("HEARING category");
						logger.info("HEARING category");
						//errors.put("categoryType", "Please give remark for hearing category.");
					}
					
				}

			}

//	  ========================Appendages=====================================================
			if (ameMasterStatus.getUpperLimbShape() == null || ameMasterStatus.getUpperLimbShape().isEmpty()
					|| ameMasterStatus.getLowerLimbShape() == null || ameMasterStatus.getLowerLimbShape().isEmpty()
					|| ameMasterStatus.getAppendages() == null || ameMasterStatus.getAppendages().isEmpty()) {
				errors.put("appendages", "Appendages is not filled!");
				logger.info("Appendages is not filled!");

			} 
			else {
				String upperLimbShape[] = ameMasterStatus.getUpperLimbShape().split("/");
				String lowerLimbShape[] = ameMasterStatus.getLowerLimbShape().split("/");
				String spineShape[] = ameMasterStatus.getSpineShape().split("/");

				String upperLimbCategory = upperLimbShape[0];
				String lowerLimbCaString = lowerLimbShape[0];
				String spineCategory = spineShape[0];

				if (!upperLimbCategory.equals("A-1(U)")) {
					if(finalCategoryRemarkTemp.stream().noneMatch(i->i.getCategoryType().equalsIgnoreCase("UA"))){
						categoryRemarkList.add("UPPER LIMB category");
						logger.info("UPPER LIMB category");
						//errors.put("categoryType", "Please give remark for upperLimb category.");
					}
					//errors.put("upperLimbCategory", "Upper Limb Category is not A-1.");
				}
				if (!lowerLimbCaString.equals("A-1(L)")) {
					if(finalCategoryRemarkTemp.stream().noneMatch(i->i.getCategoryType().equalsIgnoreCase("LA"))){
						categoryRemarkList.add("LOWER LIMB category");
						logger.info("LOWER LIMB category");
						//errors.put("categoryType", "Please give remark for lowerLimb category.");
					}
					//errors.put("lowerLimbCategory", "Lower Limb Category is not A-1.");
				}
				if (!spineCategory.equals("A-1(S)")) {
					if(finalCategoryRemarkTemp.stream().noneMatch(i->i.getCategoryType().equalsIgnoreCase("SA"))){
						categoryRemarkList.add("LOWER category");
						logger.info("LOWER category");
						//errors.put("categoryType", "Please give remark for spine category.");
					}
					//errors.put("spineCategory", "Spine Category is not A-1.");
				}

			}

//	  ========================Physical=====================================================	  
			if (ameMasterStatus.getPhysicalShape() == null || ameMasterStatus.getPhysicalShape().isEmpty()
					|| ameMasterStatus.getPhysicalMeasurement() == null
					|| ameMasterStatus.getPhysicalMeasurement().isEmpty()) {
				errors.put("physical", "Physical is not filled!");
				logger.info("Physical is not filled!");

			} 
			else {
				String physicalShape[] = ameMasterStatus.getPhysicalShape().split("/");
				String physicalCategory = physicalShape[0];
				System.out.println("psychologyCategory: " + physicalCategory);
				if (!physicalCategory.equals("P-1")) {
					if(finalCategoryRemarkTemp.stream().noneMatch(i->i.getCategoryType().equalsIgnoreCase("P"))){
						categoryRemarkList.add("PHYSICAL category");
						logger.info("PHYSICAL category!");						//errors.put("categoryType", "Please give remark for spine category.");
					}
					//errors.put("physicalCategory", "Physical Category Category is not S-1.");
				}

			}

//	  ========================Eye=====================================================	  
			if (ameMasterStatus.getEyeShape() == null || ameMasterStatus.getEyeShape().isEmpty()) {
				errors.put("Eye", "Eye is not filled!");
				logger.info("Eye is not filled!");	
			} 
			else {
				String eyeShape[] = ameMasterStatus.getEyeShape().split("/");

				String eyeCategory = eyeShape[0];

				if (!eyeCategory.equals("E-1")) {
					if(finalCategoryRemarkTemp.stream().noneMatch(i->i.getCategoryType().equalsIgnoreCase("E"))){
						categoryRemarkList.add("EYE category");
						logger.info("Eye category");	
						//errors.put("categoryType", "Please give remark for spine category.");
					}
					//errors.put("eyeCategory", " Eye Category is not E-1.");
				}

			}
				 

//	  ========================Gynaecology=====================================================	  
			if (gender.equalsIgnoreCase("Female")) {
				if (ameMasterStatus.getGynaecologyShape() == null || ameMasterStatus.getGynaecologyShape().isEmpty()
						|| ameMasterStatus.getGynaecologyObs() == null
						|| ameMasterStatus.getGynaecologyObs().isEmpty()) {
					errors.put("gynaecology", "Gynaecology is not filled!");
					logger.info("Gynaecology is not filled!");	

				} 
				else {
					String gynaecologyShape[] = ameMasterStatus.getGynaecologyShape().split("/");
					String gynaecologyCategory = gynaecologyShape[0];
					if (!gynaecologyCategory.equals("G-1")) {
						if(finalCategoryRemarkTemp.stream().noneMatch(i->i.getCategoryType().equalsIgnoreCase("G"))){
							categoryRemarkList.add("GYNAECOLOGY category");
							logger.info("GYNAECOLOGY category");	
							//errors.put("categoryType", "Please give remark for spine category.");
						}
						//errors.put("gynaecologyCategory", "Gynaecology Category is not G-1.");
					}

				}
					 
			}

//	  ========================Physical Measurement=====================================================  
			if (ameMasterStatus.getPhysicalMeasurement() == null
					|| ameMasterStatus.getPhysicalMeasurement().isEmpty()) {
				errors.put("physicalMeasurement", "Physical measurement is not filled!");
				logger.info("Physical measurement is not filled!");

			}

//	  ========================General Examination=====================================================  
			if (ameMasterStatus.getGeneralExamination() == null || ameMasterStatus.getGeneralExamination().isEmpty()) {
				errors.put("generalExamination", "General examination is not filled!");
				logger.info("General examination is not filled!");

			}
//	  ===========================================CNS=====================================================  
			if (ameMasterStatus.getCns() == null || ameMasterStatus.getCns().isEmpty()
					|| ameMasterStatus.getCranialnervesmeningealsign() == null
					|| ameMasterStatus.getCranialnervesmeningealsign().isEmpty()) {
				errors.put("cns", "cns is not filled!");
				logger.info("cns is not filled");
			}
			if(categoryRemarkList.size()>0) {
				errors.put("categoryRemarkList",categoryRemarkList);
				logger.info("categoryRemarkList");
			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				logger.info("Error occured:isValid:false");
				return ResponseEntity.ok(response);

			} else {

				response.put("isValid", true);
				logger.info("Error occured:isValid:true");
				return ResponseEntity.ok(response);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return null;

	}

	@PostMapping("save-ame-final-details")
	public String saveAmeFinalDetails(@RequestParam("ameId") String ameId,
			                           @RequestParam("finalShapeAwarded") String finalShapeAwarded,
			                           @RequestParam("remark") String remark,
			                           @RequestParam("categoryDownCode12") int categoryDownCode12,
			                           @RequestParam("categoryDownCode24") int categoryDownCode24,
			                           HttpServletRequest httpServletRequest,HttpSession httpSession) 
	{
		AmeMasterStatus ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);
		String boardId = ameMasterStatus.getBoardId();
		String candidateForcePersonalId = ameMasterStatus.getForcePersonalId();

		AmeFinalDetailDto ameFinalDetailDto = new AmeFinalDetailDto();
		// =============Finding Candidate Force Personal Details==================//
        String medicalRoleCode=String.valueOf(httpSession.getAttribute("rCodeMedical"));
        String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");
        
		Optional<ForcePersonnelDto> candidateForcepersonalDetailsOptional = forcePersonalService
				.getForcePersonnelDetailsByForcePersonnelId(candidateForcePersonalId);
		if (!candidateForcepersonalDetailsOptional.isEmpty()) {
			ameFinalDetailDto
					.setCandidateForcePersonalId(candidateForcepersonalDetailsOptional.get().getForcePersonalId());
			ameFinalDetailDto.setCandidateIrlaNo(candidateForcepersonalDetailsOptional.get().getForceId());

		}
		ameFinalDetailDto.setAmeFinalCategoryAwarded(finalShapeAwarded);
		ameFinalDetailDto.setAmeId(ameId);
		ameFinalDetailDto.setBoardId(boardId);
		ameFinalDetailDto.setCreatedOn(Calendar.getInstance().getTime());
		ameFinalDetailDto.setStatus(1);
		ameFinalDetailDto.setCandidateIrlaNo(candidateForcePersonalId);
		ameFinalDetailDto.setIpClientAddress(GetIpAddressClient.getIpAddressFromHeaderClient(httpServletRequest));
		ameFinalDetailDto.setRemark(remark);

		// finding board member details
		List<MedicalBoardMemberDto> boardMemberDtos = medicalBoardMemberService
				.getListOfAllBoardMembersByBoardIdOnlyActiveMember(boardId);

		logger.info(">>>>>>" + ameFinalDetailDto);
		logger.info(">>>>>>Medical_ROLE_CODE : "+medicalRoleCode);
		
		if(getRoleNameByMedicalRoleCodeStatus(medicalRoleCode)) {
			logger.info("Saving data for PO & BM.....!");
			ameFinalDetailService.saveAmeFinalDetailGO(ameFinalDetailDto, boardMemberDtos, 
					categoryDownCode12, categoryDownCode24, medicalRoleCode,loggedInUserForcepersonnelId);
		}else {
			logger.info("Saving data for AMA......!");
			ameFinalDetailService.saveAmeFinalDetail(ameFinalDetailDto, boardMemberDtos,categoryDownCode12,categoryDownCode24);

		}
		

		return "redirect:/application-under-process-ma";
	}

	@PostMapping("save-blood-sugar")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveBloodSugar(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		// String specialCharacterRegex = ".*[!@#$%^&*(),.?:{}|<>].*";
		String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
		

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		BloodSugar bloodSugar = new BloodSugar();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					bloodSugar.setAmeId(value);
				}

				if (key.equalsIgnoreCase("bloodSugarPP")) {
					bloodSugar.setBloodSugarPP(value);
				}
				if (key.equalsIgnoreCase("bloodSugarF")) {
					bloodSugar.setBloodSugarF(value);
				}
				if (key.equalsIgnoreCase("bloodSugarRandom")) {
					bloodSugar.setBloodSugarRandom(value);
				}
				if (key.equalsIgnoreCase("HbA1c")) {
					bloodSugar.setHbA1c(value);
				}
				if (key.equalsIgnoreCase("testCode")) {
					bloodSugar.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {

			if (bloodSugar.getBloodSugarPP().isBlank() || bloodSugar.getBloodSugarPP().isEmpty()
					
					|| !bloodSugar.getBloodSugarPP().matches(specialCharacterRegex)) {
				errors.put("bloodSugarPP", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (bloodSugar.getBloodSugarF().isBlank() || bloodSugar.getBloodSugarF().isEmpty()
					
					||! bloodSugar.getBloodSugarF().matches(specialCharacterRegex)) {
				errors.put("bloodSugarF", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + bloodSugar.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						bloodSugar.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Blood Sugar";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Blood Sugar");

				

				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					String savePath = testNameFolderCreated + File.separator + bloodSugar.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Blood Sugar.........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					bloodSugar.setLastModifiedOn(Calendar.getInstance().getTime());
					bloodSugar.setLastModifiedBy(loggedInUserForcepersonnelId);
					bloodSugar.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
                    bloodSugar.setFileName(fileUploadDto.getFileName());
					bloodSugar.setFileType(fileUploadDto.getFileType());
					bloodSugar.setPath(savePath);
					bloodSugar.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					investigationMasterService.saveBloodSugar(bloodSugar);
					checkUpListRepo.updateFileUploadStatus(bloodSugar.getAmeId(), bloodSugar.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Blood Sugar.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	@PostMapping("validate-and-save-blood-sugar-pp")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndBloodSugarPP(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		// String specialCharacterRegex = ".*[!@#$%^&*(),.?:{}|<>].*";
		String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
		

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		BloodSugarPP bloodSugarpp = new BloodSugarPP();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					bloodSugarpp.setAmeId(value);
				}

				if (key.equalsIgnoreCase("bloodSugarPP")) {
					bloodSugarpp.setBloodSugarPP(value);
				}
				if (key.equalsIgnoreCase("testCode")) {
					bloodSugarpp.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {


			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + bloodSugarpp.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						bloodSugarpp.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Blood Sugar";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Blood Sugar");

				

				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					String savePath = testNameFolderCreated + File.separator + bloodSugarpp.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Blood Sugar.........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					bloodSugarpp.setLastModifiedOn(Calendar.getInstance().getTime());
					bloodSugarpp.setLastModifiedBy(loggedInUserForcepersonnelId);
					bloodSugarpp.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					bloodSugarpp.setFileName(fileUploadDto.getFileName());
					bloodSugarpp.setFileType(fileUploadDto.getFileType());
					bloodSugarpp.setPath(savePath);
					bloodSugarpp.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					bloodSugarPPRepository.save(bloodSugarpp);
					checkUpListRepo.updateFileUploadStatus(bloodSugarpp.getAmeId(), bloodSugarpp.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Blood Sugar.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	
//	---------------bsf--------------------
	@PostMapping("validate-and-save-blood-sugar-f")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndBloodSugarF(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
		

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		BloodSugarF bloodSugarf = new BloodSugarF();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					bloodSugarf.setAmeId(value);
				}

				if (key.equalsIgnoreCase("bloodSugarF")) {
					bloodSugarf.setBloodSugarF(value);
				}
				if (key.equalsIgnoreCase("testCode")) {
					bloodSugarf.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {


			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + bloodSugarf.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						bloodSugarf.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Blood Sugar-F";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Blood Sugar-F");

				

				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					String savePath = testNameFolderCreated + File.separator + bloodSugarf.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Blood Sugar.........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					bloodSugarf.setLastModifiedOn(Calendar.getInstance().getTime());
					bloodSugarf.setLastModifiedBy(loggedInUserForcepersonnelId);
					bloodSugarf.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					bloodSugarf.setFileName(fileUploadDto.getFileName());
					bloodSugarf.setFileType(fileUploadDto.getFileType());
					bloodSugarf.setPath(savePath);
					bloodSugarf.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					bloodSugarfRepository.save(bloodSugarf);
					checkUpListRepo.updateFileUploadStatus(bloodSugarf.getAmeId(), bloodSugarf.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Blood Sugar F.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
//	---------------------bsf random-------------------
	
	@PostMapping("validate-and-save-blood-sugar-random")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndBloodSugarRandom(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
		

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		BloodSugarRandom bloodSugarRandom = new BloodSugarRandom();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					bloodSugarRandom.setAmeId(value);
				}

				if (key.equalsIgnoreCase("bloodSugarRandom")) {
					bloodSugarRandom.setBloodSugarRandom(value);
				}
				if (key.equalsIgnoreCase("testCode")) {
					bloodSugarRandom.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {


			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + bloodSugarRandom.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						bloodSugarRandom.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Blood Sugar-Random";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Blood Sugar-Random");

				

				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					String savePath = testNameFolderCreated + File.separator + bloodSugarRandom.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Blood Sugar.........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					bloodSugarRandom.setLastModifiedOn(Calendar.getInstance().getTime());
					bloodSugarRandom.setLastModifiedBy(loggedInUserForcepersonnelId);
					bloodSugarRandom.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					bloodSugarRandom.setFileName(fileUploadDto.getFileName());
					bloodSugarRandom.setFileType(fileUploadDto.getFileType());
					bloodSugarRandom.setPath(savePath);
					bloodSugarRandom.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					bloodSugarRandomRepository.save(bloodSugarRandom);
					checkUpListRepo.updateFileUploadStatus(bloodSugarRandom.getAmeId(), bloodSugarRandom.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Blood Sugar F.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	

	
	
//	---------------------bs HbA1c-------------------  //
	
	@PostMapping("validate-and-save-blood-sugar-HbA1c")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndBloodSugarHbA1c(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
		

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		BloodSugarHbA1c bloodSugarHbA1c = new BloodSugarHbA1c();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					bloodSugarHbA1c.setAmeId(value);
				}

				if (key.equalsIgnoreCase("bloodSugarHbA1c")) {
					bloodSugarHbA1c.setHbA1c(value);
				}
				if (key.equalsIgnoreCase("testCode")) {
					bloodSugarHbA1c.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {


			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + bloodSugarHbA1c.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						bloodSugarHbA1c.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Blood Sugar-HbA1c";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Blood Sugar-HbA1c");

				

				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					String savePath = testNameFolderCreated + File.separator + bloodSugarHbA1c.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Blood Sugar-HbA1c.........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					bloodSugarHbA1c.setLastModifiedOn(Calendar.getInstance().getTime());
					bloodSugarHbA1c.setLastModifiedBy(loggedInUserForcepersonnelId);
					bloodSugarHbA1c.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					bloodSugarHbA1c.setFileName(fileUploadDto.getFileName());
					bloodSugarHbA1c.setFileType(fileUploadDto.getFileType());
					bloodSugarHbA1c.setPath(savePath);
					bloodSugarHbA1c.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					bloodSugarHbA1cRepository.save(bloodSugarHbA1c);
					checkUpListRepo.updateFileUploadStatus(bloodSugarHbA1c.getAmeId(), bloodSugarHbA1c.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Blood Sugar F.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	
	
	@PostMapping("save-lft")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveLft(@RequestParam("file") MultipartFile file,
			@RequestParam("jsonData") String jsonData,HttpSession httpSession,HttpServletRequest request) {
		
		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		// String specialCharacterRegex = ".*[!@#$%^&*(),.?:{}|<>].*";
		String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		
//System.out.println("Data received: "+jsonData);
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();

		LiverFunctionTest liverFunctionTest = new LiverFunctionTest();
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					liverFunctionTest.setAmeId(value);
				}

				if (key.equalsIgnoreCase("bilirubin")) {
					liverFunctionTest.setBilirubin(value);
				}
				if (key.equalsIgnoreCase("directBilirubin")) {
					liverFunctionTest.setDirectBilirubin(value);
				}
				if (key.equalsIgnoreCase("indirectBilirubin")) {
					liverFunctionTest.setIndirectBilirubin(value);
				}

				if (key.equalsIgnoreCase("sGOT")) {
					liverFunctionTest.setsGOT(value);
				}
				if (key.equalsIgnoreCase("sGPT")) {
					liverFunctionTest.setsGPT(value);
				}
				if (key.equalsIgnoreCase("aLP")) {
					liverFunctionTest.setaLP(value);
				}
				if (key.equalsIgnoreCase("totalProtein")) {
					liverFunctionTest.setTotalProtein(value);
				}
				if (key.equalsIgnoreCase("ggpt")) {
					liverFunctionTest.setGGTP(value);
				}
				if (key.equalsIgnoreCase("albumin")) {
					liverFunctionTest.setAlbumin(value);
				}
				if (key.equalsIgnoreCase("globulin")) {
					liverFunctionTest.setGlobulin(value);
				}
				if (key.equalsIgnoreCase("AGRatio")) {
					liverFunctionTest.setAGRatio(value);
				}

				if (key.equalsIgnoreCase("testCode")) {
					liverFunctionTest.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}

		try {

			if (liverFunctionTest.getBilirubin().isBlank() || liverFunctionTest.getBilirubin().isEmpty()) {
				errors.put("bilirubin", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (liverFunctionTest.getDirectBilirubin().isBlank() || liverFunctionTest.getDirectBilirubin().isEmpty()) {
				errors.put("directBilirubin", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (liverFunctionTest.getIndirectBilirubin().isBlank() || liverFunctionTest.getIndirectBilirubin().isEmpty()
					) {
				errors.put("indirectBilirubin", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (liverFunctionTest.getsGOT().isBlank() || liverFunctionTest.getsGOT().isEmpty()) {
				errors.put("sGOT", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (liverFunctionTest.getsGPT().isBlank() || liverFunctionTest.getsGPT().isEmpty()) {
				errors.put("sGPT", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (liverFunctionTest.getaLP().isBlank() || liverFunctionTest.getaLP().isEmpty()) {
				errors.put("aLP", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));
 
				
				
				path = path + File.separator + liverFunctionTest.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						liverFunctionTest.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "LiverFunctionTest";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "LiverFunctionTest");

				

				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					
					String savePath = testNameFolderCreated + File.separator + liverFunctionTest.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Liver Function Test.........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					liverFunctionTest.setLastModifiedOn(Calendar.getInstance().getTime());
					liverFunctionTest.setLastModifiedBy(loggedInUserForcepersonnelId);
					liverFunctionTest.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
                    liverFunctionTest.setFileName(fileUploadDto.getFileName());
					liverFunctionTest.setFileType(fileUploadDto.getFileType());
					liverFunctionTest.setPath(savePath);
					liverFunctionTest.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					investigationMasterService.saveLiverFunctionTest(liverFunctionTest);
					checkUpListRepo.updateFileUploadStatus(liverFunctionTest.getAmeId(),
							liverFunctionTest.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!liver Function Test.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

//    =================================================RK(save CBC)===============================================================

	@PostMapping("save-cbc")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveCbc(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

		
		//String specialCharacterRegex = ".*[^a-zA-Z\\s].*";
		//String digitOnlyRegex = "\\d+";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		CompleteBloodCount completeBloodCount = new CompleteBloodCount();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					completeBloodCount.setAmeId(value);
				}

				if (key.equalsIgnoreCase("haemoglobin")) {
					completeBloodCount.setHaemoglobin(value);
				}
				if (key.equalsIgnoreCase("totalLeukocyteCount")) {
					completeBloodCount.setTotalLeukocyteCount(value);
				}

				if (key.equalsIgnoreCase("differentialLeukocyteCount")) {
					completeBloodCount.setDifferentialLeukocyteCount(value);
				}
				if (key.equalsIgnoreCase("neutrophilsOrPolymorphs")) {
					completeBloodCount.setNeutrophilsOrPolymorphs(value);
				}
				if (key.equalsIgnoreCase("monocytes")) {
					completeBloodCount.setMonocytes(value);
				}
				if (key.equalsIgnoreCase("lymphocytesBTCells")) {
					completeBloodCount.setLymphocytes(value);
				}

				if (key.equalsIgnoreCase("basophils")) {
					completeBloodCount.setBasophils(value);
				}
				if (key.equalsIgnoreCase("eosinophils")) {
					completeBloodCount.setEosinophils(value);
				}
				if (key.equalsIgnoreCase("erythrocyteSedimentationRate")) {
					completeBloodCount.setErythrocyteSedimentationRate(value);
				}
				if (key.equalsIgnoreCase("plateletCount")) {
					completeBloodCount.setPlateletCount(value);
				}

				if (key.equalsIgnoreCase("testCode")) {
					completeBloodCount.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {

			if (completeBloodCount.getHaemoglobin().isBlank() || completeBloodCount.getHaemoglobin().isEmpty()) {
				errors.put("haemoglobin", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (completeBloodCount.getTotalLeukocyteCount().isBlank()
					|| completeBloodCount.getTotalLeukocyteCount().isEmpty()) {
				errors.put("totalLeukocyteCount", RegexValidation.EMPTY_FIELD_MESSAGE);
			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + completeBloodCount.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						completeBloodCount.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "CompleteBloodSugar";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "CompleteBloodSugar");

			

				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					String savePath = testNameFolderCreated + File.separator + completeBloodCount.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Blood Sugar.........!");
					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					completeBloodCount.setLastModifiedOn(Calendar.getInstance().getTime());
					completeBloodCount.setLastModifiedBy(loggedInUserForcepersonnelId);
					completeBloodCount.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					completeBloodCount.setFileName(fileUploadDto.getFileName());
					completeBloodCount.setFileType(fileUploadDto.getFileType());
					completeBloodCount.setPath(savePath);
					completeBloodCount.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					investigationMasterService.saveCompleteBloodCountCBC(completeBloodCount);
					checkUpListRepo.updateFileUploadStatus(completeBloodCount.getAmeId(),
							completeBloodCount.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Blood Sugar.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

//=======================================================RK(save Lipid)============================================================
	@PostMapping("save-lipid")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveLipid(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

		String specialCharacterRegex = ".*[^a-zA-Z\\s].*";
		String digitOnlyRegex = "\\d+";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		Lipid lipid = new Lipid();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					lipid.setAmeId(value);
				}

				if (key.equalsIgnoreCase("serumCcholesterol")) {
					lipid.setSerumCcholesterol(value);
				}
				if (key.equalsIgnoreCase("lDLCholesterol")) {
					lipid.setlDLCholesterol(value);
				}

				if (key.equalsIgnoreCase("vLDLcholesterol")) {
					lipid.setvLDLcholesterol(value);
				}
				if (key.equalsIgnoreCase("hDLCholesterol")) {
					lipid.sethDLCholesterol(value);
				}
				if (key.equalsIgnoreCase("triglycerides")) {
					lipid.setTriglycerides(value);
				}

				if (key.equalsIgnoreCase("lDLhDLRatio")) {
					lipid.setlDLhDLRatio(value);
				}
				if (key.equalsIgnoreCase("testCode")) {
					lipid.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {

//  			if (lipid.getHaemoglobin().isBlank() || lipid.getHaemoglobin().isEmpty()
//  					|| lipid.getHaemoglobin().matches(digitOnlyRegex)
//  					|| lipid.getHaemoglobin().matches(specialCharacterRegex)) {
//  				errors.put("haemoglobin", "* Invalid Input");
//  			}
//  			
//  			if (lipid.getTotalLeukocyteCount().isBlank() || lipid.getTotalLeukocyteCount().isEmpty()
//  					|| lipid.getTotalLeukocyteCount().matches(digitOnlyRegex)
//  					|| lipid.getTotalLeukocyteCount().matches(specialCharacterRegex)) {
//  				errors.put("totalLeukocyteCount", "* Invalid Input");
//  			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + lipid.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						lipid.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Lipid Profile";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Lipid Profile");


				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {

					String savePath = testNameFolderCreated + File.separator + lipid.getAmeId() + "-"
							
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Lipid.........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
						
					} catch (IOException e) {

						e.printStackTrace();
					}
					
					
					lipid.setLastModifiedOn(Calendar.getInstance().getTime());
					lipid.setLastModifiedBy(loggedInUserForcepersonnelId);
					lipid.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					lipid.setFileName(fileUploadDto.getFileName());
					lipid.setFileType(fileUploadDto.getFileType());
					lipid.setPath(savePath);
					lipid.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					investigationMasterService.saveLipid(lipid);
					checkUpListRepo.updateFileUploadStatus(lipid.getAmeId(), lipid.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Lipid.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);

				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

//    ===============================================RK(Save KFT)====================================================
	@PostMapping("save-kft")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveKft(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {
		
		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

		String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		String digitOnlyRegex = "\\d+";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		KidneyFunctionTest kidneyFunctionTest = new KidneyFunctionTest();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					kidneyFunctionTest.setAmeId(value);
				}

				if (key.equalsIgnoreCase("bloodUrea")) {
					kidneyFunctionTest.setBloodUrea(value);
				}

				if (key.equalsIgnoreCase("scrumCreatinine")) {
					kidneyFunctionTest.setScrumCreatinine(value);
				}
				if (key.equalsIgnoreCase("uricAcid")) {
					kidneyFunctionTest.setUricAcid(value);
				}
				if (key.equalsIgnoreCase("testCode")) {
					kidneyFunctionTest.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {

//  			if (lipid.getHaemoglobin().isBlank() || lipid.getHaemoglobin().isEmpty()
//  					|| lipid.getHaemoglobin().matches(digitOnlyRegex)
//  					|| lipid.getHaemoglobin().matches(specialCharacterRegex)) {
//  				errors.put("haemoglobin", "* Invalid Input");
//  			}
//  			
//  			if (lipid.getTotalLeukocyteCount().isBlank() || lipid.getTotalLeukocyteCount().isEmpty()
//  					|| lipid.getTotalLeukocyteCount().matches(digitOnlyRegex)
//  					|| lipid.getTotalLeukocyteCount().matches(specialCharacterRegex)) {
//  				errors.put("totalLeukocyteCount", "* Invalid Input");
//  			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + kidneyFunctionTest.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						kidneyFunctionTest.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Kidney Function Test";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Kidney Function Test");

				
				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					String savePath = testNameFolderCreated + File.separator + kidneyFunctionTest.getAmeId() + "-"
							+ fileUploadDto.getFileName();

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					kidneyFunctionTest.setLastModifiedOn(Calendar.getInstance().getTime());
					kidneyFunctionTest.setLastModifiedBy(loggedInUserForcepersonnelId);
					kidneyFunctionTest.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					kidneyFunctionTest.setFileName(fileUploadDto.getFileName());
					kidneyFunctionTest.setFileType(fileUploadDto.getFileType());
					kidneyFunctionTest.setPath(savePath);
					kidneyFunctionTest.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					investigationMasterService.saveKidneyFunctionTestKFT(kidneyFunctionTest);
					checkUpListRepo.updateFileUploadStatus(kidneyFunctionTest.getAmeId(),
							kidneyFunctionTest.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	// =======================================================RK(save
	@PostMapping("save-utmicrophy")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveUrine(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
        
		//String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		//String digitOnlyRegex = "\\d+";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		UrineTestPhysicalMicroscopic urineTestPhysicalMicroscopic = new UrineTestPhysicalMicroscopic();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					urineTestPhysicalMicroscopic.setAmeId(value);
				}

				if (key.equalsIgnoreCase("epithelialCells")) {
					urineTestPhysicalMicroscopic.setEpithelialCells(value);
				}
				

				if (key.equalsIgnoreCase("rbc")) {
					urineTestPhysicalMicroscopic.setRbc(value);
				}
				if (key.equalsIgnoreCase("crystal")) {
					urineTestPhysicalMicroscopic.setCrystal(value);
				}
				if (key.equalsIgnoreCase("colour")) {
					urineTestPhysicalMicroscopic.setColour(value);
				}

				if (key.equalsIgnoreCase("transparency")) {
					urineTestPhysicalMicroscopic.setTransparency(value);
				}

				if (key.equalsIgnoreCase("reaction")) {
					urineTestPhysicalMicroscopic.setReaction(value);
				}

				if (key.equalsIgnoreCase("albuminCells")) {
					urineTestPhysicalMicroscopic.setAlbuminCells(value);
				}
				
				if (key.equalsIgnoreCase("sugar")) {
					urineTestPhysicalMicroscopic.setSugar(value);
				}

				if (key.equalsIgnoreCase("bileSalt")) {
					urineTestPhysicalMicroscopic.setBileSalt(value);
				}

				if (key.equalsIgnoreCase("ketones")) {
					urineTestPhysicalMicroscopic.setKetones(value);
				}

				if (key.equalsIgnoreCase("specificGravity")) {
					urineTestPhysicalMicroscopic.setSpecificGravity(value);
				}

				if (key.equalsIgnoreCase("testCode")) {
					urineTestPhysicalMicroscopic.setTestCode(value);
				}
				
				
				// =========NEW FIELD TESTS=========//
		
				if (key.equalsIgnoreCase("castCells")) {
					urineTestPhysicalMicroscopic.setCastCells(value);
					break;
				}
				

				if (key.equalsIgnoreCase("upt")) {
					urineTestPhysicalMicroscopic.setUpt(value);
					break;
				}


				if (key.equalsIgnoreCase("pusCells")) {
					urineTestPhysicalMicroscopic.setPusCells(value);
					break;
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {

//  			if (lipid.getHaemoglobin().isBlank() || lipid.getHaemoglobin().isEmpty()
//  					|| lipid.getHaemoglobin().matches(digitOnlyRegex)
//  					|| lipid.getHaemoglobin().matches(specialCharacterRegex)) {
//  				errors.put("haemoglobin", "* Invalid Input");
//  			}
//  			
//  			if (lipid.getTotalLeukocyteCount().isBlank() || lipid.getTotalLeukocyteCount().isEmpty()
//  					|| lipid.getTotalLeukocyteCount().matches(digitOnlyRegex)
//  					|| lipid.getTotalLeukocyteCount().matches(specialCharacterRegex)) {
//  				errors.put("totalLeukocyteCount", "* Invalid Input");
//  			}

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + urineTestPhysicalMicroscopic.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						urineTestPhysicalMicroscopic.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Urine Test Physical Microscopic";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Urine Test Physical Microscopic");

				 
				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {
					String savePath = testNameFolderCreated + File.separator + urineTestPhysicalMicroscopic.getAmeId() + "-"
							+ fileUploadDto.getFileName();

					logger.info("File Exist Urine.........!"+savePath);
					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					
					urineTestPhysicalMicroscopic.setLastModifiedOn(Calendar.getInstance().getTime());
					urineTestPhysicalMicroscopic.setLastModifiedBy(loggedInUserForcepersonnelId);
					urineTestPhysicalMicroscopic.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
	                urineTestPhysicalMicroscopic.setFileName(fileUploadDto.getFileName());
					urineTestPhysicalMicroscopic.setFileType(fileUploadDto.getFileType());
					urineTestPhysicalMicroscopic.setPath(savePath);
					urineTestPhysicalMicroscopic.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					investigationMasterService.saveUrineTestPhysicalMicroscopic(urineTestPhysicalMicroscopic);
					checkUpListRepo.updateFileUploadStatus(urineTestPhysicalMicroscopic.getAmeId(),
							urineTestPhysicalMicroscopic.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {

					logger.info("File Not Exist Urine.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);

				}

			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	
//	================================================Other Test====================================================================

	@PostMapping("validate-and-save-other-test")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveOtherTestPart1(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

		System.out.println("Received data: " + jsonData);
		System.out.println("Received file: " + file.getOriginalFilename());

		String specialCharacterRegex = ".*[^a-zA-Z\\s].*";
		String digitOnlyRegex = "\\d+";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		OtherTestReportParameters otherTestReportParameters=new OtherTestReportParameters();

		try {

			OtherTest otherTest = new OtherTest();
			CheckUpList checkUpList= new CheckUpList();
         // System.out.println(OtherTest.getTestCode());
			List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
			for (Map<String, String> map : list) {

				for (Map.Entry<String, String> entry : map.entrySet()) {

					String key = entry.getKey();
					String value = entry.getValue();
					if (key.equalsIgnoreCase("ameId")) {
						otherTest.setAmeId(value);
						
						break;
					}

					if (key.equalsIgnoreCase("testName")) {
						otherTest.setTestName(value);
						checkUpList.setTestName(value);
						otherTestReportParameters.setTestCode(value);
						break;
					}
					if (key.equalsIgnoreCase("testCode")) {
						otherTest.setTestCode(value);
						checkUpList.setTestCode(value);
						otherTestReportParameters.setTestCode(value);
						break;
					}
					if(key.equalsIgnoreCase("otherTestValue")) {
						otherTestReportParameters.setValue(value);
						break;
					}
                    if(key.equalsIgnoreCase("otherTestName")) {
                    	otherTestReportParameters.setName(value);
                    	break;
					}
				}
			}
	//		String otherTestCheck="others"+otherTest.getTestName();
	//		System.out.println(otherTestCheck+" -> "+otherTestCheck.equalsIgnoreCase(checkUpList.getTestName()));
			
			List<CheckUpList> checkUpLists = checkUpListRepo.findByAmeId(otherTest.getAmeId());

			if (checkUpLists != null && !checkUpLists.isEmpty()) {
				boolean found = false;
				for (CheckUpList checkUpListItr : checkUpLists) {
					if (checkUpListItr.getTestCode().equalsIgnoreCase(otherTest.getTestCode())) {
						found = true;
						break;
                    }
				}

				if (found) {
					errors.put("otherTest","Test already added to the list!");
					
				}

			}

			
			
			

					if (!errors.isEmpty()) {
						response.put("isValid", false);
						response.put("errors", errors);
						return ResponseEntity.ok(response);

					} else {
						
						checkUpList.setAmeId(otherTest.getAmeId());
			     		checkUpList.setTestName(otherTest.getTestName());
						checkUpList.setTestCode(otherTest.getTestName().toLowerCase());
						checkUpList.setOtherFlag(1);
						checkUpList.setUploadFlag(1);

						FileUploadDto fileUploadDto = new FileUploadDto();
						fileUploadDto.setFileContent(file.getBytes());
						fileUploadDto.setFileName(file.getOriginalFilename());
						fileUploadDto.setFileSize(String.valueOf(file.getSize()));
						fileUploadDto.setFileType(file.getContentType());

						int year = UserDateUtil.getYear(new Date());

						String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator
								+ year;

						String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
								String.valueOf(year));

						path = path + File.separator + otherTest.getAmeId();

						String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
								otherTest.getAmeId());

						String testNameFolder = pathForAmeId + File.separator + "Other Test";

						String testNameFolderCreated = CreateFileDirectory
								.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Other Test");
						
						String testNameFolderInsideOtherTest = testNameFolder+ File.separator + otherTest.getTestName();
						
						String testNameFolderCreatedInsideOtherTest = CreateFileDirectory
								.createNewFolderOrDirUploadUserRoleOrder(testNameFolderInsideOtherTest,otherTest.getTestName());

						if (Files.isDirectory(Paths.get(testNameFolderCreatedInsideOtherTest))) {
							String savePath = testNameFolderCreatedInsideOtherTest + File.separator + otherTest.getAmeId() + "-"
									+ fileUploadDto.getFileName();
							logger.info("Path Exist......!"+otherTest.getTestName()+".........!");

							try {
								FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
							} catch (IOException e) {

								e.printStackTrace();
							}

							otherTest.setLastModifiedOn(Calendar.getInstance().getTime());
							otherTest.setLastModifiedBy(loggedInUserForcepersonnelId);
							otherTest.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

							otherTest.setFileName(fileUploadDto.getFileName());
							otherTest.setFileType(fileUploadDto.getFileType());
							otherTest.setPath(savePath);
							otherTest.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));
							List<OtherTestReportParameters> otherTestReportParametersList=new ArrayList<>();
							otherTestReportParametersList.add(otherTestReportParameters);
							otherTest.setOtherTestReportParameters(otherTestReportParametersList);
							
							investigationMasterService.saveOtherTest(otherTest);
							checkUpListRepo.save(checkUpList);
							
							

							response.put("isValid", true);
							return ResponseEntity.ok(response);
						} else {
							logger.info("Path not Exist......!Other Test.........!");
							response.put("isValid", false);
							errors.put("fileError",
									"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
							response.put("errors", errors);
							return ResponseEntity.ok(response);

						}
					}
					
			
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	//==============================saveviralMakers============================


	@PostMapping("save-vrl")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveViralMaker(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");

		String specialCharacterRegex = ".*[^a-zA-Z\\s].*";
		String digitOnlyRegex = "\\d+";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		ViralMakers viralMakers = new ViralMakers();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					viralMakers.setAmeId(value);
				}

				if (key.equalsIgnoreCase("hiv1")) {
					viralMakers.setHiv1(value);
				}
				if (key.equalsIgnoreCase("hiv2")) {
					viralMakers.setHiv2(value);
				}

				if (key.equalsIgnoreCase("hbsAg")) {
					viralMakers.setHbsAg(value);
				}
				if (key.equalsIgnoreCase("vdrl")) {
					viralMakers.setVdrl(value);
				}
				if (key.equalsIgnoreCase("hcv")) {
					viralMakers.setHcv(value);
				}

			
				if (key.equalsIgnoreCase("testCode")) {
					viralMakers.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + viralMakers.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
				viralMakers.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Viral Makers Profile";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Viral Makers Profile");


				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {

					String savePath = testNameFolderCreated + File.separator + viralMakers.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Viral Makers .........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					
					viralMakers.setLastModifiedOn(Calendar.getInstance().getTime());
					viralMakers.setLastModifiedBy(loggedInUserForcepersonnelId);
					viralMakers.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					viralMakers .setFileName(fileUploadDto.getFileName());
					viralMakers.setFileType(fileUploadDto.getFileType());
					viralMakers.setPath(savePath);
					viralMakers.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					investigationMasterService.saveViral(viralMakers);
					checkUpListRepo.updateFileUploadStatus(viralMakers.getAmeId(), viralMakers.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Viral Makers.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);

				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}


//	<!--===============================Thyroid profile =======================-->
    @PostMapping("save-tp")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveTyphoidProfile(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

    	String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
        String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		String digitOnlyRegex = "\\d+";
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		ThyroidProfile thyroidProfile = new ThyroidProfile();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("ameId")) {
					thyroidProfile.setAmeId(value);
				}

				if (key.equalsIgnoreCase("t3")) {
					thyroidProfile.setT3(value);
				}
				if (key.equalsIgnoreCase("t4")) {
					thyroidProfile.setT4(value);
				}

				if (key.equalsIgnoreCase("tsh")) {
					thyroidProfile.setTsh(value);
				}
			
				if (key.equalsIgnoreCase("testCode")) {
					thyroidProfile.setTestCode(value);
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);
				

			}

		}
		try {

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + thyroidProfile.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
				thyroidProfile.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Thypoid Profile";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder, "Thypoid Profile");


				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {

					String savePath = testNameFolderCreated + File.separator + thyroidProfile.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......Thypoid Profile .........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					thyroidProfile.setLastModifiedOn(Calendar.getInstance().getTime());
					thyroidProfile.setLastModifiedBy(loggedInUserForcepersonnelId);
					thyroidProfile.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
                    thyroidProfile.setFileName(fileUploadDto.getFileName());
					thyroidProfile.setFileType(fileUploadDto.getFileType());
					thyroidProfile.setPath(savePath);
					thyroidProfile.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					investigationMasterService.saveThyroidProfile(thyroidProfile);
					checkUpListRepo.updateFileUploadStatus(thyroidProfile.getAmeId(), thyroidProfile.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Thypoid Profile .........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);

				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}



//=========================================Save Other Test======================================
	@PostMapping("save-other")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveOtherTest(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file,HttpSession httpSession,HttpServletRequest request) {

		String loggedInUserForcepersonnelId= (String) httpSession.getAttribute("forcepersonalId");
        String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		String digitOnlyRegex = "\\d+";

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		String testName=null;
		Others others = new Others();
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();

				if (key.equalsIgnoreCase("ameId")) {
					others.setAmeId(value);
				}  
				if (key.equalsIgnoreCase("testName")) {
					others.setTestName(value);
				}

			
				if (key.equalsIgnoreCase("testCode")) {
					others.setTestCode(value);
					testName=value;
				}

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {

			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_INVESTIGATION_FILE_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,
						String.valueOf(year));

				path = path + File.separator + others.getAmeId();

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
				others.getAmeId());

				String testNameFolder = pathForAmeId + File.separator + "Other Test Profile";

				String testNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(testNameFolder,"Other Test Profile");


				if (Files.isDirectory(Paths.get(testNameFolderCreated))) {

					String savePath = testNameFolderCreated + File.separator + others.getAmeId() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!Other Test .........!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					others.setLastModifiedOn(Calendar.getInstance().getTime());
					others.setLastModifiedBy(loggedInUserForcepersonnelId);
					others.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));

					others.setFileName(fileUploadDto.getFileName());
					others.setFileType(fileUploadDto.getFileType());
					others.setPath(savePath);
					others.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));
					investigationMasterService.savOthers(others);
					checkUpListRepo.updateFileUploadStatus(others.getAmeId(), others.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!Other Test.........!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);

				}

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@PostMapping("report-of-investigation-form")
	public String reportofIvestigationform(Model model, HttpSession session, @RequestParam("ameId") String ameId,
			@RequestParam("forcepersonalId") String candidateforcepersonalId) {
		String loginForcePersonalId = (String) session.getAttribute("forcepersonalId");
		ForcePersonnelDto forcePersonal = loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId);
		model.addAttribute("candidateDetails", forcePersonal);
		int rCode = (int) session.getAttribute("rCodeMedical");
		logger.info("RCode>>>>>>>>>>>>>>>>>>" + rCode + ">>Investigation>>>>>>>>>>>");
		String uri = mapUriToUserService.getUriForInvestigationUser(rCode);
		logger.info("RCode>>>>>>>>>>>>>>>>>>" + uri + ">>>>Inveastigation");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		List<CheckUpList> lists = investigationMasterService.checkUpListsByAmeId(ameId, CommonConstant.ACTIVE_FLAG_YES);
		model.addAttribute("checkUpLists", lists);
		model.addAttribute("ameId", ameId);
		return "medical-sub-ordinate/view/report-of-investigation-form";
	}

	@GetMapping("/blood-sugar")
	public ResponseEntity<BloodSugar> showInvestigationReportBloodSugar(@RequestParam("ameId") String ameId) {
		Optional<BloodSugar> bloodSugarOptional = bloodSugarRepository.findByAmeId(ameId);
		if (bloodSugarOptional.isPresent()) {
			BloodSugar bloodSugar = bloodSugarOptional.get();
			return new ResponseEntity<>(bloodSugar, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PostMapping("/download-investigation-report-file")
	public ResponseEntity<FileSystemResource> downloadNotitificationFile(@RequestParam("filePath") String path) {
		File file = new File(path);

		if (file == null || !file.exists()) {
			return ResponseEntity.notFound().build();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(new FileSystemResource(file));
	}

	@GetMapping("/view-investigation-report-kft")
	public ResponseEntity<KidneyFunctionTest> showInvestigationReportKft(@RequestParam("ameId") String ameId) {
		Optional<KidneyFunctionTest> kftOptional = kidneyFunctionTestKFTRepository.findByAmeId(ameId);
		if (kftOptional.isPresent()) {
			KidneyFunctionTest kidneyFunctionTestKFT = kftOptional.get();
			return new ResponseEntity<>(kidneyFunctionTestKFT, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/view-investigation-report-lft")
	public ResponseEntity<LiverFunctionTest> showInvestigationReportlft(@RequestParam("ameId") String ameId) {
		Optional<LiverFunctionTest> lftOptional = liverFunctionTestRepository.findByAmeId(ameId);
		if (lftOptional.isPresent()) {
			LiverFunctionTest liverFunctionTest = lftOptional.get();
			return new ResponseEntity<>(liverFunctionTest, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/view-investigation-report-lipid")
	public ResponseEntity<Lipid> showInvestigationReportlipid(@RequestParam("ameId") String ameId) {
		Optional<Lipid> lipidOptional = lipidRepository.findByAmeId(ameId);
		if (lipidOptional.isPresent()) {
			Lipid lipid = lipidOptional.get();
			return new ResponseEntity<>(lipid, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	@GetMapping("/view-investigation-report-cbc")
	public ResponseEntity<CompleteBloodCount> showInvestigationReportcbc(@RequestParam("ameId") String ameId) {
		Optional<CompleteBloodCount> cbcOptional = completeBloodCountCBCRepository.findByAmeId(ameId);
		if (cbcOptional.isPresent()) {
			CompleteBloodCount completeBloodCountCBC = cbcOptional.get();
			return new ResponseEntity<>(completeBloodCountCBC, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/view-investigation-report-viral-markers")
	public ResponseEntity<ViralMakers> showInvestigationReportviralmarkers(@RequestParam("ameId") String ameId) {
		Optional<ViralMakers> markersOptional = viralMakersRepository.findByAmeId(ameId);
		if (markersOptional.isPresent()) {
			ViralMakers viralMakers = markersOptional.get();
			return new ResponseEntity<>(viralMakers, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}



	@GetMapping("/view-investigation-report-ecg")
	public ResponseEntity<Others> showInvestigationReportother(@RequestParam("ameId") String ameId) {
		Optional<Others> othersOptional = othersTestRepository.findAllByAmeIdAndTestCode(ameId,"ecg");
		if (othersOptional.isPresent()) {
			Others others=othersOptional.get();
			return new ResponseEntity<>(others, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/view-investigation-report-usg")
	public ResponseEntity<Others> showInvestigationReportusg(@RequestParam("ameId") String ameId) {
		Optional<Others> othersOptional = othersTestRepository.findAllByAmeIdAndTestCode(ameId,"usg");
		if (othersOptional.isPresent()) {
			Others others=othersOptional.get();
			return new ResponseEntity<>(others, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/view-investigation-report-xray")
	public ResponseEntity<Others> showInvestigationReportXray(@RequestParam("ameId") String ameId) {
		Optional<Others> othersOptional = othersTestRepository.findAllByAmeIdAndTestCode(ameId,"xray");
		if (othersOptional.isPresent()) {
			Others others=othersOptional.get();
			return new ResponseEntity<>(others, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/view-investigation-report-urine-test")
	public ResponseEntity<UrineTestPhysicalMicroscopic> showInvestigationReporturine(@RequestParam("ameId") String ameId) {
		Optional<UrineTestPhysicalMicroscopic> urineTestOptional = urineTestPhysicalMicroscopicRespository.findByAmeId(ameId);
		if (urineTestOptional.isPresent()) {
			UrineTestPhysicalMicroscopic urineTestPhysicalMicroscopic = urineTestOptional.get();
			return new ResponseEntity<>(urineTestPhysicalMicroscopic, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@GetMapping("/view-investigation-report-thyroid-profile")
	public ResponseEntity<ThyroidProfile> showInvestigationReportthyroidprofile(@RequestParam("ameId") String ameId) {
		Optional<ThyroidProfile> thyroidProfileOptional = thyroidProfileRepository.findByAmeId(ameId);
		if (thyroidProfileOptional.isPresent()) {
			ThyroidProfile thyroidProfile = thyroidProfileOptional.get();
			return new ResponseEntity<>(thyroidProfile, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

    @CrossOrigin(originPatterns = { StringConstants.CROSS_ORIGIN_BASE_URL}, allowCredentials = "true")
	@PostMapping("view-report-of-investigation-form") 
	public String viewreportofIvestigationform(Model model, 
			HttpSession session, @RequestParam("ameId") String ameId,
			@RequestParam("forcepersonalId") String forcePersonalId) {
		
		ForcePersonnelDto ameCandidateForcePersonnelDto = loginUserDetails.getCandicateForcePersonalId(forcePersonalId);
		Optional<AmeDetailsDto> ameDetailsDtoOptional = ameDetailsService.getCandidateAmeDetailsByAmeId(ameId);

		if (ameDetailsDtoOptional.isPresent()) {
			model.addAttribute("forcePersonalDetails", ameDetailsDtoOptional.get());
		}

		else {
			model.addAttribute("forcePersonalDetails", ameCandidateForcePersonnelDto);
		}
		
		String loginForcePersonalId = (String) session.getAttribute("forcepersonalId");
		InvestigationDto investigationDto = new InvestigationDto();
		
		investigationDto=this.investigationMasterService.getReportView(ameId);
		ForcePersonnelDto loginUserDetailsData=loginUserDetails.getLoginUserDetails(forcePersonalId);
		
		AmeMasterStatus ameMasterStatus=ameMasterStatusService.getAmeMasterStatus(ameId);
		
		//ForcePersonnel forcePersonalDetails=ameAssessmentServicePart_1_impl.getforceForcePersonal(forcePersonalId);
		
		//Optional<ForcePersonnelDto> forcePersonalDetails=forcePersonnelService.getByForcePersonnelId(forcePersonalId);
		
		
		model.addAttribute("investigationDto", investigationDto);
		int rCode = (int) session.getAttribute("rCodeMedical");
		
		logger.info("RCode>>>>>>>>>>>>>>>>>>" + rCode + ">>Investigation>>>>>>>>>>>");
		String uri = mapUriToUserService.getUriForInvestigationUser(rCode);
		
		logger.info("RCode>>>>>>>>>>>>>>>>>>" + uri + ">>>>Inveastigation");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		
	//	model.addAttribute("forcePersonalDetails", forcePersonalDetails.get());
		model.addAttribute("forcepersonalId",forcePersonalId);
		
		model.addAttribute("ameMasterStatus", ameMasterStatus);
		model.addAttribute("loginUserDetails", loginUserDetailsData);
		
		return "medical-sub-ordinate/view/view-report-of-investigation-form";
		}

//------------------------------------------upload ame final report-------------------------------------------------------------
	@PostMapping("upload-ame-final-report")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> uploadAmeFinalReport(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file ,HttpServletRequest request,HttpSession session) {
		
		String ameId = null;

	 // String specialCharacterRegex = ".*[!@#$%^&*(),.?:{}|<>].*";
		String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		String digitOnlyRegex = "\\d+";
		String loggedInUserForcepersonnelId = (String) session.getAttribute("forcepersonalId");
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		String testName=null;
	   int rCode = (int) session.getAttribute("rCodeMedical");
		
	 
	  
	  
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();

				if (key.equalsIgnoreCase("ameId")) {
					ameId=value;
								}  
				

				logger.info("subTestCode:>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}
		try {
			
			 
			  
			  
			  AmeApplicationFlowStatus ameApplicationFlowStatus=ameApplicationFlowStatusRepo.findByAmeIdAndRowIsValid(ameId,true).get();
			  
			  
			  if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				
				 Optional<AmeDeclarationIndividualModel> ameDeclarationIndividualModelOptional = 
						  ameDeclarationRepository.findByAmeId(ameId);
				if(rCode==2||rCode==3||rCode==4) {
					
					 AmeFinalReportFileDirGo ameFinalReportFileDirGo	 =new AmeFinalReportFileDirGo();
					AmeFinalReportDetailsGo ameFinalReportDetailsGo = ameFinalReportDetailsGoRepository.findByAmeIdAndStatus(ameId,1).get();
					
					FileUploadDto fileUploadDto = new FileUploadDto();
					fileUploadDto.setFileContent(file.getBytes());
					fileUploadDto.setFileName(file.getOriginalFilename());
					fileUploadDto.setFileSize(String.valueOf(file.getSize()));
					fileUploadDto.setFileType(file.getContentType());

					int year = UserDateUtil.getYear(new Date());

					String directoryName = CommonConstant.AME_FINAL_REPORT_PATH + File.separator + year;

					String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,String.valueOf(year));

					path = path + File.separator + ameId;

					String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,ameId);

				//	String testNameFolder = pathForAmeId + File.separator + "";

				//	String testNameFolderCreated = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(testNameFolder,"");


					if (Files.isDirectory(Paths.get(pathForAmeId))) {

						String savePath = pathForAmeId + File.separator + ameId + "-"
								+ fileUploadDto.getFileName();
						logger.info("Path Exist..............!");

						try {
							FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
						} catch (IOException e) {

							e.printStackTrace();
						}
						ameFinalReportFileDirGo.setUniqueAmeId(ameFinalReportDetailsGo.getUniqueAmeId());
						ameFinalReportFileDirGo.setAmeId(ameFinalReportDetailsGo.getAmeId());
						ameFinalReportFileDirGo.setFileName(fileUploadDto.getFileName());
						ameFinalReportFileDirGo.setFileType(fileUploadDto.getFileType());
						ameFinalReportFileDirGo.setFilePath(savePath);
						ameFinalReportFileDirGo.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));
						ameFinalReportFileDirGo.setCreatedOn(Calendar.getInstance().getTime());
						ameFinalReportFileDirGo.setCreatedBy(loggedInUserForcepersonnelId);
						ameFinalReportFileDirGoRepository.save(ameFinalReportFileDirGo);

						
						
	                       					
						if(ameDeclarationIndividualModelOptional.isPresent()){
							AmeDeclarationIndividualModel ameDeclarationIndividualModel=new AmeDeclarationIndividualModel();
							ameDeclarationIndividualModel=ameDeclarationIndividualModelOptional.get();
							ameDeclarationIndividualModel.setFinalUploadFlag(1);
							
							if(rCode==3||rCode==4) {
								ameApplicationFlowStatus.setPhysicalReportUploadBy(5);
								ameApplicationFlowStatus.setAmeFinalUplaod(0);
								
								}
							else if(rCode==2) {
								ameApplicationFlowStatus.setPhysicalReportUploadBy(10);
								ameApplicationFlowStatus.setAmeFinalUplaod(1);
								
								}
							ameApplicationFlowStatus.setAmeYear(ameDeclarationIndividualModelOptional.get().getDeclaration_year());
							ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
							SMSTemplateDto smsTemplateDto=new SMSTemplateDto();
						   Optional<AmeReviewCandidatesList> ameReviewCandidatasList= ameReviewCandidatesListRepository.findByAmeIdAndStatusCode(ameId,CommonConstant.REVIEW_PENDING);
				           if(ameReviewCandidatasList.isPresent()) {
				        	
				        	AmeReviewCandidatesList ameReviewCandidatesData=new AmeReviewCandidatesList();
				        	ameReviewCandidatesData=ameReviewCandidatasList.get();
				        	ForcePersonnelDto candidateForcePersonnel = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(ameReviewCandidatesData.getCandidateForcePersonalId()).get();
				            Optional<ForcePersonnelDto> candidateForcePersonnelOPOptional	=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(ameReviewCandidatesData.getCandidateForcePersonalId());
				        	smsTemplateDto.setIrlaNo(candidateForcePersonnel.getForceId());
				        	smsTemplateDto.setMobileNumber(String.valueOf(candidateForcePersonnel.getMobileNumber()));
				        	smsTemplateDto.setCountryIsdCode("91");
				        	smsTemplateDto.setCategoryAwarded(ameFinalReportDetailsGo.getFinalCategoryAwarded());
				        	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-YYYY");
				       	    smsTemplateDto.setAmeCompletedOnDate(simpleDateFormat.format(ameReviewCandidatesData.getReviewCreatedOn()));
				       	    smsTemplateDto.setAmeReviewDate(simpleDateFormat.format(ameReviewCandidatesData.getReviewEndDate()));
				        	
				        	
				       	 SmsResponse smsresponse=smsTemplateService.reviewMedicalBoardSMS(smsTemplateDto,SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_REVIEW_MEDICAL_BOARD);
						
				
						
				    	Optional<ApplicationStateDescription> applicationStateDescription=applicationStateDescriptionRepository.findById(55);
				    	Optional<RefMedicalExamType> examType=medicalExamTypeRepo.findById(1L);
				     
				    	AlertAndNotification alertAndNotificationRecieved=createAlertAndNotification.saveAlertAndNotification(request, candidateForcePersonnelOPOptional, smsresponse.getMessage(), 
								applicationStateDescription, examType,"UploadAmeFinalReport By manual Process", "NA","NA");
				        alertAndNotificationRepository.save(alertAndNotificationRecieved);
						
						logger.info("AmeApplication Status Flow updated......"+ameApplicationFlowStatus);
				        }
				        
				         Optional<AmeFinalReportDetails> ameFinalReportOptional=ameFinalDetailService.findByAmeId(ameId);
				         if(ameFinalReportOptional.isPresent()) {
				        	 SMSTemplateDto dto=new SMSTemplateDto();
				        	 AmeFinalReportDetails ameFinalReportDetailsData=new AmeFinalReportDetails();
				        	 ameFinalReportDetailsData=ameFinalReportOptional.get();
				        	 SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-YYYY");
				        	 dto.setAmeCompletedOnDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
				        	 dto.setCategoryAwarded(ameFinalReportDetailsData.getFinalCategoryAwarded());
							 Optional<ForcePersonnelDto> forcePersonnelOptional= forcePersonalService.getForcePersonnelDetailsByForcePersonnelId(ameFinalReportDetailsData.getCandidateforcePersonalId());
							 dto.setIrlaNo(forcePersonnelOptional.get().getForceId());
							 dto.setMobileNumber(String.valueOf(forcePersonnelOptional.get().getMobileNumber()));
							 dto.setCountryIsdCode("91");
							  
							SmsResponse responseReview= smsTemplateService.ameSMS(dto,SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_AME_SMS);
							
							CreateAlertAndNotification createAlertAndNotificationReview=new CreateAlertAndNotification();
							Optional<ApplicationStateDescription> applicationStateDescriptionReview=applicationStateDescriptionRepository.findById(55);
					    	Optional<RefMedicalExamType> examTypeReview=medicalExamTypeRepo.findById(1L);
					        AlertAndNotification alertAndNotificationReview=createAlertAndNotificationReview.saveAlertAndNotification(request, forcePersonnelOptional, responseReview.getMessage(), 
					        		applicationStateDescriptionReview, examTypeReview,"UploadAmeFinalReport By manual Process", "NA","NA");
					        alertAndNotificationRepository.save(alertAndNotificationReview);
							 
					        logger.info("Final report status updated...........! And SMS message sent successfully.....!");
				         }
				         
						 
				        
					
						}
						
						

						response.put("isValid", true);
						return ResponseEntity.ok(response);
					} else {
						logger.info("Path not Exist...............!");
						response.put("isValid", false);
						response.put("fileError","Folder does not exist & data not saved.....Please contact developer team.....");
						//response.put("errors", errors);
						return ResponseEntity.ok(response);

					}
					
					
					
					
				}
				
				else {				
					
				AmeFinalReportFileDir ameFinalReportFileDir	 =new AmeFinalReportFileDir();
				AmeFinalReportDetails ameFinalReportDetails= ameFinalReportDetailsRepository.findByAmeIdAndStatus(ameId,1).get();
				
				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.AME_FINAL_REPORT_PATH + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,String.valueOf(year));

				path = path + File.separator + ameId;

				String pathForAmeId = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,ameId);

			//	String testNameFolder = pathForAmeId + File.separator + "";

			//	String testNameFolderCreated = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(testNameFolder,"");


				if (Files.isDirectory(Paths.get(pathForAmeId))) {

					String savePath = pathForAmeId + File.separator + ameId + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist..............!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}
				
					ameFinalReportFileDir.setAmeId(ameFinalReportDetails.getAmeId());
					ameFinalReportFileDir.setFileName(fileUploadDto.getFileName());
					ameFinalReportFileDir.setFileType(fileUploadDto.getFileType());
					ameFinalReportFileDir.setFilePath(savePath);
					ameFinalReportFileDir.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));
					ameFinalReportFileDir.setCreatedOn(Calendar.getInstance().getTime());
					ameFinalReportFileDir.setCreatedBy(loggedInUserForcepersonnelId);
					ameFinalReportFileDirRepository.save(ameFinalReportFileDir);

					
					
                       					
					if(ameDeclarationIndividualModelOptional.isPresent()){
						AmeDeclarationIndividualModel ameDeclarationIndividualModel=new AmeDeclarationIndividualModel();
						ameDeclarationIndividualModel=ameDeclarationIndividualModelOptional.get();
						ameDeclarationIndividualModel.setFinalUploadFlag(1);
						ameApplicationFlowStatus.setAmeFinalUplaod(1);
						ameApplicationFlowStatus.setAmeFinalStatus(1);
						ameApplicationFlowStatus.setAmeYear(ameDeclarationIndividualModelOptional.get().getDeclaration_year());
						ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
						SMSTemplateDto smsTemplateDto=new SMSTemplateDto();
					   Optional<AmeReviewCandidatesList> ameReviewCandidatasList= ameReviewCandidatesListRepository.findByAmeIdAndStatusCode(ameId,CommonConstant.REVIEW_PENDING);
			           if(ameReviewCandidatasList.isPresent()) {
			        	
			        	AmeReviewCandidatesList ameReviewCandidatesData=new AmeReviewCandidatesList();
			        	ameReviewCandidatesData=ameReviewCandidatasList.get();
			        	ForcePersonnelDto candidateForcePersonnel = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(ameReviewCandidatesData.getCandidateForcePersonalId()).get();
			            Optional<ForcePersonnelDto> candidateForcePersonnelOPOptional	=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(ameReviewCandidatesData.getCandidateForcePersonalId());
			        	smsTemplateDto.setIrlaNo(candidateForcePersonnel.getForceId());
			        	smsTemplateDto.setMobileNumber(String.valueOf(candidateForcePersonnel.getMobileNumber()));
			        	smsTemplateDto.setCountryIsdCode("91");
			        	smsTemplateDto.setCategoryAwarded(ameFinalReportDetails.getFinalCategoryAwarded());
			        	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-YYYY");
			       	    smsTemplateDto.setAmeCompletedOnDate(simpleDateFormat.format(ameReviewCandidatesData.getReviewCreatedOn()));
			       	    smsTemplateDto.setAmeReviewDate(simpleDateFormat.format(ameReviewCandidatesData.getReviewEndDate()));
			        	
			        	
			       	 SmsResponse smsresponse=smsTemplateService.reviewMedicalBoardSMS(smsTemplateDto,SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_REVIEW_MEDICAL_BOARD);
					
			
					
			    	Optional<ApplicationStateDescription> applicationStateDescription=applicationStateDescriptionRepository.findById(55);
			    	Optional<RefMedicalExamType> examType=medicalExamTypeRepo.findById(1L);
			     
			    	AlertAndNotification alertAndNotificationRecieved=createAlertAndNotification.saveAlertAndNotification(request, candidateForcePersonnelOPOptional, smsresponse.getMessage(), 
							applicationStateDescription, examType,"UploadAmeFinalReport By manual Process", "NA","NA");
			        alertAndNotificationRepository.save(alertAndNotificationRecieved);
					
					logger.info("AmeApplication Status Flow updated......"+ameApplicationFlowStatus);
			        }
			        
			         Optional<AmeFinalReportDetails> ameFinalReportOptional=ameFinalDetailService.findByAmeId(ameId);
			         if(ameFinalReportOptional.isPresent()) {
			        	 SMSTemplateDto dto=new SMSTemplateDto();
			        	 AmeFinalReportDetails ameFinalReportDetailsData=new AmeFinalReportDetails();
			        	 ameFinalReportDetailsData=ameFinalReportOptional.get();
			        	 SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-YYYY");
			        	 dto.setAmeCompletedOnDate(simpleDateFormat.format(Calendar.getInstance().getTime()));
			        	 dto.setCategoryAwarded(ameFinalReportDetailsData.getFinalCategoryAwarded());
						 Optional<ForcePersonnelDto> forcePersonnelOptional= forcePersonalService.getForcePersonnelDetailsByForcePersonnelId(ameFinalReportDetailsData.getCandidateforcePersonalId());
						 dto.setIrlaNo(forcePersonnelOptional.get().getForceId());
						 dto.setMobileNumber(String.valueOf(forcePersonnelOptional.get().getMobileNumber()));
						 dto.setCountryIsdCode("91");
						  
						SmsResponse responseReview= smsTemplateService.ameSMS(dto,SMSConfigurationConstantsAME.DLT_TEMPLATE_ID_AME_SMS);
						
						CreateAlertAndNotification createAlertAndNotificationReview=new CreateAlertAndNotification();
						Optional<ApplicationStateDescription> applicationStateDescriptionReview=applicationStateDescriptionRepository.findById(55);
				    	Optional<RefMedicalExamType> examTypeReview=medicalExamTypeRepo.findById(1L);
				        AlertAndNotification alertAndNotificationReview=createAlertAndNotificationReview.saveAlertAndNotification(request, forcePersonnelOptional, responseReview.getMessage(), 
				        		applicationStateDescriptionReview, examTypeReview,"UploadAmeFinalReport By manual Process", "NA","NA");
				        alertAndNotificationRepository.save(alertAndNotificationReview);
						 
				        logger.info("Final report status updated...........! And SMS message sent successfully.....!");
			         }
			         
					 
			        
				
					}
					
					

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist...............!");
					response.put("isValid", false);
					response.put("fileError","Folder does not exist & data not saved.....Please contact developer team.....");
					//response.put("errors", errors);
					return ResponseEntity.ok(response);

				}}


			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	
	@PostMapping("get-list-of-un-added-investigation-list")
	public ResponseEntity<?> getListofUnAddedInvestigation(@RequestParam("ameId") String ameId) {
		//TODO: process POST request
		List<MedicalCheckUpMaster> checkUpMastersList= medicalCheckUpMasterRepo.MedicalCheckUpMasterUnAssign(ameId);
		if(checkUpMastersList.size()>0) {
			return ResponseEntity.status(HttpStatus.OK).body(checkUpMastersList);
		}else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(checkUpMastersList);
		}
		
		
	}
	
	public static boolean getRoleNameByMedicalRoleCodeStatus(String rMedicalCode) {
		if(rMedicalCode.equalsIgnoreCase("2")||rMedicalCode.equalsIgnoreCase("3")||rMedicalCode.equalsIgnoreCase("4"))
			return true;
		else
			return false;
	}
	
	
	
}
