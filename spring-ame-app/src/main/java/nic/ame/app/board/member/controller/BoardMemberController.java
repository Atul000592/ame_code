package nic.ame.app.board.member.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.BoardMemberRequest;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.model.RefMedicalExamType;
import nic.ame.app.admin.model.RefRoleMedical;
import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RefMedicalExamTypeRepo;
import nic.ame.app.admin.repository.RefRoleTypeRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.admin.serviceImpl.ForcePersonalSearchServiceImpl;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.UserRole;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.UserRoleRepo;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.constant.CommonConstant;

@Controller
@EnableTransactionManagement
public class BoardMemberController {

	private Logger logger = LogManager.getLogger(BoardMemberController.class);

	@Autowired
	private AmeDeclarationIndividualDetailsRepo ameDeclarationIndividualDetailsRepo;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private AmeDeclarationRepository ameDeclarationRepository;
	
    @Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
    
    
 	@Autowired
	private RefRoleTypeRepo refRoleType;

	@Autowired
	private RefMedicalExamTypeRepo refMedicalExamTypeRepo;

	@Autowired
	private UnitRepository unitRepository;

	@Autowired
	private AmeParametersRepository ameParametersRepository;

	@Autowired
	private ForcePersonalSearchServiceImpl forcePersonalSearchService;
	
	@Autowired
	private ForcePersonalService forcePersonalService;
    
    @Autowired
    private MedicalBoardRepo medicalBoardRepo;
    
	@Autowired
	RefForceService refForceService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;

	@Autowired
	private ForceRepo refForcerepo;
    
    @Autowired
    private ForcePersonnelService forcePersonnelService;

	@Autowired
	private UserRoleRepo userRoleRepo;

	@Autowired
	private MedicalBoardMemberRepo medicalBoardMemberRepo;

	@Autowired
	private MedicalBoardIndividualMappingService medicalBoardIndividualMappingService;
	
	private static final String AME_ROLE_CODE="1";
	

	@RequestMapping(path = "/boardMember-dashboard", method = RequestMethod.GET)
	public String goToBoardMemberDashboard() {
		return "BoardMember/dashboard";
	}
	

	@RequestMapping(path = "/show-declaration-to-controller" ,method = RequestMethod.GET)
	public String showdeclarationtocontroller(Model model,HttpSession httpSession) {
		
		String forcepersonalId=(String) httpSession.getAttribute("forcepersonalId");
		System.out.println("controlling force personalId :"+forcepersonalId);
		Optional<ForcePersonnel> optional=forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
		String unit= optional.get().getUnit();
		
		System.out.println(">>>>>>>"+unit);
		if(forcepersonalId==null) {
			
			model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
      	  return "bootstrap_medical_temp/index";
		}
	            // List<AmeDeclarationIndividualDetails> declarationIndividualDetails=ameDeclarationIndividualDetailsRepo.findAllByFinalDeclaration();
		          List<AmeDeclarationIndividualModel> declarationIndividualDetails=ameDeclarationRepository.findByFinalDeclaration(unit);
	           
		          if(declarationIndividualDetails.isEmpty()) {
                     model.addAttribute("message","HAVE NO RECORD TO DISPALY ");
	            	return"BoardMember/show-declaration-to-controller";
	            }
		     model.addAttribute("declarationDetails",declarationIndividualDetails);
             return"BoardMember/show-declaration-to-controller";
	}
	
	@RequestMapping(path = "/update-controller-feed",method = RequestMethod.POST)
	public String updatethecontollerresponse(@RequestParam("forcepersonalId")String forcePercenalId,@RequestParam("ameId") String ameId,Model model) {
		
         logger.debug(">>>>Forcepersonal>>"+forcePercenalId +">>>>>>>>>>>>AME-ID"+ameId);
		
		Optional<AmeDeclarationIndividualDetails> optional=ameDeclarationIndividualDetailsRepo.findByAmeId(ameId);
		Optional<AmeDeclarationIndividualModel> optional2=ameDeclarationRepository.findByForcePersonalIdData(forcePercenalId,ameId);
		AmeDeclarationIndividualDetails details=new AmeDeclarationIndividualDetails();
		AmeDeclarationIndividualModel individualModel=new AmeDeclarationIndividualModel();
		if(optional.isPresent()&&optional2.isPresent()) {
			logger.info(">>>>>>> ameID :- "+optional.get().getAmeId());
			logger.info(">>>>>>>>>ForcePersonalId :-"+optional2.get().getForcePersonalId());
			individualModel=optional2.get();
		    details=optional.get();
		    model.addAttribute("forcepersonalId",forcePercenalId);
		    model.addAttribute("name",individualModel.getName());
		    model.addAttribute("forceId", individualModel.getForceId());
			model.addAttribute("details",details);
			model.addAttribute("individualModel",individualModel);
	        model.addAttribute("AmeId", ameId);
			return"BoardMember/show-declaration-to-userInfo";
		}
		else {
			model.addAttribute("forcepersonalId",forcePercenalId);
			model.addAttribute("details",details);
			model.addAttribute("individualModel",individualModel);
	        model.addAttribute("AmeId", ameId);
            return"BoardMember/show-declaration-to-userInfo";
		}
	
		
	}

	
	
