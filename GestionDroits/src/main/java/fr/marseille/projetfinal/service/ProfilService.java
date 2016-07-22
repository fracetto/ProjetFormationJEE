package fr.marseille.projetfinal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.marseille.projetfinal.dao.DroitDao;
import fr.marseille.projetfinal.dao.ProfilDao;
import fr.marseille.projetfinal.model.Profil;

@Service
public class ProfilService {
    @Autowired
    private ProfilDao profilDao;

    public ProfilService(DroitDao droitDao) {
        super();
        this.profilDao = profilDao;
    }

    public ProfilService() {
        super();
    }

    public Profil save(Profil profil) {
        return profilDao.save(profil);
    }

    public List<Profil> save(List<Profil> profils) {
        return profilDao.save(profils);
    }

    public Profil find(Integer id) {
        return profilDao.find(id);
    }

    public List<Profil> findAll() {
        return profilDao.findAll();
    }

    public Profil update(Profil profil) {
        return profilDao.update(profil);
    }

    public void delete(Integer id) {
        profilDao.delete(id);
    }
}
