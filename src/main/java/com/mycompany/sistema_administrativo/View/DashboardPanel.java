/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

public class DashboardPanel extends JPanel {

    public DashboardPanel(JFreeChart barChart, JFreeChart pieChart) {
        setLayout(new GridLayout(2, 1, 10, 10));
        add(new ChartPanel(barChart));
        add(new ChartPanel(pieChart));
    }
}
