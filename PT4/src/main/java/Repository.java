import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public abstract class Repository<E, K> {

    private final EntityManagerFactory emf;

    private final Class<E> clas;

    public Repository(EntityManagerFactory emf, Class<E> clas) {
        this.emf = emf;
        this.clas = clas;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public List<E> findAll() {
        EntityManager em = emf.createEntityManager();
        List<E> list = em.createQuery("select e from " + clas.getSimpleName() + " e", clas).getResultList();
        em.close();
        return list;
    }

    public E find(K id) {
        EntityManager em = emf.createEntityManager();
        E entity = em.find(clas, id);
        em.close();
        return entity;
    }

    public void delete(E entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.merge(entity));
        transaction.commit();
        em.close();
    }

    public void add(E entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(entity);
        transaction.commit();
        em.close();
    }

    public void update(E entity) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(entity);
        transaction.commit();
        em.close();
    }
}
