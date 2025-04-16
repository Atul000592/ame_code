package nic.ame.app.bmpo.controller;


import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.admin.service.MedicalBoardMemberService;

import nic.ame.app.master.controller.MasterFunctionalController;

import nic.ame.app.master.dto.AppointmentListDto;

import nic.ame.app.master.dto.UserRoleDto;

import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;

import nic.ame.app.master.model.ForcePersonnel;

import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.AmeAppointmentAndDeclarationService;

import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.constant.CommonConstant;

@Controller
public class BoardMemberAndPresidingOfficerController {
	


	@Autowired
	private ForcePersonnelService forcePersonnelService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;

	@Autowired
	private MedicalBoardIndividualMappingService boardIndividualMappingService;

	@Autowired
	private AmeParametersRepository ameParametersRepository;

	@Autowired
	private AmeAppointmentAndDeclarationService ameAppointmentAndDeclarationService;

	@Autowired
	private MapUriToUserService mapUriToUserService;
	
	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
	
	
	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;

	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
	
	@Autowired
	private ServletContext servletContext;
	

	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
	
	
	
	Logger logger = LogManager.getLogger(MasterFunctionalController.class);



	
	@PostMapping(value={"role-map-to-board-member-dashboard"})
	public String gazettedBoardMemberDashboard(Model model,
			HttpServletRequest httpServletRequest,
			@RequestParam(name="forcePersonalId",required = false) String forcePersonalId,
			@RequestParam(name="boardId",required = false) String boardId,
			@RequestParam(name="rCode",required = false)String rCode,HttpSession httpSession) {
        String loggedInForcePersonnel;
      
		
		if(forcePersonalId==null) {
			loggedInForcePersonnel=(String) httpSession.getAttribute("forcepersonalId");
			
		}else {
			loggedInForcePersonnel=forcePersonalId;
		}
		if(boardId==null) {
			boardId=(String) httpSession.getAttribute("boardId");
		}
		
		if(rCode==null) {
			rCode=(String) httpSession.getAttribute("rCode");
		}else {
			  httpSession.setAttribute("rCode", rCode);
		}
		
	   httpSession.setAttribute("boardId", boardId);
		
       model.addAttribute("forcePersonalId",loggedInForcePersonnel);
       model.addAttribute("boardId",boardId);
       model.addAttribute("rCode",rCode);
		model.addAttribute("loginUserDetails", forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(loggedInForcePersonnel).get());
	
		return "medical-bm/gazetted-board-member-dashboard-page";
	}
	
	
	
	@GetMapping("pending-for-appointment-controller-bm")
	public String getAppointmentDetailsAma(Model model,HttpSession httpSession) {
		
		String forcePersonnelId = (String) httpSession.getAttribute("forcepersonalId");
		String boardId=(String) httpSession.getAttribute("boardId");
		
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonnelId));
		
		List<MedicalBoardMemberDto> meicalBoardMemberList = medicalBoardMemberService.listOfBoardByLoginForcePersonnelBoardId(boardId,forcePersonnelId);
		
		if (meicalBoardMemberList.size() == 0) {

			model.addAttribute("boardList", meicalBoardMemberList);

		} else {
			model.addAttribute("boardList", meicalBoardMemberList);
		}
		return "medical-bm/list-of-board-to-force-personal";
	}

//------------------------ Declaration Approval by Board Member ------------------------//
	
	@RequestMapping(value = "/give-appointment-to-force-personal-bm", method = RequestMethod.POST)
	public String giveAppointmentToForcepersonalForAma(@RequestParam("boardId") String boardId,
			@RequestParam("forcepersonalId") String forcePersonnelId, Model model) {

		List<MedicalBoardMemberDto> boardMemberDtos = boardIndividualMappingService
				.getAmeAppointmentPendingListByBoardId(boardId, Integer.parseInt(
						ameParametersRepository.getAmeParameterValue(CommonConstant.AME_APPOINTMENT_PENDING)));
		List<AppointmentListDto> listOfAppointment = ameAppointmentAndDeclarationService.getAppointmentDtoList(boardId);
		model.addAttribute("boardMemberDtos", boardMemberDtos);
		model.addAttribute("boardId", boardId);

		model.addAttribute("LoginForcePersonalId", forcePersonnelId);
		model.addAttribute("listOfAppointment", listOfAppointment);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonnelId));
		
		return "medical-bm/give-appointment-to-force-personal-bm";
	}
	
