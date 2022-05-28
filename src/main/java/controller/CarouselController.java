/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.PlanFacadeLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import modelo.Plan;
import org.primefaces.model.ResponsiveOption;
/**
 *
 * @author sergi
 */
@Named
@ViewScoped



public class CarouselController implements Serializable{
    private List<Plan> plans;
    private List<ResponsiveOption> responsiveOptions;

    
    @EJB
    private PlanFacadeLocal planEJB;
    

    
    @PostConstruct
    public void init() {
        plans = planEJB.getPlanesPopulares();
        responsiveOptions = new ArrayList<>();
        responsiveOptions.add(new ResponsiveOption("1024px", 3, 3));
        responsiveOptions.add(new ResponsiveOption("768px", 2, 2));
        responsiveOptions.add(new ResponsiveOption("560px", 1, 1));
    }
    public List<Plan> getPlans(){
        return this.plans;
    }
    
    public List<ResponsiveOption> getResponsiveOptions() {
        return responsiveOptions;
    }

    public void setResponsiveOptions(List<ResponsiveOption> responsiveOptions) {
        this.responsiveOptions = responsiveOptions;
    }
   
}