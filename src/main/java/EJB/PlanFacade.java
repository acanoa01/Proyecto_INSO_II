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
import modelo.Plan;

/**
 *
 * @author santy
 */
@Stateless
public class PlanFacade extends AbstractFacade<Plan> implements PlanFacadeLocal {

    @PersistenceContext(unitName = "PlanazzoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlanFacade() {
        super(Plan.class);
    }
    
    @Override
    public void validatePlan(Plan plan){
//        String consultaPlan = "FROM Admin a WHERE a.user=:param1";
//        Query queryCliente = em.createQuery(consultaAdmin);
//        queryCliente.setParameter("param1", user);
//
//        List<Admin> admins = queryCliente.getResultList();
//
//        if (admins.isEmpty() || admins.size() != 1) {
//            return null;
//        } else {
//            System.out.println("ESTOY EN GETADMIN CON EL ADMIN: " + admins.get(0).getUser().getUserName());
//            return admins.get(0);
//        }

    }
    
}