	//------------- Saving PhysicalMeasurement data into database------------------//
	
	@RequestMapping(path = "/physical-measurement" ,method = RequestMethod.POST)
	public String savePhysicalMeasurement(@ModelAttribute PhysicalMeasurement physicalMeasurement, @RequestParam String forcePersonalId,@RequestParam String ameId,Model model ,RedirectAttributes redirectAttributes) {
		
		
		   Optional<PhysicalMeasurement>optional=ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(ameId);
		   if(!optional.isPresent()) {
			   ameAssessmentServicePart_2.savePhysicalMeasurement(physicalMeasurement,ameId);
			   logger.info(">>>>>>>>>>>>>>> Save First Time Physical Measurement");
		   }else {
			   ameAssessmentServicePart_2.updatePhysicalMeasurement(ameId, physicalMeasurement);
			   logger.info(">>>>>>>>>>>>>>>  Physical Measurement Updated Successfully");

		   } 
		   
        model.addAttribute("forcepersonalId", forcePersonalId);
		model.addAttribute("ameId", ameId);
		 
		
		
		redirectAttributes.addFlashAttribute("forcepersonalId", forcePersonalId);
	    redirectAttributes.addFlashAttribute("ameId", ameId);
		
		
		return"redirect:/form-index-page";
	}
	
	
	//----redirecting after saving Ame report data into database-----------//
	
	@RequestMapping(path = "/form-index-page")
	public String redirectToFromIndexPage(Model model,HttpServletRequest request ) {
		  String forcepersonalId = null;
		  String ameId = null;
		
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
	    if (inputFlashMap != null) {
	    	forcepersonalId =  (String) inputFlashMap.get("forcepersonalId");
	    	ameId =(String)inputFlashMap.get("ameId");
	    } 
	    if(forcepersonalId!=null && ameId!=null) {
		model.addAttribute("forcepersonalId", forcepersonalId);
		model.addAttribute("ameId", ameId);
		return"BoardMember/form-index-page";
		} 
	    else {
	    	model.addAttribute("errorMsg", "You have been sign out // Session expired.....!");
			return "bootstrap_medical_temp/index";
			
		}
		
		
	}
	//====================================================display-declaration-requests-completed"==========================================//
	
	 @RequestMapping(path = "/display-declaration-requests-completed",method = RequestMethod.GET)
		public String displayDeclarationRequestsCompleted(Model model,HttpSession httpSession) {
			
			String forcepersonalId=(String) httpSession.getAttribute("forcepersonalId");
			System.out.println("controlling force personalId :"+forcepersonalId);
			Optional<ForcePersonnel> optional=forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
			String unit= optional.get().getUnit();
			
			System.out.println(">>>>Display Controller page>>>"+unit);
			if(forcepersonalId==null) {
				
				model.addAttribute("errorMsg","INVALID USER TRY TO GET THE DATA ..........!.......INVALID REQUEST");
	      	  return "bootstrap_medical_temp/index";
			}
		            // List<AmeDeclarationIndividualDetails> declarationIndividualDetails=ameDeclarationIndividualDetailsRepo.findAllByFinalDeclaration();
			          List<AmeDeclarationIndividualModel> declarationIndividualDetails=ameDeclarationRepository.findByFinalDeclarationApproved(unit);
		           
			          if(declarationIndividualDetails.isEmpty()) {
	                     model.addAttribute("message","HAVE NO RECORD TO DISPALY ");
		            	return"BoardMember/show-declaration-to-controller";
		            }
			     model.addAttribute("declarationDetails",declarationIndividualDetails);
			
	
			return"BoardMember/display-declaration-requests-completed";
		}
		
	
	
