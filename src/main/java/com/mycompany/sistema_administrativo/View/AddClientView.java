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

public class AddClientView extends JDialog {
    private JTextField nameField, identificationField, emailField, addressField, phoneField;
    private JButton saveButton, cancelButton;

    public AddClientView(JFrame parent) {
        super(parent, "Agregar Cliente", true);
        setSize(350, 250);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Campos de entrada
        add(new JLabel("Nombre:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Cédula:"));
        identificationField = new JTextField();
        add(identificationField);

        add(new JLabel("Correo:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Dirección:"));
        addressField = new JTextField();
        add(addressField);

        add(new JLabel("Teléfono:"));
        phoneField = new JTextField();
        add(phoneField);

        // Botones para guardar y cancelar
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    // Métodos para obtener los valores ingresados
    public String getClientName() { return nameField.getText(); }
    public String getClientIdentification() { return identificationField.getText(); }
    public String getClientEmail() { return emailField.getText(); }
    public String getClientAddress() { return addressField.getText(); }
    public String getClientPhone() { return phoneField.getText(); }

    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}

