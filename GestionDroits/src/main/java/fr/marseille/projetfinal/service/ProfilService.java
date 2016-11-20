package fr.marseille.projetfinal.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.marseille.projetfinal.dao.ProfilDao;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

/**
 * TODO Throws DAOException in ProfilService class
 */

@Service
public class ProfilService {
    
    @Autowired
    private ProfilDao profilDao;

   
//Construct par defaut Spring problem
    public ProfilService() {
        super();
    }

    @Transactional
    public Profil save(Profil profil) {
        return profilDao.save(profil);
    }

    @Transactional
    public List<Profil> save(List<Profil> profils) {
        return profilDao.save(profils);
    }

    @Transactional
    public Profil find(Integer id) {
        return profilDao.find(id);
    }

    @Transactional
    public List<Profil> findAll() {
        return profilDao.findAll();
    }

    @Transactional
    public List<User> findAll(Integer id) {
        return profilDao.findAll(id);
    }

    @Transactional
    public List<Droit> findAllDroits(Integer id) {
        return profilDao.findAllDroits(id);
    }

    @Transactional
    public Profil update(Profil profil) {
        return profilDao.update(profil);
    }

    @Transactional
    public void delete(Integer id) {
        profilDao.delete(id);
    }
}
