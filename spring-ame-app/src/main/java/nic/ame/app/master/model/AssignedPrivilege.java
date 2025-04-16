package nic.ame.app.master.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_assigned_privilege")
@Entity
public class AssignedPrivilege {
    
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
	private String transactionalId;
	
	private String canAssign;
	
	private int status;
	
	private String clientIpAddress;
	
	private Date createdOn;

	private String satausChangeReason;
	
	private Date statusChangeDate;
	
	private String statusChangeBy;
	
	private String statusChangeFrom;

 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getTransactionalId() {
		return transactionalId;
	}

	public void setTransactionalId(String transactionalId) {
		this.transactionalId = transactionalId;
	}

	public String getCanAssign() {
		return canAssign;
	}

	public void setCanAssign(String canAssign) {
		this.canAssign = canAssign;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getStatusChangeFrom() {
		return statusChangeFrom;
	}

	public void setStatusChangeFrom(String statusChangeFrom) {
		this.statusChangeFrom = statusChangeFrom;
	}

}
