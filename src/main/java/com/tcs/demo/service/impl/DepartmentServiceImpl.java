package com.tcs.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tcs.demo.exception.ResourceNotFoundException;
import com.tcs.demo.model.Department;
import com.tcs.demo.repository.DepartmentRepository;
import com.tcs.demo.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	private final DepartmentRepository departmentRepository;

	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public Department createDepartment(Department department) {
		return departmentRepository.save(department);
	}

	@Override
	public Department getDepartmentById(Long id) {
		return departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
	}

	@Override
	public Department updateDepartment(Long id, Department department) {
		if (!departmentRepository.existsById(id)) {
			throw new ResourceNotFoundException("Department not found with id " + id);
		}
		department.setId(id);
		return departmentRepository.save(department);
	}

	@Override
	public void deleteDepartment(Long id) {
		if (!departmentRepository.existsById(id)) {
			throw new ResourceNotFoundException("Department not found with id " + id);
		}
		departmentRepository.deleteById(id);
	}

	@Override
	public List<Department> getAllDepartments() {
		return departmentRepository.findAll();
	}
}