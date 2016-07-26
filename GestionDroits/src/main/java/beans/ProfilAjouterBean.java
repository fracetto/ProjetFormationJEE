package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.annotation.SessionScope;
import fr.marseille.projetfinal.exception.DAOException;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.ProfilService;

@ManagedBean
@SessionScope
public class ProfilAjouterBean {

    Profil                                 profil;
    List<Droit>                            droits   = new ArrayList<>();
    List<User>                             users    = new ArrayList<>();
    private ClassPathXmlApplicationContext context;
    private ProfilService                  profilServiceBean;
    private final String                   NULL_STR = "";

    public ProfilAjouterBean() {
        super();
        this.context = new ClassPathXmlApplicationContext("application-context.xml");
        this.profilServiceBean = (ProfilService) context.getBean("profilService");
        this.profil = new Profil();
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public List<Droit> getDroits() {
        return droits;
    }

    public void setDroits(List<Droit> droits) {
        this.droits = droits;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void save() throws DAOException {
        if (!NULL_STR.equals(profil.getName())) {
            try {
                profilServiceBean.save(profil);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successfully Added", "Added value to " + toString()));
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (NULL_STR.equals(profil.getName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Name est vide", ""));
                // throw new UIExceptBean("Le champ Firstname est vide");
            }
            if (NULL_STR.equals(profil.getDescription())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Description est vide", ""));
                // throw new UIExceptBean("Le champ Lastname est vide");
            }
        }

    }

}
