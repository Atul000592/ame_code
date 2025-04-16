package nic.ame.app.admin.service;



import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.dto.ForcePersonalResponeseAjax;
import nic.ame.app.admin.dto.MedicalBoardIndividualMappingDto;
import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.admin.model.MedicalBoardIndividualMapping;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.model.ForcePersonnel;

public interface MedicalBoardIndividualMappingService {
	
	public MedicalBoardIndividualMapping getBoardDetailByForcePersonalId(String forcePersonalId);
	
	public int getForcePersonalBoardMappingStatus(String forcePersonalId,String year);
	
    public boolean saveBoardunitMapping(MedicalBoardIndividualMappingDto medicalBoardIndividualMappingDto,HttpServletRequest request);
	
	public void updateDeclarationStatusByForcePersonalId(String forcePersonalId);
	
	public List<MedicalBoardIndividualMapping> getAllForcePersonalByBoardId(String boardId);
	
 	public boolean saveMedicalBoardMember(List<MedicalBoardMemberDto> medicalBoardMemberDtolist,String forcePersonalLogin,HttpServletRequest request);
 	
	public List<MedicalBoardMemberDto> getMedicalBoardById(String boardId);
	
	public void saveForcePersonnelToBoard(List<String> forcePersonalList,String boardId,int forceNumber,String unitNumber,HttpSession httpSession,HttpServletRequest httpServletRequest);
	
	public  List<ForcePersonnel> getBoardMappedDataByBoardId(String boardId,int forceNo,String unitNo);
	
	public List<ForcePersonalResponeseAjax> getMappedMamberByBoardIdOnly(String boardId);
	
	public void deleteExistingMappedForcePersonalFromBoard(String forcePersonal,String boardId);
	
	public List<String> getListofForcePersonalForAppointmentData(String forcePersonalId);
	
	public List<MedicalBoardMemberDto> getAmeAppointmentPendingListByBoardId(String boardId,int statusCode);
	
	public List<String> getForcePersonnelIdForUnMapping(String BoardId);
	

	
}
