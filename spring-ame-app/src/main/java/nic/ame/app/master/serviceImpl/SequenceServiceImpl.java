package nic.ame.app.master.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import nic.ame.app.master.service.SequenceService;

@Service
public class SequenceServiceImpl  implements SequenceService{

	@Override
	public String createSequenceNgoAme(int forceNo) {
		// TODO Auto-generated method stub
		StringBuilder builder=new StringBuilder();
		Date date=new Date();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyMMddHHmm");
		String as=dateFormat.format(date);
		
		return builder.append("01").append(as).toString();
	}
	
	

}
