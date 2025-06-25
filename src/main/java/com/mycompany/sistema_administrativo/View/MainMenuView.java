/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import com.mycompany.sistema_administrativo.Controller.ManageUsersController;
import com.mycompany.sistema_administrativo.Controller.ManageProductsController;
import com.mycompany.sistema_administrativo.Controller.ManageSuppliersController;
import com.mycompany.sistema_administrativo.Controller.ManageClientsController;
import com.mycompany.sistema_administrativo.Controller.ManageTransactionsController;

import com.mycompany.sistema_administrativo.Controller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainMenuView extends JFrame {
    private ManageUsersView manageUsersView = null;

    public MainMenuView(String userEmail, String userRole) {
        setTitle("Menú Principal");
        setSize(420, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fuente para todo
        Font titleFont = new Font("Segoe UI", Font.BOLD, 16);
        Font buttonFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Panel principal con padding
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Mensaje de bienvenida
        JLabel welcomeLabel = new JLabel("Bienvenido al sistema de la Clinica Santa Sofia ");
        welcomeLabel.setFont(titleFont);
        JLabel roleLabel = new JLabel("Rol: " + userRole);
        roleLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        panel.add(welcomeLabel);
        panel.add(roleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botones
        JButton manageUsersButton = createStyledButton("Gestionar Usuarios", buttonFont);
        JButton manageSuppliersButton = createStyledButton("Gestionar Proveedores", buttonFont);
        JButton manageProductsButton = createStyledButton("Gestionar Productos", buttonFont);
        JButton manageClientsButton = createStyledButton("Gestionar Clientes", buttonFont);
        JButton manageTransactionsButton = createStyledButton("Gestionar Transacciones", buttonFont);
        JButton logoutButton = createStyledButton("Cerrar Sesión", buttonFont);

        panel.add(manageUsersButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(manageSuppliersButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(manageProductsButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(manageClientsButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(manageTransactionsButton);
        panel.add(Box.createVerticalStrut(20));
        panel.add(logoutButton);

        add(panel);

        // Listeners
        manageUsersButton.addActionListener(e -> {
            if (manageUsersView == null || !manageUsersView.isVisible()) {
                manageUsersView = new ManageUsersView();
                new ManageUsersController(manageUsersView, userEmail);
                manageUsersView.setVisible(true);
                manageUsersView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        manageUsersView = null;
                    }
                });
            }
        });

        manageSuppliersButton.addActionListener(e -> {
            ManageSuppliersView view = new ManageSuppliersView();
            new ManageSuppliersController(view);
            view.setVisible(true);
        });

        manageProductsButton.addActionListener(e -> {
            ManageProductsView view = new ManageProductsView();
            new ManageProductsController(view);
            view.setVisible(true);
        });

        manageClientsButton.addActionListener(e -> {
            ManageClientsView view = new ManageClientsView();
            new ManageClientsController(view);
            view.setVisible(true);
        });

        manageTransactionsButton.addActionListener(e -> {
            ManageTransactionsView view = new ManageTransactionsView();
            new ManageTransactionsController(view);
            view.setVisible(true);
        });

        logoutButton.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }

    private JButton createStyledButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 35));
        return button;
    }
}