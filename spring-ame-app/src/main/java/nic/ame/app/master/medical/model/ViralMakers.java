package nic.ame.app.master.medical.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tt_viral_makers")
public class ViralMakers {

    @Id
    private String ameId;

    @Column(name = "hiv1")
    private String hiv1;

    @Column(name = "hiv2")
    private String hiv2;

    @Column(name = "hbsag")
    private String hbsAg;

    @Column(name = "vdrl")
    private String vdrl;

    @Column(name = "hcv")
    private String hcv;

    @Column(name = "test_code")
    private String testCode;
     
    @Column(name = "path")
    private String path;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_size")
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

    public String getHiv1() {
        return hiv1;
    }

    public void setHiv1(String hiv1) {
        this.hiv1 = hiv1;
    }

    public String getHiv2() {
        return hiv2;
    }

    public void setHiv2(String hiv2) {
        this.hiv2 = hiv2;
    }

    public String getHbsAg() {
        return hbsAg;
    }

    public void setHbsAg(String hbsAg) {
        this.hbsAg = hbsAg;
    }

    public String getVdrl() {
        return vdrl;
    }

    public void setVdrl(String vdrl) {
        this.vdrl = vdrl;
    }

    public String getHcv() {
        return hcv;
    }

    public void setHcv(String hcv) {
        this.hcv = hcv;
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

    

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "created_by")
    private String createdBy;






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
