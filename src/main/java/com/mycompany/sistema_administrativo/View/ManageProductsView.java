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
    private JButton editButton, addButton, deleteButton, backButton;
    private JComboBox<String> supplierComboBox;


    public ManageProductsView(){
        setTitle("Gestión de Productos");
        setSize(800,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Crear modelo de la tabla con las columnas de productos
        String[] columnNames = {"ID", "Código", "Nombre", "Descripción", "Precio", "Stock", "Proveedor"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        
        // Crear botón "Editar Producto", "Agregar Producto", "Eliminar Producto" y "Volver"
        editButton = new JButton("Editar Producto");
        addButton = new JButton("Agregar Producto"); 
        deleteButton = new JButton("Eliminar Producto"); 
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

    // Métodos para acceder a los botones y la tabla
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
    
    public void setSelectedSupplier(String supplierId) {
    for (int i = 0; i < supplierComboBox.getItemCount(); i++) {
        String item = supplierComboBox.getItemAt(i);
        if (item.startsWith(supplierId + " -")) {
            supplierComboBox.setSelectedIndex(i);
            break;
        }
    }
}

}
