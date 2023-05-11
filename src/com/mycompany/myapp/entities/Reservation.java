/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.myapp.entities;
import java.util.Date; 

/**
 *
 * @author Theto
 */

public class Reservation {
    private int id_res;
    public String date_res;
    private String heure_res;
    private int nb_place;
    private String type_ticket;    
    private float prix;
    private float prix_totale;

    
    
    public Reservation() {
    }

    // Constructor
    public Reservation(int id_res, String date_res, String heure_res, String Type_ticket, float prix, int nb_place, float prix_totale) {
        this.id_res = id_res;
        this.date_res = date_res;
        this.heure_res = heure_res;
        this.nb_place = nb_place;
        this.prix = prix;
        this.prix_totale = prix_totale;
        this.type_ticket = Type_ticket;
       
    }

    public Reservation(int id_res, String type_ticket, int nb_place, Date date_res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId_res() {
        return id_res;
    }

    public void setId_res(int id_res) {
        this.id_res = id_res;
    }

    public String getDate_res() {
        return date_res;
    }

    public void setDate_res(String date_res) {
        this.date_res = date_res;
    }

    public String getHeure_res() {
        return heure_res;
    }

    public void setHeure_res(String heure_res) {
        this.heure_res = heure_res;
    }

    public int getNb_place() {
        return nb_place;
    }

    public void setNb_place(int nb_place) {
        this.nb_place = nb_place;
    }

    public String getType_ticket() {
        return type_ticket;
    }

    public void setType_ticket(String type_ticket) {
        this.type_ticket = type_ticket;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrix_totale() {
        return prix_totale;
    }

    public void setPrix_totale(float prix_totale) {
        this.prix_totale = prix_totale;
    }

  

    @Override
    public String toString() {
        return "Reservation{" + "id_res=" + id_res + ", date_res=" + date_res + ", heure_res=" + heure_res + ", nb_place=" + nb_place + ", type_ticket=" + type_ticket + ", prix=" + prix + ", prix_totale=" + prix_totale +  '}';
    }

    

    public void setImmatriculation(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setImmat(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setnbplace(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}