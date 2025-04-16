package nic.ame.app.user.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.service.RoleService;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.app.user.dto.UserFeedBack;
import nic.ame.app.user.management.controller.UserMamagementDashBoardController;
import nic.ame.app.user.service.UserfeedbackService;

@Controller
public class getfeedbackbyforceunit {

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
	
	
@PostMapping("/get-feedback-by-force-unit")
@ResponseBody
public List<UserFeedBack> getFeedbackByForceAndUnit(
        @RequestParam Integer forceId,
        @RequestParam String unitId) {

    // Log incoming parameters for debugging
    logger.info("Received request for forceId: {} and unitId: {}", forceId, unitId);

    // Validate inputs
    if (forceId == null || forceId == 0 || unitId == null || unitId.equals("0")) {
        logger.warn("Invalid forceId or unitId provided.");
        return List.of(); // Return an empty list in case of invalid input
    }

    // Fetch filtered feedbacks
    List<UserFeedBack> feedbackList = feedbackService.getFeedbackByForceAndUnit(forceId, unitId);

    // Log the results
    logger.info("Returning {} feedback entries.", feedbackList.size());

    return feedbackList;
}
}