//------------------------ Declaration Approval by Board Member ------------------------//
	
	/*
	 * @GetMapping("show-ame-declaration-list-bm") public String
	 * showDeclarationtoController(HttpSession httpSession, Model model) {
	 * 
	 * String forcepersonalId = (String)
	 * httpSession.getAttribute("forcepersonalId"); String boardId = (String)
	 * httpSession.getAttribute("boardId"); int rCode =
	 * Integer.parseInt((String)(httpSession.getAttribute("rCode"))); final String
	 * URI =
	 * mapUriToUserService.getUriForShowingAmeDeclarationListToUserMedicalRole(rCode
	 * );
	 * 
	 * logger.info("rCode>>>>>>>>>"+rCode+">>>>>>>>>>>>>>>>BoardId>>>>>>>>>>>>>>>>"+
	 * boardId); logger.info("URI>>>>>>>>>"+URI);
	 * 
	 * if (forcepersonalId == null) {
	 * 
	 * model.addAttribute("errorMsg",
	 * "INVALID USER TRY TO GET THE DATA ..........!.......Invalid request or session expired"
	 * ); return "bootstrap_medical_temp/login-page"; }
	 * 
	 * List<AmeApplicationFlowStatusDto> AppointmentCompletedListDisplay =
	 * ameApplicationFlowStatusService .ameApplicationFlowStatusDtos(boardId);
	 * 
	 * 
	 * model.addAttribute("AppointmentCompletedListDisplay",
	 * AppointmentCompletedListDisplay);
	 * 
	 * String candidateGazettedNonGazettedFlag = (String)
	 * httpSession.getAttribute("candidateGazettedNonGazettedFlag");
	 * model.addAttribute("candidateGazettedNonGazettedFlag",
	 * candidateGazettedNonGazettedFlag);
	 * 
	 * String gazettedNonGazettedFlag = (String)
	 * httpSession.getAttribute("gazettedNonGazettedFlag");
	 * model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
	 * 
	 * List<DealingHandDto> ameApplicationFlowStatusDtos = amaDealingHandService
	 * .listOfDeclarationCompletePendingForApproval(boardId);
	 * model.addAttribute("loginUserDetails",
	 * loginUserDetails.getCandicateForcePersonalId(forcepersonalId)); if
	 * (ameApplicationFlowStatusDtos.isEmpty()) { model.addAttribute("message",
	 * "HAVE NO RECORD TO DISPALY...! "); return URI;
	 * 
	 * } model.addAttribute("declarationDetails", ameApplicationFlowStatusDtos);
	 * 
	 * return URI; }
	 */

