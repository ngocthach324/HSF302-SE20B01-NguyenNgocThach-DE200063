package fu.de200063.dao;

import fu.de200063.pojo.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeDAO {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("hsf302FU");

    // ---------- CREATE (TODO 0.3) ----------
    public void save(Employee e) {
        // [Lifecycle] Trước dòng này: e đang ở trạng thái NEW / TRANSIENT
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e); // -> e chuyển sang MANAGED, sẽ được INSERT khi commit
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close(); // sau dòng này, e trở thành DETACHED
        }
    }

    // ---------- READ (TODO 0.4) ----------
    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            // [Lifecycle] Đối tượng trả về là MANAGED trong phạm vi em, sau khi return/close sẽ thành DETACHED
            return em.find(Employee.class, id); // trả về null nếu không tìm thấy
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ---------- READ CÓ ĐIỀU KIỆN (TODO 0.5) ----------
    public Employee findByEmail(String email) {
        EntityManager em = emf.createEntityManager();
        try {
            List<Employee> result = em.createQuery(
                            "SELECT e FROM Employee e WHERE e.email = :email", Employee.class)
                    .setParameter("email", email)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public List<Employee> findBySalaryGreaterThanAndActive(BigDecimal minSalary) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery(
                            "SELECT e FROM Employee e WHERE e.salary > :minSalary AND e.active = true",
                            Employee.class)
                    .setParameter("minSalary", minSalary)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // ---------- UPDATE (TODO 0.6) ----------
    public Employee update(Employee e) {
        // [Lifecycle] e truyền vào có thể đang ở trạng thái DETACHED
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee merged = em.merge(e); // merge() TRẢ VỀ một entity MANAGED mới trong session này
            em.getTransaction().commit();
            return merged; // Nên dùng đối tượng merged này tiếp theo
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close(); // sau dòng này, merged trở thành DETACHED
        }
    }
}
