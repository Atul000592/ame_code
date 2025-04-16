package nic.ame.app.master.medical.service;

import java.util.List;
import java.util.Optional;

import nic.ame.app.admin.dto.MedicalBoardMemberDto;
import nic.ame.app.master.dto.AmeFinalDetailDto;
import nic.ame.app.master.medical.model.AmeFinalReportDetails;

public interface AmeFinalDetailService {
	
	
	
	public boolean saveAmeFinalDetail(AmeFinalDetailDto ameFinalDetailDto,List<MedicalBoardMemberDto> medicalBoardMemberDtos,int categoryDownCode12,int categoryDownCode24);
	public boolean saveAmeFinalDetailGO(AmeFinalDetailDto ameFinalDetailDto,List<MedicalBoardMemberDto> 
	medicalBoardMemberDtos,int categoryDownCode12,int categoryDownCode24,String rCode,String loggedInUserForcepersonnelId);

	
	List<AmeFinalDetailDto> listOfAmeStatusCompletedOrUnderReview(String boardID,int ameFinalStatus,int finalUploadFlag);
	
	AmeFinalDetailDto ameFinalDeclarationMap(String candidateForcePersonalId,String ameId);
	
    AmeFinalDetailDto ameFinalFilledReportDisplay(String candidateForcePersonalId,String ameId);
    
    
    Optional<AmeFinalReportDetails> findByAmeId(String ameId);
	
}
