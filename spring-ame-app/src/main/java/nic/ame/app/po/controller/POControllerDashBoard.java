package nic.ame.app.po.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.dto.AppointmentListDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.AmeAppointmentAndDeclarationService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.po.dto.PoServiceDto;
import nic.ame.app.po.service.POService;
import nic.ame.constant.CommonConstant;

@Controller
public class POControllerDashBoard {

	Logger logger=LogManager.getLogger(POControllerDashBoard.class);
	
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private POService poService;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private TTAppointmentAmeRepo ttAppointmentAmeRepo;
	
	@Autowired
	ForcePersonnelService forcePersonnelService;

	@Autowired
	private MedicalBoardIndividualMappingService boardIndividualMappingService;
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	@Autowired
	AmeAppointmentAndDeclarationService ameAppointmentAndDeclarationService;

	@RequestMapping(value = { "/get-declaration-list-po" })
	public String getDeclarationListPo(HttpSession httpSession, Model model) {
		String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
	    String boardId=(String)httpSession.getAttribute("boardId");
	    logger.info("BoardId >>>> "+boardId);
	    Optional<ForcePersonnelDto> optional = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
	    model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
	    if (forcepersonalId == null) {

			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
	    

		String candidateGazettedNonGazettedFlag=(String) httpSession.getAttribute("candidateGazettedNonGazettedFlag");
		model.addAttribute("candidateGazettedNonGazettedFlag", candidateGazettedNonGazettedFlag);
		
		String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		
		 List<PoServiceDto> getListOfDeclarationList=poService.getDeclarationPersonalListToPo(boardId);
         for (PoServiceDto poServiceDto : getListOfDeclarationList) {
			System.out.println(">>>>>>>>>>>>>>>>>"+poServiceDto.getForcePersonalId());
		}
			if (getListOfDeclarationList.isEmpty()) {
				model.addAttribute("message", "HAVE NO RECORD TO DISPALY...! ");
				return"medical-po/declaration-list-page-po";

			}
			model.addAttribute("ListOfDeclarationList",getListOfDeclarationList);
		 
		return"medical-po/declaration-list-page-po";
	}
	

	
	
//	@RequestMapping(value = {"/po-dashboard","/home-page-po"})
//	public String getToPoDashBoard(Model model,HttpServletRequest httpServletRequest) {
//		
//		HttpSession session=httpServletRequest.getSession(false);
//		if(session==null) {
//			model.addAttribute("errorMsg", "No valid details found in the database please contact admin...");
//			return "bootstrap_medical_temp/index";
//			
//		}
//		String forcepersonalId = (String) session.getAttribute("forcepersonalId");
//		String boardId =(String) session.getAttribute("boardId");
//
//		
//		 String roleName="po"; 
//		Integer declarationCount= ttAppointmentAmeRepo.getAppointmentCountByBoardId(boardId);
//		String pendingCountValue;
//		if(declarationCount==null) {
//			pendingCountValue="0";
//		}else {
//			pendingCountValue=String.valueOf(declarationCount);
//		}
//	
//        session.setAttribute("forcepersonalId", forcepersonalId);
//		session.setAttribute("roleName",roleName.trim().toUpperCase());
//		model.addAttribute("declarationCountPo", pendingCountValue);
//		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcepersonalId));
//           return"medical-po/po-dashboard";
//	
//	}

	

	@GetMapping("generate-po-final-pdf")
	public String generatepofinalPdfNow(@RequestParam String forcepersonalId, @RequestParam String ameId,
			Model model) {
		logger.info("/generateamefinalPdf");
		model.addAttribute("forcepersonalId", forcepersonalId);
		model.addAttribute("ameId", ameId);
		return "UserMenu/view-ame-final-esign-pdf"; // Return the view name
	}

}
