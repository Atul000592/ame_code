package nic.ame.app.master.service;

import java.util.List;

import nic.ame.app.master.model.ForcePersonnel;

public interface AmaNgoLIstService {
	
	
	List<ForcePersonnel> getNgoForcePersonalList(String transactionalId);
	
	public List<String> getTransactionalId(String amaForcePersonalId);

}