//	START AME DASHBOARD
	
	
	@RequestMapping(value = "role-map-to-ame-bm-dashboard",method = {RequestMethod.GET,RequestMethod.POST})
	public String getToAmaDashboard(
			@RequestParam(name="forcePersonalId",required = false) String forcePersonalId,
			@RequestParam(name="boardId",required = false) String boardId,
			@RequestParam(name="rCode",required = false) String rCode,
			Model model,
			HttpServletRequest httpServletRequest,HttpSession httpSession) {
		
		HttpSession session=httpServletRequest.getSession(false);
		if(session==null) {
			model.addAttribute("errorMsg", "No valid details found in the database please contact admin...");
			return "bootstrap_medical_temp/index";
			
		}
		
		if(forcePersonalId==null)
			forcePersonalId=(String)session.getAttribute("forcepersonalId");
		if(boardId==null)
			boardId=(String)session.getAttribute("boardId");
		if(rCode==null)
			rCode=(String)session.getAttribute("rCode");
			
			
		
		int checkupListPending;
		int checkupListCompleted;
		Integer pendingAmeFormCheckPending;
		Integer fileuploadPending;
		//int rCode= (int) httpSession.getAttribute("rCodeMedical");
		MedicalBoardDetailDto boardDetailDto=new MedicalBoardDetailDto();
		session.setAttribute("boardId", boardId.trim());
		session.setAttribute("rCodeMedical",Integer.parseInt(rCode.trim()));
		/* logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+rCode); */
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
		//Optional<ForcePersonnel> optional= forcePersonnelRepository.getByForcePersonnelId(forcePersonalId);
		//int forceNo=optional.get().getForceNo();
		//String unit=optional.get().getUnit();
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
		
	     
		@SuppressWarnings("unchecked")
		List<UserRoleDto> userRoleDtos= (List<UserRoleDto>) servletContext.getAttribute(forcePersonalId);
        model.addAttribute("roleDtosList", userRoleDtos);
		
		
		
		session.setAttribute("forcePersonalId", forcePersonalId);
		session.setAttribute("roleName",roleName.trim().toUpperCase());
		model.addAttribute("pendingcountama", pendingCountValue);
		model.addAttribute("appointmentcompletedama",appointmentCompletedValue);
		model.addAttribute("totalCompletedAma", totalCompletedAma);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
		model.addAttribute("boardId",boardId);
		model.addAttribute("forcePersonnelId", forcePersonalId);
		model.addAttribute("forcePersonalId", forcePersonalId);
		model.addAttribute("rCode", rCode);
		model.addAttribute("boardCount",medicalBoardIndividualMappingRepo.boardCount
				(forcePersonalId,Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG))));
          
		
		return"medical-bm/ame-dashboard-bm";
	
	}
	
	
	
