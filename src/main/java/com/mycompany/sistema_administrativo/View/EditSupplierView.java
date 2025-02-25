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
public class EditSupplierView extends JDialog{
    private JTextField nameField, phoneField, emailField, addressField, rifField;
    private JButton saveButton, cancelButton;
    
    public EditSupplierView(JFrame parent){
    //Diseno de la ventana
    super(parent, "Editar proveedor", true);
    setSize(350, 250);
    setLayout(new GridLayout(6, 2, 10, 10));
    
        add(new JLabel("Nombre"));      
        nameField = new JTextField();  
        add(nameField);   
        
        add(new JLabel("Telefono"));      
        phoneField = new JTextField();  
        add(phoneField);   
        
        add(new JLabel("Correo"));      
        emailField = new JTextField();  
        add(emailField);   
        
        add(new JLabel("Direccion"));      
        addressField = new JTextField();  
        add(addressField);   
        
        add(new JLabel("RIF"));      
        rifField = new JTextField();  
        add(rifField);
        
        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);
 
    // Centrar ventana emergente
    setLocationRelativeTo(parent);
    }

    public void setSupplierName(String name){ nameField.setText(name);}         
    public void setSupplierPhone(String phone){ phoneField.setText(phone);}   
    public void setSupplierEmail(String email){ emailField.setText(email);}            
    public void setSupplierAddress(String address){ addressField.setText(address);}
    public void setSupplierRif(int rif) { rifField.setText(String.valueOf(rif)); }
    
    public String getSupplierName() { return nameField.getText(); }
    public String getSupplierPhone() { return phoneField.getText(); }
    public String getSupplierEmail() { return emailField.getText(); }
    public String getSupplierAddress() { return addressField.getText(); }
    public int getSupplierRIF() { return Integer.parseInt(rifField.getText()); }
    
    // Metodo para acceder a los botones
    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}


