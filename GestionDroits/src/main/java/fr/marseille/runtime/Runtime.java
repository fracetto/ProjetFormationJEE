package fr.marseille.runtime;

import java.util.List;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.UserService;

public class Runtime {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        UserService userServiceBean = (UserService) context.getBean("userService");

        // ProfilService profilServiceBean = (ProfilService) context.getBean("profilService");
        //
        // User user = new User();
        // user.setFirstName("Donia");
        // user.setLastName("Toumi");
        // user.setComment("J'adore mes enfants :) ");
        //
        // Profil profil = new Profil();
        // profil.setName("Admin");
        // profil.setDescription("Administrateur de l'application");
        // profil.setPermissions(null);
        // profilServiceBean.save(profil);
        //
        // List<Profil> profiles = new ArrayList<>();
        // profiles.add(profil);
        // user.setProfiles(profiles);
        //
        // userServiceBean.save(user);
        List<User> findAll = userServiceBean.findAll();
        System.out.println(findAll.toString());
        context.close();
    }

}
