package nic.ame.app.master.ref.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="tm_duration_master")
@Entity
public class RefDurationMaster {
	
	@Id
	private int id;
	private String name;
	private String durationCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDurationCode() {
		return durationCode;
	}
	public void setDurationCode(String durationCode) {
		this.durationCode = durationCode;
	}
	
	

}
