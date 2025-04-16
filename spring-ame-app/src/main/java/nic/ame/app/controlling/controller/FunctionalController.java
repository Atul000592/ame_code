package nic.ame.app.controlling.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.admin.model.UnitModel;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.UnitRepository;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.master.dto.NgoControllerToAmaDto;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.service.ForcePersonalService;

@Controller
public class FunctionalController {

	Logger logger=LogManager.getLogger(FunctionalController.class);
	
	
	@Autowired
	private ForceRepo forceRepo;
	
	@Autowired
	private UnitRepository unitRepository;
	
	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;
	
	@Autowired
	private ForcePersonalService forcePersonalService;
	
	@RequestMapping("/send-data-to-ngoAme-master")
	public String saveDataTongoAmeMaster(@RequestParam("transId") String transationalId,Model model,HttpSession httpSession) {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+transationalId);
		String controllingForcePersonalId=(String) httpSession.getAttribute("forcepersonalId");
	   List<ForcePersonnel> fPNgoAmaList= forcePersonalService.findNgoAmaListWithTransactionalId(transationalId);
	   model.addAttribute("fPNgoAmaList", fPNgoAmaList);
		model.addAttribute("transationalId", transationalId);
		List<Force> forceList=forceRepo.findAll();
		model.addAttribute("forceList", forceList);
		model.addAttribute("controllingForcePersonalId",controllingForcePersonalId);
		
		return"medical-controlling/Assign-Ama-to-ngo";
	}
	
	

	
	@RequestMapping(path = "/get-unit-by-forceNo")
	public ResponseEntity<?>  getDistrictByState(@RequestParam ("forceId") int forceId){
		List<UnitModel> unitList=unitRepository.getUnitDataByForceId(forceId);
		return ResponseEntity.status(HttpStatus.OK).body(unitList);
	}
	
	@RequestMapping(path = "/get-unit-by-forceNo-board")
	public ResponseEntity<?>  getDistrictByStateBoard(@RequestParam ("forceId") int forceId,
			@RequestParam (value =  "boardId" ,required = false) String boardId){
		List<UnitModel> unitList=unitRepository.getUnitDataByForceId(forceId);
		Map<String,Object> map=new HashMap<>();
		map.put("unitList", unitList);
		map.put("boardId",boardId);
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	
	@RequestMapping(path = "/get-ama-by-force-unit")
	public ResponseEntity<?>  getAmaByForceAndUnit(@RequestParam ("unitId") String unitId,@RequestParam("forceId") int forceId){
	   NgoControllerToAmaDto controllerToAmaDto=new NgoControllerToAmaDto();
	   String roleName="ama";
	   Optional<MedicalBoardMember> optional=medicalBoardMemberService.getBoardMemberForNgoPersonal(roleName,String.valueOf(forceId), unitId);
	   if(!optional.isEmpty()) {
		   controllerToAmaDto.setAmaForcePersonalId(optional.get().getForcePersonalId());
		   controllerToAmaDto.setAmaName(optional.get().getName());
		   controllerToAmaDto.setBoardId(optional.get().getBoardId());
	   }
		return ResponseEntity.status(HttpStatus.OK).body(controllerToAmaDto);
	}
	
	
	
	@RequestMapping(path = "send-list-to-ngo-ama")
	public String sendListToAmaApproval(@RequestParam("amaForcePersonalId") String amaForcePersonalId ,
			@RequestParam("controllingForcePersonalId") String controllingForcePersonalId ,
			@RequestParam("boardId") String boardId,
			@RequestParam("transatinalId") String transatinalId) {
		
		System.out.println(">>>>>>ameForcePersonalId>>>>>>"+amaForcePersonalId+">>>>>>>controllingForcePersonalId>>>>>>"+controllingForcePersonalId+">>>>>>>>boardId>>>>>>>>"+boardId+"\n>>>>transatinalId>>>"+transatinalId);

		
		
		return"";
	}
	
	@RequestMapping(path = "get-back-dashboard-ngo-decline-ch")
	public String getBackToHomeNgoProcessDecline() {
		return"";
	}
	
	@RequestMapping(path = "/terminate-data-save-in-ngo-list")
	public String terminateTheSaveNgoListWithGivenTransactionalId(Model model,@RequestParam("transatinalId") String transatinalId) {
		
		model.addAttribute("message","Process of sending data to ama With Transactinal Id "+ transatinalId+" has been terminated");
		
		return"";
	}
}
