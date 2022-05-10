/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.PlanFacadeLocal;
import EJB.RolFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Client;
import modelo.Plan;
/**
 *
 * @author sergi
 */
@Named
@ViewScoped



public class CreatePlanController implements Serializable{
    private Plan plan;
    private Client client;
    
    @EJB
    private PlanFacadeLocal planEJB;
    
    @EJB
    RolFacadeLocal rolEJB;

    
    @PostConstruct
    public void init(){
        this.plan = new Plan();
        this.client = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    
    public String insertPlan(){
        this.client = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
        if(this.client != null){
            this.plan.setVerified(false);
            this.plan.setClient(this.client);
            planEJB.create(plan);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PLAN CREADO CORRECTAMENTE"));
            return "faces/createPlan.xhtml";
        }else{
            return ".";
        }
        
    }
   
}