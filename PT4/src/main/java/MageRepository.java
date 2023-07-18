import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class MageRepository extends Repository<Mage, String>{
    public MageRepository(EntityManagerFactory emf) {
        super(emf, Mage.class);
    }

    public List<Mage> findAllMagesWithLevelHigherThan(int level) {
        EntityManager em = getEmf().createEntityManager();
        List<Mage> mages = em.createQuery("select m from Mage m where m.level > :level", Mage.class)
                .setParameter("level", level)
                .getResultList();
        em.close();
        return mages;
    }

    public List<Mage> findAllMagesFromTower(Tower tower) {
        EntityManager em = getEmf().createEntityManager();
        List<Mage> mages = em.createQuery("select m from Mage m where m.tower = :tower", Mage.class)
                .setParameter("tower", tower)
                .getResultList();
        em.close();
        return mages;
    }

    public List<Mage> sortedMagesWithLetterA() {
        EntityManager em = getEmf().createEntityManager();
        List<Mage> mages = em.createQuery("select m from Mage m where m.name like lower('%a%') order by m.name", Mage.class)
                .getResultList();
        em.close();
        return mages;
    }
}
