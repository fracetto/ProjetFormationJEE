package fr.marseille.projetfinal.beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.DroitService;
import fr.marseille.projetfinal.service.ProfilService;
import fr.marseille.projetfinal.service.UserService;

@Component
@ManagedBean
@SessionScoped
public class ListeProfilsBean {
    private List<String>                   permissions;
    private List<String>                   users;
    private List<Droit>                    droits;
    Integer                                profilId;
    Profil                                 profil;
    
    @Autowired
    private ProfilService                  profilServiceBean;
    
//    @Autowired
//    private UserService                    userServiceBean;
    
    @Autowired    
    private DroitService                   droitServiceBean;

    private static Logger log = Logger
			.getLogger(ListeProfilsBean.class);
    
    public ListeProfilsBean() {
        super();
        this.profil = new Profil();
    }

//   @PostConstruct
    public void init() {
        permissions = new ArrayList<String>();
        droits = droitServiceBean.findAll();
        String str;
        for (Droit droit : droits) {
            str = droit.getId() + " " + droit.getLabeel();
            permissions.add(str);
        }
//         return "listeProfils";
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

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getUsers() {

        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public List<Profil> findAll() {
        return profilServiceBean.findAll();
    }

    public void delete(Profil profil) {
        profilServiceBean.delete(profil.getId());
    }

    public Profil update(Profil profil) {

        return profilServiceBean.update(profil);
    }

    public String findImage(Profil profil) {
        if ((profil.getName()).equalsIgnoreCase("Administrateur") || (profil.getName()).equalsIgnoreCase("Admin")) {
            return "Administrateur.png";
        }
        if ((profil.getName()).equalsIgnoreCase("Utilisateur") || (profil.getName()).equalsIgnoreCase("user")) {
        	log.debug("findImage User " + profil.getName());
        	return "user.png";
        }
        if ((profil.getName()).equalsIgnoreCase("Super Utilisateur")
                || (profil.getName()).equalsIgnoreCase("Super user")) {
        	log.debug("findImage super user : " + profil.getName());
            return "superuser.jpg";
        }
        
        return "user.png";
    }

    public List<String> findAllUser(Profil profil) {
        users = new ArrayList<>();
        List<User> utilisateurs = new ArrayList<User>();
        utilisateurs = profilServiceBean.findAll(profil.getId());
        String str = "";
        for (User user : utilisateurs) {
            str = user.getSerialNbr() + " " + user.getFirstName() + " " + user.getLastName();
            users.add(str);
        }
        log.debug("findAllUser : " + profil.getId());
        return users;

    }

    public List<String> findAllDroits(Profil profil) {
        permissions = new ArrayList<>();
        List<Droit> droits = new ArrayList<Droit>();
        droits = profilServiceBean.findAllDroits(profil.getId());
        String str;
        if (droits!=null && droits.size()!=0)
        for (Droit droit : droits) {
            str = droit.getId() + " " + droit.getLabeel();
            permissions.add(str);
        }
        log.debug("findAllDroits : " + profil.getId());
        return permissions;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO + "Edit Changed " + profilId);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        log.debug("onRowEdit : " + profilId);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        log.debug("onRowCancel : " + profilId);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        profil.setId(profilId);
        update(profil);
        Profil newValue = (Profil) event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
                    "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);

        }
    }

}
