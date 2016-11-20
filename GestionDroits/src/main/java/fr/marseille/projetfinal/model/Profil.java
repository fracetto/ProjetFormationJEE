package fr.marseille.projetfinal.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

/**
 * The Class Profil.
 */
@Entity
public class Profil implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2807505030896618379L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer     id;

    /** The name. */
    private String      name;

    /** The description. */
    private String      description;

    @ManyToMany
    @JoinTable(name = "profil_users", joinColumns = @JoinColumn(name = "profils_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "serialNbr"))
    private List<User>  users  = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "profil_droits", joinColumns = @JoinColumn(name = "profils_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "droits_id", referencedColumnName = "id"))
    private List<Droit> droits = new ArrayList<>();

    /**
     * Instantiates a new profil.
     */
    public Profil() {
        super();
    }

    /**
     * Instantiates a new profil.
     *
     * @param id
     *            the id
     * @param name
     *            the name
     * @param description
     *            the description
     */

    public Profil(Integer id, String name, String description, List<User> users, List<Droit> droits) {
        super();
        this.id = id;
        this.name = name;
        this.description = description;
        this.users = users;
//        this.droits = droits;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Droit> getDroits() {
        return droits;
    }

    public void setDroits(List<Droit> droits) {
        this.droits = droits;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description
     *            the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
