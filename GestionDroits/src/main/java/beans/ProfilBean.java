package beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.ProfilService;

@ManagedBean
@SessionScoped
public class ProfilBean {

    private List<User>                     users = new ArrayList<>();
    private Integer                        profilId;
    private Profil                         profil;
    private Profil                         profilDefault;
    private ClassPathXmlApplicationContext context;
    private ProfilService                  profilServiceBean;

    public ProfilBean() {
        super();
        this.context = new ClassPathXmlApplicationContext("application-context.xml");
        this.profilServiceBean = (ProfilService) context.getBean("profilService");
        this.profilDefault = new Profil();
    }

    @PostConstruct
    public void init() {
        profilDefault.setName("USER_TEST");
        profilDefault.setDescription("Utilisateur lambda de l'application");
        profilDefault.setDroits(null);
    }

    public Profil save(Profil profil) {

        return profilServiceBean.save(profil);
    }

    public List<Profil> findAll() {

        return profilServiceBean.findAll();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getProfilId() {
        return profilId;
    }

    public void setProfilId(Integer profilId) {
        this.profilId = profilId;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Profil getProfilDefault() {
        return profilDefault;
    }

    public void setProfilDefault(Profil profilDefault) {
        this.profilDefault = profilDefault;
    }

}
