package nic.ame.app.master.controller;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.RequestContextUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import nic.ame.app.admin.dto.NotificationDto;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.service.NotificationService;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.SHARandomKeyHolder;
import nic.ame.app.master.repository.SHARandomKeyHolderRepository;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.RandomGenerator;
import nic.ame.master.util.RandomKeyGenerator;


@Controller
@Slf4j
public class HomepageController {
	
	Logger logger=LoggerFactory.getLogger(HomepageController.class);
	
	private final ForceRepo  refForceRepo;		
	private final NotificationService notificationService;
	private final SHARandomKeyHolderRepository randomKeyHolderRepository; 
	
	
	
	
	


	public HomepageController(ForceRepo refForceRepo, NotificationService notificationService,
			SHARandomKeyHolderRepository randomKeyHolderRepository) {
		super();
		this.refForceRepo = refForceRepo;
		this.notificationService = notificationService;
		this.randomKeyHolderRepository = randomKeyHolderRepository;
	}


	@GetMapping({"/initial-login"})
    public String homePage(HttpServletRequest httpServletRequest,Model model,HttpServletResponse httpServletResponse) {
	  
		  HttpSession httpSession=httpServletRequest.getSession();
		
		 
		  
		  List<Force> refForces=refForceRepo.findAll();
	  model.addAttribute("refForceList", refForces);
	  String message = null;
	            
	  
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
		if (inputFlashMap != null) {
			message = (String) inputFlashMap.get("errorMsg");

		}else {
			if(httpSession!=null) {
			message=(String)httpSession.getAttribute("errorMsg");
			logger.info(">>>>>>>>>message>>>>>>>>>"+message);}
			
		
		if(message==null) {
			//model.addAttribute("errorMsg", "Invalid UserName or Password......!");
		}else {
			 model.addAttribute("errorMsg", message)  ;
		}
		}  
	   
		
        model.addAttribute("salt", CommonConstant.SHA_KEY_256);
        
        String randomKey=RandomKeyGenerator.generateRandomAlphaNumericKey(10);
        model.addAttribute("randomKey", randomKey); 
       //httpSession.setAttribute("randomKey",randomKey); 
	    String ipAddress=GetIpAddressClient.getIpAddressFromHeaderClient(httpServletRequest);
	    SHARandomKeyHolder keyHolder=new SHARandomKeyHolder();
	    
	   // randomKeyHolderRepository.deleteAll();
	    logger.info("All Key are removed");
		

	     int randomCode=Integer.parseInt(RandomGenerator.getRandomOneTimePassword(5).trim());
	     keyHolder.setId(randomCode);
         keyHolder.setRandomKey(randomKey);
	     keyHolder.setDate(Calendar.getInstance().getTime());
	     SHARandomKeyHolder randomKeyHoldersaved=randomKeyHolderRepository.save(keyHolder);
		      // logger.info(">>>>>>hexKey >>>>>>"+secretKey); 
		      // logger.info(">>>>>>ivHex >>>>>>>"+ivParam);
	     model.addAttribute("randomId",randomKeyHoldersaved.getId());
		   	   logger.info(">>>>>>Time >>>>>>"+LocalDate.now()+"ipAddress"+ipAddress);
		       logger.info("Session Id>>>>"+httpSession.getId());
		       logger.info("randomKey>>>>>>>>>"+randomKey);
		       return"base-template/login-page"; 
	  
	  }
	 
	  
	/*
	 * @RequestMapping(value={"/initial-login"}) public String homePage() {
	 * 
	 * return"bootstrap_medical_temp/index"; }
	 */
	 
	
	@GetMapping("/contactus")
	public String contactus(){
		
		return"bootstrap_medical_temp/contactus";
	}
	
	@GetMapping("/published-notifications")
	public String publishedNotifications(Model model){
		
		List<NotificationDto> allActiveNotification = notificationService.getAllActiveNotification();
		System.out.println(allActiveNotification);
		model.addAttribute("notifications", allActiveNotification);
		
		return"bootstrap_medical_temp/published-notifications";
	}
	
	@GetMapping("/dashboard-home")
	public String getToDeshBorad(Model model) {
		String email="admin@nic.in";
		String password="1234";
		System.out.println(email+" deshboard");
		
		if(!email.equals("admin@nic.in") && !password.equals("pass")) {
			model.addAttribute("errorMsg","Invalid user with worng Email or password");
			
			
			return"bootstrap_medical_temp/index";
			
		}
		model.addAttribute("emailId",email);
		model.addAttribute("designation","XYZ");
		model.addAttribute("depart","DEFANCE");
		model.addAttribute("name","APLHA");
	
		
		return"bootstrap_medical_temp/dashboard";
	}
}
