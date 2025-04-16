package nic.ame.app.master.serviceImpl;


import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.admin.model.MedicalBoard;
import nic.ame.app.admin.model.RefRoleMedical;
import nic.ame.app.admin.repository.MedicalBoardRepo;
import nic.ame.app.admin.repository.RefRoleMedicalRepo;
import nic.ame.app.master.model.AmeApprovalProcess;
import nic.ame.app.master.model.AmeApprovalStatus;
import nic.ame.app.master.repository.AmeApprovalProcessRepository;
import nic.ame.app.master.repository.AmeApprovalStatusRepository;
import nic.ame.app.master.service.AmeApprovalProcessService;


@Service
public class AmeApprovalProcessServiceImpl implements AmeApprovalProcessService{


	
	@Autowired
	private AmeApprovalProcessRepository ameApprovalProcessRepository;
	
	@Autowired
	private RefRoleMedicalRepo refRoleMedicalRepo;
	
	@Autowired
	private MedicalBoardRepo medicalBoardRepo;
	
	@Autowired
	private AmeApprovalStatusRepository ameApprovalStatusRepository;
	
	@Override
	public AmeApprovalProcess updateStatus(String LoginForcePersonnel, String receiverRoleCode, String remark,
			int statusCode,String ameId,String senderRoleCode,String boardId) {
		AmeApprovalProcess ameApprovalProcessSaved=this.ameApprovalProcessRepository.findByAmeId(ameId);
		if(ameApprovalProcessSaved==null) {
			AmeApprovalProcess ameApprovalProcess=new AmeApprovalProcess();
			
			ameApprovalProcess.setAmeId(ameId);
		
			ameApprovalProcess.setCreatedOn(Calendar.getInstance().getTime());
		
			ameApprovalProcess.setActiveStatus(true);
			
			
			Optional<RefRoleMedical> refRoleMedicalReceiverOptional=refRoleMedicalRepo.findById(Long.parseLong(receiverRoleCode));
			ameApprovalProcess.setReceiverRoleCode(refRoleMedicalReceiverOptional.get());
			
			Optional<RefRoleMedical> refSenderRoleCodeOptional=refRoleMedicalRepo.findById(Long.parseLong(senderRoleCode));
			ameApprovalProcess.setSenderRoleCode(refSenderRoleCodeOptional.get());
			
			MedicalBoard board=new MedicalBoard();
			board=medicalBoardRepo.findByBoardId(boardId);
			ameApprovalProcess.setBoardId(board);
			
			ameApprovalProcess.setCreatedBy(LoginForcePersonnel);
			
			ameApprovalProcess.setCreatedOn(Calendar.getInstance().getTime());
		    ameApprovalProcess.setSenderRemark(remark);
			
			AmeApprovalStatus ameApprovalStatus;
			Optional<AmeApprovalStatus> optional = ameApprovalStatusRepository.findById(statusCode);

			if (optional.isPresent()) {
			     ameApprovalStatus = optional.get();  // Managed entity
			    ameApprovalProcess.setAmeApprovalStatus(ameApprovalStatus);
			    ameApprovalProcessRepository.save(ameApprovalProcess);
			} else {
			    System.out.println("AmeApprovalStatus with the given statusCode does not exist.");
			}

			
			return ameApprovalProcess;
	
		
		}else {
			ameApprovalProcessSaved.setCreatedOn(Calendar.getInstance().getTime());
		
			ameApprovalProcessSaved.setActiveStatus(true);
			
			
			Optional<RefRoleMedical> refRoleMedicalReceiverOptional=refRoleMedicalRepo.findById(Long.parseLong(receiverRoleCode));
			ameApprovalProcessSaved.setReceiverRoleCode(refRoleMedicalReceiverOptional.get());
			ameApprovalProcessSaved.setrCode("2");
			Optional<RefRoleMedical> refSenderRoleCodeOptional=refRoleMedicalRepo.findById(Long.parseLong(senderRoleCode));
			ameApprovalProcessSaved.setSenderRoleCode(refSenderRoleCodeOptional.get());
			ameApprovalProcessSaved.setCreatedBy(LoginForcePersonnel);
			
			ameApprovalProcessSaved.setCreatedOn(Calendar.getInstance().getTime());
			ameApprovalProcessSaved.setSenderRemark(remark);
			
			AmeApprovalStatus ameApprovalStatus;
			Optional<AmeApprovalStatus> optional = ameApprovalStatusRepository.findById(statusCode);

			if (optional.isPresent()) {
			     ameApprovalStatus = optional.get();  // Managed entity
			     ameApprovalProcessSaved.setAmeApprovalStatus(ameApprovalStatus);
			    ameApprovalProcessRepository.save(ameApprovalProcessSaved);
			} else {
			    System.out.println("AmeApprovalStatus with the given statusCode does not exist.");
			}

			
			return ameApprovalProcessSaved;
	
		}
	
	}

	

}
