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
public class ManageProductsView extends JFrame {
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private JButton editButton, addButton, deleteButton;
    
    public ManageProductsView(){
        setTitle("Gestión de Productos");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear modelo de la tabla con las columnas de productos
        String[] columnNames = {"ID", "Código", "Nombre", "Descripción", "Precio", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        
        // Crear botón "Editar Producto"
        editButton = new JButton("Editar Producto");
        addButton = new JButton("Agregar Producto"); 
        deleteButton = new JButton("Eliminar Producto"); 
     
        // Diseño de la interfaz
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER); // Tabla en el centro
        
        JPanel buttonsPanel = new JPanel();
                buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH); // Botón en la parte inferior

        add(panel);
    }

    // Métodos para acceder a la tabla y el botón
    public JTable getProductsTable() {
        return productsTable;
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
    
    public void loadProducts(Object[][] productsData) {
    System.out.println("🔹 Cargando productos en la tabla...");

    DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
    model.setRowCount(0); // Limpiar la tabla antes de agregar datos nuevos

    for (Object[] productRow : productsData) {
        System.out.println("🔹 Agregando producto: " + Arrays.toString(productRow));
        model.addRow(productRow); // Agregar cada fila de productos al modelo de la tabla
    }
}

}
