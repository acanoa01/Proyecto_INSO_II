/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Client;
import modelo.Favourite;
import modelo.Plan;


/**
 *
 * @author santy
 */
@Local
public interface FavouriteFacadeLocal {

    void create(Favourite favourite);

    void edit(Favourite favourite);

    void remove(Favourite favourite);

    Favourite find(Object id);

    List<Favourite> findAll();

    List<Favourite> findRange(int[] range);

    int count();
    
    boolean favouriteExists(Favourite favourite);
    
    List<Plan> getFavoritos(Client cliente);
}
