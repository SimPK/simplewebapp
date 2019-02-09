package com.godeltech.mastery.task.rest;

import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.Gender;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeController employeeController;

    @Test
    public void list() throws Exception {
        Employee employee = new Employee();
        Employee employee1 = new Employee();
        employee.setFirstName("fname1");
        employee1.setFirstName("fname2");
        List<Employee> list = new ArrayList<>();
        list.add(employee);
        list.add(employee1);

        given(employeeController.list()).willReturn(list);

        mvc.perform(get("/simplewebapp/"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(2)))
            .andExpect(jsonPath("$[1].firstName", is(employee1.getFirstName())));
    }

    @Test
    public void getOne() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("fname1");
        employee.setEmployeeId(1L);

        given(employeeController.getOne(employee.getEmployeeId())).willReturn(employee);

        mvc.perform(get("/simplewebapp/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("firstName", is(employee.getFirstName())));
    }

    @Test
    public void create() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("fname1");
        employee.setLastName("lname1");
        employee.setJobTitle("doctor");
        employee.setEmployeeId(1L);
        employee.setDateOfBirth("01/01/90");
        employee.setGender(Gender.MALE);
        employee.setDepartmentId(1L);

        Gson gson = new Gson();
        String json = gson.toJson(employee);

        given(employeeController.create(employee)).willReturn(employee);

        mvc.perform(post("/simplewebapp/")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("lastName", is("lname1")));
    }

    @Test
    public void deleteEmp() throws Exception {
        mvc.perform(delete("/simplewebapp/1"))
            .andExpect(status().isOk());
    }

    @Test
    public void update() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("fname1");
        employee.setLastName("lname1");
        employee.setJobTitle("doctor");
        employee.setEmployeeId(1L);
        employee.setDateOfBirth("01/01/90");
        employee.setGender(Gender.MALE);
        employee.setDepartmentId(1L);

        Gson gson = new Gson();
        String json = gson.toJson(employee);

        given(employeeController.update(employee.getEmployeeId(),employee)).willReturn(employee);

        mvc.perform(put("/simplewebapp/1")
            .content(json)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("firstName", is("fname1")));
    }
}
