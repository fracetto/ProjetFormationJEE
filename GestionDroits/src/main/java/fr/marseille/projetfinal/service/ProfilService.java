package fr.marseille.projetfinal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.marseille.projetfinal.dao.ProfilDao;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

@Service
public class ProfilService {
    @Autowired
    private ProfilDao profilDao;

    public ProfilService(ProfilDao profilDao) {
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

    public List<User> findAll(Integer id) {
        return profilDao.findAll(id);
    }

    public List<Droit> findAllDroits(Integer id) {
        return profilDao.findAllDroits(id);
    }

    public Profil update(Profil profil) {
        return profilDao.update(profil);
    }

    public void delete(Integer id) {
        profilDao.delete(id);
    }
}
