package nic.ame.master.util;


import java.util.Calendar;
import java.util.Date;

public class CompareDateUtil {

	public static int ValidateDate(Date date) {
		Date currentDate=Calendar.getInstance().getTime();
	    if(currentDate.after(date)) {
	    	return 1;
	    }
        if(date.equals(currentDate)) {
        	return 1;
        }
        if(date.before(currentDate)) {
        	return 0;
        }
		return 0;
		
	}
	
   public static int validateLockDateAndTime(Date date) {
	   
	    Date currentDate=Calendar.getInstance().getTime();
	    if(date.after(currentDate)) {
	    	return 1;
	    }
	    else if(date.before(currentDate)) {
	    	return 0;
	    }
	    else{
	    	return 1;
	    }
	   
   }
	
	
}
