/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.UserFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import modelo.Plan;
import modelo.User;
/**
 *
 * @author sergi
 */
@Named
@RequestScoped


public class CreatePlanController implements Serializable{
    private Plan plan;
    
    @EJB
    private UserFacadeLocal userEJB;
    
    @PostConstruct
    public void init(){
        this.plan = new Plan();
    }


    
}