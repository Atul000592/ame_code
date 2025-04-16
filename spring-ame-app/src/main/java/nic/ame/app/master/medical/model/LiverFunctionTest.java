package nic.ame.app.master.medical.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tt_liver_function_test")
public class LiverFunctionTest {
	@Id
    private String ameId;
	private String bilirubin;
	private String directBilirubin;
	private String indirectBilirubin;
	private String sGOT;
	private String sGPT;
	private String aLP;
	
	
	
	private String testCode;
	private String path;
	private String fileName;
	private String fileType;
	private int fileSize;
	
	//===================new fields added=========================//
	
	
	
	private String totalProtein;
	private String albumin;
	private String globulin;
	private String AGRatio;
	private String GGTP;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	
	
	
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public String getBilirubin() {
		return bilirubin;
	}
	public void setBilirubin(String bilirubin) {
		this.bilirubin = bilirubin;
	}
	public String getDirectBilirubin() {
		return directBilirubin;
	}
	public void setDirectBilirubin(String directBilirubin) {
		this.directBilirubin = directBilirubin;
	}
	public String getIndirectBilirubin() {
		return indirectBilirubin;
	}
	public void setIndirectBilirubin(String indirectBilirubin) {
		this.indirectBilirubin = indirectBilirubin;
	}
	public String getsGOT() {
		return sGOT;
	}
	public void setsGOT(String sGOT) {
		this.sGOT = sGOT;
	}
	public String getsGPT() {
		return sGPT;
	}
	public void setsGPT(String sGPT) {
		this.sGPT = sGPT;
	}
	public String getaLP() {
		return aLP;
	}
	public void setaLP(String aLP) {
		this.aLP = aLP;
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
	public String getTotalProtein() {
		return totalProtein;
	}
	public void setTotalProtein(String totalProtein) {
		this.totalProtein = totalProtein;
	}
	public String getAlbumin() {
		return albumin;
	}
	public void setAlbumin(String albumin) {
		this.albumin = albumin;
	}
	public String getGlobulin() {
		return globulin;
	}
	public void setGlobulin(String globulin) {
		this.globulin = globulin;
	}
	public String getAGRatio() {
		return AGRatio;
	}
	public void setAGRatio(String aGRatio) {
		AGRatio = aGRatio;
	}
	public String getGGTP() {
		return GGTP;
	}
	public void setGGTP(String gGTP) {
		GGTP = gGTP;
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
