package nic.ame.app.master.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.InvestigationDto;
import nic.ame.app.master.medical.dto.InvestigationFinalReportDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.AmeFinalReportBoardMemberDetails;
import nic.ame.app.master.medical.model.AmeFinalReportDetails;
import nic.ame.app.master.medical.model.AmeFinalReportFileDir;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.medical.serviceImpl.AmeAssessmentServicePart_1_impl;
import nic.ame.app.master.model.FinalCategoryRemarkTemp;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeFinalReportBoardmemberDetailsRepository;
import nic.ame.app.master.repository.AmeFinalReportDetailsRepository;
import nic.ame.app.master.repository.FinalCategoryRemarkTempRepo;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmeFormDropDownService;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;

@Controller
public class AmeFinalDetailMasterController {
	
	@Autowired
    private AmeMasterStatusService ameMasterStatusService;
	@Autowired
    private AmeAssessmentDisplayService ameAssessmentDisplayService;
	@Autowired
    private InvestigationMasterService investigationMasterService;
	@Autowired
    private AmeAssessmentServicePart_1_impl ameAssessmentServicePart_1_impl;
	@Autowired
	private ForcePersonalService forcePersonalService;
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;
	@Autowired
	private LoginUserDetails loginUserDetails;
	@Autowired
	private MapUriToUserService mapUriToUserService;
	@Autowired
	private AmeFormDropDownService ameFormDropDownService;
	@Autowired
	private FinalCategoryRemarkTempRepo finalCategoryRemarkTempRepo;
	@Autowired
	private AmeFinalReportDetailsRepository ameFinalReportDetailsRepository;
	@Autowired
	private RefForceService refForceService;
   @Autowired
	private AmeFinalReportBoardmemberDetailsRepository ameFinalReportBoardmemberDetailsRepository;
   @Autowired
   private MedicalBoardRepo medicalBoardRepo;
   
   @Autowired
   ForcePersonnelService forcePersonnelService;
   
   @Autowired
   UnitRepository unitRepository;
	
	
	Logger logger=LoggerFactory.getLogger(AmeFinalDetailMasterController.class);
	
	
	
	
	//==========================display Report Final AME============================================//
	
	@PostMapping(path = "view-closed-report-ame" )
	public String DisplayAmeReport(@RequestParam("ameId") String ameId,Model model,
			@RequestParam("forcepersonalId") String forcePersonalId,HttpSession httpSession,RedirectAttributes redirectAttributes) {
		boolean goToFlag = false;
		
		MedExamDtoRequest examDtoRequest = new MedExamDtoRequest();
		
		AmeMasterStatus ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);
		
		Optional<AmeFinalReportDetails> ameFinalReportDetailsOptional =ameFinalReportDetailsRepository.findByAmeId(ameId);
		
		AmeFinalReportDetails ameFinalReportDetails =new AmeFinalReportDetails();
		String loginInForcePersonnel=(String) httpSession.getAttribute("forcepersonalId");

		List<AmeFinalReportBoardMemberDetails> ameFinalReportBoardMemberDetails =ameFinalReportBoardmemberDetailsRepository.
				findByBoardIdAndRoleNameAndBoardMemberForcePersonalId(ameFinalReportDetailsOptional.get().getBoardId(),"ama",loginInForcePersonnel);
		String boardForcePersonnelId, boardId=null;
		Optional<ForcePersonnelDto> boardMemberDetailsOptional = java.util.Optional.empty();
		for (AmeFinalReportBoardMemberDetails ameFinalReportBoardMemberDetails2 : ameFinalReportBoardMemberDetails) {
			if(ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId().equals(loginInForcePersonnel)) {
			boardForcePersonnelId=ameFinalReportBoardMemberDetails2.getBoardMemberForcePersonalId();
			boardId=ameFinalReportBoardMemberDetails2.getBoardId();
			boardMemberDetailsOptional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(boardForcePersonnelId);
			break;
			}
			else {
			
				
				redirectAttributes.addFlashAttribute("message","Login member is not allowed to sign please contact Board Detailing Authority");
			    return"redirect:/application-under-process-ma";
			}
			
		}
		String finalCategoryAwarded = null ,remark = null;
        ForcePersonnelDto  boardMemberDetails = boardMemberDetailsOptional.get();
		
	
		
