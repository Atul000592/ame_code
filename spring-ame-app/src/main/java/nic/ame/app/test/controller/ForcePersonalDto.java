package nic.ame.app.test.controller;

import nic.ame.app.master.model.ForcePersonnel;


public class ForcePersonalDto {

	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	private ForcePersonnel forcePersonal;
	private String password;
	
	public ForcePersonnel getForcePersonal() {
		return forcePersonal;
	}
	public void setForcePersonal(ForcePersonnel forcePersonal) {
		this.forcePersonal = forcePersonal;
	}
	
	
	
}
