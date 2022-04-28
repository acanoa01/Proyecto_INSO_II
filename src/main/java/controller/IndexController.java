/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.UserFacadeLocal;
import java.io.Serializable;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;
/**
 *
 * @author sergi
 */
@Named
@RequestScoped


public class IndexController implements Serializable{
    private User user;
    
    @EJB
    private UserFacadeLocal userEJB;
    
    @PostConstruct
    public void init(){
        user = new User();
    }
    public String moveToPrueba(){
        return "prueba";
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