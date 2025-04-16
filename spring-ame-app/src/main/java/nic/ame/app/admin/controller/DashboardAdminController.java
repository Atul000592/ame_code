package nic.ame.app.admin.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


import org.hibernate.query.NativeQuery.ReturnableResultNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.AjaxResponse;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardIndividualMappingDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.model.QueryData;
import nic.ame.app.admin.model.RefMedicalExamType;
import nic.ame.app.admin.model.RefRoleMedical;

import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.ForcePersonalAdminRepository;
import nic.ame.app.admin.repository.ForceUnitRepo;
import nic.ame.app.admin.repository.MedicalBoardMemberRepo;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.RefMedicalExamTypeRepo;
import nic.ame.app.admin.repository.RefRoleTypeRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.AdminBoardService;
import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;
import nic.ame.app.admin.serviceImpl.ForcePersonalSearchServiceImpl;
import nic.ame.app.admin.serviceImpl.MedicalBoardIndividualMappingServiceImpl;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.model.Login;

import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.LoginRepository;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.constant.CommonConstant;

@Controller
@EnableTransactionManagement
public class DashboardAdminController {

	private Logger logger = LoggerFactory.getLogger(DashboardAdminController.class);

	@Autowired
	private LoginUserDetails loginUserDetails;
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private ForcePersonalAdminRepository forcePersonalRepository;

	@Autowired
	private ForcePersonalSearchServiceImpl forcePersonalSearchService;
	@Autowired
	private MedicalBoardIndividualMappingService medicalBoardIndividualMappingService;

	@Autowired
	private MedicalBoardRepo medicalBoardRepo;

	@Autowired
	private ForcePersonalService forcePersonalService;

	@Autowired
	private RefMedicalExamTypeRepo refMedicalExamTypeRepo;
	@Autowired
	private RefRoleTypeRepo refRoleType;

	@Autowired
	private ForceRepo refForcerepo;
	@Autowired
	ForceUnitRepo forceUnitRepo;
	@Autowired
	private RefForceService refForceService;

	@Autowired
	private AdminBoardService adminBoardService;

	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;

	@Autowired
	private AmeParametersRepository ameParametersRepository;

	
	
	
	
	
	
	@RequestMapping(path = "/get-force-personal-by-unit-doctor", method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToBoardFormation(@RequestParam String forceId, @RequestParam String unitId, Model model,
			HttpSession session) {

		List<ForcePersonalResponeseAjax> personalResponeseAjaxs = new ArrayList<ForcePersonalResponeseAjax>();
		String loginForcepersonalId = (String) session.getAttribute("forcepersonalId");
		

		  String forceName =refForceService.getForceNameByForceId(Integer.parseInt(forceId)); 
		  String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(forceId), unitId.trim());
		  
	
		Map<String,Object> resultMap=new HashMap<>();
		List<ForcePersonalResponeseAjax> unitDoctorList = new ArrayList<ForcePersonalResponeseAjax>();
		unitDoctorList = forcePersonalSearchService.ListOfMedicalCadre(unitId, Integer.parseInt(forceId));
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
		
		if(unitDoctorList.size()<0) {
			resultMap.put("code",0);
			resultMap.put("message","No Record found ...Please contact Unit Admin...! forceName: "+forceName+" and Unit: "+unitName);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(resultMap);
			}else {
				resultMap.put("code",1);
			resultMap.put("unitDoctorList", unitDoctorList);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(resultMap);
			}
		
		
	}

	
	//=============================================attach unit ================================================================//
	
