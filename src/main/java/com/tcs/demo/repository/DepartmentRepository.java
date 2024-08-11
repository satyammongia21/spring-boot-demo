package com.tcs.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.demo.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
