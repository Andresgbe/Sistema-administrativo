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
public class ProductsView extends JFrame {
    public ProductsView() {
        setTitle("Gestión de Productos");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Gestión de Productos");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Tabla para mostrar productos
        String[] columnNames = {"ID", "Nombre", "Precio", "Stock"};
        Object[][] data = {}; // Temporalmente vacío
        JTable productsTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(productsTable);
        panel.add(scrollPane);

        // Botones para CRUD
        JButton addButton = new JButton("Agregar Producto");
        JButton editButton = new JButton("Editar Producto");
        JButton deleteButton = new JButton("Eliminar Producto");

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(addButton);
        panel.add(editButton);
        panel.add(deleteButton);

        add(panel);
    }
}
