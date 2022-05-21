/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


/**
 *
 * @author Alberto
 */
@RequestScoped
@Named

public class NavigationController implements Serializable {

    public String goToCreatePlanAdmin() {

        return "createPlan.xhtml";
    }

    public String goToEditPlanAdmin() {

        return "editPlan.xhtml";
    }

    public String goToverifyPlanAdmin() {

        return "index.xhtml";
    }

}
