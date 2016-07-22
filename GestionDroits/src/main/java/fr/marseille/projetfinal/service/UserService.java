package fr.marseille.projetfinal.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.marseille.projetfinal.dao.ProfilDao;
import fr.marseille.projetfinal.dao.UserDao;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

@Service
public class UserService {
    @Autowired
    private UserDao   userDao;
    @Autowired
    private ProfilDao profilDao;

    public UserService(UserDao userDao) {
        super();
        this.userDao = userDao;
    }

    public UserService() {
        super();
    }

    public User save(User user) {
        return userDao.save(user);

    }

    public User save(User user, List<Profil> profils) {
        List<Profil> profilMaj = new ArrayList<>();
        for (Profil profil : profils) {
            profilMaj.add(profilDao.save(profil));
        }
        user.setProfiles(profilMaj);
        userDao.save(user);
        return user;
    }

    public List<User> save(List<User> users) {
        return userDao.save(users);
    }

    public User find(Integer id) {
        return userDao.find(id);
    }

    public List<Profil> findAll(Integer id) {
        return userDao.findAll(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User update(User user) {
        return userDao.update(user);
    }

    public void delete(Integer id) {
        userDao.delete(id);
    }
}
