/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.FavouriteFacadeLocal;
import EJB.PlanFacadeLocal;
import EJB.ReviewFacade;
import EJB.ReviewFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Client;
import modelo.Favourite;
import modelo.Plan;
import modelo.Review;

/**
 *
 * @author sergi
 */
@Named
@ViewScoped

public class PlanViewController implements Serializable {

    private Plan plan;
    private Review review;

    @EJB
    private ReviewFacadeLocal reviewEJB;

    @EJB
    private FavouriteFacadeLocal favouriteEJB;
    
    @EJB
    private PlanFacadeLocal planEJB;
    
    @PostConstruct
    public void init(){
        this.review = new Review();
        this.plan = (Plan) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("plan");
        if(this.plan == null){
            doRedirect("/planazzo/faces/privado/cliente/index.xhtml");
        }
    }

    private void doRedirect(String url) {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().redirect(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public String getImage() {

        if (this.plan.getImage() == null) {
            return "adventureClouds.jpg";
        } else {
            return this.plan.getImage();
        }
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public List<Review> getReviews() {
        return reviewEJB.getReviews(this.plan);
    }

    public void insertarResena() throws Exception{
 
        Client client = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
        this.review.setClient(client);
        this.review.setPlan(this.plan);
        reviewEJB.create(this.review);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "REVIEW CREADA CORRECTAMENTE"));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("plan", plan);   
        FacesContext.getCurrentInstance().getExternalContext().redirect("/planazzo/faces/privado/cliente/planView.xhtml");
    }
    
    public void agregarFavoritos(){  
        Favourite favourite = new Favourite();
        Client cliente = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente"); 
        favourite.setClient(cliente);
        favourite.setPlan(this.plan);
        if(!favouriteEJB.favouriteExists(favourite)){
            favouriteEJB.create(favourite);
            this.plan.setLikes(this.plan.getLikes() + 1);
            planEJB.edit(plan);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "EL PLAN HA SIDO AGREGADO A FAVORITOS", ""));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ESTE PLAN YA ESTA EN TUS FAVORITOS", ""));
        }
        
    }
}
