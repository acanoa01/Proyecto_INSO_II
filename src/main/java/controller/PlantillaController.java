/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import modelo.Admin;
import modelo.Client;

/**
 *
 * @author sergi
 */
@Named
@RequestScoped



public class PlantillaController implements Serializable{

    @PostConstruct
    public void init() {
        
    }
   
    private void doRedirect(String url) {
        System.out.println("URL RECIBIDA: " + url);
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().redirect(url);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void verificarYMostrar(String rol){
        Client client = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("admin");
        if(client == null && admin == null){
            doRedirect("/planazzo/faces/privado/PermisosInsuficientes.xhtml");
        }else if(client != null && !client.getUser().getRol().getUserType().equals(rol)){
            doRedirect("/planazzo/faces/privado/PermisosInsuficientes.xhtml");
        }else if(admin != null && !admin.getUser().getRol().getUserType().equals(rol)){
            doRedirect("/planazzo/faces/privado/PermisosInsuficientes.xhtml");
        }
    }
    
        
}