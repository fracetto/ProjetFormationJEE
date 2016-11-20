package fr.marseille.projetfinal.beans;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Component;

@ManagedBean
@SessionScoped
public class LocaleBean {

    private Locale locale;

    @PostConstruct
    public void init() {
        this.locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
    }

    public LocaleBean() {
        // TODO Auto-generated constructor stub
	super();
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
