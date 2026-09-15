package fu.de200063;

import fu.de200063.util.JPAUtil;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println(">> TODO 1 OK: JPAUtil va Persistence Unit da khoi tao thanh cong!");
        em.close();
        JPAUtil.close();
    }
}