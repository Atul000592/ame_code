package nic.ame.app.admin.controller;


import java.util.Calendar;
import java.util.Date;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import nic.ame.app.admin.dto.NotificationDto;
import nic.ame.app.admin.model.Notification;




import nic.ame.app.admin.repository.ForceRepo;

import nic.ame.app.admin.service.NotificationService;

import nic.ame.app.board.member.repository.AmeDeclarationFilesRepo;
import nic.ame.app.esign.StringConstants;

import nic.ame.app.master.medical.model.AmeDeclarationFiles;
import nic.ame.app.master.medical.model.AmeFinalReportFileDir;

import nic.ame.app.master.model.Force;

import nic.ame.app.master.repository.AmeFinalReportFileDirRepository;

import nic.ame.app.master.service.LoginUserDetails;

import nic.ame.constant.CommonConstant;
import nic.ame.master.util.RandomCodeGenerator;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;





@Controller
public class NotificationContactUsController {

	Logger logger = LogManager.getLogger(NotificationContactUsController.class);

	@Autowired
	private NotificationService notificationService;


	@Autowired
	private LoginUserDetails loginUserDetails;



	@Autowired
	private ForceRepo refForcerepo;


	
	@Autowired 
	private AmeDeclarationFilesRepo ameDeclarationFilesRepo;
	
	@Autowired
	private AmeFinalReportFileDirRepository ameFinalReportFileDirRepository;
	

	


// ============================================= Create Notification =======================================================//
	@GetMapping("/add-notifications")
	public String createNewNotification(HttpServletRequest httpServletRequest, Model model) {

		HttpSession httpSession = httpServletRequest.getSession();
		if (httpSession.equals(null)) {
			httpSession.invalidate();
			return "";
		}

		String message = null;
// ===============================================================================================//
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
		if (inputFlashMap != null) {
			message = (String) inputFlashMap.get("message");

		}
// ===============================================================================================//
		
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));

// ====================Force List/Department=====================================================//
		List<Force> refForces = refForcerepo.findAll();

		Notification notification = new Notification();

		model.addAttribute("notification", notification);
		model.addAttribute("refForces", refForces);
		model.addAttribute("message", message);

		return "admin-template/add-notifications";
	}

 //====================================================Save Notification====================================================//	
	@PostMapping("create-new-notification")
	public String createNewNotification(MultipartFile file, @ModelAttribute("notification") Notification notification,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date visibleFromDateRequest,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date visibleToDateRequest,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date orderDateRequest,
			RedirectAttributes redirectAttributes,

			HttpServletRequest httpServletRequest) {

		System.out.println(notification);
		System.out.println("visibleFromDateRequest" + visibleFromDateRequest + ">>>> visibleFromDateRequest >>   "
				+ visibleToDateRequest);
		System.out.println(file.getOriginalFilename());

		Date entryDate = Calendar.getInstance().getTime();

		HttpSession httpSession = httpServletRequest.getSession();
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		if (httpSession.equals(null)) {
			logger.info("invalid session");
		}
		notification.setOrderDate(orderDateRequest);
		notification.setOrderId(RandomCodeGenerator.getTransactionalIdUserRole());
		notification.setEntryDate(entryDate);
		notification.setVisibleFromDate(visibleFromDateRequest);
		notification.setVisibleToDate(visibleToDateRequest);

		boolean result = notificationService.saveNotification(notification, loginForcePersonalId, httpServletRequest,
				file);

		if (result)
			redirectAttributes.addFlashAttribute("message", "Notification has been created successfully!");
		else
			redirectAttributes.addFlashAttribute("message", "Notification creation failed.......!");

		return "redirect:add-notifications";
	}
    @CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL, StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
	@GetMapping("view-active-notifications")
	public String viewNotifications(HttpServletRequest httpServletRequest,Model model) {
		
		HttpSession httpSession = httpServletRequest.getSession();
		if (httpSession.equals(null)) {
			httpSession.invalidate();
			return "";
		}
		
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));

		List<NotificationDto> allActiveNotification = notificationService.getAllActiveNotification();
		System.out.println(allActiveNotification);
		model.addAttribute("notifications", allActiveNotification);
		return "admin-template/view-notifications";

	}
//=====================================download notification files===========================================================//
    @CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL, StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
    @GetMapping("/download-notification-file")
	public ResponseEntity<FileSystemResource> downloadNotitificationFile(@ModelAttribute("file") File file) {
		if (file == null || !file.exists()) {
			return ResponseEntity.notFound().build();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
				.contentType(MediaType.APPLICATION_PDF).body(new FileSystemResource(file));
	}

	@ModelAttribute("file")
	public File getFile(@RequestParam(value = "filePath", required = false) String filePath) {
		if (filePath == null || filePath.isEmpty()) {
			return null; // Return null if no file path is provided
		}

		return new File(filePath);
	}


//===========================================Download declaration file uploaded=========================================================//




    @CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL, StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
@GetMapping("download-declaration-file-uploaded")
public ResponseEntity<FileSystemResource> downloadDeclarationFileUploaded(@RequestParam("ameId") String ameId) {
	
   String filePath=null;
	AmeDeclarationFiles ameDeclarationFiles  = ameDeclarationFilesRepo.findFileNameByAmeId(ameId);
	if(ameDeclarationFiles!=null) {
		if(ameDeclarationFiles.getFilePath()!=null)
			filePath=ameDeclarationFiles.getFilePath();
		else
			filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
	}
	File file=new File(filePath);
	
	if (file == null || !file.exists()) {
		 file=new File(CommonConstant.DEFAULT_FILE_PATH_NAME);
		
	}

	HttpHeaders headers = new HttpHeaders();
	headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
	headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE);

	return ResponseEntity.ok().headers(headers).contentLength(file.length())
			.contentType(MediaType.APPLICATION_PDF).body(new FileSystemResource(file));

}


@CrossOrigin(origins = {StringConstants.CROSS_ORIGIN_BASE_URL, StringConstants.CROSS_ORIGIN_AME_URL},allowCredentials = "true") 
@GetMapping("download-ame-final-report-uploaded")
    public ResponseEntity<InputStreamResource> downloadAmeFinalReportUploaded(@RequestParam("ameId") String ameId) {
	
     String filePath=null;
     Optional<AmeFinalReportFileDir> ameFinalReportFileDirOptional =ameFinalReportFileDirRepository.findByAmeId(ameId);
     String fileName=null;
	if(!ameFinalReportFileDirOptional.isEmpty()) {
		if(ameFinalReportFileDirOptional.get().getFilePath()!=null) {
			filePath=ameFinalReportFileDirOptional.get().getFilePath();
		fileName=ameFinalReportFileDirOptional.get().getFileName()
				;}
		else {
			filePath=CommonConstant.DEFAULT_FILE_PATH_NAME;
	}}
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


