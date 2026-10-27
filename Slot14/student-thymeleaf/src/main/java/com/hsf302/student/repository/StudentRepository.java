package com.hsf302.student.repository;

import com.hsf302.student.entity.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // TODO 19.1: Tìm theo mã SV HOẶC họ tên chứa từ khóa, không phân biệt hoa thường, sắp xếp theo id tăng dần
    List<Student> findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrderByIdAsc(String code, String name);

    // TODO 19.2: Kiểm tra mã SV đã tồn tại (dùng khi THÊM MỚI)
    boolean existsByStudentCodeIgnoreCase(String studentCode);

    // TODO 19.3: Kiểm tra mã SV đã tồn tại ở sinh viên KHÁC (dùng khi CẬP NHẬT – bỏ qua chính nó)
    boolean existsByStudentCodeIgnoreCaseAndIdNot(String studentCode, Long id);

    // TODO MỞ RỘNG (G8): Thống kê theo ngành bằng JPQL (Số lượng và GPA trung bình)
    @Query("SELECT s.major, COUNT(s), AVG(s.gpa) FROM Student s GROUP BY s.major")
    List<Object[]> getStatsByMajor();

    // TODO MỞ RỘNG (G1, G2): Tìm kiếm kết hợp từ khóa, lọc theo ngành và sắp xếp linh hoạt
    @Query("SELECT s FROM Student s WHERE " +
           "(:kw IS NULL OR LOWER(s.studentCode) LIKE LOWER(CONCAT('%', :kw, '%')) OR LOWER(s.fullName) LIKE LOWER(CONCAT('%', :kw, '%'))) " +
           "AND (:major IS NULL OR :major = '' OR s.major = :major)")
    List<Student> searchAndFilter(@Param("kw") String keyword, @Param("major") String major, Sort sort);
}
