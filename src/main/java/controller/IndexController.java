/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.ClientFacadeLocal;
import EJB.RolFacadeLocal;
import EJB.UserFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Client;
import modelo.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author sergi
 */
@Named
@ViewScoped

public class IndexController implements Serializable {

    private User user;
    private Client client;

    @EJB
    private UserFacadeLocal userEJB;

    @EJB
    private ClientFacadeLocal clientEJB;
    
    @EJB
    private RolFacadeLocal rolEJB;

    @PostConstruct
    public void init() {
        this.user = new User();
        this.user.setType("U");
        this.client = new Client();
    }


    /* LOGIN IMPLEMENTATION */
    public void verificarUsuario() {
        
        User checkUser = this.userEJB.verificarUsuario(this.user);
        if (checkUser == null) {
            
        } else {
            int i = 1;//Lo pongo por ver una cosa
            this.client = clientEJB.getClient(checkUser);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", this.client);
            //quitar de pantalla el login
         
        }
    }

    /* REGISTER IMPLEMENTATION */
    public String insertarUsuario() {
        
        String plainPassword = user.getPassword();
        String hashPassword = hashPassword(plainPassword);
        user.setPassword(hashPassword);
        user.setType("C");
        user.setRol(rolEJB.getRol(user.getType()));
        client.setUser(user);
        
        System.out.println(client.getAge());
        System.out.println(client.getCity());
        System.out.println(client.getClientID());

        try {
            clientEJB.create(client);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", client);
            return "privado/home";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear el usuario"));
            System.out.println("Error: "+e.getMessage());
            return "index";
        }

    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
