package fu.de200063;

import fu.de200063.pojo.Department;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Department dept = new Department("IT", "Ha Noi");
        Employee emp = new Employee("test@company.com", "Test", Gender.OTHER,
                new BigDecimal("1000"), LocalDate.now());

        dept.addEmployee(emp);

        System.out.println(">> Test 1 (dept chua emp): " + dept.getEmployees().contains(emp));
        System.out.println(">> Test 2 (emp tro ve dept): " + (emp.getDepartment() == dept));
    }
}