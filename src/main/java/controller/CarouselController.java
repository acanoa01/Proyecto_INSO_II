/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.PlanFacadeLocal;
import EJB.RolFacadeLocal;
import java.nio.file.Files;
import java.io.InputStream;
import java.io.Serializable;
import static java.lang.Thread.sleep;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import modelo.Client;
import modelo.Plan;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.ResponsiveOption;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
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