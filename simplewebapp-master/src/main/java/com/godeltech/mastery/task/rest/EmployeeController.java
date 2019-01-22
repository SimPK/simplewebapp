package com.godeltech.mastery.task.rest;

import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("simplewebapp")
public class EmployeeController {
    @Autowired
    public EmployeeService employeeService;

    @GetMapping
    public List<Employee> list() {
        return employeeService.getAll();
    }

    @GetMapping("{id}")
    public Employee getOne(@PathVariable("id") Long id) {
        return employeeService.get(id);
    }

    @PostMapping
    public Employee create(@RequestBody Employee employee) {
        return employeeService.add(employee);
    }

    @PutMapping("{id}")
    public Employee update(
            @PathVariable("id") Long id,
            @RequestBody Employee employee
    ) {
        return employeeService.update(id, employee);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id) {
        employeeService.delete(id);
    }
}
