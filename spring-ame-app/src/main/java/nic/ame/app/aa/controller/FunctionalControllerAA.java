package nic.ame.app.aa.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.aa.model.ApprovalStatus;
import nic.ame.app.aa.repository.ApprovalStatusRepository;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.esign.StringConstants;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.model.AmeFinalReportFileDir;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeApprovalProcess;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.go.AmeFinalReportFileDirGo;
import nic.ame.app.master.model.go.repository.AmeFinalReportFileDirGoRepository;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeApprovalProcessRepository;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.po.controller.FunctionalControllerPO;
import nic.ame.app.test.controller.ForcePersonalDto;
import nic.ame.constant.CommonConstant;

@Controller
public class FunctionalControllerAA {

	Logger logger = LoggerFactory.getLogger(FunctionalControllerPO.class);
	@Autowired
	private LoginUserDetails loginUserDetails;


	
	@Autowired
	private AmeApprovalProcessRepository ameApprovalProcessRepository;

	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;

	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepository;
	
	
	@Autowired
	private ForcePersonalService forcePersonalService;
	
	
	@Autowired
	private AmeFinalReportFileDirGoRepository ameFinalReportFileDirGoRepository;
	
	
	@Autowired
	private ApprovalStatusRepository approvalStatusRepositoy;
	
	


	@PostMapping("/pending-request-for-ame-aa")
	public String getlistSignedAA(@RequestParam("forcePersonalId") String forcePersonalId,
			@RequestParam("roleId") String roleId, @RequestParam("rCode") String rCode, Model model,
			HttpServletRequest httpServletRequest,HttpSession session) {
		
		List<ForcePersonnel> forcePersonallist = new ArrayList<>();
		session.setAttribute("rCodeMedical",Integer.parseInt(rCode.trim()));
		session.setAttribute("candidateIrlaNuber", forcePersonalId);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		List<String> boardIds = medicalBoardMemberRepository.findBoardIdsByForcePersonalIdAndRole(forcePersonalId,
				rCode);
		
	    if (boardIds.isEmpty()) {
	        model.addAttribute("boardList", forcePersonallist);
	        return "aa-template/list-of-board-aa";
	    }
		
		
		List<String> ameIds = ameApprovalProcessRepository.findAmeIdByRoleCodeAndBoardId(boardIds, rCode);

		  if (ameIds.isEmpty()) {
		        model.addAttribute("boardList", forcePersonallist);
		        return "aa-template/list-of-board-aa";
		    }
		
		 List<AmeApplicationFlowStatus> forcePersonnelDetails = ameApplicationFlowStatusRepo.findByAmeIds(ameIds);
		 if (forcePersonnelDetails.isEmpty()) {
		        model.addAttribute("boardList", forcePersonallist);
		        return "aa-template/list-of-board-aa";
		    }	 
		 
	
		 for (AmeApplicationFlowStatus ameApplicationFlowStatus : forcePersonnelDetails) {
		        Optional<ForcePersonnelDto> optionalForcePersonalDto = forcePersonalService.findByForcePersonalId(ameApplicationFlowStatus.getForcepersonalId());
		        
		        if (optionalForcePersonalDto.isPresent()) {
		            ForcePersonnel forcePersonnel = new ForcePersonnel();
		            forcePersonnel.setName(optionalForcePersonalDto.get().getName());
		            forcePersonnel.setForceNo(optionalForcePersonalDto.get().getForceNo());
		            forcePersonnel.setForceId(optionalForcePersonalDto.get().getForceId());
		            forcePersonnel.setUnit(optionalForcePersonalDto.get().getUnit());
		            forcePersonnel.setAmeId(ameApplicationFlowStatus.getAmeId());
		          //  forcePersonnel.setForcePersonalId(optionalForcePersonalDto.get().getForcePersonalId());
		       	   
		            forcePersonallist.add(forcePersonnel);
		        }
		        session.setAttribute("candidateIrlaNuber", optionalForcePersonalDto.get().getForceId());
		    }
		
		if (forcePersonallist.size() == 0) {

			model.addAttribute("boardList", forcePersonallist);
			
			

		} else {
			model.addAttribute("boardList", forcePersonallist);
			model.addAttribute("forcepersonalId", forcePersonalId);

		}
		return "aa-template/list-of-board-aa";

	}

