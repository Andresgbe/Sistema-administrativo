/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Model;

/**
 *
 * @author andresgbe
 */
public class Clients {
    // Atributos de los clientes
    private String id;
    private String name;
    private String identification;
    private String email;
    private String phone;
    private String address; 
    
    // Constructor que se usa cuando se recuperan clientes de la base de datos
    public Clients(String id, String name, String identification, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.identification = identification;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Constructor para agregar un nuevo cliente
    public Clients(String name, String identification, String email, String phone, String address) {
        this.name = name;
        this.identification = identification;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Métodos Getters y Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getIdentification() {
        return identification;
    }
    
    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Sobreescribe el método toString() para mostrar la información de una forma más legible
    @Override
    public String toString() {
        return "Clients{" +
                "name='" + name + '\'' +
                ", identification='" + identification + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}