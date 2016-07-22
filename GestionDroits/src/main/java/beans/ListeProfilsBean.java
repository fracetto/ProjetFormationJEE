package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.ProfilService;

@ManagedBean
@RequestScoped
public class ListeProfilsBean {
    List<User>                             users = new ArrayList<>();
    Integer                                profilId;
    Profil                                 profil;
    private ClassPathXmlApplicationContext context;
    private ProfilService                  profilServiceBean;

    public ListeProfilsBean() {
        super();
        this.context = new ClassPathXmlApplicationContext("application-context.xml");
        this.profilServiceBean = (ProfilService) context.getBean("profilService");
        Profil profil = new Profil();
    }

    // public Profil getCurrentProfil() {
    // Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    // String str = params.get("param");
    // if (!str.isEmpty()) {
    // profil = profilServiceBean.find(Integer.parseInt(str));
    // return profil;
    // }
    // return null;
    // }

    public Integer getProfilId() {
        return profilId;
    }

    public void setProfilId(Integer profilId) {
        this.profilId = profilId;
    }

    public Profil getProfil() {
        // if (profil == null) {
        // return getCurrentProfil();
        // }
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Profil> findAll() {
        return profilServiceBean.findAll();
    }

    public void delete(Profil profil) {
        System.out.println("je suis la !");
        profilServiceBean.delete(profil.getId());
    }

    public Profil update(Profil profil) {

        System.out.println("je suis dans update la !");
        return profilServiceBean.update(profil);
    }

    public List<User> findAllUser(Profil profil) {
        return profilServiceBean.findAll(profil.getId());

    }

    public List<Droit> findAllDroit(Profil profil) {
        return null;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO + "Edit Changed " + profilId);
        // FacesMessage msg = new FacesMessage("Profil Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {

        Object oldValue = event.getOldValue();
        profil.setId(profilId);
        update(profil);
        Profil newValue = (Profil) event.getNewValue();
        System.out.println();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
                    "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

}
