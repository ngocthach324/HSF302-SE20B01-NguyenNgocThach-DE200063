package fu.de200063;

import fu.de200063.dao.EmployeeDAO;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("========== TEST TODO 0.6: UPDATE EMPLOYEE ==========");

        // 1. Đảm bảo có 1 nhân viên để test update (tìm nhân viên id=1, nếu chưa có thì tạo)
        Employee found = dao.findById(1L);
        if (found == null) {
            found = new Employee("Nguyen Van A", "a@fpt.edu.vn",
                    new BigDecimal("15000000.00"), Gender.MALE, LocalDate.of(2022, 3, 1));
            dao.save(found);
            System.out.println(">> Chưa có nhân viên, đã tạo mới: " + found);
        } else {
            System.out.println(">> Nhân viên ban đầu trước khi sửa: " + found);
        }

        // 2. Thay đổi mức lương
        BigDecimal newSalary = new BigDecimal("18500000.00");
        found.setSalary(newSalary);
        System.out.println(">> Đã sửa salary trên RAM thành: " + newSalary);

        // 3. Gọi hàm update() trong DAO
        Employee updated = dao.update(found);
        System.out.println(">> Kết quả trả về sau khi dao.update(): " + updated);

        // 4. Đọc lại từ CSDL bằng findById để kiểm chứng dữ liệu đã lưu xuống DB
        Employee reChecked = dao.findById(found.getId());
        System.out.println(">> Đọc lại từ DB sau update: " + reChecked);
        System.out.println(">> Kiểm tra lương trong DB có khớp: " + (reChecked.getSalary().compareTo(newSalary) == 0));
    }
}