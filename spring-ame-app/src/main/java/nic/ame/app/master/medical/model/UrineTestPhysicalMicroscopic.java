package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tt_urine_test_physical_microscopic")
public class UrineTestPhysicalMicroscopic {
	
	@Id
	private String ameId;
	
	//=======microscopy=======================
	private String epithelialCells; 
	 
	private String pusCells;
	
	private String rbc;
	
	private String crystal;

	
	private String castCells;
	
	//============================others============================//
	
     private String colour; 	
    
	private String transparency; 
	
	private String reaction; 	
	
	private String albuminCells; 
	
	private String sugar;	
	
	private String bileSalt; 
	
	private String ketones; 	
	
	private String specificGravity;
	
	private String upt;
	
//=================================test code file==========================//
	private String testCode;
	private String path;
	private String fileName;
	private String fileType;
	private int fileSize;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	
	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getEpithelialCells() {
		return epithelialCells;
	}
	public void setEpithelialCells(String epithelialCells) {
		this.epithelialCells = epithelialCells;
	}
	public String getPusCells() {
		return pusCells;
	}
	public void setPusCells(String pusCells) {
		this.pusCells = pusCells;
	}
	public String getRbc() {
		return rbc;
	}
	public void setRbc(String rbc) {
		this.rbc = rbc;
	}
	public String getCrystal() {
		return crystal;
	}
	public void setCrystal(String crystal) {
		this.crystal = crystal;
	}
	public String getCastCells() {
		return castCells;
	}
	public void setCastCells(String castCells) {
		this.castCells = castCells;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getTransparency() {
		return transparency;
	}
	public void setTransparency(String transparency) {
		this.transparency = transparency;
	}
	public String getReaction() {
		return reaction;
	}
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}
	public String getAlbuminCells() {
		return albuminCells;
	}
	public void setAlbuminCells(String albuminCells) {
		this.albuminCells = albuminCells;
	}
	public String getSugar() {
		return sugar;
	}
	public void setSugar(String sugar) {
		this.sugar = sugar;
	}
	public String getBileSalt() {
		return bileSalt;
	}
	public void setBileSalt(String bileSalt) {
		this.bileSalt = bileSalt;
	}
	public String getKetones() {
		return ketones;
	}
	public void setKetones(String ketones) {
		this.ketones = ketones;
	}
	public String getSpecificGravity() {
		return specificGravity;
	}
	public void setSpecificGravity(String specificGravity) {
		this.specificGravity = specificGravity;
	}
	public String getUpt() {
		return upt;
	}
	public void setUpt(String upt) {
		this.upt = upt;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
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
