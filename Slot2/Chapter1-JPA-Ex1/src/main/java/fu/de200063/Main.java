package fu.de200063;

import fu.de200063.dao.EmployeeDAO;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // ===== CREATE (TODO 0.3) =====
        Employee emp = new Employee("Tran Van B", "b@fpt.edu.vn",
                new BigDecimal("20000000.00"), Gender.FEMALE, LocalDate.of(2021, 5, 15));
        dao.save(emp);
        System.out.println(">> [TODO 0.3] Đã tạo thành công: " + emp);

        // ===== READ (TODO 0.4) =====
        // 1. Test findById
        Employee found = dao.findById(emp.getId());
        System.out.println(">> [TODO 0.4] Đọc theo ID (" + emp.getId() + "): " + found);

        // 2. Test findAll
        List<Employee> list = dao.findAll();
        System.out.println(">> [TODO 0.4] Danh sách tất cả nhân viên (size = " + list.size() + "):");
        for (Employee e : list) {
            System.out.println("   - " + e);
        }
    }
}