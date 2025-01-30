/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import com.mycompany.sistema_administrativo.Controller.ManageUsersController;
import com.mycompany.sistema_administrativo.Model.Users;
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author andresgbe
 */
public class AddUserView extends JDialog {
    // Campos de entrada de texto
    private JTextField nameField, emailField, phoneField, roleField;
    private JPasswordField passwordField;
    private JButton addButton, cancelButton;
    private ManageUsersController controller;

public AddUserView(JFrame parent, ManageUsersController controller) {
    super(parent, "Agregar Usuario", true); // Ventana modal
    this.controller = controller;
    // Configuración de la ventana
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

    // Botón para agregar usuario
    addButton = new JButton("Agregar");
    cancelButton = new JButton("Cancelar");
    add(addButton);
    add(cancelButton);

    setLocationRelativeTo(parent);
    configureListeners();
}

    private void configureListeners() {
        // Acción del botón "Agregar"
        addButton.addActionListener(e -> {
            System.out.println("🔹 Botón Agregar Usuario presionado.");

            // Obtener los datos de los campos de texto
            String name = nameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = new String(passwordField.getPassword());
            String role = roleField.getText();

            // Validar que los campos no estén vacíos
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear un objeto de usuario sin ID (lo genera la base de datos)
            Users newUser = new Users(name, email, phone, BCrypt.hashpw(password, BCrypt.gensalt()), role);

            // Llamar al controlador para agregar el usuario a la BD
            controller.addUserToDatabase(newUser);

            // Cerrar la ventana después de agregar el usuario
            dispose();
        });

        // Acción del botón "Cancelar"
        cancelButton.addActionListener(e -> dispose());
    }

    // Métodos para obtener los valores ingresados por el usuario
    public String getUserName() { return nameField.getText(); }
    public String getUserEmail() { return emailField.getText(); }
    public String getUserPhone() { return phoneField.getText(); }
    public String getUserPassword() { return new String(passwordField.getPassword()); }
    public String getUserRole() { return roleField.getText(); }
    
    public JButton getAddButton() {
    return addButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

}