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

        System.out.println("========== TODO 5.10: TIM NHAN VIEN THAM GIA > 1 PROJECT ==========");

        List<Project> projectList = projectDAO.findAll();
        List<Employee> employeeList = employeeDAO.findAll();

        if (projectList.isEmpty()) {
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
        } else {
            Employee empA = employeeList.get(0);
            Project prjA = projectList.get(0);
            Project prjB = projectList.get(1);
            employeeDAO.assignEmployeeToProject(empA.getId(), prjA.getId());
            employeeDAO.assignEmployeeToProject(empA.getId(), prjB.getId());
        }

        List<Employee> multiProjectEmployees = employeeDAO.findActiveEmployeesInMultipleProjects();
        System.out.println(">> So luong nhan vien active tham gia > 1 du an: " + multiProjectEmployees.size());
        for (Employee emp : multiProjectEmployees) {
            System.out.println(">> Nhan vien: " + emp.getFullName() + " (" + emp.getEmail() + ") - So du an: " + emp.getProjects().size());
            for (Project proj : emp.getProjects()) {
                System.out.println("   + " + proj.getProjectCode() + " - " + proj.getProjectName());
            }
        }

        JPAUtil.close();
    }
}