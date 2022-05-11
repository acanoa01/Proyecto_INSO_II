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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import modelo.Client;
import modelo.Plan;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.primefaces.shaded.commons.io.FilenameUtils;
/**
 *
 * @author sergi
 */
@Named
@RequestScoped



public class CreatePlanController implements Serializable{
    private Plan plan;
    private Client client;

    
    @EJB
    private PlanFacadeLocal planEJB;
    
    @EJB
    RolFacadeLocal rolEJB;

    
    @PostConstruct
    public void init(){
        this.plan = new Plan();
        this.client = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
    
    public String insertPlan(){
        this.client = (Client)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
        if(this.client != null){
            this.plan.setVerified(false);
            this.plan.setClient(this.client);
            planEJB.create(plan);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PLAN CREADO CORRECTAMENTE"));
            return "faces/createPlan.xhtml";
        }else{
            return "faces/.";
        }
        
    }
    
    public void saveImage(FileUploadEvent event){
       
        UploadedFile uploadedFile = event.getFile();
        
        Path folder = Paths.get("D:\\images");
        
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");  
        LocalDateTime now = LocalDateTime.now(); 
        String filename = dtf.format(now);
        try (InputStream input = uploadedFile.getInputStream()) {
            Path filee = Files.createTempFile(folder, filename + "-", "." + extension);
            Files.copy(input, filee, StandardCopyOption.REPLACE_EXISTING);
            this.plan.setImage(filee.toString());
        }catch(Exception e){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar la imagen"+e.getMessage()));
             System.out.println(e.getMessage());
        }
    }
   
}