/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.UserFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author Alberto
 */
@Named
@ViewScoped

public class LoginController implements Serializable {

    private User user;

    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void init() {
        user = new User();
    }

    public String verificarUsuario() {
        User checkUser = null;
        checkUser = userEJB.verificarUsuario(this.user);
        if (checkUser == null) {
            return "index";
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", checkUser);
            return "privado/home";
        }

    }
    
        public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    } 

}
