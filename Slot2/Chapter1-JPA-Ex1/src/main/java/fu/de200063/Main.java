package fu.de200063;

import fu.de200063.dao.EmployeeDAO;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("========== TEST TODO 0.7: DELETE EMPLOYEE ==========");

        // 1. Tạo 1 nhân viên tạm để test xoá
        Employee tempEmp = new Employee("Le Van Can Xoa", "temp.delete@fpt.edu.vn",
                new BigDecimal("12000000.00"), Gender.OTHER, LocalDate.now());
        dao.save(tempEmp);
        System.out.println(">> Đã tạo nhân viên tạm: " + tempEmp);
        Long targetId = tempEmp.getId();

        // 2. Thực hiện xoá theo ID
        dao.delete(targetId);
        System.out.println(">> Đã gọi dao.delete(" + targetId + ")");

        // 3. Tìm lại để kiểm chứng nhân viên đã bị xoá thật sự khỏi CSDL
        Employee afterDelete = dao.findById(targetId);
        System.out.println(">> Tìm lại nhân viên sau khi xoá: " + afterDelete); // Kỳ vọng: null
        System.out.println(">> Kiểm tra xoá thành công (afterDelete == null): " + (afterDelete == null));
    }
}