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
    private JComboBox<Suppliers> supplierComboBox;
    private JButton saveButton, cancelButton;

    public AddProductView(JFrame parent) {
        super(parent, "Agregar Producto", true);
        setSize(350, 300);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Código:"));
        codeField = new JTextField();
        add(codeField);

        add(new JLabel("Nombre:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Descripción:"));
        descriptionField = new JTextField();
        add(descriptionField);

        add(new JLabel("Precio:"));
        priceField = new JTextField();
        add(priceField);

        add(new JLabel("Stock:"));
        stockField = new JTextField();
        add(stockField);

        add(new JLabel("Proveedor:"));
        supplierComboBox = new JComboBox<>();
        add(supplierComboBox);

        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
         // Validadores

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