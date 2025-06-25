/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

/**
 *
 * @author andresgbe
 */
public class ManageUsersView extends JFrame {
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, backButton;
    
    public ManageUsersView() {
        setTitle("Gestión de Usuarios");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear modelo de la tabla con las columnas
        String[] columnNames = {"ID", "Nombre", "Correo", "Teléfono"};
        tableModel = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(usersTable);

        // Crear botón "Editar Usuario", "Agregar Usuario", "Eliminar Usuario" y "Volver"
        editButton = new JButton("Editar Usuario");
        addButton = new JButton("Agregar Usuario"); 
        deleteButton = new JButton("Eliminar Usuario"); 
        backButton = new JButton("Volver"); 

        // Diseño de la interfaz
        JPanel panel = new JPanel(new BorderLayout());  // Usamos BorderLayout para organizar la ventana
        panel.add(scrollPane, BorderLayout.CENTER); // Tabla en el centro

        // Crear el panel para el botón "Volver" y agregarlo al BorderLayout.NORTH (parte superior)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // FlowLayout para alinear a la izquierda
        topPanel.add(backButton);  // Agregar el botón "Volver"
        panel.add(topPanel, BorderLayout.NORTH); // Colocar el panel de "Volver" en la parte superior

        // Crear el panel de botones para "Agregar", "Editar" y "Eliminar", y agregarlo a BorderLayout.SOUTH (parte inferior)
        JPanel buttonsPanel = new JPanel();  // Los demás botones en la parte inferior
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH); // Colocar los botones en la parte inferior

        add(panel);  // Agregar todo al marco

        // Configurar el ActionListener para el botón "Volver"
        backButton.addActionListener(e -> {
            this.dispose();  // Cerrar la ventana actual
        });
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
    
    // Método para cargar usuarios en la tabla
public void loadUsers(Object[][] usersData) {
    System.out.println("🔹 Recibiendo datos para la tabla: " + usersData.length + " usuarios.");
    
    DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
    model.setRowCount(0); // Limpiar la tabla

    for (Object[] userRow : usersData) {
        System.out.println("🔹 Agregando fila a la tabla: " + Arrays.toString(userRow));
        model.addRow(userRow);
    }
}
}