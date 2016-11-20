package fr.marseille.projetfinal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.marseille.projetfinal.dao.DroitDao;
import fr.marseille.projetfinal.model.Droit;

/**
 * TODO Throws DAOException in DroitService class
 */

@Service
public class DroitService {
    
    @Autowired
    private DroitDao droitDao;

    public DroitService() {
        super();
    }
    
    @Transactional
    public Droit save(Droit droit) {
        return droitDao.save(droit);
    }
    
    @Transactional
    public List<Droit> save(List<Droit> droits) {
        return droitDao.save(droits);
    }
    
    @Transactional
    public Droit find(Integer id) {
        return droitDao.find(id);
    }
    
    @Transactional
    public List<Droit> findAll() {
        return droitDao.findAll();
    }
    
    @Transactional
    public Droit update(Droit droit) {
        return droitDao.update(droit);
    }
    
    @Transactional
    public void delete(Integer id) {
        droitDao.delete(id);
    }
}
