package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.List;
import java.util.Optional;
//Alt+enter -> implement interface
public interface EmployeeRepository {
    String save(Employee employee);
    Optional<Employee> findById(Long id);
    Iterable<Employee> findAll();
    String delete(Long id);

}