	//====================================================declaration-requests-completed-action"==========================================//
	
	@RequestMapping(path = "/declaration-requests-completed")
	public String declarationRequestsCompleted(@RequestParam("forcepersonalId") String forcepersonalId,@RequestParam("ameId") String ameId,Model model) {
		
		logger.info(">>> forcepersonalId >>>"+forcepersonalId+">>> ameId >>>"+ ameId);
		
		AmeDeclarationIndividualModel declarationIndividualModel= ameDeclarationRepository.findByForceIdandAmeId(forcepersonalId,ameId);
		ForcePersonnel forcePersonal; 
		Optional<ForcePersonnel> optional=forcePersonnelRepository.getByForcePersonnelId(forcepersonalId);
		if(!optional.isPresent()) {
			model.addAttribute("error-message","sorry for inconvenience....Our engineers are working on to solve the issue.");
			return "bootstrap_medical_temp/error-page";
			
		}
		forcePersonal=optional.get();
		model.addAttribute("forcepersonalId",forcepersonalId);
		model.addAttribute("ameId",ameId);
        
	    Optional<PhysicalMeasurement> optional2=ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(ameId);
	    PhysicalMeasurement physicalMeasurement=new PhysicalMeasurement();
	    
	    if(optional2.isEmpty()) {
	    	model.addAttribute("physicalMeasurement",physicalMeasurement);
	    	return"BoardMember/physical-measurement";
	    }else {
	    	return"BoardMember/form-index-page";
	    }
		
          
	}

	@RequestMapping("/add-board-member")
	public String addBoardMember(HttpServletRequest httpServletRequest, Model model,
			@RequestParam("boardId") String boardId) {
		HttpSession httpSession = httpServletRequest.getSession(false);

		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");

		List<Force> forceList = refForcerepo.findAll();

		List<MedicalBoard> medicalBoardsList = medicalBoardRepo
				.getMedicalBoardByCreatedByForcePersonal(loginForcepersonalId);
				MedicalBoardDetailDto boardDetailDtos = medicalBoardMemberService.medicalBoardDetailByBoardId(boardId);
				MedicalBoardMemberDto createdByDetails = medicalBoardMemberService.findCreatedByDetailsByBoardId(boardId.trim());
			
				StringBuilder createdBy = new StringBuilder();
				createdBy.append(createdByDetails.getIrlaNumber() + " || ").append(createdByDetails.getName() + " || ")
						.append(createdByDetails.getForceName() + " || ").append(createdByDetails.getUnitName());
				String createdByStr = createdBy.toString();
		List<DropDownDto> downDtosList = new ArrayList<>();
		for (MedicalBoard medicalBoard : medicalBoardsList) {
			DropDownDto downDto = new DropDownDto();
			StringBuffer buffer = new StringBuffer();
			String boardPlace = refForceService.getUnitNameByUnitId(
					Integer.parseInt(medicalBoard.getBoardAtForceNo().trim()), medicalBoard.getPlace());
			buffer = buffer.append(medicalBoard.getBoardId()).append(" || " + boardPlace)
					.append(" || " + medicalBoard.getUsedFor() + " || ");
			if (medicalBoard.getGazettedFlag() == 1) {
				buffer.append("Gazetted");
			} else {
				buffer.append("Non-Gazetted");
			}
			downDto.setBoardId(medicalBoard.getBoardId());
			downDto.setValue(buffer.toString());
			downDtosList.add(downDto);
		}
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
		model.addAttribute("forceList", forceList);
		model.addAttribute("boardId", boardId);
		model.addAttribute("downDtosList", downDtosList);
		model.addAttribute("medicalBoard", boardDetailDtos);
		model.addAttribute("createdBy", createdByStr);

		return "UserMenu/add-board-member";

	}

	@PostMapping("/get-medical-board")

	public ResponseEntity<?> getMedicalBoardDetails(@RequestParam("boardId") String boardId, HttpSession httpSession,
			@RequestParam("forcePersonalId") String forcePersonalId) {

		Map<String, Object> map = new HashMap();
		List<MedicalBoard> list = new ArrayList();
		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");

		List<Force> forceList = refForcerepo.findAll();

		List<MedicalBoard> medicalBoardsList = medicalBoardRepo
				.getMedicalBoardByCreatedByForcePersonal(loginForcepersonalId);

		Optional<ForcePersonnelDto> optionalForcePerOptional = this.forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);
		ForcePersonnelDto forcePersonnelDto = new ForcePersonnelDto();
		if (optionalForcePerOptional.isPresent()) {
			forcePersonnelDto = optionalForcePerOptional.get();
			map.put("forcePersonnelDto", forcePersonnelDto);
		} else {
			map.put("forcePersonnelDto", forcePersonnelDto);
		}

