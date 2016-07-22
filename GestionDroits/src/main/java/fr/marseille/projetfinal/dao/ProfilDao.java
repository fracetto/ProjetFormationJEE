package fr.marseille.projetfinal.dao;

import java.util.List;
import fr.marseille.projetfinal.model.Profil;

public interface ProfilDao {

    public Profil save(Profil profil);

    public List<Profil> save(List<Profil> profils);

    public Profil find(Integer id);// cherche le profil complet par id du profil ou du user

    public List<Profil> findAll();

    public void delete(Integer id);

    public Profil update(Profil profil);
}
