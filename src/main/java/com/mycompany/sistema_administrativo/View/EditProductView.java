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
public class EditProductView extends JDialog{
    
    //Declaramos los componentes de interfaz gráfica para cada variable y los botones
    
    private JTextField codeField, nameField, descriptionField, priceField, stockField;
    private JButton saveButton, cancelButton;
    
    public EditProductView(JFrame parent){
        // Diseno de la ventan
        super(parent, "Editar Producto", true);
        setSize(350, 250);
        setLayout(new GridLayout(6, 2, 10, 10));
        
        //Creacion de componentes y campos de entrada en la ventana 
        
        add(new JLabel("Codigo"));      // Etiqueta con el texto "CODIGO"
        codeField = new JTextField();  // Campo de texto para ingresar el codigo
        add(codeField);               // Agrega el campo de texto a la ventana
        
        add(new JLabel("Nombre:"));
        nameField = new JTextField();
        add(nameField);
        
        add(new JLabel("Descripción:"));
        descriptionField = new JTextField();
        add(descriptionField);
        
        add(new JLabel("Precio:"));
        priceField = new JTextField();
        add(priceField);
        
        add(new JLabel("Stock:"));
        stockField = new JTextField();
        add(stockField);
        
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);
        
    // Centrar ventana emergente
    setLocationRelativeTo(parent);
    }
    
    // Métodos para establecer los valores en los campos de texto
    public void setProductCode(String code) { codeField.setText(code); }
    public void setProductName(String name) { nameField.setText(name); }
    public void setProductDescription(String description) { descriptionField.setText(description); }
    public void setProductPrice(float price) { priceField.setText(String.valueOf(price)); }
    public void setProductStock(int stock) { stockField.setText(String.valueOf(stock)); }

    // Métodos para obtener los valores ingresados
    public String getProductCode() { return codeField.getText(); }
    public String getProductName() { return nameField.getText(); }
    public String getProductDescription() { return descriptionField.getText(); }
    public float getProductPrice() { return Float.parseFloat(priceField.getText()); }
    public int getProductStock() { return Integer.parseInt(stockField.getText()); }

    // Metodo para acceder a los botones
    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}