//	Approval //
	/*
	 * @RequestMapping(path = "show-ame-declaration-form-details-bm", method =
	 * RequestMethod.POST) public String
	 * showAmeDeclarationDetailsToUser(@RequestParam("forcepersonalId") String
	 * candidateForcePercenalId,
	 * 
	 * @RequestParam("ameId") String ameId, Model model, HttpSession httpSession) {
	 * 
	 * logger.info(">>>>Forcepersonal>>" + candidateForcePercenalId +
	 * ">>>>>>>>>>>>AME-ID" + ameId);
	 * 
	 * String forcepersonalId = (String)
	 * httpSession.getAttribute("forcepersonalId");
	 * 
	 * int rCode = (int) httpSession.getAttribute("rCodeMedical");
	 * 
	 * logger.info("rCode>>>>>>>>>"+rCode); String uri =
	 * mapUriToUserService.getUriForShowingAmeDeclarationFormToUser(rCode);
	 * logger.info("URI info......>>"+uri);
	 * 
	 * String gazettedNonGazettedFlag = (String)
	 * httpSession.getAttribute("gazettedNonGazettedFlag");
	 * model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
	 * 
	 * Optional<AmeDeclarationIndividualDetails>
	 * ameDeclarationIndividualDetailsOptional =
	 * ameDeclarationIndividualDetailsRepo.findByAmeId(ameId);
	 * Optional<AmeDeclarationIndividualModel> ameDeclarationIndividualModelOptional
	 * =
	 * ameDeclarationRepository.findByForcePersonalIdData(candidateForcePercenalId,
	 * ameId);
	 * 
	 * AmeDeclarationIndividualDetails ameDeclarationIndividualDetailsOptionalData =
	 * new AmeDeclarationIndividualDetails(); AmeDeclarationIndividualModel
	 * ameDeclarationIndividualModelOptionalData = new
	 * AmeDeclarationIndividualModel(); ForcePersonnelDto
	 * candidateForcepersonnel=loginUserDetails.getCandicateForcePersonalId(
	 * candidateForcePercenalId);
	 * 
	 * model.addAttribute("loginUserDetails",
	 * loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
	 * model.addAttribute("candidateDetails", candidateForcepersonnel);
	 * 
	 * 
	 * if (ameDeclarationIndividualDetailsOptional.isPresent() &&
	 * ameDeclarationIndividualModelOptional.isPresent()) {
	 * logger.info(">>>>>>> ameID :- " +
	 * ameDeclarationIndividualDetailsOptional.get().getAmeId());
	 * logger.info(">>>>>>>>>ForcePersonalId :-" +
	 * ameDeclarationIndividualModelOptional.get().getForcePersonalId());
	 * ameDeclarationIndividualModelOptionalData =
	 * ameDeclarationIndividualModelOptional.get();
	 * 
	 * model.addAttribute("designation",rankRepo.findById(
	 * ameDeclarationIndividualModelOptionalData.getDesignation()).get().
	 * getRankFullName());
	 * 
	 * ameDeclarationIndividualDetailsOptionalData =
	 * ameDeclarationIndividualDetailsOptional.get();
	 * 
	 * model.addAttribute("forcepersonalId", candidateForcePercenalId);
	 * model.addAttribute("name",
	 * ameDeclarationIndividualModelOptionalData.getName());
	 * model.addAttribute("forceId",
	 * ameDeclarationIndividualModelOptionalData.getForceId());
	 * model.addAttribute("dataList1", ameDeclarationIndividualDetailsOptionalData);
	 * model.addAttribute("individualModel",
	 * ameDeclarationIndividualModelOptionalData); model.addAttribute("AmeId",
	 * ameId);
	 * 
	 * return uri; } else {
	 * 
	 * model.addAttribute("forcepersonalId", candidateForcePercenalId);
	 * model.addAttribute("details", ameDeclarationIndividualDetailsOptionalData);
	 * model.addAttribute("individualModel",
	 * ameDeclarationIndividualModelOptionalData); model.addAttribute("AmeId",
	 * ameId);
	 * 
	 * return uri; }
	 * 
	 * }
	 */
	
	

	@RequestMapping(path = "/ame-declaration-checked-approved-bm",method = RequestMethod.POST)
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
		
		URI=mapUriToUserService.getUriAfterAMEDeclarationDataCheck(rCode);
		logger.info("URI>>>>>>>>>"+URI);
		String candidateGazettedNonGazettedFlag = (String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

	
		logger.info(">>> forcepersonalId >>>" + candidateForcepersonalId + ">>> ameId >>>" + ameId);

	
		Optional<ForcePersonnel> optional = forcePersonnelRepository.getByForcePersonnelId(candidateForcepersonalId);
		if (!optional.isPresent()) {
			model.addAttribute("error-message",
					"sorry for inconvenience....Our Team is working on to solve the issue.");
			return "bootstrap_medical_temp/error-page";

		}
		
		model.addAttribute("forcepersonalId", candidateForcepersonalId);
		model.addAttribute("ameId", ameId);
		boolean result = ameAssessmentServicePart_2.updateAmeStatus(ameId,remark);

		if (result) {
			logger.info("Appointment given Sucessfully >>>" + result);
			}

	
		return "redirect:/show-ame-declaration-list";

	}
	
