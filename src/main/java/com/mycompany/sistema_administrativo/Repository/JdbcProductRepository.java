/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Repository;

import com.mycompany.sistema_administrativo.Model.Products;
import com.mycompany.sistema_administrativo.Database.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class JdbcProductRepository implements ProductRepository {

    @Override
    public void addProductToDatabase(Products product) {
        String query = "INSERT INTO productos " +
                "(code, name, description, price, stock, supplier_id, tipoProducto) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getCode());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setFloat(4, product.getPrice());
            stmt.setInt(5, product.getStock());
            stmt.setString(6, product.getSupplierId());
            stmt.setString(7, product.getTipoProducto());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    String generatedId = String.valueOf(generatedKeys.getInt(1));
                    product.setId(generatedId);
                }
                JOptionPane.showMessageDialog(null,
                        "Producto agregado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "Error al agregar el producto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al agregar el producto en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void updateProductInDatabase(Products product) {
        if (product.getId() == null || product.getId().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Error: El producto no tiene un ID válido y no puede actualizarse.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String query = "UPDATE productos SET " +
                "code = ?, name = ?, description = ?, price = ?, stock = ?, " +
                "supplier_id = ?, tipoProducto = ? " +
                "WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, product.getCode());
            stmt.setString(2, product.getName());
            stmt.setString(3, product.getDescription());
            stmt.setFloat(4, product.getPrice());
            stmt.setInt(5, product.getStock());
            stmt.setString(6, product.getSupplierId());
            stmt.setString(7, product.getTipoProducto());
            stmt.setString(8, product.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null,
                        "Producto actualizado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se encontró el producto para actualizar.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al actualizar el producto en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteProductFromDatabase(String productId) {
        String query = "DELETE FROM productos WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, productId);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null,
                        "Producto eliminado exitosamente.",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se pudo eliminar el producto.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al eliminar el producto en la base de datos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean productCodeExist(String code) {
        String query = "SELECT COUNT(*) FROM productos WHERE code = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error al validar código de producto.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    @Override
    public List<Products> loadProductsFromDatabase() {
        List<Products> products = new ArrayList<>();
        String query = "SELECT id, code, name, description, price, stock, supplier_id, tipoProducto FROM productos";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                products.add(new Products(
                        rs.getString("id"),
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getFloat("price"),
                        rs.getInt("stock"),
                        rs.getString("supplier_id"),
                        rs.getString("tipoProducto")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al cargar los productos.", e);
        }

        return products;
    }
}

    