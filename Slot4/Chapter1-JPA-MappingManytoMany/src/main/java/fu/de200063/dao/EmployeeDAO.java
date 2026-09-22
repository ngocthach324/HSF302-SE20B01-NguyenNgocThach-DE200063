package fu.de200063.dao;

import fu.de200063.pojo.Employee;
import fu.de200063.pojo.Project;
import fu.de200063.util.JPAUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EmployeeDAO {

    public void save(Employee e) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(e);
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public List<Employee> findAll() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Employee e", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findAllWithProjects() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.projects", Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findActiveEmployeesInMultipleProjects() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT DISTINCT e FROM Employee e LEFT JOIN FETCH e.projects WHERE e.active = true AND SIZE(e.projects) > 1",
                    Employee.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Employee update(Employee e) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Employee merged = em.merge(e);
            em.getTransaction().commit();
            return merged;
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Employee e = em.find(Employee.class, id);
            if (e != null) {
                em.remove(e);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void assignEmployeeToProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Employee e = em.find(Employee.class, employeeId);
            Project p = em.find(Project.class, projectId);
            if (e != null && p != null) {
                e.assignToProject(p);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void unassignEmployeeFromProject(Long employeeId, Long projectId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Employee e = em.find(Employee.class, employeeId);
            Project p = em.find(Project.class, projectId);
            if (e != null && p != null) {
                e.unassignFromProject(p);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }

    public void deactivateEmployee(Long employeeId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Employee e = em.find(Employee.class, employeeId);
            if (e != null) {
                e.setActive(false);
            }
            em.getTransaction().commit();
        } catch (RuntimeException ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw ex;
        } finally {
            em.close();
        }
    }
}
