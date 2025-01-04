/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author andresgbe
 */
public class MainMenuView extends JFrame {
    public MainMenuView(String userEmail, String userRole){
        setTitle("Menu Principal");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // panel para mostrar el menú
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Bienvenido: " + userEmail));
        panel.add(new JLabel("Rol: " + userRole));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JButton logoutButton = new JButton("Cerrar sesion");
        add(panel);
        
        //Acción para cerrar sesion
        logoutButton.addActionListener( e-> {
            dispose(); // cerrar menu principalú
            new LoginView().setVisible(true); //regresar al login
        });
    }
}
