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
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Rol;
import modelo.User;

/**
 *
 * @author Alberto
 */
@Named
@ViewScoped
public class RegisterController implements Serializable{
 private User user;

    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void init() {
        user = new User();
    }
    
    
//    //Crear usuario
    public void createUser() {
        //Comprobar que el nombre de usuario esta disponible
        //Comprobar que el email sea válido
        //Comprobar que la contraseña no sea débil
        //Mostrar errores de campos vacios
        
        //Seteamos el rolId a 1
        Rol userRol = new Rol();
        userRol.setRolID(Integer.parseInt("1"));
        userRol.setDescription("Rol de usuario asignado");
        userRol.setUserType("C");
        
        user.setRol(userRol);
        
        user.setUserID(Integer.parseInt("1"));
        
  
        
        //Comprobaciones
        
        //Insertar usuario en la base de datos
        try {
            userEJB.create(user);
        }catch(Exception e){
            System.out.println("Error al insertar el usuario en la base de datos " + e.getMessage());
        }
       
    }

    //iniciar sesion de usuario
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }   
}
