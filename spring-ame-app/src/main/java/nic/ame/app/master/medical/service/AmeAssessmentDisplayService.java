package nic.ame.app.master.medical.service;


import java.util.List;
import java.util.Optional;

import nic.ame.app.master.medical.dto.MedExamDtoRequest;
import nic.ame.app.master.medical.model.AmeMasterStatus;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.medical.model.MedicalCheckUpMaster;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;

public interface AmeAssessmentDisplayService  {

	
	public MedExamDtoRequest getMedExamDtoRequestData(String ameId);
	
	public List<AmeDeclarationIndividualModel>displayApplicationUnderProcess();
	
	public Optional<AmeMasterStatus> displayApplicationCompleted(String status);
	
	public List<MedicalCheckUpMaster> checkUpMasters();
	
	public boolean saveCheckUpList(CheckUpList checkUpList);
	
	public String saveNgoAmeList(List<String> list,String controllingForcepersonalId,int forceNo);
	
}
