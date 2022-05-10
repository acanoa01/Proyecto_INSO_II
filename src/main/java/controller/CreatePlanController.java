/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.PlanFacadeLocal;
import EJB.RolFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Client;
import modelo.Plan;
import modelo.Rol;
import modelo.User;
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
        /*TODO ESTO SE QUITA CON LA SESIÓN
        Client clienteP = new Client();
        clienteP.setAge(12);
        clienteP.setCity("Madrid");
        User usuarioP = new User();
        usuarioP.setEmail("santi@gmail.com");
        usuarioP.setName("Santi");
        usuarioP.setPassword("1234");
        List<Rol> rolP = rolEJB.findAll();
        Rol rol = rolP.get(1);    
        usuarioP.setRol(rol);
        usuarioP.setType("C");
        usuarioP.setUserName("Santiago");
        clienteP.setUser(usuarioP);*/
        if(this.client != null){
            this.plan.setVerified(false);
            this.plan.setClient(this.client);
            planEJB.create(plan);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PLAN CREADO CORRECTAMENTE"));
            return ".";
        }else{
            return ".";
        }
        
    }
   
}