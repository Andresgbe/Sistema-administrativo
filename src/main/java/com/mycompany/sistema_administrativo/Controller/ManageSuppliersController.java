/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.ManageSuppliersView;
import com.mycompany.sistema_administrativo.Model.Suppliers;
import com.mycompany.sistema_administrativo.View.EditSupplierView;
import com.mycompany.sistema_administrativo.View.AddSupplierView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author andresgbe
 */
    public class ManageSuppliersController {
        private ManageSuppliersView manageSuppliersView;
        private List<Suppliers> suppliers = new ArrayList<>(); 

        public ManageSuppliersController(ManageSuppliersView manageSuppliersView){
            this.manageSuppliersView = manageSuppliersView;
            configureListeners();
            loadSuppliersFromDatabase(); // Cargar productos en la tabla al abrir
            updateSuppliersTable();
        }

    private void configureListeners() {
        manageSuppliersView.getEditButton().addActionListener(e -> {
            int selectedRow = manageSuppliersView.getSuppliersTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageSuppliersView, "Selecciona un proveedor para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener ID del proveedor seleccionado
            String supplierId = manageSuppliersView.getSuppliersTable().getValueAt(selectedRow, 0).toString();

            // Buscar el proveedor en la lista
            Suppliers supplierToEdit = suppliers.stream()
                    .filter(supplier -> supplier.getId().equals(supplierId))
                    .findFirst()
                    .orElse(null);

            if (supplierToEdit == null) {
                JOptionPane.showMessageDialog(manageSuppliersView, "Proveedor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear y mostrar la ventana de edición
            EditSupplierView editSupplierView = new EditSupplierView(manageSuppliersView);
            editSupplierView.setSupplierName(supplierToEdit.getName());
            editSupplierView.setSupplierPhone(supplierToEdit.getPhone());
            editSupplierView.setSupplierEmail(supplierToEdit.getEmail());
            editSupplierView.setSupplierAddress(supplierToEdit.getAddress());
            editSupplierView.setSupplierRif(supplierToEdit.getRif());

            editSupplierView.getSaveButton().addActionListener(event -> {
                String name = editSupplierView.getSupplierName();
                String phone = editSupplierView.getSupplierPhone();
                String email = editSupplierView.getSupplierEmail();
                String address = editSupplierView.getSupplierAddress();
                int rif = editSupplierView.getSupplierRIF();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(manageSuppliersView, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (isDuplicateSupplier(email, phone, rif)) {
                JOptionPane.showMessageDialog(manageSuppliersView, 
                    "Ya existe un proveedor con el mismo correo, teléfono o RIF.", 
                    "Duplicado detectado", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

                supplierToEdit.setName(name);
                supplierToEdit.setPhone(phone);
                supplierToEdit.setEmail(email);
                supplierToEdit.setAddress(address);
                supplierToEdit.setRif(rif);

                updateSupplierInDatabase(supplierToEdit);
                editSupplierView.dispose();
            });

            editSupplierView.getCancelButton().addActionListener(event -> editSupplierView.dispose());

            editSupplierView.setVisible(true);
        });

        manageSuppliersView.getAddButton().addActionListener(e -> {
            AddSupplierView addSupplierView = new AddSupplierView(manageSuppliersView);

            // Acción de guardar proveedor
            addSupplierView.getSaveButton().addActionListener(event -> {
                String name = addSupplierView.getSupplierName();
                String phone = addSupplierView.getSupplierPhone();
                String email = addSupplierView.getSupplierEmail();
                String address = addSupplierView.getSupplierAddress();
                int rif = addSupplierView.getSupplierRIF();

                // Validación de campos
                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty()) {
                    JOptionPane.showMessageDialog(manageSuppliersView, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                if (isDuplicateSupplier(email, phone, rif)) {
                JOptionPane.showMessageDialog(manageSuppliersView, 
                    "Ya existe un proveedor con el mismo correo, teléfono o RIF.", 
                    "Duplicado detectado", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

                // Crear el objeto proveedor
                Suppliers newSupplier = new Suppliers(name, phone, email, address, rif);
                addSupplierToDatabase(newSupplier);  // Insertar el proveedor en la base de datos
                addSupplierView.dispose();  // Cerrar la ventana
            });

            // Acción de cancelar
            addSupplierView.getCancelButton().addActionListener(event -> addSupplierView.dispose());

            addSupplierView.setVisible(true);
        });

        // Vinculamos el botón "Eliminar Proveedor"
        manageSuppliersView.getDeleteButton().addActionListener(e -> {
            int selectedRow = manageSuppliersView.getSuppliersTable().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageSuppliersView, "Selecciona un proveedor para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el ID del proveedor seleccionado
            String supplierId = manageSuppliersView.getSuppliersTable().getValueAt(selectedRow, 0).toString();
            deleteSupplierFromDatabase(supplierId); // Eliminar proveedor de la base de datos
        });
    }

    private void loadSuppliersFromDatabase() {
        System.out.println("🔹 Cargando proveedores desde la base de datos...");

String query = "SELECT id, name, phone, email, address, rif FROM suppliers"; // <--- Cambio aquí
try (Connection connection = DatabaseConnection.getConnection();
     PreparedStatement statement = connection.prepareStatement(query);
     ResultSet resultSet = statement.executeQuery()) {

    suppliers.clear(); // Limpiar la lista antes de cargar los nuevos proveedores
    List<Object[]> supplierList = new ArrayList<>();

    while (resultSet.next()) {
        Suppliers supplier = new Suppliers(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("phone"),
                resultSet.getString("email"),
                resultSet.getString("address"),
                resultSet.getInt("rif")
        );

        suppliers.add(supplier);
        // Agregar los datos en formato de tabla
        Object[] supplierRow = {
                supplier.getId(),
                supplier.getName(),
                supplier.getPhone(),
                supplier.getEmail(),
                supplier.getAddress(),
                supplier.getRif()
        };
        supplierList.add(supplierRow);
        System.out.println("🔹 Proveedor agregado: " + supplier.getName());
    }

    System.out.println("🔹 Total proveedores cargados: " + supplierList.size());
    manageSuppliersView.loadSuppliers(supplierList.toArray(new Object[0][0]));

} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(null, "Error al cargar los proveedores de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
}

    }

    private void updateSuppliersTable() {
    System.out.println("🔹 Actualizando tabla de proveedores...");
    
    Object[][] data = new Object[suppliers.size()][6]; // 6 columnas (ID, Nombre, Teléfono, Correo, Dirección, RIF)

    for (int i = 0; i < suppliers.size(); i++) {
        Suppliers supplier = suppliers.get(i);
        data[i][0] = supplier.getId();
        data[i][1] = supplier.getName();
        data[i][2] = supplier.getPhone();
        data[i][3] = supplier.getEmail();
        data[i][4] = supplier.getAddress();
        data[i][5] = supplier.getRif();
    }

    manageSuppliersView.loadSuppliers(data);
}

    private void updateSupplierInDatabase(Suppliers supplier) {
    String query = "UPDATE suppliers SET name = ?, phone = ?, email = ?, address = ?, rif = ? WHERE id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, supplier.getName());
        statement.setString(2, supplier.getPhone());
        statement.setString(3, supplier.getEmail());
        statement.setString(4, supplier.getAddress());
        statement.setInt(5, supplier.getRif());
        statement.setString(6, supplier.getId());

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(null, "Proveedor actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadSuppliersFromDatabase();
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el proveedor para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

   private void addSupplierToDatabase(Suppliers supplier) {
    String query = "INSERT INTO suppliers (name, phone, email, address, rif) VALUES (?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, supplier.getName());
        statement.setString(2, supplier.getPhone());
        statement.setString(3, supplier.getEmail());
        statement.setString(4, supplier.getAddress());
        statement.setInt(5, supplier.getRif());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Proveedor agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            loadSuppliersFromDatabase(); // Recargar los proveedores
        } else {
            JOptionPane.showMessageDialog(null, "Error al agregar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al agregar el proveedor en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void deleteSupplierFromDatabase(String supplierId) {
    String checkQuery = "SELECT COUNT(*) FROM productos WHERE supplier_id = ?";
    String deleteQuery = "DELETE FROM suppliers WHERE id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {

        checkStmt.setString(1, supplierId);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int productCount = rs.getInt(1);

        if (productCount > 0) {
            int confirm = JOptionPane.showConfirmDialog(null,
                "Este proveedor tiene productos asociados.\n" +
                "Si lo elimina, no se podrá asociar más productos con este proveedor.\n" +
                "¿Está seguro que desea continuar?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }
        }

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setString(1, supplierId);
            int rowsDeleted = deleteStmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Proveedor eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadSuppliersFromDatabase(); // Recargar proveedores
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private boolean isDuplicateSupplier(String email, String phone, int rif) {
        String query = "SELECT COUNT(*) FROM suppliers WHERE email = ? OR phone = ? OR rif = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            statement.setString(2, phone);
            statement.setInt(3, rif);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al validar duplicados.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    
    }
