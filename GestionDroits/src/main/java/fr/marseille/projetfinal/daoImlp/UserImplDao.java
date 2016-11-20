package fr.marseille.projetfinal.daoImlp;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.marseille.projetfinal.dao.UserDao;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

@Repository
public class UserImplDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
	this.sessionFactory = sf;
    }

    public UserImplDao() {
	super();
    }

    @Override
    public User save(User user) {

	Session session = sessionFactory.getCurrentSession();
	session.persist(user);
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
	User user = new User();

	Session session = sessionFactory.getCurrentSession();
	user = (User) session.get(User.class,id);

	return user;
    }

    public List<Profil> findAll(Integer id) {

	List<Profil> profils = new ArrayList<>();
	Session session = sessionFactory.getCurrentSession();
	User user = (User) session.get(User.class, id);
	profils = user.getProfiles();

	return profils;
    }

    @Override
    public List<User> findAll() {

	List<User> users = new ArrayList<>();
	Session session = sessionFactory.getCurrentSession();
	users = (List<User>) session.createQuery("from User").list();

	return users;
    }

    @Override
    public User update(User user) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(user);
	session.flush();

	return user;
    }

    @Override
    public void delete(Integer id) {
	
	// supprimer les éléments dea ltable des personnes
//	Session session = sessionFactory.getCurrentSession();
//	User user = (User) session.get(User.class, id);
//
//	if (null != user) {
//	    session.delete(user);
//	}
	
	Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("delete from User where id=:id)");

        query.setParameter("id", id);
        query.executeUpdate();
	
	
    }

}
