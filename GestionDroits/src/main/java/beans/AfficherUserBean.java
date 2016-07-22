package beans;

import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import fr.marseille.projetfinal.model.User;
import fr.marseille.projetfinal.service.UserService;

@ManagedBean
@SessionScoped
public class AfficherUserBean {
    private User                           user;
    private ClassPathXmlApplicationContext context;

    private UserService userServiceBean;

    public AfficherUserBean() {
        this.context = new ClassPathXmlApplicationContext("application-context.xml");
        this.userServiceBean = (UserService) context.getBean("userService");
    }

    public User getUser() {
        if (null == user) {
            return getCurrentUser();
        }

        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getCurrentUser() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String str = params.get("param");
        if (!str.isEmpty()) {
            user = userServiceBean.find(Integer.parseInt(str));
            System.out.println("Le user est : " + user.getSerialNbr());
            return user;
        }
        return null;
    }
}
