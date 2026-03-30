package ru.ranepa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        // Перед каждым тестом создаём новый репозиторий и сервис
        // Это гарантирует, что тесты не влияют друг на друга
        repository = new EmployeeRepositoryImpl();
        employeeService = new EmployeeService(repository);
    }

    // Тест 1: Средняя зарплата для [1000, 2000, 3000] = 2000
    @Test
    void shouldCalculateAverageSalary() {
        // 1. Подготовка (Given)
        repository.save(new Employee("Sergeev Vladimir Nikolaevich", "Developer", 1000, LocalDate.now()));
        repository.save(new Employee("Smirnova Aleksandra Mikhailovna", "Manager", 2000, LocalDate.now()));
        repository.save(new Employee("Petrov Maksim Alekseevich", "Designer", 3000, LocalDate.now()));

        // 2. Действие (When)
        BigDecimal average = employeeService.calculateAverageSalary();

        // 3. Проверка (Then)
        // Сравниваем через compareTo, потому что BigDecimal нельзя сравнивать через ==
        assertEquals(0, average.compareTo(new BigDecimal("2000.00")),
                "Средняя зарплата должна быть 2000.00");
    }

    // Тест 2: Поиск сотрудника с самой высокой зарплатой
    @Test
    void shouldFindTopPaidEmployee() {
        // 1. Подготовка (Given)
        repository.save(new Employee("Sergeev Vladimir Nikolaevich", "Developer", 1000, LocalDate.now()));
        repository.save(new Employee("Smirnova Aleksandra Mikhailovna", "Manager", 5000.00, LocalDate.now()));
        repository.save(new Employee("Petrov Maksim Alekseevich", "Designer", 2000.00, LocalDate.now()));

        // 2. Действие (When)
        Optional<Employee> top = employeeService.findTopPaidEmployee();

        // 3. Проверка (Then)
        assertTrue(top.isPresent(), "Должен быть найден сотрудник");
        assertEquals("Smirnova Aleksandra Mikhailovna", top.get().getName(), "Имя сотрудника должно быть Maria");
        assertEquals(0, top.get().getSalary().compareTo(new BigDecimal("5000.00")),
                "Зарплата должна быть 5000.00");
    }

    // Тест 3: Пустой список → метод возвращает пустой Optional
    @Test
    void shouldReturnEmptyWhenNoEmployees() {
        // 1. Подготовка (Given) — ничего не добавляем, репозиторий пустой

        // 2. Действие (When)
        Optional<Employee> top = employeeService.findTopPaidEmployee();

        // 3. Проверка (Then)
        assertTrue(top.isEmpty(), "Optional должен быть пустым, когда сотрудников нет");
    }
}


