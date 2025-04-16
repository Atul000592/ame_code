package nic.ame.app.admin.service;

import java.util.List;

import nic.ame.app.admin.dto.MedicalBoardDetailDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.dto.MedicalMemberMappedDto;

public interface AdminBoardService {

	
	
	
	public int getBoardCountCreated(String loginForcePersonalId);
    public int getTotalMemberMapToAllBoardFormByAdmin(String loginForcePersonalId);
    public List<MedicalBoardMemberDto> getListOfAllMappedPersonalByCreatedByBoardPersonal(String loginForcePersonalId);
    
    public List<MedicalMemberMappedDto> getAllMemberMappedToBoardByCreatedByForcePersonalWithAppointmentStatus(String loginForcePersonalId);
    public List<MedicalMemberMappedDto> getAllMemberMappedToBoardByCreatedByForcePersonalWithApplicationStatusFlow(String loginForcePersonalId);
    
}
