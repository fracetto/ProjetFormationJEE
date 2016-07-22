package beans;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.exception.DAOException;
import fr.marseille.projetfinal.exception.UIExceptBean;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.UserService;

@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    private User                           user;
    private ClassPathXmlApplicationContext context;
    private UserService                    userServiceBean;
    private final String                   NULL_STR = "";
    private User                           currentUser;

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

    // public User init() {
    // if (currentUser == null) {
    // Map<String, String> requestMap = FacesContext.getCurrentInstance().getExternalContext()
    // .getRequestParameterMap();
    // String strParam = requestMap.get("param");
    // if (!NULL_STR.equals(strParam) & strParam != null) {
    // serialNbr = Integer.parseInt(strParam);
    // // Access on user by id
    // ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
    // UserService userServiceBean = (UserService) context.getBean("userService");
    // currentUser = userServiceBean.find(serialNbr);
    // } else {
    // // currentUser = user;
    // }
    // }
    //
    // return currentUser;
    // }

    public void saveUser() throws UIExceptBean, DAOException {
        if (!NULL_STR.equals(user.getFirstName()) & !NULL_STR.equals(user.getFirstName())) {
            try {
                userServiceBean.save(user);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Successfully Updated", "Updated value to " + toString()));
            } catch (Exception e) {
                throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (NULL_STR.equals(user.getFirstName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Firstname est vide", ""));
                // throw new UIExceptBean("Le champ Firstname est vide");
            }
            if (NULL_STR.equals(user.getFirstName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Lastname est vide", ""));
                // throw new UIExceptBean("Le champ Lastname est vide");
            }
        }

    }

    public void deleteUser(User user) throws DAOException {
        try {
            userServiceBean.delete(user.getSerialNbr());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Successfully Updated", "Updated value to " + toString()));
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e.getCause());
        }

    }

    public String updateUser() throws DAOException {
        // Save to the database
        if (!NULL_STR.equals(currentUser.getFirstName()) & !NULL_STR.equals(currentUser.getFirstName())) {
            try {
                userServiceBean.update(currentUser);
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Probleme de BDD", ""));
                throw new DAOException(e.getMessage(), e.getCause());
            }
        } else {
            if (NULL_STR.equals(currentUser.getFirstName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Firstname est vide", ""));
                // throw new UIExceptBean("Le champ Firstname est vide");
            }
            if (NULL_STR.equals(currentUser.getFirstName())) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le champ Lastname est vide", ""));
                // throw new UIExceptBean("Le champ Lastname est vide");
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
