package com.tcs.demo.service;

import java.util.List;

import com.tcs.demo.model.Employee;

public interface EmployeeService {
	Employee createEmployee(Employee employee);

	Employee getEmployeeById(Long id);

	Employee updateEmployee(Long id, Employee employee);

	void deleteEmployee(Long id);

	List<Employee> getAllEmployees();
}
