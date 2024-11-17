package com.luv2code.springboot.thymeleaf.service;

import com.luv2code.springboot.thymeleaf.dao.EmployeeRepository;
import com.luv2code.springboot.thymeleaf.dao.EmployeeHistoryRepository;
import com.luv2code.springboot.thymeleaf.entity.Employee;
import com.luv2code.springboot.thymeleaf.entity.EmployeeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeHistoryRepository employeeHistoryRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository theEmployeeRepository, EmployeeHistoryRepository theEmployeeHistoryRepository) {
        employeeRepository = theEmployeeRepository;
        employeeHistoryRepository = theEmployeeHistoryRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }

    @Override
    public List<Employee> findAllActiveEmployees() {
        return employeeRepository.findAllActiveEmployees();
    }

    @Override
    public List<Employee> findAllDeletedEmployees() {
        return employeeRepository.findAllDeletedEmployees();
    }

    @Override
    public Employee findById(int theId) {
        Optional<Employee> result = employeeRepository.findById(theId);

        return result.orElseThrow(() -> new RuntimeException("Did not find employee id - " + theId));
    }

    @Override
    public Employee save(Employee theEmployee) {
        return employeeRepository.save(theEmployee);
    }

//    @Override
//    public void deleteById(int theId) {
//        employeeRepository.deleteById(theId);
//    }

    // New deleteEmployee method for soft delete
    @Override
    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Employee not found with id - " + id));

        employee.setIsDeleted(true);
        employee.setDeletedAt(LocalDateTime.now());
        employeeRepository.save(employee); // Soft delete by updating fields
    }

    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        // Retrieve the existing employee
        Employee employee = employeeRepository.findById(id.intValue())
                .orElseThrow(() -> new RuntimeException("Employee not found with id - " + id));

        EmployeeHistory history = new EmployeeHistory();
        history.setEmployeeId((long) employee.getId());
        history.setFirstName(employee.getFirstName());
        history.setLastName(employee.getLastName());
        history.setEmail(employee.getEmail());
        history.setModifiedAt(LocalDateTime.now());
        employeeHistoryRepository.save(history);

        // Delete the old record from the employee table
        employeeRepository.deleteById(id.intValue());

        // Create a new employee entry with the updated details
        Employee newEmployee = new Employee();
        newEmployee.setFirstName(updatedEmployee.getFirstName());
        newEmployee.setLastName(updatedEmployee.getLastName());
        newEmployee.setEmail(updatedEmployee.getEmail());

        // Save the new employee record
        return employeeRepository.save(newEmployee);
    }
    
}
