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
import fr.marseille.projetfinal.dao.DroitDao;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

/**
 * TODO The DroitImplDao class must declare a constant String GestionDroits
 */

@Repository
public class DroitImplDao implements DroitDao {
    
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sf) {
	this.sessionFactory = sf;
    }
    public DroitImplDao() {
        super();
    }

    public Droit save(Droit droit) {
	Session session = sessionFactory.getCurrentSession();
	session.persist(droit);
        
        return droit;
    }

    public List<Droit> save(List<Droit> droits) {

        for (Droit droit : droits) {
            this.save(droit);
        }
        return droits;
    }

    public Droit find(Integer id) {
       
	Session session = sessionFactory.getCurrentSession();
	Droit droit = new Droit();
	droit = (Droit) session.get(Droit.class,id);


        return droit;
    }

    public List<Droit> findAll() {
	
	Session session = sessionFactory.getCurrentSession();
        List<Droit> droits = new ArrayList<>();
        droits = (List<Droit>) session.createQuery("from Droit").list();

        return droits;
    }

    public Droit update(Droit droit) {

	Session session = sessionFactory.getCurrentSession();
	session.merge(droit);
	session.flush();
	
        return droit;
    }

    public void delete(Integer id) {
	// supprimer les éléments dea ltable des personnes
	Session session = sessionFactory.getCurrentSession();
	Droit droit = (Droit) session.get(Droit.class, id);

	if (null != droit) {
	    session.delete(droit);
	}
    }

}
