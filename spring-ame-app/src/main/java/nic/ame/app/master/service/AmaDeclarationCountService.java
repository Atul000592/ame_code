package nic.ame.app.master.service;

public interface AmaDeclarationCountService {

	/*===================Ama count =================*/
	public Integer	getAMADeclarationPendingListCount(String boardId);
	public Integer findDataForAMAAppointmentCount(String boardId);
	
	
	/*===================dealinghand count =================*/
	Integer findDataForDealingHandCount(String unitNo,Integer forceNo);
	Integer findMyDeclarationCount(String forcePersonalId);
	
	Integer findTotalDeclarationUnderProcessByBoardId(String boardId);
}
