package ru.ranepa.service;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepository;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    // Добавляем инициализацию для того, чтобы объявить переменную
    public BigDecimal calculateAverageSalary() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();

        BigDecimal sumSalary = BigDecimal.ZERO;
        int count = 0;

        for (Employee employee : allEmployees) {
            sumSalary = sumSalary.add(employee.getSalary());
            count++;
        }
        if (count==0) {
            return BigDecimal.ZERO;
        }
        return sumSalary.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    public Optional<Employee> findTopPaidEmployee() {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        Employee topPaid = null;
        BigDecimal maxSalary = BigDecimal.ZERO;

        for (Employee employee : allEmployees) {
            if (topPaid == null || employee.getSalary().compareTo(maxSalary) > 0) {
                topPaid = employee;
                maxSalary = employee.getSalary();
            }

        }
        return Optional.ofNullable(topPaid);
    }

    public List<Employee> filtrationEmployee(String position) {
        Iterable<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> filtrationEmployee = new ArrayList<>();

        for (Employee employee : allEmployees) {
            if (employee.getPosition().equalsIgnoreCase(position)) {
                filtrationEmployee.add(employee);
            }
        }
        return filtrationEmployee;
    }

}


