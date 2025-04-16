package nic.ame.app.master.service;

import java.util.List;
import java.util.Optional;

import nic.ame.app.master.medical.model.CheckUpList;

public interface CandidateCheckUpListService {

	
	
	List<CheckUpList> getCandidateCheckUpList(String ameId);
}
