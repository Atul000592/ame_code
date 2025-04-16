package nic.ame.app.user.service;

import java.util.List;

import nic.ame.app.master.dto.AmeDeclarationIndividualDto;
import nic.ame.app.master.medical.model.AmeDeclarationFiles;
import nic.ame.app.master.model.AmeDeclarationIndividualModel;
import nic.ame.app.user.dto.AmeFinalResultCandidate;

public interface UserService {

	
	public String getStatus(String ameId,String forcePersonalId);
		
	public List<AmeDeclarationFiles> findByAmeId(String ameId);
	
	public boolean findFileExist(String ameId);
	
	public boolean getAmeDeclartionForCurrentYear(String forceId,long currentYear);
	
	public List<AmeDeclarationIndividualDto> showUserAllDeclaration(String forcePersonalId);
	 
	public List<AmeFinalResultCandidate> getAmeResultForCandidateForcePersonalId(String forcePersonalId);
	public List<AmeFinalResultCandidate> getAmeResultForCandidateForcePersonalIdGo(String forcePersonalId);
     
	public List<AmeFinalResultCandidate> getAmeResultForSameYearCandidateForcePersonalId(String ameId);
}
