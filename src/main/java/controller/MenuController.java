/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.MenuFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import modelo.Admin;
import modelo.Client;
import modelo.Menu;
import modelo.User;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;


/**
 *
 * @author sergi
 */
@Named
@SessionScoped

public class MenuController implements Serializable {

    @EJB
    private MenuFacadeLocal menuEJB;
    
    private MenuModel modelo;
    
    @PostConstruct
    public void init() {
        this.modelo = new DefaultMenuModel();
    }
    
    public MenuModel obtenerMenu(){
        Client cliente = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
        Admin admin = (Admin)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("admin");
        
        if(cliente != null){
            User usuario = cliente.getUser();
            return obtenerMenuUsuario(usuario);
        }else if(admin != null){
            User usuario = admin.getUser();
            return obtenerMenuUsuario(usuario);
        }else{
            return obtenerMenuSinRegistrar();
        }
    }
    
    public MenuModel obtenerMenuUsuario(User usuario){
        List<Menu> menus = menuEJB.obtenerMenusUsuarios(usuario);
        for(int i = 0; i<menus.size();i++){
            Menu menu = menus.get(i);
            DefaultMenuItem item = DefaultMenuItem.builder().value(menu.getName()).url(menu.getUrl()).icon(menu.getIcon()).build();
            this.modelo.getElements().add(item);   
        }
        return this.modelo;
    }
    
    public MenuModel obtenerMenuSinRegistrar(){
        List<Menu> menus = menuEJB.obtenerMenusUsuariosNoRegistrado();
        for(int i = 0; i<menus.size();i++){
            Menu menu = menus.get(i);
            DefaultMenuItem item = DefaultMenuItem.builder().value(menu.getName()).url(menu.getUrl()).icon(menu.getIcon()).build();
            this.modelo.getElements().add(item);   
        }
        return this.modelo;
    }
    
    public void cerrarSesion(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("/planazzo/faces/index.xhtml");
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        }catch(Exception e){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cerrar sesión"));
        }
    }
}
