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
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    
    public ManageUsersView() {
        setTitle("Gestión de Usuarios");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear modelo de la tabla con las columnas
        String[] columnNames = {"ID", "Nombre", "Correo", "Teléfono", "Rol"};
        tableModel = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(usersTable);

        // Crear botones
        addButton = new JButton("Agregar Usuario");
        editButton = new JButton("Editar Usuario");
        deleteButton = new JButton("Eliminar Usuario");

        // Diseño de la interfaz
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER); // Tabla en el centro

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