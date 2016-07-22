package fr.marseille.projetfinal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Droit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    private String labeel;

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

    @ManyToMany(mappedBy = "droits")
    private List<Profil> profiles = new ArrayList<>();

    public List<Profil> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profil> profiles) {
        this.profiles = profiles;
    }

}
