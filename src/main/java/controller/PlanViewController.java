/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Plan;

/**
 *
 * @author sergi
 */
@Named
@ViewScoped

public class PlanViewController implements Serializable {

   
    private Plan plan;
   

    @PostConstruct
    public void init() {
        this.plan = (Plan) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("plan");
    }
    
    public String getImage(){
        
        if(this.plan.getImage() == null){
            return "placeHolder.png";
        }else{
            return this.plan.getImage();
        }
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    
    
}
