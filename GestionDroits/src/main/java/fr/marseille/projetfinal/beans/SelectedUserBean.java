package fr.marseille.projetfinal.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import fr.marseille.projetfinal.model.User;

@ManagedBean
@RequestScoped
public class SelectedUserBean implements Serializable {

    @ManagedProperty(value = "#{userBean}")
    private UserBean userBean;

    private User     selectedUser;

    public SelectedUserBean() {
        super();
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

}
