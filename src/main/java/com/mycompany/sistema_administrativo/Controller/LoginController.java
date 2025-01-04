/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Model.Users;
import com.mycompany.sistema_administrativo.View.LoginView;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author andresgbe
 */
    public class LoginController {
    private LoginView loginView;
    private Map<String, String> userDatabase; // Simulación de una base de datos de usuarios (correo y contraseña)

    // Constructor
    public LoginController(LoginView loginView) {
        this.loginView = loginView;

        // Simular una base de datos de usuarios
        userDatabase = new HashMap<>();
        userDatabase.put("admin@example.com", "1234"); // Usuario administrador
        userDatabase.put("user@example.com", "abcd"); // Usuario empleado

        // Configurar acciones para los botones
        configureListeners();
    }

    // Configurar eventos de los botones
    private void configureListeners() {
        loginView.getLoginButton().addActionListener(e -> handleLogin());
        loginView.getCancelButton().addActionListener(e -> handleCancel());
    }

    // Lógica para el botón "Iniciar Sesión"
    private void handleLogin() {
        String email = loginView.getMail(); // Obtener correo ingresado
        String password = loginView.getPassword(); // Obtener contraseña ingresada

        // Validar credenciales
        if (userDatabase.containsKey(email) && userDatabase.get(email).equals(password)) {
            JOptionPane.showMessageDialog(loginView, "Inicio de sesión exitoso.\nBienvenido: " + email);
        } else {
            JOptionPane.showMessageDialog(loginView, "Correo o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Lógica para el botón "Cancelar"
    private void handleCancel() {
        int response = JOptionPane.showConfirmDialog(loginView, "¿Seguro que quieres salir?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            loginView.dispose(); // Cierra la ventana
        }
    }
}

    
