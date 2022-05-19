/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.org.glassfish.gmbal.NameValue;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.bean.ManagedProperty;

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

        return "verifyPlan.xhtml";
    }

//    public String showPage() {
//        System.out.println(this.pageId);
//        if (null == pageId) {
//            return "privado/administrador/index";
//        } else {
//            switch (pageId) {
//                case "verify":
//                    return "/WEB-INF/privado/administrador/index";
//                case "edit":
//                    
//                case "create":
//                    return "/WEB-INF/privado/administrador/createPlan";
//                default:
//                    break;
//            }
//        }
//        return "/WEB-INF/privado/administrador/index";
//
//    }
}
