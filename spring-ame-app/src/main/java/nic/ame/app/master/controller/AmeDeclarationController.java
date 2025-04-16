package nic.ame.app.master.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.util.JSONPObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.ForceUnitService;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.admin.service.UnitAndForceService;
import nic.ame.app.board.member.repository.CheckUpListRepo;
import nic.ame.app.board.member.repository.RefAppetiteRepo;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.dto.AmeAppointmentDto;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.medical.model.AmeReviewCandidatesList;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.service.AmeDropDownService;
import nic.ame.app.master.medical.service.AppointmentService;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.model.AmeDeclarationDtoRequest;
import nic.ame.app.master.model.AmeDeclarationDtoResponse;
import nic.ame.app.master.model.AmeDeclarationIndividualDetails;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.RequestDto;
import nic.ame.app.master.model.TTAppointmentAme;
import nic.ame.app.master.model.TestModel;
import nic.ame.app.master.ref.entity.RefAppetite;
import nic.ame.app.master.ref.entity.RefSleep;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeDeclarationIndividualDetailsRepo;
import nic.ame.app.master.repository.AmeDeclarationRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.AmeReviewCandidatesListRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.app.master.service.AmeDeclarationService;
import nic.ame.app.master.service.AmeUniqueIdGenerationService;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.user.dto.UserDeclarationDto;
import nic.ame.app.user.management.service.UserManagementService;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;
import nic.ame.app.user.service.UserService;
import nic.ame.constant.CommonConstant;
import nic.ame.master.util.DateConvert;
import nic.ame.master.util.DateFormatConversion;
import nic.ame.master.util.GetIpAddressClient;

@Controller
@EnableTransactionManagement
public class AmeDeclarationController {

	private org.slf4j.Logger logger =  LoggerFactory.getLogger(AmeDeclarationController.class);

	@Autowired
	private TTAppointmentAmeRepo ttAppointmentAmeRepo;
	@Autowired
	private MedicalBoardIndividualMappingService medicalBoardIndividualMappingService;

	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private AmeDropDownService ameDropDownService;

	@Autowired
	private AmeDeclarationService ameDeclarationService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private AmaDeclarationCountService amaDeclarationCountService;

	@Autowired
	private RefForceService refForceService;

	@Autowired
	private AmeUniqueIdGenerationService ameUniqueIdGenerationService;

	@Autowired
	private UnitAndForceService unitAndForceService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private UserManagementService userManagementService;

	@Autowired
	private AmeApplicationFlowStatusService ameApplicationFlowStatusService;
	@Autowired
	private AmeParametersRepository ameParametersRepository;

	@Autowired
	private AmeDeclarationIndividualModelRepoUser ameDeclarationIndividualModelRepoUser;

	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private RefAppetiteRepo appetiteRepo;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private CheckUpListRepo checkUpListRepo;
	@Autowired
	private AmeReviewCandidatesListRepository ameReviewCandidatesListRepository;


	


