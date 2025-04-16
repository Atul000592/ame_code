package nic.ame.app.master.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RefRoleMedicalRepo;
import nic.ame.app.admin.repository.RefRoleRepo;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.ama.dto.DealingHandDto;
import nic.ame.app.dealinghand.dto.RequestDealingHandDto;
import nic.ame.app.dealinghand.service.DealingHandService;
import nic.ame.app.master.dto.AmeResultStatusDto;
import nic.ame.app.master.dto.IndividuaUnitMappedToControllingAmeAppointment;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.medical.dto.MedicalBoardMemberRoleDto;
import nic.ame.app.master.medical.model.AmeReviewCandidatesList;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.MapIndividualAndUnitToReporting;
import nic.ame.app.master.model.TTDealingHand;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.AmeReviewCandidatesListRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.IndividualUnitMappedToControllingRepository;
import nic.ame.app.master.repository.MapIndividualAndUnitToReportingRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.repository.TTDealingHandRepo;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.AmeAppointmentService;
import nic.ame.app.master.service.AmeResultStatusService;
import nic.ame.app.master.service.BoardMemberRoleService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.constant.CommonConstant;

@Controller
public class MasterDashboardController {

	Logger logger=LoggerFactory.getLogger(MasterDashboardController.class);
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;

	@Autowired
	private MedicalBoardRepo medicalBoardRepo;

	@Autowired
	private MedicalBoardIndividualMappingRepo  medicalBoardIndividualMappingRepo;
	
     @Autowired
     private RefForceService refForceService; 
	
	@Autowired
	private TTAppointmentAmeRepo ttAppointmentAmeRepo;
	
	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;
	
    @Autowired
    private BoardMemberRoleService boardMemberRoleService;
    
    @Autowired
    private DealingHandService dealingHandService;
    
    @Autowired
    private RefRoleRepo refRoleRepo;
    

    
    @Autowired
    private AmeParametersRepository ameParametersRepository;
    
    
    @Autowired
    private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
    
 
    
    @Autowired
    private AmeAppointmentService ameAppointmentService;
    
    @Autowired
    private AmeResultStatusService ameResultStatusService;
    
    @Autowired
    private ServletContext context;
    
    @Autowired
    private MapIndividualAndUnitToReportingRepository mapIndividualAndUnitToReportingRepository;
    


	
	//================================role map to ama dashboard==========================//
	
