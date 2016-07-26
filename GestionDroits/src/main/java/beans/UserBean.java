package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.exception.DAOException;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.UserService;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    /**
     * Author Franck
     */

    private static final Logger            LOG              = Logger.getLogger(UserBean.class);
    private static final String            MSG_EN_SUCCESS   = "Successfully Updated";
    private static final String            MSG_FR_SUCCESS   = "Enregistrement reussi";
    private static final String            SUCCESS          = "succes";
    private static final String            MSG_EN_F_NAME    = "Error : First Name is empty !";
    private static final String            MSG_FR_F_NAME    = "Erreur : Prenom est vide ! ";
    private static final String            F_NAME           = "f_name";
    private static final String            MSG_EN_L_NAME    = "Error : Last Name is empty !";
    private static final String            MSG_FR_L_NAME    = "Erreur : Nom est vide ! ";
    private static final String            L_NAME           = "l_name";
    private static final String            MSG_EN_EXIST     = "Error : Existing User !";
    private static final String            MSG_FR_EXIST     = "Erreur : Utilisateur existant !";
    private static final String            EXIST            = "exist";
    private static final String            MSG_EN_ERROR     = "Error : DB !";
    private static final String            MSG_FR_ERROR     = "Erreur : BD !";
    private static final String            ERROR            = "error";
    private static final String            MSG_UNKNOWN      = "Error unknown !";
    private static final long              serialVersionUID = 1L;
    private User                           user;
    private ClassPathXmlApplicationContext context;
    private UserService                    userServiceBean;
    private static final String            NULLSTR          = "";
    private User                           currentUser;
    private List<Profil>                   profiles;

    @ManagedProperty(value = "#{localeBean}")
    private LocaleBean                     localebean;

    @ManagedProperty(value = "#{profilBean}")
    private ProfilBean                     profilBean;

    public UserBean() {
        super();
        this.context = new ClassPathXmlApplicationContext("application-context.xml");
        this.userServiceBean = (UserService) context.getBean("userService");
        this.user = new User();
    }

    /**
     * method to compare user and currentUser
     * 
     * @return true if user and currentUser has same properties
     */
    // TODO validate unicity in DB
    private boolean comparedProperties() {
        boolean isEqual = false;
        if (user.getFirstName().equals(currentUser.getFirstName())
                & user.getLastName().equals(currentUser.getLastName())
                & user.getComment().equals(currentUser.getComment())) {
            isEqual = true;
        }
        return isEqual;
    }

    private FacesMessage getFrMessage(String messageLevel) {
        FacesMessage message = null;
        switch (messageLevel) {
            case SUCCESS:
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_SUCCESS, null);
                break;
            case F_NAME:
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, MSG_FR_F_NAME, null);
                break;
            case L_NAME:
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, MSG_FR_L_NAME, null);
                break;
            case EXIST:
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, MSG_FR_EXIST, null);
                break;
            case ERROR:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_FR_ERROR, null);
                break;
            default:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_UNKNOWN, null);
                break;
        }

        return message;
    }

    private FacesMessage getEnMessage(String messageLevel) {
        FacesMessage message = null;
        switch (messageLevel) {
            case SUCCESS:
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_SUCCESS, null);
                break;
            case F_NAME:
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, MSG_EN_F_NAME, null);
                break;
            case L_NAME:
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, MSG_EN_L_NAME, null);
                break;
            case EXIST:
                message = new FacesMessage(FacesMessage.SEVERITY_WARN, MSG_EN_EXIST, null);
                break;
            case ERROR:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_EN_ERROR, null);
                break;
            default:
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSG_UNKNOWN, null);
                break;
        }
        return message;
    }

    private FacesMessage localizeMessage(String messageLevel) {
        FacesMessage message = null;
        if (localebean.getLanguage() == "fr") {
            message = getFrMessage(messageLevel);
        } else if (localebean.getLanguage() == "en") {
            message = getEnMessage(messageLevel);
        }
        return message;
    }

    /**
     * method to create a new user after validate him like new one
     * 
     * @throws DAOException
     */
    public void saveUser() throws DAOException {

        if ((currentUser == null) || (currentUser != null && !comparedProperties())) {
            if (!NULLSTR.equals(user.getFirstName()) & !NULLSTR.equals(user.getLastName())) {
                try {
                    // Save user to the database
                    user = userServiceBean.save(user);
                    // method to save default Profile to User by User List : users
                    // List<User> users = this.setDefaultProfile(user);
                    // currentUser = users.remove(0);
                    // message ihm
                    FacesMessage message = localizeMessage(SUCCESS);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    // log
                    LOG.info("Log : Save User  : " + user.getFirstName());
                } catch (Exception e) {
                    // log
                    LOG.debug("Log : Fail save User  : " + user.getFirstName() + ", error context : " + e);
                    // throw exception
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            } else {
                if (NULLSTR.equals(user.getFirstName())) {
                    FacesMessage message = localizeMessage(F_NAME);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    LOG.debug("Log : Firstname is empty");
                }
                if (NULLSTR.equals(user.getLastName())) {
                    FacesMessage message = localizeMessage(L_NAME);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    LOG.debug("Log : Lastname is empty");
                }
            }
        } else {
            FacesMessage message = localizeMessage(EXIST);
            FacesContext.getCurrentInstance().addMessage(null, message);
            LOG.info("Log : Unable to save user, user since register in database ");
        }
        this.setCurrentUser(user);
        this.setUser(new User());
    }

    /**
     * Method to delete the user given in parameter
     * 
     * @param user
     * @return
     * @throws DAOException
     */
    public void deleteUser(User user) throws DAOException {
        try {
            // delete user in database
            userServiceBean.delete(user.getSerialNbr());
            // message ihm
            FacesMessage message = localizeMessage(SUCCESS);
            FacesContext.getCurrentInstance().addMessage(null, message);
            // log
            LOG.info("Log : user deleted " + user.getSerialNbr());
        } catch (Exception e) {
            LOG.debug("Log : unable delete User  : " + user.getSerialNbr() + ", error context : " + e);
            throw new DAOException(e.getMessage(), e.getCause());
        }
        this.setUser(new User());
        // return "user";
    }

    public String updateUser() throws DAOException {
        // Save to the database
        // message ihm
        if (!NULLSTR.equals(currentUser.getFirstName()) & !NULLSTR.equals(currentUser.getLastName())) {
            try {
                currentUser = userServiceBean.update(currentUser);
                FacesMessage message = localizeMessage(SUCCESS);
                FacesContext.getCurrentInstance().addMessage(null, message);
                LOG.debug("Log update User  : " + user.getSerialNbr());
            } catch (Exception e) {
                FacesMessage message = localizeMessage(ERROR);
                FacesContext.getCurrentInstance().addMessage(null, message);
                LOG.debug("Log echec update User  : " + user.getSerialNbr() + ", error context : " + e);
                throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (NULLSTR.equals(currentUser.getFirstName())) {
                FacesMessage message = localizeMessage(F_NAME);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }
            if (NULLSTR.equals(currentUser.getLastName())) {
                FacesMessage message = localizeMessage(L_NAME);
                FacesContext.getCurrentInstance().addMessage(null, message);
                return null;
            }
        }
        return "user";
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public List<User> populateEmp() {
        return userServiceBean.findAll();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public LocaleBean getLocalebean() {
        return localebean;
    }

    public void setLocalebean(LocaleBean localebean) {
        this.localebean = localebean;
    }

    public ProfilBean getProfilBean() {
        return profilBean;
    }

    public void setProfilBean(ProfilBean profilBean) {
        this.profilBean = profilBean;
    }

    public List<Profil> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profil> profiles) {
        this.profiles = profiles;
    }

    private List<User> setDefaultProfile(User user) {
        Profil defProfil = profilBean.getProfilDefault();
        List<User> users = new ArrayList<>();
        users.add(user);
        defProfil.setUsers(users);
        defProfil = profilBean.save(defProfil);
        this.setProfiles(profiles);
        return users;
    }

    public List<Profil> findAllProfiles(User userTemp) {
        List<Profil> findAll = new ArrayList<>();
        if (userTemp != null) {
            findAll = userServiceBean.findAll(userTemp.getSerialNbr());
            return findAll;
        } else
            return findAll;
    }
}
