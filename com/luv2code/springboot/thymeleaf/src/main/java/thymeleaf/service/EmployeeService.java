package com.luv2code.springboot.thymeleaf.service;

import com.luv2code.springboot.thymeleaf.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int theId);

    Employee save(Employee theEmployee);

    // New methods for soft delete and update with history logging
    void deleteEmployee(Long id);
    Employee updateEmployee(Long id, Employee updatedEmployee);

    // New method to retrieve only active (non-deleted) employees
    List<Employee> findAllActiveEmployees();

    List<Employee> findAllDeletedEmployees();


}