	@PostMapping("/role-map-to-ama-dashboard")
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
		Optional<ForcePersonnel> optional= forcePersonnelRepository.getByForcePersonnelId(forcePersonalId);
		int forceNo=optional.get().getForceNo();
		String unit=optional.get().getUnit();
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
          
		
		return"medical-sub-ordinate/dashboard-ma";
	
	}
	
	
     //===========================dash board mapping to the dealing hand================================//
	
	@RequestMapping(path =  "role-map-to-dhm-dashboard",method = RequestMethod.POST)
	public String mapToSubordinateRole(@RequestParam("forcePersonalId") String forcepersonalId,
			@RequestParam("unit") String unit,
			@RequestParam("forceNo")  Integer forceNo,
			@RequestParam("rCode") String rCode,
			Model model,
			HttpSession httpSession) {
		
		String roleName="dealinghand";
		httpSession.setAttribute("rCodeMedical",Integer.parseInt(rCode.trim()));
		RequestDealingHandDto dealingHandDto=new RequestDealingHandDto();
		dealingHandDto.setForceNo(forceNo);
		dealingHandDto.setForcePersonalId(forcepersonalId);
		dealingHandDto.setRoleName(roleName);
		dealingHandDto.setUnit(unit);
		
		List<DealingHandDto> dealingHands=dealingHandService.getDealingHandDetailsByForcePersonalId(forcepersonalId, CommonConstant.USER_ROLE_STATUS_ACTIVE);
	
		Integer count=amaDeclarationCountService.findDataForDealingHandCount(unit, forceNo);
		String dealinghandPendingListCount;
		if(count==null) {
			dealinghandPendingListCount="0";
			}
		else {
			dealinghandPendingListCount=String.valueOf(count);
		}
		httpSession.setAttribute("dealinghandPendingListCount", dealinghandPendingListCount);
		            
		//httpSession.setAttribute("dealingHandDto", dealingHandDto);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
		model.addAttribute("dealinghandPendingListCount",dealinghandPendingListCount);
		model.addAttribute("dealingHands",dealingHands);
		
		return"dealinghand/dashboard-dh-main";
	}
	
	@RequestMapping(path = "/role-map-to-ch-dashboard",method = RequestMethod.POST)
	public String mapToControllerRole(Model model,HttpServletRequest httpServletRequest) {
		HttpSession httpSession=httpServletRequest.getSession(false);
		
		if(httpSession==null) {
			
		}
		
		String forcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
		
		Optional<ForcePersonnel> forcePersonal=forcePersonnelRepository.getByForcePersonnelId(forcePersonalId);
		
		if(forcePersonal.isEmpty()) {
			
		}
		
		//int forceNo=forcePersonal.get().getForce_no();
		 //List<ForcePersonal> forcePersonalListNGO = forcePersonalRepository.getForcePersonalListNGONew(forcePersonal.get().getForce_no(),forcePersonal.get().getUnit().trim());
		 List<ForcePersonnel> forcePersonalListNGO= 
				 forcePersonnelRepository.getIntialNgoRequestListToControlling
				 ( forcePersonal.get().getForceNo(),forcePersonal.get().getUnit().trim());
		 model.addAttribute("dataList", forcePersonalListNGO);
		 
		 return"medical-controlling/dashboard-ch";
		
	}
	
	
	@RequestMapping(path="/role-map-to-bm-dashboard",method = RequestMethod.POST)
	public String mapToBoardMemberDashBoard(Model model,
			HttpServletRequest httpServletRequest,
			@RequestParam(name="forcePersonalId",required = false) String forcepersonalId,
			@RequestParam(name="unit",required = false) String unit,
			@RequestParam(name="forceNo",required = false)  Integer forceNo ,HttpSession httpSession) {
		
		String loginForcePersonnel;
		
		if(forcepersonalId==null) {
			 String loginForcePersonnelId=(String) httpSession.getAttribute("forcepersonalId");

			loginForcePersonnel=loginForcePersonnelId;
		}else {
			loginForcePersonnel=forcepersonalId;
		}
		
		List<MedicalBoardMemberRoleDto> boardMembersRoleListForNGO = boardMemberRoleService
				.getBoardMemberRoleForNGO(loginForcePersonnel, httpSession);
			 	List<MedicalBoardMemberRoleDto> boardMembersRoleListGO = boardMemberRoleService
				.getBoardMemberRoleForGO(loginForcePersonnel, httpSession);
		 
		List<UserRoleDto> userRoleDtos = (List<UserRoleDto>) httpSession.getAttribute("roleDtosList");
	    

		model.addAttribute("roleDtosList", userRoleDtos);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(loginForcePersonnel));
		model.addAttribute("boardMemberRoleDtos", boardMembersRoleListForNGO);
	    model.addAttribute("boardMembersRoleListGO", boardMembersRoleListGO);
		return "UserMenu/board-member-dashboard";
		
	}
	
	@RequestMapping(path = "/role-map-to-po-dashboard",method = RequestMethod.POST)
	public String getToPoDashBoard(
			@RequestParam("forcePersonalId") String forcePersonalId,
			@RequestParam("boardId") String boardId,
			@RequestParam("rCode") String rCode,
			Model model,HttpServletRequest httpServletRequest) {
		
		HttpSession session=httpServletRequest.getSession(false);
		if(session==null) {
			model.addAttribute("errorMsg", "No valid details found in the database please contact admin...");
			return "bootstrap_medical_temp/index";
			
		}
		session.setAttribute("boardId",boardId);
		
		 String roleName="po"; 
		Integer declarationCount=ttAppointmentAmeRepo.getAppointmentCountByBoardId(boardId);
		String pendingCountValue;
		if(declarationCount==null) {
			pendingCountValue="0";
		}else {
			pendingCountValue=String.valueOf(declarationCount);
		}
        
		session.setAttribute("forcepersonalId", forcePersonalId);
		session.setAttribute("roleName",roleName.trim().toUpperCase());
		session.setAttribute("rCode", rCode);
		model.addAttribute("declarationCountPo", pendingCountValue);
		model.addAttribute("boardId", boardId);
		model.addAttribute("forcepersonalId", forcePersonalId);
		model.addAttribute("rCode", rCode);
	
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
           return"po-template/po-dashboard";
	
	}
	
	@RequestMapping(path = "/role-map-to-AA-dashboard",method = RequestMethod.POST)
	public String getToAADashBoard(
			@RequestParam("forcePersonalId") String forcePersonalId,
			@RequestParam("boardId") String boardId,
			@RequestParam("rCode") String rCode,
			Model model,HttpServletRequest httpServletRequest) {
		
		HttpSession session=httpServletRequest.getSession(false);
		if(session==null) {
			model.addAttribute("errorMsg", "No valid details found in the database please contact admin...");
			return "bootstrap_medical_temp/index";
			
		}
		session.setAttribute("boardId",boardId);
		
		 String roleName="aa"; 
		Integer declarationCount=ttAppointmentAmeRepo.getAppointmentCountByBoardId(boardId);
		String pendingCountValue;
		if(declarationCount==null) {
			pendingCountValue="0";
		}else {
			pendingCountValue=String.valueOf(declarationCount);
		}
        
		session.setAttribute("forcepersonalId", forcePersonalId);
		session.setAttribute("roleName",roleName.trim().toUpperCase());
		session.setAttribute("rCode", rCode);
		model.addAttribute("declarationCountPo", pendingCountValue);
		model.addAttribute("boardId", boardId);
		model.addAttribute("forcepersonalId", forcePersonalId);
		model.addAttribute("rCode", rCode);
	
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
           return"aa-template/aa-dashboard";
	
	}
	@RequestMapping(path = "/role-map-to-spu-dashboard",method = RequestMethod.GET)
	public String mapToSuperUserManagementDashBoard(Model model,
			HttpServletRequest httpServletRequest) {
		
		 String forcePersonalIdLogin = null;
		 String unit;
		 int forceNo = 0;
		

	
		  
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
	    if (inputFlashMap != null) {
	    	forcePersonalIdLogin =  (String) inputFlashMap.get("forcepersonalId");
	    	unit =(String)inputFlashMap.get("unit");
	    	forceNo=(int) inputFlashMap.get("forceNumber");
	    } 
		
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));
		model.addAttribute("loginForcePersonalId", forcePersonalIdLogin);
		model.addAttribute("userType","SUPER USER");
		HttpSession httpSession=httpServletRequest.getSession();
		
		httpSession.setAttribute("LoginForcePersonalId", forcePersonalIdLogin);
	
		//httpSession.setAttribute("loginForceName", refForceService.getForceNameByForceId(forceNo));
		httpSession.setAttribute("userType", "SUPER USER");
		//model.addAttribute("loginForceName",refForceService.getForceNameByForceId(forceNo));
		
		int activeRoleCount=refRoleRepo.activeRoleCount(forcePersonalIdLogin);
	    int InactiveRoleCount=refRoleRepo.inActiveRoleCount(forcePersonalIdLogin);
	    int totalRoleCount=activeRoleCount+InactiveRoleCount;
	     model.addAttribute("totalRoleCount", totalRoleCount);
	     model.addAttribute("activeRoleCount", activeRoleCount);
	     model.addAttribute("InActiveRoleCount", InactiveRoleCount);
	
		return"user-management-template/super-user/dashboard-user-mg-spu";
		
	}
	
	
	@RequestMapping(path="/mg-user-dash-board",method = RequestMethod.GET)
	public String getToUserDashBoard(Model model,HttpSession httpSession) {
	   String loginForcePersonal=(String) httpSession.getAttribute("LoginForcePersonalId");
	   model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonal.trim()));
		model.addAttribute("loginForcePersonalId", loginForcePersonal);
		model.addAttribute("userType","SUPER USER"); 
		String loginForceName=(String) httpSession.getAttribute("loginForceName");
	
		  model.addAttribute("loginForceName", loginForceName);
		int activeRoleCount=refRoleRepo.activeRoleCount(loginForcePersonal);
	    int InactiveRoleCount=refRoleRepo.inActiveRoleCount(loginForcePersonal);
	    int totalRoleCount=activeRoleCount+InactiveRoleCount;
	     model.addAttribute("totalRoleCount", totalRoleCount);
	     model.addAttribute("activeRoleCount", activeRoleCount);
	     model.addAttribute("InActiveRoleCount", InactiveRoleCount);
	
	   
		return"user-management-template/super-user/dashboard-user-mg-spu";
	}
	
	
	
	// anand for forceAdmin user management
	
	@RequestMapping(value = {"/role-map-to-fad-dashboard"},method = RequestMethod.POST)
	public String mapToForceAdminManagementDashBoard(Model model,
			HttpServletRequest httpServletRequest,
			@RequestParam("forcePersonalId") String forcepersonalId,
			@RequestParam("unit") String unit,
			@RequestParam("forceNo")  Integer forceNo,
			@RequestParam("rCode") int rCode) {
		
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
		model.addAttribute("loginForcePersonalId", forcepersonalId);
		model.addAttribute("userType","FORCE-ADMIN");

		HttpSession httpSession=httpServletRequest.getSession();
        httpSession.setAttribute("rCode", rCode);		
		httpSession.setAttribute("LoginForcePersonalId", forcepersonalId);
		httpSession.setAttribute("loginForceName", refForceService.getForceNameByForceId(forceNo));
		httpSession.setAttribute("userType", "FORCE-ADMIN");
		model.addAttribute("loginForceName",refForceService.getForceNameByForceId(forceNo));
		  int activeRoleCount=refRoleRepo.activeRoleCount(forcepersonalId);
	  	    int InactiveRoleCount=refRoleRepo.inActiveRoleCount(forcepersonalId);
	  	    int totalRoleCount=activeRoleCount+InactiveRoleCount;
	  	     model.addAttribute("totalRoleCount", totalRoleCount);
	  	     model.addAttribute("activeRoleCount", activeRoleCount);
	  	     model.addAttribute("InActiveRoleCount", InactiveRoleCount);
	        model.addAttribute("rCode", rCode);
	  	     return"user-management-template/force-admin-user/dashboard-fad";
		
	}
	
	
	@PostMapping(path = "/role-map-to-capfad-dashboard")
	public String mapToCapfAdminManagementDashBoard(Model model,
			HttpServletRequest httpServletRequest,
			@RequestParam("forcePersonalId") String forcepersonalId,
			@RequestParam("unit") String unit,
			@RequestParam("forceNo")  Integer forceNo,
			@RequestParam("rCode") String rCode) 
	{
		
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
		model.addAttribute("loginForcePersonalId", forcepersonalId);
		model.addAttribute("userType","CAPF ADMIN");
		HttpSession httpSession=httpServletRequest.getSession();
	
		httpSession.setAttribute("LoginForcePersonalId", forcepersonalId);
		httpSession.setAttribute("loginForceName", refForceService.getForceNameByForceId(forceNo));
		model.addAttribute("loginForceName",refForceService.getForceNameByForceId(forceNo));
		httpSession.setAttribute("userType", "CAPF ADMIN");
		
		httpSession.setAttribute("rCode",Integer.parseInt(rCode));
    
		int activeRoleCount=refRoleRepo.activeRoleCount(forcepersonalId);
  	    int InactiveRoleCount=refRoleRepo.inActiveRoleCount(forcepersonalId);
  	    int totalRoleCount=activeRoleCount+InactiveRoleCount;
  	    
  	    model.addAttribute("totalRoleCount", totalRoleCount);
  	    model.addAttribute("activeRoleCount", activeRoleCount);
  	    model.addAttribute("InActiveRoleCount", InactiveRoleCount);
		
		return"user-management-template/force-admin-user/dashboard-fad";
		
	}
	
	@RequestMapping(path="/mg-FadorCAPF-dash-board",method = RequestMethod.GET)
	public String getToFadorCAPFUserDashBoard(Model model,HttpSession httpSession) {
	   String loginForcePersonal=(String) httpSession.getAttribute("LoginForcePersonalId");
	   model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonal.trim()));
		model.addAttribute("loginForcePersonalId", loginForcePersonal);
		   String loginForceName=(String) httpSession.getAttribute("loginForceName");
			 String userType=(String)httpSession.getAttribute("userType");
			  model.addAttribute("loginForceName", loginForceName);
			  model.addAttribute("userType", userType);
			  int activeRoleCount=refRoleRepo.activeRoleCount(loginForcePersonal);
		  	    int InactiveRoleCount=refRoleRepo.inActiveRoleCount(loginForcePersonal);
		  	    int totalRoleCount=activeRoleCount+InactiveRoleCount;
		  	     model.addAttribute("totalRoleCount", totalRoleCount);
		  	     model.addAttribute("activeRoleCount", activeRoleCount);
		  	     model.addAttribute("InActiveRoleCount", InactiveRoleCount);
		  	     int rCode=(int) httpSession.getAttribute("rCode");
		  	   model.addAttribute("rCode",rCode);
				
		return"user-management-template/force-admin-user/dashboard-fad";
	}
	
	
	
	//==========================Dash Board to unit-admin and Reporting officer==================================//
	

	@RequestMapping(value = {"role-map-to-uad-dashboard"},method = RequestMethod.POST)
	public String mapToUadAdminManagementDashBoard(Model model,
			HttpServletRequest httpServletRequest,
			@RequestParam("forcePersonalId") String forcepersonalId,
			@RequestParam("unit") String unit,
			@RequestParam("forceNo")  Integer forceNo,
			@RequestParam("rCode") int rCode) {
		
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
		model.addAttribute("loginForcePersonalId", forcepersonalId);
		model.addAttribute("userType","UNIT ADMIN");
		HttpSession httpSession=httpServletRequest.getSession();
	     logger.info("rCode>>>>>>"+rCode);
		httpSession.setAttribute("LoginForcePersonalId", forcepersonalId);
		httpSession.setAttribute("loginForceName", refForceService.getForceNameByForceId(forceNo));
		httpSession.setAttribute("rCode", rCode);		
		
	
		model.addAttribute("loginForceName",refForceService.getForceNameByForceId(forceNo));
		
		httpSession.setAttribute("userType", "UNIT ADMIN");
	    int activeRoleCount=refRoleRepo.activeRoleCount(forcepersonalId);
  	    int InactiveRoleCount=refRoleRepo.inActiveRoleCount(forcepersonalId);
  	    int totalRoleCount=activeRoleCount+InactiveRoleCount;
  	     model.addAttribute("totalRoleCount", totalRoleCount);
  	     model.addAttribute("activeRoleCount", activeRoleCount);
  	     model.addAttribute("InActiveRoleCount", InactiveRoleCount);
  	     model.addAttribute("rCode",rCode);
  	     
  	   
		
		return"user-management-template/force-admin-user/dashboard-fad";
		
	}
	
	
	
	
	//-----------------------------------------------Controlling Dashboard-------------------------------------------------------//
	
		@RequestMapping(value = {"role-map-to-cho-dashboard"},method = RequestMethod.POST)
		public String roleMapToControllingDashboard(Model model,
				HttpServletRequest httpServletRequest,
				@RequestParam("forcePersonalId") String forcepersonalId,
				@RequestParam("unit") String unit,
				@RequestParam("forceNo")  Integer forceNo,
				@RequestParam("rCode") int rCode) {
			
			model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
			model.addAttribute("loginForcePersonalId", forcepersonalId);
			model.addAttribute("userType","Controlling Officer");
			HttpSession httpSession=httpServletRequest.getSession();
		     logger.info("rCode>>>>>>>>>>"+rCode);
			int count=mapIndividualAndUnitToReportingRepository.findByReportingForcePersionIdIdCandidateCount(forcepersonalId);
			int ameCompleteCount=mapIndividualAndUnitToReportingRepository.findAmeCompletedCount(forcepersonalId);
			httpSession.setAttribute("LoginForcePersonalId", forcepersonalId);
			httpSession.setAttribute("loginForceName", refForceService.getForceNameByForceId(forceNo));
			httpSession.setAttribute("rCode", rCode);		
			httpSession.setAttribute("userType", "Controlling Officer");
			httpSession.setAttribute("forceNo", forceNo);	
			httpSession.setAttribute("unit", unit);
			  model.addAttribute("coCount",count);
		  	     model.addAttribute("ameCompleteCount",ameCompleteCount);
		  	     model.addAttribute("ameUnderProcess",count- ameCompleteCount);
		  	     
			return "medical-controlling/role-map-to-controlling-dashboard";
	}
		
		@GetMapping({"ame-appointment-status-check"})
		public String ameAppointmentStatus(Model model,
				HttpServletRequest httpServletRequest) 
		{
			HttpSession httpSession=httpServletRequest.getSession();
			String forcePersonnelId=(String) httpSession.getAttribute("LoginForcePersonalId");
			int rCode=(Integer)httpSession.getAttribute("rCode");
			int forceNo=(Integer)httpSession.getAttribute("forceNo");
			String unit=(String)httpSession.getAttribute("unit");
	        String loginForceName=(String)httpSession.getAttribute("loginForceName");
			
			model.addAttribute("loginUserDetails",loginUserDetails.getLoginUserDetails(forcePersonnelId) );
			model.addAttribute("loginForcePersonalId",forcePersonnelId);
			model.addAttribute("userType","CONTROLLING");
			model.addAttribute("forceNo",forceNo);
			model.addAttribute("loginForceName",refForceService.getForceNameByForceId(forceNo));
			model.addAttribute("rCode",rCode);
			model.addAttribute("unit",unit);
			
			List<IndividuaUnitMappedToControllingAmeAppointment> allWithCompletedAppointment=
					ameAppointmentService.findAllWithCompletedAppointment(forcePersonnelId);
			//System.out.println(allWithCompletedAppointment);
			model.addAttribute("allWithCompletedAppointment",allWithCompletedAppointment);
			
			
			return "medical-controlling/ame-appointment-status";
			
		}
	
		
