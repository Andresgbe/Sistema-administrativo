/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo;

/**
 *
 * @author andresgbe
 */
import org.mindrot.jbcrypt.BCrypt;

public class TestBCrypt {
    public static void main(String[] args) {
        // Define la contraseña que quieres encriptar
        String password = "123"; 

        // Generar el hash de la contraseña
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Imprimir el hash generado
        System.out.println("Nuevo hash para 123: " + hashedPassword);
    }
}
