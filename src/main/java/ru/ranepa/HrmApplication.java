package ru.ranepa;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;

import java.time.LocalDate;

public class HrmApplication {
    public static void main(String[] args) {
        Employee emp = new Employee(
                "Khromin Ivan Evgenevich",
                "java developer",
                200_000.0,
                LocalDate.of(2026, 3, 2)
        );
        System.out.println(emp.getSalary());

        var repo = new EmployeeRepositoryImpl();
        System.out.println("===========");
        System.out.println(repo.save(emp));
        System.out.println("===========");
        var emp1 = repo.findById(1L)
                .orElseThrow();
        System.out.println("Employee was found: " + emp1);

    }
}