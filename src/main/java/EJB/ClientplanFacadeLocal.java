/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Clientplan;

/**
 *
 * @author santy
 */
@Local
public interface ClientplanFacadeLocal {

    void create(Clientplan clientplan);

    void edit(Clientplan clientplan);

    void remove(Clientplan clientplan);

    Clientplan find(Object id);

    List<Clientplan> findAll();

    List<Clientplan> findRange(int[] range);

    int count();
    
}
