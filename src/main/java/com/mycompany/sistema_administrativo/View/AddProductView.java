/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import com.mycompany.sistema_administrativo.Model.Suppliers;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddProductView extends JDialog {

    private JTextField codeField, nameField, descriptionField, priceField, stockField;
    private JComboBox<String> tipoProductoComboBox;
    private JComboBox<Suppliers> supplierComboBox;
    private JButton saveButton, cancelButton;

    public AddProductView(JFrame parent) {
        super(parent, "Agregar Producto", true);
        setSize(400, 450);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int row = 0;

        // Código
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Código:"), gbc);
        gbc.gridx = 1;
        codeField = new JTextField();
        add(codeField, gbc);
        row++;

        // Nombre
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField();
        add(nameField, gbc);
        row++;

        // Descripción
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField();
        add(descriptionField, gbc);
        row++;

        // Precio
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Precio:"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField();
        add(priceField, gbc);
        row++;

        // Stock
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        stockField = new JTextField();
        add(stockField, gbc);
        row++;

        // Tipo de producto
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Tipo de producto:"), gbc);
        gbc.gridx = 1;
        tipoProductoComboBox = new JComboBox<>(new String[]{
                "Insumo Médico", "Medicamento"
        });
        add(tipoProductoComboBox, gbc);
        row++;

        // Proveedor
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Proveedor:"), gbc);
        gbc.gridx = 1;
        supplierComboBox = new JComboBox<>();
        add(supplierComboBox, gbc);
        row++;

        // Botones
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        add(buttonsPanel, gbc);

        // Validadores
        addValidators();
    }

    private void addValidators() {
        // Nombre: solo letras y espacios
        nameField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetter(c) && c != ' ' && c != '\b') {
                    e.consume();
                }
            }
        });

        // Código: solo alfanumérico
        codeField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isLetterOrDigit(c) && c != '\b') {
                    e.consume();
                }
            }
        });

        // Precio: float
        priceField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = priceField.getText();
                if ((!Character.isDigit(c) && c != '.') || (c == '.' && text.contains("."))) {
                    e.consume();
                }
            }
        });

        // Stock: solo números enteros
        stockField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
            }
        });
    }

    public String getProductCode() { return codeField.getText(); }
    public String getProductName() { return nameField.getText(); }
    public String getProductDescription() { return descriptionField.getText(); }
    public float getProductPrice() { return Float.parseFloat(priceField.getText()); }
    public int getProductStock() { return Integer.parseInt(stockField.getText()); }
    public String getProductType() { return (String) tipoProductoComboBox.getSelectedItem(); }
    public void setProductType(String tipo) {
        tipoProductoComboBox.setSelectedItem(tipo);
    }
    public void setSupplierOptions(List<Suppliers> suppliers) {
        supplierComboBox.removeAllItems();
        for (Suppliers supplier : suppliers) {
            supplierComboBox.addItem(supplier);
        }
    }

    public String getSelectedSupplierId() {
        Suppliers selected = (Suppliers) supplierComboBox.getSelectedItem();
        return selected != null ? selected.getId() : null;
    }

    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}