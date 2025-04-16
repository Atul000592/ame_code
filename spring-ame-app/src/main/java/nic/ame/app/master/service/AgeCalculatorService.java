package nic.ame.app.master.service;

import java.util.Date;

public interface AgeCalculatorService  {
	
	
	public int getAge(Date dob );
	
	public String calculateYearsMothsandDays(Date date);
}
