package nic.ame.app.master.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.IndividualAlertAndNotificationDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.service.AgeCalculatorService;
import nic.ame.app.master.service.AlertAndNotificationService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.user.dto.AmeFinalResultCandidate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class IndividualAlertAndNotificationController {
	
	
	@Autowired
	private ForcePersonnelService  forcePersonnelService;
	
	@Autowired
	private  AlertAndNotificationService alertAndNotificationService;
	
	@Autowired
	private AgeCalculatorService ageCalculatorService;
	
	@SuppressWarnings({ "unused", "unchecked" })
	@GetMapping("individual-alert-and-notification-status")
	public String individualAlertAndNotificationStatus(Model model,HttpSession httpSession) {
		
		List<UserRoleDto> roleDtosList	= (List<UserRoleDto>) httpSession.getAttribute("roleDtosList");
		String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");
		Integer notificationCount=0;
		

		if (httpSession == null) {
			model.addAttribute("errorMsg",
					"INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST SESSION EXPIRED......");
		//	logger.info("session...invalidated...........!");
			return "redirect:/login-page";
		}
		String forcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		if (forcePersonalId == null) {
			model.addAttribute("errorMsg",
					"INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST SESSION EXPIRED......");
		//	logger.info("Invalid User Login..........!");
			return "redirect:/login-page";
		}
		
		 
		 ForcePersonnelDto forcePersonnelDetails = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(loggedInUserForcepersonnelId).get();
		 List<IndividualAlertAndNotificationDto> listOfAlertsAndNotificationsByForcePersonnelId = alertAndNotificationService.alertAndNotifications(loggedInUserForcepersonnelId);
		 
		 Date dob = forcePersonnelDetails.getDob();
		 int age = ageCalculatorService.getAge(dob);
		 Date doj = forcePersonnelDetails.getJoiningDate();
		 String dateOfJoining = ageCalculatorService.calculateYearsMothsandDays(doj);
	
		 notificationCount=listOfAlertsAndNotificationsByForcePersonnelId.size();
		 
		 model.addAttribute("age", age);
		 model.addAttribute("doj", dateOfJoining);
		 model.addAttribute("listOfAlertsAndNotificationsByForcePersonnelId",listOfAlertsAndNotificationsByForcePersonnelId);
	     model.addAttribute("notificationCount", notificationCount);
         model.addAttribute("loginUserDetails",forcePersonnelDetails);
         model.addAttribute("roleDtosList", roleDtosList);
		 model.addAttribute("candidateForcePersonalId", loggedInUserForcepersonnelId);
		 model.addAttribute("candidateDetails", forcePersonnelDetails);
		
		return "medical-sub-ordinate/individual-alert-and-notification-status-page";
	}

}
