package fu.de200063;

import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;
import fu.de200063.pojo.Project;
import fu.de200063.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("========== TEST TODO 5.4: EQUALS VA HASHCODE ==========");

        Employee e1 = new Employee("Nguyen Van A", new BigDecimal("1500.00"), LocalDate.of(2023, 1, 15), "anv@company.com", Gender.MALE);
        Employee e2 = new Employee("Nguyen Van A Khac", new BigDecimal("2000.00"), LocalDate.of(2024, 2, 20), "anv@company.com", Gender.MALE);

        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(e1);
        employeeSet.add(e2);

        System.out.println(">> So luong Employee trong Set (cung email): " + employeeSet.size());
        System.out.println(">> e1.equals(e2): " + e1.equals(e2));

        Project p1 = new Project("PRJ-01", "Banking App", new BigDecimal("50000.00"), LocalDate.of(2024, 1, 1), null);
        Project p2 = new Project("PRJ-01", "Banking App Version 2", new BigDecimal("80000.00"), LocalDate.of(2024, 6, 1), null);

        Set<Project> projectSet = new HashSet<>();
        projectSet.add(p1);
        projectSet.add(p2);

        System.out.println(">> So luong Project trong Set (cung projectCode): " + projectSet.size());
        System.out.println(">> p1.equals(p2): " + p1.equals(p2));

        EntityManager em = JPAUtil.getEntityManager();
        System.out.println(">> Khoi tao EntityManager va tao bang thanh cong!");
        em.close();
        JPAUtil.close();
    }
}