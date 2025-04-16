package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "ref_tt_ame_parameters")
@Entity
public class AmeParameters {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String description;
	private String value;
	private String clientIpAddress;
	private Date createdOn;
	private String createdBy;
	
    private String satausChangeReason;
	
	private Date statusChangeDate;
	
	private String statusChangeBy;
	
	private String statusChangeClientIp;
	
	
	private int status;
	private String inActiveReason;
	private Date inActiveDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getInActiveReason() {
		return inActiveReason;
	}
	public void setInActiveReason(String inActiveReason) {
		this.inActiveReason = inActiveReason;
	}
	public Date getInActiveDate() {
		return inActiveDate;
	}
	public void setInActiveDate(Date inActiveDate) {
		this.inActiveDate = inActiveDate;
	}
	public String getClientIpAddress() {
		return clientIpAddress;
	}
	public void setClientIpAddress(String clientIpAddress) {
		this.clientIpAddress = clientIpAddress;
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
	public String getSatausChangeReason() {
		return satausChangeReason;
	}
	public void setSatausChangeReason(String satausChangeReason) {
		this.satausChangeReason = satausChangeReason;
	}
	public Date getStatusChangeDate() {
		return statusChangeDate;
	}
	public void setStatusChangeDate(Date statusChangeDate) {
		this.statusChangeDate = statusChangeDate;
	}
	public String getStatusChangeBy() {
		return statusChangeBy;
	}
	public void setStatusChangeBy(String statusChangeBy) {
		this.statusChangeBy = statusChangeBy;
	}
	public String getStatusChangeClientIp() {
		return statusChangeClientIp;
	}
	public void setStatusChangeClientIp(String statusChangeClientIp) {
		this.statusChangeClientIp = statusChangeClientIp;
	}
	
	
	
	
	
	

}
