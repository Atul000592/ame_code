package nic.ame.app.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.service.RoleService;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.TTAppointmentAme;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.LoginUserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class NewTotalDashboard {

    @Autowired
    private LoginUserDetails loginUserDetails;

    @Autowired
    private ForceRepo refForcerepo;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
    
    
    @Autowired
    private TTAppointmentAmeRepo ttAppointmentAmeRepo;

    @GetMapping("ame-stats-dashboard")
    public String showDashboard(Model model, HttpSession httpSession) {
        model.addAttribute("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
        System.out.println("Force Personal ID from Session: " + loginForcePersonalId);
        ForcePersonnelDto userDetails = loginUserDetails.getLoginUserDetails(loginForcePersonalId);
        System.out.println("User Details Retrieved: " + userDetails);

        if (userDetails != null) {
            model.addAttribute("loginUserDetails", userDetails);

            // Fetch forces
            List<Force> forceList = refForcerepo.findAll();
            model.addAttribute("forceList", forceList);

            int totalAME = ameApplicationFlowStatusService.getTotalAMECount();
            model.addAttribute("totalAME", totalAME);

            int todayAME = ameApplicationFlowStatusService.getTodayAMECount();
            model.addAttribute("todayAME", todayAME);

            int totalAMEAppointment = ameApplicationFlowStatusService.getTotalAMEAppointment();
            model.addAttribute("totalAMEAppointment", totalAMEAppointment);

            int totalDeclaration = ameApplicationFlowStatusService.getTotalDeclaration();
            model.addAttribute("totalDeclaration", totalDeclaration);

            int totalAMEPending = ameApplicationFlowStatusService.getTotalAMEPending();
            model.addAttribute("totalAMEPending", totalAMEPending);

            int totalAMECompleted = ameApplicationFlowStatusService.getTotalAMECompleted();
            model.addAttribute("totalAMECompleted", totalAMECompleted);

            int totalAMEReview = ameApplicationFlowStatusService.getTotalAMEReview();
            model.addAttribute("totalAMEReview", totalAMEReview);
            
            int todayAMEAppointment = ameApplicationFlowStatusService.getTodayAMEAppointment();
            model.addAttribute("todayAMEAppointment", todayAMEAppointment);

            int todayDeclaration = ameApplicationFlowStatusService.getTodayDeclaration();
            model.addAttribute("todayDeclaration", todayDeclaration);

            int todayAMEPending = ameApplicationFlowStatusService.getTodayAMEPending();
            model.addAttribute("todayAMEPending", todayAMEPending);

            int todayAMECompleted = ameApplicationFlowStatusService.getTodayAMECompleted();
            model.addAttribute("todayAMECompleted", todayAMECompleted);

            int todayAMEReview = ameApplicationFlowStatusService.getTodayAMEReview();
            model.addAttribute("todayAMEReview", todayAMEReview);
            
            int forceTotalAMEAppointment = ameApplicationFlowStatusService.ForceTotalAMEAppointment();
            model.addAttribute("totalAMEAppointmentByForce", forceTotalAMEAppointment);
            
            int forceTotalDeclaration = ameApplicationFlowStatusService.ForceTotalDeclaration();
            model.addAttribute("forceTotalDeclaration", forceTotalDeclaration);
            
            int forceTotalPending = ameApplicationFlowStatusService.ForceTotalPending();
            model.addAttribute("forceTotalPending", forceTotalPending);
            
            return "UserMenu/ame-stats-dashboard";
        } else {
            model.addAttribute("error", "User details not found");
            return "redirect:/intial-login";
        }
    }

    
    @GetMapping
    public String loadDashboard(Model model) {
     int totalAMEByForce = ameApplicationFlowStatusService.getTotalAMEbyForce();
    int todayAMEByForce = ameApplicationFlowStatusService.getTodayAMEbyForce();
    int forceTotalAMEAppointment = ameApplicationFlowStatusService.ForceTotalAMEAppointment();
    int forceTotalDeclaration = ameApplicationFlowStatusService.ForceTotalDeclaration();
    int forceTotalPending = ameApplicationFlowStatusService.ForceTotalPending();
    model.addAttribute("totalAMEbyForce", totalAMEByForce);
    model.addAttribute("todayAMEbyForce", todayAMEByForce);
    model.addAttribute("forceTotalAMEAppointment", forceTotalAMEAppointment);
    model.addAttribute("forceTotalDeclaration", forceTotalDeclaration);
    model.addAttribute("forceTotalPending", forceTotalPending);

        return "UserMenu/ame-stats-dashboard"; // Thymeleaf template
    }

    @PostMapping("get-force-stats")
    @ResponseBody
    public Map<String, Integer> getForceStats(@RequestParam("forceNo") Integer forceNo) {
        System.out.println("Received forceNo: " + forceNo); // Debug log for forceNo
        Map<String, Integer> stats = new HashMap<>();
        
        // Fetch the statistics for the selected force
        stats.put("totalAMEByForce", ameApplicationFlowStatusService.getTotalAMEByForce(forceNo));
        stats.put("todayAMEByForce", ameApplicationFlowStatusService.getTodayAMEByForce(forceNo));
        stats.put("ForceTotalAMEAppointment", ameApplicationFlowStatusService.ForceTotalAMEAppointment(forceNo));
        stats.put("ForceTotalDeclaration", ameApplicationFlowStatusService.ForceTotalDeclaration(forceNo));
        stats.put("ForceTotalPending", ameApplicationFlowStatusService.ForceTotalPending(forceNo));
        stats.put("ForceTotalCompleted", ameApplicationFlowStatusService.ForceTotalCompleted(forceNo));
        stats.put("ForceTotalReview", ameApplicationFlowStatusService.ForceTotalReview(forceNo));
        stats.put("ForceTodayAMEAppointment", ameApplicationFlowStatusService.ForceTodayAMEAppointment(forceNo));
        stats.put("ForceTodayDeclaration", ameApplicationFlowStatusService.ForceTodayDeclaration(forceNo));
        stats.put("ForceTodayPending", ameApplicationFlowStatusService.ForceTodayPending(forceNo));
        stats.put("ForceTodayCompleted", ameApplicationFlowStatusService.ForceTodayCompleted(forceNo));
        stats.put("ForceTodayReview", ameApplicationFlowStatusService.ForceTodayReview(forceNo));
      //  
        
        // Return the stats as a map
        return stats;
    }





    
    
    
}
    