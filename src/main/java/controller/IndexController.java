/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.ClientFacadeLocal;
import EJB.PlanFacadeLocal;
import EJB.RolFacadeLocal;
import EJB.UserFacadeLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Admin;
import modelo.Client;
import modelo.Plan;
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
    private Admin admin;

    private Rol rol;
    private List<Rol> roles;

    private Plan plan;

    @EJB
    private UserFacadeLocal userEJB;

    @EJB
    private ClientFacadeLocal clientEJB;

    @EJB
    private RolFacadeLocal rolEJB;

    @EJB
    private PlanFacadeLocal planEJB;

    @PostConstruct
    public void init() {
        this.user = new User();
        this.user.setType("U");
        this.client = new Client();
        this.roles = rolEJB.findAll();
        this.plan = new Plan();
        this.admin = new Admin();
    }

    /*GESTION DE ROLES */
    public String getRolDescription() {
        return rol.getDescription();
    }

    public List<Rol> getRoles() {
        return roles;
    }

    /* LOGIN IMPLEMENTATION */
    public void verificarUsuario() {

        User checkUser = this.userEJB.verificarUsuario(this.user);

        if (checkUser == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "error", "Usuario o contraseña incorrectos"));
            doRedirect("index.xhtml");
        } else {
            System.out.println("ROL DE USUARIO QUE HA INICIADO SESIÓN: " + checkUser.getRol().getDescription());
            if (checkUser.getRol().getUserType().equals("A")) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", checkUser);
                doRedirect("privado/administrador/index.xhtml");
            } else {
                this.client = clientEJB.getClient(checkUser);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", this.client);
                doRedirect("privado/cliente/home.xhtml");
            }

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

            return "privado/cliente/home";
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al crear el usuario"));
            System.out.println("Error: " + e.getMessage());
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

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    private String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public String moveToIndexAdmin() {
        return "indexAdmin";
    }

    public String moveToCrearPlan() {
        return "createPlanAdmin";
    }

    public String moveToAdmin() {
        System.out.println("menuda mierda");
        return "privado/administrador/createPlan";
    }

    public String moveToIndex() {
        return "index";
    }

    public void verifyLogin() {
        this.user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        if (this.user == null || !(this.user.getRol().getUserType().equals("C"))) {
            doRedirect("index.xhtml");
        }

    }

    private void doRedirect(String url) {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            fc.getExternalContext().redirect(url);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void generateRandomPlan() {
        List<Plan> randomPlan = planEJB.findAll();
        Random random = new Random();
        int randomNumber = random.nextInt(randomPlan.size());

        this.plan = randomPlan.get(randomNumber);

    }

}
