package com.tcs.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tcs.demo.exception.ResourceNotFoundException;
import com.tcs.demo.model.Employee;
import com.tcs.demo.repository.EmployeeRepository;

public class EmployeeServiceImplTest {

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	public EmployeeServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreateEmployee() {
		Employee employee = new Employee(1L, "Satyam Mongia", null);
		when(employeeRepository.save(employee)).thenReturn(employee);

		Employee createdEmployee = employeeService.createEmployee(employee);
		assertNotNull(createdEmployee);
		assertEquals("Satyam Mongia", createdEmployee.getName());
	}

	@Test
	public void testGetEmployeeById() {
		Employee employee = new Employee(1L, "Satyam Mongia", null);
		when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(employee));

		Employee foundEmployee = employeeService.getEmployeeById(1L);
		assertNotNull(foundEmployee);
		assertEquals("Satyam Mongia", foundEmployee.getName());
	}

	@Test
	public void testGetEmployeeByIdNotFound() {
		when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

		ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.getEmployeeById(1L);
		});

		assertEquals("Employee not found with id 1", thrown.getMessage());
	}

	@Test
	public void testUpdateEmployee() {
		Employee existingEmployee = new Employee(1L, "Satyam Mongia", null);
		Employee updatedEmployee = new Employee(1L, "Satyam", null);
		when(employeeRepository.findById(anyLong())).thenReturn(Optional.of(existingEmployee));
		when(employeeRepository.save(updatedEmployee)).thenReturn(updatedEmployee);

		Employee result = employeeService.updateEmployee(anyLong(), updatedEmployee);
		assertNotNull(result);
		assertEquals("Satyam", result.getName());
	}

	@Test
	public void testDeleteEmployee() {
		when(employeeRepository.existsById(anyLong())).thenReturn(true);
		employeeService.deleteEmployee(1L);
		verify(employeeRepository, times(1)).deleteById(1L);
	}
}
