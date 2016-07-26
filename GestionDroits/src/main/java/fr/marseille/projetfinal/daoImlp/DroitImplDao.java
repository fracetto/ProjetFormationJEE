package fr.marseille.projetfinal.daoImlp;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.stereotype.Repository;
import fr.marseille.projetfinal.dao.DroitDao;
import fr.marseille.projetfinal.model.Droit;

/**
 * TODO The DroitImplDao class must declare a constant String GestionDroits
 */

@Repository
public class DroitImplDao implements DroitDao {

    public DroitImplDao() {
        super();
    }

    public Droit save(Droit droit) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(droit);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
        return droit;
    }

    public List<Droit> save(List<Droit> droits) {

        for (Droit droit : droits) {
            this.save(droit);
        }
        return droits;
    }

    public Droit find(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        Droit droit = new Droit();
        droit = entityMng.find(Droit.class, id);

        entityMng.close();
        entityManagerFactory.close();

        return droit;
    }

    public List<Droit> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        List<Droit> droits = new ArrayList<>();

        droits = entityMng.createQuery("from Droit").getResultList();

        entityMng.close();
        entityManagerFactory.close();

        return droits;
    }

    public Droit update(Droit droit) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();

        // debut de la transaction
        entityMng.getTransaction().begin();
        Droit droit1 = entityMng.find(Droit.class, droit.getId());

        if (null != droit1) {
            droit1.setLabeel(droit.getLabeel());
            droit1.setProfiles(droit.getProfiles());
        }
        entityMng.merge(droit1);
        entityMng.getTransaction().commit();
        entityMng.close();
        return droit1;
    }

    public void delete(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        // supprimer les éléments dea ltable des personnes
        entityMng.getTransaction().begin();
        Droit droit = entityMng.find(Droit.class, id);

        if (null != droit) {
            entityMng.remove(droit);
        }
        entityMng.getTransaction().commit();
        entityMng.close();
        entityManagerFactory.close();
    }

}
