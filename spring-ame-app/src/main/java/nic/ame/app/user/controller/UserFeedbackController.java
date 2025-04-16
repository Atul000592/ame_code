package nic.ame.app.user.controller;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.RefRole;
import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.medical.service.RoleService;
import nic.ame.app.master.model.FeedbackForm;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.app.user.dto.UserFeedBack;
import nic.ame.app.user.management.controller.UserMamagementDashBoardController;
import nic.ame.app.user.repository.FeedbackRepository;
import nic.ame.app.user.service.UserfeedbackService;


@Controller
public class UserFeedbackController {
	
	Logger logger = LogManager.getLogger(UserMamagementDashBoardController.class);
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired 
	private LoginRepository loginRepository;

	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
    private UserfeedbackService feedbackService;                                       
	
	@Autowired
	private ForceRepo refForcerepo;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UnitRepository unitRepository;
	@Autowired
	private FeedbackRepository feedbackRepository;
	

	@RequestMapping(value = "feedback-form", method = RequestMethod.GET)
	public String getFeedbackForm(Model model, HttpSession httpSession) {
	    String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
	    System.out.println("Force Personal ID from Session: " + loginForcePersonalId);
	    
	    ForcePersonnelDto userDetails = loginUserDetails.getLoginUserDetails(loginForcePersonalId);
	    System.out.println("User Details Retrieved: " + userDetails);

	    if (userDetails != null) {
	        model.addAttribute("loginUserDetails", userDetails);
	        
	        // Create FeedbackForm and populate from userDetails
	        FeedbackForm feedbackForm = new FeedbackForm();
	        feedbackForm.setIrlaNo(userDetails.getForceId());
	        feedbackForm.setName(userDetails.getName());
	        feedbackForm.setDesignation(userDetails.getDesignation());
	        feedbackForm.setForceName(userDetails.getForceName());
	        feedbackForm.setForceNo(userDetails.getForceNo());
	        feedbackForm.setUnit(userDetails.getUnit());

	        model.addAttribute("feedbackForm", feedbackForm);
	    } else {
	        model.addAttribute("error", "User details not found");
	    }
	    
	    return "UserMenu/feedback-form";  // The view that contains the feedback form modal.
	}



	@PostMapping("/submit-feedback")
	public String submitFeedback(
	        @RequestParam String irlaNo,
	        @RequestParam String name,
	        @RequestParam String designation,
	        @RequestParam String forceName,
	        @RequestParam String feedback,
	        @RequestParam int starRating,
	        @RequestParam int forceNo,

	        @RequestParam String unit,
	        RedirectAttributes redirectAttributes
	        ) {

	    System.out.println("Feedback received:");
	    System.out.println("IRLA No: " + irlaNo);
	    System.out.println("Name: " + name);
	    System.out.println("Designation: " + designation);
	    System.out.println("Force Name: " + forceName);
	    System.out.println("Feedback: " + feedback);
	    System.out.println("Star Rating: " + starRating);
	    System.out.println("Force Number: " + forceNo);
	    System.out.println("Unit Number: " + unit);

	    // Create a FeedbackForm object or direct entity for saving
	    UserFeedBack userFeedBack = new UserFeedBack();
	    userFeedBack.setIrlaNo(irlaNo);
	    userFeedBack.setName(name);
	    userFeedBack.setDesignation(designation);
	    userFeedBack.setForceName(forceName);
	    userFeedBack.setFeedback(feedback);
	    userFeedBack.setStarRating(starRating);
	    userFeedBack.setForceNo(forceNo);
	    userFeedBack.setUnit(unit);

	    // Save feedback via the service
	    
	    if(feedbackRepository.save(userFeedBack) != null)
	    	redirectAttributes.addFlashAttribute("message", "Your Feedback has been saved successfully!");
	    else
	    	redirectAttributes.addFlashAttribute("message", "Something went Wrong!");

	    return "redirect:/feedback-form"; // Redirect back to the feedback form after submission.
	}

	@RequestMapping(value = "feedback-list", method = RequestMethod.GET) 
	public String getFeedbacks(Model model, HttpServletRequest httpServletRequest) {
	    HttpSession httpSession = httpServletRequest.getSession(false); // Avoid creating a new session
	    
	    if (httpSession == null) {
	        logger.info("Invalid session");
	        return "redirect:/login"; // Redirect to login or an error page
	    }
	    
	    String message = null;
	    Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
	    if (inputFlashMap != null) {
	        message = (String) inputFlashMap.get("message");
	    }

	    // Retrieve session attributes
	    String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
	    if (loginForcePersonalId == null) {
	        logger.info("Force personal ID not found in session");
	        return "redirect:/login";
	    }

	    // Fetch login user details
	    ForcePersonnelDto userDetails = loginUserDetails.getLoginUserDetails(loginForcePersonalId);
	    if (userDetails == null) {
	        logger.info("User details not found");
	        return "redirect:/login";
	    }

	    model.addAttribute("loginUserDetails", userDetails);

	    // Fetch forces
	    List<Force> forceList = refForcerepo.findAll();
	    model.addAttribute("forceList", forceList);
	    
	    List<UnitModel> unitList = unitRepository.findAll();
	    model.addAttribute("unitList", unitList);

	    // Set additional session data
	    String loginForceName = (String) httpSession.getAttribute("loginForceName");
	    String userType = (String) httpSession.getAttribute("userType");
	    model.addAttribute("loginForceName", loginForceName);
	    model.addAttribute("userType", userType);
	    model.addAttribute("message", message);

	    // Fetch and filter feedback list
	    List<UserFeedBack> feedbackList = feedbackService.getAllFeedbacks();
	    List<UserFeedBack> filteredFeedbackList = feedbackList.stream()
	    	    .filter(feedback -> Objects.equals(feedback.getForceNo(), userDetails.getForceNo()))
	    	    .filter(feedback -> Objects.equals(feedback.getUnit(), userDetails.getUnit()))
	    	    .collect(Collectors.toList());
	    



	    System.out.println("Filtered Feedback List: " + filteredFeedbackList); // Debugging
	    model.addAttribute("feedbackList", filteredFeedbackList);

	    return "UserMenu/feedback-list";
	}



}



