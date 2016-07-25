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

/**
 * 
 */
@Entity
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public User(Integer serialNbr, List<Profil> profiles, String lastName, String firstName, String comment) {
        super();
        this.serialNbr = serialNbr;
        this.profiles = profiles;
        this.lastName = lastName;
        this.firstName = firstName;
        this.comment = comment;
    }

    /**
     * 
     */

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer serialNbr;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Profil> profiles = new ArrayList<>();

    /**
     * 
     */
    private String lastName;

    /**
     * 
     */
    private String firstName;

    /**
     * 
     */
    private String comment;

    /**
     * Default constructor
     */
    public User() {
    }

    public Integer getSerialNbr() {
        return serialNbr;
    }

    public void setSerialNbr(Integer serialNbr) {
        this.serialNbr = serialNbr;
    }

    public List<Profil> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profil> profiles) {
        this.profiles = profiles;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}