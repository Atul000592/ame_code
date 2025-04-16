package nic.ame.app.master.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.repository.AmeApplicationFlowStatusRepo;
import nic.ame.app.master.service.AmaDeclarationCountService;
import nic.ame.app.user.repository.AmeDeclarationIndividualModelRepoUser;

@Service
public class AmaDeclarationCountServiceImpl implements AmaDeclarationCountService {

	@Autowired
	private AmeDeclarationIndividualModelRepoUser ameDeclarationIndividualModelRepoUser;
	
	@Autowired
	private AmeApplicationFlowStatusRepo ameApplicationFlowStatusRepo;
	
	//===============================AMADeclarationPendingListCount==============================//
	
	@Override
	public Integer getAMADeclarationPendingListCount(String boardId) {
		return ameDeclarationIndividualModelRepoUser.findByAMADeclarationPendingListCount(boardId);
	}


	@Override
	public Integer findDataForAMAAppointmentCount(String boardId) {
		
		return ameDeclarationIndividualModelRepoUser.findDataForAMAAppointmentCount(boardId);
	}

	
	//===============================DealingHandPendingListCount==============================//
	
	@Override
	public Integer findDataForDealingHandCount(String unitNo, Integer forceNo) {
		
		return ameApplicationFlowStatusRepo.candidateListForDealingHand(unitNo, forceNo);
	}


	@Override
	public Integer findMyDeclarationCount(String forcePersonalId) {
	   Integer count=ameDeclarationIndividualModelRepoUser.myDeclarationCount(forcePersonalId);
		return count;
	}
	
	
	
	public Integer findTotalDeclarationUnderProcessByBoardId(String boardId) {
		Integer count=ameApplicationFlowStatusRepo.getCompleteDeclarationCountComplete(boardId.trim());
	     return count;
	}
		
	

}
