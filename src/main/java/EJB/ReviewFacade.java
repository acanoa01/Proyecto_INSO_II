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
import modelo.Plan;
import modelo.Review;

/**
 *
 * @author santy
 */
@Stateless
public class ReviewFacade extends AbstractFacade<Review> implements ReviewFacadeLocal {

    @PersistenceContext(unitName = "PlanazzoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReviewFacade() {
        super(Review.class);
    }
    
    @Override
    public List<Review> getReviews(Plan plan) {
        String consulta = "FROM Review r WHERE r.plan=:param1";
        
        Query query = em.createQuery(consulta);
        query.setParameter("param1", plan);
        List<Review> reviews = query.getResultList();

        return reviews;
    }
    
}
