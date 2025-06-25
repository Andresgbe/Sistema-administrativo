/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.ManageUsersView;
import com.mycompany.sistema_administrativo.Model.Users;
import com.mycompany.sistema_administrativo.View.AddUserView;
import com.mycompany.sistema_administrativo.View.EditUserView;

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
    private String loggedInUserEmail;

public ManageUsersController(ManageUsersView manageUsersView, String loggedInUserEmail) {
    this.manageUsersView = manageUsersView;
    this.loggedInUserEmail = loggedInUserEmail;
    configureListeners();
    // Cargar usuarios al abrir la vista
    loadUsersFromDataBase();
    updateUsersTable();
}


private void configureListeners() {
    manageUsersView.getAddButton().addActionListener(e -> {
        System.out.println("🔹 Botón 'Agregar Usuario' presionado.");

        // Crear y mostrar la ventana emergente para agregar usuario
        AddUserView addUserView = new AddUserView(manageUsersView, this);
        addUserView.setVisible(true);


        // Si el usuario presiona "Agregar", obtenemos los datos ingresados
        addUserView.getAddButton().addActionListener(event -> {
            String name = addUserView.getUserName();
            String email = addUserView.getUserEmail();
            String phone = addUserView.getUserPhone();
            String password = addUserView.getUserPassword();
            String role = "administrador";


            // Validamos que los campos no estén vacíos
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(manageUsersView, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Creamos un objeto usuario
            Users newUser = new Users(name, email, phone, password, role);

            // Llamamos a la función para agregar usuario a la base de datos
            addUserToDatabase(newUser);

            // Cerramos la ventana emergente
            addUserView.dispose();
        });

        // Si el usuario presiona "Cancelar", simplemente cerramos la ventana
        addUserView.getCancelButton().addActionListener(event -> addUserView.dispose());
    });
    
    // --------------- Listener para eliminar usuarios --------------------------------------
    manageUsersView.getDeleteButton().addActionListener(e -> {
        int selectedRow = manageUsersView.getUsersTable().getSelectedRow();
    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(manageUsersView, "Selecciona un usuario para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener ID del usuario seleccionado
        String userId = manageUsersView.getUsersTable().getValueAt(selectedRow, 0).toString();

        // Confirmación
        int confirm = JOptionPane.showConfirmDialog(manageUsersView, 
            "¿Estás seguro de que deseas eliminar este usuario?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
    
        if (confirm == JOptionPane.YES_OPTION) {
            deleteUserFromDatabase(userId);
        }
    });

    // --------------- Listener de edicion de usuarios --------------------------------------
   manageUsersView.getEditButton().addActionListener(e -> {
    int selectedRow = manageUsersView.getUsersTable().getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(manageUsersView, "Selecciona un usuario para editar.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Obtener ID del usuario seleccionado
    String userId = manageUsersView.getUsersTable().getValueAt(selectedRow, 0).toString();

    // Obtener el usuario de la lista
    Users userToEdit = users.stream()
            .filter(user -> user.getId().equals(userId))
            .findFirst()
            .orElse(null);

    if (userToEdit == null) {
        JOptionPane.showMessageDialog(manageUsersView, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    String selectedEmail = manageUsersView.getUsersTable().getValueAt(selectedRow, 2).toString();

    if (!selectedEmail.equalsIgnoreCase(loggedInUserEmail)) {
        JOptionPane.showMessageDialog(manageUsersView,
            "No puedes editar otros usuarios. Solo puedes modificar tu propio perfil.",
            "Acceso denegado", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Crear y mostrar la ventana de edición
    EditUserView editUserView = new EditUserView(manageUsersView);
    editUserView.setUserName(userToEdit.getName());
    editUserView.setUserEmail(userToEdit.getEmail());
    editUserView.setUserPhone(userToEdit.getPhone());

    editUserView.getSaveButton().addActionListener(event -> {
        String name = editUserView.getUserName();
        String email = editUserView.getUserEmail();
        String phone = editUserView.getUserPhone();
        String password = editUserView.getUserPassword();
        String role = "administrador";


        // Validar que los campos no estén vacíos
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(manageUsersView, "Todos los campos excepto la contraseña son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar el usuario en la base de datos
        userToEdit.setName(name);
        userToEdit.setEmail(email);
        userToEdit.setPhone(phone);
        userToEdit.setRole(role);

        if (!password.isEmpty()) {
            userToEdit.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        }

        updateUserInDatabase(userToEdit);
        editUserView.dispose();
    });

    editUserView.getCancelButton().addActionListener(event -> editUserView.dispose());

    editUserView.setVisible(true);
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
    
public void addUserToDatabase(Users user) {
    if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPhone().isEmpty() || user.getPassword().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try (Connection connection = DatabaseConnection.getConnection()) {
        // Verificar duplicados de correo o teléfono
        String checkQuery = "SELECT COUNT(*) FROM usuarios WHERE email = ? OR phone = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, user.getEmail());
            checkStmt.setString(2, user.getPhone());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Ya existe un usuario con ese correo o teléfono.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Encriptar la contraseña antes de guardarla
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        if (!user.getPassword().startsWith("$2a$")) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        }

        // Asignar rol fijo
        user.setRole("administrador");

        String query = "INSERT INTO usuarios (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, hashedPassword);
            statement.setString(5, user.getRole());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Usuario agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadUsersFromDataBase();
                updateUsersTable();
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al agregar el usuario a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}


private void deleteUserFromDatabase(String userId) {
    String query = "DELETE FROM usuarios WHERE id = ?";
    
    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, userId);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(null, "Usuario eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Recargar la tabla
            loadUsersFromDataBase();
            updateUsersTable();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al eliminar el usuario de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void updateUserInDatabase(Users user) {
    try (Connection connection = DatabaseConnection.getConnection()) {
        // Validar duplicados excluyendo al propio usuario
        String checkQuery = "SELECT COUNT(*) FROM usuarios WHERE (email = ? OR phone = ?) AND id != ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setString(1, user.getEmail());
            checkStmt.setString(2, user.getPhone());
            checkStmt.setString(3, user.getId());
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Ya existe otro usuario con ese correo o teléfono.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String query = "UPDATE usuarios SET name = ?, email = ?, phone = ?, password = ?, role = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getRole());
            statement.setString(6, user.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadUsersFromDataBase();
                updateUsersTable();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el usuario para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al actualizar el usuario en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

}