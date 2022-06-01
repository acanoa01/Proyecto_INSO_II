/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.PlanFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Admin;
import modelo.Client;
import modelo.Plan;

/**
 *
 * @author Alberto
 */
@Named
@ViewScoped

public class AdminController implements Serializable {

    private Admin admin;
    private List<Plan> planes;
    private List<Plan> selectedPlanes;
    private List<Plan> verifyPlans;
    private Plan editPlan;
    
    @EJB
    private PlanFacadeLocal planEJB;

    @PostConstruct
    public void init() {
        this.admin = (Admin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("admin");
        this.verifyPlans = planEJB.getNotValidated();
        this.planes = planEJB.findAll();
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Plan> getPlanes() {
        return planes;
    }

    public void setSelectedPlanes(List<Plan> selectedPlanes) {
        this.selectedPlanes = selectedPlanes;
    }

    public void setPlanes(List<Plan> planes) {
        this.planes = planes;
    }

    public List<Plan> getSelectedPlanes() {
        return this.selectedPlanes;
    }

    public List<Plan> getVerifyPlans() {
        return verifyPlans;
    }

    public void setVerifyPlans(List<Plan> verifyPlans) {
        this.verifyPlans = verifyPlans;
    }

    public Plan getEditPlan() {
        return editPlan;
    }

    public void setEditPlan(Plan editPlan) {
        this.editPlan = editPlan;
    }
    
    public void validatePlans() {
        for (int i = 0; i < this.selectedPlanes.size(); i++) {
            Plan newPlan = this.selectedPlanes.get(i);
            newPlan.setVerified(true);
            newPlan.setAdmin(admin);
            planEJB.edit(newPlan);
            this.planes.remove(newPlan);
        }
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

    public void edit() {
        if (this.editPlan != null) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("plan", this.editPlan);
            doRedirect("/planazzo/faces/privado/administrador/editPlanView.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "DEBE SELECCIONAR UN PLAN"));
        }
    }
    
    public void generarPlanAleatorio(int inicio, int fin, String tipo){
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("admin");
        
        if(inicio > fin){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "INICIO DESPUES DE FIN"));
        }
        while(inicio < fin){
            Plan newPlan = this.selectedPlanes.get(inicio);
            newPlan.setVerified(true);
            inicio += 1;
            if(tipo.equals("admin") && fin <= 10){
                newPlan.setAdmin(admin);
                newPlan.setLikes(fin);
            }else{
                newPlan.setLikes(0);
            }
        }

    }
}
