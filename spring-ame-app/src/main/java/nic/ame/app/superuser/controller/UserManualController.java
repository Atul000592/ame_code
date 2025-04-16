package nic.ame.app.superuser.controller;

import nic.ame.app.master.dto.FileUploadDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.model.BloodSugarPP;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.service.InvestigationMasterService;
import nic.ame.app.master.medical.service.RoleService;
import nic.ame.app.master.model.FeedbackForm;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.superuser.dto.UserRoleDto;
import nic.ame.app.superuser.entity.UserManualEntity;
import nic.ame.app.superuser.repository.UserManualEntityRepository;
import nic.ame.app.user.management.controller.UserMamagementDashBoardController;
import nic.ame.constant.CommonConstant;
import nic.ame.constant.RegexValidation;
import nic.ame.master.util.CreateFileDirectory;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.UserDateUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserManualController {

	Logger logger = LogManager.getLogger(UserMamagementDashBoardController.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;

	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private InvestigationMasterService investigationMasterService;
	
	@Autowired
	private UserManualEntityRepository userManualEntityRepository;
	
	@Autowired
	private ForcePersonnelService forcePersonnelService;


	@GetMapping("upload-form")
	public String showForm(Model model, HttpSession httpSession) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		System.out.println("Force Personal ID from Session: " + loginForcePersonalId);

		ForcePersonnelDto userDetails = loginUserDetails.getLoginUserDetails(loginForcePersonalId);
		System.out.println("User Details Retrieved: " + userDetails);
		model.addAttribute("loginUserDetails",userDetails);
		if (userDetails != null) {
			model.addAttribute("loginUserDetails",loginUserDetails);
			// Fetch roles from the database
			List<UserRoleDto> userRoleDtoList = roleService.getListOfRoles();

			// Add the roles to the model
			model.addAttribute("userRoleDtoList", userRoleDtoList);

			return "user-management-template/super-user/form";
		} else {
			model.addAttribute("error", "User details not found");
		}

		return "base-template/login-page";
	}

	@PostMapping("submitmanual")
	public String submitForm(@RequestParam("name") String name, @RequestParam("pdfRole") Long pdfRole,
			@RequestParam("document") MultipartFile document, Model model) {
		// Handle file upload logic here
		if (!document.isEmpty()) {
			model.addAttribute("message", "File uploaded successfully!");
		} else {
			model.addAttribute("message", "Please upload a file.");
		}

		return "user-management-template/super-user/form";
	}

	@PostMapping("validate-and-save-user-manual")
	@ResponseBody
	@Transactional
	public ResponseEntity<?> validateAndSaveUserManual(@RequestParam("jsonData") String jsonData,
			@RequestParam("file") MultipartFile file, HttpSession httpSession, HttpServletRequest request) {

		// String specialCharacterRegex = ".*[!@#$%^&*(),.?:{}|<>].*";
		String specialCharacterRegex = RegexValidation.REGEX_ALPHANUMERIC;
		String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();

		UserManualEntity userManualEntity = new UserManualEntity();

		
		  
		List<Map<String, String>> list = investigationMasterService.convertJsonToList(jsonData);
		for (Map<String, String> map : list) {

			for (Map.Entry<String, String> entry : map.entrySet()) {

				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equalsIgnoreCase("roleId")) {
					userManualEntity.setRoleId(Integer.parseInt(value));
				}

				if (key.equalsIgnoreCase("roleName")) {
					userManualEntity.setRoleName(value);
				}
				/*
				 * if (key.equalsIgnoreCase("roleCode")) { userManualEntity.setRoleCode(value);
				 * }
				 */
				logger.info("key>>>>>>>" + key + ">>>>Value>>>>" + value);

			}

		}

		try {
			/*
			 * ObjectMapper objectMapper = new ObjectMapper(); UserManualEntity
			 * userManualEntity = objectMapper.convertValue(data.get("jsonData"),
			 * UserManualEntity.class);
			 */	if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {

				FileUploadDto fileUploadDto = new FileUploadDto();
				fileUploadDto.setFileContent(file.getBytes());
				fileUploadDto.setFileName(file.getOriginalFilename());
				fileUploadDto.setFileSize(String.valueOf(file.getSize()));
				fileUploadDto.setFileType(file.getContentType());

				int year = UserDateUtil.getYear(new Date());

				String directoryName = CommonConstant.File_PATH_USER_MANUAL_UPLOAD + File.separator + year;

				String path = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(directoryName,String.valueOf(year));

				path = path + File.separator + userManualEntity.getRoleName();

				String pathForRoleName = CreateFileDirectory.createNewFolderOrDirUploadUserRoleOrder(path,
						userManualEntity.getRoleName());

				String roleNameFolder = pathForRoleName + File.separator + fileUploadDto.getFileName();

				String roleNameFolderCreated = CreateFileDirectory
						.createNewFolderOrDirUploadUserRoleOrder(roleNameFolder, userManualEntity.getRoleName());

				if (Files.isDirectory(Paths.get(roleNameFolderCreated))) {
					String savePath = roleNameFolderCreated + File.separator + userManualEntity.getRoleName() + "-"
							+ fileUploadDto.getFileName();
					logger.info("Path Exist......!");

					try {
						FileCopyUtils.copy(fileUploadDto.getFileContent(), new File(savePath));
					} catch (IOException e) {

						e.printStackTrace();
					}

					userManualEntity.setLastModifiedOn(Calendar.getInstance().getTime());
					userManualEntity.setLastModifiedBy(loggedInUserForcepersonnelId);
					userManualEntity.setLastModifiedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
					userManualEntity.setFileName(fileUploadDto.getFileName());
					userManualEntity.setFileType(fileUploadDto.getFileType());
					userManualEntity.setPath(savePath);
					userManualEntity.setFileSize(Integer.parseInt(fileUploadDto.getFileSize()));

					userManualEntityRepository.save(userManualEntity);
					//checkUpListRepo.updateFileUploadStatus(bloodSugarpp.getAmeId(), bloodSugarpp.getTestCode());

					response.put("isValid", true);
					return ResponseEntity.ok(response);
				} else {
					logger.info("Path not Exist......!");
					response.put("isValid", false);
					errors.put("fileError",
							"Folder Does not Exist & Data Not Saved .......Please Contact Developer Team...");
					response.put("errors", errors);
					return ResponseEntity.ok(response);
				}
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	@GetMapping("user-manual")
    public String viewAllDocuments(HttpSession session, Model model) {
		String loggedInUserForcepersonnelId = (String) session.getAttribute("forcepersonalId");
		List<UserManualEntity> listOfUserManualForAllRoles = userManualEntityRepository.findAll();
		Optional<ForcePersonnelDto> forcePersonnelDto = forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(loggedInUserForcepersonnelId);
		//ForcePersonnelDto loginUserDetails = loginUserDetails.getLoginUserDetails(loggedInUserForcepersonnelId);
       model.addAttribute("listOfUserManualForAllRoles",listOfUserManualForAllRoles);
   	model.addAttribute("loginUserDetails",forcePersonnelDto.get());
       
        return "UserMenu/user-manual-page";
    }


}
