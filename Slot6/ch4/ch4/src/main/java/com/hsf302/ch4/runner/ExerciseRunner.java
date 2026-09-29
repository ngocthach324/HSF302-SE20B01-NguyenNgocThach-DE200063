package com.hsf302.ch4.runner;

import com.hsf302.ch4.pojo.Gender;
import com.hsf302.ch4.pojo.Student;
import com.hsf302.ch4.service.DepartmentService;
import com.hsf302.ch4.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;

@Component
@Order(2)
@RequiredArgsConstructor
public class ExerciseRunner implements CommandLineRunner {

    private final DepartmentService departmentService;
    private final StudentService studentService;

    @Override
    public void run(String... args) {
        partB();
        partC();
        partD();
        bonus();
        partE();
    }

    private void partB() {
        todo6();
        todo7();
    }

    private void todo6() {
        title("TODO 6: count / findById / existsById");
        System.out.println("Departments: " + departmentService.count());
        System.out.println("Students   : " + studentService.count());

        studentService.findById(1L).ifPresentOrElse(
                s -> System.out.println("findById(1)  -> " + s),
                () -> System.out.println("findById(1)  -> Not found"));

        System.out.println("findById(99) -> " + studentService.findById(99L)
                .map(Object::toString)
                .orElse("Not found"));

        System.out.println("existsById(4) department -> " + departmentService.existsById(4L));
    }

    private void todo7() {
        title("TODO 7a: findAll(Sort) - GPA giam dan");
        printList("Toan bo student (GPA desc)", studentService.findAllOrderByGpaDesc());

        title("TODO 7b: findAll(Pageable) - Trang 2 (index 1), size 3, sort fullName asc");
        Page<Student> page = studentService.findPage(1, 3, "fullName");
        printList("Trang 2", page.getContent());
        System.out.println("   totalElements = " + page.getTotalElements());
        System.out.println("   totalPages    = " + page.getTotalPages());
        System.out.println("   hasNext       = " + page.hasNext());
    }

    private void partC() {
        todo8();
        todo9();
        todo10();
        todo11();
    }

    private void todo8() {
        title("TODO 8: findBy / existsBy / countBy");

        studentService.findByStudentCode("AI002").ifPresentOrElse(
                s -> System.out.println("findByStudentCode(AI002) -> " + s),
                () -> System.out.println("findByStudentCode(AI002) -> Not found"));

        System.out.println("findByStudentCode(XX999) -> " + studentService.findByStudentCode("XX999")
                .map(Object::toString)
                .orElse("Not found"));

        System.out.println("existsByEmail(binh.tt@fpt.edu.vn) -> "
                + studentService.isEmailExisted("binh.tt@fpt.edu.vn"));

        System.out.println("countByActiveTrue -> " + studentService.countActive());
    }

    private void todo9() {
        title("TODO 9a: searchByName('nguyen')");
        printList("Student co ten chua 'nguyen'", studentService.searchByName("nguyen"));

        title("TODO 9b: findByEmailDomain('gmail.com')");
        printList("Student dung email gmail", studentService.findByEmailDomain("gmail.com"));

        title("TODO 9c: findWithoutEmail()");
        printList("Student chua co email", studentService.findWithoutEmail());
    }

    private void todo10() {
        title("TODO 10a: findByGpaRange(3.0, 3.6)");
        printList("Student co GPA [3.0, 3.6] (GPA desc)", studentService.findByGpaRange(3.0, 3.6));

        title("TODO 10b: findActiveByGender(MALE)");
        printList("Student nam dang active", studentService.findActiveByGender(Gender.MALE));

        title("TODO 10c: findBornAfter(2005-01-01)");
        printList("Student sinh sau 2005-01-01", studentService.findBornAfter(LocalDate.of(2005, 1, 1)));
    }

    private void todo11() {
        title("TODO 11a: findByDepartment('SE') - sort fullName asc");
        printList("Student thuoc khoa SE", studentService.findByDepartment("SE"));

        title("TODO 11b: countByDepartment('AI')");
        System.out.println("So student khoa AI = " + studentService.countByDepartment("AI"));

        title("TODO 11c: findTop3ByGpa()");
        printList("Top 3 student GPA cao nhat", studentService.findTop3ByGpa());

        title("TODO 11d: findDepartmentsWithoutStudents()");
        printList("Department chua co student", departmentService.findDepartmentsWithoutStudents());
    }

    private void partD() {
        todo12();
        todo13();
        todo14();
        todo15();
    }

    private void todo12() {
        title("TODO 12: JPQL + named parameter");
        printList("SE, GPA >= 3.0", studentService.findGoodStudents("SE", 3.0));
    }

    private void todo13() {
        title("TODO 13: JPQL LIKE");
        printList("keyword 'hoa'", studentService.searchByKeyword("hoa"));
        printList("keyword 'gmail'", studentService.searchByKeyword("gmail"));
    }

    private void todo14() {
        title("TODO 14: Thong ke department (LEFT JOIN + GROUP BY + DTO)");
        printList("Thong ke theo khoa", departmentService.getStatistics());
    }

    private void todo15() {
        title("TODO 15: Subquery - GPA above average");
        printList("GPA > AVG", studentService.findAboveAverageGpa());
    }

    private void bonus() {
    }

    private void partE() {
    }

    private void title(String t) {
        System.out.println("\n===== " + t + " =====");
    }

    private void printList(String label, Collection<?> list) {
        System.out.println("-- " + label + ":");
        list.forEach(o -> System.out.println("   " + o));
        System.out.println("   -> " + list.size() + " record(s)");
    }
}
