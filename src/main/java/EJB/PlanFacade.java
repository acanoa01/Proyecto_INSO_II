/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
    public List<Plan> getPlanesPopulares(){
        
        String consultaPlan = "FROM Plan p WHERE p.verified=1 order by p.likes desc";
        Query queryCliente = em.createQuery(consultaPlan);

        queryCliente.setMaxResults(5);
        
        List<Plan> plans = queryCliente.getResultList();
        return plans;
    }
    
    @Override
    public Plan getRandomPlan(Plan plan){
        String consultaPlan = "FROM Plan p WHERE p.city=:param1 and p.companion=:param2 and p.price=:param3 and p.age=:param4 and p.type=:param5 and p.verified=1";
        Query queryPlan = em.createQuery(consultaPlan);
        queryPlan.setParameter("param1", plan.getCity());
        queryPlan.setParameter("param2", plan.getCompanion());
        queryPlan.setParameter("param3", plan.getPrice());
        queryPlan.setParameter("param4", plan.getAge());
        queryPlan.setParameter("param5", plan.getType());
        
        List<Plan> plans = queryPlan.getResultList();
        if(!plans.isEmpty()){
            int randomNum = ThreadLocalRandom.current().nextInt(0, plans.size());
            return plans.get(randomNum);
        }else{
            return null;
        }
    }
    
    @Override
    public List<Plan> getNotValidated(){
        String consultaPlan = "FROM Plan p WHERE p.verified=0";
        Query queryPlan = em.createQuery(consultaPlan);
        
        List<Plan> plans = queryPlan.getResultList();
        return plans;
    }
}
