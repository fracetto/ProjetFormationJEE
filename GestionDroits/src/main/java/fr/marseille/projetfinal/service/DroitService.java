package fr.marseille.projetfinal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.marseille.projetfinal.dao.DroitDao;
import fr.marseille.projetfinal.model.Droit;

@Service
public class DroitService {
    @Autowired
    private DroitDao droitDao;

    public DroitService(DroitDao droitDao) {
        super();
        this.droitDao = droitDao;
    }

    public DroitService() {
        super();
    }

    public Droit save(Droit droit) {
        return droitDao.save(droit);
    }

    public List<Droit> save(List<Droit> droits) {
        return droitDao.save(droits);
    }

    public Droit find(Integer id) {
        return droitDao.find(id);
    }

    public List<Droit> findAll() {
        return droitDao.findAll();
    }

    public Droit update(Droit droit) {
        return droitDao.update(droit);
    }

    public void delete(Integer id) {
        droitDao.delete(id);
    }
}
