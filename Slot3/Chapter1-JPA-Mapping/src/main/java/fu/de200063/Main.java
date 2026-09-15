package fu.de200063;

import fu.de200063.dao.DepartmentDAO;
import fu.de200063.pojo.Department;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;
import fu.de200063.util.JPAUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        Department it = new Department("Marketing", "Ha Noi");
        Employee e1 = new Employee("aa.nguyen@company.com", "Nguyen Van A", Gender.MALE,
                new BigDecimal("15000000"), LocalDate.of(2022, 1, 10));
        Employee e2 = new Employee("bb.tran@company.com", "Tran Thi B", Gender.FEMALE,
                new BigDecimal("18000000"), LocalDate.of(2021, 6, 1));
        Employee e3 = new Employee("cc.le@company.com", "Le Van C", Gender.OTHER,
                new BigDecimal("12000000"), LocalDate.of(2023, 3, 15));

        it.addEmployee(e1);
        it.addEmployee(e2);
        it.addEmployee(e3);

        departmentDAO.save(it);
        System.out.println(">> Da luu Department thanh cong, ID = " + it.getId());

        Department found = departmentDAO.findByIdWithEmployees(it.getId());
        if (found != null) {
            System.out.println(">> Phong ban: " + found.getName() + " (" + found.getLocation() + ")");
            System.out.println(">> Danh sach " + found.getEmployees().size() + " nhan vien:");
            for (Employee e : found.getEmployees()) {
                System.out.println("   - " + e);
            }
        }

        JPAUtil.close();
    }
}