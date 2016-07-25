package fr.marseille.projetfinal.daoImlp;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.stereotype.Repository;
import fr.marseille.projetfinal.dao.UserDao;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

@Repository
public class UserImplDao implements UserDao {

    private static final String EMF = "GestionDroits";

    public UserImplDao() {
        super();
    }

    @Override
    public User save(User user) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(EMF);
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        entityManager.persist(user);

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
        return user;
    }

    public List<User> save(List<User> users) {

        for (User user : users) {
            this.save(user);
        }
        return users;
    }

    @Override
    // find by user
    public User find(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(EMF);
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        User user = new User();
        user = entityMng.find(User.class, id);

        entityMng.close();
        entityManagerFactory.close();

        return user;
    }

    public List<Profil> findAll(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(EMF);
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        entityMng.getTransaction().begin();
        List<Profil> profils = new ArrayList<>();
        User user = entityMng.find(User.class, id);

        profils = user.getProfiles();

        entityMng.close();
        entityManagerFactory.close();

        return profils;
    }

    @Override
    public List<User> findAll() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(EMF);
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        List<User> users = new ArrayList<>();
        users = (List<User>) entityMng.createQuery("from User").getResultList();
        entityMng.close();
        entityManagerFactory.close();
        return users;
    }

    @Override
    public User update(User user) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(EMF);
        EntityManager entityMng = entityManagerFactory.createEntityManager();

        // debut de la transaction
        entityMng.getTransaction().begin();
        User user1 = entityMng.find(User.class, user.getSerialNbr());

        if (null != user1) {
            user1.setFirstName(user.getFirstName());
            user1.setLastName(user.getLastName());
            user1.setComment(user.getComment());
            user1.setProfiles(user.getProfiles());
        }
        entityMng.merge(user1);
        entityMng.getTransaction().commit();
        entityMng.close();
        return user1;
    }

    @Override
    public void delete(Integer id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(EMF);
        EntityManager entityMng = entityManagerFactory.createEntityManager();
        // supprimer les éléments dea ltable des personnes
        entityMng.getTransaction().begin();
        User user = entityMng.find(User.class, id);

        if (null != user) {
            entityMng.remove(user);
        }
        entityMng.getTransaction().commit();
        entityMng.close();
        entityManagerFactory.close();
    }

}
