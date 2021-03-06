package tn.esprit.b1.esprit1718b1erp.mbeans;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import tn.esprit.b1.esprit1718b1erp.entities.User;
import tn.esprit.b1.esprit1718b1erp.services.UserServiceLocal;

@ManagedBean
@SessionScoped
public class Identity {
    private boolean isLogged = false;
    private User user = new User();
    @EJB
    private UserServiceLocal userServiceLocal;

    public String logout() {
        isLogged = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    public String doLogin() {
        String navigateTo = "";
        User userLoggedIn = userServiceLocal.login(user.getLogin(), user.getPassword());
        if (userLoggedIn != null) {
            isLogged = true;
            user = userLoggedIn;
            navigateTo = "/home?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Veuillez insérer un login et un mot de passe valide", ""));
            return "/login?faces-redirect=true";

        }
        System.out.println(user.getName());
        return navigateTo;

    }
    
    public String doLogout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login?faces-redirect=true";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public Boolean getIsLogged() {
        return isLogged;
    }

    public void setIsLogged(Boolean isLogged) {
        this.isLogged = isLogged;
    }

}
