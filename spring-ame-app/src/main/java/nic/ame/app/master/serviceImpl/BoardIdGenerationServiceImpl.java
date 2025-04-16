package nic.ame.app.master.serviceImpl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nic.ame.app.master.repository.CustomSequenceCodeRepo;
import nic.ame.app.master.service.BoardIdGenerationService;
import nic.ame.master.util.RandomGenerator;

@Service
public class BoardIdGenerationServiceImpl implements BoardIdGenerationService {

	@Autowired
	private CustomSequenceCodeRepo customSequenceCodeRepo;
	
	@Override
	public String boardDynamicId(String forceId,int name) {
    
		StringBuilder builder=new StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String rdCode=RandomGenerator.getRandomOneTimePassword();
		  int customCode=customSequenceCodeRepo.getCustomSeqCode();
		 builder.append(name).append(forceId).append(year).append(customCode).append(rdCode);
		
		return builder.toString();
	
	}

}
