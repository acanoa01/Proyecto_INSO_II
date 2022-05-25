/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.ClientFacadeLocal;
import EJB.UserFacadeLocal;
import java.io.IOException;
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
public class ClientController implements Serializable {

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
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");;
        this.client.setUser(user);
    }

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        doRedirect("../../index.xhtml");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void verifyLogin() {
        System.out.println("VERIFICANDO SI EL CLIENTE " + this.client.getClientID() + " HA INICIADO SESIÓN...");

        if (this.client == null || !(this.client.getUser().getRol().getUserType().equals("C"))) {
            doRedirect("index.xhtml");
        } else {
            System.out.println("EL CLIENTE " + this.client.getUser().getUserName() + " HA INICIADO SESIÓN...");
        }

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
}
