package com.hsf302.ch4.service;

import com.hsf302.ch4.dto.StudentSummary;
import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.repository.StudentRepository;
import com.hsf302.ch4.specification.StudentSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public long count() {
        return studentRepository.count();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public List<Student> findAllOrderByGpaDesc() {
        return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "gpa"));
    }

    @Override
    public Page<Student> findPage(int pageIndex, int size, String sortField) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException("pageIndex phai >= 0 va size phai > 0");
        }
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by(sortField).ascending());
        return studentRepository.findAll(pageable);
    }

    @Override
    public Optional<Student> findByStudentCode(String code) {
        return studentRepository.findByStudentCode(code);
    }

    @Override
    public boolean isEmailExisted(String email) {
        return studentRepository.existsByEmail(email);
    }

    @Override
    public long countActive() {
        return studentRepository.countByActiveTrue();
    }

    @Override
    public List<Student> searchByName(String kw) {
        if (kw == null || kw.trim().isEmpty()) {
            return List.of();
        }
        return studentRepository.findByFullNameContainingIgnoreCase(kw.trim());
    }

    @Override
    public List<Student> findByEmailDomain(String domain) {
        if (domain == null || domain.trim().isEmpty()) {
            return List.of();
        }
        String suffix = domain.trim();
        if (!suffix.startsWith("@")) {
            suffix = "@" + suffix;
        }
        return studentRepository.findByEmailEndingWith(suffix);
    }

    @Override
    public List<Student> findWithoutEmail() {
        return studentRepository.findByEmailIsNull();
    }

    @Override
    public List<Student> findByGpaRange(double min, double max) {
        if (min > max) {
            throw new IllegalArgumentException("min phai <= max");
        }
        return studentRepository.findByGpaBetweenOrderByGpaDesc(min, max);
    }

    @Override
    public List<Student> findActiveByGender(Gender g) {
        return studentRepository.findByGenderAndActiveTrue(g);
    }

    @Override
    public List<Student> findBornAfter(LocalDate d) {
        return studentRepository.findByDobAfter(d);
    }

    @Override
    public List<Student> findByDepartment(String deptCode) {
        if (deptCode == null || deptCode.trim().isEmpty()) {
            return List.of();
        }
        return studentRepository.findByDepartment_CodeOrderByFullNameAsc(deptCode.trim());
    }

    @Override
    public long countByDepartment(String deptCode) {
        if (deptCode == null || deptCode.trim().isEmpty()) {
            return 0;
        }
        return studentRepository.countByDepartment_Code(deptCode.trim());
    }

    @Override
    public List<Student> findTop3ByGpa() {
        return studentRepository.findTop3ByOrderByGpaDesc();
    }

    @Override
    public List<Student> findGoodStudents(String deptCode, double minGpa) {
        if (deptCode == null || deptCode.trim().isEmpty()) {
            return List.of();
        }
        return studentRepository.findGoodStudentsInDepartment(deptCode.trim(), minGpa);
    }

    @Override
    public List<Student> searchByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return List.of();
        }
        return studentRepository.searchByKeyword(keyword.trim());
    }

    @Override
    public List<Student> findAboveAverageGpa() {
        return studentRepository.findAboveAverageGpa();
    }

    @Override
    public List<Student> findTopNInDepartment(String deptCode, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n phai > 0");
        }
        if (deptCode == null || deptCode.trim().isEmpty()) {
            return List.of();
        }
        return studentRepository.findTopNByDepartmentNative(deptCode.trim(), n);
    }

    @Override
    public List<StudentSummary> getActiveSummaries() {
        return studentRepository.findActiveSummaries();
    }

    @Override
    public Page<Student> findActiveByDepartment(String deptCode, int pageIndex, int size) {
        if (pageIndex < 0 || size <= 0) {
            throw new IllegalArgumentException("pageIndex phai >= 0 va size phai > 0");
        }
        if (deptCode == null || deptCode.trim().isEmpty()) {
            return Page.empty();
        }
        Pageable pageable = PageRequest.of(pageIndex, size, Sort.by("gpa").descending());
        return studentRepository.findActiveByDepartment(deptCode.trim(), pageable);
    }

    @Override
    @Transactional
    public Student updateGpa(String studentCode, double newGpa) {
        if (newGpa < 0 || newGpa > 4) {
            throw new IllegalArgumentException("GPA phai trong khoang [0, 4]");
        }
        Student s = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new IllegalArgumentException("Student not found: " + studentCode));
        s.setGpa(newGpa);
        return s;
    }

    @Override
    @Transactional
    public int deactivateLowGpa(double threshold) {
        return studentRepository.deactivateLowGpa(threshold);
    }

    @Override
    @Transactional
    public long deleteInactiveStudents() {
        return studentRepository.deleteByActiveFalse();
    }

    @Override
    public List<Student> search(String kw, String deptCode, Double minGpa, Boolean active) {
        Specification<Student> spec = Specification.where(StudentSpecs.nameContains(kw))
                .and(StudentSpecs.inDepartment(deptCode))
                .and(StudentSpecs.gpaAtLeast(minGpa))
                .and(StudentSpecs.isActive(active));
        return studentRepository.findAll(spec, Sort.by("fullName"));
    }
}
