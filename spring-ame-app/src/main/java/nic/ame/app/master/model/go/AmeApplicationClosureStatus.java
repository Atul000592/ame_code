package nic.ame.app.master.model.go;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tm_ame_application_closure_status")
@Entity
public class AmeApplicationClosureStatus {
	

	@Id
	private int code;
	private String value;
	// 0 - inactive , 1- active
	private int status;
	private Date createdOn;
	private String createdBy;
	private String createdFrom;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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
	
	
	// 0-Open
	// 1-Close By AMA
	// 2-Close By BM
	// 3-Close By BM and PO
	// 4-Close By BM,PO and AA 
	
	

}
