/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import com.mycompany.sistema_administrativo.Controller.ManageProductsController;
import com.mycompany.sistema_administrativo.Controller.ManageUsersController;

import javax.swing.*;
import java.awt.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 *
 * @author andresgbe
 */
public class MainMenuView extends JFrame {
    private ManageUsersView manageUsersView = null;
    private ManageProductsView manageProductsView = null;
   
    
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

        manageUsersButton.addActionListener(e -> {
            System.out.println("🔹 Botón Gestionar Usuarios presionado.");

            // Verificar si ya hay una ventana abierta
            if (manageUsersView == null || !manageUsersView.isVisible()) {
                manageUsersView = new ManageUsersView();
                new ManageUsersController(manageUsersView);
                manageUsersView.setVisible(true);

                // Agregar un listener para detectar cuando se cierra
                manageUsersView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("🔹 Ventana de gestión de usuarios cerrada.");
                        manageUsersView = null; // Resetear la variable
                    }
                });
            } else {
                System.out.println("⚠️ Ya hay una ventana abierta.");
            }
        });

        
       manageProductsButton.addActionListener(e -> {
            System.out.println("🔹 Botón Gestionar Productos presionado.");
            if (manageProductsView == null || !manageProductsView.isVisible()) {
                manageProductsView = new ManageProductsView();
                new ManageProductsController(manageProductsView);  // 🔥 Aquí enlazamos la vista con el controlador
                manageProductsView.setVisible(true);

                manageProductsView.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.out.println("🔹 Ventana de gestión de productos cerrada.");
                        manageProductsView = null;
                    }
                });
            } else {
                System.out.println("⚠️ Ya hay una ventana de productos abierta.");
            }
        });

        // Acción para cerrar sesión
        logoutButton.addActionListener(e -> {
            dispose(); // Cerrar menú principal
            new LoginView().setVisible(true); // Regresar al login
        });
    }
}
