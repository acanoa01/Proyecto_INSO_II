/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author santy
 */
@Entity 
@Table(name="listplan")
public class Listplan implements Serializable{
      
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int listPlanID;
    
    @JoinColumn(name="PlanID")
    @ManyToMany
    @ElementCollection  
    private Set<Plan> plans =new HashSet<Plan>();  
    
    @JoinColumn(name="ListID")
    @ManyToMany
    @ElementCollection 
    private Set<List> lists =new HashSet<List>();  

    public int getListPlanID() {
        return listPlanID;
    }

    public void setListPlanID(int listPlanID) {
        this.listPlanID = listPlanID;
    }

    public Set<Plan> getPlans() {
        return plans;
    }

    public void setPlans(Set<Plan> plans) {
        this.plans = plans;
    }

    public Set<List> getLists() {
        return lists;
    }

    public void setLists(Set<List> lists) {
        this.lists = lists;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.listPlanID;
        hash = 17 * hash + Objects.hashCode(this.plans);
        hash = 17 * hash + Objects.hashCode(this.lists);
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
        final Listplan other = (Listplan) obj;
        if (this.listPlanID != other.listPlanID) {
            return false;
        }
        if (!Objects.equals(this.plans, other.plans)) {
            return false;
        }
        if (!Objects.equals(this.lists, other.lists)) {
            return false;
        }
        return true;
    }


}