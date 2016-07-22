package beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import fr.marseille.projetfinal.model.Profil;
import fr.marseille.projetfinal.service.ProfilService;

public class EditProfilView {
    @ManagedBean
    // (name = "dtEditView")
    @ViewScoped
    public class EditView implements Serializable {

        private Profil profil;

        @ManagedProperty("#{profileService}")
        private ProfilService service;

        @PostConstruct
        public void init() {
            Profil profil = new Profil();
        }

        public Profil getProfil() {
            return profil;
        }

        public void onRowEdit(RowEditEvent event) {
            FacesMessage msg = new FacesMessage("Profil Edited");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        public void onRowCancel(RowEditEvent event) {
            FacesMessage msg = new FacesMessage("Edit Cancelled");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

        public void onCellEdit(CellEditEvent event) {
            Object oldValue = event.getOldValue();
            Object newValue = event.getNewValue();

            if (newValue != null && !newValue.equals(oldValue)) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
                        "Old: " + oldValue + ", New:" + newValue);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }
}
