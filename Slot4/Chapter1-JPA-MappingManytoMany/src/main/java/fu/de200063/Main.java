package fu.de200063;

import fu.de200063.dao.EmployeeDAO;
import fu.de200063.dao.ProjectDAO;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;
import fu.de200063.pojo.Project;
import fu.de200063.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ProjectDAO projectDAO = new ProjectDAO();

        System.out.println("========== TODO 5.7: TAO VA PHAN CONG DU AN N-N ==========");

        Project pA = new Project("PRJ-A", "E-Commerce System", new BigDecimal("50000.00"), LocalDate.of(2024, 1, 1), null);
        Project pB = new Project("PRJ-B", "Mobile Banking App", new BigDecimal("80000.00"), LocalDate.of(2024, 3, 15), null);

        projectDAO.save(pA);
        projectDAO.save(pB);

        Employee e1 = new Employee("Nguyen Van A", new BigDecimal("1500.00"), LocalDate.of(2023, 1, 15), "anv@company.com", Gender.MALE);
        Employee e2 = new Employee("Tran Thi B", new BigDecimal("2200.00"), LocalDate.of(2022, 5, 10), "btt@company.com", Gender.FEMALE);
        Employee e3 = new Employee("Le Van C", new BigDecimal("1800.00"), LocalDate.of(2024, 3, 1), "clv@company.com", Gender.OTHER);

        employeeDAO.save(e1);
        employeeDAO.save(e2);
        employeeDAO.save(e3);

        employeeDAO.assignEmployeeToProject(e1.getId(), pA.getId());
        employeeDAO.assignEmployeeToProject(e1.getId(), pB.getId());
        employeeDAO.assignEmployeeToProject(e2.getId(), pB.getId());
        employeeDAO.assignEmployeeToProject(e3.getId(), pA.getId());

        System.out.println("\n--- DANH SACH PROJECT CUA TUNG NHAN VIEN ---");
        List<Employee> employees = employeeDAO.findAllWithProjects();
        for (Employee emp : employees) {
            System.out.println(">> Nhan vien: " + emp.getFullName() + " (" + emp.getEmail() + ") - So du an: " + emp.getProjects().size());
            for (Project proj : emp.getProjects()) {
                System.out.println("   + " + proj.getProjectCode() + " - " + proj.getProjectName());
            }
        }

        System.out.println("\n--- DANH SACH NHAN VIEN CUA TUNG PROJECT ---");
        List<Project> projects = projectDAO.findAllWithEmployees();
        for (Project proj : projects) {
            System.out.println(">> Du an: " + proj.getProjectName() + " (" + proj.getProjectCode() + ") - So thanh vien: " + proj.getEmployees().size());
            for (Employee emp : proj.getEmployees()) {
                System.out.println("   + " + emp.getFullName() + " (" + emp.getEmail() + ")");
            }
        }

        JPAUtil.close();
    }
}