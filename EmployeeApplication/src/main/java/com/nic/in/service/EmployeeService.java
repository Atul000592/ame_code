package com.nic.in.service;

import java.util.List;

import com.nic.in.modal.Employee;

public interface EmployeeService {

	    List < Employee > getAllEmployees();
	  
	  
	    void saveEmployee(Employee employee);
	    
	    Employee getEmployeeById(long id);
	    
	    void deleteEmployeeById(long id);
	
}
