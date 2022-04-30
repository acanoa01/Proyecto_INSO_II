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
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author santy
 */
@Named
@ViewScoped

public class PruebaController implements Serializable{
    private User user;
    
    @EJB
    private UserFacadeLocal userEJB;
    
    @PostConstruct
    public void init(){
        user = new User();
    }
    
    public void insertarUsuario(){
        try{
            userEJB.create(user);
        }catch(Exception e){
            System.out.println("Error al insertar el usuario en la base de datos "+ e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
