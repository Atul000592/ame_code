package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Table(name = "tt_medical_examination_eyefactor")
@Entity
public class EyeFactor {
	@Id
	private String ameId;
	
	private String distant_vision;
	
    private String distant_vision_rt_wg;
	private String distant_vision_lt_wg;
	
	private String distant_vision_rt_wog;
	private String distant_vision_lt_wog;

	private String near_vision;
	
	private String near_vision_rt_wg;
	private String near_vision_rt_wog;

	private String near_vision_lt_wg;
	private String near_vision_lt_wog;

	
	private String color_vision_rt;
	private String color_vision_lt;

	
	private String field_of_vision_rt;
	private String field_of_vision_lt;

	
	private String any_other_pathology_rt;
	private String any_other_pathology_lt;

	
	private String iol_rt;
	private String iol_lt;

	
//  private String e_no_rt;
//  private String e_no_lt;
	
//  private String e_type_rt;
//	private String e_type_lt;
	
//	private String e_duration_rt;
//	private String e_duration_lt;
	
	private String eyeCategory;
	private String eyeType;
	private String eyeDuration;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;


	

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getDistant_vision() {
		return distant_vision;
	}
	public void setDistant_vision(String distant_vision) {
		this.distant_vision = distant_vision;
	}
	public String getDistant_vision_rt_wg() {
		return distant_vision_rt_wg;
	}
	public void setDistant_vision_rt_wg(String distant_vision_rt_wg) {
		this.distant_vision_rt_wg = distant_vision_rt_wg;
	}
	public String getDistant_vision_lt_wg() {
		return distant_vision_lt_wg;
	}

	public void setDistant_vision_lt_wg(String distant_vision_lt_wg) {
		this.distant_vision_lt_wg = distant_vision_lt_wg;
	}
	public String getDistant_vision_rt_wog() {
		return distant_vision_rt_wog;
	}
	public void setDistant_vision_rt_wog(String distant_vision_rt_wog) {
		this.distant_vision_rt_wog = distant_vision_rt_wog;
	}
	public String getDistant_vision_lt_wog() {
		return distant_vision_lt_wog;
	}
	public void setDistant_vision_lt_wog(String distant_vision_lt_wog) {
		this.distant_vision_lt_wog = distant_vision_lt_wog;
	}
	public String getNear_vision() {
		return near_vision;
	}
	public void setNear_vision(String near_vision) {
		this.near_vision = near_vision;
	}
	public String getNear_vision_rt_wg() {
		return near_vision_rt_wg;
	}
	public void setNear_vision_rt_wg(String near_vision_rt_wg) {
		this.near_vision_rt_wg = near_vision_rt_wg;
	}
	public String getNear_vision_rt_wog() {
		return near_vision_rt_wog;
	}
	public void setNear_vision_rt_wog(String near_vision_rt_wog) {
		this.near_vision_rt_wog = near_vision_rt_wog;
	}
	public String getNear_vision_lt_wg() {
		return near_vision_lt_wg;
	}
	public void setNear_vision_lt_wg(String near_vision_lt_wg) {
		this.near_vision_lt_wg = near_vision_lt_wg;
	}
	public String getNear_vision_lt_wog() {
		return near_vision_lt_wog;
	}
	public void setNear_vision_lt_wog(String near_vision_lt_wog) {
		this.near_vision_lt_wog = near_vision_lt_wog;
	}
	public String getColor_vision_rt() {
		return color_vision_rt;
	}
	public void setColor_vision_rt(String color_vision_rt) {
		this.color_vision_rt = color_vision_rt;
	}
	public String getColor_vision_lt() {
		return color_vision_lt;
	}
	public void setColor_vision_lt(String color_vision_lt) {
		this.color_vision_lt = color_vision_lt;
	}
	public String getField_of_vision_rt() {
		return field_of_vision_rt;
	}
	public void setField_of_vision_rt(String field_of_vision_rt) {
		this.field_of_vision_rt = field_of_vision_rt;
	}
	public String getField_of_vision_lt() {
		return field_of_vision_lt;
	}

	public void setField_of_vision_lt(String field_of_vision_lt) {
		this.field_of_vision_lt = field_of_vision_lt;
	}

	public String getAny_other_pathology_rt() {
		return any_other_pathology_rt;
	}

	public void setAny_other_pathology_rt(String any_other_pathology_rt) {
		this.any_other_pathology_rt = any_other_pathology_rt;
	}

	public String getAny_other_pathology_lt() {
		return any_other_pathology_lt;
	}

	public void setAny_other_pathology_lt(String any_other_pathology_lt) {
		this.any_other_pathology_lt = any_other_pathology_lt;
	}

	public String getIol_rt() {
		return iol_rt;
	}

	public void setIol_rt(String iol_rt) {
		this.iol_rt = iol_rt;
	}

	public String getIol_lt() {
		return iol_lt;
	}

	public void setIol_lt(String iol_lt) {
		this.iol_lt = iol_lt;
	}

	public String getEyeCategory() {
		return eyeCategory;
	}

	public void setEyeCategory(String eyeCategory) {
		this.eyeCategory = eyeCategory;
	}

	public String getEyeType() {
		return eyeType;
	}

	public void setEyeType(String eyeType) {
		this.eyeType = eyeType;
	}

	public String getEyeDuration() {
		return eyeDuration;
	}

	public void setEyeDuration(String eyeDuration) {
		this.eyeDuration = eyeDuration;
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
