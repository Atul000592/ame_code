package nic.ame.master.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class UserDateUtil {

	public static  int getDay(Date date1) {
		LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int day = localDate.getDayOfMonth();
	    
		return day;
	}

	public static int getYear(Date date1) {
		
		LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int years = localDate.getYear();

		return years;
	}

	public static int getMonth(Date date1) {
		
		LocalDate localDate = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
	    
		return month;
	}

	

}
