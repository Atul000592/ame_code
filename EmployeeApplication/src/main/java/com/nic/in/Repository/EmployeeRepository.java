package com.nic.in.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nic.in.modal.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
