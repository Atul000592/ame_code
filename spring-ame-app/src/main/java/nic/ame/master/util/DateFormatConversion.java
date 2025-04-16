package nic.ame.master.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatConversion {
	
	
	public static Date outputdate (String inputDate)throws ParseException {
		
        
        // Define the input date format (dd-MM-yyyy)
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        
        // Parse the input date into a Date object
        Date date = inputFormat.parse(inputDate);
        

        
        // If you want to print it in yyyy-MM-dd format, use SimpleDateFormat
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = outputFormat.format(date);
        
  
        SimpleDateFormat finalOutFormat = new SimpleDateFormat("dd-MM-yyyy");
        // If you need to keep the Date object, the Date object remains unchanged.
        // The output here is just to illustrate the formatted output.
        
        Date finalDate=finalOutFormat.parse(formattedDate);
		return finalDate;
        
      
	}
	
   
}
