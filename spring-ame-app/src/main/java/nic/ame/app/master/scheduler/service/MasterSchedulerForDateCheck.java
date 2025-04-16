package nic.ame.app.master.scheduler.service;


import java.util.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MasterSchedulerForDateCheck {
	
	Logger logger=LoggerFactory.getLogger(MasterSchedulerForDateCheck.class);
	
	@Scheduled(fixedDelay = 600000)
	public void checkDate() {
		logger.info("Logger is running at :-"+Calendar.getInstance().getTime());
		
	}
	
	

}
