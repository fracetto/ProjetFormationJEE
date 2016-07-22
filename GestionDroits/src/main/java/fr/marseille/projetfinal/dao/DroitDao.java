package fr.marseille.projetfinal.dao;

import java.util.List;
import fr.marseille.projetfinal.model.Droit;

public interface DroitDao {

    public Droit save(Droit droit);

    public List<Droit> save(List<Droit> droits);

    public Droit find(Integer id);// cherche le profil complet par id du profil ou du user

    public List<Droit> findAll();

    public Droit update(Droit droit);

    public void delete(Integer id);

}
