package com.nic.in.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
public class Employee {
	

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
private long id;


@Column(name="name")
private String name;


@Column(name="designation")
private String designation;


@Column(name="email")
private String email;


@Column(name="salary")
private String salary;


@Column(name="hra")
private String hra;

@Column(name="basic")
private String basic;

@Column(name="gender")
private String gender;

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getDesignation() {
	return designation;
}

public void setDesignation(String designation) {
	this.designation = designation;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}


public String getSalary() {
	return salary;
}

public void setSalary(String salary) {
	this.salary = salary;
}

public String getHra() {
	return hra;
}

public void setHra(String hra) {
	this.hra = hra;
}

public String getBasic() {
	return basic;
}

public void setBasic(String basic) {
	this.basic = basic;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}












}
