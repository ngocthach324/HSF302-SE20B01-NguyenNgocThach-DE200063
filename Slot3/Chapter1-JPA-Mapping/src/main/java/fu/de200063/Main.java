package fu.de200063;

import fu.de200063.dao.DepartmentDAO;
import fu.de200063.dao.EmployeeDAO;
import fu.de200063.pojo.Department;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;
import fu.de200063.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();
        EmployeeDAO employeeDAO = new EmployeeDAO();

        Department dept = new Department("Human Resources", "Da Nang");
        departmentDAO.save(dept);
        System.out.println(">> Da tao Department: " + dept);

        Employee emp = new Employee("hr.test@company.com", "Nguyen HR", Gender.FEMALE,
                new BigDecimal("12000000.00"), LocalDate.now());
        emp.setDepartment(dept);
        employeeDAO.save(emp);
        System.out.println(">> Da tao Employee: " + emp);

        Department foundDept = departmentDAO.findById(dept.getId());
        System.out.println(">> Tim lai Department theo ID: " + foundDept);

        Employee foundEmp = employeeDAO.findById(emp.getId());
        System.out.println(">> Tim lai Employee theo ID: " + foundEmp);

        JPAUtil.close();
    }
}