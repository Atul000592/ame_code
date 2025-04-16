package nic.ame.app.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.dto.MedicalMemberMappedDto;
import nic.ame.app.admin.dto.UserRoleMapDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.model.RefRole;
import nic.ame.app.admin.model.UnitModel;

import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.AdminBoardService;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.admin.serviceImpl.ForcePersonalSearchServiceImpl;
import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PersonnelOthersDto;
import nic.ame.app.master.dto.RoleStatusDto;
import nic.ame.app.master.dto.StatusDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.medical.service.RoleService;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.UserRoleRepo;

import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.RandomCodeGenerator;

@Controller
public class AdminFunctionalController {

	Logger logger = LogManager.getLogger(AdminFunctionalController.class);

	@Autowired
	private AdminBoardService adminBoardService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private ForceRepo refForcerepo;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RoleService roleService;


	@Autowired
	private UserRoleRepo userRoleRepo;

	
	@Autowired
	private MedicalBoardIndividualMappingService medicalBoardIndividualMappingService;

	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;

	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private RefForceService refForceService ;
	
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	ForcePersonalSearchServiceImpl forcePersonalSearchServiceImpl;
	
	@Autowired
	ForcePersonnelService forcePersonnelService;
	
	
	@Autowired
	private RankRepo rankRepo;
	
	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;

	@GetMapping("/total-board-created")
	public String getBoardDetailsByForcePersonlIdData(Model model, HttpSession session) {
		String loginForcePersonalId = (String) session.getAttribute("forcepersonalId");
		List<MedicalBoardMemberDto> medicalBoardList = medicalBoardMemberService
				.findBoardDetailsListByLoginForcePersonal(loginForcePersonalId);

		model.addAttribute("medicalBoardList", medicalBoardList);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));

		return "admin-template/view-board-list";
	}

	@GetMapping("/total-member-map-to-boards")
	public String getTotalMemberMapToBoards(HttpSession session, Model model) {
		String loginForcePersonalId = (String) session.getAttribute("forcepersonalId");
		
		List<MedicalMemberMappedDto> boardDetailDtosMappedMember = adminBoardService
				.getAllMemberMappedToBoardByCreatedByForcePersonalWithAppointmentStatus(loginForcePersonalId);
		
		List<MedicalMemberMappedDto> boardDetailDtosMappedMemberStatusFlow = adminBoardService
				.getAllMemberMappedToBoardByCreatedByForcePersonalWithApplicationStatusFlow(loginForcePersonalId);
		
		model.addAttribute("boardDetailDtosMappedMember", boardDetailDtosMappedMember);
		model.addAttribute("boardDetailDtosMappedMemberStatusFlow", boardDetailDtosMappedMemberStatusFlow);
		 
		
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));

		return "admin-template/total-member-map-to-boards";
	}

	@PostMapping("/get-board-member-data-by-boardId")
	public ResponseEntity<?> sendBoardDetailsAjaxCall(@RequestParam("boardId") String boardId) {
		// MedicalBoard medicalBoard = medicalBoardRepo.getByBoardId(boardId);
		System.out.println("=============" + boardId);

		List<MedicalBoardMemberDto> medicalBoardMemberDtoList = medicalBoardIndividualMappingService
				.getMedicalBoardById(boardId);
		MedicalBoardDetailDto boardDetailDtos = medicalBoardMemberService.medicalBoardDetailByBoardId(boardId);

		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("medicalBoardMemberDtoList", medicalBoardMemberDtoList);
		dataMap.put("boardDetailDtos", boardDetailDtos);
		MedicalBoardMemberDto createdByDetails = medicalBoardMemberService
				.findCreatedByDetailsByBoardId(boardId.trim());
		StringBuilder createdBy = new StringBuilder();
		createdBy.append(createdByDetails.getIrlaNumber() + " || ").append(createdByDetails.getName() + " || ")
				.append(createdByDetails.getForceName() + " || ").append(createdByDetails.getUnitName()).trimToSize();
		dataMap.put("createdBy", createdBy);
		return ResponseEntity.status(HttpStatus.OK).body(dataMap);

	}

	// ===============================assign new role
	// bda===========================//

	@GetMapping("/assign-new-role-to-bda")
	public String assignNewRoleToUser(Model model, HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();
		if (httpSession.equals(null)) {
			logger.info("invalid session");
		}

		if (httpSession.equals(null)) {
			logger.info("invalid session");
		}
		String message = null;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
		if (inputFlashMap != null) {
			message = (String) inputFlashMap.get("message");

		}

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));

		List<UserRoleDto> userRoleDtos = userRoleService.getListOfUserWithRoles(loginForcePersonalId);
		model.addAttribute("userRoleDtosList", userRoleDtos);

		int rCode = (int) httpSession.getAttribute("rCode");
		List<RefRole> MasterRoleList = roleService.getMasterRoleList(loginForcePersonalId, rCode);

		List<Force> forceList = refForcerepo.findAll();
		model.addAttribute("forceList", forceList);
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		model.addAttribute("message", message);
		return "admin-template/assign-new-role-to-bda";
	}

