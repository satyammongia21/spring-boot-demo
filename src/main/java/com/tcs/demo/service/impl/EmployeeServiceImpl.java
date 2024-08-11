package com.tcs.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.demo.exception.ResourceNotFoundException;
import com.tcs.demo.model.Employee;
import com.tcs.demo.repository.EmployeeRepository;
import com.tcs.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	private final EmployeeRepository employeeRepository;

	public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	@Override
	public Employee createEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Override
	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
	}

	@Override
	public Employee updateEmployee(Long id, Employee employee) {
		if (!employeeRepository.existsById(id)) {
			throw new ResourceNotFoundException("Employee not found with id " + id);
		}
		employee.setId(id);
		return employeeRepository.save(employee);
	}

	@Override
	public void deleteEmployee(Long id) {
		if (!employeeRepository.existsById(id)) {
			throw new ResourceNotFoundException("Employee not found with id " + id);
		}
		employeeRepository.deleteById(id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}
}