	@PostMapping(path = "personal-details")
	public String getToAmeDeclaration(Model model, HttpSession session, HttpServletRequest request,
			@RequestParam("forcePersonalId") String forcepersonalId, @RequestParam("boardId") String boardId) {

		String Name = (String) session.getAttribute("name");
		String gazettedNonGazettedFlag = (String) session.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		// UserRoleDto userRoleDto=(UserRoleDto) session.getAttribute("userRoleDto");
		UserRoleDto userRoleDto = new UserRoleDto();

		// String forcepersonalId=(String)
		// request.getSession().getAttribute("forcepersonalId");
		if (forcepersonalId == null) {
			forcepersonalId = (String) session.getAttribute("forcepersonalId");
		}
		int myRequestCount = amaDeclarationCountService.findMyDeclarationCount(forcepersonalId);
		model.addAttribute("myRequestCount", myRequestCount);

		// Optional<ForcePersonnelDto> optional
		// =forcePersonalRepository.getByForcePersonalId(forcepersonalId);

		Optional<ForcePersonnelDto> forcePersonnelOptional = forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
		ForcePersonnelDto forcePersonnel = forcePersonnelOptional.get();

		ForcePersonnelDto forcePersonalCandidate = loginUserDetails.getCandicateForcePersonalId(forcepersonalId);
		model.addAttribute("loginUserDetails", forcePersonalCandidate);
		model.addAttribute("boardId", boardId);
		
		if (forcePersonnelOptional.isPresent()) {
			
			
			long currentYear =	Long.parseLong(medicalBoardRepo.getBoardYear(boardId));

			
			ForcePersonnelDto forcePersonal = forcePersonnelOptional.get();
			String force_id = forcePersonal.getForceId();
			int forceNumber = forcePersonal.getForceNo();
			String forceName = refForceService.getForceNameByForceId(forceNumber);
			List<UnitModel> unitList = unitAndForceService.getUnitDropDown(forceNumber);
			boolean result = userService.getAmeDeclartionForCurrentYear(force_id, currentYear);
			String declarationyear = medicalBoardRepo.getBoardYear(boardId);
			int mappingStatus = medicalBoardIndividualMappingService.getForcePersonalBoardMappingStatus(forcepersonalId,
					String.valueOf(declarationyear));
			Optional<TTAppointmentAme> ttAppointMentAmeOptional = appointmentService.getAmeAppointmentStatus(forcepersonalId, boardId);

			if (!ttAppointMentAmeOptional.isEmpty()) {

				AmeAppointmentDto ameAppointmentDto = new AmeAppointmentDto();
				ameAppointmentDto = appointmentService.checkValidationForAppointmentDate(ttAppointMentAmeOptional);
				if (ameAppointmentDto == null) {

					logger.info(">>>>>>>>>>>>>>>>>ameAppointmentDto is null......");
					model.addAttribute("userRoleDto", userRoleDto);
					model.addAttribute("message", "invalid Appointment Date ........"
							+ "(Note:- Please contact the  Medical officer of the unit)");
					return "UserMenu/dashboard";
				}
			} else {
				model.addAttribute("userRoleDto", userRoleDto);
				logger.info(">>>>>>>>>>>>>>>>>ttAppointMentAmeOptional is not provided......");
				model.addAttribute("message",
						"User Can not fill declaration for the selected year  " + currentYear
								+ " because user has not been assigned any Declaration Date."
								+ "(Note:- contact your Medical officer of the unit)");
				return "UserMenu/dashboard";
			}

			if (mappingStatus == 0) {
				model.addAttribute("userRoleDto", userRoleDto);
				logger.info(">>>>>>>>>>>>>>>>>mappingStatus is 0......");
				model.addAttribute("message",
						"User Can not fill declaration for the selected year  " + currentYear
								+ " because user has not been assigned to any board."
								+ "(Note:- contact your Medical officer of the unit)");
				return "UserMenu/dashboard";
			}
			if (!result) {
				model.addAttribute("userRoleDto", userRoleDto);
				logger.info(">>>>>>>>>>>>>>>>>Result  is ......");
				model.addAttribute("message",
						"Decleration for the current year : " + currentYear + " has been submitted");
				return "UserMenu/dashboard";

			}
         
			
			model.addAttribute("unitList", unitList);
			model.addAttribute("forceName", forceName);
			model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
			List<RefAppetite> refAppetite = ameDropDownService.refAppetitesList();
			List<RefSleep> refSleeps = ameDropDownService.refSleepsList();

			AmeDeclarationDtoResponse declarationDtoResponse = new AmeDeclarationDtoResponse();
			AmeDeclarationDtoResponse ameDeclarationDtoResponse = new AmeDeclarationDtoResponse();

			declarationDtoResponse.setAmeId("AME" + forcepersonalId);
			declarationDtoResponse.setForcePersonalId(forcepersonalId);
			declarationDtoResponse.setLastAmePlace(forcePersonal.getAmePlace());
			declarationDtoResponse.setName(forcePersonal.getName());
			declarationDtoResponse.setLastAmeDate((forcePersonal.getLastAmeDate()));

			logger.info(">>>>>forcepersonalId>>>>>" + forcepersonalId + ">>>>Unit>>>>" + forcePersonal.getUnit());

			declarationDtoResponse.setDesignation(forcePersonal.getDesignation());
			declarationDtoResponse.setRank(forcePersonal.getRank());
			declarationDtoResponse.setForceNumber(forcePersonal.getForceNo());
			declarationDtoResponse.setUnit(refForceService.getUnitNameByUnitId(forcePersonal.getForceNo(), forcePersonal.getUnit()));
			declarationDtoResponse.setForceId(forcePersonal.getForceId());
			
			String lastAMECategory=null;
			lastAMECategory=forcePersonal.getLastAmeShape();
			model.addAttribute("lastAMECategory", lastAMECategory);
			Date lastAmeDate=forcePersonal.getLastAmeDate();
			String lastAmePlace=forcePersonal.getAmePlace();
			model.addAttribute("lastAmeDate", lastAmeDate);
			model.addAttribute("lastAmePlace", lastAmePlace);


			
			
			
			// List<StateMaster> stateList=stateAndDistrictService.getStateMasterList();

			Date joinigDate = forcePersonal.getJoiningDate();

			model.addAttribute("unitList", unitList);
			if (declarationDtoResponse != null && ameDeclarationDtoResponse != null) {

				if (forcePersonal.getDesignation().contains("approver")) {
					logger.info(">>>>>>>>>>>>>>>>> Stage-------------1.....");
					model.addAttribute("amedeclarationdtoresponse", declarationDtoResponse);
					model.addAttribute("joinigDate", joinigDate);
					model.addAttribute("userRoleDto", userRoleDto);
					model.addAttribute("loginUserDetails",
							loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
					return "Approver/declaration-from";

				} else if (forcePersonal.getDesignation().contains("DirM")) {
					logger.info(">>>>>>>>>>>>>>>>> Stage-------------2.....");
					model.addAttribute("amedeclarationdtoresponse", declarationDtoResponse);
					model.addAttribute("joinigDate", joinigDate);
					model.addAttribute("userRoleDto", userRoleDto);
					model.addAttribute("refAppetite", refAppetite);
					model.addAttribute("refSleeps", refSleeps);
					model.addAttribute("loginUserDetails",
							loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
					return "UserMenu/declaration-form-user";

				} else if (!forcePersonal.getDesignation().contains("DirM")
						&& !forcePersonal.getDesignation().contains("approver")) {
					logger.info(">>>>>>>>>>>>>>>>> Stage-------------3.....");

					model.addAttribute("amedeclarationdtoresponse", declarationDtoResponse);
					model.addAttribute("joinigDate", joinigDate);
					model.addAttribute("userRoleDto", userRoleDto);
					model.addAttribute("refAppetite", refAppetite);
					model.addAttribute("refSleeps", refSleeps);
					model.addAttribute("loginUserDetails",
							loginUserDetails.getCandicateForcePersonalId(forcepersonalId));
                                logger.info("acwcawcaw");
					return "UserMenu/declaration-form-user";

				}

			}

		}

		return "bootstrap_medical_temp/index";
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST, path = "/AjaxHandlerPart1")
	public ResponseEntity<?> saveDeclaration(@RequestBody AmeDeclarationDtoRequest declarationDtoRequest, Model model,
			HttpSession httpSession,HttpServletRequest  httpServletRequest) {

		AmeDeclarationIndividualModel individualModel = new AmeDeclarationIndividualModel();
		AmeDeclarationIndividualDetails individualDetails = new AmeDeclarationIndividualDetails();

		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		String forcepersonalId = (String) httpSession.getAttribute("forcepersonalId");

		Optional<ForcePersonnelDto> forcePersonnelOptional = forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcepersonalId);
		String ameidGenerator= null;
		String boardYearForAme=medicalBoardRepo.getBoardYear(declarationDtoRequest.getBoardId());
		ForcePersonnelDto forcePersonal;

		if (forcePersonnelOptional.isPresent()) {

			forcePersonal = forcePersonnelOptional.get();
			List<AmeReviewCandidatesList> ameReviewCandidatesLists=ameReviewCandidatesListRepository.getByCandidateForcePersonalIdAndYear(forcepersonalId,boardYearForAme);
			if(ameReviewCandidatesLists.size()>0) {
           	     String ameIdFlag = "RR";	
    			 ameidGenerator = ameUniqueIdGenerationService.generateAmeId(forcePersonal.getForceNo(),
    					forcePersonal.getForceId(), ameIdFlag);
            }
            else {
            String ameIdFlag = "OR";
			  ameidGenerator = ameUniqueIdGenerationService.generateAmeId(forcePersonal.getForceNo(),
					forcePersonal.getForceId(), ameIdFlag);
            }
			
			// ===============get the current Year=================//
             ForcePersonnel forcePersonnel=forcePersonnelRepository.getByForcePersonnelId(forcepersonalId).get();  
			

			// System.out.println(currentYear);

			individualModel.setDeclarationDate(Calendar.getInstance().getTime());
			// individualModel.setsNumber(21);
			individualModel.setName(forcePersonal.getName());
			individualModel.setForcePersonalId(forcePersonal.getForcePersonalId());
			//individualModel.setLastAmeDate(Calendar.getInstance().getTime());
			individualModel.setAmeId(ameidGenerator);
			individualModel.setSelfCertifyAboveInfo(declarationDtoRequest.getSelfCertifyAboveInfo());
			individualModel.setDate_of_joining(forcePersonal.getJoiningDate());
			individualModel.setCurent_new_unit(declarationDtoRequest.getCurentnewunit());
			individualModel.setPrevious_unit(declarationDtoRequest.getPreviousunit());
			individualModel.setLastAmePlace(declarationDtoRequest.getLastAmePlace());
			//======================create on create from and declaration valid status(true) =============//
			
			individualModel.setCreatedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(httpServletRequest));
			individualModel.setCreatedOn(Calendar.getInstance().getTime());
			individualModel.setDeclarationStatusValid(true);
			
			try {
				
				 
		        if (isValidDate(declarationDtoRequest.getLastAmeDate(), "dd-MM-yyyy")) {
		        	individualModel.setLastAmeDate(DateFormatConversion.outputdate(declarationDtoRequest.getLastAmeDate()));
		        	logger.info("previous date formated.......!");
		        } else if (isValidDate(declarationDtoRequest.getLastAmeDate(), "yyyy-MM-dd")) {
		        	individualModel.setLastAmeDate(DateConvert.convertStringToDate(declarationDtoRequest.getLastAmeDate()));
		        	logger.info("date inputted by user formated.......!");
		        }
		        
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//	String boardYearForAme=medicalBoardRepo.getBoardYear(declarationDtoRequest.getBoardId());
			individualModel.setPlace(declarationDtoRequest.getPlace());
		    individualModel.setDesignation(forcePersonnel.getDesignation());
		    individualModel.setRank(forcePersonnel.getRank());
			individualModel.setFlag("Y");
			individualModel.setForceId(declarationDtoRequest.getForceId());
			individualModel.setStatus("P");
			individualModel.setForceId(forcePersonal.getForceId());
			individualModel.setDeclaration_year(Integer.parseInt(boardYearForAme.trim()));
			individualModel.setSelfCertifyAboveInfo(declarationDtoRequest.getSelfCertifyAboveInfo());
			
			logger.debug(">>>>>>>>>>>>>>>>> ForcePersonalId >>>>" + declarationDtoRequest.getForcePersonalId());

			individualDetails.setAmeId(ameidGenerator);
			// individualDetails.setCurentnewunit(forcePersonal.getUnit());
			// individualDetails.setDeclarationDate(Calendar.getInstance().getTime());
			individualDetails.setAccidentinjurysurgery(declarationDtoRequest.getAccidentinjurysurgery());
			individualDetails.setAlcoholintake(declarationDtoRequest.getAlcoholintake());
			individualDetails.setChestpainpalpitation(declarationDtoRequest.getChestpainpalpitation());
			individualDetails.setCopd(declarationDtoRequest.getCopd());
			// individualDetails.setCurentnewunit(forcePersonal.getUnit());
			individualDetails.setDefectivecolorperception(declarationDtoRequest.getDefectivecolorperception());
			individualDetails.setColorperceptioncat(declarationDtoRequest.getColor_perception_cat());
			individualDetails.setCpDate(declarationDtoRequest.getDefectivecolorperception_date());
			individualDetails.setDiabetesmellitus(declarationDtoRequest.getDiabetesmellitus());
			individualDetails.setEpilepsy(declarationDtoRequest.getEpilepsy());
			individualDetails.setForcePersonalId(forcepersonalId);
			individualDetails.setGiddiness(declarationDtoRequest.getGiddiness());
			individualDetails.setHypertension(declarationDtoRequest.getHypertension());
			individualDetails.setIchaemicheart(declarationDtoRequest.getIchaemicheart());
			individualDetails.setMajorailmenthospitalized(declarationDtoRequest.getMajorailmenthospitalized());
			individualDetails.setMenatlinstability(declarationDtoRequest.getMenatlinstability());
			individualDetails.setPersistent_headache(declarationDtoRequest.getPersistentheadache());
			// individualDetails.setPreviousunit(declarationDtoRequest.getPreviousunit());
			individualDetails.setSleep(declarationDtoRequest.getSleep());
			individualDetails.setSmokinghabit(declarationDtoRequest.getSmokinghabit());
			individualDetails.setTb(declarationDtoRequest.getTb());
			individualDetails.setHivTest(declarationDtoRequest.getHivTest());
			individualDetails.setSleep(declarationDtoRequest.getSleep());
			individualDetails.setAppetite(declarationDtoRequest.getAppetite());
			individualDetails.setDiabetesmellitus(declarationDtoRequest.getDiabetesmellitus());
			individualDetails.setPersistent_headache(declarationDtoRequest.getPersistentheadache());
			individualDetails.setMenatlinstability(declarationDtoRequest.getMenatlinstability());
			individualDetails.setSleep(declarationDtoRequest.getSleep());
			individualDetails.setAppetite(declarationDtoRequest.getAppetite());
			individualDetails.setRecentTransferData(declarationDtoRequest.getTransferredid());
			individualDetails.setLastAmeStatus(declarationDtoRequest.getLastAmeStatus());

			// ================change this method===================================//
			// medicalBoardIndividualMappingService.updateDeclarationStatusByForcePersonalId(forcepersonalId);

			if (declarationDtoRequest.getSmokinghabit().equals("Y")) {
				individualDetails
						.setNumberofcigarettes(Integer.parseInt(declarationDtoRequest.getNumberofcigarettes()));
			} else {
				individualDetails.setNumberofcigarettes(0);

			}
			if (declarationDtoRequest.getAlcoholintake().equals("Y")) {
				individualDetails.setQuantityperday(Integer.parseInt(declarationDtoRequest.getQuantityperday()));

			} else {
				individualDetails.setQuantityperday(0);
			}

			// =====================update to
			// tt_appointmentTable===============================================//
			ttAppointmentAmeRepo.updateDeclaationStatusInTTAppointment(
					Integer.parseInt(
							ameParametersRepository.getAmeParameterValue(CommonConstant.AME_DECLARAION_COMPLETED)),
					String.valueOf(boardYearForAme), forcepersonalId, declarationDtoRequest.getBoardId());

			// =======================update
			// Ame_master_status_flow================================//

			Boolean boolean1 = ameApplicationFlowStatusService.createAmeMasterFlowStatusAfterCandidateDeclaration(
					ameidGenerator, forcepersonalId, declarationDtoRequest.getForceNumber(), 0,
					declarationDtoRequest.getBoardId());
			logger.info("AME STATUS CREATED RESULT>>>" + boolean1);

			RequestDto requestDto = new RequestDto();
			requestDto.setAmeDeclarationIndividualDetails(individualDetails);
			requestDto.setDeclarationIndividualModel(individualModel);

			ameDeclarationService.savedeclaration(requestDto);
			return ResponseEntity.status(HttpStatus.OK).body(requestDto);

		}

		// model.addAttribute("ErrorMessage","Error in Processing the data Our Engineers
		// are Working on to resolve the issue...!");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("error");

	}

	@RequestMapping(path = "/show-all-declaration", method = RequestMethod.POST)
	public String datasave(Model model, HttpSession httpSession) {
		String forcePersonnelId;
		UserRoleDto userRoleDto = (UserRoleDto) httpSession.getAttribute("userRoleDto");
		String gazettedNonGazettedFlag = (String) httpSession.getAttribute("gazettedNonGazettedFlag");
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);
		forcePersonnelId = (String) httpSession.getAttribute("forcepersonalId");
		// Optional<ForcePersonnelDto> optional
		// =forcePersonalRepository.getByForcePersonalId(forcepersonalId);
		Optional<ForcePersonnelDto> forcePersonnelOptional = forcePersonnelService
				.getForcePersonnelDetailsByForcePersonnelId(forcePersonnelId);

		String forcepersonalForceId = null;
		if (!forcePersonnelOptional.isEmpty()) {
			forcepersonalForceId = forcePersonnelOptional.get().getForceId().toString();
		}

		model.addAttribute("loginUserDetails", loginUserDetails.getCandicateForcePersonalId(forcePersonnelId));
		model.addAttribute("userRoleDto", userRoleDto);
		model.addAttribute("getMessage", "User With force id :" + forcepersonalForceId);
		return "UserMenu/show-all-decalaration";
	}

