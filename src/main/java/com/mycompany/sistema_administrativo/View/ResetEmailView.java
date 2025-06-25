/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;

public class ResetEmailView extends JDialog {
    private JTextField emailField;
    private JButton validateButton, cancelButton;

    public ResetEmailView(JFrame parent) {
        super(parent, "Restablecer Contraseña", true);
        setSize(300, 150);
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("Correo electrónico:"));
        emailField = new JTextField();
        add(emailField);

        validateButton = new JButton("Validar");
        cancelButton = new JButton("Cancelar");

        add(validateButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    public String getEmail() {
        return emailField.getText();
    }

    public JButton getValidateButton() {
        return validateButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}