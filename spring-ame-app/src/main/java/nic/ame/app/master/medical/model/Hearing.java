package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_medical_examination_hearing")
@Entity
public class Hearing {

	@Id
	private String ameId;
	private String normal_in_both_ears;
	private String moderate_defect_in_one_ear;
	private String auroscopy;
	private String partial_defect_in_both_ears;
	private String rennie_test;
	private String weber_test;
	private String any_other_combination;
	private String audiometry;
	private String hNo;
	private String hType;
	private String hDuration;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;

	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getNormal_in_both_ears() {
		return normal_in_both_ears;
	}
	public void setNormal_in_both_ears(String normal_in_both_ears) {
		this.normal_in_both_ears = normal_in_both_ears;
	}
	public String getModerate_defect_in_one_ear() {
		return moderate_defect_in_one_ear;
	}
	public void setModerate_defect_in_one_ear(String moderate_defect_in_one_ear) {
		this.moderate_defect_in_one_ear = moderate_defect_in_one_ear;
	}
	public String getAuroscopy() {
		return auroscopy;
	}
	public void setAuroscopy(String auroscopy) {
		this.auroscopy = auroscopy;
	}
	public String getPartial_defect_in_both_ears() {
		return partial_defect_in_both_ears;
	}
	public void setPartial_defect_in_both_ears(String partial_defect_in_both_ears) {
		this.partial_defect_in_both_ears = partial_defect_in_both_ears;
	}
	public String getRennie_test() {
		return rennie_test;
	}
	public void setRennie_test(String rennie_test) {
		this.rennie_test = rennie_test;
	}
	public String getWeber_test() {
		return weber_test;
	}
	public void setWeber_test(String weber_test) {
		this.weber_test = weber_test;
	}
	public String getAny_other_combination() {
		return any_other_combination;
	}
	public void setAny_other_combination(String any_other_combination) {
		this.any_other_combination = any_other_combination;
	}
	public String getAudiometry() {
		return audiometry;
	}
	public void setAudiometry(String audiometry) {
		this.audiometry = audiometry;
	}
	public String gethNo() {
		return hNo;
	}
	public void sethNo(String hNo) {
		this.hNo = hNo;
	}
	public String gethType() {
		return hType;
	}
	public void sethType(String hType) {
		this.hType = hType;
	}
	public String gethDuration() {
		return hDuration;
	}
	public void sethDuration(String hDuration) {
		this.hDuration = hDuration;
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
