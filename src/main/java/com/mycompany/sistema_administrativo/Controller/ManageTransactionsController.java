/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;
import com.mycompany.sistema_administrativo.Model.Transactions;
import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.ManageTransactionsView;
import com.mycompany.sistema_administrativo.View.AddTransactionView;


/**
 *
 * @author andresgbe
 */

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class ManageTransactionsController {
    private ManageTransactionsView manageTransactionsView;
    private List<Transactions> transactions = new ArrayList<>();

    public ManageTransactionsController(ManageTransactionsView manageTransactionsView) {
        this.manageTransactionsView = manageTransactionsView;
        configureListeners();
        loadTransactionsFromDatabase();
        updateTransactionsTable();
    }

    private void configureListeners() {
        manageTransactionsView.getAddButton().addActionListener(e -> {
            AddTransactionView addView = new AddTransactionView(manageTransactionsView);

            // Listeners para actualizar el monto automáticamente
            addView.productCodeField.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    updateTotalAmount(addView);
                }
            });

            addView.quantityField.addFocusListener(new java.awt.event.FocusAdapter() {
                @Override
                public void focusLost(java.awt.event.FocusEvent e) {
                    updateTotalAmount(addView);
                }
            });

            // Cargar clientes
            addView.setCustomerOptions(fetchCustomerItems());


            // Guardar
            addView.getSaveButton().addActionListener(event -> {
                try {
                    int customerID = Integer.parseInt(addView.getSelectedCustomer());
                    int productID = getProductIdByCode(addView.getProductCode());
                    if (productID == -1) {
                        JOptionPane.showMessageDialog(manageTransactionsView, "Producto no encontrado con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String type = addView.getTransactionType();
                    int quantity = addView.getQuantity();
                    String date = java.time.LocalDate.now().toString();
                    float amount = addView.getTotalAmount();

                    Transactions newTransaction = new Transactions(customerID, type, productID, quantity, date, amount);
                    addTransactionToDatabase(newTransaction);
                    addView.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(manageTransactionsView, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            addView.getCancelButton().addActionListener(event -> addView.dispose());
            addView.setVisible(true);
        });

        manageTransactionsView.getDeleteButton().addActionListener(e -> {
            int selectedRow = manageTransactionsView.getTransactionsTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageTransactionsView, "Selecciona una transacción para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String transactionId = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 0).toString();
            deleteTransactionFromDatabase(transactionId);
        });

        manageTransactionsView.getEditButton().addActionListener(e -> {
            JOptionPane.showMessageDialog(manageTransactionsView, "Funcionalidad de edición en desarrollo.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    private void loadTransactionsFromDatabase() {
        System.out.println("🔹 Cargando transacciones desde la base de datos...");
        String query = "SELECT * FROM transactions";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            transactions.clear();
            List<Object[]> dataList = new ArrayList<>();

            while (resultSet.next()) {
                Transactions tx = new Transactions(
                        resultSet.getString("id"),
                        resultSet.getInt("customerID"),
                        resultSet.getString("transactionType"),
                        resultSet.getInt("productID"),
                        resultSet.getInt("quantity"),
                        resultSet.getString("transactionDate"),
                        resultSet.getFloat("totalAmount")
                );
                transactions.add(tx);

                Object[] row = {
                    tx.getId(),
                    tx.getCustomerID(),
                    tx.getTransactionType(),
                    tx.getProductID(),
                    tx.getQuantity(),
                    tx.getTransactionDate(),
                    tx.getTotalAmount()
                };
                dataList.add(row);
            }

            manageTransactionsView.loadTransactions(dataList.toArray(new Object[0][0]));
            System.out.println("🔹 Total transacciones cargadas: " + dataList.size());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las transacciones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTransactionsTable() {
        Object[][] data = new Object[transactions.size()][7];
        for (int i = 0; i < transactions.size(); i++) {
            Transactions tx = transactions.get(i);
            data[i][0] = tx.getId();
            data[i][1] = tx.getCustomerID();
            data[i][2] = tx.getTransactionType();
            data[i][3] = tx.getProductID();
            data[i][4] = tx.getQuantity();
            data[i][5] = tx.getTransactionDate();
            data[i][6] = tx.getTotalAmount();
        }
        manageTransactionsView.loadTransactions(data);
    }

    private void addTransactionToDatabase(Transactions tx) {
        String query = "INSERT INTO transactions (customerID, transactionType, productID, quantity, transactionDate, totalAmount) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, tx.getCustomerID());
            statement.setString(2, tx.getTransactionType());
            statement.setInt(3, tx.getProductID());
            statement.setInt(4, tx.getQuantity());
            statement.setString(5, tx.getTransactionDate());
            statement.setFloat(6, tx.getTotalAmount());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Transacción registrada correctamente.");
                loadTransactionsFromDatabase();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo registrar la transacción.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error en la base de datos al registrar transacción.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTransactionFromDatabase(String id) {
        String query = "DELETE FROM transactions WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Transacción eliminada exitosamente.");
                loadTransactionsFromDatabase();
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar la transacción.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar transacción.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<ClienteComboItem> fetchCustomerItems() {
        List<ClienteComboItem> list = new ArrayList<>();
        String query = "SELECT id, name FROM clientes";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                list.add(new ClienteComboItem(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private int getProductIdByCode(String code) {
        String query = "SELECT id FROM productos WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // No encontrado
    }

    private float getProductPriceByCode(String code) {
        String query = "SELECT price FROM productos WHERE code = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getFloat("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1f; // No encontrado
    }

    private void updateTotalAmount(AddTransactionView view) {
        try {
            String code = view.getProductCode();
            int quantity = view.getQuantity();
            float price = getProductPriceByCode(code);
            if (price > 0) {
                float total = price * quantity;
                view.setTotalAmount(total);
            } else {
                view.setTotalAmount(0);
            }
        } catch (Exception e) {
            view.setTotalAmount(0);
        }
    }
    
    private static class ClienteComboItem {
    private int id;
    private String nombre;

    public ClienteComboItem(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return nombre;
    }
}

}