	@RequestMapping(path = "get-force-personal-by-attach-unit-doctor", method = RequestMethod.POST)
	public ResponseEntity<?> sendDataToBoardFormationAttachUnit(@RequestParam String forceId, @RequestParam String unitId, Model model,
			HttpSession session) {

		List<ForcePersonalResponeseAjax> personalResponeseAjaxs = new ArrayList<ForcePersonalResponeseAjax>();
		String loginForcepersonalId = (String) session.getAttribute("forcepersonalId");
		

		  String forceName =refForceService.getForceNameByForceId(Integer.parseInt(forceId)); 
		  String unitName = refForceService.getUnitNameByUnitId(Integer.parseInt(forceId), unitId.trim());
		  
	
		Map<String,Object> resultMap=new HashMap<>();
		List<ForcePersonalResponeseAjax> unitDoctorList = new ArrayList<ForcePersonalResponeseAjax>();
		unitDoctorList = forcePersonalSearchService.ListOfMedicalCadreAttachUnit(unitId, Integer.parseInt(forceId));
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
		
		if(unitDoctorList.size()<0) {
			resultMap.put("code",0);
			resultMap.put("message","No Record found ...Please contact Unit Admin...! forceName: "+forceName+" and Unit: "+unitName);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(resultMap);
			}else {
				resultMap.put("code",1);
			resultMap.put("unitDoctorList", unitDoctorList);
				return ResponseEntity.status(HttpStatus.ACCEPTED).body(resultMap);
			}
		
		
	}
	
	
	
	
	

	@RequestMapping(path = { "/search-all", "/search" },method = RequestMethod.GET)
	public String SearchForcePersonal(Model model, HttpSession session) {

		String loginForcepersonalId = (String) session.getAttribute("forcepersonalId");
		List<RefRoleMedical> roleTypeList = refRoleType.findAll();
		List<RefMedicalExamType> examTypesList = refMedicalExamTypeRepo.findAll();
		List<UnitModel> unitList = unitRepository.findAll();
		List<Force> forceList = refForcerepo.findAll();
		// List<UnitModel> unitList = unitRepository.findAll();
		model.addAttribute("roleTypeList", roleTypeList);
		model.addAttribute("examTypesList", examTypesList);
		model.addAttribute("forceList", forceList);
		model.addAttribute("unitList", unitList);
		// model.addAttribute("unitList", unitList);
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

		return "admin-template/board-formation-search";
	}
	
	

	

