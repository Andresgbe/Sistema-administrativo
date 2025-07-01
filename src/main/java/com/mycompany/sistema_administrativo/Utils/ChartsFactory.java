/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Utils;

import java.util.Map;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ChartsFactory {

    public static JFreeChart createSalesBarChart(Map<String, Float> ventasPorProducto) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (var entry : ventasPorProducto.entrySet()) {
            dataset.addValue(entry.getValue(), "Ventas", entry.getKey());
        }
        return ChartFactory.createBarChart(
                "Ventas por Producto",
                "Producto",
                "Monto Total",
                dataset
        );
    }

    public static JFreeChart createInventoryPieChart(Map<String, Integer> stockPorTipo) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (var entry : stockPorTipo.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        return ChartFactory.createPieChart(
                "Inventario por Tipo de Producto",
                dataset
        );
    }
}
