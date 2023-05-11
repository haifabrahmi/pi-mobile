/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

import java.util.Date;

/**
 *
 * @author haifa
 */
public class Bus {
       private int id_bus;
   private Date depart; 
    private Date arrivee;
    private String modele;
    private int numero_de_plaque;
    private int capacite;
    private String destination;
    private String image;

    public Bus() {
    }

    public Bus(int id_bus, Date depart, Date arrivee, String modele, int numero_de_plaque, int capacite, String destination, String image) {
        this.id_bus = id_bus;
        this.depart = depart;
        this.arrivee = arrivee;
        this.modele = modele;
        this.numero_de_plaque = numero_de_plaque;
        this.capacite = capacite;
        this.destination = destination;
        this.image = image;
    }

    public Bus(Date depart, Date arrivee, String modele, int numero_de_plaque, int capacite, String destination, String image) {
        this.depart = depart;
        this.arrivee = arrivee;
        this.modele = modele;
        this.numero_de_plaque = numero_de_plaque;
        this.capacite = capacite;
        this.destination = destination;
        this.image = image;
    }

    public int getId_bus() {
        return id_bus;
    }

    public Date getDepart() {
        return depart;
    }

    public Date getArrivee() {
        return arrivee;
    }

    public String getModele() {
        return modele;
    }

    public int getNumero_de_plaque() {
        return numero_de_plaque;
    }

    public int getCapacite() {
        return capacite;
    }

    public String getDestination() {
        return destination;
    }

    public String getImage() {
        return image;
    }

    public void setId_bus(int id_bus) {
        this.id_bus = id_bus;
    }

    public void setDepart(Date depart) {
        this.depart = depart;
    }

    public void setArrivee(Date arrivee) {
        this.arrivee = arrivee;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNumero_de_plaque(int numero_de_plaque) {
        this.numero_de_plaque = numero_de_plaque;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
}
