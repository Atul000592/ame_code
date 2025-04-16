package nic.ame.app.master.service;

import java.util.List;
import java.util.Optional;

import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.master.medical.dto.MedicalBoardMemberRoleDto;

public interface BoardMemberRoleService {
	
	
	
	public List<MedicalBoardMemberRoleDto> getBoardMemberRoleForNGO(String forcePersonalId,HttpSession httpSession);

	public List<MedicalBoardMemberRoleDto> getBoardMemberRoleForGO(String forcePersonalId,HttpSession httpSession);
	
	Optional<MedicalBoardMemberRoleDto> getSingleBoardMemberRoleForGO(String forcePersonalId,String boardId ,HttpSession httpSession);
	 

}
