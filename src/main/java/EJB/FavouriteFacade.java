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
import modelo.Favourite;
import modelo.Plan;

/**
 *
 * @author santy
 */
@Stateless
public class FavouriteFacade extends AbstractFacade<Favourite> implements FavouriteFacadeLocal {

    @PersistenceContext(unitName = "PlanazzoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FavouriteFacade() {
        super(Favourite.class);
    }
    
    @Override
    public boolean favouriteExists(Favourite favourite){
        String consulta = "FROM Favourite f WHERE f.client=:param1 and f.plan=:param2";
        Query query = em.createQuery(consulta);
        query.setParameter("param1", favourite.getClient());
        query.setParameter("param2", favourite.getPlan());
        List<Favourite> favourites = query.getResultList();

        return !favourites.isEmpty();
    }
    
    @Override
    public List<Plan> getFavoritos(Client cliente){
        String consulta = "FROM Favourite f WHERE f.client=:param1";
        Query query = em.createQuery(consulta);
        query.setParameter("param1", cliente);
        List<Favourite> favourites = query.getResultList();
        List<Plan> favoritos = new ArrayList<Plan>();
        for(int i =0; i<favourites.size(); i++){
            favoritos.add(favourites.get(i).getPlan());
        }
        return favoritos;
    }
   
}