	@PostMapping("/read-esigned-pdf-by-aa")
	public String readEsignedPdfByBm(
	        @RequestParam("ameId") String ameId,
	        @RequestParam("forcepersonalId") String forcepersonalId,
	        @RequestParam("approvalStatus") String approvalStatus,
	        @RequestParam("remarks") String remarks,
	        Model model) {


		ApprovalStatus approvedStatus = new ApprovalStatus();
		approvedStatus.setAmeId(ameId);
		approvedStatus.setForcePersonalId(forcepersonalId);
		approvedStatus.setApprovalStatus(approvalStatus);
		approvedStatus.setRemark(remarks);
		approvedStatus.setCreatedOn(LocalDateTime.now());
		approvedStatus.setUpdatedOn(LocalDateTime.now());
		approvalStatusRepositoy.save(approvedStatus);

	    String filePath = ameFinalReportFileDirGoRepository.findByAmeId(ameId).get(0).getFilePath();

	    model.addAttribute("filePath", filePath);
	    model.addAttribute("ameId", ameId);
	    model.addAttribute("forcepersonalId", forcepersonalId);
	    model.addAttribute("approvalStatus", approvalStatus);
	    model.addAttribute("remarks", remarks);

	    return "aa-template/read-esigned-pdf-by-aa";
	}

	
	@RequestMapping(path = "/aa-dashboard",method = RequestMethod.GET)
	public String getPoDashBoard(
			@RequestParam(value = "forcePersonalId", required = false) String forcePersonalId,
			@RequestParam(value = "boardId", required = false) String boardId,
			@RequestParam(value="rCode",required = false ) String rCode,
			Model model,HttpServletRequest httpServletRequest) {
		
		HttpSession session=httpServletRequest.getSession(false);
		if(session==null) {
			model.addAttribute("errorMsg", "No valid details found in the database please contact admin...");
			return "bootstrap_medical_temp/index";
			
		}
		
		if(forcePersonalId==null) {
		forcePersonalId = (String) session.getAttribute("forcepersonalId");

		}
		if(rCode==null) {
			rCode=(String) session.getAttribute("rCode");
		}
		if(boardId==null) {
			boardId=(String) session.getAttribute("boardId");
		}
		
		 String roleName="po"; 
		//Integer declarationCount=ttAppointmentAmeRepo.getAppointmentCountByBoardId(boardId);
		String pendingCountValue;
//		if(declarationCount==null) {
//			pendingCountValue="0";
//		}else {
//			pendingCountValue=String.valueOf(declarationCount);
//		}
        
		session.setAttribute("forcepersonalId", forcePersonalId);
		session.setAttribute("roleName",roleName.trim().toUpperCase());
	//	model.addAttribute("declarationCountPo", pendingCountValue);
		model.addAttribute("boardId", boardId);
		model.addAttribute("forcepersonalId", forcePersonalId);
		model.addAttribute("rCode", rCode);
	
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
           return"aa-template/aa-dashboard";
	
	}
	
	
	@GetMapping("/total-go-completed")
	public String totalGOCompleted(@RequestParam("forcePersonalId") String forcePersonalId,
			@RequestParam("roleId") String roleId, @RequestParam("rCode") String rCode, Model model,
			HttpServletRequest httpServletRequest,HttpSession session) {
		
		
		List<ForcePersonnel> forcePersonallist = new ArrayList<>();
		session.setAttribute("rCodeMedical",Integer.parseInt(rCode.trim()));
		session.setAttribute("candidateIrlaNuber", forcePersonalId);
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonalId));
		List<String> boardIds = medicalBoardMemberRepository.findBoardIdsByForcePersonalIdAndRole(forcePersonalId,
				rCode);
		
	    if (boardIds.isEmpty()) {
	        model.addAttribute("boardList", forcePersonallist);
	        return "aa-template/list-of-board-aa";
	    }
		
		
		List<String> ameIds = ameApprovalProcessRepository.findAmeIdByRoleCodeAndBoardIdGOCompleted(boardIds);

		  if (ameIds.isEmpty()) {
		        model.addAttribute("boardList", forcePersonallist);
		        return "aa-template/list-of-board-aa";
		    }
		
		 List<AmeApplicationFlowStatus> forcePersonnelDetails = ameApplicationFlowStatusRepo.findByAmeIds(ameIds);
		 if (forcePersonnelDetails.isEmpty()) {
		        model.addAttribute("boardList", forcePersonallist);
		        return "aa-template/list-of-board-aa";
		    }	 
		 
	
		 for (AmeApplicationFlowStatus ameApplicationFlowStatus : forcePersonnelDetails) {
		        Optional<ForcePersonnelDto> optionalForcePersonalDto = forcePersonalService.findByForcePersonalId(ameApplicationFlowStatus.getForcepersonalId());
		        
		        if (optionalForcePersonalDto.isPresent()) {
		            ForcePersonnel forcePersonnel = new ForcePersonnel();
		            forcePersonnel.setName(optionalForcePersonalDto.get().getName());
		            forcePersonnel.setForceNo(optionalForcePersonalDto.get().getForceNo());
		            forcePersonnel.setForceId(optionalForcePersonalDto.get().getForceId());
		            forcePersonnel.setUnit(optionalForcePersonalDto.get().getUnit());
		            forcePersonnel.setAmeId(ameApplicationFlowStatus.getAmeId());
		          //  forcePersonnel.setForcePersonalId(optionalForcePersonalDto.get().getForcePersonalId());
		       	   
		            forcePersonallist.add(forcePersonnel);
		        }
		        session.setAttribute("candidateIrlaNuber", optionalForcePersonalDto.get().getForceId());
		    }
		
		if (forcePersonallist.size() == 0) {

			model.addAttribute("boardList", forcePersonallist);
			
			

		} else {
			model.addAttribute("boardList", forcePersonallist);
			model.addAttribute("forcepersonalId", forcePersonalId);

		}
		return "aa-template/list-of-board-aa";

	}
	
	@CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL, StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
	@GetMapping("download-ame-final-report-uploaded-go")
	    public ResponseEntity<InputStreamResource> downloadAmeFinalReportUploadedGO(@RequestParam("ameId") String ameId) {
		
	     String filePath=null;
	 	List<AmeFinalReportFileDirGo> ameFinalReportFileDirList = ameFinalReportFileDirGoRepository.findByAmeId(ameId);
	    String fileName=null;
		if (ameFinalReportFileDirList.size() == 1) {

			Optional<AmeFinalReportFileDirGo> ameFinalReportFileDirOptional = ameFinalReportFileDirGoRepository
					.findByUniqueAmeId(ameFinalReportFileDirList.get(0).getUniqueAmeId());
			//Map<String, String> response = getFilePathForAmeFinalResult(ameFinalReportFileDirOptional);
	 
		if(!ameFinalReportFileDirOptional.isEmpty()) {
			if(ameFinalReportFileDirOptional.get().getFilePath()!=null) {
				filePath=ameFinalReportFileDirOptional.get().getFilePath();
			fileName=ameFinalReportFileDirOptional.get().getFileName()
					;}
			else {
				filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
		}}}
		File file=new File(filePath);
		FileInputStream fileInputStream = null;
		if (file == null || !file.exists()) {
			 file=new File(CommonConstant.DEFAULT_FILE_PATH_NAME);
			
		}
		  try {
			  fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName);
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

		 return ResponseEntity.ok()
		            .headers(headers)
		            .contentLength(file.length())
		            .contentType(MediaType.APPLICATION_PDF)
		            .body(new InputStreamResource(fileInputStream));

	}

	
}

