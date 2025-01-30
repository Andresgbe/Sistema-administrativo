/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Model;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author andresgbe
 */
public class Users {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String role;
    
    //Constructor
    public Users(String id, String name, String email, String phone, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }
    
     public Users(String name, String email, String phone, String password, String role) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    //getters & setters
    public String getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public String getRole(){
        return role;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public boolean ValidatePassword(String inputPassword) {
        return BCrypt.checkpw(inputPassword, this.password); // Comparar usando BCrypt
    }
    @Override
    public String toString() {
        return "Users{" +
                "username='" + name + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }   
}



