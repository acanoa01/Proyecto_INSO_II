/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.AdminFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import modelo.Admin;
import modelo.Client;
import modelo.Plan;
import modelo.User;

/**
 *
 * @author Alberto
 */
public class AdminController implements Serializable {

    private Admin admin;
    private User user;

    @EJB
    private AdminFacadeLocal adminEJB;

    @PostConstruct
    public void init() {
        this.user = new User();
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.admin = new Admin();
        this.admin.setUser(user);

    }

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
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

}
