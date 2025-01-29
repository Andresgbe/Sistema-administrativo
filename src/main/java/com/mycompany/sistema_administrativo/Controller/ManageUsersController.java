/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.ManageUsersView;
import com.mycompany.sistema_administrativo.Model.Users;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 
import java.util.Arrays;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent; 
import java.awt.Cursor;

import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author andresgbe
 */
public class ManageUsersController {
    private ManageUsersView manageUsersView;
    private List<Users> users = new ArrayList<>();

public ManageUsersController(ManageUsersView manageUsersView) {
    this.manageUsersView = manageUsersView;
    
    
    System.out.println("🔹 ManageUsersController inicializado.");
    
    configureListeners();
    
    // Cargar usuarios al abrir la vista
    loadUsersFromDataBase();
    updateUsersTable();
}

    
    private void configureListeners(){
        manageUsersView.getAddButton().addActionListener(e -> {
            String userId = JOptionPane.showInputDialog(manageUsersView, "Ingresa el ID del usuario");
            String userName = JOptionPane.showInputDialog(manageUsersView, "Ingrese el nombre del usuario");
            String userEmail = JOptionPane.showInputDialog(manageUsersView, "Ingrese el correo del usuario");
            String userPhone = JOptionPane.showInputDialog(manageUsersView, "Ingrese el numero de telefono");
            String userPassword = JOptionPane.showInputDialog(manageUsersView, "Ingrese la contrasena");
            String userRole = JOptionPane.showInputDialog(manageUsersView, "Ingrese el rol del usuario");
            
            Users newUser = new Users(userId, userName, userEmail, userPhone, userPassword, userRole);
            users.add(newUser);
});
 
    }
    
private void loadUsersFromDataBase(){
    System.out.println("🔹 Cargando usuarios desde la base de datos...");

    String query = "SELECT id, name, email, phone, password, role FROM usuarios";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        users.clear(); // Limpiar la lista antes de cargar los nuevos usuarios
        List<Object[]> userList = new ArrayList<>();

        while (resultSet.next()) {
            Users user = new Users(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getString("password"),
                resultSet.getString("role")
            );

            users.add(user);

            // Agregar los datos en formato de tabla
            Object[] userRow = {
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
            };
            userList.add(userRow);

            System.out.println("🔹 Usuario agregado: " + Arrays.toString(userRow));
        }

        System.out.println("🔹 Total usuarios cargados: " + userList.size());

        // Enviar los datos a la vista
        manageUsersView.loadUsers(userList.toArray(new Object[0][0]));

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al cargar los usuarios de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    
    private void updateUsersTable(){
        System.out.println("🔹 Actualizando tabla de usuarios...");
        Object[][] data = new Object[users.size()][6];
        
        for (int i = 0; i < users.size(); i++){
            Users user = users.get(i);
            data[i][0] = user.getId();
            data[i][1] = user.getName();
            data[i][2] = user.getEmail();
            data[i][3] = user.getPhone();
            data[i][4] = user.getPassword();
            data[i][5] = user.getRole();
        }
        
        manageUsersView.loadUsers(data);
    }
    
private void addUserToDatabase(Users user) {
    // Validación: No permitir valores vacíos
    if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPhone().isEmpty() ||
        user.getPassword().isEmpty() || user.getRole().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        return; // Detener ejecución si hay un campo vacío
    }

    // Encriptar la contraseña antes de guardarla
    String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
    
    if (!user.getPassword().startsWith("$2a$")) { 
    // Si la contraseña NO empieza con "$2a$", significa que no está encriptada
    user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
}

    String query = "INSERT INTO usuarios (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setString(1, user.getName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPhone());
        statement.setString(4, hashedPassword); // Guardamos la contraseña encriptada
        statement.setString(5, user.getRole());

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Recargar los datos en la tabla
            loadUsersFromDataBase();
            updateUsersTable();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al agregar el usuario a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
}