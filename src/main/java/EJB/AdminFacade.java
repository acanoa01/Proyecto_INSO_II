/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Admin;
import modelo.Client;
import modelo.User;

/**
 *
 * @author santy
 */
@Stateless
public class AdminFacade extends AbstractFacade<Admin> implements AdminFacadeLocal {

    @PersistenceContext(unitName = "PlanazzoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminFacade() {
        super(Admin.class);
    }

    @Override
    public Admin getAdmin(User user) {

        String consultaAdmin = "FROM Admin a WHERE a.user=:param1";
        Query queryCliente = em.createQuery(consultaAdmin);
        queryCliente.setParameter("param1", user);

        List<Admin> admins = queryCliente.getResultList();

        if (admins.isEmpty() || admins.size() != 1) {
            return null;
        } else {
            System.out.println("ESTOY EN GETADMIN CON EL ADMIN: " + admins.get(0).getUser().getUserName());
            return admins.get(0);
        }

    }

}
