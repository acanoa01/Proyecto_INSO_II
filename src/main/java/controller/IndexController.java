package controller;

import EJB.UserFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.User;

/**
 *
 * @author Alberto
 */
@Named
@ViewScoped
public class IndexController implements Serializable {

    private User user;

    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void init() {
        user = new User();
    }

    public String moveToLogin() {
        return "login";
    }

    public String moveToRegister() {
        return "register";
    }

    public void moveToHome() {
        LoginController controller = new LoginController();
        controller.verificarUsuario();
        
    }
//    //Crear usuario
//    public void createUser() {
//
//        try {
//
//            userEJB.create(user);
//
//        } catch (Exception e) {
//        
//            System.out.println("Error al crear el usuario en la base de datos " + e.getMessage());
//        }
//    }
//
//    //iniciar sesion de usuario
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

}