		if(ameFinalReportDetailsOptional.isPresent()) {
			ameFinalReportDetails=ameFinalReportDetailsOptional.get();
			finalCategoryAwarded=ameFinalReportDetails.getFinalCategoryAwarded();
			remark=ameFinalReportDetails.getRemark();
			
          }
		
		List<FinalCategoryRemarkTemp> finalCategoryRemarkList=finalCategoryRemarkTempRepo.findByAmeId(ameId);
        
		examDtoRequest = ameAssessmentDisplayService.getMedExamDtoRequestData(ameId);
		
		Map<String, Map<String, String>> investigationReportDtosList = investigationMasterService.findInvestigationReportByAmeId(ameId);

		investigationMasterService.findAllInvestigationReportByAmeId(ameId);
		
		InvestigationFinalReportDto investigationFinalReportDto = investigationMasterService.findAllInvestigationReportByAmeId(ameId);

		ForcePersonnelDto forcePersonalDetails = forcePersonalService.getForcePersonalDetails(forcePersonalId);
		
		Optional<ForcePersonnelDto> forcePersonnelOptional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);
		ForcePersonnelDto  forcePersonnel = forcePersonnelOptional.get();
		
		if(forcePersonnel.getAttachUnit()!=null) {
		forcePersonnel.setAttachUnitName(unitRepository.findByUnitId(forcePersonnel.getAttachUnit()).get().getUnitFullName());
		}
		else {
			forcePersonnel.setAttachUnit(null);
		}
		//ForcePersonnelDto forcePersonal=ameAssessmentServicePart_1_impl.getforceForcePersonal(forcePersonalId);
		  
		String boardMemberUnitName=refForceService.getUnitNameByUnitId(boardMemberDetails.getForceNo(), boardMemberDetails.getUnit());
		
		String boardMemberForceName=refForceService.getForceNameByForceId(boardMemberDetails.getForceNo());
		
		MedicalBoard medicalBoardMemberDetails= medicalBoardRepo.getByBoardId(boardId);
		
		String medicalBoardMemberPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoardMemberDetails.getBoardAtForceNo()),medicalBoardMemberDetails.getPlace());
		
		InvestigationDto investigationDto = new InvestigationDto();
		investigationDto = this.investigationMasterService.getReportView(ameId);
		List<FinalCategoryRemarkTemp> finalCategoryRemarkTemps = new ArrayList<>();
		finalCategoryRemarkTemps = finalCategoryRemarkTempRepo.findByAmeId(ameId);

		model.addAttribute("finalCategoryRemarkTemps", finalCategoryRemarkTemps);
        model.addAttribute("remark", remark);
		model.addAttribute("investigationDto", investigationDto);
		
		model.addAttribute("forcePersonalDetails", forcePersonalDetails);
		
		model.addAttribute("investigationReportDtosList", investigationReportDtosList);

		model.addAttribute("ameId", ameId);

		model.addAttribute("examDtoRequest", examDtoRequest);

		model.addAttribute("ameMasterStatus", ameMasterStatus);
		model.addAttribute("forcePersonal", forcePersonnel);


		model.addAttribute("goToFlag", goToFlag);

		model.addAttribute("investigationFinalReportDto", investigationFinalReportDto);
		
		model.addAttribute("finalCategoryAwarded",finalCategoryAwarded);
		
		model.addAttribute("finalCategoryRemarkList", finalCategoryRemarkList);
		
		model.addAttribute("place", medicalBoardMemberPlace);
		
		model.addAttribute("boardMemberUnitName", boardMemberUnitName);
		
		model.addAttribute("boardMemberForceName", boardMemberForceName);
		
		model.addAttribute("boardMemberUnitName", boardMemberUnitName);
		
		model.addAttribute("BoardMemberDetails",boardMemberDetails);
		
		model.addAttribute("forcepersonalId", forcePersonalId);

		return "medical-sub-ordinate/view/ame-report";
		
		
		
	}	

	
	// ======================================Ame final investigation details=======================================//

	@PostMapping("ame-assessment-final-details")
	public String ameAssessmentFinalDetails(@RequestParam("forcepersonalId") String forcepersonalId,
			@RequestParam("ameId") String ameId, @RequestParam("function") String function, Model model,
			HttpSession httpSession) {
		
		
		AmeMasterStatus ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);
		String loginInForcePersonnel=(String) httpSession.getAttribute("forcepersonalId");

		ForcePersonnelDto forcePersonalDetails = forcePersonalService.getForcePersonalDetails(forcepersonalId);
		
		Date declarationDate = forcePersonnelRepository.getDecalarionDate(ameId);
		
		String boardId = forcePersonnelRepository.getBoardId(ameId);
		
		// check.......................................................................................................
		MedicalBoardDetailDto boardDetailDto = medicalBoardMemberService.findMedicalBoardDetailsByCreatedByAndBoardId(boardId);
		// ............................................................................................................
	
		ForcePersonnelDto loginUserDetailsData = loginUserDetails.getLoginUserDetails(loginInForcePersonnel);

		List<MedicalBoardMemberDto> medicalBoardMemberDtos = medicalBoardMemberService
				.getListOfAllBoardMembersByBoardIdOnlyActiveMember(boardId);

		List<MedicalBoardMemberDto> medicalBoardMemberDtosPrint = medicalBoardMemberDtos.stream()
				.filter(s -> s.getRoleName().equals("ama")).collect(Collectors.toList());

		//medicalBoardMemberDtosPrint.forEach(System.out::println);

		// =================================================Personal
		// Details=========================================================
		model.addAttribute("forcePersonalDetails", forcePersonalDetails);
		model.addAttribute("declarationDate", declarationDate);
		// =========================================================================================================================
		model.addAttribute("medicalBoard", boardDetailDto);
		model.addAttribute("loginUserDetails", loginUserDetailsData);

		// =====================================Medical board members Details
		// List====================================================
		model.addAttribute("medicalBoardMemberDtoList", medicalBoardMemberDtos);

		// =======================================Ame Master
		// Status===================================================================
		model.addAttribute("ameMasterStatus", ameMasterStatus);

		String finalShapeAwarded = null;
		
		int categoryDownCode12=0;
		int categoryDownCode24=0;
		
		StringBuffer buffer = new StringBuffer();
		
		if(forcePersonalDetails.getGender().equals("Female"))
		{
			if(ameMasterStatus.getPsycological_shape() != null
					&&ameMasterStatus.getHearingShape() != null
					&&ameMasterStatus.getUpperLimbShape() != null
					&&ameMasterStatus.getLowerLimbShape() != null
					&&ameMasterStatus.getSpineShape() != null
					&&ameMasterStatus.getPhysicalShape() != null
					&&	ameMasterStatus.getEyeShape() != null
					&&ameMasterStatus.getGynaecologyShape() != null) {
				if(ameMasterStatus.getPsycological_shape().equals("S-1")
						&&ameMasterStatus.getHearingShape().equals("H-1")
						&&ameMasterStatus.getUpperLimbShape().equals("A-1(U)")
						&&ameMasterStatus.getLowerLimbShape().equals("A-1(L)")
						&&ameMasterStatus.getSpineShape().equals("A-1(S)")
						&&ameMasterStatus.getPhysicalShape().equals("P-1")
						&&	ameMasterStatus.getEyeShape().equals("E-1")
						&&ameMasterStatus.getGynaecologyShape().equals("G-1")) {
					finalShapeAwarded ="SHAPEG-1";
				}
				else {
					finalShapeAwarded = ameMasterStatus.getPsycological_shape()
							          + "/" + ameMasterStatus.getHearingShape()
							          + "/" + ameMasterStatus.getUpperLimbShape()
							          + "/" + ameMasterStatus.getLowerLimbShape()
							          + "/" + ameMasterStatus.getSpineShape() 
							          + "/" + ameMasterStatus.getPhysicalShape() 
							          + "/" + ameMasterStatus.getEyeShape()
							          + "/" + ameMasterStatus.getGynaecologyShape();
				}
			}
		}
		else {
			if(ameMasterStatus.getPsycological_shape() != null
					&&ameMasterStatus.getHearingShape() != null
					&&ameMasterStatus.getUpperLimbShape() != null
					&&ameMasterStatus.getLowerLimbShape() != null
					&&ameMasterStatus.getSpineShape() != null
					&&ameMasterStatus.getPhysicalShape() != null
					&&	ameMasterStatus.getEyeShape() != null) {
				if(ameMasterStatus.getPsycological_shape().equals("S-1")
						&&ameMasterStatus.getHearingShape().equals("H-1")
						&&ameMasterStatus.getUpperLimbShape().equals("A-1(U)")
						&&ameMasterStatus.getLowerLimbShape().equals("A-1(L)")
						&&ameMasterStatus.getSpineShape().equals("A-1(S)")
						&&ameMasterStatus.getPhysicalShape().equals("P-1")
						&&	ameMasterStatus.getEyeShape().equals("E-1")) {
					
					finalShapeAwarded ="SHAPE-1";
				}
				else {
					finalShapeAwarded = ameMasterStatus.getPsycological_shape()
							          + "/" + ameMasterStatus.getHearingShape()
							          + "/" + ameMasterStatus.getUpperLimbShape()
							          + "/" + ameMasterStatus.getLowerLimbShape()
							          + "/" + ameMasterStatus.getSpineShape() 
							          + "/" + ameMasterStatus.getPhysicalShape() 
							          + "/" + ameMasterStatus.getEyeShape();
				}
				
			}
			
		}
		
				
		
		
		 /* if (ameMasterStatus.getPsycological_shape() != null)
		  buffer.append(ameMasterStatus.getPsycological_shape()); if
		  (ameMasterStatus.getHearingShape() != null) buffer.append(" / " +
		  ameMasterStatus.getHearingShape()); if (ameMasterStatus.getUpperLimbShape()
		  != null) buffer.append(" / " + ameMasterStatus.getUpperLimbShape()); if
		  (ameMasterStatus.getLowerLimbShape() != null) buffer.append(" / " +
		  ameMasterStatus.getLowerLimbShape()); if (ameMasterStatus.getSpineShape() !=
		  null) buffer.append(" / " + ameMasterStatus.getSpineShape()); if
		  (ameMasterStatus.getPhysicalShape() != null) buffer.append(" / " +
		  ameMasterStatus.getPhysicalShape()); if (ameMasterStatus.getEyeShape() !=
		  null) buffer.append(" / " + ameMasterStatus.getEyeShape()); if
		  (ameMasterStatus.getGynaecologyShape() != null) { buffer.append(" / " +
		  ameMasterStatus.getGynaecologyShape()); } else { buffer.append(""); }*/
		 
		//finalShapeAwarded = buffer.toString();
		
		if(ameMasterStatus.getPsycological_shape()!=null
				&&ameMasterStatus.getHearingShape()!=null
						&&ameMasterStatus.getUpperLimbShape()!=null
								&&ameMasterStatus.getLowerLimbShape()!=null
										&&ameMasterStatus.getSpineShape()!=null
												&&ameMasterStatus.getPhysicalShape()!=null
														&&ameMasterStatus.getEyeShape()!=null) {
		if(ameMasterStatus.getPsycological_shape().contains("T-12")
				||ameMasterStatus.getHearingShape().contains("T-12")
				||ameMasterStatus.getUpperLimbShape().contains("T-12")
				||ameMasterStatus.getLowerLimbShape().contains("T-12")
				||ameMasterStatus.getSpineShape().contains("T-12")
				||ameMasterStatus.getPhysicalShape().contains("T-12")
				||ameMasterStatus.getEyeShape().contains("T-12")) {
			categoryDownCode12=1;
		}}

		if(ameMasterStatus.getPsycological_shape()!=null
				&&ameMasterStatus.getHearingShape()!=null
						&&ameMasterStatus.getUpperLimbShape()!=null
								&&ameMasterStatus.getLowerLimbShape()!=null
										&&ameMasterStatus.getSpineShape()!=null
												&&ameMasterStatus.getPhysicalShape()!=null
														&&ameMasterStatus.getEyeShape()!=null) {
		if(ameMasterStatus.getPsycological_shape().contains("T-24")
				||ameMasterStatus.getHearingShape().contains("T-24")
				||ameMasterStatus.getUpperLimbShape().contains("T-24")
				||ameMasterStatus.getLowerLimbShape().contains("T-24")
				||ameMasterStatus.getSpineShape().contains("T-24")
				||ameMasterStatus.getPhysicalShape().contains("T-24")
				||ameMasterStatus.getEyeShape().contains("T-24")) {
			categoryDownCode24=1;
		}
		}
		if(ameMasterStatus.getGynaecologyShape()!=null) {
			if(ameMasterStatus.getGynaecologyShape().contains("T-12")) {
				categoryDownCode12=1;
			}else if(ameMasterStatus.getGynaecologyShape().contains("T-24")) {
				categoryDownCode24=2;
			}
		}
		
		// =======================================ForceId===================================================================
		model.addAttribute("forcepersonalId", forcepersonalId);
		model.addAttribute("ameId", ameId);
		model.addAttribute("finalShapeAwarded", finalShapeAwarded);

		int rCode = (int) httpSession.getAttribute("rCodeMedical");

		String uri = mapUriToUserService.getUriForFinalDetailsProcess(rCode);

		logger.info("rCode>>>>>>>>>" + rCode + ">>>>>>>>>>>>>>>>BoardId>>>>>>>>>>>>>>>>" + boardId);
		logger.info("URI>>>>>>>>>" + uri);

		List<DropDownDto> categoryType = ameFormDropDownService.getListOfDownCategory(ameMasterStatus,
				forcePersonalDetails.getGender());
		List<FinalCategoryRemarkTemp> finalCategoryRemarkTemp = finalCategoryRemarkTempRepo.findByAmeId(ameId);
		Stream<DropDownDto> filter = categoryType.stream().filter(i->finalCategoryRemarkTemp.stream().noneMatch(j->j.getCategoryType().equals(i.getcValue())));
		model.addAttribute("categoryType",filter.collect(Collectors.toList()) );
	  //model.addAttribute("categoryType", categoryType);
		model.addAttribute("categoryDownCode12", categoryDownCode12);
		model.addAttribute("categoryDownCode24", categoryDownCode24);

		List<FinalCategoryRemarkTemp> finalCategoryRemarkTemps = new ArrayList<>();
		finalCategoryRemarkTemps = finalCategoryRemarkTempRepo.findByAmeId(ameId);

		model.addAttribute("finalCategoryRemarkTemps", finalCategoryRemarkTemps);

		if (function.equalsIgnoreCase("print")) {
			model.addAttribute("baoadMemberName", medicalBoardMemberDtosPrint.get(0).getName());
			model.addAttribute("baoadMemberDesignation", medicalBoardMemberDtosPrint.get(0).getDesignation());

			return uri + "/print-ame-assessment-final-details-page";
		}

		else {

			return uri + "/ame-assessment-final-details-page";
		}

	}
	
	@PostMapping("get-category-type-list-for-remark")
	
	public ResponseEntity<?> getCategoryTypeListForRemark(@RequestBody Map<String, Object> data) {

		Map<String, Object> response = new HashMap<>();
		Map<String, Object> errors = new HashMap<>();
		try {
			String ameId = (String) data.get("ameId");
			String forcePersonnelId = (String) data.get("forcePersonnelId");
			AmeMasterStatus ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);

			ForcePersonnelDto forcePersonalDetails = forcePersonalService.getForcePersonalDetails(forcePersonnelId);

			List<DropDownDto> categoryType = ameFormDropDownService.getListOfDownCategory(ameMasterStatus,
					forcePersonalDetails.getGender());
			List<FinalCategoryRemarkTemp> finalCategoryRemarkTemp = finalCategoryRemarkTempRepo.findByAmeId(ameId);
			Stream<DropDownDto> filter = categoryType.stream().filter(
					i -> finalCategoryRemarkTemp.stream().noneMatch(j -> j.getCategoryType().equals(i.getcValue())));
			List<DropDownDto> categoryTypeList = filter.collect(Collectors.toList());
			if (categoryTypeList.size() < 0) {
				errors.put("errorMessage", "Oops something went wrong!");
			}
			response.put("categoryType", categoryTypeList);

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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");

		}
	}
	
	
}
