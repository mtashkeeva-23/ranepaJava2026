package ru.ranepa.repository;

import ru.ranepa.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private Map<Long, Employee> employees = new HashMap<>();
    private Long nextId = 1L;  // Убрала static

    @Override
    public String save(Employee employee) {
        employee.setId(nextId++);
        employees.put(employee.getId(), employee);
        return "Employee " + employee.getId() + " was saved successfuly";
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(employees.get(id)); // Добавила ofNullable
    }

    @Override
    public Iterable<Employee> findAll() {
        return employees.values();
    } // Возвращаем список сотрудников

    @Override
    public String delete(Long id) {   // Добавлена реализация, чтобы проверить существует ли такой сотрудник, чтобы его удалить
        if (employees.remove(id) != null) {
            return "Employee with ID " + id + " was deleted successfully";
        }
        return "Employee with ID " + id + " not found";
    }
}