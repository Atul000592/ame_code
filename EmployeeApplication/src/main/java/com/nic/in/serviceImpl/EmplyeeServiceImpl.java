package com.nic.in.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nic.in.Repository.EmployeeRepository;
import com.nic.in.modal.Employee;
import com.nic.in.service.EmployeeService;

@Service
public class EmplyeeServiceImpl implements EmployeeService {

	
	 @Autowired
	    private EmployeeRepository employeeRepository;

	    @Override
	    public List< Employee > getAllEmployees() {
	        return employeeRepository.findAll();
	    }

	    @Override
	    public void saveEmployee(Employee employee) {
	    	
	    	
	    	Long hraEmp=	Long.parseLong(employee.getHra()) ;
	    	Long basicEmp=	Long.parseLong(employee.getBasic()) ;
	    String salarEmp=Long.toString(hraEmp + basicEmp);
	    employee.setSalary(salarEmp);
	        this.employeeRepository.save(employee);
	    }

	    @Override
	    public Employee getEmployeeById(long id) {
	        Optional < Employee > optional = employeeRepository.findById(id);
	        Employee employee = null;
	        if (optional.isPresent()) {
	            employee = optional.get();
	        } else {
	            throw new RuntimeException(" Employee not found for id :: " + id);
	        }
	        return employee;
	    }

	    @Override
	    public void deleteEmployeeById(long id) {
	        this.employeeRepository.deleteById(id);
	    }
}
