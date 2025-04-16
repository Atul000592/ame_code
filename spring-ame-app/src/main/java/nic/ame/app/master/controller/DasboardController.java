package nic.ame.app.master.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.NotificationDto;
import nic.ame.app.admin.repository.RefRoleRepo;
import nic.ame.app.admin.service.NotificationService;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.IndividualAlertAndNotificationDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.Login;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.service.AlertAndNotificationService;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.master.util.RoleCodeNameUtil;



@Controller
public class DasboardController {
	
	

	@Autowired
	private AmeDeclarationRepository declarationRepository;
	
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired 
	private LoginRepository loginRepository;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;

	@Autowired
	private RefRoleRepo refRoleRepo;
	@Autowired
	private NotificationService notificationService;
	
	@Autowired 
    private  ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private AlertAndNotificationService alertAndNotificationService;
	
	@RequestMapping(value="/pendingRequest",method = RequestMethod.GET)
	public String  getPendingRequestPage( Model model,HttpSession httpSession) {
		
		System.out.println("DasboardController.getPendingRequestPage()");
		 String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		String forcepersonalId=(String) httpSession.getAttribute("forcepersonalId");
		AmeDeclarationIndividualModel individualModel=new AmeDeclarationIndividualModel();
		
	Optional<ForcePersonnelDto> optional=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
	System.out.println("DasboardController.getPendingRequestPage()>>>>>>>");
		if(forcepersonalId!=null) {
			if(optional.isEmpty()) {
			
			
			 ForcePersonnelDto forcePersonal= optional.get();
              if(forcePersonal.getDesignation().contains("DirM")) {
            	 List<AmeDeclarationIndividualModel> dataList= declarationRepository.findAll();
            	  
            	  model.addAttribute("dataList",dataList);
            	  return"bootstrap_medical_temp/pendingRequest";
            	  
               }else {
              	model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
            	  return "bootstrap_medical_temp/index";
              }
         
          }}
	     model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
   	  return "bootstrap_medical_temp/index";
		}
	
	
	@RequestMapping(value = { "/dashboard-user"},method = RequestMethod.GET)
	public String gettodeshboard(HttpSession httpSession,Model model) {
		 Integer notificationCount= (Integer) httpSession.getAttribute("notificationCount");
		 model.addAttribute("notificationCount", notificationCount);
		UserRoleDto userRoleDto=new UserRoleDto();
		String forcepersonalId=(String) httpSession.getAttribute("forcepersonalId");
		 String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		 int rCode;
		if(forcepersonalId!=null) {
			 int myRequestCount=amaDeclarationCountService.findMyDeclarationCount(forcepersonalId);
			 model.addAttribute("myRequestCount", myRequestCount);
			Optional<Login> optional=loginRepository.getByForcePersonalId(forcepersonalId);
			
			Optional<ForcePersonnelDto> forcePersonal=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
			ForcePersonnelDto forcePersonal2=forcePersonal.get();
			Set<String> userRoles= userRoleService.getRoleByforcePersonalId(forcepersonalId);
			 String roleCode;
			 List<UserRoleDto> roleDtosList = new ArrayList<>();
			
			 Iterator<String> roleIterator = userRoles.iterator();
			  while(roleIterator.hasNext()) {
					UserRoleDto userRoleDto1 =new UserRoleDto();
				    userRoleDto1.setForceNo(forcePersonal2.getForceNo());
					userRoleDto1.setForcePersonalId(forcePersonal2.getForcePersonalId());
					userRoleDto1.setUnit(forcePersonal2.getUnit());
					roleCode=roleIterator.next();
					userRoleDto1.setRoleCode(RoleCodeNameUtil.getRoleNameByRoleCode(roleCode));
				    rCode=Integer.parseInt(refRoleRepo.getRoleIdByRoleCode(roleCode.trim()));
		            userRoleDto1.setrCode(rCode);
					userRoleDto1.setUri("role-map-to-"+roleCode.trim()+"-dashboard");
					roleDtosList.add(userRoleDto1);
					
				  }
				  
				 model.addAttribute("roleDtosList",roleDtosList);
			
			
		
			
			if(!userRoles.isEmpty()||userRoles.size()==0) {
				model.addAttribute("userRoleDto",userRoleDto);
				model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
				//===============================================Notification Scrolling RK==========================================================
				Integer forceNo=forcePersonal2.getForceNo();
				List<NotificationDto>NotificationDtoList= notificationService.getAllNotifictionForScrollView(forceNo);
				model.addAttribute("notificationList",NotificationDtoList);
//				session.setAttribute("notificationList", NotificationDtoList);
		//==================================================================================================================================		
				List<IndividualAlertAndNotificationDto> listOfAlertsAndNotificationsByForcePersonnelId = alertAndNotificationService.alertAndNotifications(forcepersonalId);
				 
				 ForcePersonnelDto forcePersonnelDetails = forcePersonnelService
							.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId).get();

					notificationCount = listOfAlertsAndNotificationsByForcePersonnelId.size();

					model.addAttribute("listOfAlertsAndNotificationsByForcePersonnelId",
							listOfAlertsAndNotificationsByForcePersonnelId);
					model.addAttribute("notificationCount", notificationCount);
					model.addAttribute("loginUserDetails", forcePersonnelDetails);
					//model.addAttribute("candidateForcePersonalId", userName);
					model.addAttribute("candidateDetails", forcePersonnelDetails);
				
				return "UserMenu/dashboard";
				
			}else {
				model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			   	  return "bootstrap_medical_temp/index";
				
			}
			
			

			
		}
		
		model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
	   	  return "bootstrap_medical_temp/index";
		
		
}


	@RequestMapping("/profile-page")
	public String getToProfilePage() {
     
		return"bootstrap_medical_temp/profile-page";
	}
	
	
	@RequestMapping("health-card")
	public String getToHealthCard() {
		return"health-form/health-card";
	}

}
