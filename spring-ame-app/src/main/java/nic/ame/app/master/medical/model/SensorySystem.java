package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Table(name = "tt_medical_examination_sensory_system")
@Entity
public class SensorySystem {

	@Id
	private String ameId;
//	private String sensorySystem;
	private String reflexes;
	private String rombergSign;
	private String slrTestLimbRight;
	private String slrTestLimbLeft;
	private String fingerNoseTest;
	private String skullAndBone;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	    
	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	
	public String getReflexes() {
		return reflexes;
	}
	public void setReflexes(String reflexes) {
		this.reflexes = reflexes;
	}
	public String getRombergSign() {
		return rombergSign;
	}
	public void setRombergSign(String rombergSign) {
		this.rombergSign = rombergSign;
	}
	public String getSlrTestLimbRight() {
		return slrTestLimbRight;
	}
	public void setSlrTestLimbRight(String slrTestLimbRight) {
		this.slrTestLimbRight = slrTestLimbRight;
	}
	public String getSlrTestLimbLeft() {
		return slrTestLimbLeft;
	}
	public void setSlrTestLimbLeft(String slrTestLimbLeft) {
		this.slrTestLimbLeft = slrTestLimbLeft;
	}
	public String getFingerNoseTest() {
		return fingerNoseTest;
	}
	public void setFingerNoseTest(String fingerNoseTest) {
		this.fingerNoseTest = fingerNoseTest;
	}
	public String getSkullAndBone() {
		return skullAndBone;
	}
	public void setSkullAndBone(String skullAndBone) {
		this.skullAndBone = skullAndBone;
	}
	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}
	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	public String getLastModifiedFrom() {
		return lastModifiedFrom;
	}
	public void setLastModifiedFrom(String lastModifiedFrom) {
		this.lastModifiedFrom = lastModifiedFrom;
	}
	
	


}
