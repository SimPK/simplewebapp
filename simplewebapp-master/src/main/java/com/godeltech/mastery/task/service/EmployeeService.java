package com.godeltech.mastery.task.service;

import com.godeltech.mastery.task.dao.EmployeeDao;
import com.godeltech.mastery.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeDao employeeDao;

    public Employee add(Employee employee){
        return employeeDao.add(employee);
    }

    public List<Employee> getAll(){
        return employeeDao.getAll();
    }

    public Employee get(Long id){
        return employeeDao.get(id);
    }

    public Employee update(Long id, Employee employee){
        return employeeDao.update(id, employee);
    }

    public void delete(Long id){
        employeeDao.delete(id);
    }
}
