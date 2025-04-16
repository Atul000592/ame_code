package nic.ame.app.user.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.board.member.repository.AmeAppointmentDetailsRepo;
import nic.ame.app.board.member.repository.AmeDeclarationFilesRepo;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.AmeAppointmentDetails;
import nic.ame.app.master.medical.model.AmeDeclarationFiles;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.CandidateCheckUpListService;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.serviceImpl.ForcePersonalServiceImpl;
import nic.ame.app.user.dto.UserStatusDto;
import nic.ame.app.user.repository.AmeDeclarationDetailRepoUser;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;
import nic.ame.app.user.serviceImpl.UserServiceImpl;

@Controller
public class UserController {
	
	@Autowired
	private AmeDeclarationDetailRepoUser ameDeclarationDetailsRepoUser;
	
	@Autowired
	private AmeDeclarationIndividualModelRepoUser ameDeclarationIndividualModelRepoUser;
	
	@Autowired
	private AmeAppointmentDetailsRepo ameAppointmentDetailsRepo;
	
	@Autowired
	private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
	@Autowired
	private AmeDeclarationFilesRepo ameDeclarationFilesRepo; 
	
	@Autowired
	private UserServiceImpl serviceImpl;
	
	@Autowired
	private ForcePersonnelRepository forcePersonalRepository;
	
	@Autowired
	private ForcePersonalServiceImpl forcePersonalServiceImpl;
	
	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private ForcePersonalService forcePersonalService;
	
	@Autowired
	private CandidateCheckUpListService candidateCheckUpListService;
	
	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;
	
	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	ForcePersonnelRepository forcePersonnelRepository;
	
	
	
	Logger logger=LogManager.getLogger(UserController.class);
	
	@RequestMapping(path ="/show-user-declaration",method = RequestMethod.POST)
	public String showdeclaration(@RequestParam("ameId")String AmeId, @RequestParam("forcepersonalId") String forcepersonal,Model model) {
		
		logger.debug(">>>>Forcepersonal>>"+forcepersonal +">>>>>>>>>>>>AME-ID"+AmeId);
		
		 UserRoleDto userRoleDto =new UserRoleDto();
		
		
		Optional<AmeDeclarationIndividualDetails> optional=ameDeclarationDetailsRepoUser.findByAmeId(AmeId);
		List<AmeDeclarationIndividualModel> optional2=ameDeclarationIndividualModelRepoUser.findByForcePersonalId(forcepersonal);
		AmeDeclarationIndividualDetails details=new AmeDeclarationIndividualDetails();
		AmeDeclarationIndividualModel individualModel=new AmeDeclarationIndividualModel();
		if(optional.isPresent()&&optional2.isEmpty()) {
			//logger.info(">>>>>>> ameID :- "+optional.get().getAmeId());
			//logger.info(">>>>>>>>>ForcePersonalId :-"+optional2.getForcePersonalId());
			//individualModel=optional2.get();
		    details=optional.get();
		}
	
		model.addAttribute("forcepersonalId",forcepersonal);
		model.addAttribute("details",details);
		model.addAttribute("individualModel",individualModel);
		 model.addAttribute("userRoleDto",userRoleDto);
		model.addAttribute("AmeId", AmeId);
		return "UserMenu/show-user-declaration";
	}
	
	@RequestMapping(path = "/update-from-user" ,method = RequestMethod.POST)
	public String showupdatedata(@RequestParam("ameId")String AmeId, @RequestParam("ameId") String ameId,Model model,@RequestParam("forcepersonalId") String forcepersonalId) {
		
		Optional<AmeDeclarationIndividualDetails> optional=ameDeclarationDetailsRepoUser.findByAmeId(ameId);
		List<AmeDeclarationIndividualModel> optional2=ameDeclarationIndividualModelRepoUser.findByForcePersonalId(forcepersonalId);
		if(ameId!=null) {

			if(optional.isPresent()&&optional2.isEmpty()) {
				UserRoleDto userRoleDto =new UserRoleDto();
			
				//AmeDeclarationIndividualModel individualModel=optional2.get();
				AmeDeclarationIndividualDetails individualDetails=optional.get();
				
				model.addAttribute("individualDetails",individualDetails);
				//model.addAttribute("individualModel",individualModel);
				 model.addAttribute("userRoleDto",userRoleDto);
				return"UserMenu/declaration-edit-form";
			}
			return"bootstrap_medical_temp/index";
		}
        return"bootstrap_medical_temp/index";
	}
	
	@RequestMapping(path = "/update-user-declaration",method = RequestMethod.POST)
	public String savedeclarationupdate(@RequestBody AmeDeclarationIndividualDetails individualDetails,Model model) {
		
		System.out.println(">>>>>>>>"+individualDetails.getAmeId());
		
		
		return"save";
	}
	

	

	@RequestMapping(path = "/status-user-ame", method =RequestMethod.GET )
	public String statususer(HttpServletRequest httpServletRequest,Model model,HttpSession session) {
	    @SuppressWarnings("unchecked")
		List<UserRoleDto> roleDtosList	= (List<UserRoleDto>) session.getAttribute("roleDtosList");

		model.addAttribute("roleDtosList", roleDtosList);
		HttpSession httpSession=httpServletRequest.getSession(false);
		
		if(httpSession==null) {
			model.addAttribute("errorMsg", "INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
			return "bootstrap_medical_temp/index";
		}
		 String gazettedNonGazettedFlag=(String) httpSession.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		 String candidateForcepersonalId=(String) httpSession.getAttribute("forcepersonalId");
	
		
			/*
			 * List<AmeDeclarationIndividualModel> individualModels=
			 * ameDeclarationIndividualModelRepoUser.
			 * findListOfDeclarationByUserIncurrentYear(candidateForcepersonalId.trim());
			 */
		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(candidateForcepersonalId));

		
			return"UserMenu/user-status-ame";
			
		}
		
		
	
	
	
