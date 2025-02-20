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

public class AddProductView extends JDialog {
    private JTextField codeField, nameField, descriptionField, priceField, stockField;
    private JButton saveButton, cancelButton;

    public AddProductView(JFrame parent) {
        super(parent, "Agregar Producto", true);
        setSize(350, 250);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Campos de entrada
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

        // Botones para guardar y cancelar
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    // Métodos para obtener los valores ingresados
    public String getProductCode() { return codeField.getText(); }
    public String getProductName() { return nameField.getText(); }
    public String getProductDescription() { return descriptionField.getText(); }
    public float getProductPrice() { return Float.parseFloat(priceField.getText()); }
    public int getProductStock() { return Integer.parseInt(stockField.getText()); }

    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}