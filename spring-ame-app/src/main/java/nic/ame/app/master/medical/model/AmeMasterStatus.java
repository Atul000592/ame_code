package nic.ame.app.master.medical.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ame_master_status")
public class AmeMasterStatus {

	
	
	
	@Id
	@Column(name = "ame_id", nullable = false)
	private String ameId;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String abdomen;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String appendages;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String cns;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String cranialnervesmeningealsign;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String eyefactor;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String finalDetails;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String generalExamination;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String hearing;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String investigation;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String phychologicalAssessmentAsLaidDown;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String physicalMeasurement;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String reflexes;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String respiratorySystem;

	@Column(columnDefinition = "varchar(5) default 'No'")
	private String sensorySystem;
	@Column(columnDefinition = "varchar(5) default 'No'")
	private String gynaecologyObs;

	@Column(name = "psycological_shape")
	private String psycological_shape;

	@Column(name = "hearing_shape")
	private String hearingShape;

	
	  // @Column(name = "appendages_shape") // private String appendagesShape;
	 
	@Column(name = "physical_shape")
	private String physicalShape;
    
	@Column(name = "gynaecology_shape",columnDefinition = "varchar(5) default 'No'")
	private String gynaecologyShape;

	@Column(name = "final_Shape")
	private String finalShape;
  
	@Column(name = "status")
	private String status;

	private String boardId;

	private String year;

	private String forcePersonalId;

//	private String leftEyeShape;

//	private String rightEyeShape;
	
	private String eyeShape;
	
//	===========Appendages Shape====================================================================================//

	private String upperLimbShape;

	private String lowerLimbShape;

	private String spineShape;

	public String getSpineShape() {
		return spineShape;
	}

	public void setSpineShape(String spineShape) {
		this.spineShape = spineShape;
	}

	public String getAmeId() {
		return ameId;
	}

	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}

	public String getAbdomen() {
		return abdomen;
	}

	public void setAbdomen(String abdomen) {
		this.abdomen = abdomen;
	}

	public String getAppendages() {
		return appendages;
	}

	public void setAppendages(String appendages) {
		this.appendages = appendages;
	}

	public String getCns() {
		return cns;
	}

	public void setCns(String cns) {
		this.cns = cns;
	}

	public String getCranialnervesmeningealsign() {
		return cranialnervesmeningealsign;
	}

	public void setCranialnervesmeningealsign(String cranialnervesmeningealsign) {
		this.cranialnervesmeningealsign = cranialnervesmeningealsign;
	}

	public String getEyefactor() {
		return eyefactor;
	}

	public void setEyefactor(String eyefactor) {
		this.eyefactor = eyefactor;
	}

	public String getFinalDetails() {
		return finalDetails;
	}

	public void setFinalDetails(String finalDetails) {
		this.finalDetails = finalDetails;
	}

	public String getGeneralExamination() {
		return generalExamination;
	}

	public void setGeneralExamination(String generalExamination) {
		this.generalExamination = generalExamination;
	}

	public String getHearing() {
		return hearing;
	}

	public void setHearing(String hearing) {
		this.hearing = hearing;
	}

	public String getInvestigation() {
		return investigation;
	}

	public void setInvestigation(String investigation) {
		this.investigation = investigation;
	}

	public String getPhychologicalAssessmentAsLaidDown() {
		return phychologicalAssessmentAsLaidDown;
	}

	public void setPhychologicalAssessmentAsLaidDown(String phychologicalAssessmentAsLaidDown) {
		this.phychologicalAssessmentAsLaidDown = phychologicalAssessmentAsLaidDown;
	}

	public String getPhysicalMeasurement() {
		return physicalMeasurement;
	}

	public void setPhysicalMeasurement(String physicalMeasurement) {
		this.physicalMeasurement = physicalMeasurement;
	}

	public String getReflexes() {
		return reflexes;
	}

	public void setReflexes(String reflexes) {
		this.reflexes = reflexes;
	}

	public String getRespiratorySystem() {
		return respiratorySystem;
	}

	public void setRespiratorySystem(String respiratorySystem) {
		this.respiratorySystem = respiratorySystem;
	}

	public String getSensorySystem() {
		return sensorySystem;
	}

	public void setSensorySystem(String sensorySystem) {
		this.sensorySystem = sensorySystem;
	}

	public String getGynaecologyObs() {
		return gynaecologyObs;
	}

	public void setGynaecologyObs(String gynaecologyObs) {
		this.gynaecologyObs = gynaecologyObs;
	}

	public String getPsycological_shape() {
		return psycological_shape;
	}

	public void setPsycological_shape(String psycological_shape) {
		this.psycological_shape = psycological_shape;
	}

	public String getHearingShape() {
		return hearingShape;
	}

	public void setHearingShape(String hearingShape) {
		this.hearingShape = hearingShape;
	}

	

	public String getPhysicalShape() {
		return physicalShape;
	}

	public void setPhysicalShape(String physicalShape) {
		this.physicalShape = physicalShape;
	}

	public String getGynaecologyShape() {
		return gynaecologyShape;
	}

	public void setGynaecologyShape(String gynaecologyShape) {
		this.gynaecologyShape = gynaecologyShape;
	}

	public String getFinalShape() {
		return finalShape;
	}

	public void setFinalShape(String finalShape) {
		this.finalShape = finalShape;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getForcePersonalId() {
		return forcePersonalId;
	}

	public void setForcePersonalId(String forcePersonalId) {
		this.forcePersonalId = forcePersonalId;
	}



	public String getEyeShape() {
		return eyeShape;
	}

	public void setEyeShape(String eyeShape) {
		this.eyeShape = eyeShape;
	}

	public String getUpperLimbShape() {
		return upperLimbShape;
	}

	public void setUpperLimbShape(String upperLimbShape) {
		this.upperLimbShape = upperLimbShape;
	}

	public String getLowerLimbShape() {
		return lowerLimbShape;
	}

	public void setLowerLimbShape(String lowerLimbShape) {
		this.lowerLimbShape = lowerLimbShape;
	}

	/*
	 * private String leftLowerLimbShape;
	 * 
	 * private String rightLowerLimbShape;
	 * 
	 * private String leftUpperLimbShape;
	 * 
	 * private String rightUpperLimbShape;
	 */

}
