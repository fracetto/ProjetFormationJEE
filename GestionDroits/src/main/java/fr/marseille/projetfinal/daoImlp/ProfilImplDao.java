package fr.marseille.projetfinal.daoImlp;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.stereotype.Repository;
import fr.marseille.projetfinal.dao.ProfilDao;
import fr.marseille.projetfinal.model.Profil;

@Repository
public class ProfilImplDao implements ProfilDao {

    public ProfilImplDao() {
        super();
    }

    @Override
    public Profil save(Profil profil) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(profil);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
        return profil;
    }

    public List<Profil> save(List<Profil> profils) {

        for (Profil profil : profils) {
            this.save(profil);
        }
        return profils;
    }

    @Override
    public Profil find(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        Profil profil = new Profil();
        profil = entityMng.find(Profil.class, id);

        entityMng.close();
        entityManagerFactory.close();

        return profil;
    }

    @Override
    public List<Profil> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        List<Profil> profils = new ArrayList<>();
        // Query createQuery = entityMng.createQuery("from Competence");
        for (Object o : entityMng.createQuery("select p from Profil p order by p.nom asc").getResultList()) {
            System.out.println(o);
            Profil profil = new Profil();
            profil = (Profil) o;
            profils.add(profil);

        }

        entityMng.close();
        entityManagerFactory.close();

        return profils;
    }

    @Override
    public Profil update(Profil profil) {
        return profil;
    }

    public void delete(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        // supprimer les éléments dea ltable des personnes
        entityMng.getTransaction().begin();
        Profil profil = entityMng.find(Profil.class, id);

        if (null != profil) {
            entityMng.remove(profil);
        }

        entityMng.close();
        entityManagerFactory.close();
    }

}
