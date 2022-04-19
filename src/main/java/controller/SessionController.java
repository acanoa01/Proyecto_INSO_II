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

public class SessionController implements Serializable {

    private User user;

    @EJB
    private UserFacadeLocal userEJB;

    @PostConstruct
    public void init() {
        user = new User();
    }

    //Crear usuario
    public void createUser() {
        Rol rol = new Rol();
        rol.setRolID("1");
        user.setRol(rol);
        try {

            userEJB.create(user);

        } catch (Exception e) {
            System.out.println("Error al crear el usuario en la base de datos " + user.toString());
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
