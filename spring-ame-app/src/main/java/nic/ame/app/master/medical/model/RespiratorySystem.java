package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_medical_examination_respiratory_system")
@Entity
public class RespiratorySystem {
	@Id
	private String ameId;
	private String chestDeformityAny;
	private String percussion;
	private String breathSounds;
	private String adventitiousSounds;
	
	private Date lastModifiedOn;
	 private String lastModifiedBy;
	 private String lastModifiedFrom;
	 
	
	
	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getChestDeformityAny() {
		return chestDeformityAny;
	}
	public void setChestDeformityAny(String chestDeformityAny) {
		this.chestDeformityAny = chestDeformityAny;
	}
	public String getPercussion() {
		return percussion;
	}
	public void setPercussion(String percussion) {
		this.percussion = percussion;
	}
	public String getBreathSounds() {
		return breathSounds;
	}
	public void setBreathSounds(String breathSounds) {
		this.breathSounds = breathSounds;
	}
	public String getAdventitiousSounds() {
		return adventitiousSounds;
	}
	public void setAdventitiousSounds(String adventitiousSounds) {
		this.adventitiousSounds = adventitiousSounds;
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
