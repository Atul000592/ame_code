package nic.ame.app.user.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.controller.AdminLoginController;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.user.service.UserProfileService;
import nic.ame.app.user.service.UserService;

@Controller
public class UserProfileController {

    Logger logger = LogManager.getLogger(AdminLoginController.class);

   
    @Autowired
    private UserProfileService userService;

   @GetMapping("api-user-details")
public ResponseEntity<ForcePersonnelDto> getUserDetails(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Use appropriate status code
    }

    String loginForcePersonalId = (String) session.getAttribute("forcepersonalId");
    if (loginForcePersonalId == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Use appropriate status code
    }

    ForcePersonnelDto userDetails = userService.getLoginUserDetails(loginForcePersonalId);

    if (userDetails == null) {
        return ResponseEntity.notFound().build(); // Handle case where user details are not found
    }

    return ResponseEntity.ok(userDetails);
}

}
