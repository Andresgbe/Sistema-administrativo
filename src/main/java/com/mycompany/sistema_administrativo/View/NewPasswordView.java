/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;

public class NewPasswordView extends JDialog {
    private JPasswordField passwordField, confirmPasswordField;
    private JButton resetButton, cancelButton;

    public NewPasswordView(JFrame parent) {
        super(parent, "Nueva Contraseña", true);
        setSize(300, 150);
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Nueva contraseña:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Confirmar contraseña:"));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);

        resetButton = new JButton("Restablecer");
        cancelButton = new JButton("Cancelar");
        add(resetButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmedPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}