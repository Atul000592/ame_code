package nic.ame.app.master.service;



import java.util.List;

import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.model.AmeApplicationFlowStatus;

public interface AmeApplicationFlowStatusService {

	
	 boolean createApplicationFlowStatus(String ameId,String forcePersonalId,String unit,Integer forceNo);
	 
     AmeApplicationFlowStatus getApplicationFlowDataByAmeId(String AmeId);
     
     boolean updateApplicationFlowStatus(AmeApplicationFlowStatus ameApplicationFlowStatus);
     
     List <AmeApplicationFlowStatusDto>ameApplicationFlowStatusDtos(String boardId);
     
     List <AmeApplicationFlowStatusDto>ameListOfCandidateForDealingHand(String unit,Integer forceNo);
     
     boolean createAmeMasterFlowStatusAfterCandidateDeclaration(String ameId,String forcePersonalId,String unit,int forceNo,String boardId);
     
     public boolean checkPhysicalMeasurmentcreateOrNot(String ameId);
     
     int   ameDeclarationFormUploadCompletedPending(String boardId);
     
	int  ameDeclarationFormUploadCompleted(String boardId);
	
	int ameCheckupListProvidedPending(String boardId);

	int   ameCheckupListProvided(String boardId);
    public List<AmeApplicationFlowStatusDto> getAmeApplicationFlowStatusListUploadCompletedByBoardId(String boardId);
    
    public int getTotalAMECount();
    
    public int getTodayAMECount();
    
    public  int getTotalAMEAppointment();
    
    public int getTotalDeclaration();
    
    public int getTotalAMEPending();
    
    public  int getTotalAMECompleted();
    
    public int getTotalAMEReview();

    public int getTodayAMEAppointment();

    public int getTodayDeclaration();
	    
    public   int getTodayAMEPending();
	    
    public   int getTodayAMECompleted();
	    
    public   int getTodayAMEReview();

	public int getTotalAMEByForce(Integer forceNo);
	
	public int getTodayAMEByForce(Integer forceNo);

	public int getTotalAMEbyForce();

	public int getTodayAMEbyForce();

	int ForceTotalAMEAppointment();

	int forceTotalAMEAppointment(Integer forceNo);

	int ForceTotalAMEAppointment(Integer forceNo);
	
	int ForceTotalDeclaration(Integer forceNo);

	int ForceTotalDeclaration();
	
	int forceTotalPending(Integer forceNo);
	
	int ForceTotalReview(Integer forceNo);

	int ForceTotalPending(Integer forceNo);

	int ForceTotalPending();
	
	int ForceTotalCompleted(Integer forceNo);
	
	int ForceTodayAMEAppointment(Integer forceNo);
	
	int ForceTodayDeclaration(Integer forceNo);
	
	int ForceTodayPending(Integer forceNo);
	
	int ForceTodayCompleted(Integer forceNo);
	
	int ForceTodayReview(Integer forceNo);

	public List<String> getStatusByBoardIdsAndRoleId(List<String> boardIds, String roleId);
	
	
}
