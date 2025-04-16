package nic.ame.app.user.management.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.catalina.valves.rewrite.InternalRewriteMap.UpperCase;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.admin.dto.UserRoleMapDto;
import nic.ame.app.admin.model.QueryData;
import nic.ame.app.admin.model.RefRole;
import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.RefRoleRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.ForcePersonalSearchService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.MedicalCheckUpListDto;
import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.RoleStatusDto;
import nic.ame.app.master.dto.StatusDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.medical.service.RoleService;
import nic.ame.app.master.model.AssignedPrivilege;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.TTOrderFileRoleCreation;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.master.model.UserRoleTemp;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.AssignedPrivilegeRepo;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.UserRoleRepo;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.MapUriToUserService;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.app.user.management.service.UserManagementService;
import nic.ame.app.user.service.UserService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.CheckAdminFlag;
import nic.ame.master.util.GetClientIP;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.RandomCodeGenerator;


@Controller
public class UserManagementFunctionalController {

	Logger logger=org.slf4j.LoggerFactory.getLogger(UserManagementFunctionalController.class);

	@Autowired
	private ForcePersonalSearchService forcePersonalSearchService;

	@Autowired
	private RefForceService refForceService;

	@Autowired
	private UserRoleRepo userRoleRepo;
	@Autowired
	private ForceRepo refForcerepo;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RefRoleRepo refRoleRepo;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private ForcePersonalService forcePersonalService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	@Autowired
	private AssignedPrivilegeRepo assignedPrivilegeRepo;

	@Autowired
	private MapUriToUserService mapUriToUserService;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private RankRepo rankRepo;
	

	



//******************************************************************************************************************************************************************//

