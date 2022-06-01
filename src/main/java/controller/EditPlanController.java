/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.FavouriteFacadeLocal;
import EJB.PlanFacadeLocal;
import EJB.ReviewFacade;
import EJB.ReviewFacadeLocal;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import modelo.Client;
import modelo.Favourite;
import modelo.Plan;
import modelo.Review;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;

/**
 *
 * @author sergi
 */
@Named
@ViewScoped

public class EditPlanController implements Serializable {

    private Plan plan;

    @EJB
    private PlanFacadeLocal planEJB;

    @PostConstruct
    public void init() {
        this.plan = (Plan) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("plan");

    }

    public String getImage() {

        if (this.plan.getImage() == null) {
            return "adventureClouds.jpg";
        } else {
            return this.plan.getImage();
        }
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void editPlan() throws Exception {     
        this.plan.setCity(this.plan.getCity().toUpperCase());
        if (this.plan.getImage() == null) {
            this.plan.setImage("adventureClouds.jpg");
        }
        planEJB.edit(plan);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/planazzo/faces/privado/administrador/editPlan.xhtml");
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PLAN CREADO CORRECTAMENTE"));
    }
    
       public void saveImage(FileUploadEvent event){
       
        UploadedFile uploadedFile = event.getFile();
        
        String aux = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/images").replace("\\", "\\\\");
        
        String src = aux.replace("\\\\target\\\\INSO-1.0-SNAPSHOT", "\\\\src\\\\main\\\\webapp");
        Path folder = Paths.get(src);
        
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");  
        LocalDateTime now = LocalDateTime.now(); 
        String filename = dtf.format(now);
        try (InputStream input = uploadedFile.getInputStream()) {
            Path filee = Files.createTempFile(folder, filename + "-", "." + extension);
            Files.copy(input, filee, StandardCopyOption.REPLACE_EXISTING);
            this.plan.setImage(filee.getFileName().toString());
        }catch(Exception e){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar la imagen"+e.getMessage()));
             System.out.println(e.getMessage());
        }
    }
}