//===================================================//
	@PostMapping("get-personal-details-for-role-assignment-bda")
	public ResponseEntity<?> getpersonaldetailsforroleassignmentbda(
			@RequestParam("forcePersonalId") String forcePersonalId, Model model, HttpSession httpSession) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		int rCode = (int) httpSession.getAttribute("rCode");

		List<RefRole> MasterRoleList = roleService.getMasterRolePermissionList(loginForcePersonalId, rCode);
		List<RefRole> permissionRoleList = roleService.getPermissionRoleList(loginForcePersonalId, rCode);
		List<Force> departmentList = refForcerepo.findAll();

		Optional<ForcePersonnelDto> forcePersonnelDto = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);

		Map<String, Object> map = new HashMap<>();
		/*
		 * System.out.println("?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		 * ForcePersonalDto forcePersonalDto=new ForcePersonalDto();
		 * forcePersonalDto=forcePersonalService.getForcePersonalDetails(forcePersonalId
		 * ); map.put("forcePersonalDto", forcePersonalDto);
		 * map.put("roleListDto",userRoleService.getUserRoleHistory(forcePersonalId));
		 */
		map.put("forcePersonalId", forcePersonalId);
		map.put("masterRoleList", MasterRoleList);
		map.put("permissionRoleList", permissionRoleList);
		map.put("forcePersonalDto", forcePersonnelDto.get());
		map.put("departmentList", departmentList);
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

	// ======================================================Assign new Role By
	// bda===============================================================//

	@PostMapping("/assign-new-role-by-bda-user")
	public String assignNewRoleSuperUserController(@RequestParam("roleId") String roleId,
			@RequestParam("forcePersonalIdAssignRole") String forcePersonalIdAssignRole,
			@RequestParam("file") MultipartFile[] files, HttpSession httpSession, RedirectAttributes redirectAttributes,
			@RequestParam("permissionRoleId") String permissionRoleId, @RequestParam("orderNumbar") String orderNumbar,
			@RequestParam("orderDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate,
			@RequestParam("name_irlaNo") String name_irlaNo, @RequestParam("department") String department,
			@RequestParam("designation") String designation, @RequestParam("remark") String remark) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		logger.info(
				"roleId>>>>>>>>>>>>>>" + roleId + ">>>>>>>>>forcePersonalIdAssignRole>>>>" + forcePersonalIdAssignRole);

		int roleAssignCount = userRoleRepo.getRoleExistStatus(forcePersonalIdAssignRole.trim(), roleId.trim());

		if (roleAssignCount > 0) {
			redirectAttributes.addFlashAttribute("message", "Same User already have this Role");
			return "redirect:assign-new-role-to-bda";
		}

		if (Integer.parseInt(roleId) == 7) {
			int count = userRoleService.checkRoleCount(roleId);
			if (count > 2) {
				redirectAttributes.addFlashAttribute("message",
						"Max Role Assign Count Exceeded the Limit(Max limit 2) for SuperUser");
				return "redirect:assign-new-role-to-bda";
			}
		}

		if (Integer.parseInt(roleId) == 9) {
			int count = userRoleService.checkRoleCount(roleId);
			if (count > 2) {
				redirectAttributes.addFlashAttribute("message",
						"Max Role Assign Count Exceeded the Limit(Max limit 2) for CAPF ADMIN");
				return "redirect:assign-new-role-to-bda";
			}
		}

		FileUploadDto fileUploadDto = new FileUploadDto();
		for (MultipartFile file : files) {

			fileUploadDto.setFileName(file.getOriginalFilename());
			fileUploadDto.setFileType(file.getContentType());
			try {
				fileUploadDto.setFileContent(file.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		fileUploadDto.setDepartment(Integer.parseInt(department));
		fileUploadDto.setDesignation(designation);
		fileUploadDto.setName_IrlaNo(name_irlaNo);
		fileUploadDto.setOrderNo(orderNumbar);
		fileUploadDto.setPermissionToAssignRole(permissionRoleId.trim());
		fileUploadDto.setOrderDate(orderDate);
		fileUploadDto.setRemark(remark);

		Optional<ForcePersonnel> forcePersonal = forcePersonnelRepository.getByForcePersonnelId(loginForcePersonalId);
		ForcePersonnel forcePersonal2 = new ForcePersonnel();
		if (!forcePersonal.isEmpty()) {
			forcePersonal2 = forcePersonal.get();
		}
		UserRoleMapDto userRoleMapDto = new UserRoleMapDto();
		String transationalId = RandomCodeGenerator.getTransactionalIdUserRole();
		userRoleMapDto.setMappedBy(forcePersonal2.getForcePersonalId());
		userRoleMapDto.setMappedByName(forcePersonal2.getName());
		userRoleMapDto.setMappedOn(Calendar.getInstance().getTime());
		userRoleMapDto.setForcePersonalId(forcePersonalIdAssignRole);
		userRoleMapDto.setRoleId(roleId);
		userRoleMapDto.setStatus(
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)));

		userRoleService.createNewUserRoleBySuperAdmin(userRoleMapDto, fileUploadDto, transationalId);
		redirectAttributes.addFlashAttribute("message", "New Role Has been Assign to User");

		return "redirect:assign-new-role-to-bda";
	}

	// ==============bda Show user=============//
	@GetMapping("/show-existing-user-role-bda-side")
	public String showExistingUserRoleBdaSide(Model model, HttpSession httpSession) {
		List<StatusDto> option = new ArrayList<>();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);

		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);

		option.add(new StatusDto(
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)),
				"Active"));
		option.add(new StatusDto(
				Integer.parseInt(
						ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_INACTIVE)),
				"InActive"));
		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("options", option);

		return "admin-template/list-of-user-bda-ad";
	}

