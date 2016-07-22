package fr.marseille.projetfinal.daoImlp;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.stereotype.Repository;
import fr.marseille.projetfinal.dao.ProfilDao;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

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
        profils = entityMng.createQuery("from Profil").getResultList();

        entityMng.close();
        entityManagerFactory.close();

        return profils;
    }

    public List<User> findAll(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();

        List<User> users = new ArrayList<>();
        Profil profil = entityMng.find(Profil.class, id);

        users = profil.getUsers();
        for (User user : users) {
            System.out.println("Name USer " + user.getFirstName());
        }
        entityMng.close();
        entityManagerFactory.close();

        return users;
    }

    @Override
    public Profil update(Profil profil) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();

        // debut de la transaction
        entityMng.getTransaction().begin();
        Profil profil1 = entityMng.find(Profil.class, profil.getId());

        if (null != profil1) {
            profil1.setId(profil.getId());
            profil1.setName(profil.getName());
            profil1.setDescription(profil.getDescription());
            profil1.setPermissions(null);
            profil1.setUsers(null);
        }
        entityMng.merge(profil1);
        entityMng.getTransaction().commit();
        entityMng.close();
        return profil1;
    }

    public void delete(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("GestionDroits");
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        // supprimer les éléments dea ltable des personnes
        entityMng.getTransaction().begin();
        Profil profil = entityMng.find(Profil.class, id);
        System.out.println("j'apdate !!!! ");
        if (null != profil) {
            entityMng.remove(profil);
        }
        entityMng.getTransaction().commit();
        entityMng.close();
        entityManagerFactory.close();
    }

}
