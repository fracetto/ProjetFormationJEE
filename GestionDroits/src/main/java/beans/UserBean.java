package beans;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.exception.DAOException;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.UserService;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    /**
     * Author Franck
     */
    private static final long              serialVersionUID = 1L;
    private User                           user;
    private ClassPathXmlApplicationContext context;
    private UserService                    userServiceBean;
    private static final String            NULLSTR          = "";
    private User                           currentUser;
    private static final Logger            LOG              = Logger.getLogger(UserBean.class);
    private static final String            MSG_EN_SUCCESS   = "Successfully Updated";
    private static final String            MSG_FR_SUCCESS   = "Enregistrement reussi";
    private static final String            MSG_EN_F_NAME    = "Error : First Name is empty !";
    private static final String            MSG_FR_F_NAME    = "Erreur : Prenom est vide ! ";
    private static final String            MSG_EN_L_NAME    = "Error : Last Name is empty !";
    private static final String            MSG_FR_L_NAME    = "Erreur : Nom est vide ! ";
    private static final String            MSG_EN_EXIST     = "Error : Existing User !";
    private static final String            MSG_FR_EXIST     = "Erreur : Utilisateur existant !";
    private static final String            MSG_EN_ERROR     = "Error : DB !";
    private static final String            MSG_FR_ERROR     = "Erreur : BD !";

    public UserBean() {
        super();
        this.context = new ClassPathXmlApplicationContext("application-context.xml");
        this.userServiceBean = (UserService) context.getBean("userService");
        this.user = new User();
    }

    @ManagedProperty(value = "#{localeBean}")
    private LocaleBean localebean;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    private boolean comparedProperties() {
        boolean isEqual = false;
        if (currentUser != null) {
            if (user.getFirstName().equals(currentUser.getFirstName())
                    & user.getLastName().equals(currentUser.getLastName())
                    & user.getComment().equals(currentUser.getComment())) {
                isEqual = true;
            }
        }
        return isEqual;
    }

    public void saveUser() throws DAOException {
        // Save to the database
        // message ihm
        if (!comparedProperties()) {
            if (!NULLSTR.equals(user.getFirstName()) & !NULLSTR.equals(user.getLastName())) {
                try {
                    user = userServiceBean.save(user);
                    if (localebean.getLanguage() == "fr") {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_SUCCESS, null);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    } else if (localebean.getLanguage() == "en") {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_SUCCESS, null);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                    LOG.info("Log : Save User  : " + user.getFirstName());
                } catch (Exception e) {
                    LOG.debug("Log : Fail save User  : " + user.getFirstName());
                    throw new DAOException(e.getMessage(), e.getCause());
                }
            } else {
                if (NULLSTR.equals(user.getFirstName())) {
                    if (localebean.getLanguage() == "fr") {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_F_NAME, null);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    } else if (localebean.getLanguage() == "en") {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_F_NAME, null);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                    LOG.debug("Log : Firstname is empty");
                }
                if (NULLSTR.equals(user.getLastName())) {
                    if (localebean.getLanguage() == "fr") {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_L_NAME, null);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    } else if (localebean.getLanguage() == "en") {
                        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_L_NAME, null);
                        FacesContext.getCurrentInstance().addMessage(null, message);
                    }
                    LOG.debug("Log : Lastname is empty");
                }
            }
        } else {
            if (localebean.getLanguage() == "fr") {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_EXIST, null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else if (localebean.getLanguage() == "en") {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_EXIST, null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            LOG.info("Log : Unable to save user, user since register in database " + user.getSerialNbr());
        }
        currentUser = user;
        user = new User();
    }

    public User deleteUser(User user) throws DAOException {
        // Save to the database
        // message ihm
        try {
            userServiceBean.delete(user.getSerialNbr());
            if (localebean.getLanguage() == "fr") {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_SUCCESS, null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else if (localebean.getLanguage() == "en") {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_SUCCESS, null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            LOG.info("Log : user deleted " + user.getSerialNbr());
        } catch (Exception e) {
            LOG.debug("Log : unable delete User  : " + user.getSerialNbr());
            throw new DAOException(e.getMessage(), e.getCause());
        }
        user = new User();
        return user;
    }

    public String updateUser() throws DAOException {
        // Save to the database
        // message ihm
        if (!NULLSTR.equals(currentUser.getFirstName()) & !NULLSTR.equals(currentUser.getLastName())) {
            try {
                currentUser = userServiceBean.update(currentUser);
                if (localebean.getLanguage() == "fr") {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_SUCCESS, null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else if (localebean.getLanguage() == "en") {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_SUCCESS, null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            } catch (Exception e) {
                if (localebean.getLanguage() == "fr") {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_ERROR, null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else if (localebean.getLanguage() == "en") {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_ERROR, null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                LOG.debug("Log echec update User  : " + user.getSerialNbr());
                throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (NULLSTR.equals(currentUser.getFirstName())) {
                if (localebean.getLanguage() == "fr") {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_F_NAME, null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else if (localebean.getLanguage() == "en") {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_F_NAME, null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                return null;
            }
            if (NULLSTR.equals(currentUser.getLastName())) {
                if (localebean.getLanguage() == "fr") {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_FR_L_NAME, null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else if (localebean.getLanguage() == "en") {
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, MSG_EN_L_NAME, null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
                return null;
            }
        }
        return "index";
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

}
