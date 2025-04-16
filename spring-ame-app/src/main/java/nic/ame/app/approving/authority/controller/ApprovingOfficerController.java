package nic.ame.app.approving.authority.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.master.service.LoginUserDetails;


@Controller
public class ApprovingOfficerController {
    
    @Autowired
    private LoginUserDetails loginUserDetails;

@Autowired
    MedicalBoardMemberService medicalBoardMemberService;

    @PostMapping("/role-map-to-AO-dashboard")
    public String getToAmaDashboardBoardForOfficerAO(
            @RequestParam("forcePersonalId") String forcePersonalId,
            @RequestParam("boardId") String boardId,
            @RequestParam("rCode") String rCode,
            Model model,
            HttpServletRequest httpServletRequest) {

        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            model.addAttribute("errorMsg", "No valid details found in the database please contact admin...");
            return "bootstrap_medical_temp/index";

        }
        MedicalBoardDetailDto boardDetailDto=new MedicalBoardDetailDto();
		session.setAttribute("boardId", boardId.trim());
        boardDetailDto=medicalBoardMemberService.findBoardDetailsByBoardId(boardId);
		if(boardDetailDto.getBoardId()!=null) {
			//boardDetailDto=medicalBoardMemberService.findBoardDetailsByBoardId(boardId);
			model.addAttribute("boardDetails",boardDetailDto );
		}else {
		
			model.addAttribute("boardDetails", boardDetailDto);
		}

        session.setAttribute("forcepersonalId", forcePersonalId);
        model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
        model.addAttribute("boardId", boardId);

        return "ao-template/view-dashboard-ao";

    }
}
