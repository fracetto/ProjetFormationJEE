package beans;

import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.service.ProfilService;

@ManagedBean
@RequestScoped
public class ListeProfilsBean {

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

    public Profil getProfil() {
        // if (profil == null) {
        // return getCurrentProfil();
        // }
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
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

    public void onRowEdit(RowEditEvent event) {
        System.out.println("je suis dans evebnt la !");
        FacesMessage msg = new FacesMessage("Profil Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
                    "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

}
