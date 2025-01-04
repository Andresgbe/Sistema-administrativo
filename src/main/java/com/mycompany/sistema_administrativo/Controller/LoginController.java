/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.LoginView;
import com.mycompany.sistema_administrativo.View.MainMenuView;

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
        String email = loginView.getMail(); // Obtener correo ingresado
        String password = loginView.getPassword(); // Obtener contraseña ingresada

        try (Connection connection = DatabaseConnection.getConnection()) {
            // Consulta SQL para validar el correo y la contraseña
            String query = "SELECT * FROM usuarios WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Credenciales válidas
                String role = resultSet.getString("role");
                JOptionPane.showMessageDialog(loginView, "Inicio de sesión exitoso.\nBienvenido: " + email);

                // Abrir el menú principal
                MainMenuView mainMenu = new MainMenuView(email, role);
                mainMenu.setVisible(true);

                loginView.dispose(); // Cerrar la ventana de inicio de sesión
            } else {
                // Credenciales inválidas
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


    
