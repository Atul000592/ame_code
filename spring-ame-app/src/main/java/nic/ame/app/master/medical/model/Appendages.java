package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "tt_medical_examination_appendages")
@Entity
public class Appendages {

	// ==========================Upper Limb==========================//
	@Id
	private String ameId;
//	private String upperLimbLeft;
//	private String upperLimbRight;
	private String anyLossInfirmityDetailsUpperLeftLimb;
	private String anyLossInfirmityDetailsUpperRightLimb;
    private String upperLimbShape;
    private String upperLimbType;
    private String upperLimbDuration;
// ==========================lower Limb==========================//
//    private String lowerLimbLeft;
//    private String lowerLimbRight;
    private String anyLossInfirmityDetailsLowerLeftLimb;
    private String anyLossInfirmityDetailsLowerRightLimb;
    private String lowerLimbShape;
    private String lowerLimbType;
    private String lowerLimbDuration;

//===========================other details========================//
    private Date lastModifiedOn;
    private String lastModifiedBy;
    private String lastModifiedFrom;
//================================================================//
    private String spine;
    private String spineShape;
    private String spineType;
    private String spineDuration;
    
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getAnyLossInfirmityDetailsUpperLeftLimb() {
		return anyLossInfirmityDetailsUpperLeftLimb;
	}
	public void setAnyLossInfirmityDetailsUpperLeftLimb(String anyLossInfirmityDetailsUpperLeftLimb) {
		this.anyLossInfirmityDetailsUpperLeftLimb = anyLossInfirmityDetailsUpperLeftLimb;
	}
	public String getAnyLossInfirmityDetailsUpperRightLimb() {
		return anyLossInfirmityDetailsUpperRightLimb;
	}
	public void setAnyLossInfirmityDetailsUpperRightLimb(String anyLossInfirmityDetailsUpperRightLimb) {
		this.anyLossInfirmityDetailsUpperRightLimb = anyLossInfirmityDetailsUpperRightLimb;
	}
	public String getUpperLimbShape() {
		return upperLimbShape;
	}
	public void setUpperLimbShape(String upperLimbShape) {
		this.upperLimbShape = upperLimbShape;
	}
	public String getUpperLimbType() {
		return upperLimbType;
	}
	public void setUpperLimbType(String upperLimbType) {
		this.upperLimbType = upperLimbType;
	}
	public String getUpperLimbDuration() {
		return upperLimbDuration;
	}
	public void setUpperLimbDuration(String upperLimbDuration) {
		this.upperLimbDuration = upperLimbDuration;
	}
	public String getAnyLossInfirmityDetailsLowerLeftLimb() {
		return anyLossInfirmityDetailsLowerLeftLimb;
	}
	public void setAnyLossInfirmityDetailsLowerLeftLimb(String anyLossInfirmityDetailsLowerLeftLimb) {
		this.anyLossInfirmityDetailsLowerLeftLimb = anyLossInfirmityDetailsLowerLeftLimb;
	}
	public String getAnyLossInfirmityDetailsLowerRightLimb() {
		return anyLossInfirmityDetailsLowerRightLimb;
	}
	public void setAnyLossInfirmityDetailsLowerRightLimb(String anyLossInfirmityDetailsLowerRightLimb) {
		this.anyLossInfirmityDetailsLowerRightLimb = anyLossInfirmityDetailsLowerRightLimb;
	}
	public String getLowerLimbShape() {
		return lowerLimbShape;
	}
	public void setLowerLimbShape(String lowerLimbShape) {
		this.lowerLimbShape = lowerLimbShape;
	}
	public String getLowerLimbType() {
		return lowerLimbType;
	}
	public void setLowerLimbType(String lowerLimbType) {
		this.lowerLimbType = lowerLimbType;
	}
	public String getLowerLimbDuration() {
		return lowerLimbDuration;
	}
	public void setLowerLimbDuration(String lowerLimbDuration) {
		this.lowerLimbDuration = lowerLimbDuration;
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
	public String getSpine() {
		return spine;
	}
	public void setSpine(String spine) {
		this.spine = spine;
	}
	public String getSpineShape() {
		return spineShape;
	}
	public void setSpineShape(String spineShape) {
		this.spineShape = spineShape;
	}
	public String getSpineType() {
		return spineType;
	}
	public void setSpineType(String spineType) {
		this.spineType = spineType;
	}
	public String getSpineDuration() {
		return spineDuration;
	}
	public void setSpineDuration(String spineDuration) {
		this.spineDuration = spineDuration;
	}
	
	
	
	
	
	
}
