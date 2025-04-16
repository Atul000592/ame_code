package nic.ame.app.master.medical.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="tt_other_test")
public class OtherTest {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
    private String ameId;
	private String testName;
    private String testCode;

	private String path;
	private String fileName;
	private String fileType;
	private int fileSize;
	
	private Date lastModifiedOn;
	private String lastModifiedBy;
	private String lastModifiedFrom;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="fk_ameId",referencedColumnName = "ameId")
	List<OtherTestReportParameters> otherTestReportParameters;
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAmeId() {
		return ameId;
	}
	public void setAmeId(String ameId) {
		this.ameId = ameId;
	}
	public List<OtherTestReportParameters> getOtherTestReportParameters() {
		return otherTestReportParameters;
	}
	public void setOtherTestReportParameters(List<OtherTestReportParameters> otherTestReportParameters) {
		this.otherTestReportParameters = otherTestReportParameters;
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
