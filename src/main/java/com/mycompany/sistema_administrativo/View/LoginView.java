/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JButton resetPasswordButton;

    public LoginView() {
        setTitle("Inicio de sesión");
        setSize(420, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializamos los componentes
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Iniciar sesión");
        cancelButton = new JButton("Cancelar");
        resetPasswordButton = new JButton("Has olvidado tu contrasena?");
        
        // Personalización de fuente
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);

        // Colores de los botones
        loginButton.setFont(buttonFont);
        loginButton.setBackground(new Color(0, 153, 76));
        loginButton.setForeground(Color.WHITE);

        cancelButton.setFont(buttonFont);
        cancelButton.setBackground(new Color(204, 0, 0));
        cancelButton.setForeground(Color.WHITE);

        // Configurar el diseño
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        setContentPane(contentPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta y campo de correo
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(createLabel("Correo electrónico:", labelFont), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPanel.add(emailField, gbc);

        // Etiqueta y campo de contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(createLabel("Contraseña:", labelFont), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(passwordField, gbc);

        // Botones centrados
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(resetPasswordButton);
        contentPanel.add(buttonPanel, gbc);
    }

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
    
    public JButton getResetPasswordButton() {
    return resetPasswordButton;
    }
}


