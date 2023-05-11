/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entities;

/**
 *
 * @author asus
 */
public class User {

public int idUser;
public String nom;

    public User() {
    }

    public User(int idUser, String nom) {
        this.idUser = idUser;
        this.nom = nom;
    }

    public User(String nom) {
        this.nom = nom;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", nom=" + nom + '}';
    }

 
 
 
  
 
    

}
