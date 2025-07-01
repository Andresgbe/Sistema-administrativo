/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.View;

import com.mycompany.sistema_administrativo.Controller.ManageUsersController;
import com.mycompany.sistema_administrativo.Controller.ManageProductsController;
import com.mycompany.sistema_administrativo.Controller.ManageSuppliersController;
import com.mycompany.sistema_administrativo.Controller.ManageClientsController;
import com.mycompany.sistema_administrativo.Controller.ManageTransactionsController;
import com.mycompany.sistema_administrativo.Controller.ManageProductsController;
import com.mycompany.sistema_administrativo.Controller.ManageTransactionsController;
import com.mycompany.sistema_administrativo.Utils.ChartsFactory;


import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.jfree.chart.JFreeChart;

public class MainMenuView extends JFrame {
    private ManageUsersView manageUsersView = null;
    private String userName;

    public MainMenuView(String userEmail, String userName, String userRole) {
        this.userName = userName;
        setTitle("Menú Principal - Clínica Santa Sofía");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(320);
        splitPane.setDividerSize(5);
        splitPane.setContinuousLayout(true);

        // Sidebar izquierdo
        JPanel sidebarPanel = createSidebar(userEmail, userName, userRole);
        sidebarPanel.setPreferredSize(new Dimension(250, getHeight()));
        splitPane.setLeftComponent(sidebarPanel);

        // Dashboard derecho (gráficos)
        DashboardPanel dashboardPanel = new DashboardPanel(
                ChartsFactory.createSalesBarChart(loadVentasPorProducto()),
                ChartsFactory.createInventoryPieChart(loadInventarioPorTipo())
        );
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        rightPanel.add(dashboardPanel, BorderLayout.CENTER);
        splitPane.setRightComponent(rightPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createLogoPanel(), BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createSidebar(String userEmail, String userName, String userRole) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(new Color(30, 30, 60)); // azul oscuro moderno
    panel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));

    JLabel labelUser = new JLabel("<html><div style='text-align:center;'>"
            + "<span style='color:#FFFFFF;font-size:14px;'>Usuario:</span><br>"
            + "<span style='color:#FFD700;font-weight:bold;'>" + userName + "</span></div></html>");
    JLabel labelRole = new JLabel("<html><div style='text-align:center;'>"
            + "<span style='color:#FFFFFF;font-size:14px;'>Rol:</span><br>"
            + "<span style='color:#00FFCC;font-weight:bold;'>" + userRole + "</span></div></html>");

    labelUser.setAlignmentX(Component.CENTER_ALIGNMENT);
    labelRole.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(labelUser);
    panel.add(Box.createVerticalStrut(10));
    panel.add(labelRole);
    panel.add(Box.createVerticalStrut(30));

    Font font = new Font("Segoe UI", Font.BOLD, 16);

    panel.add(createModernSidebarButton("Gestionar Usuarios", font, e -> openUsersView(userEmail)));
    panel.add(Box.createVerticalStrut(15));

    panel.add(createModernSidebarButton("Gestionar Proveedores", font, e -> {
        ManageSuppliersView view = new ManageSuppliersView();
        new ManageSuppliersController(view);
        view.setVisible(true);
    }));
    panel.add(Box.createVerticalStrut(15));

    panel.add(createModernSidebarButton("Gestionar Productos", font, e -> {
        ManageProductsView view = new ManageProductsView();
        new ManageProductsController(view);
        view.setVisible(true);
    }));
    panel.add(Box.createVerticalStrut(15));

    panel.add(createModernSidebarButton("Gestionar Clientes", font, e -> {
        ManageClientsView view = new ManageClientsView();
        new ManageClientsController(view);
        view.setVisible(true);
    }));
    panel.add(Box.createVerticalStrut(15));

    panel.add(createModernSidebarButton("Gestionar Transacciones", font, e -> {
        ManageTransactionsView view = new ManageTransactionsView();
        new ManageTransactionsController(view);
        view.setVisible(true);
    }));
    panel.add(Box.createVerticalStrut(30));

    panel.add(createModernSidebarButton("Cerrar Sesión", font, e -> {
        dispose();
        new LoginView().setVisible(true);
    }));

    return panel;
    }

    private JButton createModernSidebarButton(String text, Font font, java.awt.event.ActionListener action) {
    JButton btn = new JButton(text);
    btn.setFont(font);
    btn.setForeground(Color.WHITE);
    btn.setBackground(new Color(50, 50, 100));
    btn.setFocusPainted(false);
    btn.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
    btn.setAlignmentX(Component.CENTER_ALIGNMENT);

    // 🔹 TAMAÑO FIJO para que todos midan lo mismo:
    btn.setMaximumSize(new Dimension(280, 50));

    // Bordes redondeados
    btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 70, 140), 1),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
    ));

    // Hover effect
    btn.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            btn.setBackground(new Color(70, 70, 140));
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            btn.setBackground(new Color(50, 50, 100));
        }
    });

    btn.addActionListener(action);
    return btn;
}

    private JPanel createLogoPanel() {
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);

        try {
            ImageIcon logoIcon = new ImageIcon(getClass().getResource("/logo.png"));
            Image img = logoIcon.getImage().getScaledInstance(200, 60, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(img);

            JLabel logoLabel = new JLabel(logoIcon);
            logoPanel.add(logoLabel);
        } catch (Exception e) {
            logoPanel.add(new JLabel("Clínica Santa Sofía"));
        }

        return logoPanel;
    }

    /**
     * Consulta total de ventas agrupado por producto.
     * @return Map producto → totalAmount
     */
    private Map<String, Float> loadVentasPorProducto() {
        Map<String, Float> ventasPorProducto = new HashMap<>();
        String sql = """
            SELECT p.name AS producto, SUM(t.totalAmount) as total
            FROM transactions t
            JOIN productos p ON t.productID = p.id
            WHERE t.transactionType = 'venta'
            GROUP BY p.name
            """;

        try (Connection conn = com.mycompany.sistema_administrativo.Database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String producto = rs.getString("producto");
                float total = rs.getFloat("total");
                ventasPorProducto.put(producto, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventasPorProducto;
    }

    /**
     * Consulta stock agrupado por tipo de producto.
     * @return Map tipoProducto → stock total
     */
    private Map<String, Integer> loadInventarioPorTipo() {
        Map<String, Integer> inventarioPorTipo = new HashMap<>();
        String sql = """
            SELECT tipoProducto, SUM(stock) as total
            FROM productos
            GROUP BY tipoProducto
            """;

        try (Connection conn = com.mycompany.sistema_administrativo.Database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String tipo = rs.getString("tipoProducto");
                int total = rs.getInt("total");
                inventarioPorTipo.put(tipo, total);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventarioPorTipo;
    }

    private void openUsersView(String userEmail) {
        if (manageUsersView == null || !manageUsersView.isVisible()) {
            manageUsersView = new ManageUsersView();
            new ManageUsersController(manageUsersView, userEmail);
            manageUsersView.setVisible(true);
            manageUsersView.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    manageUsersView = null;
                }
            });
        }
    }
}