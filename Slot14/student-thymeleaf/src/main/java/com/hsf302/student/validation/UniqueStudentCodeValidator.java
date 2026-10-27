package com.hsf302.student.validation;

import com.hsf302.student.entity.Student;
import com.hsf302.student.repository.StudentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueStudentCodeValidator implements ConstraintValidator<UniqueStudentCode, Student> {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public boolean isValid(Student student, ConstraintValidatorContext context) {
        if (student == null || student.getStudentCode() == null || student.getStudentCode().isBlank() || studentRepository == null) {
            return true;
        }
        boolean exists = student.getId() == null
                ? studentRepository.existsByStudentCodeIgnoreCase(student.getStudentCode().trim())
                : studentRepository.existsByStudentCodeIgnoreCaseAndIdNot(student.getStudentCode().trim(), student.getId());
        if (exists) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Mã sinh viên đã tồn tại")
                    .addPropertyNode("studentCode")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
