package fr.marseille.projetfinal.dao;

import java.util.List;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;

public interface ProfilDao {

    public Profil save(Profil profil);

    public List<Profil> save(List<Profil> profils);

    public Profil find(Integer id);// cherche le profil complet par id du profil ou du user

    public List<Profil> findAll();

    public List<User> findAll(Integer id);

    public List<Droit> findAllDroits(Integer id);

    public void delete(Integer id);

    public Profil update(Profil profil);
}
