/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.ManageProductsView;
import com.mycompany.sistema_administrativo.View.AddProductsView;
import com.mycompany.sistema_administrativo.Model.Products;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageProductsController {
    private ManageProductsView manageProductsView;
    private List<Products> products = new ArrayList<>();

    public ManageProductsController(ManageProductsView manageProductsView) {
        this.manageProductsView = manageProductsView;
        System.out.println("🔹 ManageProductsController inicializado.");
        configureListeners();
        loadProductsFromDatabase();
    }

    private void configureListeners() {
        System.out.println("🔹 Configurando listeners en ManageProductsView...");

        manageProductsView.getAddButton().addActionListener(e -> {
            System.out.println("🟢 Botón 'Agregar Producto' presionado.");
            openAddProductView();
        });

        manageProductsView.getDeleteButton().addActionListener(e -> {
            System.out.println("🟠 Botón 'Eliminar Producto' presionado.");
            deleteProduct();
        });
    }

    private void loadProductsFromDatabase() {
    System.out.println("🔹 Cargando productos desde la base de datos...");
    
    // 🔥 Corrección: Incluir 'code' en la consulta SQL
    String query = "SELECT id, code, name, description, price, stock FROM products";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {
        
        products.clear();
        List<Object[]> productList = new ArrayList<>();
        
        while (resultSet.next()) {
            Products product = new Products(
                    resultSet.getInt("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("stock")
            );
            products.add(product);
            productList.add(new Object[]{
                    product.getId(),
                    product.getCode(),  // 🔥 Incluir 'code' en la tabla
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStock()
            });
        }
        
        manageProductsView.loadProducts(productList.toArray(new Object[0][0]));
        System.out.println("✅ Productos cargados correctamente.");
        
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al cargar los productos de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void openAddProductView() {
        System.out.println("🟢 Abriendo AddProductsView...");
        AddProductsView addProductsView = new AddProductsView(manageProductsView, this);
        addProductsView.setVisible(true);
    }

public void addProductToDatabase(String code, String name, String description, double price, int stock) {
    System.out.println("🔹 Agregando producto a la base de datos...");
    System.out.println("➡️ Código: " + code);
    System.out.println("➡️ Nombre: " + name);
    System.out.println("➡️ Descripción: " + description);
    System.out.println("➡️ Precio: " + price);
    System.out.println("➡️ Stock: " + stock);

    // 🔥 CORRECCIÓN: Ahora también insertamos "code" en la base de datos
    String query = "INSERT INTO products (code, name, description, price, stock) VALUES (?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, code);
        statement.setString(2, name);
        statement.setString(3, description);
        statement.setDouble(4, price);
        statement.setInt(5, stock);

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("✅ Producto agregado con éxito.");
            JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadProductsFromDatabase();
        } else {
            System.out.println("❌ No se pudo agregar el producto.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al agregar el producto a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    private void deleteProduct() {
        int selectedRow = manageProductsView.getProductsTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageProductsView, "Selecciona un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String productId = manageProductsView.getProductsTable().getValueAt(selectedRow, 0).toString();
        System.out.println("🟠 Eliminando producto con ID: " + productId);

        String query = "DELETE FROM products WHERE id = ?"; // 🔥 Cambio aquí
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Producto eliminado correctamente.");
                JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadProductsFromDatabase();
            } else {
                System.out.println("❌ No se pudo eliminar el producto.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

