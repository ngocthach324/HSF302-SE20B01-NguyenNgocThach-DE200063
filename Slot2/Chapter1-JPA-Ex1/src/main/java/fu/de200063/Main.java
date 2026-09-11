package fu.de200063;

import fu.de200063.dao.EmployeeDAO;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // ===== CREATE (TODO 0.3) =====
        // [Lifecycle] emp đang ở trạng thái NEW / TRANSIENT (mới "new", chưa liên quan DB)
        Employee emp = new Employee("Nguyen Van A", "a@fpt.edu.vn",
                new BigDecimal("15000000.00"), Gender.MALE, LocalDate.of(2022, 3, 1));

        dao.save(emp);
        // [Lifecycle] sau save(): emp là MANAGED trong transaction; sau khi save() return, emp trở thành DETACHED
        System.out.println(">> Đã tạo thành công: " + emp);
        System.out.println(">> ID sinh tự động bởi DB: " + emp.getId());
        System.out.println(">> Số năm làm việc (Transient): " + emp.getYearsOfService());
    }
}