/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Model;

/**
 *
 * @author andresgbe
 */
public class Transactions {
    private String id;
    private int customerID;
    private String transactionType; // "compra interna" o "venta externa"
    private int productID;
    private int quantity;
    private String transactionDate;
    private float totalAmount;

    // Constructor para cuando se recupera desde la base de datos (incluye id)
    public Transactions(String id, int customerID, String transactionType, int productID, int quantity, String transactionDate, float totalAmount) {
        this.id = id;
        this.customerID = customerID;
        this.transactionType = transactionType;
        this.productID = productID;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.totalAmount = totalAmount;
    }

    // Constructor para cuando se va a insertar (sin id)
    public Transactions(int customerID, String transactionType, int productID, int quantity, String transactionDate, float totalAmount) {
        this.customerID = customerID;
        this.transactionType = transactionType;
        this.productID = productID;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
        this.totalAmount = totalAmount;
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public int getProductID() {
        return productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    // Setters
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Transactions{" +
                "id='" + id + '\'' +
                ", customerID=" + customerID +
                ", transactionType='" + transactionType + '\'' +
                ", productID=" + productID +
                ", quantity=" + quantity +
                ", transactionDate='" + transactionDate + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}