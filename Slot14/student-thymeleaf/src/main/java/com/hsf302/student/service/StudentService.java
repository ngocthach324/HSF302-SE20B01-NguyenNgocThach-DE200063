package com.hsf302.student.service;

import com.hsf302.student.dto.MajorStatDTO;
import com.hsf302.student.entity.Student;
import com.hsf302.student.repository.StudentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> findAll() {
        return studentRepository.findAll(Sort.by("id"));
    }

    public List<Student> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return findAll();
        }
        String kw = keyword.trim();
        return studentRepository
                .findByStudentCodeContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrderByIdAsc(kw, kw);
    }

    // TODO MỞ RỘNG (G1, G2): Tìm kiếm kết hợp lọc ngành và sắp xếp động
    public List<Student> searchAndFilter(String keyword, String major, String sortField, String sortDir) {
        String kw = (keyword == null || keyword.isBlank()) ? null : keyword.trim();
        String mj = (major == null || major.isBlank()) ? null : major.trim();

        String field = "id";
        if (sortField != null) {
            switch (sortField) {
                case "studentCode":
                case "fullName":
                case "dateOfBirth":
                case "gpa":
                case "major":
                    field = sortField;
                    break;
                default:
                    field = "id";
            }
        }
        Sort.Direction direction = "desc".equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, field);

        return studentRepository.searchAndFilter(kw, mj, sort);
    }

    // TODO MỞ RỘNG (G6, G8): Thống kê số lượng sinh viên và GPA trung bình theo từng ngành bằng JPQL
    public List<MajorStatDTO> getMajorStats(Map<String, String> majorsMap) {
        List<Object[]> rawStats = studentRepository.getStatsByMajor();
        List<MajorStatDTO> list = new ArrayList<>();
        for (Object[] row : rawStats) {
            String code = (String) row[0];
            long count = ((Number) row[1]).longValue();
            double avg = row[2] != null ? ((Number) row[2]).doubleValue() : 0.0;
            String name = majorsMap != null ? majorsMap.getOrDefault(code, code) : code;
            list.add(new MajorStatDTO(code, name, count, avg));
        }
        return list;
    }

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    /** id == null → INSERT, id != null → UPDATE */
    @Transactional
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    /** Kiểm tra trùng mã SV, bỏ qua chính sinh viên đang sửa (excludeId) */
    public boolean isStudentCodeDuplicated(String code, Long excludeId) {
        if (code == null || code.isBlank()) return false;
        String c = code.trim();
        return excludeId == null
                ? studentRepository.existsByStudentCodeIgnoreCase(c)
                : studentRepository.existsByStudentCodeIgnoreCaseAndIdNot(c, excludeId);
    }

    public double averageGpa(List<Student> list) {
        return list.stream()
                .map(Student::getGpa)
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }
}
