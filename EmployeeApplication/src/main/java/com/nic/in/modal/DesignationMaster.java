package com.nic.in.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="designation")
public class DesignationMaster {
	
	

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
private long id;





@Column(name="designation")
private String designation;





public long getId() {
	return id;
}





public void setId(long id) {
	this.id = id;
}





public String getDesignation() {
	return designation;
}





public void setDesignation(String designation) {
	this.designation = designation;
}





}
