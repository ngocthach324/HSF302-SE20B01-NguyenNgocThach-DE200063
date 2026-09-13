package fu.de200063;

import fu.de200063.dao.EmployeeDAO;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // Dọn dẹp dữ liệu cũ nếu đã tồn tại từ lần test trước để chạy demo trơn tru
        Employee oldEmp = dao.findByEmail("a@fpt.edu.vn");
        if (oldEmp != null) {
            dao.delete(oldEmp.getId());
        }

        System.out.println("========== TODO 0.8: DEMO LUỒNG CRUD TUẦN TỰ ==========");

        // ===== 1. CREATE =====
        Employee emp = new Employee("Nguyen Van A", "a@fpt.edu.vn",
                new BigDecimal("15000000.00"), Gender.MALE, LocalDate.of(2022, 3, 1));
        dao.save(emp);
        System.out.println("1. [CREATE] Đã tạo nhân viên: " + emp);

        // ===== 2. READ =====
        Employee found = dao.findById(emp.getId());
        System.out.println("2. [READ] Đọc lại nhân viên theo ID (" + emp.getId() + "): " + found);

        // ===== 3. UPDATE =====
        found.setSalary(new BigDecimal("17000000.00"));
        Employee updated = dao.update(found);
        System.out.println("3. [UPDATE] Sau khi cập nhật lương: " + updated);

        // ===== 4. READ LẠI ĐỂ KIỂM CHỨNG =====
        Employee reChecked = dao.findById(emp.getId());
        System.out.println("4. [RE-CHECK] Đọc lại từ DB sau update: " + reChecked);

        // ===== 5. DELETE =====
        dao.delete(emp.getId());
        System.out.println("5. [DELETE] Đã xoá nhân viên ID: " + emp.getId());

        // ===== 6. READ LẠI ĐỂ KIỂM CHỨNG ĐÃ XOÁ =====
        Employee afterDelete = dao.findById(emp.getId());
        System.out.println("6. [RE-CHECK] Tìm lại sau khi xoá: " + afterDelete); // Kỳ vọng: null
    }
}