	@RequestMapping(value = { "get-force-personal-by-unit-force-id"}, method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToAjax(@RequestParam String queryData, Model model, HttpSession httpSession) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();
		
		List<ForcePersonnel> list = forcePersonalSearchService
				.geAllForcePersonalByForceAndUnit(Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo());
		
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),
				myQueryData.getUnitNo().trim());
		for (ForcePersonnel forcePersonnel : list) {
			ForcePersonalResponeseAjax fPA = new ForcePersonalResponeseAjax();
			fPA.setForceName(forceName);
			fPA.setForcePersonalId(forcePersonnel.getForcePersonalId());
			fPA.setIrlaNumber(forcePersonnel.getForceId());
			fPA.setName(forcePersonnel.getName());
		    fPA.setDesignation(rankRepo.findById(forcePersonnel.getDesignation()).get().getRankFullName());
			fPA.setUnitName(unitName);
			if(forcePersonnel.getGender().equalsIgnoreCase("M")) 
				fPA.setGender("Male");
			
			else 
				fPA.setGender("Female");
			
			personalResponeseAjax.add(fPA);

		}
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = refForcerepo.findAll();
		int rCode = (int) httpSession.getAttribute("rCode");

		//List<RefRole> MasterRoleList = roleService.getMasterRolePermissionList(forcePersonalIdLogin, rCode);
		//List<RefRole> permissionRoleList = roleService.getPermissionRoleList(forcePersonalIdLogin, rCode);

		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		//map.put("masterRoleList", MasterRoleList);
		//map.put("permissionRoleList", permissionRoleList);

		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	
	//
	
	
	@RequestMapping(value = { "get-force-personal-by-unit-force-id-dh"}, method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToAssignDealingHand(@RequestParam String queryData, Model model, HttpSession httpSession) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();
		
		List<ForcePersonnel> list = forcePersonalSearchService
				.geAllForcePersonalByForceAndUnit(Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo());
		
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),
				myQueryData.getUnitNo().trim());
		for (ForcePersonnel forcePersonnel : list) {
			ForcePersonalResponeseAjax fPA = new ForcePersonalResponeseAjax();
			fPA.setForceName(forceName);
			fPA.setForcePersonalId(forcePersonnel.getForcePersonalId());
			fPA.setIrlaNumber(forcePersonnel.getForceId());
			fPA.setName(forcePersonnel.getName());
		    fPA.setDesignation(rankRepo.findById(forcePersonnel.getDesignation()).get().getRankFullName());
			fPA.setUnitName(unitName);
			if(forcePersonnel.getGender().equalsIgnoreCase("M")) 
				fPA.setGender("Male");
			
			else 
				fPA.setGender("Female");
			
			personalResponeseAjax.add(fPA);

		}
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = refForcerepo.findAll();
		//int rCode = (int) httpSession.getAttribute("rCode");

		//List<RefRole> MasterRoleList = roleService.getMasterRolePermissionList(forcePersonalIdLogin, rCode);
		//List<RefRole> permissionRoleList = roleService.getPermissionRoleList(forcePersonalIdLogin, rCode);

		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		//map.put("masterRoleList", MasterRoleList);
		//map.put("permissionRoleList", permissionRoleList);

		map.put("forceList", forceList);
		map.put("boardId", myQueryData.getBoardId());

		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//==============================================================================================================================//
	
	
	@RequestMapping(value = {"get-force-personal-by-attach-unit-force-id"}, method = RequestMethod.POST)
	public ResponseEntity<?> getForcePersonalByAttachUnitForceId(@RequestParam String queryData, Model model, HttpSession httpSession) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();
		List<ForcePersonnel> list = forcePersonalSearchService
				.geAllForcePersonalByForceAndAttachUnit(Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo());
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),myQueryData.getUnitNo().trim());
		
		for (ForcePersonnel forcePersonal : list) {
			ForcePersonalResponeseAjax fPA = new ForcePersonalResponeseAjax();
			fPA.setForceName(forceName);
			fPA.setForcePersonalId(forcePersonal.getForcePersonalId());
			fPA.setIrlaNumber(forcePersonal.getForceId());
			fPA.setName(forcePersonal.getName());
			fPA.setUnitName(unitName);
		    fPA.setDesignation(rankRepo.findById(forcePersonal.getDesignation()).get().getRankFullName());
			fPA.setAttachUnit(refForceService.getUnitNameByUnitId(forcePersonal.getForceNo(),forcePersonal.getAttachUnit()));
			if(forcePersonal.getGender().equalsIgnoreCase("M"))
				fPA.setGender("Male");
			else
				fPA.setGender("Female");

				
			personalResponeseAjax.add(fPA);

		}
		String forcePersonalIdLoginString = (String) httpSession.getAttribute("forcepersonalId");
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = refForcerepo.findAll();
		int rCode = (int) httpSession.getAttribute("rCode");

		//List<RefRole> MasterRoleList = roleService.getMasterRolePermissionList(forcePersonalIdLogin, rCode);
	//	List<RefRole> permissionRoleList = roleService.getPermissionRoleList(forcePersonalIdLogin, rCode);

		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		//map.put("masterRoleList", MasterRoleList);
		//map.put("permissionRoleList", permissionRoleList);

		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	
	
	// ******************************************************************************************************************************************************************//

	@PostMapping(value = { "/manage-existing-user-role" })
	public String manageExistingUserRole(Model model, @RequestParam("forcepersonalId") String forcePersonalId,
			HttpSession httpSession) {

		int rCode = (int) httpSession.getAttribute("rCode");

		List<StatusDto> option = new ArrayList<>();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);

		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)),"Active"));
		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_INACTIVE)), "InActive"));
		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("options", option);
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		return "user-management-template/super-user/manage-existing-user-role";
	}

	// ******************************************************************************************************************************************************************//

	@GetMapping(value = { "/manage-existing-user-role-sup-ad" })
	public String manageExistingUserRoleSupSide(Model model, HttpSession httpSession) {
		List<StatusDto> option = new ArrayList<>();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);

		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "Active"));
		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "InActive"));
		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("options", option);
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		return "user-management-template/super-user/manage-existing-user-role";
	}

	/*
	 * @GetMapping(value = { "/show-existing-user-role-sup-ad" }) public String
	 * showExistingUserRoleSupSide(Model model, HttpSession httpSession) {
	 * List<StatusDto> option = new ArrayList<>(); String forcePersonalIdLogin =
	 * (String) httpSession.getAttribute("forcepersonalId");
	 * model.addAttribute("loginUserDetails",
	 * loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));
	 * 
	 * List<RoleStatusDto> dtos =
	 * roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);
	 * 
	 * option.add(new StatusDto(CommonConstant.USER_ROLE_STATUS_ACTIVE, "Active"));
	 * option.add(new StatusDto(CommonConstant.USER_ROLE_STATUS_INACTIVE,
	 * "InActive")); model.addAttribute("roleListDetails", dtos);
	 * model.addAttribute("options", option); String loginForceName = (String)
	 * httpSession.getAttribute("loginForceName"); String userType = (String)
	 * httpSession.getAttribute("userType"); // model.addAttribute("loginForceName",
	 * loginForceName); model.addAttribute("userType", userType); return
	 * "user-management-template/super-user/list-of-user-sup-ad"; }
	 */

	// ******************************************************************************************************************************************************************//

	@RequestMapping(value="/update-user-role-by-boardId",method = RequestMethod.POST)
	public String updateUserRoleByBoardId(@RequestParam("userRoleId") String userRole,
			@RequestParam("forcepersonalId") String forcePersonalId, @RequestParam("status") String status,

			HttpSession httpSession, Model model) {

		System.out.println(">>>>>>>>>>>>>>>>>" + status);
		UserRole role = userManagementService.getUserRoleByUserRoleId(Integer.parseInt(userRole.trim()));
		if (role.getUserRoleId() != 0) {

			role.setStatus(Integer.parseInt(status.trim()));
			userManagementService.updateUserRoleByUserRoleId(role);
		}
		List<StatusDto> option = new ArrayList<>();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);

		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "Active"));
		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_INACTIVE)), "InActive"));
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);

		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("options", option);

		return "user-management-template/super-user/manage-existing-user-role";
	}

	// ******************************************************************************************************************************************************************//
	// *************************************** update user Role Status to active or
	// inactive user***************************************//
	// ***************************************CAPF,Force Admin and Other
	// Admin***************************************//

	@RequestMapping(value="/update-user-role-by-forcePersonalId-frcpf-admin",method = RequestMethod.POST)
	public String updateUserRoleByForcePersonalIdFrcpfDdmin(@RequestParam("roleIdId") String roleIdId,
			@RequestParam("forcePersonalIdAssignRole") String forcePersonalId, @RequestParam("status") String status,
			HttpSession httpSession, Model model, @RequestParam("file") MultipartFile[] files,
			@RequestParam("orderNumber") String orderNumber, @RequestParam("name_irlaNo") String Name_Irla_No,
			@RequestParam("designation") String designation, @RequestParam("department") String department,
			@RequestParam("orderDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate,
			@RequestParam("remark") String remark) {

		String transactionalId = RandomCodeGenerator.getTransactionalIdUserRole();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));
		logger.info("STATUS CODE::::::::" + status);
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

		TTOrderFileRoleCreation ttUserRoleOrderFiles = new TTOrderFileRoleCreation();
		ttUserRoleOrderFiles.setRank(designation);
		ttUserRoleOrderFiles.setUploadOn(Calendar.getInstance().getTime());
		ttUserRoleOrderFiles.setUploadBy(forcePersonalIdLogin);
		ttUserRoleOrderFiles.setRank(remark);
		ttUserRoleOrderFiles.setOrderDate(orderDate);
		ttUserRoleOrderFiles.setTransactionalId(new BigInteger(transactionalId));

		userRoleService.updateUserRoleStatus(ttUserRoleOrderFiles, fileUploadDto, transactionalId, status, roleIdId);

		logger.info("ALL DATA SAVE.........");

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);
		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("message", "User Role Status Has been Updated");

		return "user-management-template/force-admin-user/manage-existing-user-role";
	}

	// ******************************************************************************************************************************************************************//

	@GetMapping(path = { "manage-all-existing-user-role", "manage-existing-user-role-FadorCAPF" })
	public String manageExistingUserRoleFadorCAPFSide(Model model, HttpSession httpSession) {

		String uri;

		List<StatusDto> option = new ArrayList<>();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);

		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "Active"));
		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_INACTIVE)), "InActive"));
		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("options", option);

		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		int rCode = (int) httpSession.getAttribute("rCode");

		String userRoleName = refRoleRepo.findByRoleId(String.valueOf(rCode));

		String userType = refRoleRepo.findByRoleId(String.valueOf(rCode));
		uri = mapUriToUserService.getUriManageExistingRole(rCode);

		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		model.addAttribute("rCode",rCode);
		return uri;

	}

	// ******************************************************************************************************************************************************************//

	@RequestMapping(value="/show-all-assign-user-role",method = RequestMethod.GET)
	public String showAllAssignUserRole(Model model, HttpServletRequest request) {

		// =====get session from httpServletRequest=====//
		HttpSession httpSession = request.getSession();

		List<StatusDto> option = new ArrayList<>();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");

		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);

		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "Active"));
		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "InActive"));
		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("options", option);

		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		int rCode = (int) httpSession.getAttribute("rCode");
		String userRoleName = refRoleRepo.findByRoleId(String.valueOf(rCode));

		String userType = refRoleRepo.findByRoleId(String.valueOf(rCode));
		
		
		String uri = mapUriToUserService.getUriForViewUserRoleDetails(rCode);
		model.addAttribute("rCode",rCode);

		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		return uri;

	}

	// ******************************************************************************************************************************************************************//

	@PostMapping("/view-user-role-details")
	public String viewUserRoleDetails(Model model, @RequestParam("forcepersonalId") String forcePersonalId,
			HttpSession httpSession) {

		List<StatusDto> option = new ArrayList<>();
		String forcePersonalIdLogin = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(forcePersonalIdLogin));

		List<RoleStatusDto> dtos = roleService.getAllRoleByForcePersonalId(forcePersonalIdLogin);

		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "Active"));
		
		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "InActive"));
		
		model.addAttribute("roleListDetails", dtos);
		model.addAttribute("options", option);
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		int rCode = (int) httpSession.getAttribute("rCode");

		String userRoleName = refRoleRepo.findByRoleId(String.valueOf(rCode));
		String userType = refRoleRepo.findByRoleId(String.valueOf(rCode));
		String uri = mapUriToUserService.getUriForViewUserRoleDetails(rCode);

		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		return uri;
	}

	@PostMapping("get-user-role-history")
	public ResponseEntity<?> getUserHistory(@RequestParam("forcePersonalId") String forcePersonalId) {
		System.out.println(">>>>>>>>>>" + forcePersonalId);
		Map<String, Object> map = new HashMap<>();
		System.out.println("?>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		ForcePersonnelDto forcePersonalDto = new ForcePersonnelDto();
		forcePersonalDto = forcePersonalService.getForcePersonalDetails(forcePersonalId);
		map.put("forcePersonalDto", forcePersonalDto);
		map.put("roleListDto", userRoleService.getUserRoleHistory(forcePersonalId));
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

	// ******************************************************************************************************************************************************************//

	// =================new Role Assign to forcePersonal (Admin
	// Roles)=======================================//

	@PostMapping("get-personal-details-for-role-assignment")
	public ResponseEntity<?> getpersonaldetailsforroleassignment(
			@RequestParam("forcePersonalId") String forcePersonalId, Model model, HttpSession httpSession) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		int rCode = (int) httpSession.getAttribute("rCode");
		// List<RefRole> MasterRoleList=refRoleRepo.getAllRoleByHierarchy();

		logger.info("RCODE>>>>>>>>>>>>" + rCode);

		Optional<String> rCodeName = refRoleRepo.findRoleCodeByRoleId(String.valueOf(rCode));
		List<RefRole> masterRoleList = new ArrayList<>();
		List<RefRole> permissionRoleList = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		if (!rCodeName.isEmpty()) {

			if (rCodeName.get().equals(CommonConstant.ROLE_NAME_SP_ADMIN)) {
				masterRoleList = refRoleRepo.findAll();
				List<RefRole> masterRoleListFilter = masterRoleList.stream()
						.filter(s -> s.getRoleCode().equals(CommonConstant.ROLE_NAME_CAPF_AD)).collect(Collectors.toList());
				masterRoleListFilter.forEach(System.out::println);
				permissionRoleList = masterRoleList.stream()
						.filter(s -> s.getRoleCode().equals(CommonConstant.ROLE_NAME_FORCE_AD))
						.filter(s -> s.getRefAdminFlag() == CommonConstant.ROLE_ADMIN_FLAG_YES).collect(Collectors.toList());
				map.put("masterRoleList", masterRoleListFilter);
				map.put("refRoles", permissionRoleList);
				logger.info("SUPER AD ROLE......");

			} else {

				if (rCodeName.get().equals(CommonConstant.ROLE_NAME_CAPF_AD)) {
					masterRoleList = refRoleRepo.findAll();
					masterRoleList=masterRoleList.stream().filter(s->s.getRoleCode().equals(CommonConstant.ROLE_NAME_FORCE_AD))
							.collect(Collectors.toList());
					permissionRoleList=refRoleRepo.findRoleByAdminFlag(CommonConstant.ROLE_ADMIN_FLAG_YES);
					permissionRoleList=permissionRoleList.stream()
				    .filter(s->!s.getRoleCode().equals(CommonConstant.ROLE_NAME_CAPF_AD))
					.filter(s->!s.getRoleCode().equals(CommonConstant.ROLE_NAME_FORCE_AD)).
					collect(Collectors.toList());
                     logger.info("CAPF ADMIN ROLE......");
					map.put("masterRoleList", masterRoleList);
					map.put("refRoles", permissionRoleList);

				} else {
					Optional<String> transationalId = userRoleRepo.findTransactionalIdByRoleIdAndForcePersonalId(
							String.valueOf(rCode), loginForcePersonalId.trim(), CommonConstant.ROLE_ADMIN_FLAG_YES,CommonConstant.ACTIVE_FLAG_YES);
					;
					logger.info("TransactionalId>>>>>>>>>>>>>>>>" + transationalId.get());

					Optional<String> assignedPrivilege = assignedPrivilegeRepo
							.findAssignPrivilegeByTranstionalId(transationalId.get());
					
					if (!assignedPrivilege.isEmpty()) {

						String privilege = assignedPrivilege.get().trim();
						String[] str = privilege.split(",");

						for(String roleCode : str) {

							Optional<String> rCodeNameId = refRoleRepo.findRoleCodeByRoleId(roleCode);
							if (!rCodeNameId.isEmpty()) {
								masterRoleList.add(refRoleRepo.findAll().stream().filter(s->s.getRoleCode().equals(rCodeNameId.get())).findAny().get());
							}
						}

					}
					logger.info("Others ROLE......");
					map.put("masterRoleList", masterRoleList);
					map.put("refRoles", masterRoleList);
				}
			}

		}

		Optional<ForcePersonnelDto> forcePersonnelDto = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);

		List<StatusDto> option = new ArrayList<>();
		List<Force> departmentList = refForcerepo.findAll();
		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE)), "Active"));
		option.add(new StatusDto(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_INACTIVE)), "InActive"));
		model.addAttribute("options", option);

		map.put("forcePersonalId", forcePersonalId);
		map.put("forcePersonalDto", forcePersonnelDto.get());
		map.put("departmentList", departmentList);

		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

	@PostMapping("role-assign-file-upload-final")
	public String roleAssignFileUploadFinal(@RequestParam("transactionalId") String transactionalId, Model model,
			HttpSession httpSession) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>" + transactionalId);
		List<UserRoleDto> userRoleDtos = userRoleService
				.getListOfUserWithRolesTemp(new BigInteger(transactionalId.trim()));
		model.addAttribute("userRoleDtosList", userRoleDtos);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		return "user-management-template/force-admin-user/role-assign-file-upload-final";

	}

	// ******************************************************************************************************************************************************************//

	// ========================upload user role orderFile=======================//

	@PostMapping("upload-and-save-role-order-file")
	public String uploadAndSaveRoleOrderFile(@RequestParam("file") MultipartFile[] files,
			@RequestParam("orderNumber") String orderNumber, 
			@RequestParam("Name_Irla_No") String Name_Irla_No,
			@RequestParam("designation") String designation,
			@RequestParam("department") String department,
			@RequestParam("orderDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate,
			@RequestParam("remark") String remark,
			@RequestParam("transactionalId") String transactionalId,
			HttpSession httpSession, 
			RedirectAttributes redirectAttributes) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
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

		TTOrderFileRoleCreation ttUserRoleOrderFiles = new TTOrderFileRoleCreation();
		// ttUserRoleOrderFiles.setDepartment(Integer.parseInt(department));
		ttUserRoleOrderFiles.setRank(designation);
		ttUserRoleOrderFiles.setUploadOn(Calendar.getInstance().getTime());
		ttUserRoleOrderFiles.setUploadBy(loginForcePersonalId);
		ttUserRoleOrderFiles.setRank(remark);
		ttUserRoleOrderFiles.setOrderDate(orderDate);
		ttUserRoleOrderFiles.setTransactionalId(new BigInteger(transactionalId));

		List<UserRoleTemp> userRoleTemps = new ArrayList<>();
		redirectAttributes.addFlashAttribute("message", "New Role Has Been Assign to the User");
		userRoleTemps = userRoleService.getUserRoleByTransactionalId(new BigInteger(transactionalId));
		userRoleService.saveNewUserRoleWithFile(ttUserRoleOrderFiles, fileUploadDto, userRoleTemps);

		return "redirect:/assign-new-role-to-FadorCAPF";
	}

	// ****************************************************************************************************************************************************//
	           // *******************************************Assign-New-Role-By-Super-User**********************************************************//

	@RequestMapping(value="/assign-new-role-super-user",method = RequestMethod.POST)
	public String assignNewRoleSuperUserController(@RequestParam("roleId") String roleId,
			@RequestParam("forcePersonalIdAssignRole") String forcePersonalIdAssignRole,
			@RequestParam("file") MultipartFile[] files, 
			@RequestParam("permissionRoleId") String permissionRoleId,
			@RequestParam("orderNumbar") String orderNumbar,
			@RequestParam("orderDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate,
			@RequestParam("name_irlaNo") String name_irlaNo,
			@RequestParam("department") String department,
			@RequestParam("designation") String designation, 
			@RequestParam("remark") String remark,
			HttpSession httpSession, RedirectAttributes redirectAttributes) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		logger.info(
				"roleId>>>>>>>>>>>>>>" + roleId + ">>>>>>>>>forcePersonalIdAssignRole>>>>" + forcePersonalIdAssignRole);

		int roleAssignCount = userRoleRepo.getRoleExistStatus(forcePersonalIdAssignRole.trim(), roleId.trim());

		if (roleAssignCount > 0) {
			redirectAttributes.addFlashAttribute("message", "Same User already have this Role");
			return "redirect:assign-new-role-to-user";
		}
		List<RefRole> refRoleList = refRoleRepo.findAll();
		Optional<String> roleNameOptional = refRoleList.stream().filter(s -> s.getRoleId().equals(roleId.trim()))
				.map(s -> s.getRoleCode()).findAny();

		String roleName = null;
		if(!roleNameOptional.isEmpty()) {
			roleName=roleNameOptional.get();
		}
		
		if (roleName.equals(CommonConstant.ROLE_NAME_SP_ADMIN)) {
			int count = userRoleService.checkRoleCount(roleId);
			if (count > 2) {
				redirectAttributes.addFlashAttribute("message",
						"Max Role Assign Count Exceeded the Limit(Max limit 2) for SuperUser");
				return "redirect:assign-new-role-to-user";
			}
		}

		if (roleName.equals(CommonConstant.ROLE_NAME_CAPF_AD)) {
			int count = userRoleService.checkRoleCount(roleId);
			if (count > 2) {
				redirectAttributes.addFlashAttribute("message",
						"Max Role Assign Count Exceeded the Limit(Max limit 2) for CAPF ADMIN");
				return "redirect:assign-new-role-to-user";
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
		
		
		 int AFlagValue=CheckAdminFlag.checkAdminFlagStatus(roleName);
		
		fileUploadDto.setDepartment(Integer.parseInt(department));
		fileUploadDto.setDesignation(designation);
		fileUploadDto.setName_IrlaNo(name_irlaNo);
		fileUploadDto.setOrderNo(orderNumbar);
		fileUploadDto.setPermissionToAssignRole(permissionRoleId.trim());
		fileUploadDto.setOrderDate(orderDate);
		fileUploadDto.setRemark(remark);

		Optional<ForcePersonnelDto> forcePersonal = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(loginForcePersonalId);
		ForcePersonnelDto forcePersonal2 = new ForcePersonnelDto();
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
		userRoleMapDto.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE).trim()));
		userRoleMapDto.setPrivilege(permissionRoleId);
		userRoleMapDto.setRefAdminFlag(AFlagValue);
		

		userRoleService.createNewUserRoleBySuperAdmin(userRoleMapDto, fileUploadDto, transationalId);
		redirectAttributes.addFlashAttribute("message", "New Role Has been Assign to User");

		return "redirect:assign-new-role-to-user";
	}

	// ********************************************************************************************************************************************************//
	            // ****************************************View-Data-Logs-CAPF-ADMIN*********************************************//

	@RequestMapping(value="view-data-logs-fcpf",method = RequestMethod.GET)
	public String viewDataLogsFcpfAdmin(Model model, HttpSession httpSession) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		return "user-management-template/force-admin-user/view-data-logs-fcpf";
	}

