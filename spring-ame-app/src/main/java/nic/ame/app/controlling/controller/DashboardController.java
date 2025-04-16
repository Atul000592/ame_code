package nic.ame.app.controlling.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.master.medical.service.AmeAssessmentDisplayService;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.LoginUserDetails;


@Controller
public class DashboardController {
	
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	
	
	@Autowired
	private AmeAssessmentDisplayService ameAssessmentDisplayService;

	
	@RequestMapping(value = {"/medical-controlling-dashboard"},method = RequestMethod.GET)
	public String goToBoardMemberDashboard(HttpSession session,Model model) {
		String boardId = (String) session.getAttribute("boardId");
		String forcepersonalId = (String) session.getAttribute("forcepersonalId");
		String roleName="CH";
		Optional<ForcePersonnel> forceOptional=forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
		String unit = null;
		int forceNo = 0;
		if(forceOptional.isPresent()) {
			unit=forceOptional.get().getUnit();
			forceNo=forceOptional.get().getForceNo();
		}
		Integer pendingCount= amaDeclarationCountService.getAMADeclarationPendingListCount(boardId);
		String pendingCountValue;
		if(pendingCount==null) {
			pendingCountValue="0";
		}else {
			pendingCountValue=String.valueOf(pendingCount);
		}
	
		Integer appointmentCompleted=amaDeclarationCountService.findDataForAMAAppointmentCount(boardId);
	   String appointmentCompletedValue;
		if(appointmentCompleted==null) {
			appointmentCompletedValue="0";
		}else {
			appointmentCompletedValue=String.valueOf(appointmentCompleted);
		}
		Integer totalCompletedAma=amaDeclarationCountService.findTotalDeclarationUnderProcessByBoardId(boardId);
		String totalCompletedAmaValue="0";
        String forcePersonalId=(String) session.getAttribute("forcepersonalId");
    	//Optional<ForcePersonal> optional= forcePersonalRepository.getByForcePersonalId(forcePersonalId);
		
		Integer totalCompletedCount=amaDeclarationCountService.findDataForDealingHandCount(unit,forceNo);
		if(totalCompletedCount!=null) {
			totalCompletedAmaValue=String.valueOf(totalCompletedCount);
		}
		if(forcePersonalId==null) {
			session.invalidate();
			model.addAttribute("errorMsg", "You have been sign out // Session expired.....! or Invalid User....");
			return "bootstrap_medical_temp/index";
		}
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
		model.addAttribute("pendingcountama", pendingCountValue);
		model.addAttribute("appointmentcompletedama",appointmentCompletedValue);
		model.addAttribute("totalCompletedAma", totalCompletedAma);
		
		return "medical-controlling/dashboard-ma";
	}
	
	
	
	@RequestMapping(path = "/send-ngo-request-to-ama")
	public ResponseEntity<?> saveAndSendNgoAmeRequestToAma(Model model,HttpSession session,@RequestBody List<String> data) {
		
		String forcepersonalId = (String) session.getAttribute("forcepersonalId");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+data);
		
		Optional<ForcePersonnel> forcePersonnelOptional=forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
		
	    String response=ameAssessmentDisplayService.saveNgoAmeList(data,forcepersonalId,forcePersonnelOptional.get().getForceNo());      
	    Map<String, String> uniqueKey=new HashMap<>();
	    System.out.println(response);
	    
	    uniqueKey.put("response",response);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
	
		return ResponseEntity.status(HttpStatus.OK).body(uniqueKey);
	}
	
	
}
