/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.sistema_administrativo;
/**
 *
 * @author andresgbe
 */

import com.mycompany.sistema_administrativo.Database.DatabaseConnection;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            System.out.println("¡Conexión exitosa con la base de datos!");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error al conectar con la base de datos.");
        }
    }
}
