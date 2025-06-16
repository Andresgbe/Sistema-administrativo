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
public class ManageSuppliersView extends JFrame{
    private JTable suppliersTable;
    private DefaultTableModel tableModel;
    private JButton editButton, addButton, deleteButton, backButton;
    
    public ManageSuppliersView(){
        setTitle("Gestión de Proveedor");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        String[] columnNames = {"ID", "Nombre", "Teléfono", "Correo", "Dirección", "RIF"};
        tableModel = new DefaultTableModel(columnNames, 0);
        suppliersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(suppliersTable);
        
        // Crear botón "Editar proveedor", "Agregar proveedor", "Eliminar proveedor" y "Volver"
        editButton = new JButton("Editar Proveedor");
        addButton = new JButton("Agregar proveedor"); 
        deleteButton = new JButton("Eliminar Proveedor"); 
        backButton = new JButton("Volver"); 
        
        // Diseño de la interfaz
        JPanel panel = new JPanel(new BorderLayout());  // Usamos BorderLayout para organizar la ventana
        panel.add(scrollPane, BorderLayout.CENTER); // Tabla en el centro
        
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

    public JTable getSuppliersTable() {
        return suppliersTable;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
    
    
    public void loadSuppliers(Object[][] suppliersData) {
        System.out.println("🔹 Cargando proveedores en la tabla...");

        DefaultTableModel model = (DefaultTableModel) suppliersTable.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de agregar datos nuevos

        for (Object[] supplierRow : suppliersData) {
            System.out.println("🔹 Agregando proveedor: " + Arrays.toString(supplierRow));
            model.addRow(supplierRow); // Agregar cada fila de proveedor al modelo de la tabla
        }
    }
}
