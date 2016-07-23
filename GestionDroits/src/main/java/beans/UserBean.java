package beans;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
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

    private User                           user;
    private ClassPathXmlApplicationContext context;
    private UserService                    userServiceBean;
    private static final String            NULLSTR = "";
    private User                           currentUser;
    private static final Logger            LOG     = Logger.getLogger(UserBean.class);

    public UserBean() {
        super();
        this.context = new ClassPathXmlApplicationContext("application-context.xml");
        this.userServiceBean = (UserService) context.getBean("userService");
        this.user = new User();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void saveUser() throws DAOException {
        if (!NULLSTR.equals(user.getFirstName()) & !NULLSTR.equals(user.getLastName())) {
            try {
                userServiceBean.save(user);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successfully Updated", "Updated value to " + toString()));
                LOG.info("Log save User  : " + user.getFirstName());
            } catch (Exception e) {
                LOG.debug("Log echec save User  : " + user.getFirstName());
                throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (NULLSTR.equals(user.getFirstName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Firstname est vide", ""));
                LOG.debug("Le champ Firstname est vide");
            }
            if (NULLSTR.equals(user.getLastName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Lastname est vide", ""));
                LOG.debug("Le champ Lastname est vide" + user.getFirstName());
            }
        }

    }

    public void deleteUser(User user) throws DAOException {
        try {
            userServiceBean.delete(user.getSerialNbr());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successfully Updated", "Updated value to " + toString()));
        } catch (Exception e) {
            LOG.debug("Log echec delete User  : " + user.getSerialNbr());
            throw new DAOException(e.getMessage(), e.getCause());
        }

    }

    public String updateUser() throws DAOException {
        // Save to the database
        if (!NULLSTR.equals(currentUser.getFirstName()) & !NULLSTR.equals(currentUser.getLastName())) {
            try {
                userServiceBean.update(currentUser);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Probleme de BDD", ""));
                LOG.debug("Log echec update User  : " + user.getSerialNbr());
                throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (NULLSTR.equals(currentUser.getFirstName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Firstname est vide", ""));
                return null;
            }
            if (NULLSTR.equals(currentUser.getLastName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Lastname est vide", ""));
                return null;
            }
        }
        // message ihm
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully Updated", ""));
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
}
