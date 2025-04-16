package nic.ame.app.master.ref.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table
@Entity
public class RefInvestigationSubTestMaster {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String testName;
    private String subtestcodeName;
	private String subTestCode;
	private String createdBy;
	private Date createdOn;
	private int status;
	private String IpAddressClient;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getSubtestcodeName() {
		return subtestcodeName;
	}
	public void setSubtestcodeName(String subtestcodeName) {
		this.subtestcodeName = subtestcodeName;
	}
	public String getSubTestCode() {
		return subTestCode;
	}
	public void setSubTestCode(String subTestCode) {
		this.subTestCode = subTestCode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getIpAddressClient() {
		return IpAddressClient;
	}
	public void setIpAddressClient(String ipAddressClient) {
		IpAddressClient = ipAddressClient;
	}
	
	
	
	
}
