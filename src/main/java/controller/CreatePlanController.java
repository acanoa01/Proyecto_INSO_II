/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import EJB.PlanFacadeLocal;
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
import javax.faces.view.ViewScoped;
import modelo.Admin;
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
@ViewScoped

public class CreatePlanController implements Serializable {

    private Plan plan;
    private Client client;

    @EJB
    private PlanFacadeLocal planEJB;

    @PostConstruct
    public void init() {
        this.plan = new Plan();
        this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public void insertPlan(boolean verified) throws Exception {
        this.client = (Client) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cliente");
        Admin admin = (Admin) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("admin");

        this.plan.setVerified(verified);
        this.plan.setCity(this.plan.getCity().toUpperCase());
        this.plan.setClient(this.client);
        this.plan.setAdmin(admin);
        if (this.plan.getImage() == null) {
            this.plan.setImage("adventureClouds.jpg");
        }
        planEJB.create(plan);
        if (verified) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/planazzo/faces/privado/administrador/index.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/planazzo/faces/privado/cliente/index.xhtml");
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "PLAN CREADO CORRECTAMENTE"));
    }

    public void saveImage(FileUploadEvent event) {

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
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar la imagen" + e.getMessage()));
            System.out.println(e.getMessage());
        }
    }

    public String getImage() {

        if (plan.getImage() == null) {
            return "adventureClouds.jpg";
        } else {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
            }
            return plan.getImage();
        }
    }

}
