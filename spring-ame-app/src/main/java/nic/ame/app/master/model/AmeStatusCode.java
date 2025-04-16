package nic.ame.app.master.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="tm_ame_status_code")
@Entity
public class AmeStatusCode {
	
	@Id
	private int code;
	
	private String descriptionn;
	
	private int enableStatus;
	
	
//----------------------------------------------Getters and Setters----------------------------------------------------------------//
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescriptionn() {
		return descriptionn;
	}

	public void setDescriptionn(String descriptionn) {
		this.descriptionn = descriptionn;
	}

	public int getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(int enableStatus) {
		this.enableStatus = enableStatus;
	}
	
	

}
