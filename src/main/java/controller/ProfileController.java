/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.ClientplanFacadeLocal;
import EJB.FavouriteFacadeLocal;
import EJB.PlanFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import modelo.Client;
import modelo.Plan;
import org.primefaces.model.ResponsiveOption;
/**
 *
 * @author sergi
 */
@Named
@ViewScoped



public class ProfileController implements Serializable{
    private List<Plan> plans;
    private List<Plan> favourites;
    private List<ResponsiveOption> responsiveOptions;

    
    @EJB
    private FavouriteFacadeLocal favouriteEJB;
    
    @EJB
    private ClientplanFacadeLocal clientPlanEJB;
    

    
    @PostConstruct
    public void init() {
        Client cliente = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente"); 
        plans = clientPlanEJB.getPlanes(cliente);
        favourites = favouriteEJB.getFavoritos(cliente);
        responsiveOptions = new ArrayList<>();
        responsiveOptions.add(new ResponsiveOption("1024px", 3, 3));
        responsiveOptions.add(new ResponsiveOption("768px", 2, 2));
        responsiveOptions.add(new ResponsiveOption("560px", 1, 1));
    }
    
    public List<Plan> getPlans(){
        return this.plans;
    }

    public void setPlans(List<Plan> plans) {
        this.plans = plans;
    }
    
    
    
    public List<ResponsiveOption> getResponsiveOptions() {
        return responsiveOptions;
    }

    public void setResponsiveOptions(List<ResponsiveOption> responsiveOptions) {
        this.responsiveOptions = responsiveOptions;
    }

    public List<Plan> getFavourites() {
        return favourites;
    }

    public void setFavourites(List<Plan> favourites) {
        this.favourites = favourites;
    }
    
    private void doRedirect(String url) {
        System.out.println("URL RECIBIDA: " + url);
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().redirect(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void verPlan(Plan plan){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("plan", plan);   
        doRedirect("/planazzo/faces/privado/cliente/planView.xhtml");
    }
        
}