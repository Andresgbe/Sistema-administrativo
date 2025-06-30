/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Repository;

import com.mycompany.sistema_administrativo.Model.Products;
import java.util.List;

public interface ProductRepository {
    void addProductToDatabase(Products p);
    void updateProductInDatabase(Products p);
    void deleteProductFromDatabase(String id);
    boolean productCodeExist(String code);
    List<Products> loadProductsFromDatabase();
}