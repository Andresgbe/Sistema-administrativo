/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.Repository.ProductRepository;
import com.mycompany.sistema_administrativo.Repository.JdbcProductRepository;
import com.mycompany.sistema_administrativo.View.ManageProductsView;
import com.mycompany.sistema_administrativo.View.AddProductView;
import com.mycompany.sistema_administrativo.View.EditProductView;
import com.mycompany.sistema_administrativo.Model.Products;
import com.mycompany.sistema_administrativo.Model.Suppliers;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class ManageProductsController {
    private final ManageProductsView manageProductsView;
    private final ProductRepository productRepository;
    private final Map<String, String> supplierIdToName = new HashMap<>();
    private List<Products> products = new ArrayList<>();

    public ManageProductsController(ManageProductsView manageProductsView) {
        this.manageProductsView = manageProductsView;
        this.productRepository = new JdbcProductRepository();

        configureListeners();
        loadProductsFromDatabase();
        updateProductsTable();
    }

    private void configureListeners() {
        manageProductsView.getEditButton().addActionListener(e -> showEditProductDialog());
        manageProductsView.getAddButton().addActionListener(e -> showAddProductDialog());
        manageProductsView.getDeleteButton().addActionListener(e -> deleteSelectedProduct());
    }

    private void loadProductsFromDatabase() {
        cargarSuppliers();
        products = productRepository.loadProductsFromDatabase();
    }

    private void updateProductsTable() {
        Object[][] data = new Object[products.size()][8];
        for (int i = 0; i < products.size(); i++) {
            Products p = products.get(i);
            data[i][0] = p.getId();
            data[i][1] = p.getCode();
            data[i][2] = p.getName();
            data[i][3] = p.getDescription();
            data[i][4] = p.getPrice();
            data[i][5] = p.getStock();
            data[i][6] = supplierIdToName.getOrDefault(p.getSupplierId(), "Desconocido");
            data[i][7] = p.getTipoProducto();
        }
        manageProductsView.loadProducts(data);
    }

    private void cargarSuppliers() {
        supplierIdToName.clear();
        String query = "SELECT id, name FROM suppliers";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                supplierIdToName.put(rs.getString("id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(manageProductsView, "Error al cargar proveedores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddProductDialog() {
        AddProductView addProductView = new AddProductView(manageProductsView);
        addProductView.setSupplierOptions(fetchSuppliersFromDatabase());

        addProductView.getSaveButton().addActionListener(event -> {
            String code = addProductView.getProductCode();
            String name = addProductView.getProductName();
            String description = addProductView.getProductDescription();
            float price;
            int stock;
            String supplierId = addProductView.getSelectedSupplierId();
            String tipo = addProductView.getProductType();

            if (code.isEmpty() || name.isEmpty() || description.isEmpty()
                    || supplierId == null || tipo == null || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(manageProductsView,
                        "Todos los campos son obligatorios.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                price = addProductView.getProductPrice();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(manageProductsView,
                        "El precio debe ser un número válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                stock = addProductView.getProductStock();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(manageProductsView,
                        "El stock debe ser un número entero válido.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (productRepository.productCodeExist(code)) {
                JOptionPane.showMessageDialog(manageProductsView,
                        "Ya existe un producto con ese código.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            Products newProduct = new Products(code, name, description, price, stock, supplierId, tipo);
            productRepository.addProductToDatabase(newProduct);
            addProductView.dispose();
            loadProductsFromDatabase();
            updateProductsTable();
        });

        addProductView.getCancelButton().addActionListener(event -> addProductView.dispose());
        addProductView.setVisible(true);
    }

    private void showEditProductDialog() {
        int selectedRow = manageProductsView.getProductsTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageProductsView, "Selecciona un producto para editar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String productId = manageProductsView.getProductsTable().getValueAt(selectedRow, 0).toString();
        Products productToEdit = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (productToEdit == null) {
            JOptionPane.showMessageDialog(manageProductsView, "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EditProductView editProductView = new EditProductView(manageProductsView);
        editProductView.setSupplierOptions(fetchSuppliersFromDatabase());
        editProductView.setSelectedSupplier(productToEdit.getSupplierId());
        editProductView.setProductCode(productToEdit.getCode());
        editProductView.setProductName(productToEdit.getName());
        editProductView.setProductDescription(productToEdit.getDescription());
        editProductView.setProductPrice(productToEdit.getPrice());
        editProductView.setProductStock(productToEdit.getStock());
        editProductView.setProductType(productToEdit.getTipoProducto()); // ➤ NUEVO

        editProductView.getSaveButton().addActionListener(event -> {
            String code = editProductView.getProductCode();
            String name = editProductView.getProductName();
            String description = editProductView.getProductDescription();
            float price = editProductView.getProductPrice();
            int stock = editProductView.getProductStock();
            String supplierId = editProductView.getSelectedSupplierId();
            String tipo = editProductView.getProductType(); 

            if (code.isEmpty() || name.isEmpty() || description.isEmpty()
                    || tipo == null || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(manageProductsView,
                        "Todos los campos son obligatorios.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            productToEdit.setCode(code);
            productToEdit.setName(name);
            productToEdit.setDescription(description);
            productToEdit.setPrice(price);
            productToEdit.setStock(stock);
            productToEdit.setSupplierId(supplierId);
            productToEdit.setTipoProducto(tipo); // ➤ NUEVO

            if (stock == 0) {
                JOptionPane.showMessageDialog(manageProductsView,
                        "El stock de este producto es 0",
                        "Stock agotado",
                        JOptionPane.WARNING_MESSAGE);
            }

            productRepository.updateProductInDatabase(productToEdit);
            editProductView.dispose();
            loadProductsFromDatabase();
            updateProductsTable();
        });

        editProductView.getCancelButton().addActionListener(event -> editProductView.dispose());
        editProductView.setVisible(true);
    }

    private void deleteSelectedProduct() {
        int selectedRow = manageProductsView.getProductsTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageProductsView, "Selecciona un producto para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String productId = manageProductsView.getProductsTable().getValueAt(selectedRow, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(manageProductsView, "¿Eliminar producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productRepository.deleteProductFromDatabase(productId);
            loadProductsFromDatabase();
            updateProductsTable();
        }
    }

    private List<Suppliers> fetchSuppliersFromDatabase() {
        List<Suppliers> suppliers = new ArrayList<>();
        String query = "SELECT id, name, phone, email, address, rif FROM suppliers";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                suppliers.add(new Suppliers(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getInt("rif")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(manageProductsView, "Error al cargar proveedores.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return suppliers;
    }
}
