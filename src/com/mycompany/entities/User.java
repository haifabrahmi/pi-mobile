/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

/**
 *
 * @author msi
 */
public class User {
    
    private int idUser;
    private String nom, prenom, num, email,password,role,token;
    private boolean verified;

    public int getIdUser() {
        return idUser;
    }

    public User(int idUser, String nom, String prenom, String num, String email, String password, String role, String token, boolean verified) {
        this.idUser = idUser;
        this.nom = nom;
        this.prenom = prenom;
        this.num = num;
        this.email = email;
        this.password = password;
        this.role = role;
        this.token = token;
        this.verified = verified;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" + "idUser=" + idUser + ", nom=" + nom + ", prenom=" + prenom + ", num=" + num + ", email=" + email + ", password=" + password + ", role=" + role + ", token=" + token + ", verified=" + verified + '}';
    }

    public User(String nom, String prenom, String num, String email, String password, String role, String token, boolean verified) {
        this.nom = nom;
        this.prenom = prenom;
        this.num = num;
        this.email = email;
        this.password = password;
        this.role = role;
        this.token = token;
        this.verified = verified;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    
    
}