		map.put("medicalBoardData", medicalBoardsList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@PostMapping("save-board-member")
	@Transactional
	public ResponseEntity<?> saveBoardMember(
			@RequestBody BoardMemberRequest request, HttpSession session) {

		String boardId = request.getBoardId();
		String forcePersonalId = request.getForcePersonalId();
		Optional<ForcePersonnelDto> optionalForcePersonelDto = this.forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcePersonalId);

		String loginForcePersonalId = (String) session.getAttribute("forcepersonalId");
		Optional<ForcePersonnelDto> optionalForcePersonelLogin = this.forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(loginForcePersonalId);

		List<UserRole> existingUserRolelist = userRoleRepo.findByForcePersonalId(forcePersonalId);
		if (existingUserRolelist.isEmpty()) {
			    ForcePersonnelDto forcePersonnel = optionalForcePersonelDto.get();
				ForcePersonnelDto forcePersonnellogin = optionalForcePersonelLogin.get();
				UserRole newUserRole = new UserRole();
				newUserRole.setRoleId("2");
				newUserRole.setForcePersonalId(forcePersonnel.getForcePersonalId());
				newUserRole.setAllocatedUnit(0l); 
				newUserRole.setMappedBy(loginForcePersonalId); 
				newUserRole.setMappedOn(new Date()); 
				newUserRole.setStatus(1); 
				newUserRole.setMappedByName(forcePersonnellogin.getName());
				newUserRole.setMappedByUnit(Long.parseLong(forcePersonnellogin.getUnit())); 
				newUserRole.setMappedByForceNo(forcePersonnellogin.getForceNo()); 
				newUserRole.setModifiedOn(new Date()); 
				newUserRole.setModifiedBy(loginForcePersonalId); 
		
				newUserRole.setMappedToForce(forcePersonnel.getForceName()); 
				//newUserRole.setTransactionalId(transactionalId); 
				userRoleRepo.save(newUserRole);

				MedicalBoardMember newBoardMember = new MedicalBoardMember();
				newBoardMember.setForcePersonalId(forcePersonnel.getForcePersonalId());
				newBoardMember.setBoardId(boardId); 
				newBoardMember.setName(forcePersonnel.getName()); 
				newBoardMember.setDesignation(forcePersonnel.getDesignation()); 
				newBoardMember.setForce_no(String.valueOf(forcePersonnel.getForceNo())); 
				newBoardMember.setRoleName("1"); 
				newBoardMember.setStatusCode(0); 
				newBoardMember.setCreatedBy(forcePersonnellogin.getForcePersonalId()); 
				newBoardMember.setModifiedBy(forcePersonnellogin.getForcePersonalId()); 
				newBoardMember.setBoardYear(medicalBoardRepo.getBoardYear(boardId));
				newBoardMember.setGazettedFlag(0); 
				newBoardMember.setChangeStatusFromDate(new Date()); 
				newBoardMember.setChangeStatusToDate(new Date()); 
				newBoardMember.setResereveFlag(0); 
				newBoardMember.setRank(forcePersonnel.getRank()); 
				newBoardMember.setGender(forcePersonnel.getGender()); 
				medicalBoardMemberRepo.save(newBoardMember);
		
			
		} 
		else {
			for (UserRole userRole : existingUserRolelist) {
				if(!userRole.getRoleId().equalsIgnoreCase("2")) {
					 ForcePersonnelDto forcePersonnel = optionalForcePersonelDto.get();
						ForcePersonnelDto forcePersonnellogin = optionalForcePersonelLogin.get();
						UserRole newUserRole = new UserRole();
						newUserRole.setRoleId("2");
						newUserRole.setForcePersonalId(forcePersonnel.getForcePersonalId());
						newUserRole.setAllocatedUnit(0l); 
						newUserRole.setMappedBy(loginForcePersonalId); 
						newUserRole.setMappedOn(new Date()); 
						newUserRole.setStatus(1); 
						newUserRole.setMappedByName(forcePersonnellogin.getName());
						newUserRole.setMappedByUnit(Long.parseLong(forcePersonnellogin.getUnit())); 
						newUserRole.setMappedByForceNo(forcePersonnellogin.getForceNo()); 
						newUserRole.setModifiedOn(new Date()); 
						newUserRole.setModifiedBy(loginForcePersonalId); 
				
						newUserRole.setMappedToForce(forcePersonnel.getForceName()); 
						//newUserRole.setTransactionalId(transactionalId); 
						userRoleRepo.save(newUserRole);

						MedicalBoardMember newBoardMember = new MedicalBoardMember();
						newBoardMember.setForcePersonalId(forcePersonnel.getForcePersonalId());
						newBoardMember.setBoardId(boardId); 
						newBoardMember.setName(forcePersonnel.getName()); 
						newBoardMember.setDesignation(forcePersonnel.getDesignation()); 
						newBoardMember.setForce_no(String.valueOf(forcePersonnel.getForceNo())); 
						newBoardMember.setRoleName("1"); 
						newBoardMember.setStatusCode(0); 
						newBoardMember.setCreatedBy(forcePersonnellogin.getForcePersonalId()); 
						newBoardMember.setModifiedBy(forcePersonnellogin.getForcePersonalId()); 
						newBoardMember.setBoardYear(medicalBoardRepo.getBoardYear(boardId));
						newBoardMember.setGazettedFlag(0); 
						newBoardMember.setChangeStatusFromDate(new Date()); 
						newBoardMember.setChangeStatusToDate(new Date()); 
						newBoardMember.setResereveFlag(0); 
						newBoardMember.setRank(forcePersonnel.getRank()); 
						newBoardMember.setGender(forcePersonnel.getGender()); 
						medicalBoardMemberRepo.save(newBoardMember);
				
				}else {
					Optional<MedicalBoardMember> optional= medicalBoardMemberRepo.findbyForcePersonelAndBoardIdAndRoleName
			        		   (forcePersonalId,boardId,AME_ROLE_CODE);
					if(optional.isEmpty()) {
					MedicalBoardMember newBoardMember = new MedicalBoardMember();
					 ForcePersonnelDto forcePersonnel = optionalForcePersonelDto.get();
					 ForcePersonnelDto forcePersonnellogin = optionalForcePersonelLogin.get();
					newBoardMember.setForcePersonalId(forcePersonnel.getForcePersonalId());
					newBoardMember.setBoardId(boardId); 
					newBoardMember.setName(forcePersonnel.getName()); 
					newBoardMember.setDesignation(forcePersonnel.getDesignation()); 
					newBoardMember.setForce_no(String.valueOf(forcePersonnel.getForceNo())); 
					newBoardMember.setRoleName("1"); 
					newBoardMember.setStatusCode(0); 
					newBoardMember.setCreatedBy(forcePersonnellogin.getForcePersonalId()); 
					newBoardMember.setModifiedBy(forcePersonnellogin.getForcePersonalId()); 
					newBoardMember.setBoardYear(medicalBoardRepo.getBoardYear(boardId));
					newBoardMember.setGazettedFlag(0); 
					newBoardMember.setChangeStatusFromDate(new Date()); 
					newBoardMember.setChangeStatusToDate(new Date()); 
					newBoardMember.setResereveFlag(0); 
					newBoardMember.setRank(forcePersonnel.getRank()); 
					newBoardMember.setGender(forcePersonnel.getGender()); 
					medicalBoardMemberRepo.save(newBoardMember);
				}
				}
				
			}
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}


	

    @GetMapping("create-medical-board")
    public String creatingBoardForGazzated(HttpServletRequest httpServletRequest, Model model) {

		HttpSession httpSession = httpServletRequest.getSession(false);
		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
        return "UserMenu/board-for-medical-officer";  
    }

	@RequestMapping(path =  "create-board-for-gazetted-officer" ,method = RequestMethod.GET)
	public String SearchForcePersonal(Model model, HttpSession session) {

		String loginForcepersonalId = (String) session.getAttribute("forcepersonalId");
		List<RefRoleMedical> roleTypeList = refRoleType.findAll();
		List<RefMedicalExamType> examTypesList = refMedicalExamTypeRepo.findAll();
		List<UnitModel> unitList = unitRepository.findAll();
		List<Force> forceList = refForcerepo.findAll();
	
		model.addAttribute("roleTypeList", roleTypeList);
		model.addAttribute("examTypesList", examTypesList);
		model.addAttribute("forceList", forceList);
		model.addAttribute("unitList", unitList);
		
		DropDownDto downDto = new DropDownDto(
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.NON_GAZETTED)),
				"Non-Gazetted");
		DropDownDto downDto1 = new DropDownDto(
				Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.GAZETTED)), "Gazetted");

		List<DropDownDto> downDtos = new ArrayList<>();
		downDtos.add(downDto);
		downDtos.add(downDto1);
		model.addAttribute("downDtos", downDtos);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
		String forcePersonalLogin = (String) session.getAttribute("forcepersonalId");
		System.out.println("=========search======" + forcePersonalLogin + "--------------------------");

		Optional<ForcePersonnelDto> optional = forcePersonalService.findByForcePersonalId(forcePersonalLogin);
		List<ForcePersonalResponeseAjax> forcePersonalList = new ArrayList<ForcePersonalResponeseAjax>();
		if (optional.isEmpty()) {
			session.invalidate();
			model.addAttribute("errorMsg", "Invalid user with wrong Email or password");
			return "redirect:/login-page";
		} else {
			forcePersonalList = forcePersonalSearchService.ListOfMedicalCadre(optional.get().getUnit(),
					optional.get().getForceNo());
		}

		model.addAttribute("forcePersonalList", forcePersonalList);

		return "admin-template/create-board-for-gazzatted-officer";
	}
	
	@RequestMapping(path ="create-medical-board-for gazzatted",method = RequestMethod.POST)
	public ResponseEntity<?> addCommitteeMember(Model model, HttpSession session, HttpServletRequest request,
			@RequestBody List<MedicalBoardMemberDto> medicalBoardMemberDtolist) {
				Map<String, Object> response = new HashMap<>();
				Map<String, String> errors = new HashMap<>();
				Set<String> foundRoles = new HashSet<>();
		
		try {
			Set<String> requiredRoles = new HashSet<>(Arrays.asList("AO", "PO", "RO", "MB1", "MB2"));
			
			Set<String> assignedForcePersonalIds = new HashSet<>();
			
			//Map<String,String>assignedForcePersonalIdsMap=new HashMap<>();
			
			for(MedicalBoardMemberDto medicalBoardMemberDto:medicalBoardMemberDtolist) {
				 if (medicalBoardMemberDto.getRoleName() == null || medicalBoardMemberDto.getRoleName().trim().isEmpty()) {
                    errors.put(medicalBoardMemberDto.getName(), medicalBoardMemberDto.getName() + ": Please provide the role !");
                    continue;
                }       
				String roleName = medicalBoardMemberDto.getRoleName();
                if (requiredRoles.contains(roleName)) {
                    foundRoles.add(roleName);  
                }       
                String forcePersonalId = medicalBoardMemberDto.getForcePersonalId();
                if (assignedForcePersonalIds.contains(forcePersonalId)) {
                    errors.put(medicalBoardMemberDto.getName(), "This person already has a role assigned!");
                } else {
                    assignedForcePersonalIds.add(forcePersonalId);
                }
			
              }
				/*
				 * if (!foundRoles.containsAll(requiredRoles)) { errors.put("requiredRoles",
				 * "The board must include AO, PO, RO, MB1, and MB2 roles."); }
				 */
			
			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			} else {
				String loginForcepersonalId = (String) session.getAttribute("forcepersonalId");
			    logger.info(">>>>>>>>>>>>>>>> medicalBoardMemberDtolist "+medicalBoardMemberDtolist);
				
				List<UnitModel> unitList = unitRepository.findAll();
			//	List<HospitalModel> hospitalList = hospitalRepository.findAll();
				
				String forcePersonalLogin = (String) session.getAttribute("forcepersonalId");
				logger.info(">>>>>>>>>>> login_forcepersonal_Id :"+forcePersonalLogin);
				
				
				boolean boardDetailSaved = medicalBoardIndividualMappingService
						.saveMedicalBoardMember(medicalBoardMemberDtolist, forcePersonalLogin, request);
				
				model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
			    model.addAttribute("unitList", unitList);
				//model.addAttribute("hospitalList", hospitalList);
				logger.info(">>>>>>>>>>>board creation status >>>>>>>"+boardDetailSaved);
				response.put("isValid", true);
				response.put("message","Medical board created successfully!");
				return ResponseEntity.ok(response);
				//return ResponseEntity.status(HttpStatus.OK).body(medicalBoardMemberDtolist);

				
				   } 
			}
		
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
		
		return null;
		
		
			}

}
