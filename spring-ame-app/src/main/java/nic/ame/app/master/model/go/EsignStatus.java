package nic.ame.app.master.model.go;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tm_esign_status")
@Entity
public class EsignStatus {
	
	
	
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
	
	// 0 not sign 
	// 1 e_sign by AMA
	// 2 e_sign By BM
	// 3 e_sign By BM and PO
	// 4 e_sign By BM,PO and AA
	
    	
	

}
