package com.luv2code.springboot.thymeleaf.dao;

import com.luv2code.springboot.thymeleaf.entity.EmployeeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeHistoryRepository extends JpaRepository<EmployeeHistory, Long> {

    // Custom query method to find history entries by employee ID
    List<EmployeeHistory> findByEmployeeId(Long employeeId);
}
