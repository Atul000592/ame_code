package nic.ame.app.po.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.model.AmeApprovalProcess;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeApprovalProcessRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.constant.CommonConstant;

@Controller
public class FunctionalControllerPO {
	
	

	
	Logger logger=LoggerFactory.getLogger(FunctionalControllerPO.class);
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;
	
	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	private AmeApprovalProcessRepository ameApprovalProcessRepository;
	
	@RequestMapping("/form-index-page-po")
	public String getToIndexPagePo() {
	
		return"form-index-page-po";
	}

	
	
	
	
	@RequestMapping(value = "/pending-for-appointment-ame-po",method = RequestMethod.GET)
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
		return "po-template/list-of-board-po";
	}
	
	
	
	//================================role map to ama dashboard==========================//
	
	@PostMapping("/role-map-to-ame-po-dashboard")
	public String getToAmaDashboard(
			@RequestParam("forcePersonalId") String forcePersonalId,
			@RequestParam("boardId") String boardId,
			@RequestParam("rCode") String rCode,
			Model model,
			HttpServletRequest httpServletRequest) {
		
		HttpSession session=httpServletRequest.getSession(false);
		if(session==null) {
			model.addAttribute("errorMsg", "No valid details found in the database please contact admin...");
			return "bootstrap_medical_temp/index";
			
		}
		
		int checkupListPending;
		int checkupListCompleted;
		Integer pendingAmeFormCheckPending;
		Integer fileuploadPending;
		
		MedicalBoardDetailDto boardDetailDto=new MedicalBoardDetailDto();
		session.setAttribute("boardId", boardId.trim());
		session.setAttribute("rCodeMedical",Integer.parseInt(rCode.trim()));
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+rCode);
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
		
	     
		List<UserRoleDto> userRoleDtos= (List<UserRoleDto>) context.getAttribute(forcePersonalId);
        model.addAttribute("roleDtosList", userRoleDtos);
		
		
		
		session.setAttribute("forcepersonalId", forcePersonalId);
		session.setAttribute("roleName",roleName.trim().toUpperCase());
		model.addAttribute("pendingcountama", pendingCountValue);
		model.addAttribute("appointmentcompletedama",appointmentCompletedValue);
		model.addAttribute("totalCompletedAma", totalCompletedAma);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
		model.addAttribute("boardId",boardId);
		model.addAttribute("boardCount",medicalBoardIndividualMappingRepo.boardCount
				(forcePersonalId,Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG))));
          
		
		return"po-template/po-ame-dash-board";
	
	}
	
	
	
	@PostMapping("/ame-po-dashboard")
	public String getToAmePoDashboard(Model model,
			HttpServletRequest httpServletRequest) {
		
		HttpSession session=httpServletRequest.getSession(false);
		if(session==null) {
			model.addAttribute("errorMsg", "No valid details found in the database please contact admin...");
			return "bootstrap_medical_temp/index";
			
		}
		String forcePersonalId = (String) session.getAttribute("forcepersonalId");
		int checkupListPending;
		int checkupListCompleted;
		Integer pendingAmeFormCheckPending;
		Integer fileuploadPending;
		
		MedicalBoardDetailDto boardDetailDto=new MedicalBoardDetailDto();
	     String boardId=	(String)session.getAttribute("boardId");
		
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
		
	     
		List<UserRoleDto> userRoleDtos= (List<UserRoleDto>) context.getAttribute(forcePersonalId);
        model.addAttribute("roleDtosList", userRoleDtos);
		
		
		
		
		session.setAttribute("roleName",roleName.trim().toUpperCase());
		model.addAttribute("pendingcountama", pendingCountValue);
		model.addAttribute("appointmentcompletedama",appointmentCompletedValue);
		model.addAttribute("totalCompletedAma", totalCompletedAma);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
		model.addAttribute("boardId",boardId);
		model.addAttribute("boardCount",medicalBoardIndividualMappingRepo.boardCount
				(forcePersonalId,Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG))));
          
		
		return"po-template/po-ame-dash-board";
	
	}
	
}
