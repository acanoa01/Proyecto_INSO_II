/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.AdminFacadeLocal;
import EJB.ClientFacadeLocal;
import EJB.ClientplanFacadeLocal;
import EJB.FavouriteFacadeLocal;
import EJB.PlanFacadeLocal;
import EJB.RolFacadeLocal;
import EJB.UserFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import static java.lang.Thread.sleep;
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
import modelo.Clientplan;
import modelo.Favourite;
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
    private Plan randomPlan;
    
    @EJB
    private UserFacadeLocal userEJB;

    @EJB
    private ClientFacadeLocal clientEJB;
    
    @EJB
    private AdminFacadeLocal adminEJB;

    @EJB
    private RolFacadeLocal rolEJB;

    @EJB
    private PlanFacadeLocal planEJB;
    
    @EJB
    private ClientplanFacadeLocal clientPlanEJB;
    
    @EJB
    private FavouriteFacadeLocal favouriteEJB;

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
            System.out.println("ROL DE USUARIO QUE HA INICIADO SESIÓN: " + checkUser.getRol().getUserType());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario", checkUser);
            if (checkUser.getRol().getUserType().equals("A")) {
                this.admin = adminEJB.getAdmin(checkUser);
                System.out.println("ADMINISTRADOR OBTENIDO: " + this.admin.getUser().getUserName());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("admin", this.admin);
                doRedirect("privado/administrador/index.xhtml");
            } else {
                this.client = clientEJB.getClient(checkUser);
                System.out.println("CLIENTE OBTENIDO: " + this.client.getUser().getUserName());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cliente", this.client);
                doRedirect("privado/cliente/index.xhtml");
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
  
    public Plan getRandomPlan() {
        return randomPlan;
    }

    public void setRandomPlan(Plan plan) {
        this.randomPlan = plan;
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
        System.out.println("VERIFICANDO SI EL CLIENTE "  + this.client.getClientID() + " HA INICIADO SESIÓN...");
        
        if (this.client == null || !(this.client.getUser().getRol().getUserType().equals("C")) ) {
            doRedirect("index.xhtml");
        }else{
            System.out.println("EL CLIENTE "  + this.client.getUser().getUserName() + " HA INICIADO SESIÓN...");
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

    public void searchRandomPlan() {
        if(this.plan != null && this.plan.getCity() != null){
            this.plan.setCity(this.plan.getCity().toUpperCase());
        }
        this.randomPlan = planEJB.getRandomPlan(this.plan);
        if(this.randomPlan == null){
            FacesContext.getCurrentInstance().addMessage("planMsg", new FacesMessage(FacesMessage.SEVERITY_INFO, "Lo sentimos, no hemos encontrado ningun plan para ti =(", ""));
        }
    }
    
    public String getImage(){
        
        if(randomPlan == null){
            return "placeHolder.png";
        }else{
            return randomPlan.getImage();
        }
    }

    public void alertarRegistro(){
        FacesContext.getCurrentInstance().addMessage("carouselAlert", new FacesMessage(FacesMessage.SEVERITY_WARN, "Registrate para acceder a todas las funciones", ""));
    }
    
    public void aceptarPlan(){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("plan", this.randomPlan);
        Clientplan clientPlan = new Clientplan();
        Client cliente = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente"); 
        clientPlan.setClient(cliente);
        clientPlan.setPlan(this.randomPlan);
        if(!clientPlanEJB.clientplanExists(clientPlan)){
            clientPlanEJB.create(clientPlan);
        }
        doRedirect("/planazzo/faces/privado/cliente/planView.xhtml");
    }
    
    public void verPlan(Plan plan){
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("plan", plan);   
        doRedirect("/planazzo/faces/privado/cliente/planView.xhtml");
    }
    
   public void agregarFavoritos(Plan plan){  
        Favourite favourite = new Favourite();
        Client cliente = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente"); 
        favourite.setClient(cliente);
        favourite.setPlan(plan);
        if(!favouriteEJB.favouriteExists(favourite)){
            favouriteEJB.create(favourite);
            plan.setLikes(plan.getLikes() + 1);
            planEJB.edit(plan);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "EL PLAN HA SIDO AGREGADO A FAVORITOS", ""));

        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ESTE PLAN YA ESTA EN TUS FAVORITOS", ""));
        }
        
    }
}
