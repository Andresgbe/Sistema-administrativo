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

/**
 *
 * @author 
 */
public class EditClientView extends JDialog {
    
    // Declaramos los componentes de interfaz gráfica para cada variable y los botones
    private JTextField nameField, identificationField, emailField, addressField, phoneField;
    private JButton saveButton, cancelButton;
    
    public EditClientView(JFrame parent) {
        super(parent, "Editar Cliente", true);
        setSize(350, 250);
        setLayout(new GridLayout(6, 2, 10, 10));
        
        // Creación de componentes y campos de entrada en la ventana 
        
        add(new JLabel("Nombre:"));
        nameField = new JTextField();
        add(nameField);
        
        add(new JLabel("Identificación:"));
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
        
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);
        
        // Centrar ventana emergente
        setLocationRelativeTo(parent);
    }
    
    // Métodos para establecer los valores en los campos de texto
    public void setClientName(String name) { nameField.setText(name); }
    public void setClientIdentification(String identification) { identificationField.setText(identification); }
    public void setClientEmail(String email) { emailField.setText(email); }
    public void setClientAddress(String address) { addressField.setText(address); }
    public void setClientPhone(String phone) { phoneField.setText(phone); }

    // Métodos para obtener los valores ingresados
    public String getClientName() { return nameField.getText(); }
    public String getClientIdentification() { return identificationField.getText(); }
    public String getClientEmail() { return emailField.getText(); }
    public String getClientAddress() { return addressField.getText(); }
    public String getClientPhone() { return phoneField.getText(); }

    // Métodos para acceder a los botones
    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}