package com.tcs.demo.service;

import java.util.List;

import com.tcs.demo.model.Department;

public interface DepartmentService {
	Department createDepartment(Department department);

	Department getDepartmentById(Long id);

	Department updateDepartment(Long id, Department department);

	void deleteDepartment(Long id);

	List<Department> getAllDepartments();
}