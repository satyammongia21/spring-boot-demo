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

import com.tcs.demo.model.Department;
import com.tcs.demo.service.DepartmentService;

public class DepartmentControllerTest {

	private MockMvc mockMvc;

	@Mock
	private DepartmentService departmentService;

	@InjectMocks
	private DepartmentController departmentController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(departmentController).build();
	}

	@Test
	public void testCreateDepartment() throws Exception {
		Department department = new Department();
		department.setId(1L);
		department.setName("IT");

		when(departmentService.createDepartment(any(Department.class))).thenReturn(department);

		mockMvc.perform(post("/departments").contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"IT\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("IT"));
	}

	@Test
	public void testGetDepartment() throws Exception {
		Department department = new Department();
		department.setId(1L);
		department.setName("IT");

		when(departmentService.getDepartmentById(anyLong())).thenReturn(department);

		mockMvc.perform(MockMvcRequestBuilders.get("/departments/{id}", 1L)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("IT"));
	}

	@Test
	public void testUpdateDepartment() throws Exception {
		Department existingDepartment = new Department();
		existingDepartment.setId(1L);
		existingDepartment.setName("IT");

		Department updatedDepartment = new Department();
		updatedDepartment.setId(1L);
		updatedDepartment.setName("HR");

		when(departmentService.updateDepartment(anyLong(), any(Department.class))).thenReturn(updatedDepartment);

		mockMvc.perform(MockMvcRequestBuilders.put("/departments/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.content("{\"name\":\"HR\"}")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("HR"));
	}

	@Test
	public void testDeleteDepartment() throws Exception {
		doNothing().when(departmentService).deleteDepartment(anyLong());

		mockMvc.perform(MockMvcRequestBuilders.delete("/departments/{id}", 1L)).andExpect(status().isNoContent());
	}

	@Test
	public void testGetAllDepartments() throws Exception {
		Department department1 = new Department();
		department1.setId(1L);
		department1.setName("IT");

		Department department2 = new Department();
		department2.setId(2L);
		department2.setName("HR");

		List<Department> departments = Arrays.asList(department1, department2);

		when(departmentService.getAllDepartments()).thenReturn(departments);

		mockMvc.perform(MockMvcRequestBuilders.get("/departments")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[0].name").value("IT"))
				.andExpect(jsonPath("$[1].id").value(2)).andExpect(jsonPath("$[1].name").value("HR"));
	}
}
