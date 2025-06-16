/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;
import Utils.InvoiceGenerator;
import com.mycompany.sistema_administrativo.Model.Transactions;
import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.ManageTransactionsView;
import com.mycompany.sistema_administrativo.View.AddTransactionView;
import com.mycompany.sistema_administrativo.View.EditTransactionView;



/**
 *
 * @author andresgbe
 */

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManageTransactionsController {
    private ManageTransactionsView manageTransactionsView;

    public ManageTransactionsController(ManageTransactionsView manageTransactionsView) {
        this.manageTransactionsView = manageTransactionsView;
        configureListeners();
        loadTransactionsFromDatabase();
    }

    private void configureListeners() {
        manageTransactionsView.getAddButton().addActionListener(e -> {
            AddTransactionView addView = new AddTransactionView(manageTransactionsView);
            addView.setCustomerOptions(fetchCustomerItems().toArray());

            addView.getProductCodeField().addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    updateTotalAmount(addView);
                }
            });

            addView.getQuantityField().addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    updateTotalAmount(addView);
                }
            });

            addView.getSaveButton().addActionListener(event -> {
                try {
                    ClienteComboItem selectedItem = (ClienteComboItem) addView.getSelectedCustomerItem();
                    int customerID = selectedItem.getId();
                    String transactionType = addView.getTransactionType();
                    String productCode = addView.getProductCode();
                    int quantity = addView.getQuantity();
                    float totalAmount = addView.getTotalAmount();
                    String transactionDate = new java.sql.Date(System.currentTimeMillis()).toString();
                    int productID = getProductIdByCode(productCode);
                    int stockActual = getProductStock(productID);
                    
                    if (productID == -1) {
                        JOptionPane.showMessageDialog(addView, "Código inválido: el producto no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }            

                    if (transactionType.equalsIgnoreCase("venta") && quantity > stockActual) {
                        JOptionPane.showMessageDialog(addView, "No hay suficiente stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (transactionType.equalsIgnoreCase("venta") && stockActual == quantity){
                        JOptionPane.showMessageDialog(addView, "El stock del producto se agoto con esta venta");
                    }

                    Transactions tx = new Transactions(customerID, transactionType, productID, quantity, transactionDate, totalAmount);
                    addTransactionToDatabase(tx);
                    addView.dispose();
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(addView, "Error al guardar transacción: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
            int selectedRow = manageTransactionsView.getTransactionsTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageTransactionsView, "Selecciona una transacción para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String id = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 0).toString();
            String clienteNombre = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 1).toString();
            String tipo = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 2).toString();
            String productoNombre = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 3).toString();
            int cantidadOriginal = Integer.parseInt(manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 4).toString());
            String fecha = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 5).toString();
            float totalOriginal = Float.parseFloat(manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 6).toString());

            ClienteComboItem[] clientes = fetchCustomerItems().toArray(new ClienteComboItem[0]);
            int productID = getProductIdByName(productoNombre);
            String productCode = getProductCodeById(productID);

            EditTransactionView editView = new EditTransactionView(manageTransactionsView);
            editView.setCustomerOptions(clientes);
            editView.setSelectedCustomer(findClienteItemByName(clientes, clienteNombre));
            editView.setTransactionType(tipo);
            editView.setProductCode(productCode);
            editView.setQuantity(cantidadOriginal);
            editView.setTotalAmount(totalOriginal);

            editView.getProductCodeField().addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    updateTotalAmount(editView);
                }
            });

            editView.getQuantityField().addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent e) {
                    updateTotalAmount(editView);
                }
            });

            editView.getSaveButton().addActionListener(event -> {
                try {
                    int nuevoClienteID = ((ClienteComboItem) editView.getSelectedCustomerItem()).getId();
                    String nuevoTipo = editView.getTransactionType();
                    int nuevaCantidad = editView.getQuantity();
                    float nuevoTotal = editView.getTotalAmount();
                    int nuevoProductID = getProductIdByCode(editView.getProductCode());

                    int stockActual = getProductStock(nuevoProductID);
                    if (nuevoTipo.equalsIgnoreCase("venta") && nuevaCantidad > stockActual) {
                        JOptionPane.showMessageDialog(editView, "No hay suficiente stock disponible.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    updateTransactionInDatabase(id, nuevoClienteID, nuevoTipo, nuevoProductID, nuevaCantidad, fecha, nuevoTotal);

                    int diferencia = nuevaCantidad - cantidadOriginal;
                    if (diferencia != 0 || !nuevoTipo.equalsIgnoreCase(tipo)) {
                        if (tipo.equalsIgnoreCase("venta")) updateProductStock(productID, cantidadOriginal);
                        if (tipo.equalsIgnoreCase("compra")) updateProductStock(productID, -cantidadOriginal);
                        if (nuevoTipo.equalsIgnoreCase("venta")) updateProductStock(nuevoProductID, -nuevaCantidad);
                        if (nuevoTipo.equalsIgnoreCase("compra")) updateProductStock(nuevoProductID, nuevaCantidad);
                        
                        int stockFinal = getProductStock(nuevoProductID);
                        if (nuevoTipo.equalsIgnoreCase("venta") && stockFinal == 0) {
                            JOptionPane.showMessageDialog(editView,
                                "⚠️ El producto se ha quedado sin stock luego de esta modificación.",
                                "Producto agotado",
                                JOptionPane.WARNING_MESSAGE);
                        }

                    }

                    editView.dispose();
                    loadTransactionsFromDatabase();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(editView, "Error al editar transacción: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            editView.getCancelButton().addActionListener(event -> editView.dispose());
            editView.setVisible(true);
        });
        
        manageTransactionsView.getGenerateInvoiceButton().addActionListener(e -> {
            int selectedRow = manageTransactionsView.getTransactionsTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageTransactionsView, "Selecciona una transacción para generar la factura.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        String transactionId = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 0).toString(); // ID
        String clientName = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 1).toString();   // Cliente
        String productName = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 3).toString();  // Producto
        String quantity = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 4).toString();     // Cantidad
        String total = manageTransactionsView.getTransactionsTable().getValueAt(selectedRow, 6).toString();        // Monto Total


            InvoiceGenerator.generateInvoice(transactionId, productName, clientName, quantity, total);
        });
    }


    private void loadTransactionsFromDatabase() {
        String query = "SELECT t.id, c.name AS cliente_nombre, p.name AS producto_nombre, " +
                       "t.transactionType, t.quantity, t.transactionDate, t.totalAmount " +
                       "FROM transactions t " +
                       "JOIN clientes c ON t.customerID = c.id " +
                       "JOIN productos p ON t.productID = p.id";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<Object[]> dataList = new ArrayList<>();
            while (resultSet.next()) {
                Object[] row = {
                    resultSet.getInt("id"),
                    resultSet.getString("cliente_nombre"),
                    resultSet.getString("transactionType"),
                    resultSet.getString("producto_nombre"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("transactionDate"),
                    resultSet.getFloat("totalAmount")
                };
                dataList.add(row);
            }
            manageTransactionsView.loadTransactions(dataList.toArray(new Object[0][0]));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar las transacciones.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                if (tx.getTransactionType().equalsIgnoreCase("venta")) {
                    updateProductStock(tx.getProductID(), -tx.getQuantity());
                } else if (tx.getTransactionType().equalsIgnoreCase("compra")) {
                    updateProductStock(tx.getProductID(), tx.getQuantity());
                }
                loadTransactionsFromDatabase();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteTransactionFromDatabase(String id) {
        String query = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
            loadTransactionsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateTransactionInDatabase(String id, int customerID, String type, int productID, int quantity, String date, float totalAmount) {
        String query = "UPDATE transactions SET customerID = ?, transactionType = ?, productID = ?, quantity = ?, transactionDate = ?, totalAmount = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerID);
            stmt.setString(2, type);
            stmt.setInt(3, productID);
            stmt.setInt(4, quantity);
            stmt.setString(5, date);
            stmt.setFloat(6, totalAmount);
            stmt.setString(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateProductStock(int productID, int variation) {
        String query = "UPDATE productos SET stock = stock + ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, variation);
            stmt.setInt(2, productID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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

    private ClienteComboItem findClienteItemByName(ClienteComboItem[] lista, String nombre) {
        for (ClienteComboItem c : lista) {
            if (c.toString().equalsIgnoreCase(nombre)) {
                return c;
            }
        }
        return null;
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
        return -1;
    }

    private int getProductIdByName(String name) {
        String query = "SELECT id FROM productos WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getProductCodeById(int productID) {
        String query = "SELECT code FROM productos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getProductStock(int productID) {
        String query = "SELECT stock FROM productos WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void updateTotalAmount(AddTransactionView view) {
        try {
            String productCode = view.getProductCode();
            int quantity = view.getQuantity();
            float price = getProductPriceByCode(productCode);
            view.setTotalAmount(quantity * price);
        } catch (Exception e) {
            view.setTotalAmount(0);
        }
    }

    private void updateTotalAmount(EditTransactionView view) {
        try {
            String productCode = view.getProductCode();
            int quantity = view.getQuantity();
            float price = getProductPriceByCode(productCode);
            view.setTotalAmount(quantity * price);
        } catch (Exception e) {
            view.setTotalAmount(0);
        }
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
        return 0;
    }

    public static class ClienteComboItem {
        private int id;
        private String nombre;

        public ClienteComboItem(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        public String toString() {
            return nombre;
        }
    }
}