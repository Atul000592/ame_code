package nic.ame.app.master.medical.dto;

import nic.ame.app.master.medical.model.Abdomen;
import nic.ame.app.master.medical.model.Appendages;
import nic.ame.app.master.medical.model.CentralNervousSystem;
import nic.ame.app.master.medical.model.CranialNervesMeningealSign;
import nic.ame.app.master.medical.model.EyeFactor;
import nic.ame.app.master.medical.model.GeneralExamination;
import nic.ame.app.master.medical.model.GynaeAndObsFemale;
import nic.ame.app.master.medical.model.Hearing;
import nic.ame.app.master.medical.model.Investigation;
import nic.ame.app.master.medical.model.PsychologicalAssessmentAsLaidDown;
import nic.ame.app.master.medical.model.PhysicalMeasurement;
import nic.ame.app.master.medical.model.Reflexes;
import nic.ame.app.master.medical.model.RespiratorySystem;
import nic.ame.app.master.medical.model.SensorySystem;

public class MedExamDtoRequest {

	private Abdomen abdomen;
	private Appendages appendages;
	private CranialNervesMeningealSign cranialNervesMeningealSign;
	private PhysicalMeasurement physicalMeasurement;
	private GeneralExamination generalExamination;
	private CentralNervousSystem cns;
	private Reflexes reflexes;
	private SensorySystem sensorysystem;
	private EyeFactor eyeFactor;
	private Investigation investigation;
	private Hearing hearing;
	private PsychologicalAssessmentAsLaidDown phychologicalAssessmentAsLaidDown;
	private RespiratorySystem respiratorySystem;
	private GynaeAndObsFemale gynaeAndObsFemale;
	
    
	public RespiratorySystem getRespiratorySystem() {
		return respiratorySystem;
	}
	public void setRespiratorySystem(RespiratorySystem respiratorySystem) {
		this.respiratorySystem = respiratorySystem;
	}
	public Abdomen getAbdomen() {
		return abdomen;
	}
	public void setAbdomen(Abdomen abdomen) {
		this.abdomen = abdomen;
	}
	public Appendages getAppendages() {
		return appendages;
	}
	public void setAppendages(Appendages appendages) {
		this.appendages = appendages;
	}
	public CranialNervesMeningealSign getCranialNervesMeningealSign() {
		return cranialNervesMeningealSign;
	}
	public void setCranialNervesMeningealSign(CranialNervesMeningealSign cranialNervesMeningealSign) {
		this.cranialNervesMeningealSign = cranialNervesMeningealSign;
	}
	public PhysicalMeasurement getPhysicalMeasurement() {
		return physicalMeasurement;
	}
	public void setPhysicalMeasurement(PhysicalMeasurement physicalMeasurement) {
		this.physicalMeasurement = physicalMeasurement;
	}
	public GeneralExamination getGeneralExamination() {
		return generalExamination;
	}
	public void setGeneralExamination(GeneralExamination generalExamination) {
		this.generalExamination = generalExamination;
	}
	public CentralNervousSystem getCns() {
		return cns;
	}
	public void setCns(CentralNervousSystem cns) {
		this.cns = cns;
	}
	public Reflexes getReflexes() {
		return reflexes;
	}
	public void setReflexes(Reflexes reflexes) {
		this.reflexes = reflexes;
	}
	public SensorySystem getSensorysystem() {
		return sensorysystem;
	}
	public void setSensorysystem(SensorySystem sensorysystem) {
		this.sensorysystem = sensorysystem;
	}
	public EyeFactor getEyeFactor() {
		return eyeFactor;
	}
	public void setEyeFactor(EyeFactor eyeFactor) {
		this.eyeFactor = eyeFactor;
	}
	public Investigation getInvestigation() {
		return investigation;
	}
	public void setInvestigation(Investigation investigation) {
		this.investigation = investigation;
	}
	public Hearing getHearing() {
		return hearing;
	}
	public void setHearing(Hearing hearing) {
		this.hearing = hearing;
	}
	public PsychologicalAssessmentAsLaidDown getPhychologicalAssessmentAsLaidDown() {
		return phychologicalAssessmentAsLaidDown;
	}
	public void setPhychologicalAssessmentAsLaidDown(PsychologicalAssessmentAsLaidDown phychologicalAssessmentAsLaidDown) {
		this.phychologicalAssessmentAsLaidDown = phychologicalAssessmentAsLaidDown;
	}
	public GynaeAndObsFemale getGynaeAndObsFemale() {
		return gynaeAndObsFemale;
	}
	public void setGynaeAndObsFemale(GynaeAndObsFemale gynaeAndObsFemale) {
		this.gynaeAndObsFemale = gynaeAndObsFemale;
	}
	
	
	
}
