package fu.de200063.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(precision = 10, scale = 2)
    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate hireDate;

    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", nullable = false)
    @ToString.Exclude
    private Department department;

    @Transient
    private int yearsOfService;

    public Employee(String email, String fullName, Gender gender, BigDecimal salary, LocalDate hireDate) {
        this.email = email;
        this.fullName = fullName;
        this.gender = gender;
        this.salary = salary;
        this.hireDate = hireDate;
        this.active = true;
    }

    public int getYearsOfService() {
        if (hireDate == null) return 0;
        return Period.between(hireDate, LocalDate.now()).getYears();
    }
}
