package fu.de200063;

import fu.de200063.dao.EmployeeDAO;
import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        // 1. Dọn dẹp dữ liệu cũ nếu đã tồn tại từ lần test trước
        Employee oldEmp = dao.findByEmail("a@fpt.edu.vn");
        if (oldEmp != null) {
            dao.delete(oldEmp.getId());
        }
        Employee oldDup = dao.findByEmail("trung@fpt.edu.vn");
        if (oldDup != null) {
            dao.delete(oldDup.getId());
        }

        System.out.println("========== TODO 0.8 & TODO 0.10: CRUD VÀ ENTITY LIFECYCLE ==========");

        // ===== 1. CREATE =====
        // [Lifecycle] emp đang ở trạng thái NEW / TRANSIENT (mới tạo bằng từ khóa new, chưa gắn với DB, id = null)
        Employee emp = new Employee("Nguyen Van A", "a@fpt.edu.vn",
                new BigDecimal("15000000.00"), Gender.MALE, LocalDate.of(2022, 3, 1));

        dao.save(emp);
        // [Lifecycle] Trong lúc save(): em.persist(emp) đưa emp thành MANAGED (được insert khi commit).
        // Sau khi save() kết thúc (em.close()): emp chuyển sang trạng thái DETACHED (tách rời khỏi session).
        System.out.println("1. [CREATE] Đã tạo nhân viên: " + emp);

        // ===== 2. READ =====
        Employee found = dao.findById(emp.getId());
        // [Lifecycle] Trong findById(): đối tượng đọc ra là MANAGED trong phạm vi EntityManager đó.
        // Ngay khi return và EntityManager đóng (em.close()): found trở thành DETACHED.
        System.out.println("2. [READ] Đọc lại nhân viên theo ID (" + emp.getId() + "): " + found);

        // ===== 3. UPDATE =====
        // [Lifecycle] found đang DETACHED, sửa field lúc này chỉ đổi trên RAM, KHÔNG tự động đồng bộ xuống DB.
        found.setSalary(new BigDecimal("17000000.00"));

        Employee updated = dao.update(found);
        // [Lifecycle] Trong update(): em.merge(found) sao chép dữ liệu từ found (DETACHED) sang một đối tượng
        // MANAGED mới trong transaction hiện tại và thực thi UPDATE khi commit. Sau khi method đóng em: updated thành DETACHED.
        System.out.println("3. [UPDATE] Sau khi cập nhật lương: " + updated);

        // ===== 4. READ LẠI ĐỂ KIỂM CHỨNG =====
        Employee reChecked = dao.findById(emp.getId());
        System.out.println("4. [RE-CHECK] Đọc lại từ DB sau update: " + reChecked);

        // ===== 5. DELETE =====
        dao.delete(emp.getId());
        // [Lifecycle] Trong delete(): em.find() load entity thành MANAGED -> em.remove() chuyển entity sang
        // trạng thái REMOVED -> khi commit() thực thi lệnh DELETE khỏi CSDL.
        System.out.println("5. [DELETE] Đã xoá nhân viên ID: " + emp.getId());

        // ===== 6. READ LẠI ĐỂ KIỂM CHỨNG ĐÃ XOÁ =====
        Employee afterDelete = dao.findById(emp.getId());
        System.out.println("6. [RE-CHECK] Tìm lại sau khi xoá: " + afterDelete); // Kỳ vọng: null

        // ======================================================================
        // ===== TODO 0.9: KIỂM CHỨNG RÀNG BUỘC UNIQUE TRÊN CỘT EMAIL =====
        // ======================================================================
        System.out.println("\n========== TODO 0.9: KIỂM CHỨNG RÀNG BUỘC UNIQUE EMAIL ==========");

        // [Lifecycle] dup1 & dup2 ban đầu đều ở trạng thái NEW / TRANSIENT
        Employee dup1 = new Employee("User 1", "trung@fpt.edu.vn",
                new BigDecimal("10000000.00"), Gender.FEMALE, LocalDate.now());
        Employee dup2 = new Employee("User 2", "trung@fpt.edu.vn", // CỐ TÌNH TRÙNG EMAIL VỚI DUP1
                new BigDecimal("11000000.00"), Gender.MALE, LocalDate.now());

        // Thêm nhân viên thứ 1 (thành công -> dup1 trở thành DETACHED sau khi save)
        dao.save(dup1);
        System.out.println(">> Đã thêm nhân viên 1: " + dup1);

        // Thêm nhân viên thứ 2 có cùng email (kỳ vọng: vi phạm UNIQUE -> ném ConstraintViolationException)
        try {
            System.out.println(">> Đang thử thêm nhân viên 2 có cùng email: " + dup2.getEmail());
            dao.save(dup2);
            System.out.println(">> LỖI: Không thấy exception như kỳ vọng!");
        } catch (RuntimeException ex) {
            System.out.println(">> ĐÃ BẮT ĐƯỢC LỖI TRÙNG EMAIL NHƯ KỲ VỌNG: "
                    + ex.getClass().getSimpleName() + " - " + ex.getMessage());
        }
    }
}