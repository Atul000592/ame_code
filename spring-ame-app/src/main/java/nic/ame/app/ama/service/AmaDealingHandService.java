package nic.ame.app.ama.service;

import java.util.List;

import nic.ame.app.ama.dto.DealingHandDto;

public interface AmaDealingHandService{

	
	
	List<DealingHandDto> getDealingHandByBoardId(String boardId);
	
	int countDeclarationCompletePendingForApproval(String boardId);
	
	int countPendingForUploadAmeAndCheckUpList(String boardId);
	
	
	List<DealingHandDto> listOfDeclarationCompletePendingForApproval(String boardId);
	
	List<DealingHandDto> listOfPendingForUploadAmeAndCheckUpList(String boardId);
	
	boolean getExistingDealingHandByBoardIdAndForcePersonalId(String boardId,String forcePersonalId);
}
