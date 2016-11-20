package fr.marseille.projetfinal.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.marseille.projetfinal.dao.ProfilDao;
import fr.marseille.projetfinal.dao.UserDao;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

/**
 * TODO Throws DAOException in UserService class
 */

@Service
public class UserService {
    
    @Autowired
    private UserDao   userDao;
    
    @Autowired
    private ProfilDao profilDao;

    public UserService() {
	super();
	// TODO Auto-generated constructor stub
    }
    
    @Transactional
    public User save(User user) {
        return userDao.save(user);

    }

    @Transactional
    public User save(User user, List<Profil> profils) {
        List<Profil> profilMaj = new ArrayList<>();
        for (Profil profil : profils) {
           profilMaj.add(profilDao.save(profil));
        }
        user.setProfiles(profilMaj);
        userDao.save(user);
        return user;
    }

    @Transactional
    public List<User> save(List<User> users) {
        return userDao.save(users);
    }

    @Transactional
    public User find(Integer id) {
        return userDao.find(id);
    }

    @Transactional
    public List<Profil> findAll(Integer id) {
        return userDao.findAll(id);
    }
    
    @Transactional	
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Transactional
    public User update(User user) {
        return userDao.update(user);
    }

    @Transactional
    public void delete(Integer id) {
        userDao.delete(id);
    }
}
