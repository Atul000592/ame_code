package nic.ame.app.admin.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoardMember;
import nic.ame.app.master.model.ForcePersonnel;


public interface MedicalBoardMemberService {
	
	List<String> groupbyCommitteeId();

	List<MedicalBoardMember> getBoardMembers(String forcePersonalId);
	
	Optional<MedicalBoardMember> getBoardMemberForNgoPersonal(String roleName,String forceNo,String unit);
	
	List<MedicalBoardMemberDto> listOfBoardByForcePersonalId(String forcePersonnel);
	List<MedicalBoardMemberDto> listOfBoardByLoginForcePersonnelBoardId(String boardId,String forcePersonnelId);
	
	MedicalBoardDetailDto medicalBoardDetailByBoardId(String boardId);
	
	
	MedicalBoardMemberDto findCreatedByDetailsByBoardId(String boardId);
	
	
	MedicalBoardDetailDto findMedicalBoardDetailsByCreatedByAndBoardId(String boardId);
	
	MedicalBoardDetailDto findBoardDetailsByBoardId(String boardId);
	
	List<MedicalBoardMemberDto> findBoardDetailsListByLoginForcePersonal(String loginInForcePersonalId);

	Map<String,Long> findReserveByBoardIdAndRoleName(String boardId, String roleNameId);
	
	List<MedicalBoardMemberDto> getlistOfReservePersonalByBoardIdAndAlternateMedicalRoleId(String boardId,String roleId);

	void updateTransferOFMedicalRoleByBoardMember(MedicalBoardMemberDto medicalBoardMemberDto);
	
	List<MedicalBoardMemberDto> getListOfAllBoardMembersByBoardId(String boardId);

	List<MedicalBoardMemberDto> getListOfAllBoardMembersByBoardIdOnlyActiveMember(String boardId);

	//List<ForcePersonnel> getStatusesByForcePersonnelIdAndRole(String forcePersonnelId, String roleCode);
}
