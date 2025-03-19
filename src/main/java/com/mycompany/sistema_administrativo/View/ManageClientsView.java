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
public class ManageClientsView extends JFrame {
    private JTable clientsTable;
    private DefaultTableModel tableModel;
    private JButton editButton, addButton, deleteButton, backButton;

    public ManageClientsView() {
        setTitle("Gestión de Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear modelo de la tabla con las columnas de clientes
        String[] columnNames = {"ID", "Nombre", "Identificación", "Correo", "Dirección", "Teléfono"};
        tableModel = new DefaultTableModel(columnNames, 0);
        clientsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(clientsTable);

        // Crear botón "Editar Cliente", "Agregar Cliente", "Eliminar Cliente" y "Volver"
        editButton = new JButton("Editar Cliente");
        addButton = new JButton("Agregar Cliente");
        deleteButton = new JButton("Eliminar Cliente");
        backButton = new JButton("Volver");

        // Diseño de la interfaz
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Crear el panel para el botón "Volver" y agregarlo al BorderLayout.NORTH
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);
        panel.add(topPanel, BorderLayout.NORTH);

        // Crear el panel de botones para "Agregar", "Editar" y "Eliminar"
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        add(panel);

        // Configurar el ActionListener para el botón "Volver"
        backButton.addActionListener(e -> {
            this.dispose(); // Cerrar la ventana actual
        });
    }

    // Métodos para acceder a los botones y la tabla
    public JTable getClientsTable() {
        return clientsTable;
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

    public void loadClients(Object[][] clientsData) {
        System.out.println("🔹 Cargando clientes en la tabla...");

        DefaultTableModel model = (DefaultTableModel) clientsTable.getModel();
        model.setRowCount(0); // Limpiar la tabla antes de agregar datos nuevos

        for (Object[] clientRow : clientsData) {
            System.out.println("🔹 Agregando cliente: " + Arrays.toString(clientRow));
            model.addRow(clientRow); // Agregar cada fila de clientes al modelo de la tabla
        }
    }
}

