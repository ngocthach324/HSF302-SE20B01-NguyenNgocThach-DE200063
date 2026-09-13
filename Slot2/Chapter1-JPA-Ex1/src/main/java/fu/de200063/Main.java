package fu.de200063;

import fu.de200063.dao.EmployeeDAO;
import fu.de200063.pojo.Employee;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // ===== READ CÓ ĐIỀU KIỆN (TODO 0.5) =====
        System.out.println("========== TEST TODO 0.5: JPQL QUERIES ==========");

        // 1. Tìm theo Email
        String searchEmail = "a@fpt.edu.vn";
        Employee empByEmail = dao.findByEmail(searchEmail);
        System.out.println(">> Tìm theo email (" + searchEmail + "): " + empByEmail);

        // 2. Tìm theo Email không tồn tại (Kiểm tra xử lý danh sách rỗng, không bị văng Exception)
        String notFoundEmail = "notfound@fpt.edu.vn";
        Employee empNotFound = dao.findByEmail(notFoundEmail);
        System.out.println(">> Tìm theo email không tồn tại (" + notFoundEmail + "): " + empNotFound);

        // 3. Tìm nhân viên có lương > 14.000.000 và đang active = true
        BigDecimal minSalary = new BigDecimal("14000000.00");
        List<Employee> highSalaryList = dao.findBySalaryGreaterThanAndActive(minSalary);
        System.out.println(">> Danh sách nhân viên lương > " + minSalary + " & active (size = " + highSalaryList.size() + "):");
        for (Employee e : highSalaryList) {
            System.out.println("   - " + e);
        }
    }
}