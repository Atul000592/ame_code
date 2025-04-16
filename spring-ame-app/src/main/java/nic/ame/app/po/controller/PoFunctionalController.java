package nic.ame.app.po.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.admin.dto.UserRoleMapDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.ama.dto.DealingHandDto;
import nic.ame.app.ama.service.AmaDealingHandService;
import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.RandomCodeGenerator;

@Controller
public class PoFunctionalController {
	
	Logger logger=LoggerFactory.getLogger(PoFunctionalController.class);
	
	@Autowired
	private LoginUserDetails  loginUserDetails;
	
	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired
	private RefForceService refForceService;
	
	@Autowired 
	private ForceRepo forceRepo;
	
	@Autowired
	private AmaDealingHandService amaDealingHandService;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	private UserRoleService userRoleService;
	
	@Autowired
	private TTAppointmentAmeRepo ttAppointmentAmeRepo;
	
	@GetMapping(path = "/assign-dealing-hand-to-po-board")
	public String assignDealingHandPagePo(Model model ,HttpSession httpSession) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		String sBoardId=(String) httpSession.getAttribute("sBoardId");
		ForcePersonnelDto forcePersonalDto=loginUserDetails.getLoginUserDetails(loginForcePersonalId);
	    model.addAttribute("loginUserDetails", forcePersonalDto);
		List<DropDownDto> downDtosList=new ArrayList<>();

	    List<MedicalBoardMember> medicalBoardsList=medicalBoardMemberRepo.findByForcePersonalId(loginForcePersonalId);
	    
	    for (MedicalBoardMember medicalBoardMember : medicalBoardsList) {
	    	MedicalBoard medicalBoardData=medicalBoardRepo.getByBoardId(medicalBoardMember.getBoardId());
	    	if(medicalBoardData.getBoardId()!=null) {
	    		DropDownDto downDto=new DropDownDto();
	    		StringBuffer buffer=new StringBuffer();
	    		String place=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoardData.getBoardAtForceNo()),medicalBoardData.getPlace());
	    		buffer=buffer.append(medicalBoardData.getBoardId()).append(" || "+place).append(" || "+medicalBoardData.getUsedFor()+" || ");
	    		if(medicalBoardData.getGazettedFlag()==1) {
	    			buffer.append("Gazetted");
	    		}else {
	    			buffer.append("Non-Gazetted");
	    		}
	    		downDto.setBoardId(medicalBoardData.getBoardId());
	    	    downDto.setValue(buffer.toString());
	    	    downDtosList.add(downDto);	
	    	}
		}

	    List<Force> forcesList=forceRepo.findForceListByForceId(forcePersonalDto.getForceNo());
	    
		model.addAttribute("downDtosList",downDtosList);
		model.addAttribute("forceList", forcesList);
		
		
		return"po-template/assign-dealing-hand-to-po-board";
	}

	
	
	@GetMapping(path = "/view-dealing-hand-list-po")
	public String viewDealingHandListPo(Model model, HttpSession httpSession) {

		String boardId = (String) httpSession.getAttribute("boardId");
		logger.info("BoardId>>>>>>>>>>>>>>>" + boardId);
		List<DealingHandDto> dealingHandList = amaDealingHandService.getDealingHandByBoardId(boardId);
		model.addAttribute("dealingHandList", dealingHandList);
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));
		return "po-template/view-dealing-hand-list";
	}

	
	@RequestMapping(value = "/assign-dealing-hand-to-board-po", method = RequestMethod.POST)
	public String saveDealingHandForBoardPO(Model model, @RequestParam("roleIdIdDealingHand") String roleIdIdDealingHand,
			@RequestParam("boardIdDealingHand") String boardIdDealingHand,
			@RequestParam("forcePersonalIdAssignRole") String forcePersonalIdAssignRole,

			@RequestParam("file") MultipartFile[] files, @RequestParam("orderNumber") String orderNumber,
			@RequestParam("name_irlaNo") String Name_Irla_No, @RequestParam("designation") String designation,
			@RequestParam("department") String department,
			@RequestParam("orderDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate,
			@RequestParam("remark") String remark, HttpSession session, HttpServletRequest request) {

		logger.info("forcePersonalIdAssignRole" + forcePersonalIdAssignRole);
		logger.info("roleIdIdDealingHand" + roleIdIdDealingHand);
		logger.info("boardIdDealingHand" + boardIdDealingHand);
		// String message = null;

		String transactionalId = RandomCodeGenerator.getTransactionalIdUserRole();
		String forcePersonalIdLogin = (String) session.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		FileUploadDto fileUploadDto = new FileUploadDto();
		for (MultipartFile file : files) {

			fileUploadDto.setFileName(file.getOriginalFilename());
			fileUploadDto.setFileType(file.getContentType());
			try {
				fileUploadDto.setFileContent(file.getBytes());
			} catch (IOException e) {
				
				e.printStackTrace();
			}

		}
		fileUploadDto.setDepartment(Integer.parseInt(department));
		fileUploadDto.setDesignation(designation);
		fileUploadDto.setName_IrlaNo(Name_Irla_No);
		fileUploadDto.setOrderNo(orderNumber);

		fileUploadDto.setOrderDate(orderDate);
		fileUploadDto.setRemark(remark);

		Optional<ForcePersonnel> forcePersonal = forcePersonnelRepository.getByForcePersonnelId(forcePersonalIdLogin);
		ForcePersonnel forcePersonal2 = new ForcePersonnel();
		if (!forcePersonal.isEmpty()) {
			forcePersonal2 = forcePersonal.get();
		}
		UserRoleMapDto userRoleMapDto = new UserRoleMapDto();

		userRoleMapDto.setMappedBy(forcePersonalIdLogin);
		userRoleMapDto.setMappedByName(forcePersonal2.getName());
		userRoleMapDto.setMappedOn(Calendar.getInstance().getTime());
		userRoleMapDto.setForcePersonalId(forcePersonalIdAssignRole);
		userRoleMapDto.setRoleId(roleIdIdDealingHand);
		userRoleMapDto.setStatus(
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));

		userRoleService.createDealingHandByBoardMember(userRoleMapDto, fileUploadDto, transactionalId,
				boardIdDealingHand, request);

		model.addAttribute("dealingHandList", amaDealingHandService.getDealingHandByBoardId(boardIdDealingHand));
		return "po-template/list-dealing-hand-to-board";

	}
	
	@RequestMapping(path = "/po-dashboard",method = RequestMethod.GET)
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
		Integer declarationCount=ttAppointmentAmeRepo.getAppointmentCountByBoardId(boardId);
		String pendingCountValue;
		if(declarationCount==null) {
			pendingCountValue="0";
		}else {
			pendingCountValue=String.valueOf(declarationCount);
		}
        
		session.setAttribute("forcepersonalId", forcePersonalId);
		session.setAttribute("roleName",roleName.trim().toUpperCase());
		model.addAttribute("declarationCountPo", pendingCountValue);
		model.addAttribute("boardId", boardId);
		model.addAttribute("forcepersonalId", forcePersonalId);
		model.addAttribute("rCode", rCode);
	
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalId));
           return"po-template/po-dashboard";
	
	}
	

}
