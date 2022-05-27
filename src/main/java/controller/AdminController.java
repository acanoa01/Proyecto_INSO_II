/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.AdminFacadeLocal;
import EJB.PlanFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Admin;
import modelo.Client;
import modelo.Plan;
import modelo.User;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Alberto
 */
@Named
@ViewScoped

public class AdminController implements Serializable {

    private Admin admin;
    private User user;
    private Plan plan;
    private List<Plan> planes;
    private List<Plan> notVerifyPlanes;

    public List<Plan> getNotVerifyPlanes() {
        return notVerifyPlanes;
    }

    public void setNotVerifyPlanes(List<Plan> notVerifyPlanes) {
        this.notVerifyPlanes = notVerifyPlanes;
    }
    private List<Plan> selectedPlanes;
    private Plan selectedPlan;

    @EJB
    private AdminFacadeLocal adminEJB;

    @EJB
    private PlanFacadeLocal planEJB;

    @PostConstruct
    public void init() {
        this.user = new User();
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.admin = (Admin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("admin");;
        this.admin.setUser(user);
        this.planes = new ArrayList<Plan>();
        this.notVerifyPlanes = new ArrayList<Plan>();

    }

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        doRedirect("../../index.xhtml");
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void verifyLoginAdmin() {
        System.out.println("VERIFICANDO SI EL ADMIN " + this.admin.getAdminID() + " HA INICIADO SESIÓN...");

        if (this.admin == null || !(this.admin.getUser().getRol().getUserType().equals("A"))) {
            doRedirect("index.xhtml");
        } else {
            System.out.println("EL ADMIN " + this.admin.getUser().getUserName() + " HA INICIADO SESIÓN...");
        }

    }

    private void doRedirect(String url) {
        System.out.println("URL RECIBIDA: " + url);
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().redirect(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public List<Plan> getPlanes() {
        return planes;
    }

    public Plan getSelectedPlan() {
        return selectedPlan;
    }

    public void setSelectedPlan(Plan selectedPlan) {
        this.selectedPlan = selectedPlan;
    }

    public void setSelectedPlanes(List<Plan> selectedPlanes) {
        this.selectedPlanes = selectedPlanes;
    }

    public void setPlanes(List<Plan> planes) {
        this.planes = planes;
    }

    public void getAllPlans() {
        System.out.println("HOLAAAAA");
        this.planes = planEJB.findAll();
        for (int i = 0; i < this.planes.size(); i++) {
            if (!this.planes.get(i).isVerified()) {
                this.notVerifyPlanes.add(this.planes.get(i));
            }
        }

        System.out.println(this.planes.size());

    }

    public List<Plan> getSelectedPlanes() {
        return this.selectedPlanes;
    }

    public void validatePlans() {

        for (int i = 0; i < this.selectedPlanes.size(); i++) {
            Plan newPlan = new Plan();
            newPlan = this.selectedPlanes.get(i);
            newPlan.setVerified(true);
            planEJB.edit(newPlan);
        }
        doRedirect("index.xhtml");

    }

    /* EDIT PLANES */
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

    }

}
