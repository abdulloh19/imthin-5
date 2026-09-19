package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class Task1 {
    static void main() {
        List<Employee> employees = List.of(
                new Employee(1, "Abdulaziz", 30, "Uzbeksitan"),
                new Employee(2, "Zubayr", 10, "Russia"),
                new Employee(3, "Sumayya", 15, "Kazakhstan"),
                new Employee(4, "Sakina", 12, "Tojikiston"),
                new Employee(5, "Ummugulsum", 20, "Uzbeksitan")

        );
        List<Employee> uzbeksitan = EmployeeFilter
                .filter(employees, n -> n.getCountry()
                        .equals("Uzbeksitan"));
        uzbeksitan.forEach(System.out::println);
    }
}

@FunctionalInterface
interface Inter<T> {
    boolean test(T t);
}

class EmployeeFilter {
    public static List<Employee> filter(List<Employee> employees, Inter<Employee> employeeInter) {
        List<Employee> filteredEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (employeeInter.test(employee)) {
                filteredEmployees.add(employee);
            }
        }
        return filteredEmployees;
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Employee {
    private long id;
    private String name;
    private int age;
    private String country;
}