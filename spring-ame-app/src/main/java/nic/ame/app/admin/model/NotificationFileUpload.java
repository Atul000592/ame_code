package nic.ame.app.admin.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tt_notification_file_upload")
public class NotificationFileUpload {
	@Id
	private String orderId;
	
	private String fileName;
	
	private Date createdOn;
	
	private String pathName;
	
	private int size;
	
	private String format;
	
	
	
   public NotificationFileUpload() {
		
	}

public NotificationFileUpload(String orderId, String fileName, Date createdOn, String pathName, int size,
			String format) {
		super();
		this.orderId = orderId;
		this.fileName = fileName;
		this.createdOn = createdOn;
		this.pathName = pathName;
		this.size = size;
		this.format = format;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getPathName() {
		return pathName;
	}

	public void setPathName(String pathName) {
		this.pathName = pathName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
	
	

}
