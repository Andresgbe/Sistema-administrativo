/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Controller;

/**
 *
 * @author andresgbe
 */
import com.mycompany.sistema_administrativo.Database.DatabaseConnection;
import com.mycompany.sistema_administrativo.View.ManageClientsView;
import com.mycompany.sistema_administrativo.Model.Clients;
import com.mycompany.sistema_administrativo.View.EditClientView;
import com.mycompany.sistema_administrativo.View.AddClientView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 
 */
public class ManageClientsController {
    private ManageClientsView manageClientsView;
    private List<Clients> clients = new ArrayList<>();

    public ManageClientsController(ManageClientsView manageClientsView) {
        this.manageClientsView = manageClientsView;
        configureListeners();
        loadClientsFromDatabase(); // Cargar clientes en la tabla al abrir
        updateClientsTable();
    }

    private void configureListeners() {
        manageClientsView.getEditButton().addActionListener(e -> {
            int selectedRow = manageClientsView.getClientsTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageClientsView, "Selecciona un cliente para editar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener ID del cliente seleccionado
            String clientId = manageClientsView.getClientsTable().getValueAt(selectedRow, 0).toString();

            // Buscar el cliente en la lista
            Clients clientToEdit = clients.stream()
                    .filter(client -> client.getId().equals(clientId))
                    .findFirst()
                    .orElse(null);

            if (clientToEdit == null) {
                JOptionPane.showMessageDialog(manageClientsView, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (clientToEdit.getId().equals("1")) {
                JOptionPane.showMessageDialog(manageClientsView,
                    "Este cliente está protegido y no puede ser editado.",
                    "Acción no permitida",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Crear y mostrar la ventana de edición
            EditClientView editClientView = new EditClientView(manageClientsView);
            editClientView.setClientName(clientToEdit.getName());
            editClientView.setClientIdentification(clientToEdit.getIdentification());
            editClientView.setClientEmail(clientToEdit.getEmail());
            editClientView.setClientAddress(clientToEdit.getAddress());
            editClientView.setClientPhone(clientToEdit.getPhone());

            editClientView.getSaveButton().addActionListener(event -> {
                String name = editClientView.getClientName();
                String identification = editClientView.getClientIdentification();
                String email = editClientView.getClientEmail();
                String address = editClientView.getClientAddress();
                String phone = editClientView.getClientPhone();

                if (name.isEmpty() || identification.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(manageClientsView, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            if (isDuplicateClient(identification, email, phone, clientToEdit.getId())) {
                JOptionPane.showMessageDialog(manageClientsView, 
                    "Ya existe otro cliente con la misma cédula, correo o teléfono.", 
                    "Duplicado detectado", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
                clientToEdit.setName(name);
                clientToEdit.setIdentification(identification);
                clientToEdit.setEmail(email);
                clientToEdit.setAddress(address);
                clientToEdit.setPhone(phone);

                updateClientInDatabase(clientToEdit);
                editClientView.dispose();
            });

            editClientView.getCancelButton().addActionListener(event -> editClientView.dispose());

            editClientView.setVisible(true);
        });

        manageClientsView.getAddButton().addActionListener(e -> {
            AddClientView addClientView = new AddClientView(manageClientsView);

            // Acción de guardar cliente
            addClientView.getSaveButton().addActionListener(event -> {
                String name = addClientView.getClientName();
                String identification = addClientView.getClientIdentification();
                String email = addClientView.getClientEmail();
                String address = addClientView.getClientAddress();
                String phone = addClientView.getClientPhone();

                // Validación de campos
                if (name.isEmpty() || identification.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(manageClientsView, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validar duplicados excluyendo su propio ID
            if (isDuplicateClient(identification, email, phone, null)) {
                JOptionPane.showMessageDialog(manageClientsView, 
                    "Ya existe un cliente con la misma cédula, correo o teléfono.", 
                    "Duplicado detectado", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }


                // Crear el objeto cliente
                Clients newClient = new Clients(name, identification, email, address, phone);
                addClientToDatabase(newClient);  // Insertar el cliente en la base de datos
                addClientView.dispose();  // Cerrar la ventana
            });

            // Acción de cancelar
            addClientView.getCancelButton().addActionListener(event -> addClientView.dispose());

            addClientView.setVisible(true);
        });

        // Vinculamos el botón "Eliminar Cliente"
        manageClientsView.getDeleteButton().addActionListener(e -> {
            int selectedRow = manageClientsView.getClientsTable().getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(manageClientsView, "Selecciona un cliente para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el ID del cliente seleccionado
            String clientId = manageClientsView.getClientsTable().getValueAt(selectedRow, 0).toString();
            deleteClientFromDatabase(clientId); // Eliminar cliente de la base de datos
        });
    }

    private void loadClientsFromDatabase() {
        System.out.println("🔹 Cargando clientes desde la base de datos...");

        String query = "SELECT id, name, identification, email, address, phone FROM clientes";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            clients.clear(); // Limpiar la lista antes de cargar los nuevos clientes
            List<Object[]> clientList = new ArrayList<>();

            while (resultSet.next()) {
                Clients client = new Clients(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("identification"),
                        resultSet.getString("email"),
                        resultSet.getString("address"),
                        resultSet.getString("phone")
                );

                clients.add(client);
                // Agregar los datos en formato de tabla
                Object[] clientRow = {
                        client.getId(),
                        client.getName(),
                        client.getIdentification(),
                        client.getEmail(),
                        client.getAddress(),
                        client.getPhone()
                };
                clientList.add(clientRow);
                System.out.println("🔹 Cliente agregado: " + client.getName());
            }

            System.out.println("🔹 Total clientes cargados: " + clientList.size());
            manageClientsView.loadClients(clientList.toArray(new Object[0][0]));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los clientes de la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateClientsTable() {
        System.out.println("🔹 Actualizando tabla de clientes...");

        Object[][] data = new Object[clients.size()][6];

        for (int i = 0; i < clients.size(); i++) {
            Clients client = clients.get(i);
            data[i][0] = client.getId();
            data[i][1] = client.getName();
            data[i][2] = client.getIdentification();
            data[i][3] = client.getEmail();
            data[i][4] = client.getAddress();
            data[i][5] = client.getPhone();
        }

        manageClientsView.loadClients(data);
    }

    private void updateClientInDatabase(Clients client) {
        String query = "UPDATE clientes SET name = ?, identification = ?, email = ?, address = ?, phone = ? WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getIdentification());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getAddress());
            statement.setString(5, client.getPhone());
            statement.setString(6, client.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadClientsFromDatabase();
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addClientToDatabase(Clients client) {
        String query = "INSERT INTO clientes (name, identification, email, address, phone) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, client.getName());
            statement.setString(2, client.getIdentification());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getAddress());
            statement.setString(5, client.getPhone());

            statement.executeUpdate();
            loadClientsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el cliente en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
private void deleteClientFromDatabase(String clientId) {
            // Proteger cliente "Clínica"
        if (clientId.equals("1")) {
            JOptionPane.showMessageDialog(null,
                "Este cliente está protegido y no puede ser eliminado.",
                "Acción no permitida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

    
    String checkQuery = "SELECT COUNT(*) FROM transactions WHERE customerID = ?";
    String deleteQuery = "DELETE FROM clientes WHERE id = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {

        checkStmt.setString(1, clientId);
        ResultSet rs = checkStmt.executeQuery();
        rs.next();
        int transactionCount = rs.getInt(1);

        if (transactionCount > 0) {
            JOptionPane.showMessageDialog(null,
                "Este cliente tiene " + transactionCount + " transacciones registradas y no puede ser eliminado.",
                "Eliminación no permitida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
            deleteStmt.setString(1, clientId);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                loadClientsFromDatabase(); // Recargar la lista de clientes
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al eliminar el cliente en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private boolean isDuplicateClient(String identification, String email, String phone, String excludedId) {
        String query = "SELECT COUNT(*) FROM clientes WHERE (identification = ? OR email = ? OR phone = ?)";

        // Si estamos editando, excluimos el propio cliente
        if (excludedId != null) {
            query += " AND id != ?";
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, identification);
            statement.setString(2, email);
            statement.setString(3, phone);
            if (excludedId != null) {
                statement.setString(4, excludedId);
            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al validar duplicados.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }



}

