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
import modelo.Client;
import modelo.Rol;
import modelo.User;
import static org.primefaces.component.inputmask.InputMaskBase.PropertyKeys.type;

/**
 *
 * @author santy
 */
@Stateless
public class ClientFacade extends AbstractFacade<Client> implements ClientFacadeLocal {

    @PersistenceContext(unitName = "PlanazzoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }
    
    @Override
    public Client getClient(User user) {
        String consulta = "SELECT c FROM Client a WHERE c.name=:param1";
        Query query = em.createQuery(consulta);

        query.setParameter("param1", user.getName());
        
        

        List<Client> clients = query.getResultList();

        if (clients.isEmpty() || clients.size() != 1) {
            return null;
        } else {
            System.out.println(clients.get(0).getUser().getUserID());
//             return clients.get(0);
        }
        return null;
    }
}
