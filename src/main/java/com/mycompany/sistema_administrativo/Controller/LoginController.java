/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.LoginView;
import com.mycompany.sistema_administrativo.View.MainMenuView;
import com.mycompany.sistema_administrativo.View.NewPasswordView;
import com.mycompany.sistema_administrativo.View.ResetEmailView;
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
        loginView.getResetPasswordButton().addActionListener(e -> handleReset());
    }

    private void handleLogin() {
    String email = loginView.getEmail(); // Obtener correo ingresado
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

            // Verificar la contraseña ingresada con la almacenada usando BCrypt
            boolean passwordMatches = BCrypt.checkpw(password, storedHashedPassword);

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
    
    private void handleReset() {
        ResetEmailView emailView = new ResetEmailView(loginView);

        emailView.getValidateButton().addActionListener(ev -> {
            String email = emailView.getEmail();
            if (email == null || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(emailView, "El correo no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT id FROM usuarios WHERE email = ?")) {
                stmt.setString(1, email);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    emailView.dispose();

                    // Mostrar nueva vista de contraseña
                    NewPasswordView passwordView = new NewPasswordView(loginView);

                    passwordView.getResetButton().addActionListener(resetEv -> {
                        String newPassword = passwordView.getPassword();
                        String confirmPassword = passwordView.getConfirmedPassword();

                        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                            JOptionPane.showMessageDialog(passwordView, "Ambos campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!newPassword.equals(confirmPassword)) {
                            JOptionPane.showMessageDialog(passwordView, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                    try (PreparedStatement updateStmt = conn.prepareStatement("UPDATE usuarios SET password = ? WHERE email = ?")) {
                        updateStmt.setString(1, hashedPassword);
                        updateStmt.setString(2, email);
                        updateStmt.executeUpdate();

                        JOptionPane.showMessageDialog(passwordView, "Contraseña restablecida con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        passwordView.dispose();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(passwordView, "Error al actualizar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    });

                    passwordView.getCancelButton().addActionListener(resetEv -> passwordView.dispose());
                    passwordView.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(emailView, "Correo no encontrado en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(emailView, "Error al validar el correo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        emailView.getCancelButton().addActionListener(ev -> emailView.dispose());
        emailView.setVisible(true);
    }
}


    
