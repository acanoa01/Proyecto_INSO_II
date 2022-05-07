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
import modelo.Rol;
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
        user = new User();
        user.setType("U");
        
        client = new Client();
    }


    /* LOGIN IMPLEMENTATION */
    public String verificarUsuario() {
        User checkUser = userEJB.verificarUsuario(this.user);
        if (checkUser == null) {
            return "index";
        } else {
            Client cliente = clientEJB.getClient(checkUser);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", cliente);
            return "privado/home";
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

        try {
            clientEJB.create(client);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", client);
            return "home";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear el usuario"));
            System.out.println("Error: "+e.getMessage());
            return "index";
        }

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
