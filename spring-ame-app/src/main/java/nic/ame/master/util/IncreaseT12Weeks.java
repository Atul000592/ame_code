package nic.ame.master.util;

import java.util.Calendar;
import java.util.Date;

public class IncreaseT12Weeks {
	 
	public static Date getT12Date() {
	
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.WEEK_OF_YEAR, 12);
	        Date newDate = calendar.getTime();
	        
	        System.out.println("Current Date: " + new Date());
	        System.out.println("Date after 12 weeks: " + newDate);
	        
			return newDate;
	    }
	
	
}
