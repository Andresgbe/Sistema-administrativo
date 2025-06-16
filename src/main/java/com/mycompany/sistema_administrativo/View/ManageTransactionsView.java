/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

/**
 *
 * @author andresgbe
 */
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

public class ManageTransactionsView extends JFrame {
    private JTable transactionsTable;
    private DefaultTableModel tableModel;
    private JButton editButton, addButton, deleteButton, backButton;
    private JButton generateInvoiceButton; 


    public ManageTransactionsView() {
        setTitle("Gestión de Transacciones");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Definir columnas de la tabla
        String[] columnNames = {
            "ID", "Cliente", "Tipo", "Producto", "Cantidad", "Fecha", "Monto Total"
        };

        tableModel = new DefaultTableModel(columnNames, 0);
        transactionsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(transactionsTable);

        // Crear botones
        editButton = new JButton("Editar Transacción");
        addButton = new JButton("Agregar Transacción");
        deleteButton = new JButton("Eliminar Transacción");
        generateInvoiceButton = new JButton("Generar Factura");
        

        backButton = new JButton("Volver");

        // Layout general
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel superior con botón volver
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(backButton);
        panel.add(topPanel, BorderLayout.NORTH);

        // Panel inferior con los botones
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(addButton);
        bottomPanel.add(editButton);
        bottomPanel.add(deleteButton);
        bottomPanel.add(generateInvoiceButton);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);

        // Acción botón volver
        backButton.addActionListener(e -> this.dispose());
    }

    // Getters
    public JTable getTransactionsTable() {
        return transactionsTable;
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
    
    public JButton getGenerateInvoiceButton() {
    return generateInvoiceButton;
    }


    // Método para cargar los datos
    public void loadTransactions(Object[][] data) {
        System.out.println("🔹 Cargando transacciones en la tabla...");

        DefaultTableModel model = (DefaultTableModel) transactionsTable.getModel();
        model.setRowCount(0);

        for (Object[] row : data) {
            System.out.println("🔹 Transacción: " + Arrays.toString(row));
            model.addRow(row);
        }
    }
}
