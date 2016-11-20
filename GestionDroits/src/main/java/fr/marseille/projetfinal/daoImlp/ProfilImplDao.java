package fr.marseille.projetfinal.daoImlp;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.marseille.projetfinal.dao.ProfilDao;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

@Repository
public class ProfilImplDao implements ProfilDao {
    
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
	this.sessionFactory = sf;
    }

    public ProfilImplDao() {
        super();
    }

    @Override
    public Profil save(Profil profil) {

	Session session = sessionFactory.getCurrentSession();
	session.persist(profil);

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
        Profil profil = new Profil();
        Session session = sessionFactory.getCurrentSession();
        profil = (Profil) session.get(Profil.class, id);
        if (profil.getUsers() != null) {
            System.out.println("Présence de " + profil.getUsers().size() + " utilisateur(s) : ");
        }
        if (profil.getDroits() != null) {
            System.out.println("Présence de " + profil.getDroits().size() + " droits(s) : ");
        }

        return profil;
    }

    @Override
    public List<Profil> findAll() {
        List<Profil> profils = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        profils = session.createQuery("from Profil").list();

        return profils;
    }

    @Override
    public List<User> findAll(Integer id) {
	Profil profil = new Profil();
        Session session = sessionFactory.getCurrentSession();
        profil = (Profil) session.get(Profil.class, id);

        if (profil.getUsers() != null) {
            System.out.println("Présence de " + profil.getUsers().size() + " utilisateur(s) : ");
        }
        return profil.getUsers();
    }

    @Override
    public List<Droit> findAllDroits(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        List<Droit> droits = new ArrayList<>();
        Profil profil =(Profil) session.get(Profil.class, id);

        if (profil.getDroits() != null) {
            System.out.println("Présence de " + profil.getDroits().size() + " droit(s) : ");
        }

        return profil.getDroits();
    }

    @Override
    public Profil update(Profil profil) {

        Session session = sessionFactory.getCurrentSession();
        session.merge(profil);
        session.flush();
        return profil;
    }

    public void delete(Integer id) {

	Session session = sessionFactory.getCurrentSession();
	Profil profil = (Profil) session.get(Profil.class, id);

        if (null != profil) {
            session.delete(profil);
        }
    }

}
