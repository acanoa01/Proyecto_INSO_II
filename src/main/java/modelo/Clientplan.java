/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
@Table(name="clientplan")
public class Clientplan implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int clientPlanID;
         
    @JoinColumn(name="PlanID")
    @ManyToOne
    private Plan plan;
    
    @JoinColumn(name="ClientID")
    @ManyToOne
    private Client client;

    public int getClientPlanID() {
        return clientPlanID;
    }

    public void setClientPlanID(int clientPlanID) {
        this.clientPlanID = clientPlanID;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + this.clientPlanID;
        hash = 19 * hash + Objects.hashCode(this.plan);
        hash = 19 * hash + Objects.hashCode(this.client);
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
        final Clientplan other = (Clientplan) obj;
        if (this.clientPlanID != other.clientPlanID) {
            return false;
        }
        if (!Objects.equals(this.plan, other.plan)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        return true;
    }

    
    
}