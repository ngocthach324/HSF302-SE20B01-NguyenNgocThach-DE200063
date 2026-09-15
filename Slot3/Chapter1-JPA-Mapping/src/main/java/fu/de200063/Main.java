package fu.de200063;

import fu.de200063.dao.DepartmentDAO;
import fu.de200063.pojo.Department;
import fu.de200063.pojo.Employee;
import fu.de200063.util.JPAUtil;

public class Main {
    public static void main(String[] args) {
        DepartmentDAO departmentDAO = new DepartmentDAO();

        Department found = departmentDAO.findByIdWithEmployees(1L);
        if (found != null) {
            System.out.println(">> [TODO 2.6 JOIN FETCH] Phong ban: " + found.getName() + " - " + found.getLocation());
            System.out.println(">> So luong nhan vien: " + found.getEmployees().size());
            for (Employee e : found.getEmployees()) {
                System.out.println("   - " + e);
            }
        }

        JPAUtil.close();
    }
}