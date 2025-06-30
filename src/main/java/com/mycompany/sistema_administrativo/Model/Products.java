/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Model;

/**
 *
 * @author andresgbe
 */
public class Products {
    // Atributos de los productos
    private String id;
    private String code;
    private String name;
    private String description;
    private float price;
    private int stock;  
    private String supplierId;
    private String tipoProducto;

    
    // Constructor que se usa cuando se recupera productos en la base de datos
    public Products(String id, String code, String name, String description, float price, int stock, String supplierId, String tipoProducto){
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.supplierId = supplierId;
        this.tipoProducto = tipoProducto;
    }
    
    //Constructor para agregar un nuevo producto
    public Products(String code, String name, String description, float price, int stock, String supplierId, String tipoProducto){
        this.code = code;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock; 
        this.supplierId = supplierId;
        this.tipoProducto = tipoProducto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
   
    
    // Sobreescribe el metodo toString() para mostrar la informacion de una forma mas legible
    @Override
    public String toString() {
        return "Products{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

}
