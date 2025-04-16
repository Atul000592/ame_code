package nic.ame.app.admin.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.admin.dto.UserRoleMapDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.admin.model.QueryData;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.admin.repository.ForcePersonalAdminRepository;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.MedicalBoardIndividualMappingRepo;
import nic.ame.app.admin.service.ForcePersonalSearchService;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.master.dto.BoardMapper;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.MedicalBoardDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.TTAppointmentAme;
import nic.ame.app.master.repository.TTAppointmentAmeRepo;
import nic.ame.app.master.service.ForcePersonnelService;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.constant.CommonConstant;
import nic.ame.constant.RegexValidation;
import nic.ame.master.util.GetIpAddressClient;
import nic.ame.master.util.RandomCodeGenerator;
import nic.ame.master.util.RegexPatternHanlder;

@Controller
public class AdminMasterController {

	private static final String String = null;

	Logger logger = LogManager.getLogger(AdminLoginController.class);

	@Autowired
	private ForcePersonalSearchService forcePersonalSearchService;

	@Autowired
	private ForceRepo forcerepo;

	@Autowired
	private MedicalBoardRepo medicalBoardRepo;

	@Autowired
	private RefForceService refForceService;

	@Autowired
	private MedicalBoardIndividualMappingService boardIndividualMappingService;

	@Autowired
	private MedicalBoardIndividualMappingService medicalBoardIndividualMappingService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private RankRepo rankRepo;
	@Autowired
	private ForcePersonnelService forcePersonnelService;
	
	@Autowired
	private ForcePersonalAdminRepository forcePersonnelAdminRepository;
	
	@Autowired
	private MedicalBoardIndividualMappingRepo medicalBoardIndividualMappingRepo;
	
	@Autowired
	private TTAppointmentAmeRepo appointmentAmeRepository;
	
