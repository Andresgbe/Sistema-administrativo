/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.LoginView;
import com.mycompany.sistema_administrativo.View.MainMenuView;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author andresgbe
 */

public class LoginController {
    private LoginView loginView;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;

        // Configurar eventos de los botones
        configureListeners();
    }

    private void configureListeners() {
        loginView.getLoginButton().addActionListener(e -> handleLogin());
        loginView.getCancelButton().addActionListener(e -> handleCancel());
    }

private void handleLogin() {
    System.out.println("🔹 Método handleLogin() ejecutándose...");

    String email = loginView.getMail(); // Obtener correo ingresado
    String password = loginView.getPassword(); // Obtener contraseña ingresada

    try (Connection connection = DatabaseConnection.getConnection()) {
        System.out.println("🔹 Conexión con la base de datos establecida.");

        // Consulta SQL para obtener la contraseña hasheada y el rol del usuario
        String query = "SELECT password, role FROM usuarios WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Obtener la contraseña hasheada de la base de datos
            String storedHashedPassword = resultSet.getString("password");
            String role = resultSet.getString("role");

            System.out.println("🔹 Contraseña ingresada: " + password);
            System.out.println("🔹 Contraseña en BD: " + storedHashedPassword);

            // Verificar la contraseña ingresada con la almacenada usando BCrypt
            boolean passwordMatches = BCrypt.checkpw(password, storedHashedPassword);
            System.out.println("🔹 ¿Las contraseñas coinciden?: " + passwordMatches);

            if (passwordMatches) {
                JOptionPane.showMessageDialog(loginView, "Inicio de sesión exitoso.\nBienvenido: " + email);

                MainMenuView mainMenu = new MainMenuView(email, role);
                mainMenu.setVisible(true);

                loginView.dispose();
            } else {
                JOptionPane.showMessageDialog(loginView, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(loginView, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(loginView, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void handleCancel() {
        int response = JOptionPane.showConfirmDialog(loginView, "¿Seguro que quieres salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            loginView.dispose(); // Cerrar la ventana
        }
    }
}

    
