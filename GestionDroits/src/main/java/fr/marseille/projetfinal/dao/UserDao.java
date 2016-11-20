package fr.marseille.projetfinal.dao;

import java.util.List;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

public interface UserDao {
    
    public User save(User user);

    public List<User> save(List<User> users);

    public User find(Integer id);

    public List<User> findAll();

    public List<Profil> findAll(Integer id);

    public User update(User user);

    public void delete(Integer id);

}
