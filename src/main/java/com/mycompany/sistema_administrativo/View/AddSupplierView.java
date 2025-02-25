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

public class AddSupplierView extends JDialog {
    private JTextField nameField, phoneField, emailField, addressField, rifField;
    private JButton saveButton, cancelButton;

    public AddSupplierView(JFrame parent) {
        super(parent, "Agregar Proveedor", true);
        setSize(350, 250);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Campos de entrada
        add(new JLabel("Nombre:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Teléfono:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Correo:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Dirección:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("RIF:"));
        rifField = new JTextField();
        add(rifField);

        // Botones para guardar y cancelar
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    // Métodos para obtener los valores ingresados
    public String getSupplierName() { return nameField.getText(); }
    public String getSupplierPhone() { return phoneField.getText(); }
    public String getSupplierEmail() { return emailField.getText(); }
    public String getSupplierAddress() { return addressField.getText(); }
    public int getSupplierRIF() { return Integer.parseInt(rifField.getText()); }

    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}