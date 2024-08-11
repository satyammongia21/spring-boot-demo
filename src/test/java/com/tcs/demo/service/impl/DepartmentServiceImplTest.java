package com.tcs.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tcs.demo.model.Department;
import com.tcs.demo.repository.DepartmentRepository;

public class DepartmentServiceImplTest {

	@Mock
	private DepartmentRepository departmentRepository;

	@InjectMocks
	private DepartmentServiceImpl departmentService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateDepartment() {
		Department department = new Department();
		department.setId(1L);
		department.setName("IT");

		when(departmentRepository.save(any(Department.class))).thenReturn(department);

		Department createdDepartment = departmentService.createDepartment(department);
		assertNotNull(createdDepartment);
		assertEquals(1L, createdDepartment.getId());
		assertEquals("IT", createdDepartment.getName());
	}

	@Test
	public void testGetDepartmentById() {
		Department department = new Department();
		department.setId(1L);
		department.setName("IT");

		when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));

		Department foundDepartment = departmentService.getDepartmentById(1L);
		assertNotNull(foundDepartment);
		assertEquals(1L, foundDepartment.getId());
		assertEquals("IT", foundDepartment.getName());
	}

	@Test
	public void testGetDepartmentByIdNotFound() {
		when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> departmentService.getDepartmentById(1L));
	}

	@Test
	public void testUpdateDepartment() {
		Department existingDepartment = new Department();
		existingDepartment.setId(1L);
		existingDepartment.setName("IT");

		Department updatedDepartment = new Department();
		updatedDepartment.setId(1L);
		updatedDepartment.setName("HR");

		when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(existingDepartment));
		when(departmentRepository.save(any(Department.class))).thenReturn(updatedDepartment);

		Department result = departmentService.updateDepartment(1L, updatedDepartment);
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("HR", result.getName());
	}

	@Test
	public void testDeleteDepartment() {
		doNothing().when(departmentRepository).deleteById(anyLong());

		assertDoesNotThrow(() -> departmentService.deleteDepartment(1L));
	}
}
