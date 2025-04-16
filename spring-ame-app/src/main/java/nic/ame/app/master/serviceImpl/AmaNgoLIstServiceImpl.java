package nic.ame.app.master.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


import nic.ame.app.master.model.ForcePersonnel;
import nic.ame.app.master.repository.ForcePersonnelRepository;
import nic.ame.app.master.repository.NGOAMECandidateRequestDetailsRepo;
import nic.ame.app.master.repository.NGOAMERequestRepo;
import nic.ame.app.master.service.AmaNgoLIstService;

public class AmaNgoLIstServiceImpl implements AmaNgoLIstService {

	@Autowired
	private NGOAMECandidateRequestDetailsRepo ngoameCandidateRequestDetailsRepo; 
	
	@Autowired
	private NGOAMERequestRepo ngoameRequestRepo;
	
	@Autowired
	private ForcePersonnelRepository forcePersonnelRepository;
	
	@Override
	public List<ForcePersonnel> getNgoForcePersonalList(String transationalId) {
	
		List<String> listOfPersonalId=ngoameCandidateRequestDetailsRepo.getNgoListWithTransationalId(transationalId);
		List<ForcePersonnel> forcePersonalsList=new ArrayList<>();
		for (String NgoForcePersonalId : listOfPersonalId) {
			ForcePersonnel forcePersonal=new ForcePersonnel();
		   Optional<ForcePersonnel> optional=forcePersonnelRepository.getByForcePersonnelId(NgoForcePersonalId);
			if(!optional.isEmpty()) {
			forcePersonal=optional.get();
			forcePersonalsList.add(forcePersonal);
			}
		}
		return forcePersonalsList;
	}

	@Override
	public List<String> getTransactionalId(String amaForcePersonalId) {
		
		
		List<String> transactionalIdList=ngoameRequestRepo.findByForwardedToList(amaForcePersonalId);
		return transactionalIdList;
	}

}
