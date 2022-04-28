/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EJB;

import java.util.List;
import javax.ejb.Local;
import modelo.Listplan;

/**
 *
 * @author santy
 */
@Local
public interface ListplanFacadeLocal {

    void create(Listplan listplan);

    void edit(Listplan listplan);

    void remove(Listplan listplan);

    Listplan find(Object id);

    List<Listplan> findAll();

    List<Listplan> findRange(int[] range);

    int count();
    
}
