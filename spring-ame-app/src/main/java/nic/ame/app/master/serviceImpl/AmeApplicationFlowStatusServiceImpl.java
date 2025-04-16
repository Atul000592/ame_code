package nic.ame.app.master.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.controller.AdminLoginController;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.master.dto.AmeApplicationFlowStatusDto;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.service.AmeAssessmentServicePart_2;
import nic.ame.app.master.model.AmeApplicationFlowStatus;
import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.repository.AmeApprovalProcessRepository;
import nic.ame.app.master.repository.AmeParametersRepository;
import nic.ame.app.master.repository.AmeReviewCandidatesListRepository;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.service.AmeApplicationFlowStatusService;
import nic.ame.constant.CommonConstant;

@Service
public class AmeApplicationFlowStatusServiceImpl implements AmeApplicationFlowStatusService {

	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Autowired
	private AmeParametersRepository ameParametersRepository;
	
	@Autowired
	private AmeAssessmentServicePart_2 ameAssessmentServicePart_2;
	@Autowired
	private RankRepo rankRepo;
	
	@Autowired
	private AmeReviewCandidatesListRepository ameReviewCandidatesListRepository;
	
	
	@Autowired
	private AmeApprovalProcessRepository ameApprovalProcessRepo;
	
	@Override
	public boolean createApplicationFlowStatus(String ameId, String forcePersonalId,String unit,Integer forceNo) {
		
		AmeApplicationFlowStatus ameApplicationFlowStatus=new AmeApplicationFlowStatus();
		ameApplicationFlowStatus.setAmeId(ameId);
		ameApplicationFlowStatus.setForcepersonalId(forcePersonalId);
		ameApplicationFlowStatus.setForceId(forcePersonnelRepository.getForceIdByForcePersonalId(forcePersonalId));
		ameApplicationFlowStatus.setAppointmentFlag(Integer.parseInt(ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG)));
		ameApplicationFlowStatus.setForceNo(forceNo);
		ameApplicationFlowStatus.setUnit(unit);
		ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
		
