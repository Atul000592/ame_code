package nic.ame.app.master.status;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tm_declaration_status")
@Entity
public class DeclarationStatus {

	@Id
	@Column(length = 1)
	private String code;
	private String value;
	@Column(length = 30)
	private String createdBy;
	@Column(length = 30)
	private String createdFrom;

	private Date createdOn;
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedFrom() {
		return createdFrom;
	}
	public void setCreatedFrom(String createdFrom) {
		this.createdFrom = createdFrom;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	
	
	
	
}
