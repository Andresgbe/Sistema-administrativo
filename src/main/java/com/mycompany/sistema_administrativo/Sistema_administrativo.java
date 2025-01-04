/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistema_administrativo;

import com.mycompany.sistema_administrativo.View.LoginView;
import com.mycompany.sistema_administrativo.Controller.LoginController;
/**
 *
 * @author andresgbe
 */
public class Sistema_administrativo {

    public static void main(String[] args) {
        LoginView loginView = new LoginView();
        new LoginController(loginView);
        loginView.setVisible(true); //Mostrarlo
    }
}
