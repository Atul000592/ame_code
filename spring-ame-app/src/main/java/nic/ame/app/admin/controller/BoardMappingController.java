package nic.ame.app.admin.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.DropDownDto;
import nic.ame.app.admin.dto.MedicalBoardAndMemberDetailsDtos;
import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoard;

import nic.ame.app.admin.repository.MedicalBoardRepo;

import nic.ame.app.admin.service.MedicalBoardIndividualMappingService;
import nic.ame.app.admin.service.MedicalBoardMemberService;
import nic.ame.app.admin.service.RefForceService;

import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.constant.CommonConstant;




@Controller
public class BoardMappingController {
	
	
	private final Logger LOGGER =LoggerFactory.getLogger(BoardMappingController.class);
	

	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired
	private RefForceService refForceService;
	
	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@Autowired
	private MedicalBoardIndividualMappingService medicalBoardIndividualMappingService;
	
	@Autowired
	private MedicalBoardMemberService medicalBoardMemberService;
	
	@GetMapping("view-mapped-individual-to-board")
	public String viewMappedIndividualToBoard(HttpServletRequest httpServletRequest,Model model) {
		HttpSession httpSession = httpServletRequest.getSession(false);

		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
	//	List<Force> forceList = refForcerepo.findAll();
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo
				.getMedicalBoardByCreatedByForcePersonal(loginForcepersonalId);

		List<DropDownDto> downDtosList = new ArrayList<>();
		for (MedicalBoard medicalBoard : medicalBoardsList) {
			DropDownDto downDto = new DropDownDto();
			StringBuffer buffer = new StringBuffer();
			String boardPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoard.getBoardAtForceNo().trim()),medicalBoard.getPlace());
			buffer = buffer.append(medicalBoard.getBoardId()).append(" || " +boardPlace)
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
	//	model.addAttribute("forceList", forceList);

		model.addAttribute("downDtosList", downDtosList);
		

	
		
		return "admin-template/view-mapped-individual-to-board";
	}
	
	@GetMapping(value = "unmap-individual-to-board")
	public String postMethodName(HttpServletRequest httpServletRequest,Model model) {
		HttpSession httpSession = httpServletRequest.getSession(false);

		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");

		List<MedicalBoard> medicalBoardsList = medicalBoardRepo
				.getMedicalBoardByCreatedByForcePersonal(loginForcepersonalId);

		List<DropDownDto> downDtosList = new ArrayList<>();
		for (MedicalBoard medicalBoard : medicalBoardsList) {
			DropDownDto downDto = new DropDownDto();
			StringBuffer buffer = new StringBuffer();
			String boardPlace=refForceService.getUnitNameByUnitId(Integer.parseInt(medicalBoard.getBoardAtForceNo().trim()),medicalBoard.getPlace());
			buffer = buffer.append(medicalBoard.getBoardId()).append(" || " +boardPlace)
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
	

		model.addAttribute("downDtosList", downDtosList);
		
		return "admin-template/unmap-individual-to-board";
	}

	@GetMapping("map-individual-to-board-for-ngo-data")
	public String mapIndividualToBoardForNgoData (Model model,HttpSession httpSession) {
		
		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo
				.getMedicalBoardByCreatedByForcePersonalByGflag(loginForcepersonalId,CommonConstant.NON_GAZETTED_FLAG);
		List<MedicalBoardAndMemberDetailsDtos> boardAndMemberDetailsDtos=new ArrayList<>();
		
		for (MedicalBoard medicalBoard : medicalBoardsList) {
			MedicalBoardDetailDto boardDetailDtos = medicalBoardMemberService.medicalBoardDetailByBoardId(medicalBoard.getBoardId());
			List<MedicalBoardMemberDto> medicalBoardMemberDtoList = medicalBoardIndividualMappingService.getMedicalBoardById(medicalBoard.getBoardId());
			MedicalBoardAndMemberDetailsDtos mbmdd=new MedicalBoardAndMemberDetailsDtos();
			mbmdd.setMedicalBoardDetailDto(boardDetailDtos);
			mbmdd.setMedicalBoardMemberDtoList(medicalBoardMemberDtoList);
			MedicalBoardMemberDto createdByDetails = medicalBoardMemberService.findCreatedByDetailsByBoardId(medicalBoard.getBoardId().trim());
			StringBuilder createdBy = new StringBuilder();
			createdBy.append(createdByDetails.getIrlaNumber() + " || ").append(createdByDetails.getName() + " || ")
					.append(createdByDetails.getForceName() + " || ").append(createdByDetails.getUnitName());
			String createdByStr = createdBy.toString();
			mbmdd.setCreatedBy(createdByStr);
			boardAndMemberDetailsDtos.add(mbmdd);
		}
		model.addAttribute("BoardType","NGO's Board");
		model.addAttribute("uri","map-individual-to-board-NOG");
		model.addAttribute("boardAndMemberDetailsDtos", boardAndMemberDetailsDtos);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
		return "admin-template/map-individual-to-board-for-ngo-data";
	}
	
	@GetMapping("map-individual-to-board-for-go-data")
	public String mapIndividualToBoardForGoData (Model model,HttpSession httpSession) {
		
		String loginForcepersonalId = (String) httpSession.getAttribute("forcepersonalId");
		
		List<MedicalBoard> medicalBoardsList = medicalBoardRepo
				.getMedicalBoardByCreatedByForcePersonalByGflag(loginForcepersonalId,CommonConstant.GAZETTED_FLAG);
		List<MedicalBoardAndMemberDetailsDtos> boardAndMemberDetailsDtos=new ArrayList<>();
		
		for (MedicalBoard medicalBoard : medicalBoardsList) {
			MedicalBoardDetailDto boardDetailDtos = medicalBoardMemberService.medicalBoardDetailByBoardId(medicalBoard.getBoardId());
			List<MedicalBoardMemberDto> medicalBoardMemberDtoList = medicalBoardIndividualMappingService.getMedicalBoardById(medicalBoard.getBoardId());
			MedicalBoardAndMemberDetailsDtos mbmdd=new MedicalBoardAndMemberDetailsDtos();
			mbmdd.setMedicalBoardDetailDto(boardDetailDtos);
			mbmdd.setMedicalBoardMemberDtoList(medicalBoardMemberDtoList);
			MedicalBoardMemberDto createdByDetails = medicalBoardMemberService.findCreatedByDetailsByBoardId(medicalBoard.getBoardId().trim());
			StringBuilder createdBy = new StringBuilder();
			createdBy.append(createdByDetails.getIrlaNumber() + " || ").append(createdByDetails.getName() + " || ")
					.append(createdByDetails.getForceName() + " || ").append(createdByDetails.getUnitName());
			String createdByStr = createdBy.toString();
			mbmdd.setCreatedBy(createdByStr);
			boardAndMemberDetailsDtos.add(mbmdd);
		}
		model.addAttribute("uri","map-individual-to-board-for-go");
		model.addAttribute("BoardType","Gazetted Officers Board");
		model.addAttribute("boardAndMemberDetailsDtos", boardAndMemberDetailsDtos);
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcepersonalId));
		return "admin-template/map-individual-to-board-for-ngo-data";
	}
	

}