//---------------------------------------------------controlling AME STATUS----------------------------------------------------//	
		@GetMapping({"ame-result-status-check"})
		public String ameResultStatus(Model model,HttpServletRequest httpServletRequest) 
		{
			HttpSession httpSession=httpServletRequest.getSession();
			String forcePersonnelId=(String) httpSession.getAttribute("LoginForcePersonalId");
			int rCode=(Integer)httpSession.getAttribute("rCode");
			int forceNo=(Integer)httpSession.getAttribute("forceNo");
			String unit=(String)httpSession.getAttribute("unit");
	        String loginForceName=(String)httpSession.getAttribute("loginForceName");
			
	        List<AmeResultStatusDto> ameResultStatusList = ameResultStatusService.getAmeResultStatus(forcePersonnelId);
	        
			model.addAttribute("loginUserDetails",loginUserDetails.getLoginUserDetails(forcePersonnelId) );
			model.addAttribute("loginForcePersonalId",forcePersonnelId);
			model.addAttribute("userType","CONTROLLING");
			model.addAttribute("forceNo",forceNo);
			model.addAttribute("loginForceName",refForceService.getForceNameByForceId(forceNo));
			model.addAttribute("rCode",rCode);
			model.addAttribute("unit",unit);
			model.addAttribute("ameResultStatusList",ameResultStatusList);
			
			
		   
			
			return "medical-controlling/ame-result-status";
			
		}
		
		
		
		
		
	
}
