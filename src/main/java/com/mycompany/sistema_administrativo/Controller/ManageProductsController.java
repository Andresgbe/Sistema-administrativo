/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.ManageProductsView;
import com.mycompany.sistema_administrativo.Model.Products;
import com.mycompany.sistema_administrativo.View.EditProductView;
import com.mycompany.sistema_administrativo.View.AddProductView;
import com.mycompany.sistema_administrativo.Model.Suppliers;


import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 *
 * @author andresgbe
 */
public class ManageProductsController {
    private ManageProductsView manageProductsView;
    private List<Products> products = new ArrayList<>(); 
    private Map<String, String> supplierIdToName = new HashMap<>();
    private JComboBox<String> supplierComboBox;

    
    public ManageProductsController(ManageProductsView manageProductsView){
        this.manageProductsView = manageProductsView;
        configureListeners();
        
        loadProductsFromDatabase(); // Cargar productos en la tabla al abrir
        
        updateProductsTable();
    }
    
    private void configureListeners() {
        manageProductsView.getEditButton().addActionListener(e -> {
            int selectedRow = manageProductsView.getProductsTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageProductsView, "Selecciona un producto para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener ID del producto seleccionado
            String productId = manageProductsView.getProductsTable().getValueAt(selectedRow, 0).toString();

            // Buscar el producto en la lista
            Products productToEdit = products.stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (productToEdit == null) {
                JOptionPane.showMessageDialog(manageProductsView, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear y mostrar la ventana de edición
            EditProductView editProductView = new EditProductView(manageProductsView);
            
            List<Suppliers> supplierOptions = fetchSuppliersFromDatabase();
            editProductView.setSupplierOptions(supplierOptions);
            editProductView.setSelectedSupplier(productToEdit.getSupplierId());
            editProductView.setProductCode(productToEdit.getCode());
            editProductView.setProductName(productToEdit.getName());
            editProductView.setProductDescription(productToEdit.getDescription());
            editProductView.setProductPrice(productToEdit.getPrice());
            editProductView.setProductStock(productToEdit.getStock());

            editProductView.getSaveButton().addActionListener(event -> {
                String code = editProductView.getProductCode();
                String name = editProductView.getProductName();
                String description = editProductView.getProductDescription();
                float price = editProductView.getProductPrice();
                int stock = editProductView.getProductStock();

                String supplierId = editProductView.getSelectedSupplierId();
                
                if (code.isEmpty() || name.isEmpty() || description.isEmpty()) {
                    JOptionPane.showMessageDialog(manageProductsView, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                productToEdit.setCode(code);
                productToEdit.setName(name);
                productToEdit.setDescription(description);
                productToEdit.setPrice(price);
                productToEdit.setStock(stock);
                productToEdit.setSupplierId(supplierId);
                
            //  ---- NOTIFICA AL USUARIO QUE EL STOCK ES 0    
                if (stock == 0) {
                JOptionPane.showMessageDialog(manageProductsView,
                    "El stock de este producto es 0ó",
                    "Stock agotado",
                    JOptionPane.WARNING_MESSAGE);
            }

                
                updateProductInDatabase(productToEdit);
                editProductView.dispose();
            });

            editProductView.getCancelButton().addActionListener(event -> editProductView.dispose());

            editProductView.setVisible(true);
        });
        
       manageProductsView.getAddButton().addActionListener(e -> {
    AddProductView addProductView = new AddProductView(manageProductsView);

    // 🔁 Cargar proveedores como objetos
    List<Suppliers> supplierOptions = fetchSuppliersFromDatabase();
    addProductView.setSupplierOptions(supplierOptions);

    // Acción de guardar producto
    addProductView.getSaveButton().addActionListener(event -> {
        String code = addProductView.getProductCode();
        String name = addProductView.getProductName();
        String description = addProductView.getProductDescription();
        float price = addProductView.getProductPrice();
        int stock = addProductView.getProductStock();
        String supplierId = addProductView.getSelectedSupplierId();

    if (code.isEmpty() || name.isEmpty() || description.isEmpty()) {
        JOptionPane.showMessageDialog(manageProductsView, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (productCodeExists(code)) {
        JOptionPane.showMessageDialog(manageProductsView, "Ya existe un producto con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }


        Products newProduct = new Products(code, name, description, price, stock, supplierId);
        addProductToDatabase(newProduct);
        addProductView.dispose();
    });

    addProductView.getCancelButton().addActionListener(event -> addProductView.dispose());

    addProductView.setVisible(true);
});

        // Vinculamos el botón "Eliminar Producto"
        manageProductsView.getDeleteButton().addActionListener(e -> {
           int selectedRow = manageProductsView.getProductsTable().getSelectedRow();

           if (selectedRow == -1) {
               JOptionPane.showMessageDialog(manageProductsView, "Selecciona un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
               return;
           }

           // Obtener el ID del producto seleccionado
           String productId = manageProductsView.getProductsTable().getValueAt(selectedRow, 0).toString();
           deleteProductFromDatabase(productId); // Eliminar producto de la base de datos
       });

    }

    private void loadProductsFromDatabase() {
    supplierIdToName.clear();

    String supplierQuery = "SELECT id, name FROM suppliers";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(supplierQuery);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            supplierIdToName.put(
                resultSet.getString("id"),
                resultSet.getString("name")
            );
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al cargar los nombres de proveedores.", "Error", JOptionPane.ERROR_MESSAGE);
    }    
        
    System.out.println("🔹 Cargando productos desde la base de datos...");

    String query = "SELECT id, code, name, description, price, stock, supplier_id FROM productos";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        products.clear(); // Limpiar la lista antes de cargar los nuevos productos
        List<Object[]> productList = new ArrayList<>();

        while (resultSet.next()) {
            Products product = new Products(
                    resultSet.getString("id"),
                    resultSet.getString("code"),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getFloat("price"),
                    resultSet.getInt("stock"),
                    resultSet.getString("supplier_id")
            );

            products.add(product);
            // Agregar los datos en formato de tabla
            Object[] productRow = {
            product.getId(),
            product.getCode(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock(),
            supplierIdToName.getOrDefault(product.getSupplierId(), "Desconocido")
        };

            productList.add(productRow);
            System.out.println("🔹 Producto agregado: " + product.getName());
        }

        System.out.println("🔹 Total productos cargados: " + productList.size());
        manageProductsView.loadProducts(productList.toArray(new Object[0][0]));

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al cargar los productos de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void updateProductsTable() {
    System.out.println("🔹 Actualizando tabla de productos...");
    
    Object[][] data = new Object[products.size()][7]; //
    for (int i = 0; i < products.size(); i++) {
        Products product = products.get(i);
        data[i][0] = product.getId();
        data[i][1] = product.getCode();
        data[i][2] = product.getName();
        data[i][3] = product.getDescription();
        data[i][4] = product.getPrice();
        data[i][5] = product.getStock();
        data[i][6] = supplierIdToName.getOrDefault(product.getSupplierId(), "Desconocido");
    }
    
    manageProductsView.loadProducts(data);
}
  
    private void updateProductInDatabase(Products product) {
        String query = "UPDATE productos SET code = ?, name = ?, description = ?, price = ?, stock = ?, supplier_id = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, product.getCode());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setFloat(4, product.getPrice());
            statement.setInt(5, product.getStock());
            statement.setString(6, product.getSupplierId());
            statement.setString(7, product.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadProductsFromDatabase();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el producto en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void addProductToDatabase(Products product) {
    String query = "INSERT INTO productos (code, name, description, price, stock, supplier_id) VALUES (?, ?, ?, ?, ?, ?)";
    

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, product.getCode());
        statement.setString(2, product.getName());
        statement.setString(3, product.getDescription());
        statement.setFloat(4, product.getPrice());
        statement.setInt(5, product.getStock());
        statement.setString(6, product.getSupplierId());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadProductsFromDatabase(); // Recargar los productos
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al agregar el producto en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void deleteProductFromDatabase(String productId) {
    String query = "DELETE FROM productos WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, productId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadProductsFromDatabase(); // Recargar los productos
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private List<Suppliers> fetchSuppliersFromDatabase() {
        List<Suppliers> suppliers = new ArrayList<>();
        String query = "SELECT id, name, phone, email, address, rif FROM suppliers";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                suppliers.add(new Suppliers(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getString("address"),
                    resultSet.getInt("rif")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar proveedores.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return suppliers;
    }

    private boolean productCodeExists(String code) {
    String query = "SELECT COUNT(*) FROM productos WHERE code = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, code);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1) > 0;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al validar código de producto.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    return false;
}

}



