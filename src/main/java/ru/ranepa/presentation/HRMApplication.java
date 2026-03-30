package ru.ranepa.presentation;

import ru.ranepa.model.Employee;
import ru.ranepa.repository.EmployeeRepositoryImpl;
import ru.ranepa.service.EmployeeService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HRMApplication {
    private static final EmployeeRepositoryImpl repository = new EmployeeRepositoryImpl();
    private static final EmployeeService service = new EmployeeService(repository);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            printMenu();

            int number = getIntInput("Choose a number of menu: ");

            switch (number) {
                case 1:
                    showAllEmployees();
                    break;
                case 2:
                    addEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    findEmployeeById();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid number. Please try again. Choose a number from menu!");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Menu:");
        System.out.println("1.Show all employees");
        System.out.println("2.Add employee");
        System.out.println("3.Delete employee");
        System.out.println("4.Find employee by ID");
        System.out.println("5.Show statistics");
        System.out.println("6.Exit");
    }

    private static void showStatistics() {
        System.out.println("\nStatistics");

        System.out.println("Average salary: " + service.calculateAverageSalary());

        Optional<Employee> topPaid = service.findTopPaidEmployee();
        if (topPaid.isPresent()) {
            System.out.println("Top paid employee: " + topPaid.get());
        } else {
            System.out.println("No employees found");
        }
    }

    private static void findEmployeeById() {
        System.out.println("\nFind Employee by ID");
        Long id = getLongInput("Enter employee ID: ");
        Optional<Employee> employee = repository.findById(id);

        if (employee.isPresent()) {
            System.out.println("Employee found: " + employee.get());
        } else {
            System.out.println("Employee with ID " + id + " not found.");
        }
    }

    private static void deleteEmployee() {
        System.out.println("\nDelete Employee");
        Long id =   getLongInput("Enter employee ID to delete: ");
        String result = repository.delete(id);
        System.out.println(result);
    }

    private static void addEmployee() {
        System.out.println("\nAdd new employee");

        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        System.out.print("Enter position: ");
        String position = scanner.nextLine();

        double salary = getDoubleInput("Enter salary: ");

        LocalDate hireDate = null;
        while (hireDate == null) {
            System.out.print("Enter hire date (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            try {
                hireDate = LocalDate.parse(dateStr);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD");
            }
        }

        Employee employee = new Employee(name, position, salary, hireDate);
        String result = repository.save(employee);
        System.out.println(result);
    }

    private static void showAllEmployees() {
        Iterable<Employee> employees = repository.findAll();
        List<Employee> list = new ArrayList<>();
        employees.forEach(list::add);

        if (list.isEmpty()) {
            System.out.println("\nNo employees found");
        } else {
            System.out.println("\nAll Employees");
            for (Employee emp : list) {
                System.out.println(emp);
            }
            System.out.println("Total: " + list.size() + " employees");
        }
    }

    private static int getIntInput(String number) {
        while (true) {
            System.out.print(number);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please try again");
            }
        }
    }

    private static long getLongInput(String number) {
        while (true) {
            System.out.print(number);
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please try again");
            }
        }
    }

    private static double getDoubleInput(String number) {
        while (true) {
            System.out.print(number);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please try again");
            }
        }
    }
}