	@GetMapping("/declaration-history-status-panel")
	public String getToDeclarationPanel(HttpSession session, Model model) {
		String Name = (String) session.getAttribute("name");
		String forcepersonalId = (String) session.getAttribute("forcepersonalId");
        
		List<UserRoleDto> roleDtosList = (List)session.getAttribute("roleDtosList");

		model.addAttribute("roleDtosList", roleDtosList);

		
		logger.info(
				">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + Name + ">>>> forcePersonalId >>>>>>>>>>>" + forcepersonalId);
		String gazettedNonGazettedFlag = (String) session.getAttribute("gazettedNonGazettedFlag");
		ForcePersonnelDto forcePersonalCandidate = loginUserDetails.getCandicateForcePersonalId(forcepersonalId);

		List<UserDeclarationDto> declarationDtos = userManagementService.getUserDeclarationStatusByBoard(forcepersonalId);
		List<AmeApplicationFlowStatus> EsignAndPrintData= ameApplicationFlowStatusRepo.getAmeSignatureAndReportStatus(forcepersonalId);
		List<AmeApplicationFlowStatusDto> ameApplicationFlowStatusDtosList=new ArrayList<>();
		
		
		//AmeDeclarationIndividualModel ameDeclarationIndividualModel = new AmeDeclarationIndividualModel();
		List<AmeApplicationFlowStatusDto> EsignAndPrintDataFinal=new ArrayList<>();
		for (AmeApplicationFlowStatus ameApplicationFlowStatus : EsignAndPrintData) {
			MedicalBoard medicalBoard= medicalBoardRepo.findByBoardId(ameApplicationFlowStatus.getBoardId());
			AmeApplicationFlowStatusDto ameDto=new AmeApplicationFlowStatusDto();
			ameDto.setAmeId(ameApplicationFlowStatus.getAmeId());
			ameDto.setBoardId(medicalBoard.getBoardId());
			String place=medicalBoard.getPlace();
			Integer ForceNumber=Integer.parseInt(medicalBoard.getBoardAtForceNo());
                logger.info("f>"+ForceNumber+" p"+place);
			ameDto.setBoardAtPlace(unitRepository.getUnitNameByUnitIdAndForceNo(place,ForceNumber));
		    ameDto.setYear(medicalBoard.getBoardYear());
		    ameDto.setForcePersonalId(ameApplicationFlowStatus.getForcepersonalId());
		    ameDto.setEsignByCandidate(ameApplicationFlowStatus.getEsignByCandidate());
		   List<CheckUpList> checkUpLists=checkUpListRepo.findCheckUpListByAmeId(ameApplicationFlowStatus.getAmeId(), CommonConstant.ACTIVE_FLAG_NO);
		    
		  // Map<String, Long> AmeCount=checkUpLists.stream().collect(Collectors.groupingBy(CheckUpList::getAmeId,Collectors.counting()));
		    if(checkUpLists.size()>0) 
		    	
		    	ameDto.setCheckUpListFlag("Y");
		    	
		    else {
		    	if(ameApplicationFlowStatus.getAmeFinalStatus()==1) {
		    		ameDto.setCheckUpListFlag("C");
		    	}
		    	else {
		    	ameDto.setCheckUpListFlag("N");
		    	}
		    	}
		    
		  
		   EsignAndPrintDataFinal.add(ameDto);
			
		}

		if (declarationDtos.isEmpty()) {
			model.addAttribute("message", "No Data Found....................");
		} else {
			for (UserDeclarationDto userDeclarationDto : declarationDtos) {
				model.addAttribute("toDate", userDeclarationDto.getToDate());
				model.addAttribute("fromDate", userDeclarationDto.getFromDate());
				model.addAttribute("declarationMessage", "Ame Declaration can Be filled On or After");

			}
		}

		model.addAttribute("EsignAndPrintData", EsignAndPrintDataFinal);
		model.addAttribute("declarationDtos", declarationDtos);
		model.addAttribute("loginUserDetails", forcePersonalCandidate);
		model.addAttribute("gazettedNonGazettedFlag", gazettedNonGazettedFlag);

		return "UserMenu/user-declaration-panel";
	}
	public static boolean isValidDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);  // To prevent lenient parsing
        try {
            sdf.parse(date);
            return true;  // Date is valid according to the format
        } catch (ParseException e) {
            return false;  // Date is not valid for this format
        }
    }

}
