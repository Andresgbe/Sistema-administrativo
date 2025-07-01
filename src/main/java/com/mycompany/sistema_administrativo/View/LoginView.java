/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private JButton resetPasswordButton;

    public LoginView() {
        setTitle("Inicio de sesión");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ====== Cargar imagen de fondo ======
        URL url = getClass().getResource("/com/mycompany/sistema_administrativo/assets/santa.jpg");
        System.out.println("URL encontrada → " + url);
        ImageIcon backgroundIcon;

        if (url == null) {
            System.out.println("⚠️ Imagen NO encontrada.");
            backgroundIcon = new ImageIcon(); // ícono vacío si no existe
        } else {
            System.out.println("✅ Imagen encontrada: " + url);
            backgroundIcon = new ImageIcon(url);
        }

        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new GridBagLayout());
        setContentPane(backgroundLabel);

        // ====== Panel semitransparente encima ======
        JPanel overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 150));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlayPanel.setOpaque(false);
        overlayPanel.setPreferredSize(new Dimension(400, 300));
        overlayPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fuentes
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // ====== Etiqueta: Correo ======
        gbc.gridx = 0;
        gbc.gridy = 0;
        overlayPanel.add(createLabel("Correo electrónico:", labelFont, Color.WHITE), gbc);

        gbc.gridy++;
        emailField = new JTextField(20);
        emailField.setFont(fieldFont);
        overlayPanel.add(emailField, gbc);

        // ====== Etiqueta: Contraseña ======
        gbc.gridy++;
        overlayPanel.add(createLabel("Contraseña:", labelFont, Color.WHITE), gbc);

        gbc.gridy++;
        passwordField = new JPasswordField(20);
        passwordField.setFont(fieldFont);
        overlayPanel.add(passwordField, gbc);

        // ====== Botones ======
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);

        loginButton = new ModernButton(
                "Iniciar Sesión",
                new Color(50, 102, 170),
                new Color(0, 122, 204),
                new Color(0, 72, 120)
        );
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        cancelButton = new ModernButton(
                "Cancelar",
                new Color(204, 0, 0),
                new Color(230, 20, 20),
                new Color(160, 0, 0)
        );
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        

        resetPasswordButton = new JButton("¿Olvidaste tu contraseña?");
        resetPasswordButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        resetPasswordButton.setForeground(Color.WHITE);
        resetPasswordButton.setContentAreaFilled(false);
        resetPasswordButton.setBorderPainted(false);
        resetPasswordButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        gbc.gridy++;
        overlayPanel.add(buttonPanel, gbc);

        gbc.gridy++;
        overlayPanel.add(resetPasswordButton, gbc);

        backgroundLabel.add(overlayPanel, new GridBagConstraints());
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
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

    /**
     * Botón moderno con bordes redondeados, color hover y pressed.
     */
    class ModernButton extends JButton {
        private Color hoverBackgroundColor;
        private Color pressedBackgroundColor;
        private Color originalBackground;

        public ModernButton(String text, Color backgroundColor, Color hoverColor, Color pressedColor) {
            super(text);
            super.setContentAreaFilled(false);
            setFocusPainted(false);
            setBackground(backgroundColor);
            setForeground(Color.WHITE);
            setFont(new Font("Segoe UI", Font.BOLD, 14));
            setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            this.hoverBackgroundColor = hoverColor;
            this.pressedBackgroundColor = pressedColor;
            this.originalBackground = backgroundColor;

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hoverBackgroundColor);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(originalBackground);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(pressedBackgroundColor);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(hoverBackgroundColor);
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.setColor(getBackground());
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border
        }
    }

}