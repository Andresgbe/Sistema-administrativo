/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import com.mycompany.sistema_administrativo.View.ProductsView;
import com.mycompany.sistema_administrativo.Controller.ManageUsersController;


import javax.swing.*;
import java.awt.*;

/**
 *
 * @author andresgbe
 */
public class MainMenuView extends JFrame {
    public MainMenuView(String userEmail, String userRole) {
        setTitle("Menú Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel para mostrar el menú
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Mensaje de bienvenida
        panel.add(new JLabel("Bienvenido: " + userEmail));
        panel.add(new JLabel("Rol: " + userRole));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botones de navegación
        JButton manageUsersButton = new JButton("Gestionar Usuarios");
        JButton manageProductsButton = new JButton("Gestionar Productos");
        JButton logoutButton = new JButton("Cerrar Sesión");

        panel.add(manageUsersButton);
        panel.add(manageProductsButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(logoutButton);

        add(panel);

        // Acción para abrir la gestión de usuarios
        manageUsersButton.addActionListener(e -> {
            new ManageUsersView().setVisible(true);
        });

        manageUsersButton.addActionListener(e -> {
             System.out.println("🔹 Botón Gestionar Usuarios presionado.");
          ManageUsersView manageUsersView = new ManageUsersView();
          new ManageUsersController(manageUsersView); // Instanciar el controlador
          manageUsersView.setVisible(true);
        });
        
        // Acción para abrir la gestión de productos
        manageProductsButton.addActionListener(e -> {
            new ProductsView().setVisible(true);
        });

        // Acción para cerrar sesión
        logoutButton.addActionListener(e -> {
            dispose(); // Cerrar menú principal
            new LoginView().setVisible(true); // Regresar al login
        });
    }
}
