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
        System.out.println(user.getEmail());

        //Recuperar el usuario asociado
        String consultaUsuario = "FROM User u WHERE u.email=:param1";
        Query queryUsuario = em.createQuery(consultaUsuario);
        queryUsuario.setParameter("param1", user.getEmail());

        List<User> users = queryUsuario.getResultList();
        if (users.isEmpty() || users.size() != 1) {
            return null;
        } else {
            User associatedUser = users.get(0);

            //Recuperar el cliente
            Client client = new Client();
            client.setUser(associatedUser);
            
            return client;
            
//            System.out.println(associatedUser.getUserID());
//            String consultaCliente = "FROM Client c WHERE c.user=:param1";
//            Query queryCliente = em.createQuery(consultaCliente);
//            queryUsuario.setParameter("param1", associatedUser);
//
//            List<Client> clients = queryCliente.getResultList();
//
//            if (clients.isEmpty() || clients.size() != 1) {
//                return null;
//            } else {
//                return clients.get(0);
//            }
        }
    }
}
