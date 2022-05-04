/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.UserFacadeLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author Alberto
 */

@Named
@ViewScoped
public class HomeController implements Serializable{
        private User user;

    @EJB
    private UserFacadeLocal userEJB;
    
        public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }   
}
