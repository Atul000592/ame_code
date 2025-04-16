package nic.ame.app.master.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.NotificationDto;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.RefRoleRepo;
import nic.ame.app.admin.service.NotificationService;
import nic.ame.app.esign.EsignConfigurationUtil;
import nic.ame.app.esign.StringConstants;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.IndividualAlertAndNotificationDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.exceptionHandler.LoginAttemptFailedException;
import nic.ame.app.master.model.AlertAndNotification;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.Login;

import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AlertAndNotificationService;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginLogoutService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.RoleCodeNameUtil;

@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);


	@Autowired
	private RefRoleRepo refRoleRepo;
	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private ForcePersonnelService forcePersonnelService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;

	@Autowired
	private RankRepo rankRepo;

	@Autowired
	private ForceRepo refForceRepo;

	@Autowired
	private NotificationService notificationService;

     @Autowired
	 private  LoginLogoutService loginLogoutService;
     
     private String userName;
     @Autowired
     private AlertAndNotificationService alertAndNotificationService;
     
     @Autowired
     private ServletContext context;
     
    


		@PostMapping(value = { "/ame" })
		public String getToLoginPage(Model model, HttpSession session, Principal principal, HttpServletRequest request,
				CsrfToken csrfToken, RedirectAttributes redirectAttributes, @RequestParam("forceName") String ForceName,
				@RequestParam String captcha) {
			int notificationCount = 0;
			//String loggedInUserForcePersonnelId = (String) session.getAttribute("forcepersonalId");
			this.userName = principal.getName();
			// System.out.println("UserName "+userName);
			Login login = loginUserDetails.getUserNameByUserName(userName);
			//List<IndividualAlertAndNotificationDto> listOfAlertsAndNotificationsByForcePersonnelId = alertAndNotificationService.findListOfAlertsAndNotificationsByForcePersonnelId(login.getForcePersonalId());
			
			List<IndividualAlertAndNotificationDto> alertAndNotifications= alertAndNotificationService.alertAndNotifications(login.getForcePersonalId());
			//session.setAttribute("listOfAlertsAndNotificationsByForcePersonnelId", listOfAlertsAndNotificationsByForcePersonnelId);
			notificationCount = alertAndNotifications.size();
			session.setAttribute("notificationCount", notificationCount);
			model.addAttribute("notificationCount", notificationCount);

			ForcePersonnelDto forcePersonnelDetails = forcePersonnelService
					.getForcePersonnelDetailsByForcePersonnelId(login.getForcePersonalId()).get();

			notificationCount = alertAndNotifications.size();

			model.addAttribute("listOfAlertsAndNotificationsByForcePersonnelId",
					alertAndNotifications);
			model.addAttribute("notificationCount", notificationCount);
			model.addAttribute("loginUserDetails", forcePersonnelDetails);
			model.addAttribute("candidateForcePersonalId", userName);
			model.addAttribute("candidateDetails", forcePersonnelDetails);
			// System.out.println("DWaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			// logger.info("secret code:-"+boardIdGenerationService.boardDynamicId("BB",1));

		String hexKey = (String) session.getAttribute("hexKey");
		String ivHex = (String) session.getAttribute("ivHex");

		session.removeAttribute("hexKey");
		session.removeAttribute("ivHex");
		session = request.getSession(false);
		String storedCaptcha = (String) session.getAttribute("captcha");
		if (storedCaptcha != null && storedCaptcha.equalsIgnoreCase(captcha)) {
			session.removeAttribute("captcha");
			logger.info("Captcha Removed SuccessFully.....After login");

			if (!login.getForcePersonalId().equals(null)) {

				Optional<ForcePersonnelDto> optional = forcePersonnelService
						.getForcePersonnelDetailsByForcePersonnelId(login.getForcePersonalId());

				if (optional.isPresent()) {

					ForcePersonnelDto forcePersonal = optional.get();

					if (forcePersonal.getForceNo() != Integer.parseInt(ForceName)) {

						redirectAttributes.addFlashAttribute("errorMsg", "Invalid Force Name........!");

						return "redirect:/initial-login";

					} else {

						if (login.getFirstLoginFlag().equals("Y")) {
							redirectAttributes.addFlashAttribute("loginForcePersonalId", login.getForcePersonalId());
							session.setAttribute("loginForcePersonalId", login.getForcePersonalId());
							return "redirect:/change-password";
						} else {
							redirectAttributes.addFlashAttribute("loginForcePersonalId", login.getForcePersonalId());
							session.setAttribute("loginForcePersonalId", login.getForcePersonalId());
							return "redirect:/ame-user-dash-board";
						}
					}

				}
			} else {

				redirectAttributes.addFlashAttribute("errorMsg", "Force Personal Datails not Present...!");

				return "redirect:/initial-login";
			}

		}

		logger.info("Invalid Captcha.........!" + captcha);
		redirectAttributes.addFlashAttribute("errorMsg", "Invalid Captcha");

		return "redirect:/initial-login";

	}

	@GetMapping(value="ame-user-dash-board")
	public String ameUserDashBoard(HttpServletRequest httpServletRequest, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {

		String loginForcePersonal = null;
		
	
		Integer notificationCount= (Integer) session.getAttribute("notificationCount");
		 model.addAttribute("notificationCount", notificationCount);
		 
		
		 
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
		if (inputFlashMap != null) {
			loginForcePersonal = (String) inputFlashMap.get("loginForcePersonalId");

		}

		HttpSessionSecurityContextRepository contextRepository = new HttpSessionSecurityContextRepository();
		Authentication authenticateAction = contextRepository.loadDeferredContext(httpServletRequest).get()
				.getAuthentication();

		if (authenticateAction != null) {
			System.out.println("get princple.." + authenticateAction.getPrincipal());
		} else {
			System.out.println("not princi[le");
		}

		if (loginForcePersonal == null) {

			loginForcePersonal = (String) session.getAttribute("loginForcePersonalId");
			logger.info(">>>>>>> SESSIONID AFTER LOGIN:-" + session.getId());

			if (loginForcePersonal == null) {

				redirectAttributes.addFlashAttribute("errorMsg", "ForcePersonal is not present....s !");
				return "redirect:/initial-login";
			}
		}
		Optional<ForcePersonnelDto> optional = forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(loginForcePersonal);

		if (optional.isEmpty()) {

			redirectAttributes.addFlashAttribute("errorMsg", "no ForcePersonal Details in Db!");
			return "redirect:/initial-login";
		}

		ForcePersonnelDto forcePersonal = new ForcePersonnelDto();

		forcePersonal = optional.get();

		Set<String> userRoles = userRoleService.getRoleByforcePersonalId(forcePersonal.getForcePersonalId());
		// System.out.println(userRoles);
		logger.info("Login Status updated......." + CommonConstant.LOGIN_SUCCESSFULL);
		loginLogoutService.loginStatus(userName, httpServletRequest);

		int myRequestCount = amaDeclarationCountService.findMyDeclarationCount(forcePersonal.getForcePersonalId());
		model.addAttribute("myRequestCount", myRequestCount);
		model.addAttribute("loginUserDetails",
				loginUserDetails.getCandicateForcePersonalId(forcePersonal.getForcePersonalId()));
		session.setAttribute("name", forcePersonal.getName());
		session.setAttribute("irlaNumber", forcePersonal.getForceId());
		session.setAttribute("designation", forcePersonal.getDesignation());

		Optional<ForcePersonnelDto> optional2 = forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcePersonal.getForcePersonalId());

		ForcePersonnelDto forcePersonal2 = optional2.get();
		session.setAttribute("forcepersonalId", forcePersonal.getForcePersonalId());
		if (!optional2.isEmpty() && forcePersonal2.getDesignation().trim().equals("admin")) {
			return "admin-template/dashboard";
		}

		session.setAttribute("gazettedNonGazettedFlag",
				rankRepo.getGazettedNonGazettedFlag(forcePersonal.getRank().trim()));
		model.addAttribute("gazettedNonGazettedFlag",
				rankRepo.getGazettedNonGazettedFlag(forcePersonal.getRank().trim()));
		List<UserRoleDto> roleDtosList = new ArrayList<>();
		Iterator<String> roleIterator = userRoles.iterator();
		String roleCode;
		int rCode;
		while (roleIterator.hasNext()) {
			UserRoleDto userRoleDto = new UserRoleDto();
			userRoleDto.setForceNo(forcePersonal2.getForceNo());
			userRoleDto.setForcePersonalId(forcePersonal2.getForcePersonalId());
			userRoleDto.setUnit(forcePersonal2.getUnit());
			roleCode = roleIterator.next();
			userRoleDto.setRoleCode(RoleCodeNameUtil.getRoleNameByRoleCode(roleCode));
			rCode = Integer.parseInt(refRoleRepo.getRoleIdByRoleCode(roleCode.trim()));
			userRoleDto.setrCode(rCode);
			if (roleCode != null) {

				if (roleCode.equals("spu")) {

					redirectAttributes.addFlashAttribute("forcepersonalId", forcePersonal2.getForcePersonalId());
					redirectAttributes.addFlashAttribute("unit", forcePersonal2.getUnit());
					redirectAttributes.addFlashAttribute("forceNumber", forcePersonal2.getForceNo());
					int activeRoleCount = refRoleRepo.activeRoleCount(forcePersonal2.getForcePersonalId());
					int InactiveRoleCount = refRoleRepo.inActiveRoleCount(forcePersonal2.getForcePersonalId());
					int totalRoleCount = activeRoleCount + InactiveRoleCount;
					session.setAttribute("rCode", rCode);
					model.addAttribute("totalRoleCount", totalRoleCount);
					model.addAttribute("activeRoleCount", activeRoleCount);
					model.addAttribute("InActiveRoleCount", InactiveRoleCount);

					return "redirect:/role-map-to-" + roleCode.trim() + "-dashboard";

				}
			} else {
				model.addAttribute("errorMsg", "Invalid user with worng Email or password");
				return "bootstrap_medical_temp/login-page";
			}
			userRoleDto.setUri("role-map-to-" + roleCode.trim() + "-dashboard");
			roleDtosList.add(userRoleDto);

		}

//===============================================Notification Scrolling RK==========================================================
		Integer forceNo = forcePersonal.getForceNo();
		List<NotificationDto> notificationDtoList = notificationService.getAllNotifictionForScrollView(forceNo);
		model.addAttribute("notificationList", notificationDtoList);
		//session.setAttribute("notificationList", NotificationDtoList);
//==================================================================================================================================		
		model.addAttribute("roleDtosList", roleDtosList);
		//session.setAttribute("roleDtosList", roleDtosList);
		
 List<IndividualAlertAndNotificationDto> listOfAlertsAndNotificationsByForcePersonnelId = alertAndNotificationService.alertAndNotifications(forcePersonal.getForcePersonalId());
		 
		 ForcePersonnelDto forcePersonnelDetails = forcePersonnelService
					.getForcePersonnelDetailsByForcePersonnelId(forcePersonal.getForcePersonalId()).get();

			notificationCount = listOfAlertsAndNotificationsByForcePersonnelId.size();

			model.addAttribute("listOfAlertsAndNotificationsByForcePersonnelId",
					listOfAlertsAndNotificationsByForcePersonnelId);
			model.addAttribute("notificationCount", notificationCount);
			model.addAttribute("loginUserDetails", forcePersonnelDetails);
			model.addAttribute("candidateForcePersonalId", userName);
			model.addAttribute("candidateDetails", forcePersonnelDetails);
		

		return "UserMenu/dashboard";
	}

	@GetMapping("/sign-out-complete")
	public String signout(HttpServletRequest request) {

		HttpSession httpSession = request.getSession();
		if (!httpSession.equals(null)) {
			httpSession.invalidate();
		}

		return "redirect:/initial-login";

	}

	@GetMapping("/sign-out")
	public String signOutComplete(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ServletException {
		logger.info("Logging out......!");
		String forcePeronnselId=(String) request.getSession().getAttribute("forcepersonalId");
		logger.info("getting forcePeronnelId for logout:"+forcePeronnselId);
		 context.removeAttribute(forcePeronnselId);
			logger.info("Context Remove for forcePeronnelId");
		request.logout();
		List<Force> refForces = refForceRepo.findAll();
		model.addAttribute("refForceList", refForces);
		

		logger.info("Login Status updated......." + CommonConstant.LOGOUT_SUCCESSFULL);
		loginLogoutService.logoutStatus(userName, request);
		// Log out the user
		new SecurityContextLogoutHandler().logout(request, response,
				SecurityContextHolder.getContext().getAuthentication());

		// Invalidate session
		if(request.getSession(false)!=null)
			logger.info ("session Id at Log out " + request.getSession(false).getId());
		
		
		return "redirect:/initial-login";
	}

	@GetMapping("/change-password")
	public String changePassword(HttpServletRequest httpServletRequest, Model model, HttpSession session,
			RedirectAttributes redirectAttributes, CsrfToken csrfToken) {
		String loginForcePersonalId = null;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
		if (inputFlashMap != null) {
			loginForcePersonalId = (String) inputFlashMap.get("loginForcePersonalId");

		}
		if (loginForcePersonalId == null) {

			loginForcePersonalId = (String) session.getAttribute("loginForcePersonalId");

			if (loginForcePersonalId == null) {

				session.invalidate();
				redirectAttributes.addFlashAttribute("errorMsg", "Invalid Force Name........!");
				return "redirect:/initial-login";
			}
		}

		model.addAttribute("loginForcePersonalId", loginForcePersonalId);

		return "UserMenu/change-password";
	}

}
