/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import modelo.Client;
import modelo.Clientplan;
import modelo.Favourite;
import modelo.Plan;

/**
 *
 * @author santy
 */
@Stateless
public class ClientplanFacade extends AbstractFacade<Clientplan> implements ClientplanFacadeLocal {

    @PersistenceContext(unitName = "PlanazzoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientplanFacade() {
        super(Clientplan.class);
    }
    
    @Override
    public boolean clientplanExists(Clientplan clientplan){
        String consulta = "FROM Clientplan c WHERE c.client=:param1 and c.plan=:param2";
        Query query = em.createQuery(consulta);
        query.setParameter("param1", clientplan.getClient());
        query.setParameter("param2", clientplan.getPlan());
        List<Favourite> favourites = query.getResultList();

        return !favourites.isEmpty();
    }
    
    @Override
    public List<Plan> getPlanes(Client cliente){
        String consulta = "FROM Clientplan c WHERE c.client=:param1";
        Query query = em.createQuery(consulta);
        query.setParameter("param1", cliente);
        List<Clientplan> plans = query.getResultList();
        List<Plan> planes = new ArrayList<Plan>();
        for(int i =0; i<plans.size(); i++){
            planes.add(plans.get(i).getPlan());
        }
        return planes;
    }
}