// ----- Upload AME declaration and Investigation List ----- //
	/*
	 * @GetMapping("display-pending-requests-upload-checkup-bm") public String
	 * displayDeclarationRequestsCompleted(Model model, HttpServletRequest
	 * httpServletRequest) { HttpSession httpSession =
	 * httpServletRequest.getSession(); String boardId = (String)
	 * httpSession.getAttribute("boardId"); String forcepersonalId = (String)
	 * httpSession.getAttribute("forcepersonalId");
	 * logger.info("controlling force personalId :" + forcepersonalId);
	 * logger.info("controlling BoardId >>> :" + boardId);
	 * 
	 * int rCode = (int) httpSession.getAttribute("rCodeMedical");
	 * 
	 * String uri=mapUriToUserService.getUriForPendingAndUpLoad(rCode);
	 * logger.info("rCode>>>>>>>>>"+rCode+">>>>>>>>>>>>>>>>BoardId>>>>>>>>>>>>>>>>"+
	 * boardId); logger.info("URI>>>>>>>>>"+uri);
	 * 
	 * Optional<ForcePersonnel> optional =
	 * forcePersonnelRepository.getByForcePersonnelId(forcepersonalId); String
	 * gazettedNonGazettedFlag = (String)
	 * httpSession.getAttribute("gazettedNonGazettedFlag");
	 * 
	 * String candidateGazettedNonGazettedFlag = (String)
	 * httpSession.getAttribute("candidateGazettedNonGazettedFlag");
	 * model.addAttribute("candidateGazettedNonGazettedFlag",
	 * candidateGazettedNonGazettedFlag);
	 * 
	 * model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
	 * String unit = optional.get().getUnit(); Integer forceNo =
	 * optional.get().getForceNo();
	 * 
	 * logger.info(">>>>Display Controller page>>>" + unit + ">>>>>>forceNumber>>" +
	 * forceNo); if (forcepersonalId == null) { httpSession.invalidate();
	 * model.addAttribute("errorMsg",
	 * "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST"); return
	 * "bootstrap_medical_temp/index"; }
	 * 
	 * model.addAttribute("loginUserDetails",
	 * loginUserDetails.getLoginUserDetails(forcepersonalId));
	 * 
	 * List<AmeDeclarationIndividualModel> declarationIndividualDetails =
	 * ameDeclarationRepository.findDataForAMAAppointmentList(boardId);
	 * List<AmeDeclarationIndividualModelDto> declarationIndividualDetailsList=new
	 * ArrayList<>();
	 * 
	 * for (AmeDeclarationIndividualModel ameDM : declarationIndividualDetails) {
	 * AmeDeclarationIndividualModelDto adto=new AmeDeclarationIndividualModelDto();
	 * adto.setAmeId(ameDM.getAmeId());
	 * adto.setForcePersonalId(ameDM.getForcePersonalId());
	 * adto.setForceId(ameDM.getForceId()); adto.setName(ameDM.getName());
	 * 
	 * adto.setDesignation(rankRepo.findById(ameDM.getRank()).get().getRankFullName(
	 * )); adto.setDeclarationDate(ameDM.getDeclarationDate());
	 * declarationIndividualDetailsList.add(adto);
	 * 
	 * 
	 * }
	 * 
	 * 
	 * List<AmeApplicationFlowStatusDto> AppointmentCompletedList =
	 * ameApplicationFlowStatusService.
	 * getAmeApplicationFlowStatusListUploadCompletedByBoardId(boardId);
	 * 
	 * 
	 * 
	 * if (declarationIndividualDetails.isEmpty()) { model.addAttribute("message",
	 * "HAVE NO RECORD TO DISPLAY "); if (AppointmentCompletedList.size() >= 1) {
	 * model.addAttribute("AppointmentCompletedList", AppointmentCompletedList); }
	 * return uri; } else { if (AppointmentCompletedList.size() >= 1) {
	 * model.addAttribute("AppointmentCompletedList", AppointmentCompletedList);
	 * model.addAttribute("declarationDetails", declarationIndividualDetailsList); }
	 * else { model.addAttribute("declarationDetails",
	 * declarationIndividualDetailsList);
	 * model.addAttribute("AppointmentCompletedList", AppointmentCompletedList); } }
	 * model.addAttribute("declarationDetails", declarationIndividualDetailsList);
	 * model.addAttribute("AppointmentCompletedList", AppointmentCompletedList);
	 * logger.info("URI>>>>>"+uri); return uri;
	 * 
	 * }
	 */
	
	

	
	

	

	
	
	

}
