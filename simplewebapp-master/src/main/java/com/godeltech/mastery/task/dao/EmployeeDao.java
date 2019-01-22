package com.godeltech.mastery.task.dao;

import com.godeltech.mastery.task.dto.Employee;
import com.godeltech.mastery.task.dto.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDao {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    public Employee add(Employee employee) {
        String sql = "INSERT INTO EMPLOYEE " +
                "(FIRST_NAME, " +
                "LAST_NAME, " +
                "DEPARTMENT_ID, " +
                "JOB_TITLE, " +
                "GENDER, " +
                "DATE_OF_BIRTH) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartmentId(),
                employee.getJobTitle(),
                employee.getGender().toString(),
                employee.getDateOfBirth());
        sql = "SELECT * FROM EMPLOYEE WHERE FIRST_NAME=?";
        return jdbcTemplate.queryForObject(sql, new EmployeeMapper(),employee.getFirstName());
    }

    public List<Employee> getAll() {
        String sql = "SELECT * FROM EMPLOYEE";
        return jdbcTemplate.query(sql, new EmployeeMapper());
    }

    public Employee get(Long id) {
        String sql = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_ID=?";
        return jdbcTemplate.queryForObject(sql, new EmployeeMapper(), id);
    }

    public Employee update(Long id, Employee employee) {
        String sql = "UPDATE EMPLOYEE " +
                "SET FIRST_NAME=?, " +
                "LAST_NAME=?, " +
                "DEPARTMENT_ID=?, " +
                "JOB_TITLE=?, " +
                "GENDER=?, " +
                "DATE_OF_BIRTH=? " +
                "WHERE EMPLOYEE_ID=?";
        jdbcTemplate.update(sql,
                employee.getFirstName(),
                employee.getLastName(),
                employee.getDepartmentId(),
                employee.getJobTitle(),
                employee.getGender().toString(),
                employee.getDateOfBirth(),
                id);
        return get(id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM EMPLOYEE WHERE EMPLOYEE_ID=?";
        jdbcTemplate.update(sql, id);
    }

}
