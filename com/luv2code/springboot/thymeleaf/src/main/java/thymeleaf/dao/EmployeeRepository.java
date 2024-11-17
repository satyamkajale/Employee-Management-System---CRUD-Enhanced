package com.luv2code.springboot.thymeleaf.dao;

import com.luv2code.springboot.thymeleaf.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Method to sort by last name
    List<Employee> findAllByOrderByLastNameAsc();

    // Custom query to retrieve only active (non-deleted) employees
    @Query("SELECT e FROM Employee e WHERE e.isDeleted = false")
    List<Employee> findAllActiveEmployees();

    // Method to find only soft-deleted employees
    @Query("SELECT e FROM Employee e WHERE e.isDeleted = true")
    List<Employee> findAllDeletedEmployees();
}