//=================================edit=============================================//

	@GetMapping("/manage-existing-user-role-bda-side")
	public String manageExistingUserRolebdaSide(Model model, HttpSession httpSession) {
		List<StatusDto> option = new ArrayList<>();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		option.add(new StatusDto(
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)),
				"Active"));
		option.add(new StatusDto(
				Integer.parseInt(
						ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_INACTIVE)),
				"InActive"));
		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("options", option);

		return "admin-template/manage-existing-user-role-bda";
	}

	// ===============creating approving and forwarding
	// authority========================//

	public String createApprovingAndForwardingAuthority(HttpServletRequest httpServletRequest, Model model) {

		HttpSession session = httpServletRequest.getSession();
		if (session == null) {
			logger.info("invalidating session>>>( createApprovingAndForwardingAuthority )");
			session.invalidate();

			model.addAttribute("message", "Invalid user or session expired......!");

		}
		String loginForcePersonal = (String) session.getAttribute("forcepersonalId");

		if (loginForcePersonal == null) {
			logger.info("invalidating session>>>( createApprovingAndForwardingAuthority--loginForcePersonal..null)");
			session.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");
		}

		return "admin-template/approving-forwarding-authority";
	}

	@RequestMapping(value = { "board-member-modification" }, method = RequestMethod.GET)
	public String medicalBoardModification(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();

		if (session == null) {
			logger.info("invalidating session>>>( medicalBoardModification )");
			session.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");

		}

		String loginForcePersonal = (String) session.getAttribute("forcepersonalId");

		if (loginForcePersonal == null) {
			logger.info("invalidating session>>>( medicalBoardModification--loginForcePersonal..null)");
			session.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");
		}

		String loginForceName = (String) session.getAttribute("loginForceName");
		String userType = (String) session.getAttribute("userType");
		List<MedicalBoardMemberDto> boardDetailsDtos = medicalBoardMemberService
				.findBoardDetailsListByLoginForcePersonal(loginForcePersonal);

		model.addAttribute("medicalBoardList", boardDetailsDtos);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonal));
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);

		return "admin-template/medical-board-modification";
	}

	/*
	 * @PostMapping(value = { "transfer-boardmember-role-by-boardId" }) public
	 * ResponseEntity<Map<String, Object>>
	 * transferBoardmemberRoleByBoardId(@RequestParam("boardId") String boardId,
	 * 
	 * @RequestParam("roleNameId") String roleNameId,
	 * 
	 * @RequestParam("boardMemberForcePersonalId") String
	 * boardMemberForcePersonalId, HttpSession session, Model model) {
	 * 
	 * Map<String, Long> reserveStatus =
	 * medicalBoardMemberService.findReserveByBoardIdAndRoleName(boardId,
	 * roleNameId);
	 * 
	 * Map<String, Object> map = new HashMap<>(); String loginForcePersonal =
	 * (String) session.getAttribute("forcepersonalId");
	 * 
	 * if (loginForcePersonal == null) { logger.
	 * info("invalidating session>>>( medicalBoardModification--loginForcePersonal..null)"
	 * ); session.invalidate(); model.addAttribute("message",
	 * "Invalid user or session expired......!"); map.put("code", 2); return new
	 * ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST); }
	 * 
	 * if (!reserveStatus.isEmpty()) { map.put("code", 1);
	 * List<MedicalBoardMemberDto> boardMemberDtos = medicalBoardMemberService
	 * .getlistOfReservePersonalByBoardIdAndAlternateMedicalRoleId(boardId,
	 * (String.valueOf(reserveStatus.get("roleCode"))));
	 * 
	 * List<MedicalBoardMemberDto> Assigner = boardMemberDtos.stream() .filter(s ->
	 * s.getForcePersonalId().equals(boardMemberForcePersonalId) && s.getStatus() ==
	 * 1) .collect(Collectors.toList()); if (Assigner.size() > 0) {
	 * List<MedicalBoardMemberDto> Assignee = boardMemberDtos.stream() .filter(s
	 * ->s.getStatus() == 0) .collect(Collectors.toList()); map.put("Assigner",
	 * Assigner); map.put("Assignee", Assignee);
	 * map.put("rcd",reserveStatus.get("roleCode"));
	 * map.put("rrcd",reserveStatus.get("reserveRoleCode"));
	 * map.put("assignerForcePersonal",boardMemberForcePersonalId);
	 * 
	 * return new ResponseEntity<Map<String, Object>>(map, HttpStatus.ACCEPTED); }
	 * else { map.put("code", 0); return new ResponseEntity<Map<String,
	 * Object>>(map, HttpStatus.BAD_REQUEST); }
	 * 
	 * } else { map.put("code", 0); return new ResponseEntity<Map<String,
	 * Object>>(map, HttpStatus.BAD_REQUEST); }
	 * 
	 * }
	 */
	
	
	//============transfer role to inactive member of the board ====================//
	@PostMapping(value = { "transfer-boardmember-role-by-boardId" }) 
	@Transactional
	public String transferBoardmemberRoleToInActiveMember(
			@RequestParam("boardId") String boardId,
			@RequestParam(value =  "roleNameId",required = false) String roleNameId,
	        @RequestParam("boardMemberForcePersonalId") String boardMemberForcePersonalId,
	        @RequestParam("statusCodeId") int statusCodeId,
	        HttpSession session,Model model,RedirectAttributes redirectAttributes){
		
		
		  List<MedicalBoardMember> boardMembers= medicalBoardMemberRepo.findByBoardId(boardId);
		  
		  for (MedicalBoardMember medicalBoardMember : boardMembers) {
			  if(medicalBoardMember.getForcePersonalId().equalsIgnoreCase(boardMemberForcePersonalId)) {
				  if(statusCodeId==1) {
					  medicalBoardMember.setStatusCode(0);
					  medicalBoardMemberRepo.save(medicalBoardMember);
				  }else {
					  medicalBoardMember.setStatusCode(1);
					  medicalBoardMemberRepo.save(medicalBoardMember);
				  }
			
		}
		  }
		  redirectAttributes.addFlashAttribute("boardId",boardId);
		  redirectAttributes.addFlashAttribute("message", "User Role Status Change Transfer Completed...! To Other Member ......Message will disappear in 5 sec..");
		  return "redirect:/edit-board-details";

		}
		
		
		
		
		
	
	
	
	
	
	@PostMapping("transfer-role-to-boardmember-by-boardId")
	public ResponseEntity<?> transferRoleToBoardmemberByBoardId(@RequestBody List<Map<String, String>> data,HttpSession httpSession)
			throws JsonMappingException, JsonProcessingException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
	    MedicalBoardMemberDto medicalBoardMemberDto=new MedicalBoardMemberDto();
	    
		String loginForcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
		
		for (Map<String, String> entry : data) {
            // Iterate through each map entry
            for (Map.Entry<String, String> keyValue : entry.entrySet()) {
            	if(keyValue.getKey().contains("candidateId")) 
            		medicalBoardMemberDto.setForcePersonalId(keyValue.getValue());
            	if(keyValue.getKey().contains("boardId"))
            		medicalBoardMemberDto.setBoardId(keyValue.getValue());
            	if(keyValue.getKey().equals("rcd"))
            		medicalBoardMemberDto.setRoleName(keyValue.getValue());
            	if(keyValue.getKey().equals("transferForcePersonalId"))
            		medicalBoardMemberDto.setCreatedBy(keyValue.getValue());
            	if(keyValue.getKey().equals("remark"))
            		medicalBoardMemberDto.setRemark(keyValue.getValue());
                    System.out.println(keyValue.getKey() + ": " + keyValue.getValue());
            }
        }
	
		medicalBoardMemberDto.setStatus(1);
        
         medicalBoardMemberService.updateTransferOFMedicalRoleByBoardMember(medicalBoardMemberDto);
		 map.put("code", 1);
		return new ResponseEntity<Map<String,Object>>(map,HttpStatus.ACCEPTED);
	}
	
	
	
	@RequestMapping(value =  "identity-doctor-for-force" ,method = RequestMethod.GET)
	public String designateDoctorForForceIntialPage(Model model,HttpServletRequest request) {
		
		
		HttpSession session = request.getSession();

		if (session == null) {
			logger.info("invalidating session>>>( medicalBoardModification )");
			session.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");

		}

		String loginForcePersonal = (String) session.getAttribute("forcepersonalId");

		if (loginForcePersonal == null) {
			logger.info("invalidating session>>>( medicalBoardModification--loginForcePersonal..null)");
			session.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");
		}
		
		Optional<ForcePersonnelDto> forcePerOptional= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(loginForcePersonal);
		
		if(forcePerOptional.isEmpty()) {
			logger.info("invalidating session>>>( medicalBoardModification--loginForcePersonal..null)");
			session.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");
		}
		
		ForcePersonnelDto forcePersonal=new ForcePersonnelDto();
		forcePersonal=forcePerOptional.get();
		
		List<UnitModel> unitList=unitRepository.getUnitDataByForceId(forcePersonal.getForceNo());
		
		int forceNumber=forcePersonal.getForceNo();
		String loginForceName = (String) session.getAttribute("loginForceName");
		String userType = (String) session.getAttribute("userType");
		
		List<MedicalBoardMemberDto> boardDetailsDtos = medicalBoardMemberService
				.findBoardDetailsListByLoginForcePersonal(loginForcePersonal);

		model.addAttribute("medicalBoardList", boardDetailsDtos);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonal));
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		model.addAttribute("unitList", unitList);
		model.addAttribute("forceNumber",forceNumber);
          int rCode=(int) session.getAttribute("rCode");
  		model.addAttribute("rCode",rCode);

          model.addAttribute("rCode", rCode);
		System.out.println("rcoed>>>>>>>>>>>"+rCode);
		if(rCode==11) {
			return "user-management-template/force-admin-user/designate-doctor-for-force";
		}
		return "admin-template/designate-doctor-for-force";
		
	}

	
	@RequestMapping(value = "doctor-list",method = RequestMethod.GET)
   public String designateDoctorList(Model model,HttpSession httpSession) {
		
	
		
		String loginForcePersonal = (String) httpSession.getAttribute("forcepersonalId");

		if (loginForcePersonal == null) {
			logger.info("invalidating session>>>( medicalBoardModification--loginForcePersonal..null)");
			httpSession.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");
		}
		 Optional<ForcePersonnelDto> forcePerOptional= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(loginForcePersonal);
			
			if(forcePerOptional.isEmpty()) {
				logger.info("invalidating session>>>( medicalBoardModification--loginForcePersonal..null)");
				httpSession.invalidate();
				model.addAttribute("message", "Invalid user or session expired......!");
			}
			List<ForcePersonnel> doctorsList;
			if(forcePerOptional.get().getAttachUnit()!=null)
			{
				doctorsList =forcePersonnelRepository.getDoctorsListByForceAndUnit
				(forcePerOptional.get().getForceNo(),forcePerOptional.get().getAttachUnit());
			}else {
				doctorsList =forcePersonnelRepository.getDoctorsListByForceAndUnit
						(forcePerOptional.get().getForceNo(),forcePerOptional.get().getUnit());
			}
		List<ForcePersonnelDto> listOfDoctors=new ArrayList<>();
		for (ForcePersonnel forcePersonnel : doctorsList) {
			ForcePersonnelDto forcePersonnelDto=new ForcePersonnelDto();
			forcePersonnelDto.setRank(rankRepo.findById(forcePersonnel.getRank()).get().getRankFullName());
			forcePersonnelDto.setName(forcePersonnel.getName());
			forcePersonnelDto.setUnitName(unitRepository.findByUnitId(forcePersonnel.getUnit()).get().getUnitFullName());
			forcePersonnelDto.setForceName(refForcerepo.getForceName(forcePersonnel.getForceNo()));
			if(forcePersonnel.getAttachUnit()!=null)
				forcePersonnelDto.setAttachUnitName(unitRepository.findByUnitId(forcePersonnel.getAttachUnit()).get().getUnitFullName());
			else
				forcePersonnelDto.setAttachUnitName("--");
			forcePersonnelDto.setForceId(forcePersonnel.getForceId());
			
			listOfDoctors.add(forcePersonnelDto);
		}
		
		
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonal));
		
	
		model.addAttribute("forcePersonalDoc",listOfDoctors);
		
		
		int rCode=(int) httpSession.getAttribute("rCode");
		 model.addAttribute("rCode", rCode);
		if(rCode==11) {
			return "user-management-template/force-admin-user/doctors-list-force-wise";
		}
		
		return "admin-template/doctors-list-force-wise";
		
	}
	@RequestMapping("modified-designated-doctor")
    public String modifiedDesignateDoctorForForce(Model model,HttpSession httpSession) {
		
		String loginForcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
		
		return "designate-doctor-for-force";
		
	}
	
	
	@RequestMapping( value = "get-force-personal-details-for-medical-cadre" ,method = RequestMethod.POST)
	public ResponseEntity<?> getForcePersonalDetailsForMedicalCadre(@RequestParam("id") String id,HttpSession session,Model model){
		
		
		logger.info(">>>>>>>>>>>>>>>>"+id);
        Optional<ForcePersonnelDto> forcePerOptional= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(id);
		
		if(forcePerOptional.isEmpty()) {
			logger.info("invalidating session>>>( medicalBoardModification--loginForcePersonal..null)");
			session.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");
		}
		
		String forceName;
		String unitName;
		
		ForcePersonnelDto forcePersonal=new ForcePersonnelDto();
		forcePersonal=forcePerOptional.get();
		
		
		Map<String,Object> responseMap=new HashMap<>();
		forceName=refForceService.getForceNameByForceId(forcePersonal.getForceNo());
		unitName=refForceService.getUnitNameByUnitId(forcePersonal.getForceNo(),forcePersonal.getUnit());
		responseMap.put("unitName", unitName);
		responseMap.put("forceName", forceName);
		responseMap.put("fp", forcePersonal);
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	
	
	
	
	
	@Transactional
	@RequestMapping( value = "assign-new-doctor-to-unit" ,method = RequestMethod.POST)
	public ResponseEntity<?> assignNewDoctorToUnit(@RequestParam("id") String id,HttpSession session,Model model){
		
		int statusCode = 0;
		
		logger.info(">>>>>>>>>>>Assign New Doctor>>>>>"+id);
        Optional<ForcePersonnelDto> forcePerOptional= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(id);
		
		if(forcePerOptional.isEmpty()) {
			logger.info("invalidating session>>>( medicalBoardModification--loginForcePersonal..null)");
			session.invalidate();
			model.addAttribute("message", "Invalid user or session expired......!");
		}
		
		forcePersonnelRepository.updateMedicalCadre(id);
		statusCode=1;
		return ResponseEntity.status(HttpStatus.OK).body(statusCode);
	}
	
	@PostMapping("get-force-personnel-list-for-role-assigning")
	public ResponseEntity<?> getForcePersonnelForRoleAssigning(@RequestBody Map<String, Object> data) {

		String forceNo = (String) data.get("department");

		String designation = (String) data.get("designation");

		

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();

		if (!forceNo.equals("99") && !forceNo.equals("8")) {
			List<ForcePersonnelDto> forcePersonnelList = forcePersonalSearchServiceImpl
					.getForcePersonnelListByForceNoAndDesignation(Integer.parseInt(forceNo), Integer.parseInt(designation));
			response.put("forcePersonnelList", forcePersonnelList);
			response.put("isValid", true);
			return ResponseEntity.ok(response);
		}

		else {

			List<PersonnelOthersDto> personnelOthersList = forcePersonalSearchServiceImpl
					.getPersonalOthersByForceNoAndDesignation(Integer.parseInt(forceNo), Integer.parseInt(designation));

			response.put("personnelOthersList", personnelOthersList);
			return ResponseEntity.ok(response);
		}

	}
	
}
