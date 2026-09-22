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

        System.out.println("========== TODO 5.8: THONG KE SO NV ACTIVE VA TONG LUONG THEO PROJECT ==========");

        if (projectDAO.findAll().isEmpty()) {
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
        }

        List<Object[]> stats = projectDAO.getActiveEmployeeStatsByProject();
        for (Object[] row : stats) {
            String projectName = (String) row[0];
            Long count = (Long) row[1];
            BigDecimal totalSalary = (BigDecimal) row[2];
            System.out.println(">> Du an: " + projectName + " | So NV active: " + count + " | Tong luong: " + totalSalary);
        }

        JPAUtil.close();
    }
}