package nic.ame.app.master.serviceImpl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.board.member.repository.CheckUpListRepo;
import nic.ame.app.master.medical.model.CheckUpList;
import nic.ame.app.master.service.CandidateCheckUpListService;

@Service
public class CandidateCheckUpListServiceImpl implements CandidateCheckUpListService {

	@Autowired
	private CheckUpListRepo checkUpListRepo;
	
	@Override
	public List<CheckUpList> getCandidateCheckUpList(String ameId) {
       
	 List<CheckUpList> checkUpList=checkUpListRepo.findByAmeId(ameId);
         return checkUpList;
	}

}
