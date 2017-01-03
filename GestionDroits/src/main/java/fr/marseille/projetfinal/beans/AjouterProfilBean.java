package fr.marseille.projetfinal.beans;

import java.util.ArrayList;
import javax.annotation.PostConstruct;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.marseille.projetfinal.exception.DAOException;
import fr.marseille.projetfinal.exception.UIExceptBean;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.DroitService;
import fr.marseille.projetfinal.service.ProfilService;
import fr.marseille.projetfinal.service.UserService;

@Component
@ManagedBean
@SessionScoped
public class AjouterProfilBean {

    private DualListModel<String>          usersOfProfil;

    private List<String>                   selectedPermissions = new ArrayList<String>();
    private List<String>                   permissions         = new ArrayList<String>();
    private List<String>                   users               = new ArrayList<String>();

    private Profil                         profil;

    @Autowired    
    private UserService                    userServiceBean;
    
    @Autowired
    private ProfilService                  profilServiceBean;
    
    @Autowired
    private DroitService                   droitServiceBean;

    List<String>                           usersSource         = new ArrayList<String>();
    List<String>                           usersTarget         = new ArrayList<String>();

    private final String                   NULL_STR            = "";

    public AjouterProfilBean() {
        super();
        this.profil = new Profil();
    }

    @PostConstruct
    // DEBUG append list actualize in default when Bean load after modification
    public String init() {
        if (users.isEmpty()) {
            users = getAllUsers();
            usersOfProfil = new DualListModel<String>(users, usersTarget);
        } else {
            users.clear();
            users = getAllUsers();
            usersOfProfil = new DualListModel<String>(users, usersTarget);
        }
        if (permissions.isEmpty()) {
            permissions = getAllPermissions();
        } else {
            permissions.clear();
            permissions = getAllPermissions();
        }
        return "ajouterProfil";
    }

    public List<String> getAllUsers() {
        List<User> usersObj = new ArrayList<User>();
        usersObj = userServiceBean.findAll();
        String str;
        for (User user : usersObj) {
            str = user.getSerialNbr() + " " + user.getFirstName() + " " + user.getLastName();
            usersSource.add(str);
        }
        return usersSource;
    }

    public List<String> getAllPermissions() {
        List<Droit> droits = new ArrayList<Droit>();
        droits = droitServiceBean.findAll();
        String str;
        for (Droit droit : droits) {
            str = droit.getId() + " " + droit.getLabeel();
            permissions.add(str);
        }
        return permissions;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public UserService getUserServiceBean() {
        return userServiceBean;
    }

    public void setUserServiceBean(UserService userServiceBean) {
        this.userServiceBean = userServiceBean;
    }

    public DroitService getDroitServiceBean() {
        return droitServiceBean;
    }

    public void setDroitServiceBean(DroitService droitServiceBean) {
        this.droitServiceBean = droitServiceBean;
    }

    public List<String> getSelectedPermissions() {
        return selectedPermissions;
    }

    public void setSelectedPermissions(List<String> selectedPermissions) {
        this.selectedPermissions = selectedPermissions;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public DualListModel<String> getUsersOfProfil() {
        return usersOfProfil;
    }

    public void setUsersOfProfil(DualListModel<String> usersOfProfil) {
        this.usersOfProfil = usersOfProfil;
    }

    public void onTransfer(TransferEvent event) {
        StringBuilder builder = new StringBuilder();
        for (Object item : event.getItems()) {
            builder.append(item).append("<br />");
        }
        FacesMessage msg = new FacesMessage();
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
        msg.setSummary("Items Transferred");
        msg.setDetail(builder.toString());
        String usersSelected = null;
        usersSelected = builder.toString();

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }

    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

    public List<Droit> droitsSelected() {
        List<Droit> droitsSelect = new ArrayList<Droit>();
        if (this.getSelectedPermissions() != null) {
            for (String item : this.getSelectedPermissions()) {
                Integer id = Integer.parseInt(item.split(" ")[0]);
                Droit droit = droitServiceBean.find(id);
                droitsSelect.add(droit);
            }
        }
        return droitsSelect;
    }

    public List<User> usersSelected() {
        List<User> usersSelect = new ArrayList<User>();
        if (this.getUsersOfProfil().getTarget() != null) {
            for (String item : this.getUsersOfProfil().getTarget()) {
                Integer id = Integer.parseInt(item.split(" ")[0]);
                User user = userServiceBean.find(id);
                usersSelect.add(user);
            }
        }
        return usersSelect;
    }

    public void save() throws DAOException, UIExceptBean {

        if (!NULL_STR.equals(profil.getName())) {
            try {
                List<User> usersSelect = new ArrayList<User>();
                List<Droit> droitsSelected = new ArrayList<Droit>();
                droitsSelected = droitsSelected();
                usersSelect = usersSelected();
                profil.setUsers(usersSelect);
                profil.setDroits(droitsSelected);
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
                throw new UIExceptBean("Le champ Firstname est vide");
            }

        }

    }

}
