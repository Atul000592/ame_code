package nic.ame.master.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateConvert {

	
	public static Date convertStringToDate(String inputDate) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(inputDate);
        return date;
	}
	
	
	public static void main(String[] args) {
		String string = "2023-09-22";

		try {
		System.out.println(convertStringToDate(string));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
