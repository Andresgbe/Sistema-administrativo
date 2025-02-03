/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author andresgbe
 */

public class EditUserView extends JDialog{
    private JTextField nameField, emailField, phoneField, roleField;
    private JPasswordField passwordField;
    private JButton saveButton, cancelButton;
    
    public EditUserView(JFrame parent) {
        super(parent, "Editar Usuario", true);
        setSize(350, 250);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Creación de etiquetas y campos de texto
        add(new JLabel("Nombre:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Correo:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Teléfono:"));
        phoneField = new JTextField();
        add(phoneField);

        add(new JLabel("Contraseña:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Rol:"));
        roleField = new JTextField();
        add(roleField);

        // Botones para guardar y cancelar
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
    }
    
    // Métodos para establecer los valores en los campos de texto
    public void setUserName(String name) {
        nameField.setText(name);
    }

    public void setUserEmail(String email) {
        emailField.setText(email);
    }

    public void setUserPhone(String phone) {
        phoneField.setText(phone);
    }

    public void setUserRole(String role) {
        roleField.setText(role);
    }

    // Métodos para obtener los valores ingresados por el usuario
    public String getUserName() {
        return nameField.getText();
    }

    public String getUserEmail() {
        return emailField.getText();
    }

    public String getUserPhone() {
        return phoneField.getText();
    }

    public String getUserPassword() {
        return new String(passwordField.getPassword());
    }

    public String getUserRole() {
        return roleField.getText();
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}

