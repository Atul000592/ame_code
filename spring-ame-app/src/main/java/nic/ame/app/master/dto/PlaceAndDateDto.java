package nic.ame.app.master.dto;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.annotation.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="ame_individual_date_place")
@Immutable
public class PlaceAndDateDto {
	
	private String last_ame_place;
	 
	
	private Date last_ame_date;
	
	@Id
	private String ame_id; 

	public String getAme_id() {
		return ame_id;
	}

	public void setAme_id(String ame_id) {
		this.ame_id = ame_id;
	}

	public String getLast_ame_place() {
		return last_ame_place;
	}

	public void setLast_ame_place(String last_ame_place) {
		this.last_ame_place = last_ame_place;
	}

	public Date getLast_ame_date() {
		return last_ame_date;
	}

	public void setLast_ame_date(Date last_ame_date) {
		this.last_ame_date = last_ame_date;
	}

	

	

	



	

	
	
	
}
