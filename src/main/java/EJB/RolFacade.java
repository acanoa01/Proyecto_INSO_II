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
import modelo.Rol;
import modelo.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Alberto
 */
@Stateless
public class RolFacade extends AbstractFacade<Rol> implements RolFacadeLocal {

    @PersistenceContext(unitName = "PlanazzoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RolFacade() {
        super(Rol.class);
    }
    
    @Override
    public Rol getRol(String type) {
        Rol rol = null;
        String consulta = "FROM Rol r WHERE r.userType=:param1";
        Query query = em.createQuery(consulta);

        query.setParameter("param1", type);

        List<Rol> rols = query.getResultList();

        if (rols.isEmpty() || rols.size() != 1) {
            return null;
        } else {
            rol = rols.get(0);
            return rol;
        }
    }
    
    
}