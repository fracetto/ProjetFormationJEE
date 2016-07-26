package beans;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.exception.DAOException;
import fr.marseille.projetfinal.model.Droit;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.DroitService;
import fr.marseille.projetfinal.service.ProfilService;
import fr.marseille.projetfinal.service.UserService;

@ManagedBean
@SessionScoped
public class PickListView {

    // @ManagedProperty("#{profilService}")

    private DualListModel<String> cities;

    private List<String> selectedPermissions;
    private List<String> permissions;

    private Profil profil;

    private ClassPathXmlApplicationContext context;
    private UserService                    userServiceBean;
    private ProfilService                  profilServiceBean;
    private DroitService                   droitServiceBean;

    private final String NULL_STR = "";

    List<String> citiesSource;
    List<String> citiesTarget;

    public PickListView() {
        super();
        this.profil = new Profil();
    }

    @PostConstruct
    public void init() {

        this.context = new ClassPathXmlApplicationContext("application-context.xml");
        this.userServiceBean = (UserService) context.getBean("userService");
        this.profilServiceBean = (ProfilService) context.getBean("profilService");
        this.droitServiceBean = (DroitService) context.getBean("droitService");

        List<String> citiesSource = new ArrayList<String>();
        List<String> citiesTarget = new ArrayList<String>();
        selectedPermissions = new ArrayList<String>();
        permissions = new ArrayList<String>();
        List<Droit> droits = new ArrayList<Droit>();
        droits = droitServiceBean.findAll();
        String str1;
        for (Droit droit : droits) {
            str1 = droit.getId() + " " + droit.getLabeel();
            permissions.add(str1);
        }

        List<User> users = new ArrayList<User>();
        users = userServiceBean.findAll();
        List<String> usersSource = new ArrayList<String>();

        String str;
        for (User user : users) {
            str = user.getSerialNbr() + " " + user.getFirstName() + " " + user.getLastName();
            citiesSource.add(str);
        }

        cities = new DualListModel<String>(citiesSource, citiesTarget);

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

    public DualListModel<String> getCities() {
        return cities;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public void setCities(DualListModel<String> cities) {
        this.cities = cities;
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
        System.out.println("Id DroitHello:");
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
        if (this.getCities().getTarget() != null) {
            for (String item : this.getCities().getTarget()) {
                Integer id = Integer.parseInt(item.split(" ")[0]);
                User user = userServiceBean.find(id);
                usersSelect.add(user);
            }
        }
        return usersSelect;
    }

    public void save() throws DAOException {

        if (!NULL_STR.equals(profil.getName())) {
            try {
                List<User> usersSelect = new ArrayList<User>();
                List<Droit> droitsSelected = droitsSelected();
                usersSelect = usersSelected();
                profil.setUsers(usersSelect);
                profil.setDroits(droitsSelected);
                profilServiceBean.save(profil);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successfully Added", "Added value to " + toString()));
            } catch (Exception e) {
                // throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (NULL_STR.equals(profil.getName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Name est vide", ""));
                // throw new UIExceptBean("Le champ Firstname est vide");
            }

        }

    }

}