		return true;
	}

	

	
	@Override
	public AmeApplicationFlowStatus getApplicationFlowDataByAmeId(String AmeId) {
		
		    Optional<AmeApplicationFlowStatus> optional=ameApplicationFlowStatusRepo.findByAmeId(AmeId);
	       if(!optional.isEmpty()) {
	    	   return optional.get();
	       }
		     
		    return null;
	}

	@Override
	public boolean updateApplicationFlowStatus(AmeApplicationFlowStatus ameApplicationFlowStatus) {
		ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
		
		return true;
	}

	@Override
	public List<AmeApplicationFlowStatusDto> ameApplicationFlowStatusDtos(String boardId) {
		List<Object> getdto=ameApplicationFlowStatusRepo.getAmeApplicationFlowStatusListByBoardId(boardId.trim());
		List<AmeApplicationFlowStatusDto> ameApplicationFlowStatusDtosList = new ArrayList<>();
		
		if(getdto.isEmpty()) {
			return ameApplicationFlowStatusDtosList;
		}
       	Iterator<Object> iterator=getdto.iterator();
       
		while(iterator.hasNext()) {
       		Object[] obj=(Object[]) iterator.next();
       		AmeApplicationFlowStatusDto ameApplicationFlowStatusDto=new AmeApplicationFlowStatusDto();
			
			if (obj[0] != null) {
                ameApplicationFlowStatusDto.setCheckUpListFlag(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				 ameApplicationFlowStatusDto.setForceId((String.valueOf(obj[1])).trim()); 
			}
			if (obj[2] != null) {
				 ameApplicationFlowStatusDto.setName(String.valueOf(obj[2]).trim());
				
			}
			if (obj[3] != null) {
				 ameApplicationFlowStatusDto.setRank(String.valueOf(obj[3]).trim());
				}
			if (obj[4] != null) {
				ameApplicationFlowStatusDto.setDesignation(String.valueOf(obj[4]).trim());             
			}
			if (obj[5] != null) {
				 ameApplicationFlowStatusDto.setDeclarationDate((Date)obj[5]);
			}
			if (obj[6] != null) {
				 ameApplicationFlowStatusDto.setForcePersonalId((String.valueOf(obj[6])).trim());
			}
			if (obj[7] != null) {
				 ameApplicationFlowStatusDto.setAmeId(String.valueOf(obj[7]).trim());
			}
			if (obj[8] != null) {
				 ameApplicationFlowStatusDto.setBoardId(String.valueOf(obj[8]).trim());
			}
			if (obj[9] != null) {
				 ameApplicationFlowStatusDto.setIrlaNo(String.valueOf(obj[9]).trim());
			}
			
			
			
			ameApplicationFlowStatusDtosList.add(ameApplicationFlowStatusDto);
       	}
		return ameApplicationFlowStatusDtosList;
	}
	
	
	
	
	@Override
	public List<AmeApplicationFlowStatusDto> getAmeApplicationFlowStatusListUploadCompletedByBoardId(String boardId) {
		List<Object> getdto=ameApplicationFlowStatusRepo.getAmeApplicationFlowStatusListUploadCompletedByBoardId(boardId.trim());
		List<AmeApplicationFlowStatusDto> ameApplicationFlowStatusDtosList = new ArrayList<>();
		
		if(getdto.isEmpty()) {
			return ameApplicationFlowStatusDtosList;
		}
       	Iterator<Object> iterator=getdto.iterator();
       
		while(iterator.hasNext()) {
       		Object[] obj=(Object[]) iterator.next();
       		AmeApplicationFlowStatusDto ameApplicationFlowStatusDto=new AmeApplicationFlowStatusDto();
			
			if (obj[0] != null) {
                ameApplicationFlowStatusDto.setCheckUpListFlag(String.valueOf(obj[0]).trim());
			}
			if (obj[1] != null) {
				 ameApplicationFlowStatusDto.setForceId((String.valueOf(obj[1])).trim()); 
			}
			if (obj[2] != null) {
				 ameApplicationFlowStatusDto.setName(String.valueOf(obj[2]).trim());
				
			}
			if (obj[3] != null) {
				 ameApplicationFlowStatusDto.setRank(rankRepo.findById(Integer.parseInt(String.valueOf(obj[3]).trim())).get().getRankFullName());
				}
			if (obj[4] != null) {
				ameApplicationFlowStatusDto.setDesignation(rankRepo.findById(Integer.parseInt(String.valueOf(obj[4]).trim())).get().getRankFullName());             
			}
			if (obj[5] != null) {
				 ameApplicationFlowStatusDto.setDeclarationDate((Date)obj[5]);
			}
			if (obj[6] != null) {
				 ameApplicationFlowStatusDto.setForcePersonalId((String.valueOf(obj[6])).trim());
			}
			if (obj[7] != null) {
				 ameApplicationFlowStatusDto.setAmeId(String.valueOf(obj[7]).trim());
			}
			if (obj[8] != null) {
				 ameApplicationFlowStatusDto.setBoardId(String.valueOf(obj[8]).trim());
			}
			if (obj[9] != null) {
				 ameApplicationFlowStatusDto.setIrlaNo(String.valueOf(obj[9]).trim());
			}
			
			
			
			ameApplicationFlowStatusDtosList.add(ameApplicationFlowStatusDto);
       	}
		return ameApplicationFlowStatusDtosList;
	}
	
	@Override
	public List<AmeApplicationFlowStatusDto> ameListOfCandidateForDealingHand(String unit, Integer forceNo) {
		
		List<Object> getdto=ameApplicationFlowStatusRepo.getWaitingListOfCandidateForDealingHand(unit ,forceNo);
		List<AmeApplicationFlowStatusDto> ameApplicationFlowStatusDtosList = new ArrayList<>();
		
		if(getdto.isEmpty()) {
			return ameApplicationFlowStatusDtosList;
		}
       	Iterator<Object> iterator=getdto.iterator();
       
		while(iterator.hasNext()) {
       		Object[] obj=(Object[]) iterator.next();
       		AmeApplicationFlowStatusDto ameApplicationFlowStatusDto=new AmeApplicationFlowStatusDto();
			if (obj[0] != null) {
				 ameApplicationFlowStatusDto.setForceId((String.valueOf(obj[0])).trim()); 
			}
			if (obj[1] != null) {
				 ameApplicationFlowStatusDto.setName((String.valueOf(obj[1])).trim());
			}
			
			if (obj[2] != null) {
				 ameApplicationFlowStatusDto.setRank( String.valueOf(obj[2]).trim() );
			}
			if (obj[3] != null) {
				 ameApplicationFlowStatusDto.setDesignation(String.valueOf(obj[3]).trim());
			}
			
			if (obj[4] != null) {
				 ameApplicationFlowStatusDto.setDeclarationDate((Date)(obj[4]));                 
			}
			if (obj[5] != null) {
				 ameApplicationFlowStatusDto.setForcePersonalId(String.valueOf(obj[5]).trim());
			}
			if (obj[6] != null) {
			 ameApplicationFlowStatusDto.setAmeId(String.valueOf(obj[6]).trim());	
			}
			
			ameApplicationFlowStatusDtosList.add(ameApplicationFlowStatusDto);
       	}
		return ameApplicationFlowStatusDtosList;
	}




	@Override
	public boolean createAmeMasterFlowStatusAfterCandidateDeclaration(String ameId, String forcePersonalId, String unit,
			int forceNo,String boardId) {
		AmeApplicationFlowStatus ameApplicationFlowStatus=new AmeApplicationFlowStatus();
		ameApplicationFlowStatus.setAmeId(ameId);
		ameApplicationFlowStatus.setForcepersonalId(forcePersonalId);
		ameApplicationFlowStatus.setForceId(forcePersonnelRepository.getForceIdByForcePersonalId(forcePersonalId));
		ameApplicationFlowStatus.setAppointmentFlag(Integer.parseInt( ameParametersRepository.getAmeParameterValue(CommonConstant.AME_STATUS_YES_FLAG)));
		ameApplicationFlowStatus.setForceNo(forceNo);
		ameApplicationFlowStatus.setUnit(unit);
		ameApplicationFlowStatus.setBoardId(boardId);
		ameApplicationFlowStatus.setRowIsValid(true);
		ameApplicationFlowStatusRepo.save(ameApplicationFlowStatus);
		
		return true;
	
	}




	@Override
	public boolean checkPhysicalMeasurmentcreateOrNot(String ameId) {
		// TODO Auto-generated method stub
		 Optional<PhysicalMeasurement>optional=ameAssessmentServicePart_2.getPhysicalMeasurmentByAmeId(ameId);
		 if(!optional.isEmpty()) {
			 return true;
		 }
		return false;
	}

	
	@Override
	public int ameDeclarationFormUploadCompleted(String boardId) {
		int count = 0;
		 count=ameApplicationFlowStatusRepo.getAmeApplicationFlowStatusOfAmeFormUploadListByBoardId(boardId.trim());
		return count;
		
	}




	@Override
	public int ameCheckupListProvided(String boardId) {
		
		int count  =0;
		count=ameApplicationFlowStatusRepo.getAmeApplicationFlowStatusOfAmeCheckUpListByBoardId(boardId.trim());
		
		return count; 
		
	}



	@Override
	public int ameDeclarationFormUploadCompletedPending(String boardId) {
		int count = 0;
		 count=ameApplicationFlowStatusRepo.getAmeApplicationFlowStatusOfAmeFormUploadPendingByBoardId(boardId.trim());
		return count;
		
	}




	@Override
	public int ameCheckupListProvidedPending(String boardId) {
		int count = 0;
		 count=ameApplicationFlowStatusRepo.getAmeApplicationFlowStatusOfAmeCheckUpListPendingByBoardId(boardId.trim());
		return count;
	}




	@Override
	public int getTotalAMECount() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTotalAMECount();
		return count;
	}




	@Override
	public int getTodayAMECount() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTodayAMECount();
		return count;
	}
	
	@Override
	public int getTotalAMEAppointment() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTotalAMEAppointment();
		return count;
	}
	
	@Override
	public int getTotalDeclaration() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTotalDeclaration();
		return count;
	}
	
	@Override
	public int getTotalAMEPending() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTotalAMEPending();
		return count;
	}
	
	@Override
	public int getTotalAMECompleted() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTotalAMECompleted();
		return count;
	}
	
	@Override
	public int getTotalAMEReview() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameReviewCandidatesListRepository.getTotalAMEReview();
		return count;
	}
	
	@Override
	public int getTodayAMEAppointment() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTodayAMEAppointment();
		return count;
	}
	
	@Override
	public int getTodayDeclaration() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTodayDeclaration();
		return count;
	}
	
	public int getTodayAMEPending() {
        return ameApplicationFlowStatusRepo.getTodayAMEPending();
    }
	
	@Override
	public int getTodayAMECompleted() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameApplicationFlowStatusRepo.getTodayAMECompleted();
		return count;
	}
	
	@Override
	public int getTodayAMEReview() {
		// TODO Auto-generated method stub
		int count = 0;
		count=ameReviewCandidatesListRepository.getTodayAMEReview();
		return count;
	}




	@Override
	public int getTotalAMEByForce(Integer forceNo) {
	    int result = ameApplicationFlowStatusRepo.getTotalAMEByForce(forceNo);
	    System.out.println("Fetched total AME for force " + forceNo + ": " + result);
	    return result;
	}
	
	@Override
	public int getTodayAMEByForce(Integer forceNo) {
	    int result = ameApplicationFlowStatusRepo.getTodayAMEByForce(forceNo);
	    System.out.println("Fetched total AME for force " + forceNo + ": " + result);
	    return result;
	}



	@Override
	public int getTotalAMEbyForce() {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public int getTodayAMEbyForce() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ForceTotalAMEAppointment(Integer forceNo) {
	    int result = ameApplicationFlowStatusRepo.ForceTotalAMEAppointment(forceNo);
	    System.out.println("Fetched total AME Appointment for force " + forceNo + ": " + result);
	    return result;
	}

	@Override
	public int ForceTotalAMEAppointment() {
	    // Assuming you want to call the method with some default or fixed forceNo
	    Integer defaultForceNo = 1; // Use an appropriate default or fixed value
	    return ForceTotalAMEAppointment(defaultForceNo);
	}


	@Override
	public int forceTotalAMEAppointment(Integer forceNo) {
	    // Delegate to the correctly implemented method
	    return ForceTotalAMEAppointment(forceNo);
	}

	
	@Override
	public int ForceTotalDeclaration(Integer forceNo) {
	    int result = ameApplicationFlowStatusRepo.ForceTotalDeclaration(forceNo);
	    return result;
	}




	@Override
	public int ForceTotalDeclaration() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public int ForceTotalReview(Integer forceNo) {
	    int result = ameReviewCandidatesListRepository.ForceTotalReview(forceNo);
	    System.out.println("Fetched total AME Review for force " + forceNo + ": " + result);
	    return result;
	}

	@Override
	public int ForceTotalPending(Integer forceNo) {
		int result = ameApplicationFlowStatusRepo.ForceTotalPending(forceNo);
	    System.out.println("Fetched total AME Pending for force " + forceNo + ": " + result);
	    return result;
	}




	@Override
	public int forceTotalPending(Integer forceNo) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public int ForceTotalPending() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ForceTotalCompleted(Integer forceNo) {
		int result = ameApplicationFlowStatusRepo.ForceTotalCompleted(forceNo);
	    System.out.println("Fetched total AME Pending for force " + forceNo + ": " + result);
	    return result;
	}

	@Override
	public int ForceTodayAMEAppointment(Integer forceNo) {
	    int result = ameApplicationFlowStatusRepo.ForceTodayAMEAppointment(forceNo);
	    System.out.println("Fetched today AME Appointment for force " + forceNo + ": " + result);
	    return result;
	}
	
	@Override
	public int ForceTodayDeclaration(Integer forceNo) {
	    int result = ameApplicationFlowStatusRepo.ForceTodayDeclaration(forceNo);
	    System.out.println("Fetched today AME Declaration for force " + forceNo + ": " + result);
	    return result;
	}
	
	@Override
	public int ForceTodayPending(Integer forceNo) {
	    int result = ameApplicationFlowStatusRepo.ForceTodayPending(forceNo);
	    System.out.println("Fetched today AME Pending for force " + forceNo + ": " + result);
	    return result;
	}
	
	@Override
	public int ForceTodayCompleted(Integer forceNo) {
	    int result = ameApplicationFlowStatusRepo.ForceTodayCompleted(forceNo);
	    System.out.println("Fetched today AME Completed for force " + forceNo + ": " + result);
	    return result;
	}
	
	@Override
	public int ForceTodayReview(Integer forceNo) {
	    int result = ameReviewCandidatesListRepository.ForceTodayReview(forceNo);
	    System.out.println("Fetched today AME Review for force " + forceNo + ": " + result);
	    return result;
	}
	 public List<String> getStatusByBoardIdsAndRoleId(List<String> boardIds, String roleId) {
	        List<String> ameIds = ameApprovalProcessRepo.findAmeIdByRoleCodeAndBoardId(boardIds, roleId);
	        return ameApplicationFlowStatusRepo.findStatusByAmeId(ameIds);
	    }
}
