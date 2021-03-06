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
import modelo.User;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Alberto
 */
@Stateless
public class UserFacade extends AbstractFacade<User> implements UserFacadeLocal {

    @PersistenceContext(unitName = "PlanazzoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    @Override
    public User verificarUsuario(User user) {

        User usuarioVerificado = null;
        String consulta = "FROM User u WHERE u.userName=:param1";
        Query query = em.createQuery(consulta);
        query.setParameter("param1", user.getUserName());
        List<User> usersFound = query.getResultList();

        if (usersFound.isEmpty()) {
            return null;
        } else {
            usuarioVerificado = usersFound.get(0);

            if (BCrypt.checkpw(user.getPassword(), usuarioVerificado.getPassword())) {
                return usuarioVerificado;
            } else {
                return null;
            }
        }
    }

}
