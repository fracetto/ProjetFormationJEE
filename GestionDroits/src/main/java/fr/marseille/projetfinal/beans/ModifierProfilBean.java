package fr.marseille.projetfinal.beans;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
public class ModifierProfilBean implements Serializable {

    private DualListModel<String>          usersOfProfil = new DualListModel<String>();
    private List<String>                   selectedPermissions = null;
    private List<String>                   permissions = null;

    // Liste et currentProfil backup de la vue liste de profil
    private Profil                         currentProfil;
    private List<Droit>                    currentDroits;
    // fin
    
    @Autowired
    private UserService                    userServiceBean;
    
    @Autowired
    private ProfilService                  profilServiceBean;
    
    @Autowired
    private DroitService                   droitServiceBean;

    private final String                   NULL_STR      = "";
    List<String>                           usersSource;
    List<String>                           usersTarget;

    public ModifierProfilBean() {
        super();
        currentDroits = new ArrayList<>();
    }

    // @PostConstruct
    public void init() {
        if (null != currentProfil.getId()) {
            

             List<Droit> droitsCurrentProfil = currentProfil.getDroits();
             List<User> usersCurrentProfil = currentProfil.getUsers();

            selectedPermissions = new ArrayList<>();
            this.selectedPermissions = getCurrentPermissions();
            permissions = new ArrayList<>();
            permissions = getAllPermissions();
            usersSource = new ArrayList<String>();
            usersTarget = new ArrayList<String>();

            List<String> allCurrentUsers = new ArrayList<String>();
            allCurrentUsers = getCurrentUsers();

            List<String> allUsers = new ArrayList<String>();
            allUsers = getAllUsers();

            if (allCurrentUsers.size() > 0) {
                for (String userStr : allCurrentUsers) {
                    if (allUsers.contains(userStr)) {
                        allUsers.remove(userStr);
                    }
                }
            }

            usersOfProfil = new DualListModel<String>(allUsers, allCurrentUsers);
        }

    }

    public String redirect() {
        return "listeProfils";
    }

    public List<String> getAllUsers() {
        List<User> users = new ArrayList<User>();
        users = userServiceBean.findAll();
        String str = null;
        for (User user : users) {

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

    public List<String> getCurrentUsers() {
        List<User> currentusers = new ArrayList<User>();
        currentusers = profilServiceBean.findAll(currentProfil.getId());

        String str = null;
        for (User user : currentusers) {

            str = user.getSerialNbr() + " " + user.getFirstName() + " " + user.getLastName();
            usersTarget.add(str);
        }
        return usersTarget;

    }

    public List<String> getCurrentPermissions() {
        List<Droit> currentpermissions = new ArrayList<Droit>();
        //DEBUG 
        currentpermissions = profilServiceBean.findAllDroits(currentProfil.getId());

        String str = null;
        if (currentpermissions!=null && !currentpermissions.isEmpty()) {
            for (Droit droit : currentpermissions) {
                str = droit.getId() + " " + droit.getLabeel();
                selectedPermissions.add(str);
            }
        }
        return selectedPermissions;
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

    public DroitService getDroitServiceBean() {
        return droitServiceBean;
    }

    public void setDroitServiceBean(DroitService droitServiceBean) {
        this.droitServiceBean = droitServiceBean;
    }

    public DualListModel<String> getUsersOfProfil() {
        return usersOfProfil;
    }

    public Profil getCurrentProfil() {
        return currentProfil;
    }

    public void setCurrentProfil(Profil currentProfil) {
        if (currentProfil != null) {
            this.currentProfil = currentProfil;
            this.init();
        }
    }

    public void setUsersOfProfil(DualListModel<String> UsersOfProfil) {
        this.usersOfProfil = UsersOfProfil;
    }

    public UserService getUserServiceBean() {
        return userServiceBean;
    }

    public void setUserServiceBean(UserService userServiceBean) {
        this.userServiceBean = userServiceBean;
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

    public void update() throws DAOException, UIExceptBean {

        if (currentProfil != null && !NULL_STR.equals(currentProfil.getName())) {
            try {
                List<User> usersSelect = new ArrayList<User>();
                List<Droit> droitsSelected = new ArrayList<>();
                droitsSelected = droitsSelected();
                usersSelect = usersSelected();
                currentProfil.setUsers(usersSelect);
                currentProfil.setDroits(droitsSelected);
                profilServiceBean.update(currentProfil);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successfully Updated", "Added value to " + toString()));
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (currentProfil != null && NULL_STR.equals(currentProfil.getName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Name est vide", ""));
                throw new UIExceptBean("Le champ Firstname est vide");
            }

        }

    }

    public List<Droit> getCurrentDroits() {
        return currentDroits;
    }

    public void setCurrentDroits(List<Droit> currentDroits) {
        this.currentDroits = currentDroits;
    }

}
