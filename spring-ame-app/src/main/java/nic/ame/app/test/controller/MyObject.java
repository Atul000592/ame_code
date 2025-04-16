package nic.ame.app.test.controller;

import java.io.Serializable;

public class MyObject implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int age;
	private String name;
	private long salary;
	private String department;
	public MyObject(int age, String name, long salary, String department) {
		super();
		this.age = age;
		this.name = name;
		this.salary = salary;
		this.department = department;
	}
	@Override
	public String toString() {
		return "MyObject [age=" + age + ", name=" + name + ", salary=" + salary + ", department=" + department + "]";
	}
	
	
	

}