	// Committee Details is being saved//
	@RequestMapping(path ="create-medical-board",method = RequestMethod.POST)
	public ResponseEntity<?> addCommitteeMember(Model model, HttpSession session, HttpServletRequest request,
			@RequestBody List<MedicalBoardMemberDto> medicalBoardMemberDtolist) {
		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();
		
		try {
			
			for(MedicalBoardMemberDto medicalBoardMemberDto:medicalBoardMemberDtolist) {
				
				if(medicalBoardMemberDto.getRoleName().equals("0"))
				{
                    errors.put(medicalBoardMemberDto.getName(),medicalBoardMemberDto.getName()+" please provide the role name!");
				
			    }
			
              }
			
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

	// View All Board List

	@RequestMapping(path = "view-board",method = RequestMethod.GET)
	public String viewBoardList( Model model, HttpSession session,HttpServletRequest httpServletRequest) {
		// List<ForcePersonal> forcePersonalList =null
		String loginForcePersonalId = (String) session.getAttribute("forcepersonalId");
		List<MedicalBoardMemberDto> medicalBoardList = medicalBoardMemberService
				.findBoardDetailsListByLoginForcePersonal(loginForcePersonalId);
		String loginForcepersonalId = (String) session.getAttribute("forcepersonalId");
 
		String message=null;
		
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
		if (inputFlashMap != null) {
			message = (String) inputFlashMap.get("message");

		}

		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
		model.addAttribute("medicalBoardList", medicalBoardList);
        model.addAttribute("message", message);
		return "admin-template/view-board-list";
	}

	// ==================View Particular board Details================

    @RequestMapping(value = { "view-board-details", "edit-board-details" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String viewBoardDetailsByBoardId( Model model, String boardId,
			HttpSession session,RedirectAttributes redirectAttributes,HttpServletRequest httpServletRequest) {
		// List<ForcePersonal> forcePersonalList =null
		
		String message=null;
		if(boardId==null) {
			Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
			if (inputFlashMap != null) {
				boardId = (String) inputFlashMap.get("boardId");
				message=(String)inputFlashMap.get("message");

			}
		}
	
		
		System.out.println("=============" + boardId);
		
		String loginForcepersonalId = (String) session.getAttribute("forcepersonalId");
		
		List<MedicalBoardMemberDto> medicalBoardMemberDtoList = medicalBoardIndividualMappingService.getMedicalBoardById(boardId);

		if (medicalBoardMemberDtoList.size() > 0) {

			MedicalBoardDetailDto boardDetailDtos = medicalBoardMemberService.medicalBoardDetailByBoardId(boardId);

			logger.info(medicalBoardMemberDtoList.get(0).getCommitteeId() + "====="+ medicalBoardMemberDtoList.get(0).getForcePersonalId());
			
			MedicalBoardMemberDto createdByDetails = medicalBoardMemberService.findCreatedByDetailsByBoardId(boardId.trim());
			
			StringBuilder createdBy = new StringBuilder();
			createdBy.append(createdByDetails.getIrlaNumber() + " || ").append(createdByDetails.getName() + " || ")
					.append(createdByDetails.getForceName() + " || ").append(createdByDetails.getUnitName());
			String createdByStr = createdBy.toString();
			model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
			model.addAttribute("medicalBoardMemberDtoList", medicalBoardMemberDtoList);
			model.addAttribute("medicalBoard", boardDetailDtos);
			model.addAttribute("createdBy", createdByStr);
			model.addAttribute("boardId", boardId);
			model.addAttribute("message", message);
			return "admin-template/view-board-details";
		} else {
			redirectAttributes.addFlashAttribute("message","No Data to display In the board.......!>>>>>>>>> Board_Id : "+boardId);
			return "redirect:/view-board";

		}
	}

	// ========================Unit-Board-mapping==================

	@RequestMapping(path = "/board-individual-mapping-init",method = RequestMethod.POST)
	public String individualCommitteeMapping(ForcePersonnel forcePersonal, Model model, String keyword) {
		List<ForcePersonnel> forcePersonalList = null;
		List<MedicalBoard> medicalBoardList = medicalBoardRepo.findAll();

		// List<RefRoleType> roleTypeList = refRoleType.findAll();
		// List<RefMedicalExamType> examTypesList = refMedicalExamTypeRepo.findAll();
		List<Force> forceList = refForcerepo.findAll();
		List<UnitModel> unitList = unitRepository.findAll();
		/*
		 * model.addAttribute("roleTypeList", roleTypeList);
		 * model.addAttribute("examTypesList", examTypesList);
		 */
		model.addAttribute("forceList", forceList);
		model.addAttribute("unitList", unitList);
		model.addAttribute("medicalBoardList", medicalBoardList);

		return "admin-template/board-individual-mapping";
	}

	// ========================Unit-Board-mapping==================

	@RequestMapping(path = "/board-unit-mapping",method = RequestMethod.POST)
	public String individualCommitteeUnitMapping(ForcePersonnel forcePersonal, Model model, String keyword) {
		List<ForcePersonnel> forcePersonalList = null;
		List<MedicalBoard> medicalBoardList = medicalBoardRepo.findAll();

		// List<RefRoleType> roleTypeList = refRoleType.findAll();
		// List<RefMedicalExamType> examTypesList = refMedicalExamTypeRepo.findAll();
		List<Force> forceList = refForcerepo.findAll();
		List<UnitModel> unitList = unitRepository.findAll();
		/*
		 * model.addAttribute("roleTypeList", roleTypeList);
		 * model.addAttribute("examTypesList", examTypesList);
		 */
		model.addAttribute("forceList", forceList);
		model.addAttribute("unitList", unitList);
		model.addAttribute("medicalBoardList", medicalBoardList);

		return "admin-template/board-unit-mapping";
	}

// ===================get-force-unitList for corresponding ForceID=========================	

	@RequestMapping(path = "get-force-unitList",method = RequestMethod.POST)
	public ResponseEntity<?> getForceUnitList(Model model, HttpSession session,
			@RequestBody Map<String, Long> forceIdIn) {
		System.out.println("=============forceid input ==============================");
		long forceId = forceIdIn.get("forceId");
		System.out.println("=============forceid input " + forceId + "==============================");
		List<UnitModel> data = forceUnitRepo.findByForceId(forceId);

		return ResponseEntity.status(HttpStatus.OK).body(data);

	}

	// ===============================Board Unit Mapping Prefinal data
	// saved===========================

	@ResponseBody
	@RequestMapping(path = "board-individual-mapping-prefinal",method = RequestMethod.POST)
	public AjaxResponse saveBoardIndividualMappingPrefinal(Model model, HttpSession session, HttpServletRequest request,
			@RequestBody MedicalBoardIndividualMappingDto medicalBoardIndividualMappingDto) {
		AjaxResponse response = new AjaxResponse();
		Map<String, Object> map = new HashMap();
		List<ForcePersonnel> forcePersonalList = null;
		System.out.println("=====rerg=====" + medicalBoardIndividualMappingDto.getBoardId() + "======="
				+ medicalBoardIndividualMappingDto.getUnitId());
		// model.addAttribute("loginUserDetails",
		// loginUserDetails.getLoginUserDetails(loginForcepersonalId));
		MedicalBoard medicalBoard = medicalBoardRepo.getByBoardId(medicalBoardIndividualMappingDto.getBoardId());
		// comment

		/*
		 * model.addAttribute("hospitalList",hospitalList);
		 * model.addAttribute("unitList", unitList);
		 */
		/*
		 * if(result) {
		 * 
		 * return
		 * ResponseEntity.status(HttpStatus.OK).body(medicalBoardIndividualMappingDto);
		 * } else { Map<String,Boolean> finalresult =new HashMap<>();
		 * finalresult.put("result", result); return
		 * ResponseEntity.status(HttpStatus.BAD_REQUEST).body(finalresult); }
		 */
		// MedicalBoard medicalBoard =
		// medicalBoardRepo.getByBoardId(medicalBoardIndividualMappingDto.getBoardId());
		List<MedicalBoardMemberDto> medicalBoardMemberDtoList = medicalBoardIndividualMappingService
				.getMedicalBoardById(medicalBoardIndividualMappingDto.getBoardId());

		forcePersonalList = forcePersonalSearchService.geAllForcePersonalByForceAndUnit(
				medicalBoardIndividualMappingDto.getForceNo(), medicalBoardIndividualMappingDto.getUnitId());

		map.put("medicalBoard", medicalBoard);
		map.put("medicalBoardMemberDtoList", medicalBoardMemberDtoList);
		map.put("forcePersonalList", forcePersonalList);

		response.setStatusCode(1);
		response.setData(map);

		return response;

	}

	// ===============================Board Unit Mapping Prefinal data
	// saved===========================

	@RequestMapping(path = "board-unit-mapping-prefinal",method = RequestMethod.POST)
	public ResponseEntity<?> saveBoardUnitMappingPrefinal(Model model, HttpSession session, HttpServletRequest request,
			@RequestBody MedicalBoardIndividualMappingDto medicalBoardIndividualMappingDto) {

		System.out.println("==========" + medicalBoardIndividualMappingDto.getBoardId() + "======="
				+ medicalBoardIndividualMappingDto.getUnitId());

		boolean result = medicalBoardIndividualMappingService.saveBoardunitMapping(medicalBoardIndividualMappingDto,
				request);

		/*
		 * model.addAttribute("hospitalList",hospitalList);
		 * model.addAttribute("unitList", unitList);
		 */
		if (result) {

			return ResponseEntity.status(HttpStatus.OK).body(medicalBoardIndividualMappingDto);
		} else {
			Map<String, Boolean> finalresult = new HashMap<>();
			finalresult.put("result", result);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(finalresult);
		}

	}


	@RequestMapping(path = "/unit-hospital-ame", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getIndividual(HttpServletRequest request, @RequestParam String ameId) {
		System.out.println("ameId" + ameId);
		return new ResponseEntity<>("Hello", HttpStatus.OK);
	}

	

	



	@GetMapping("/sign-out-admin")
	public String signoutadmin(HttpServletRequest request) {

		HttpSession httpSession = request.getSession(false);
		if (httpSession != null) {
			httpSession.invalidate();
		}
		return "bootstrap_medical_temp/index";
 
	}

	@PostMapping("/role-map-to-bda-dashboard")
	public String admindashboard(HttpServletRequest request, Model model, @RequestParam("rCode") int rCode,
			@RequestParam("forcePersonalId") String LoginForcePersonalId) {

		HttpSession httpSession = request.getSession(false);
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");

		if (loginForcePersonalId.equals(LoginForcePersonalId)) {
			logger.info("SAME USER LOGGED IN THE SYSTEM.....");
		}
		request.getSession().setAttribute("rCode", rCode);
		int boardCount = adminBoardService.getBoardCountCreated(loginForcePersonalId);
		int boardMappedCount = adminBoardService.getTotalMemberMapToAllBoardFormByAdmin(loginForcePersonalId);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("boardMappedCount", boardMappedCount);

		httpSession.setAttribute("loginForceName", refForceService
				.getForceNameByForceId(loginUserDetails.getLoginUserDetails(loginForcePersonalId).getForceNo()));
		model.addAttribute("userType", "BOARD DETAILING AUTHORITY");
		model.addAttribute("loginForceName", refForceService
				.getForceNameByForceId(loginUserDetails.getLoginUserDetails(loginForcePersonalId).getForceNo()));

		return "admin-template/dashboard";

	}

	@PostMapping(path = "/map-individual-to-board-NOG")
	public String individualToBoardMappingNgo(HttpServletRequest httpServletRequest, Model model,@RequestParam("boardId") String boardId) {
		HttpSession httpSession = httpServletRequest.getSession(false);

		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		
		List<Force> forceList = refForcerepo.findAll();
		
		String nonGazzated=ameParametersRepository.getAmeParameterValue(CommonConstant.NON_GAZETTED);
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo.getMedicalBoardByCreatedByForcePersonalByGflag(loginForcepersonalId,Integer.parseInt(nonGazzated));

		List<DropDownDto> downDtosList = new ArrayList<>();
		for (MedicalBoard medicalBoard : medicalBoardsList) {
			DropDownDto downDto = new DropDownDto();
			StringBuffer buffer = new StringBuffer();
			String boardPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoard.getBoardAtForceNo().trim()),medicalBoard.getPlace());
			buffer = buffer.append(medicalBoard.getBoardId()).append(" || " +boardPlace)
					.append(" || " + medicalBoard.getUsedFor() + " || " +medicalBoard.getBoardYear()+" || ");
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
		model.addAttribute("downDtosList", downDtosList);
		model.addAttribute("boardId",boardId);
		

		return "admin-template/map-individual-to-board-for-ngo";
	}



	@PostMapping(path = "/map-individual-to-board-for-go")
	public String individualToBoardMappingGo(@RequestParam("boardId") String boardId, HttpServletRequest httpServletRequest, Model model) {
		HttpSession httpSession = httpServletRequest.getSession(false);

		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		List<Force> forceList = refForcerepo.findAll();


		String gazzatedFlag=ameParametersRepository.getAmeParameterValue(CommonConstant.GAZETTED);
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo.getMedicalBoardByCreatedByForcePersonalByGflag(loginForcepersonalId,Integer.parseInt(gazzatedFlag) );

		List<DropDownDto> downDtosList = new ArrayList<>();
		for (MedicalBoard medicalBoard : medicalBoardsList) {
			DropDownDto downDto = new DropDownDto();
			StringBuffer buffer = new StringBuffer();
			String boardPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoard.getBoardAtForceNo().trim()),medicalBoard.getPlace());
			buffer = buffer.append(medicalBoard.getBoardId()).append(" || " +boardPlace)
					.append(" || " + medicalBoard.getUsedFor() + " || " +medicalBoard.getBoardYear()+" || ");
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

		model.addAttribute("downDtosList", downDtosList);
		model.addAttribute("boardId", boardId);
		

		return "admin-template/map-individual-to-board-for-go";
	}

//============bda-dashboard====================//
	@GetMapping("/bda-dashboard")
	public String bdaDashBoard(HttpServletRequest request, Model model) {

		HttpSession httpSession = request.getSession(false);
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		int boardCount = adminBoardService.getBoardCountCreated(loginForcePersonalId);
		int boardMappedCount = adminBoardService.getTotalMemberMapToAllBoardFormByAdmin(loginForcePersonalId);
		model.addAttribute("boardCount", boardCount);
		model.addAttribute("boardMappedCount", boardMappedCount);
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");

		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));

		return "admin-template/dashboard";

	}

}
