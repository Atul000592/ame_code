package nic.ame.app.ama.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.ama.service.SubordinateService_2;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.MedicalCheckUpListDto;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.MedicalCheckUpMaster;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.medical.service.AmeMasterStatusService;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.constant.CommonConstant;

@Controller
public class AmaDashBoardController {

	@Autowired
	private SubordinateService_2 subordinateService_2;

	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;

	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;

	@Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;

	@Autowired
	private AmeMasterStatusService ameMasterStatusService;
	
	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	
	

	
	@Autowired
	private AmeParametersRepository ameParametersRepository;

	
	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;

	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;
	
	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	private MapUriToUserService mapUriToUserService;
	
	
	@Autowired
	private ForceRepo forceRepo;
	
	private Logger logger =LoggerFactory.getLogger(AmaDashBoardController.class);

	@GetMapping({ "/medical-attendent-dashboard",
			"/medical-subordinate-dashboard" })
	public String goToBoardMemberDashboard(HttpSession session, Model model) {
		
	
		String gazettedNonGazettedFlag = (String) session.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		
		
	
		
		String boardId = (String) session.getAttribute("boardId");
		MedicalBoard boardOptional= medicalBoardRepo.findByBoardId(boardId);
		
		//MedicalBoard board=new MedicalBoard();
		MedicalBoardDetailDto  boardDetailDto=new MedicalBoardDetailDto();
 		if(boardOptional.getBoardId()!=null) {
 			boardDetailDto=medicalBoardMemberService.findBoardDetailsByBoardId(boardId);
 			model.addAttribute("boardDetails",boardDetailDto );
 		}else {
 		
 			model.addAttribute("boardDetails", boardDetailDto);
 		}
		Integer pendingCount = amaDeclarationCountService.getAMADeclarationPendingListCount(boardId.trim());
		String pendingCountValue;
		if (pendingCount == null) {
			pendingCountValue = "0";
		} else {
			pendingCountValue = String.valueOf(pendingCount);
		}

		Integer appointmentCompleted = amaDeclarationCountService.findDataForAMAAppointmentCount(boardId);
		String appointmentCompletedValue;
		if (appointmentCompleted == null) {
			appointmentCompletedValue = "0";
		} else {
			appointmentCompletedValue = String.valueOf(appointmentCompleted);
		}
		//Integer totalCompletedAma = amaDeclarationCountService.findDataForDealingHandCount(unit,forceNo);
		String totalCompletedAmaValue = "0";
		String forcePersonalId = (String) session.getAttribute("forcepersonalId");
		// Optional<ForcePersonal> optional=
		// forcePersonalRepository.getByForcePersonalId(forcePersonalId);

		Integer totalCompletedCount = amaDeclarationCountService.findTotalDeclarationUnderProcessByBoardId(boardId);
		if (totalCompletedCount != null) {
			totalCompletedAmaValue = String.valueOf(totalCompletedCount);
		}
		if (forcePersonalId == null) {
			session.invalidate();
			model.addAttribute("errorMsg", "You have been sign out // Session expired.....! or Invalid User....");
			return "bootstrap_medical_temp/index";
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
		
		System.out.println(">>>>>>>>>>>>>>>>>>>" + boardId);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
		model.addAttribute("pendingcountama", pendingCountValue);
		model.addAttribute("appointmentcompletedama", appointmentCompletedValue);
		model.addAttribute("totalCompletedAma", totalCompletedAmaValue);
		model.addAttribute("boardCount",medicalBoardIndividualMappingRepo.boardCount(forcePersonalId,
				Integer.parseInt( ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG))));

		model.addAttribute("boardId", boardId);
		return "medical-sub-ordinate/dashboard-ma";
	}

	// ----redirecting after saving Ame report data into database-----------//

