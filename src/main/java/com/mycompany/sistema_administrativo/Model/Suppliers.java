/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Model;

/**
 *
 * @author andresgbe
 */
public class Suppliers {  
    private String id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private int rif;
    
    //Constructor
    public Suppliers(String id, String name, String phone, String email, String address, int rif){
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.rif = rif;
    }
    
    public Suppliers(String name, String phone, String email, String address, int rif){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.rif = rif;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRif() {
        return rif;
    }

    public void setRif(int rif) {
        this.rif = rif;
    }
    
   
@Override
public String toString() {
    return name; // Esto hará que JComboBox solo muestre el nombre
}


}
