import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class TowerRepository extends Repository<Tower, String> {
    public TowerRepository(EntityManagerFactory emf) {
        super(emf, Tower.class);
    }

    public List<Tower> findAllTowersWithHeight(int height) {
        EntityManager em = getEmf().createEntityManager();
        List<Tower> towers = em.createQuery("select t from Tower t where t.height = :height", Tower.class)
                .setParameter("height", height)
                .getResultList();
        em.close();
        return towers;
    }
}
