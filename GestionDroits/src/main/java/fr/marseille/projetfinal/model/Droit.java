package fr.marseille.projetfinal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Droit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    private String labeel;
    
    @ManyToMany(mappedBy = "droits", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Profil> profiles = new ArrayList<>();
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabeel() {
        return labeel;
    }

    public void setLabeel(String labeel) {
        this.labeel = labeel;
    }

    public Droit() {
        super();
    }

    public Droit(Integer id, String labeel, List<Profil> profils) {
        super();
        this.id = id;
        this.labeel = labeel;
        this.profiles = profils;
    }



    public List<Profil> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profil> profiles) {
        this.profiles = profiles;
    }

}
