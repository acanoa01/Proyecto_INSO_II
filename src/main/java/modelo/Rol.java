/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author santy
 */
@Entity
@Table(name = "rols")
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rolID;

    @Column(name = "UserType")
    private String userType;

    @Column(name = "Description")
    private String Description;

    public String getRolID() {
        return rolID;
    }

    public void setRolID(String rolID) {
        this.rolID = rolID;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.rolID);
        hash = 23 * hash + Objects.hashCode(this.userType);
        hash = 23 * hash + Objects.hashCode(this.Description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rol other = (Rol) obj;
        if (!Objects.equals(this.rolID, other.rolID)) {
            return false;
        }
        if (!Objects.equals(this.userType, other.userType)) {
            return false;
        }
        if (!Objects.equals(this.Description, other.Description)) {
            return false;
        }
        return true;
    }

}
