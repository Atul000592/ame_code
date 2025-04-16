package nic.ame.app.master.serviceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nic.ame.app.master.service.AmeUniqueIdGenerationService;

@Service
public class AmeUniqueIdGenerationServiceImpl implements AmeUniqueIdGenerationService {
	
	Logger logger=LoggerFactory.getLogger(AmeUniqueIdGenerationServiceImpl.class);

	@Override
	public String generateAmeId(int forceNo, String IrlaNo,String freshReviewFlag) {
		
		
		 Random rand = new Random();
		 int randomNumber=rand.nextInt(1000);
		StringBuilder stringBuilder=new  StringBuilder();
		int year = Calendar.getInstance().get(Calendar.YEAR);
		stringBuilder.append("AME").append(forceNo).append(year).append(freshReviewFlag).append(IrlaNo).append(randomNumber);
		logger.info("New AmeId generated at: "+new Date().getTime()+"AME ID :- "+stringBuilder.toString());
        return stringBuilder.toString() ;
	}



}
