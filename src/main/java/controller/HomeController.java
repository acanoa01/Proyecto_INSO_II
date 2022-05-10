/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.ClientFacadeLocal;
import EJB.UserFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import modelo.Client;
import modelo.User;

/**
 *
 * @author Alberto
 */

@Named
@ViewScoped
public class HomeController implements Serializable{
        
        @Inject
        private User user;
        
        @Inject
        private Client client;

    @EJB
    private UserFacadeLocal userEJB;
    
    @EJB
    private ClientFacadeLocal clientEJB;
    
    
    
    @PostConstruct
    public void init() {
//        client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
//        user = client.getUser();
    }
    
    public void cerrarSesion(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
    
        public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }   
}
