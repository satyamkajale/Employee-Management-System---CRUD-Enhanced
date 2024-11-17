package com.luv2code.springboot.thymeleaf.controller;

import com.luv2code.springboot.thymeleaf.entity.Employee;
import com.luv2code.springboot.thymeleaf.entity.EmployeeHistory;
import com.luv2code.springboot.thymeleaf.service.EmployeeService;
import com.luv2code.springboot.thymeleaf.dao.EmployeeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeHistoryRepository employeeHistoryRepository;

    @Autowired
    public EmployeeController(EmployeeService theEmployeeService, EmployeeHistoryRepository theEmployeeHistoryRepository) {
        employeeService = theEmployeeService;
        employeeHistoryRepository = theEmployeeHistoryRepository;
    }

    // List only active (non-deleted) employees
    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAllActiveEmployees());
        return "employees/list-employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        Employee theEmployee = new Employee();
        model.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("employeeId") int theId, Model model) {
        Employee theEmployee = employeeService.findById(theId);
        model.addAttribute("employee", theEmployee);
        return "employees/employee-form";
    }

    @PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee) {
        employeeService.save(theEmployee);
        return "redirect:/employees/list";
    }

    // Soft delete the employee
    @GetMapping("/delete")
    public String delete(@RequestParam("employeeId") Long theId) {
        employeeService.deleteEmployee(theId);
        return "redirect:/employees/list";
    }

    // View the history of a specific employee
    @GetMapping("/history/{id}")
    public String viewEmployeeHistory(@PathVariable Long id, Model model) {
        List<EmployeeHistory> historyList = employeeHistoryRepository.findByEmployeeId(id);
        model.addAttribute("history", historyList);
        return "employees/employee-history";
    }

    @GetMapping("/deleted")
    public String listDeletedEmployees(Model model) {
        List<Employee> deletedEmployees = employeeService.findAllDeletedEmployees();
        model.addAttribute("deletedEmployees", deletedEmployees);
        return "employees/deleted-employees";
    }

}
