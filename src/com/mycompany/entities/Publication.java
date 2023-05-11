/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

import java.util.Date;

/**
 *
 * @author asus
 */
public class Publication {
    private int idPub ,nbReaction=0,selectionner=0;
    private String texte , titre , image;
    private String datePub;
    private User user;
    public Publication() {
    }

    public Publication(int idPub, String texte, String titre, String image, String datePub, User user) {
        this.idPub = idPub;
        this.texte = texte;
        this.titre = titre;
        this.image = image;
        this.datePub = datePub;
        this.user = user;
    }

    public Publication(String texte, String titre, String image, String datePub, User user) {
        this.texte = texte;
        this.titre = titre;
        this.image = image;
        this.datePub = datePub;
        this.user = user;
    }

    
    public int getIdPub() {
        return idPub;
    }

    public void setIdPub(int idPub) {
        this.idPub = idPub;
    }

    public int getNbReaction() {
        return nbReaction;
    }

    public void setNbReaction(int nbReaction) {
        this.nbReaction = nbReaction;
    }

    public int getSelectionner() {
        return selectionner;
    }

    public void setSelectionner(int selectionner) {
        this.selectionner = selectionner;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDatePub() {
        return datePub;
    }

    public void setDatePub(String datePub) {
        this.datePub = datePub;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
                     