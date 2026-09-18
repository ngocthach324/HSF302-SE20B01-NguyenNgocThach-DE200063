package fu.de200063.pojo;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_code", unique = true, nullable = false)
    private String projectCode;

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(precision = 12, scale = 2)
    private BigDecimal budget;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToMany(mappedBy = "projects")
    @ToString.Exclude
    private Set<Employee> employees = new HashSet<>();

    public Project(String projectCode, String projectName, BigDecimal budget, LocalDate startDate, LocalDate endDate) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(projectCode, project.projectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(projectCode);
    }
}
