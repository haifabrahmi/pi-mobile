/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author user
 */
public class reclamation {
    private int id_reclamation ;
    private String categorie_reclamation ;
    private String objet_reclamation ;
    private String description_reclamation ;
    private int etat_reclamation ;
    private Date date_reclamation;

    

    public Date getDate_reclamation() {
        return date_reclamation;
    }

    public void setDate_reclamation(Date date_reclamation) {
        this.date_reclamation = date_reclamation;
    }

    public reclamation(int id_reclamation, String categorie_reclamation, String objet_reclamation, String description_reclamation, int etat_reclamation, Date date_reclamation) {
        this.id_reclamation = id_reclamation;
        this.categorie_reclamation = categorie_reclamation;
        this.objet_reclamation = objet_reclamation;
        this.description_reclamation = description_reclamation;
        this.etat_reclamation = etat_reclamation;
        this.date_reclamation = date_reclamation;
    }

    public reclamation() {
    }

    public reclamation(String categorie_reclamation, String objet_reclamation, String description_reclamation, int etat_reclamation, Date date_reclamation) {
        this.categorie_reclamation = categorie_reclamation;
        this.objet_reclamation = objet_reclamation;
        this.description_reclamation = description_reclamation;
        this.etat_reclamation = etat_reclamation;
        this.date_reclamation = date_reclamation;
    }

    public int getId_reclamation() {
        return id_reclamation;
    }

    public String getCategorie_reclamation() {
        return categorie_reclamation;
    }

    public String getObjet_reclamation() {
        return objet_reclamation;
    }

    public String getDescription_reclamation() {
        return description_reclamation;
    }

    public int getEtat_reclamation() {
        return etat_reclamation;
    }

    public void setId_reclamation(int id_reclamation) {
        this.id_reclamation = id_reclamation;
    }

    public void setCategorie_reclamation(String categorie_reclamation) {
        this.categorie_reclamation = categorie_reclamation;
    }

    public void setObjet_reclamation(String objet_reclamation) {
        this.objet_reclamation = objet_reclamation;
    }

    public void setDescription_reclamation(String description_reclamation) {
        this.description_reclamation = description_reclamation;
    }

    public void setEtat_reclamation(int etat_reclamation) {
        this.etat_reclamation = etat_reclamation;
    }

    @Override
    public String toString() {
        return "reclamation{" + "id_reclamation=" + id_reclamation + ", categorie_reclamation=" + categorie_reclamation + ", objet_reclamation=" + objet_reclamation + ", description_reclamation=" + description_reclamation + ", etat_reclamation=" + etat_reclamation + ", date_reclamation=" + date_reclamation + '}';
    }

        
    
    
}
