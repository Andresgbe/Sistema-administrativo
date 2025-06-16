/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import com.mycompany.sistema_administrativo.Model.Suppliers;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EditProductView extends JDialog {

    private JTextField codeField, nameField, descriptionField, priceField, stockField;
    private JComboBox<Suppliers> supplierComboBox;
    private JButton saveButton, cancelButton;

    public EditProductView(JFrame parent){
        super(parent, "Editar Producto", true);
        setSize(350, 300);
        setLayout(new GridLayout(7, 2, 10, 10));

        add(new JLabel("Código:"));
        codeField = new JTextField();
        add(codeField);

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

        add(new JLabel("Proveedor:"));
        supplierComboBox = new JComboBox<>();
        add(supplierComboBox);

        saveButton = new JButton("Guardar");
        cancelButton = new JButton("Cancelar");
        add(saveButton);
        add(cancelButton);

        setLocationRelativeTo(parent);
    }

    public void setProductCode(String code) { codeField.setText(code); }
    public void setProductName(String name) { nameField.setText(name); }
    public void setProductDescription(String description) { descriptionField.setText(description); }
    public void setProductPrice(float price) { priceField.setText(String.valueOf(price)); }
    public void setProductStock(int stock) { stockField.setText(String.valueOf(stock)); }

    public void setSupplierOptions(List<Suppliers> suppliers) {
        supplierComboBox.removeAllItems();
        for (Suppliers supplier : suppliers) {
            supplierComboBox.addItem(supplier);
        }
    }

    public void setSelectedSupplier(String supplierId) {
        for (int i = 0; i < supplierComboBox.getItemCount(); i++) {
            Suppliers s = supplierComboBox.getItemAt(i);
            if (s.getId().equals(supplierId)) {
                supplierComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    public String getProductCode() { return codeField.getText(); }
    public String getProductName() { return nameField.getText(); }
    public String getProductDescription() { return descriptionField.getText(); }
    public float getProductPrice() { return Float.parseFloat(priceField.getText()); }
    public int getProductStock() { return Integer.parseInt(stockField.getText()); }

    public String getSelectedSupplierId() {
        Suppliers selected = (Suppliers) supplierComboBox.getSelectedItem();
        return selected != null ? selected.getId() : null;
    }

    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
}
