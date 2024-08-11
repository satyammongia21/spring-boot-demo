package com.tcs.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.tcs.demo.model.Employee;
import com.tcs.demo.service.DepartmentService;
import com.tcs.demo.service.EmployeeService;

public class EmployeeControllerTest {

	private MockMvc mockMvc;

	@Mock
	private EmployeeService employeeService;

	@Mock
	private DepartmentService departmentService;

	@InjectMocks
	private EmployeeController employeeController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
	}

	@Test
	public void testCreateEmployee() throws Exception {

		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Satyam Mongia");

		when(employeeService.createEmployee(any(Employee.class))).thenReturn(employee);

		mockMvc.perform(
				post("/employees").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"Satyam Mongia\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("Satyam Mongia"));
	}

	@Test
	public void testGetEmployee() throws Exception {
		Employee employee = new Employee();
		employee.setId(1L);
		employee.setName("Satyam Mongia");

		when(employeeService.getEmployeeById(anyLong())).thenReturn(employee);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees/{id}", 1L)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Satyam Mongia"));
	}

	@Test
	public void testUpdateEmployee() throws Exception {
		Employee existingEmployee = new Employee();
		existingEmployee.setId(1L);
		existingEmployee.setName("Satyam Mongia");

		Employee updatedEmployee = new Employee();
		updatedEmployee.setId(1L);
		updatedEmployee.setName("Satyam");

		when(employeeService.updateEmployee(anyLong(), any(Employee.class))).thenReturn(updatedEmployee);

		mockMvc.perform(MockMvcRequestBuilders.put("/employees/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"Satyam\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Satyam"));
	}

	@Test
	public void testDeleteEmployee() throws Exception {
		doNothing().when(employeeService).deleteEmployee(anyLong());
		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/{id}", 1L)).andExpect(status().isNoContent());
	}

	@Test
	public void testGetAllEmployees() throws Exception {
		Employee employee1 = new Employee();
		employee1.setId(1L);
		employee1.setName("Satyam Mongia");

		Employee employee2 = new Employee();
		employee2.setId(2L);
		employee2.setName("Satyam");

		List<Employee> employees = Arrays.asList(employee1, employee2);

		when(employeeService.getAllEmployees()).thenReturn(employees);

		mockMvc.perform(MockMvcRequestBuilders.get("/employees")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].name").value("Satyam Mongia"))
				.andExpect(jsonPath("$[1].id").value(2)).andExpect(jsonPath("$[1].name").value("Satyam"));
	}
}