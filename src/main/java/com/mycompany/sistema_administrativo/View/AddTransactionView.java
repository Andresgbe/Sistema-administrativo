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
import java.util.List;


public class AddTransactionView extends JDialog {
    private JComboBox<Object> customerComboBox;
    public JTextField productCodeField;  
    private JComboBox<String> typeComboBox;
    public JTextField quantityField;     
    private JTextField totalAmountField;
    private JButton saveButton, cancelButton;

    public AddTransactionView(JFrame parent) {
        super(parent, "Agregar Transacción", true);
        setSize(400, 300);
        setLayout(new GridLayout(7, 2, 10, 10));

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

    // Getters para valores ingresados
    public String getTransactionType() {
        return (String) typeComboBox.getSelectedItem();
    }

    public String getSelectedCustomer() {
        return (String) customerComboBox.getSelectedItem();
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

    public void setTotalAmount(float amount) {
        totalAmountField.setText(String.valueOf(amount));
    }

    public void setCustomerOptions(List<?> customers) {
        customerComboBox.setModel(new DefaultComboBoxModel<>(customers.toArray()));
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
    
    public Object getSelectedCustomerItem() {
    return customerComboBox.getSelectedItem();
}

}