	@GetMapping("/form-index-page-am")
	public String redirectToFromIndexPage(Model model, HttpServletRequest request, HttpSession httpSession) {
		String forcepersonalId = null;
		String ameId = null;
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			forcepersonalId = (String) inputFlashMap.get("forcepersonalId");
			ameId = (String) inputFlashMap.get("ameId");
		}
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
		if (forcepersonalId != null && ameId != null) {
			model.addAttribute("forcepersonalId", forcepersonalId);
			model.addAttribute("ameId", ameId);
			return "BoardMember/form-index-page-ma";
		} else {
			model.addAttribute("errorMsg", "You have been sign out // Session expired.....!");
			return "bootstrap_medical_temp/index";

		}

	}

	

	@GetMapping("/application-under-process-ama")
	public String showdeclarationtocontroller(Model model, HttpSession httpSession) {

		String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String roleName = (String) httpSession.getAttribute("roleName");
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		logger.info(">>>>>>roleName>>>>>" + roleName + ">>>>>>>>ForcePersonalId>>>>>>>>" + forcepersonalId);
		Optional<ForcePersonnel> optional = forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
		String unit = optional.get().getUnit();

		System.out.println(">>>>>>>" + unit);
		if (forcepersonalId == null) {

			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}

		List<AmeDeclarationIndividualModel> declarationIndividualDetails = subordinateService_2
				.getDeclarationListForAMA(forcepersonalId, roleName);

		if (declarationIndividualDetails.isEmpty()) {
			model.addAttribute("message", "HAVE NO RECORD TO DISPALY...!");
			return "medical-sub-ordinate/display-application-under-process-ma";
		}
		model.addAttribute("declarationDetails", declarationIndividualDetails);
		return "medical-sub-ordinate/display-application-under-process-ma";

	}

	// ----redirecting to form-index-page-dh without any action-----------//

	@PostMapping("/back-to-index-page-ama")
	public String backToFromIndexPageAMA(Model model, HttpServletRequest request,
			@RequestParam("forcepersonalId") String candidateforcepersonalId,
			@RequestParam("ameId") String ameId,
			HttpSession httpSession) {
		/*
		 * Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		 * if (inputFlashMap != null) { forcepersonalId = (String)
		 * inputFlashMap.get("forcepersonalId"); ameId
		 * =(String)inputFlashMap.get("ameId"); }
		 */
		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		AmeMasterStatus ameMasterStatus = new AmeMasterStatus();
		ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);
		model.addAttribute("ameMasterStatus", ameMasterStatus);
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));

		if (candidateforcepersonalId != null && ameId != null) {
			model.addAttribute("forcepersonalId", candidateforcepersonalId);
			model.addAttribute("ameId", ameId);

			return "medical-sub-ordinate/form-index-page-ma";
		} else {
			model.addAttribute("errorMsg", "You have been sign out // Session expired.....!");
			return "bootstrap_medical_temp/index";

		}
	}

	@PostMapping("ame-check-up-list")
	public String medicalCheckUpListDisplayPage(Model model, String ameId, String CandidateforcePersonalId,
			HttpSession httpSession) {
		
		
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(CandidateforcePersonalId));

		int rCode = (int) httpSession.getAttribute("rCodeMedical");
    
		logger.info("rCode>>>>>>>>>"+rCode);
		String uri = mapUriToUserService.getUriForCheckListForCandidate(rCode);
		logger.info("rCode>>>>>>>>>"+uri);

		List<MedicalCheckUpMaster> medicalMaster = ameAssessmentDisplayService.checkUpMasters();
		
		
       		
		
		
		List<MedicalCheckUpListDto> checkUpListDtos=new ArrayList<>();
		
		if (medicalMaster != null && !medicalMaster.isEmpty()) {
			
		    for (int i = 0; i < medicalMaster.size(); i++) {
		    	
		    	MedicalCheckUpListDto checkUpListDto=new MedicalCheckUpListDto();
		    	checkUpListDto.setCode(medicalMaster.get(i).getTestCode());
		    	checkUpListDto.setName(medicalMaster.get(i).getTestName());
                 if(medicalMaster.get(i).getDefaultCheckedFlag()==1)
                	 checkUpListDto.setChecked(true);
                 else
                	 checkUpListDto.setChecked(false);

                //logger.info("Test Add........checkUplist");
                checkUpListDtos.add(checkUpListDto);
            
            }
		     
		}
		
		model.addAttribute("checkUpListDtos",checkUpListDtos);
		model.addAttribute("ameId", ameId);
		model.addAttribute("candidateForcePersonalId", CandidateforcePersonalId);
		
				return uri;

	}
	
	//========================Redirect to Ame Fill form page=======================//
	@PostMapping("/index-page-ama")
	public String redirectFromIndexPageAMA(Model model, HttpServletRequest request,
			@RequestParam("forcepersonalId") String candidateforcepersonalId,
			@RequestParam("ameId") String ameId,
			HttpSession httpSession) {
		/*
		 * Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		 * if (inputFlashMap != null) { forcepersonalId = (String)
		 * inputFlashMap.get("forcepersonalId"); ameId
		 * =(String)inputFlashMap.get("ameId"); }
		 */
		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		AmeMasterStatus ameMasterStatus = new AmeMasterStatus();
		ameMasterStatus = ameMasterStatusService.getAmeMasterStatus(ameId);
		model.addAttribute("ameMasterStatus", ameMasterStatus);
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		model.addAttribute("candidateDetails", loginUserDetails.getCandicateForcePersonalId(candidateforcepersonalId));

		if (candidateforcepersonalId != null && ameId != null) {
			model.addAttribute("forcepersonalId", candidateforcepersonalId);
			model.addAttribute("ameId", ameId);

			return "medical-sub-ordinate/form-index-page-ma";
		} else {
			model.addAttribute("errorMsg", "You have been sign out // Session expired.....!");
			return "bootstrap_medical_temp/index";

		}
	}
	
	
	

	@GetMapping("appointment-and-board-List")
	public String getBoardListByForcePersonal(Model model, HttpSession httpSession) {
		
		
		
		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		
		List<MedicalBoardMemberDto> list = medicalBoardMemberService.listOfBoardByForcePersonalId(forcePersonalId);

		if (list.size() == 0) {

			model.addAttribute("boardList", list);

		} else {
			model.addAttribute("boardList", list);
		}
		return "medical-sub-ordinate/list-of-board-to-force-personal";
	}

	@GetMapping("/pending-for-appointment-controller-ma")
	public String getAppointmentDetailsAma(Model model,HttpSession httpSession) {
		
		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String boardId=(String) httpSession.getAttribute("boardId");
		
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		
		List<MedicalBoardMemberDto> list = medicalBoardMemberService.listOfBoardByLoginForcePersonnelBoardId(boardId,forcePersonalId);
		
		if (list.size() == 0) {

			model.addAttribute("boardList", list);

		} else {
			model.addAttribute("boardList", list);
		}
		return "medical-sub-ordinate/list-of-board-to-force-personal";
	}

