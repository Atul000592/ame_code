package nic.ame.app.master.service;



import nic.ame.app.master.model.AmeApprovalProcess;



public interface AmeApprovalProcessService {

	
	public  AmeApprovalProcess updateStatus(String senderForcePersonnelId,String recieverRoleCode,String remark,int statusCode,String ameId,String senderRoleCode,String boardId);
	
}
