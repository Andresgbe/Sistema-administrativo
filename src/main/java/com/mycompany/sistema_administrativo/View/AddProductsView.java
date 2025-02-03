/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import com.mycompany.sistema_administrativo.Controller.ManageProductsController;

import javax.swing.*;
import java.awt.*;
import com.mycompany.sistema_administrativo.Controller.ManageProductsController;

/**
 * Ventana emergente para agregar un nuevo producto
 */
public class AddProductsView extends JDialog {
    private JTextField codeField, nameField, descriptionField, priceField, stockField;
    private JButton addButton, cancelButton;
    private ManageProductsController controller;

    public AddProductsView(JFrame parent, ManageProductsController controller) {
        super(parent, "Agregar Producto", true);
        this.controller = controller;
        setSize(350, 250);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Campos de entrada
        add(new JLabel("Codigo:"));
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

        // Botones
        addButton = new JButton("Agregar");
        cancelButton = new JButton("Cancelar");
        add(addButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
        configureListeners();
    }

    private void configureListeners() {
        addButton.addActionListener(e -> {
            System.out.println("🔹 Botón 'Agregar Producto' presionado.");

            // Obtener los datos
            String code = codeField.getText();
            String name = nameField.getText();
            String description = descriptionField.getText();
            String priceText = priceField.getText();
            String stockText = stockField.getText();

            // Validación
            if (code.isEmpty() || name.isEmpty() || description.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);
                controller.addProductToDatabase(code, name, description, price, stock);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos en Precio y Stock.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    // Métodos para obtener los valores ingresados
    public String getProductName() { return nameField.getText(); }
    public String getProductCode() { return codeField.getText() ; }
    public String getProductDescription() { return descriptionField.getText(); }
    public String getProductPrice() { return priceField.getText(); }
    public String getProductStock() { return stockField.getText(); }
    public JButton getAddButton() { return addButton; }
    public JButton getCancelButton() { return cancelButton; }
}
