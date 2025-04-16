package nic.ame.app.user.management.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.RefRole;
import nic.ame.app.admin.repository.ForceRepo;

import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.medical.service.RoleService;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.constant.CommonConstant;

@Controller
public class UserMamagementDashBoardController {

	Logger logger = LogManager.getLogger(UserMamagementDashBoardController.class);

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private ForceRepo refForcerepo;

	@Autowired
	private RoleService roleService;
	
	

	@GetMapping("/assign-new-role-to-user")
	public String assignNewRoleToUser(Model model, HttpServletRequest httpServletRequest) {
		
		
		HttpSession httpSession = httpServletRequest.getSession();
	
		if (httpSession.equals(null)) {
			logger.info("invalid session");
		}
		
		if (httpSession.equals(null)) {
			logger.info("invalid session");
		}
         String message = null;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
	    if (inputFlashMap != null) {
	    	message =  (String) inputFlashMap.get("message");
	    	
	    } 
		
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));

		List<UserRoleDto> userRoleDtos = userRoleService.getListOfUserWithRoles(loginForcePersonalId);
		model.addAttribute("userRoleDtosList", userRoleDtos);

		int rCode = (int) httpSession.getAttribute("rCode");
		List<RefRole> MasterRoleList = roleService.getMasterRoleList(loginForcePersonalId, rCode);
		

		List<Force> forceList = refForcerepo.findAll();
		
		model.addAttribute("forceList", forceList);
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		model.addAttribute("message",message);
		return "user-management-template/super-user/assign-new-role-to-user";
	}

	// cafadmin and force admin

	@GetMapping("assign-new-role-to-FadorCAPF")
	public String assignNewRoleFadorCAPF(Model model, HttpServletRequest httpServletRequest) {

		HttpSession httpSession = httpServletRequest.getSession();

		if (httpSession.equals(null)) {
			logger.info("invalid session");
		}
         String message = null;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
	    if (inputFlashMap != null) {
	    	message =  (String) inputFlashMap.get("message");
	    	
	    } 
		
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
         ForcePersonnelDto loginForcePersonnel=loginUserDetails.getLoginUserDetails(loginForcePersonalId);
		model.addAttribute("loginUserDetails",loginForcePersonnel );
				int rCode = (int) httpSession.getAttribute("rCode");

		List<RefRole> MasterRoleList = roleService.getMasterRoleList(loginForcePersonalId, rCode);
		List<Force> forceList = refForcerepo.findAll();

		if(rCode==9||rCode==7) {
          model.addAttribute("forceList", forceList);
		   }
		else {
			forceList=forceList.stream().filter(s->s.getForceNo()==loginForcePersonnel.getForceNo()).collect(Collectors.toList());   
			model.addAttribute("forceList", forceList);
		}
		String loginForceName = (String) httpSession.getAttribute("loginForceName");

		String userType = (String) httpSession.getAttribute("userType");
	  		model.addAttribute("rCode",rCode);
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		model.addAttribute("message",message);
		
		return "user-management-template/force-admin-user/assign-new-role-to-FadorCAPF";
	}

	@GetMapping("get-controlling-officer-list")
	public String getControllingOfficerList(HttpSession httpSession,Model model) {
		
		
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
        List<UserRoleDto> controllingUserRoleLis = userRoleService.getForcePersonalByControllingRole(loginForcePersonalId);
		model.addAttribute("controllingList", controllingUserRoleLis);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		return "user-management-template/force-admin-user/controlling-officer-list";
	}
	
	
}
