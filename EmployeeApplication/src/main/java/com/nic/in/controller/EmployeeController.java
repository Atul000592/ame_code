package com.nic.in.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nic.in.modal.DesignationMaster;
import com.nic.in.modal.Employee;
import com.nic.in.service.DesignationMasterService;
import com.nic.in.service.EmployeeService;



@Controller
public class EmployeeController {

	
	 @Autowired
	    private EmployeeService employeeService;
	 
	 @Autowired
	    private DesignationMasterService designationMasterService;

	    // display list of employees
	    @GetMapping("/")
	    public String viewHomePage(Model model) {
	        model.addAttribute("listEmployees", employeeService.getAllEmployees());
	        return "index";
	    }

	    @GetMapping("/entryEmployeeForm")
	    public String showNewEmployeeForm(Model model) {
	        // create model attribute to bind form data
	        Employee employee = new Employee();
	        
	        List<DesignationMaster> desiList=designationMasterService.getDesigList();
	        
	        model.addAttribute("desiList", desiList);
	        model.addAttribute("employee", employee);
	        return "save_employee";
	    }

	    @PostMapping("/saveEmployee")
	    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
	        // save employee to database
	        employeeService.saveEmployee(employee);
	        return "redirect:/";
	    }

	    @GetMapping("/employeeForUpdate/{id}")
	    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

	        // get employee from the service
	        Employee employee = employeeService.getEmployeeById(id);

	        // set employee as a model attribute to pre-populate the form
	        
	        List<DesignationMaster> desiList=designationMasterService.getDesigList();
	        
	        model.addAttribute("desiList", desiList);
	        model.addAttribute("employee", employee);
	        return "update_emplyoee";
	    }

	    @GetMapping("/deleteEmployee/{id}")
	    public String deleteEmployee(@PathVariable(value = "id") long id) {

	        // call delete employee method 
	        this.employeeService.deleteEmployeeById(id);
	        return "redirect:/";
	    }
}
