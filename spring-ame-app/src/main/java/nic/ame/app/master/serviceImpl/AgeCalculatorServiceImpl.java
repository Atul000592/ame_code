package nic.ame.app.master.serviceImpl;


import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.stereotype.Service;

import nic.ame.app.master.service.AgeCalculatorService;

@Service
public class AgeCalculatorServiceImpl implements AgeCalculatorService{

	@Override
	public int getAge(Date dob) {
		
		
		LocalDate date=Instant.ofEpochMilli(dob.getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
			     
		LocalDate localDate=LocalDate.now();
		System.out.println(localDate);
	int age=Period.between(date,localDate).getYears();
	//int month=Period.between(date, localDate).getMonths();
	return age;
	}

	@Override
	public String calculateYearsMothsandDays(Date date1) {
		LocalDate date=Instant.ofEpochMilli(date1.getTime())
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
			     
		LocalDate localDate=LocalDate.now();
		System.out.println(localDate);
	int years=Period.between(date,localDate).getYears();
	int month=Period.between(date, localDate).getMonths();
	int days=Period.between(date,localDate).getDays();
		return String.valueOf(years+" Years "+month+" Months "+days+" Days");
	}

}
