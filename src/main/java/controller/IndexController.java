/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.ClientFacadeLocal;
import EJB.UserFacadeLocal;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    private  Rol rol;

    @EJB
    private UserFacadeLocal userEJB;

    @EJB
    private ClientFacadeLocal clientEJB;

    @PostConstruct
    public void init() {
        user = new User();
        client = new Client();
        rol = new Rol();
    }


    /* LOGIN IMPLEMENTATION */
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

    /* REGISTER IMPLEMENTATION */
    public String insertarUsuario() {
        
        String plainPassword = user.getPassword();
        String hashPassword = hashPassword(plainPassword);
        user.setPassword(hashPassword);
        user.setType("C");
        rol.setDescription("Usuario registrado");
        rol.setUserType(user.getType());
        user.setRol(rol);
        client.setUser(user);
//        System.out.println("[*]/////////////////////////////////////////[*]");
//        System.out.println(user.getUserID());
//        System.out.println(user.getUserName());
//        System.out.println(user.getName());
//        System.out.println(user.getRol().getRolID());
//        System.out.println(user.getRol().getDescription());
//        System.out.println(user.getRol().getUserType());
//        System.out.println(user.getEmail());
//        System.out.println(user.getType());
//        System.out.println(user.getPassword());
//        System.out.println("[*]------------------------------------------[*]");
//        System.out.println(client.getUser());
//        System.out.println(client.getClientID());
//        System.out.println(client.getCity());
//        System.out.println(client.getAge());

        try {
            clientEJB.create(client);
            userEJB.create(user);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", user);
            return "home";
        } catch (Exception e) {
            System.out.println("Error al insertar el usuario en la base de datos " + e.getMessage());
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
