/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

/**
 *
 * @author andresgbe
 */
import javax.swing.*;
import java.awt.*;

public class EditTransactionView extends JDialog {
    private JComboBox<String> typeComboBox;
    private JComboBox<Object> customerComboBox; // Usamos Object por ClienteComboItem
    private JTextField productCodeField;
    private JTextField quantityField;
    private JTextField totalAmountField;
    private JButton saveButton, cancelButton;

    public EditTransactionView(JFrame parent) {
        super(parent, "Editar Transacción", true);
        setSize(400, 300);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Tipo de transacción
        add(new JLabel("Tipo de Transacción:"));
        typeComboBox = new JComboBox<>(new String[]{"compra", "venta"});
        add(typeComboBox);

        // Cliente
        add(new JLabel("Cliente:"));
        customerComboBox = new JComboBox<>();
        add(customerComboBox);

        // Código de producto
        add(new JLabel("Código de Producto:"));
        productCodeField = new JTextField();
        add(productCodeField);

        // Cantidad
        add(new JLabel("Cantidad:"));
        quantityField = new JTextField();
        add(quantityField);

        // Monto total (bloqueado)
        add(new JLabel("Monto Total:"));
        totalAmountField = new JTextField();
        totalAmountField.setEditable(false);
        add(totalAmountField);

        // Botones
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    // Métodos para pre-cargar datos
    public void setTransactionType(String type) {
        typeComboBox.setSelectedItem(type);
    }

    public void setSelectedCustomer(Object cliente) {
        customerComboBox.setSelectedItem(cliente);
    }

    public void setProductCode(String code) {
        productCodeField.setText(code);
    }

    public void setQuantity(int quantity) {
        quantityField.setText(String.valueOf(quantity));
    }

    public void setTotalAmount(float amount) {
        totalAmountField.setText(String.valueOf(amount));
    }

    // Métodos para leer lo ingresado
    public String getTransactionType() {
        return (String) typeComboBox.getSelectedItem();
    }

    public Object getSelectedCustomerItem() {
        return customerComboBox.getSelectedItem();
    }

    public String getProductCode() {
        return productCodeField.getText();
    }

    public int getQuantity() {
        return Integer.parseInt(quantityField.getText());
    }

    public float getTotalAmount() {
        return Float.parseFloat(totalAmountField.getText());
    }

    public void setCustomerOptions(Object[] options) {
        customerComboBox.setModel(new DefaultComboBoxModel<>(options));
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JTextField getProductCodeField() {
        return productCodeField;
    }

    public JTextField getQuantityField() {
        return quantityField;
    }
}
