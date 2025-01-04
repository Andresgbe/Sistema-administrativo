/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Model;

/**
 *
 * @author andresgbe
 */
public class Users {
    private String id;
    private String name;
    private String mail;
    private String phone;
    private String password;
    private String role;
    
    //Constructor
    public  Users(String id, String name, String mail, String phone, String password, String role){
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.phone = phone;
        this.password = password;
        this.role = role;   
}

    //getters & setters
    public String getId(){
        return id;
    }
    
    public void setId(String id){
        this.id = id;
    }
    
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getMail(){
        return mail;
    }
    
    public void setMail(String mail){
        this.mail = mail;
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
    
    public boolean ValidatePassword(String inputPassword){
        return this.password.equals(inputPassword);
    }
    
    @Override
    public String toString() {
        return "Users{" +
                "username='" + name + '\'' +
                ", role='" + role + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + mail + '\'' +
                '}';
    }   
}