//*******************************************************************************************************************************************************************//
	   // **************************************Assign New Role By CAPF And Force
	                                 // Admin***************************************//

	@RequestMapping(value="/assign-new-role-cpf-admin-user",method = RequestMethod.POST)
	public String assignNewRoleCAPFOtherAdminController(@RequestParam("roleId") String roleId,
			@RequestParam("forcePersonalIdAssignRole") String forcePersonalIdAssignRole,
			@RequestParam("file") MultipartFile[] files, HttpSession httpSession, RedirectAttributes redirectAttributes,
			@RequestParam("permissionRoleId") String permissionRoleId,
			@RequestParam("orderNumbar") String orderNumbar,
			@RequestParam("orderDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDate,
			@RequestParam("name_irlaNo") String name_irlaNo, 
			@RequestParam("department") String department,
			@RequestParam("designation") String designation,
			@RequestParam("remark") String remark,
			@RequestParam("permissionId") String permissionId) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		logger.info(
				"roleId>>>>>>>>>>>>>>" + roleId + ">>>>>>>>>forcePersonalIdAssignRole>>>>" + forcePersonalIdAssignRole);

		int roleAssignCount = userRoleRepo.getRoleExistStatus(forcePersonalIdAssignRole.trim(), roleId.trim());

		if (roleAssignCount > 0) {
			redirectAttributes.addFlashAttribute("message", "Same User already have this Role");
			return "redirect:assign-new-role-to-FadorCAPF";
		}
		if(designation.equalsIgnoreCase("0")) {
			redirectAttributes.addFlashAttribute("message", "No role assigned .......designation field was empty");
			return "redirect:assign-new-role-to-FadorCAPF";
		}
			if(!designation.equalsIgnoreCase("0") && department.equalsIgnoreCase("0")) {
				redirectAttributes.addFlashAttribute("message", "No role assigned .......Department field was empty");
				return "redirect:assign-new-role-to-FadorCAPF";
			}
		
	
		
		
		/*
		 * if (Integer.parseInt(roleId) == 7) { int count =
		 * userRoleService.checkRoleCount(roleId); if (count >= 2) {
		 * redirectAttributes.addFlashAttribute("message",
		 * "Max Role Assign Count Exceeded the Limit(Max limit 2) for SuperUser");
		 * return "redirect:assign-new-role-to-user"; } }
		 */
		List<RefRole> refRoleList = refRoleRepo.findAll();
		Optional<String> roleNameOptional = refRoleList.stream().filter(s -> s.getRoleId().equals(roleId.trim()))
				.map(s -> s.getRoleCode()).findAny();
		
		

		String roleName = null;
		if(!roleNameOptional.isEmpty()) {
			roleName=roleNameOptional.get();
		}
		
		
		 int AFlagValue=CheckAdminFlag.checkAdminFlagStatus(roleName);
		/*
		 * if (Integer.parseInt(roleId) == 9) { int count =
		 * userRoleService.checkRoleCount(roleId); if (count >= 2) {
		 * redirectAttributes.addFlashAttribute("message",
		 * "Max Role Assign Count Exceeded the Limit(Max limit 2) for CAPF ADMIN");
		 * return "redirect:assign-new-role-to-user"; } }
		 */

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

		Optional<ForcePersonnelDto> forcePersonal = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(loginForcePersonalId);
		ForcePersonnelDto forcePersonal2 = new ForcePersonnelDto();
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
		userRoleMapDto.setStatus(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.USER_ROLE_STATUS_ACTIVE).trim()));
		userRoleMapDto.setPrivilege(permissionId);
		userRoleMapDto.setRefAdminFlag(AFlagValue);

		userRoleService.createNewUserRoleBySuperAdmin(userRoleMapDto, fileUploadDto, transationalId);
		redirectAttributes.addFlashAttribute("message", "New Role Has been Assign to User");

		return "redirect:assign-new-role-to-FadorCAPF";
	}

	// ******************************************************************************************************************************************************************//
	// *******************************Get-Force-Personal-Details-For-Update-Role*************************************//

	@PostMapping("get-force-personal-details-for-update-role")
	public ResponseEntity<?> getForcePersonalDetailsForUpdateRole(
			@RequestParam("forcePersonalId") String forcePersonalId, @RequestParam("rCode") String rCode, Model model,
			HttpSession httpSession) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		ForcePersonnelDto forcePersonalDto = forcePersonalService.getForcePersonalDetails(forcePersonalId);
		 UserRole userRole=userRoleRepo.findByUserRoleId(Integer.parseInt(rCode));
         
		 
		Map<String, Object> map = new HashMap<>();
		map.put("forcePersonalId", forcePersonalId);
		map.put("rCode", rCode);
		map.put("forcePersonalDto", forcePersonalDto);
		map.put("statusCode",userRole.getStatus());
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

	@PostMapping("update-user-role-status")
	public ResponseEntity<?> updateUserRoleStatus(
			@RequestBody UserRoleDto data, HttpSession httpSession) {

		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		Map<String, Object> map = new HashMap<>();
		UserRole userRole = userManagementService.getUserRoleByUserRoleId(data.getUserRoleId());

		if (userRole.getUserRoleId() != data.getUserRoleId()) {
			map.put("code", 0);
			map.put("message", "Error occur while performing role status change operation..........!");

		} else {
           
			//=============changing Role Status ==========================//
			
			userRole.setStatus(data.getRoleStatus());
			userRole.setModifiedOn(Calendar.getInstance().getTime());
			userRole.setModifiedBy(loginForcePersonalId);
			userRole.setStatusChangeReason(data.getRemark());
			
			String result = userManagementService.updateUserRoleByUserRoleId(userRole);
			logger.info("Status Change Result: " + result+" SucessFully");
			
			map.put("code", 1);
			map.put("message", "role change has been successfully...........!");
			

		}

		return ResponseEntity.status(HttpStatus.OK).body(map);

	}
	
	
	
	
	//=======================create new controller ================================================//
	
	public String  createNewControllingOfficer(Model model, HttpServletRequest httpServletRequest) {
		
		
		return "";
	}

	// =======================create new controller
	// ================================================//
	@RequestMapping(path = "map-individual-to-controlling", method = RequestMethod.GET)
	public String createMapControllingOfficerToUnitOrIndividual(Model model, HttpSession httpSession,@RequestParam("controllingOfficerId") String controllingOfficerId) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		
		  int rCode = (int) httpSession.getAttribute("rCode");
	        
			logger.info("rCode>>>>>>>>>"+rCode+">>>>>View Controlling Data");
			
		String controllingOfficerForcePersonnelId=	forcePersonnelRepository.findByForceId(controllingOfficerId).get().getForcePersonalId();
		List<UserRoleDto> controllingUserRoleLis = userRoleService
				.getForcePersonalByControllingRoleByForcePersonnelId(loginForcePersonalId,controllingOfficerForcePersonnelId);
		model.addAttribute("controllingList", controllingUserRoleLis);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		List<Force> forceList = refForcerepo.findAll();
		model.addAttribute("forceList", forceList);

		return "user-management-template/force-admin-user/map-individual-to-controlling";
	}
  	
	
	
	@PostMapping("get-role-assigned-privilge-for-user")
	public String getRoleAssignedPrivilgeForUser(@RequestParam("transactionId") String transactionId,
			@RequestParam("candidateForcePersonnelId") String candidateForcePersonnelId ,Model model,HttpSession httpSession){
		int rCode = (int) httpSession.getAttribute("rCode");
		System.out.println(transactionId);
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		 Optional<String> assignedPrivilege = assignedPrivilegeRepo
					.findAssignPrivilegeByTranstionalId(transactionId.trim());
		     
		 List<MedicalCheckUpListDto> checkOptionsDtosList=new ArrayList<>();
			if (!assignedPrivilege.isEmpty()) {

				String privilege = assignedPrivilege.get().trim();
				String[] str = privilege.split(",");

			 	List<RefRole> refRoles=refRoleRepo.findAll();
			 	refRoles = refRoles.stream()
		                   .filter(s -> s.getRoleId().equals("10") || s.getRoleId().equals("11") || s.getRoleId().equals("5"))
		                   .collect(Collectors.toList());

				 for (RefRole refRole : refRoles) {
					  MedicalCheckUpListDto checkOptionsDto=new MedicalCheckUpListDto();
					  checkOptionsDto.setCode(refRole.getRoleId());
					  checkOptionsDto.setName(refRole.getRoleName().toUpperCase());
				      for (String roleCodeId : str) {
						if(refRole.getRoleId().equals(roleCodeId)) {
							  checkOptionsDto.setChecked(true);
							  break;

						}else {
							  checkOptionsDto.setChecked(false);

						}
					}
					  checkOptionsDtosList.add(checkOptionsDto);	}
				
				}else {
					
			 	List<RefRole> refRoles=refRoleRepo.findAll();
			 	refRoles = refRoles.stream()
		                   .filter(s -> s.getRoleId().equals("10") || s.getRoleId().equals("11") || s.getRoleId().equals("5"))
		                   .collect(Collectors.toList());

				 for (RefRole refRole : refRoles) {
					  MedicalCheckUpListDto checkOptionsDto=new MedicalCheckUpListDto();
					  checkOptionsDto.setCode(refRole.getRoleId());
					  checkOptionsDto.setName(refRole.getRoleName().toUpperCase());
					  checkOptionsDto.setChecked(false);
					  checkOptionsDtosList.add(checkOptionsDto);	}

			 	}
			Optional<ForcePersonnelDto> optional= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(candidateForcePersonnelId);
			ForcePersonnelDto user=optional.get();
			String forceName=refForceService.getForceNameByForceId(user.getForceNo());
			String unitName=refForceService.getUnitNameByUnitId(user.getForceNo(),user.getUnit());
		    
			model.addAttribute("transactionId",transactionId);
		    model.addAttribute("privilages", checkOptionsDtosList);
		    model.addAttribute("user",user);
		    model.addAttribute("forceName",forceName);
		    model.addAttribute("unitName", unitName);
			model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
			model.addAttribute("rCode",rCode);
			String userType = refRoleRepo.findByRoleId(String.valueOf(rCode));
			model.addAttribute("userType", userType);

			
		    return "user-management-template/force-admin-user/modifiy-assign-privilages";

	}
	
	@PostMapping("/edit-role-assign-privilage")
	public ResponseEntity<?> editRoleAssignPrivilage(@RequestParam("transactionId")String transactionId,
			@RequestParam("ch1") String c1,
			@RequestParam("ch2") String c2,
			@RequestParam("ch3") String c3,HttpServletRequest httpRequest ) {
		
		StringBuilder builder=new StringBuilder();
		if(!c1.equals("0"))
			builder.append(c1);
		if(!c2.equals("0"))
			builder.append(","+c2);
		if(!c3.equals("0"))
			builder.append(","+c3);
		
           AssignedPrivilege assignedPrivilege=assignedPrivilegeRepo.findByTransactionalId(transactionId);	
           assignedPrivilege.setCanAssign(builder.toString());
           assignedPrivilege.setClientIpAddress(GetIpAddressClient.getIpAddressFromHeaderClient(httpRequest));
           assignedPrivilege.setCreatedOn(new Date());
          AssignedPrivilege assignedPrivilegeSaved=assignedPrivilegeRepo.save(assignedPrivilege);
		   if(assignedPrivilegeSaved.getTransactionalId().equals(transactionId))
			   return  ResponseEntity.status(HttpStatus.OK).body("Success");
		   else
			   return  ResponseEntity.status(HttpStatus.OK).body("Success");
		
	}
	
		@RequestMapping(path = "list-of-mapped-forcepersonal-to-reporting-officer", method = RequestMethod.POST)
	public String listMapControllingOfficerToUnitOrIndividual(Model model, HttpSession httpSession,
	@RequestParam("reportinOfficerId") String reportingOfficerId ){
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		  int rCode = (int) httpSession.getAttribute("rCode");
	        
			logger.info("rCode>>>>>>>>>"+rCode+">>>>>View Controlling Data");
		List<UserRoleDto> controllingUserRoleLis = userRoleService.getForcePersonalByControllingRole(loginForcePersonalId);
		
		model.addAttribute("controllingList", controllingUserRoleLis);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		
	    List<ForcePersonnelDto > list=	this.userManagementService.list(reportingOfficerId);
		List<Force> forceList = refForcerepo.findAll();
		model.addAttribute("list", list);
		model.addAttribute("forceList", forceList);
		return "user-management-template/force-admin-user/list-of-mapped-forcepersonal-to-reporting-officer";
	}
	
	
	@RequestMapping(path = "/get-force-personal-by-unit", method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToAjax(@RequestParam String queryData, Model model) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();

		List<ForcePersonnel> list = forcePersonalSearchService.getMappingListToboardForcePersonalForNonMedic(
				Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo());
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),
				myQueryData.getUnitNo().trim());
		for (ForcePersonnel forcePersonal : list) {
			ForcePersonalResponeseAjax fPA = new ForcePersonalResponeseAjax();
			fPA.setForceName(forceName);
			fPA.setForcePersonalId(forcePersonal.getForcePersonalId());
			fPA.setIrlaNumber(forcePersonal.getForceId());
			fPA.setName(forcePersonal.getName());
		//	fPA.setDesignation(forcePersonal.getDesignation());
			fPA.setUnitName(unitName);
			personalResponeseAjax.add(fPA);

		}
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = refForcerepo.findAll();
		/*
		 * model.addAttribute("personalResponeseAjax", personalResponeseAjax);
		 * model.addAttribute("forceList", forceList);
		 * model.addAttribute("medicalBoardsList", medicalBoardsList);
		 * model.addAttribute("forceNo", myQueryData); model.addAttribute("unitId",
		 * unitNo);
		 */
		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	
	@GetMapping("get-map-individual-to-reporting-list")
	public String getMapToIndividualToReportingList(HttpSession httpSession,Model model) {
		
		
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		
		 //========get Role Code==========//
        int rCode = (int) httpSession.getAttribute("rCode");
        
		logger.info("rCode>>>>>>>>>"+rCode+">>>>>View Controlling Data");
		
        List<UserRoleDto> controllingUserRoleList = userRoleService.getForcePersonalByControllingRole(loginForcePersonalId);
        
       
        
		model.addAttribute("reportingOfficerList", controllingUserRoleList);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		if(rCode!=5)
			return "user-management-template/force-admin-user/get-mapped-individual-list-to-controlling-officer";
		else
			return "admin-template/get-mapped-individual-list-to-controlling-officer";

			
	}

	@PostMapping(value = "map-individual-to-controlling-officer")
	public ResponseEntity<?> mappedIndividualsToControlling(Model model, HttpSession session,HttpServletRequest httpServletRequest,
	@RequestBody List<String> data){
		try {
			String forcepersonalId = (String) session.getAttribute("forcepersonalId");
			int size = data.size();
			String ControllingId = data.get(size - 1);
			data.remove(size - 1);
			 boolean b=this.userManagementService.saveIndividualToReporting(data, ControllingId, session, httpServletRequest);
			 if(b)
				 return  ResponseEntity.status(HttpStatus.OK).body(1);
			 else
				 return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch(Exception e){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@RequestMapping(path = "/get-force-personal-by-unit-for-reporting-officer", method = RequestMethod.POST)
	public ResponseEntity<?> getForcePersonnelByUnitForReportingOfficer(@RequestParam String queryData, Model model) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();

		List<ForcePersonnel> list = forcePersonalSearchService.getForcePersonelForMappingToReportingOfficer(
				Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo());
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),
				myQueryData.getUnitNo().trim());
		for (ForcePersonnel forcePersonal : list) {
			ForcePersonalResponeseAjax fPA = new ForcePersonalResponeseAjax();
			fPA.setForceName(forceName);
			fPA.setForcePersonalId(forcePersonal.getForcePersonalId());
			fPA.setIrlaNumber(forcePersonal.getForceId());
			fPA.setName(forcePersonal.getName());
			fPA.setDesignation(rankRepo.findById(forcePersonal.getDesignation()).get().getRankFullName());
			fPA.setUnitName(unitName);
			if(forcePersonal.getGender().contentEquals("M"))
				fPA.setGender("Male");
			else
				fPA.setGender("Female");
			personalResponeseAjax.add(fPA);

		}
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = refForcerepo.findAll();
		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}



	@RequestMapping(path = "/get-force-personal-by-attach-unit-for-reporting-officer", method = RequestMethod.POST)
	public ResponseEntity<?> getForcePersonnelByAttachUnitForReportingOfficer(@RequestParam String queryData, Model model) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();

		List<ForcePersonnel> list = forcePersonalSearchService.getForcePersonelForMappingToReportingOfficerByAtachUnit(
				Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo());
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),
				myQueryData.getUnitNo().trim());
		for (ForcePersonnel forcePersonal : list) {
			ForcePersonalResponeseAjax fPA = new ForcePersonalResponeseAjax();
			fPA.setForceName(forceName);
			fPA.setForcePersonalId(forcePersonal.getForcePersonalId());
			fPA.setIrlaNumber(forcePersonal.getForceId());
			fPA.setName(forcePersonal.getName());
			fPA.setDesignation(rankRepo.findById(forcePersonal.getDesignation()).get().getRankFullName());
			if(forcePersonal.getGender().equalsIgnoreCase("M"))
				fPA.setGender("Male");
			else
				fPA.setGender("Female");
				
			
			fPA.setUnitName(unitName);
			personalResponeseAjax.add(fPA);

		}
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = refForcerepo.findAll();
		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}


   @PostMapping("create-a-force-personnel-name")
   public ResponseEntity<?> createForcePersonnelName(@RequestParam("forceNo") String forceNo,@RequestParam("rankCode")String rankCode){
      
	  List<DropDownDto> nameList=new ArrayList<>(); 
	  
	  userManagementService.getNameDropDownByForceAndRank(forceNo,rankCode);
	  
	   Map<String ,Object> map=new HashMap<>();
	   map.put("nameList", nameList);
	   return ResponseEntity.status(HttpStatus.OK).body(map);
	   
   }


	@RequestMapping(value = {"get-force-personal-by-attach-unit-force-id-dh"}, method = RequestMethod.POST)
	public ResponseEntity<?> getForcePersonalByAttachUnitForceIddh(@RequestParam String queryData, Model model, HttpSession httpSession) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();
		List<ForcePersonnel> list = forcePersonalSearchService
				.geAllForcePersonalByForceAndAttachUnit(Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo());
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),myQueryData.getUnitNo().trim());
		
		for (ForcePersonnel forcePersonal : list) {
			ForcePersonalResponeseAjax fPA = new ForcePersonalResponeseAjax();
			fPA.setForceName(forceName);
			fPA.setForcePersonalId(forcePersonal.getForcePersonalId());
			fPA.setIrlaNumber(forcePersonal.getForceId());
			fPA.setName(forcePersonal.getName());
			fPA.setUnitName(unitName);
		    fPA.setDesignation(rankRepo.findById(forcePersonal.getDesignation()).get().getRankFullName());
			fPA.setAttachUnit(refForceService.getUnitNameByUnitId(forcePersonal.getForceNo(),forcePersonal.getAttachUnit()));
			if(forcePersonal.getGender().equalsIgnoreCase("M"))
				fPA.setGender("Male");
			else
				fPA.setGender("Female");

				
			personalResponeseAjax.add(fPA);

		}
		String forcePersonalIdLoginString = (String) httpSession.getAttribute("forcepersonalId");
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = refForcerepo.findAll();
		//int rCode = (int) httpSession.getAttribute("rCode");

		//List<RefRole> MasterRoleList = roleService.getMasterRolePermissionList(forcePersonalIdLogin, rCode);
	//	List<RefRole> permissionRoleList = roleService.getPermissionRoleList(forcePersonalIdLogin, rCode);

		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		//map.put("masterRoleList", MasterRoleList);
		//map.put("permissionRoleList", permissionRoleList);

		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	
	
}
