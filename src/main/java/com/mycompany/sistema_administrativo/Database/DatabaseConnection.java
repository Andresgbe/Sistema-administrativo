/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author andresgbe
 */
public class DatabaseConnection {
    // URL para conectar a MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_administrativo";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    // Método para obtener una conexión 
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}