@GetMapping(path = "/assign-dealing-hand-to-unit")
public String assignDealingHandPage(Model model ,HttpSession httpSession) {

	String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
	//String sBoardId=(String) httpSession.getAttribute("sBoardId");
	ForcePersonnelDto forcePersonalDto=loginUserDetails.getLoginUserDetails(loginForcePersonalId);
    model.addAttribute("loginUserDetails", forcePersonalDto);
	List<DropDownDto> downDtosList=new ArrayList<>();

    List<MedicalBoardMember> medicalBoardsList=medicalBoardMemberRepo.findByForcePersonalId(loginForcePersonalId);
    
    for (MedicalBoardMember medicalBoardMember : medicalBoardsList) {
    	MedicalBoard medicalBoardData=medicalBoardRepo.getByBoardId(medicalBoardMember.getBoardId());
    	if(medicalBoardData.getBoardId()!=null) {
    		DropDownDto downDto=new DropDownDto();
    		StringBuffer buffer=new StringBuffer();
    		String place=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoardData.getBoardAtForceNo()),medicalBoardData.getPlace());
    		buffer=buffer.append(medicalBoardData.getBoardId()).append(" || "+place).append(" || "+medicalBoardData.getUsedFor()+" || ");
    		if(medicalBoardData.getGazettedFlag()==1) {
    			buffer.append("Gazetted");
    		}else {
    			buffer.append("Non-Gazetted");
    		}
    		downDto.setBoardId(medicalBoardData.getBoardId());
    	    downDto.setValue(buffer.toString());
    	    downDtosList.add(downDto);	
    	}
	}

    List<Force> forcesList=forceRepo.findForceListByForceId(forcePersonalDto.getForceNo());
    
	model.addAttribute("downDtosList",downDtosList);
	model.addAttribute("forceList", forcesList);
	
	
	return"medical-sub-ordinate/assign-dealing-hand-to-unit";
}

}