	@RequestMapping(path = "/get-force-personal-by-unit-id", method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToAjax(@RequestParam String queryData, Model model) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MedicalBoard medicalBoard=medicalBoardRepo.findByBoardId(myQueryData.getBoardId());
		
		List<ForcePersonalResponeseAjax> personalResponeseAjax = forcePersonalSearchService.getMappingListToboardForcePersonal
				(Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo(),medicalBoard.getGazettedFlag(),medicalBoard.getBoardYear());
		
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),myQueryData.getUnitNo().trim());
		
	
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = forcerepo.findAll();
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo.findAll();
		/*
		 * model.addAttribute("personalResponeseAjax", personalResponeseAjax);
		 * model.addAttribute("forceList", forceList);
		 * model.addAttribute("medicalBoardsList", medicalBoardsList);
		 * model.addAttribute("forceNo", myQueryData); model.addAttribute("unitId",
		 * unitNo);
		 */
		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		map.put("medicalBoardsList", medicalBoardsList);
		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	@RequestMapping(path = "/get-force-personal-by-irlaNo-for-mapping", method = RequestMethod.POST)
	public ResponseEntity<?> searchByIrlaNoForMapping(@RequestParam String queryData, Model model) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		Map<String, Object> map = new HashMap<>();
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		MedicalBoard medicalBoard=medicalBoardRepo.findByBoardId(myQueryData.getBoardId());
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo.findAll();
		Optional<ForcePersonnelDto> forcePersonnelDetails=forcePersonnelService.getForcePersonnelDetailsByIrlaNo(myQueryData.getIralNo().trim());
		
		if(!forcePersonnelDetails.isEmpty()) {
		ForcePersonnelDto forcePersonnelDto=forcePersonnelDetails.get();
		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();
		
		int isPresent=forcePersonalSearchService.isMappedCountByIrlaNoAndBoardYear(myQueryData.getIralNo(), medicalBoard.getBoardYear(),1);
		
		if(isPresent==1) {
			map.put("personalResponeseAjax", personalResponeseAjax);
		}else {
			ForcePersonalResponeseAjax ajax=new ForcePersonalResponeseAjax();
			ajax.setForceName(forcePersonnelDto.getForceName());
			ajax.setIrlaNumber(forcePersonnelDto.getForceId());
			ajax.setName(forcePersonnelDto.getName());
			ajax.setGender(forcePersonnelDto.getGender());
			ajax.setDesignation(forcePersonnelDto.getDesignation());
			ajax.setUnitName(forcePersonnelDto.getUnitName());
			ajax.setForcePersonalId(forcePersonnelDto.getForcePersonalId());
			personalResponeseAjax.add(ajax);
			map.put("personalResponeseAjax", personalResponeseAjax);
		}
				/*
				 * String forceName =
				 * refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo
				 * ()));
				 * 
				 * String unitName =
				 * refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()
				 * ),forcePersonnelDetails.get().getUnit().trim()); int responseCode=0;
				 */
	
		

		List<Force> forceList = forcerepo.findAll();
		
		/*
		 * model.addAttribute("personalResponeseAjax", personalResponeseAjax);
		 * model.addAttribute("forceList", forceList);
		 * model.addAttribute("medicalBoardsList", medicalBoardsList);
		 * model.addAttribute("forceNo", myQueryData); model.addAttribute("unitId",
		 * unitNo);
		 */ map.put("unitId", forcePersonnelDetails.get().getUnit());
			map.put("forceNo", myQueryData.getForceNo());
			map.put("medicalBoardsList", medicalBoardsList);
			map.put("forceList", forceList);
			
	        map.put("responseCode",1);
		}else {
			 map.put("unitId", myQueryData.getUnitNo());
			 map.put("forceNo", myQueryData.getForceNo());
			
			 map.put("responseCode",0);
		}
		
		
		
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	
	
	
	
	
	

	@RequestMapping(path = "get-force-personal-by-unit-id-non-medic", method = RequestMethod.POST)
	public ResponseEntity<?> getNonDoctorDataForSelectedUnit(@RequestParam String queryData, Model model) {

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
			fPA.setDesignation(rankRepo.findById(forcePersonal.getDesignation()).get().getRankFullName());
			if(forcePersonal.getGender().equalsIgnoreCase("M"))
				fPA.setGender("Male");
			else
				fPA.setGender("Female");

				
			fPA.setUnitName(unitName);
			personalResponeseAjax.add(fPA);

		}
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = forcerepo.findAll();
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo.findAll();
		/*
		 * model.addAttribute("personalResponeseAjax", personalResponeseAjax);
		 * model.addAttribute("forceList", forceList);
		 * model.addAttribute("medicalBoardsList", medicalBoardsList);
		 * model.addAttribute("forceNo", myQueryData); model.addAttribute("unitId",
		 * unitNo);
		 */
		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		map.put("medicalBoardsList", medicalBoardsList);
		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	
	
	
	
	
	
	
	
	
	
	@PostMapping("/send-request-to-map-forcepersonal-to-board")
     public ResponseEntity<?> saveAndSendNgoAmeRequestToAma(Model model, HttpSession session,
			@RequestBody List<String> data,HttpServletRequest httpServletRequest) {

		String forcepersonalId = (String) session.getAttribute("forcepersonalId");
        Map<String,Object> response = new HashMap<String, Object>();
		int size = data.size();
		String unit = data.get(size - 1);
		String force = data.get(size - 2);
		String boardId = data.get(size - 3);

		data.remove(size - 1);
		data.remove(size - 2);
		data.remove(size - 3);
		medicalBoardIndividualMappingService.saveForcePersonnelToBoard(data, boardId, Integer.parseInt(force.trim()), unit,session,httpServletRequest);
		List<ForcePersonalResponeseAjax> dataList = medicalBoardIndividualMappingService.getMappedMamberByBoardIdOnly(boardId);
		if(dataList.size()>0) {
			response.put("isValid", true);
			response.put("message", "Members have been mapped to the board: "+boardId);
			response.put("dataList",dataList);
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PostMapping("/get-mapped-data-by-boardId")
	ResponseEntity<?> getMappedDataByBoardId(@RequestParam String boardId) {
		List<ForcePersonalResponeseAjax> list = new ArrayList<>();

		List<ForcePersonalResponeseAjax> forcePersonalsList = medicalBoardIndividualMappingService.getMappedMamberByBoardIdOnly(boardId);
		
	

		new ResponseEntity<>(HttpStatus.OK);
		return ResponseEntity.ok(forcePersonalsList);
	}

	@PostMapping("list-of-force-personnel-to-unmap-from-board")
	ResponseEntity<?> unMapindividualFromBoard(@RequestParam String boardId) {
		
		List<String> boardIdAndForcePersonnelIdForUnMapping = medicalBoardIndividualMappingService.getForcePersonnelIdForUnMapping(boardId);
		
		List<ForcePersonnelDto> forcePersonnelListForUnmapping= new ArrayList<ForcePersonnelDto>();
		
		Iterator<String> itr=boardIdAndForcePersonnelIdForUnMapping.iterator();
		
		while(itr.hasNext()) {
			ForcePersonnelDto forcePersonnel= new ForcePersonnelDto();
			forcePersonnel=forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(itr.next()).get();
			forcePersonnelListForUnmapping.add(forcePersonnel);
		}
		
		new ResponseEntity<>(HttpStatus.OK);
		return ResponseEntity.ok(forcePersonnelListForUnmapping);

	}
	
	@PostMapping("get-board-and-force-personal-details")
	ResponseEntity<?> getBoardAndForcePersonnelDetails(@RequestBody Map<String, Object> data, HttpSession httpSession,
			HttpServletRequest request) {
		String boardId = (String) data.get("boardId");
		String forcePersonnelId = (String) data.get("forcePersonnelId");
		
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		
		Optional<MedicalBoard> medicalBoardOptionalData= medicalBoardRepo.getByBoardIdOptional(boardId);
		String medicalBoardPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoardOptionalData.get().getBoardAtForceNo()),medicalBoardOptionalData.get().getPlace());
		String medicalBoardForceName=forcerepo.getForceName(Integer.parseInt(medicalBoardOptionalData.get().getBoardAtForceNo()));
		Optional<ForcePersonnelDto> forcePersonnelDto= forcePersonnelService.getForcePersonnelDetailsByForcePersonnelId(forcePersonnelId);
		
		
		if(medicalBoardOptionalData.isPresent()&&forcePersonnelDto.isPresent()) {
			
			
			MedicalBoardDto medicalBoardData=BoardMapper.toDTO(medicalBoardOptionalData.get());
			
			medicalBoardData.setPlace(medicalBoardPlace);
			medicalBoardData.setBoardAtForceNo(medicalBoardForceName);
			response.put("isValid", true);
			response.put("boardMemberDetails", medicalBoardData);
			response.put("forcePersonnelDetails", forcePersonnelDto.get());
			
			
		}
		
		else {
			response.put("isValid", false);
			response.put("message", "No Record Found!");
			
		}
		return ResponseEntity.ok(response);

	}
	
	
	
	
	@PostMapping("do-unmap-force-personnel-from-board")
	ResponseEntity<?> doUnmapForcePersonnelFromBoard(@RequestBody Map<String, Object> data, HttpSession httpSession,
			HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		
		try {
			
			String boardId = (String) data.get("boardId");
			String forcePersonnelId = (String) data.get("forcePersonnelId");
			String unmappingReason = (String) data.get("unmappingReason");
			String loggedInUserForcepersonnelId = (String) httpSession.getAttribute("forcepersonalId");

		
			
			
			if(unmappingReason.isEmpty()&&unmappingReason.isBlank()) {
				errors.put("unmappingReason",RegexValidation.EMPTY_FIELD_MESSAGE);
			}
			if(!RegexPatternHanlder.isValidString(unmappingReason)) {
				errors.put("unmappingReason",RegexValidation.SPECIAL_CHARATER_ERROR_MESSAGE);
			}
			
			
			
			if (!errors.isEmpty()) {
				response.put("isValid", false);
				response.put("errors", errors);
				return ResponseEntity.ok(response);

			}
			else {
			
			Optional<MedicalBoardIndividualMapping> checkForcePersonnelForUnmapping = medicalBoardIndividualMappingRepo
					.findByBoardIdAndForcePersonalIdAndIsMappingValid(boardId, forcePersonnelId,1);
				
		 
		 
			if (checkForcePersonnelForUnmapping.isPresent()) {
				
				MedicalBoardIndividualMapping medicalBoardIndividualMappingData = new MedicalBoardIndividualMapping();
				medicalBoardIndividualMappingData =	checkForcePersonnelForUnmapping.get();
				
				if(medicalBoardIndividualMappingData.getAppointmentStatus()==0) {
					medicalBoardIndividualMappingData.setIsMappingValid(0);
				medicalBoardIndividualMappingData.setUnmappedBy(loggedInUserForcepersonnelId);
				medicalBoardIndividualMappingData
						.setUnmappedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
				medicalBoardIndividualMappingData.setUnmappedOn(Calendar.getInstance().getTime());
				medicalBoardIndividualMappingData.setUnmappingReason(unmappingReason);
				

				if (medicalBoardIndividualMappingRepo.save(medicalBoardIndividualMappingData) != null) {
					response.put("isValid", true);
					response.put("message", "Force Personnel unmapped successfully");
					return ResponseEntity.ok(response);
				}				

				else {
					response.put("isValid",false);
					response.put("message", "Error occured!");
					return ResponseEntity.ok(response);
				}
			}
				else {
					Optional<TTAppointmentAme> appointmentAmeOptional	= appointmentAmeRepository.getByBoardIdAndForcePersonnelIdAndDeclarationInvalidFlagAndDeclarationStatus(boardId, forcePersonnelId, 0,0);
					
					TTAppointmentAme appointmentAmeData = new TTAppointmentAme();
					
					if(appointmentAmeOptional.isPresent()) {
						
						appointmentAmeData = appointmentAmeOptional.get();
						appointmentAmeData.setDeclarationInvalidFlag(0);
						appointmentAmeData.setDeclarationStatus(0);
						appointmentAmeData.setIsAppointmentValid(0);
						
						medicalBoardIndividualMappingData.setAppointmentStatus(0);
						medicalBoardIndividualMappingData.setIsMappingValid(0);
						medicalBoardIndividualMappingData.setUnmappedBy(loggedInUserForcepersonnelId);
						medicalBoardIndividualMappingData
								.setUnmappedFrom(GetIpAddressClient.getIpAddressFromHeaderClient(request));
						medicalBoardIndividualMappingData.setUnmappedOn(Calendar.getInstance().getTime());
						medicalBoardIndividualMappingData.setUnmappingReason(unmappingReason);
						
						
						if (medicalBoardIndividualMappingRepo.save(medicalBoardIndividualMappingData) != null&& appointmentAmeRepository.save(appointmentAmeData)!=null) {
							response.put("isValid", true);
							response.put("message", "Force Personnel unmapped successfully");
							return ResponseEntity.ok(response);
						}				

						else {
							response.put("isValid",false);
							response.put("message", "Error occured!");
							return ResponseEntity.ok(response);
						}
						
					}
					else {
						response.put("isValid",false);
						response.put("message", "Error occured!");
						return ResponseEntity.ok(response);
					}
					
					
				}
					

			}

			else {
				response.put("isValid",false);
				response.put("message", "Error occured!");
				return ResponseEntity.ok(response);
			}
		}
			
			
			
		
		}

		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"Error Occured! Please carefully fill the form again, if the error persists contact the developement team!");
		}

		

	}
	
	

	@PostMapping("/get-new-role-to-user")
	ResponseEntity<?> saveNewUserRole(@RequestBody List<UserRoleMapDto> data, HttpServletRequest httpServletRequest,
			Model model) {
		HttpSession httpSession = httpServletRequest.getSession(false);
		if (httpSession.equals(null)) {

			logger.info("session invalidated...");

		}
		String loginforcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		logger.info("forcePersonalId :" + loginforcepersonalId);
		String message = userRoleService.saveNewUserRole(data, loginforcepersonalId);
		List<UserRoleDto> userRoleDtos = userRoleService.getListOfUserWithRoles(loginforcepersonalId);
		model.addAttribute("userRoleDtosList", userRoleDtos);
		Map<String, Object> map = new HashMap<>();
		map.put("message", message);
		new ResponseEntity<>(HttpStatus.OK);
		return ResponseEntity.ok(map);
	}

	@PostMapping("/get-new-role-to-user-temp")
	ResponseEntity<?> saveNewUserRoleTemp(@RequestBody List<UserRoleMapDto> data, HttpServletRequest httpServletRequest,
			Model model) {
		HttpSession httpSession = httpServletRequest.getSession(false);
		if (httpSession.equals(null)) {

			logger.info("session invalidated...");

		}
		String loginforcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		logger.info("forcePersonalId :" + loginforcepersonalId);
		String transactionalId = userRoleService.saveNewUserRoleTemp(data, loginforcepersonalId,RandomCodeGenerator.getTransactionalIdUserRole());
		List<UserRoleDto> userRoleDtos = userRoleService.getListOfUserWithRolesTemp(new BigInteger(transactionalId.trim()));
		Map<String, Object> map = new HashMap<>();
		map.put("message", "data save temp table..");
		map.put("transactionalId",transactionalId);
		map.put("userRoleDtosTemp",userRoleDtos);
		new ResponseEntity<>(HttpStatus.OK);
		return ResponseEntity.ok(map);
	}

	
	
	//====================get data for attach unit================================//
	
	@RequestMapping(path = "/get-force-personal-by-attach-unit-id", method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToAjaxForAttachUnit(@RequestParam String queryData, Model model) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<ForcePersonalResponeseAjax> personalResponeseAjax = new ArrayList<>();
		
		MedicalBoard medicalBoard=medicalBoardRepo.findByBoardId(myQueryData.getBoardId());
		
		
		List<ForcePersonnel> list = 
				forcePersonalSearchService.getMappingListToboardForcePersonalAttachUnit
				(Integer.parseInt(myQueryData.getForceNo()), myQueryData.getUnitNo(),medicalBoard.getGazettedFlag(),medicalBoard.getBoardYear());
		
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),myQueryData.getUnitNo().trim());
		
		for (ForcePersonnel forcePersonal : list) {
			ForcePersonalResponeseAjax fPA = new ForcePersonalResponeseAjax();
			fPA.setForceName(forceName);
			fPA.setForcePersonalId(forcePersonal.getForcePersonalId());
			fPA.setIrlaNumber(forcePersonal.getForceId());
			fPA.setName(forcePersonal.getName());
			fPA.setDesignation(rankRepo.findById(forcePersonal.getDesignation()).get().getRankFullName());
			fPA.setUnitName(unitName);
			fPA.setAttachUnit(forcePersonal.getAttachUnit());
			if(forcePersonal.getGender().equalsIgnoreCase("M"))
				fPA.setGender("Male");
			else
				fPA.setGender("Female");

				
			
			personalResponeseAjax.add(fPA);

		}
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = forcerepo.findAll();
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo.findAll();
		/*
		 * model.addAttribute("personalResponeseAjax", personalResponeseAjax);
		 * model.addAttribute("forceList", forceList);
		 * model.addAttribute("medicalBoardsList", medicalBoardsList);
		 * model.addAttribute("forceNo", myQueryData); model.addAttribute("unitId",
		 * unitNo);
		 */
		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		map.put("medicalBoardsList", medicalBoardsList);
		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	
	@RequestMapping(path = "/get-force-personal-by-unit-id-assign-board", method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToAjaxToAssignBoard(@RequestParam String queryData, Model model) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//MedicalBoard medicalBoard=medicalBoardRepo.findByBoardId(myQueryData.getBoardId());
		
		List<ForcePersonalResponeseAjax> personalResponeseAjax = forcePersonalSearchService.getMappingListToboardForcePersonalAssignBoard
				(Integer.parseInt(myQueryData.getForceNo()),myQueryData.getUnitNo());
		
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),myQueryData.getUnitNo().trim());
		
	
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = forcerepo.findAll();
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo.findAll();
		/*
		 * model.addAttribute("personalResponeseAjax", personalResponeseAjax);
		 * model.addAttribute("forceList", forceList);
		 * model.addAttribute("medicalBoardsList", medicalBoardsList);
		 * model.addAttribute("forceNo", myQueryData); model.addAttribute("unitId",
		 * unitNo);
		 */
		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		//map.put("medicalBoardsList", medicalBoardsList);
		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}


	@RequestMapping(path = "get-force-personal-by-attach-unit-id-assign-board", method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToAjaxToAssignBoardAttachUnit(@RequestParam String queryData, Model model) {

		ObjectMapper myMapper = new ObjectMapper();
		QueryData myQueryData = null;
		try {
			myQueryData = myMapper.readValue(queryData, QueryData.class);

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//MedicalBoard medicalBoard=medicalBoardRepo.findByBoardId(myQueryData.getBoardId());
		
		List<ForcePersonalResponeseAjax> personalResponeseAjax = forcePersonalSearchService.getMappingListToboardForcePersonalAssignBoardforAttachUnit
				(Integer.parseInt(myQueryData.getForceNo()),myQueryData.getUnitNo());
		
		String forceName = refForceService.getForceNameByForceId(Integer.parseInt(myQueryData.getForceNo()));
		
		String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(myQueryData.getForceNo()),myQueryData.getUnitNo().trim());
		
	
		Map<String, Object> map = new HashMap<>();

		List<Force> forceList = forcerepo.findAll();
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo.findAll();
		/*
		 * model.addAttribute("personalResponeseAjax", personalResponeseAjax);
		 * model.addAttribute("forceList", forceList);
		 * model.addAttribute("medicalBoardsList", medicalBoardsList);
		 * model.addAttribute("forceNo", myQueryData); model.addAttribute("unitId",
		 * unitNo);
		 */
		map.put("unitId", myQueryData.getUnitNo());
		map.put("forceNo", myQueryData.getForceNo());
		//map.put("medicalBoardsList", medicalBoardsList);
		map.put("forceList", forceList);
		map.put("personalResponeseAjax", personalResponeseAjax);

		return ResponseEntity.status(HttpStatus.OK).body(map);
	}

	
}
