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
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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
    private UploadedFile file;
    
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
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String directory = externalContext.getInitParameter("uploadDirectory");
        Path folder = Paths.get(directory);
        String filename = FilenameUtils.getBaseName(uploadedFile.getFileName()); 
        String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
        try (InputStream input = uploadedFile.getInputStream()) {
            File file = File.createTempFile(filename + "-","." + extension, new File(directory));
            Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Uploaded file successfully saved in " + file.toPath());
        }catch(Exception e){
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar la imagen"+e.getMessage()));
             System.out.println(e.getMessage());
        }
    }
   
}