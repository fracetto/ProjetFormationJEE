package fr.marseille.projetfinal.dao;

import fr.marseille.projetfinal.daoImlp.DroitImplDao;
import fr.marseille.projetfinal.daoImlp.ProfilImplDao;
import fr.marseille.projetfinal.daoImlp.UserImplDao;

public class FactoryDao {
    private static ProfilDao profil;
    private static UserDao   user;
    private static DroitDao  droit;

    public static UserDao getUserDao() {
        if (user == null) {
            return user = new UserImplDao();
        } else {
            return user;
        }
    }

    public static ProfilDao getProfilDao() {
        if (profil == null) {
            return profil = new ProfilImplDao();
        } else {
            return profil;
        }

    }

    public static DroitDao getDroitDao() {
        if (droit == null) {
            return droit = new DroitImplDao();
        } else {
            return droit;
        }

    }

}