	@RequestMapping(path = "/print-ame-declaration-form",method = RequestMethod.POST)
	public String printAmeDeclaration(
			@RequestParam("forcepersonalId")String forcePercenalId,
			@RequestParam("ameId") String ameId,
			Model model,HttpSession session) {
	
	    logger.debug(">>>>Forcepersonal>>"+forcePercenalId +">>>>>>>>>>>>AME-ID"+ameId);
		UserRoleDto userRoleDto =new UserRoleDto();
		 String gazettedNonGazettedFlag=(String) session.getAttribute("gazettedNonGazettedFlag");
		 model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
	    if(!ameId.isEmpty()) {
            
	    	Optional<AmeDeclarationIndividualDetails> optional=ameDeclarationIndividualDetailsRepo.findByAmeId(ameId);
			Optional<AmeDeclarationIndividualModel> optional2=ameDeclarationRepository.findByForcePersonalIdData(forcePercenalId,ameId);
			ForcePersonnelDto forcePersonnelDtoIndividualModel=forcePersonalService.getForcePersonalDetails(forcePercenalId);
			AmeDeclarationIndividualDetails details=new AmeDeclarationIndividualDetails();
			AmeDeclarationIndividualModel individualModel=new AmeDeclarationIndividualModel();
			Optional<AmeAppointmentDetails> appointmentDetails=ameAssessmentServicePart_2.ameAppointmentDetails(ameId);
			Date appointmentDate = null;
			String date = null;
			
			String amePlace=ameApplicationFlowStatusRepo.getAmePlaceByBoardIdAndAmeId(ameId.trim());
			
			if(!appointmentDetails.isEmpty()) {
				 appointmentDate=appointmentDetails.get().getAppointmentDate();
				 SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
			     date =simpleDateFormat.format(appointmentDate);
			}
			
		    model.addAttribute("amePlace", amePlace);
		    model.addAttribute("currentDate",Calendar.getInstance().getTime()); 
		   
			if(optional.isPresent()&& optional2.isPresent()) {
				logger.info(">>>>>>> ameID :- "+optional.get().getAmeId());
				logger.info(">>>>>>>>>ForcePersonalId :-"+optional2.get().getForcePersonalId());
				  
			
				
				individualModel=optional2.get();
				 details=optional.get();
					/*
					 * individualModel.setCurent_new_unit( refForceService.getUnitNameByUnitId(
					 * forcePersonnelRepository.getByForcePersonnelId(forcePercenalId).get().
					 * getForceNo(), individualModel.getCurent_new_unit()));
					 */
			   
			    model.addAttribute("forcepersonalId",forcePercenalId);
			    
			  /*  details.setCurentnewunit(refForceService.getUnitNameByUnitId(
								forcePersonalRepository.getByForcePersonalId(forcePercenalId).get().getForce_no(),
								details.getCurentnewunit()));*/
				model.addAttribute("details",details);
				model.addAttribute("individualModel",individualModel);
		        model.addAttribute("AmeId", ameId);
		        model.addAttribute("userRoleDto",userRoleDto);
		        model.addAttribute("appointmentDate",date);
		        model.addAttribute("forcePersonnelDtoIndividualModel",forcePersonnelDtoIndividualModel);
		        return"UserMenu/print-ame-declaration-form";			
		        
			}
			else {
				individualModel.setCurent_new_unit(
						refForceService.getUnitNameByUnitId(
								forcePersonnelRepository.getByForcePersonnelId(forcePercenalId).get().getForceNo(),
								individualModel.getCurent_new_unit()));
				/*details.setCurentnewunit(refForceService.getUnitNameByUnitId(
						forcePersonalRepository.getByForcePersonalId(forcePercenalId).get().getForce_no(),
						details.getCurentnewunit()));*/
				model.addAttribute("forcepersonalId",forcePercenalId);
				model.addAttribute("details",details);
				model.addAttribute("individualModel",individualModel);
		        model.addAttribute("AmeId", ameId);
		        model.addAttribute("userRoleDto",userRoleDto);
		        model.addAttribute("forcePersonnelDtoIndividualModel",forcePersonnelDtoIndividualModel);
		        return"UserMenu/print-ame-declaration-form";			}
			}
	    model.addAttribute("userRoleDto",userRoleDto);
	            return "redirect:/dashboard-user";
		 
		
	}
	
	@RequestMapping(value= "/download",method = RequestMethod.POST)
	public void downloadfile(HttpServletResponse httpServletResponse,@RequestParam String filePath) {
		
        String uploadPath =  filePath;
         File file=new File(uploadPath);
        
        try {
			FileCopyUtils.copy(Files.newInputStream(file.toPath()),httpServletResponse.getOutputStream());
			
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>File Downloaded........!");
	}catch(IOException exception) {
		
		exception.printStackTrace();
	}
}
	
	@RequestMapping(value="/show-candidate-checkuplist",method = RequestMethod.POST)
	public String getCheckUpList(@RequestParam("ameId") String ameId,Model model) {
		System.out.println("Ameid>>>>>>>>>>>"+ameId);
		List<CheckUpList> checkUpLists= candidateCheckUpListService.getCandidateCheckUpList(ameId);
		
		model.addAttribute("ameId", ameId);
		model.addAttribute("checkUpList", checkUpLists);
		return"UserMenu/show-candidate-checkuplist";
	}
	
}
