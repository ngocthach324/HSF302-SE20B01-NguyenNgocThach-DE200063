package fu.de200063;

import fu.de200063.dao.DepartmentDAO;
import fu.de200063.pojo.Department;
import fu.de200063.pojo.Employee;
import fu.de200063.util.JPAUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        System.out.println("========== TODO 2.9: FIX N+1 QUERY VOI JOIN FETCH ==========");

        List<Department> departments = departmentDAO.findAllWithEmployees();

        System.out.println(">> So luong phong ban lay duoc: " + departments.size());

        for (Department d : departments) {
            System.out.println(">> Phong ban: " + d.getName() + " - So nhan vien: " + d.getEmployees().size());
            for (Employee e : d.getEmployees()) {
                System.out.println("   - " + e.getFullName() + " (" + e.getEmail() + ")");
            }
        }

        JPAUtil.close();
    }
}