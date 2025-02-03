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
 * Vista para la gestión de productos
 * Similar a ManageUsersView
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

/**
 * Vista para la gestión de productos
 * Similar a ManageUsersView
 */
public class ManageProductsView extends JFrame {
    private JTable productsTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    public ManageProductsView() {
        setTitle("Gestión de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear modelo de la tabla con las columnas correctas
        String[] columnNames = {"ID", "Nombre", "Descripción", "Precio", "Stock"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productsTable);

        // Crear botones
        addButton = new JButton("Agregar Producto");
        editButton = new JButton("Editar Producto");
        deleteButton = new JButton("Eliminar Producto");

        // Diseño de la interfaz
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        add(panel);
    }

    // Métodos para acceder a los componentes
    public JTable getProductsTable() {
        return productsTable;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    // Método para cargar productos en la tabla
    public void loadProducts(Object[][] productsData) {
        tableModel.setRowCount(0); // Limpiar la tabla
        for (Object[] productRow : productsData) {
            tableModel.addRow(productRow);
        }
    }
}
