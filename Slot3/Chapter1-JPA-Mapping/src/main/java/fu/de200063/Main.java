package fu.de200063;

import fu.de200063.pojo.Department;
import fu.de200063.pojo.Employee;
import fu.de200063.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();

        System.out.println("========== TODO 2.8: TAI HIEN N+1 QUERY PROBLEM ==========");

        List<Department> departments = em.createQuery("SELECT d FROM Department d", Department.class)
                .getResultList();

        System.out.println(">> So luong phong ban lay duoc: " + departments.size());

        for (Department d : departments) {
            System.out.println(">> Phong ban: " + d.getName() + " - So nhan vien: " + d.getEmployees().size());
            for (Employee e : d.getEmployees()) {
                System.out.println("   - " + e.getFullName() + " (" + e.getEmail() + ")");
            }
        }

        em.close();
        JPAUtil.close();
    }
}