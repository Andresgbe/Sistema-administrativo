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
public class ManageUsersView extends JFrame {
    private JTable usersTable;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    public ManageUsersView() {
        setTitle("Gestión de Usuarios");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear componentes
        usersTable = new JTable(); // La tabla para mostrar usuarios
        addButton = new JButton("Agregar Usuario");
        editButton = new JButton("Editar Usuario");
        deleteButton = new JButton("Eliminar Usuario");

        // Diseño de la ventana
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(usersTable), BorderLayout.CENTER); // Tabla en el centro

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH); // Botones en la parte inferior

        add(panel);
    }
    // Métodos para acceder a los componentes
    public JTable getUsersTable(){
        return usersTable;
    }
    
    public JButton getAddButton(){
        return addButton;
    }
    
    public JButton getEditButton(){
        return editButton;
    }
    
    public JButton getDeleteButton(){
        return deleteButton;
    }  
}