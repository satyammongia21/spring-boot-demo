package com.tcs.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
