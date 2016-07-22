package fr.marseille.runtime;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.ProfilService;
import fr.marseille.projetfinal.service.UserService;

public class Runtime {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        UserService userServiceBean = (UserService) context.getBean("userService");

        ProfilService profilServiceBean = (ProfilService) context.getBean("profilService");

        User user = new User();
        user.setFirstName("Donia2");
        user.setLastName("Toumi2");
        user.setComment("J'adore mes enfants2 :) ");

        Profil profil = new Profil();
        profil.setName("Admin5");
        profil.setDescription("Administrateur de l'application5");
        profil.setPermissions(null);
        profilServiceBean.save(profil);

        List<User> users = new ArrayList<>();
        users.add(user);
        // profil.setUsers(users);

        List<Profil> profiles = new ArrayList<>();
        profiles.add(profil);
        user.setProfiles(profiles);
        userServiceBean.save(user);

        List<User> findAll = userServiceBean.findAll();
        System.out.println(findAll.toString());
        context.close();
    }

}
