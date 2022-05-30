/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author santy
 */
@Entity 
@Table(name="reviews")
public class Review implements Serializable{
      
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String reviewID;
    
    @JoinColumn(name="ClientID")
    @ManyToOne
    private Client client;
    
    @JoinColumn(name="PlanID")
    @ManyToOne
    private Plan plan;
        
    @Column(name="Comment")
    private String comment;
    
    @Column(name="Title")
    private String title;
    
    @Column(name="Valoration")
    private int valoration;

    public String getReviewID() {
        return reviewID;
    }

    public void setReviewID(String reviewID) {
        this.reviewID = reviewID;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValoration() {
        return valoration;
    }

    public void setValoration(int valoration) {
        this.valoration = valoration;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.reviewID);
        hash = 71 * hash + Objects.hashCode(this.client);
        hash = 71 * hash + Objects.hashCode(this.plan);
        hash = 71 * hash + Objects.hashCode(this.comment);
        hash = 71 * hash + Objects.hashCode(this.title);
        hash = 71 * hash + this.valoration;
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
        final Review other = (Review) obj;
        if (this.valoration != other.valoration) {
            return false;
        }
        if (!Objects.equals(this.reviewID, other.reviewID)) {
            return false;
        }
        if (!Objects.equals(this.comment, other.comment)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.client, other.client)) {
            return false;
        }
        if (!Objects.equals(this.plan, other.plan)) {
            return false;
        }
        return true;
    }

    



    
    
}