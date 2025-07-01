/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistema_administrativo.Utils;

/**
 *
 * @author andresgbe
 */
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Desktop;
import java.io.File;

public class InvoiceGenerator {

    public static void generateInvoice(String transactionId, String productName, String clientName, String quantity, String total) {
        try {
            String fileName = "Factura_" + transactionId + ".html";
            FileWriter writer = new FileWriter(fileName);

            String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

            String htmlContent = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Factura %s</title>
                <style>
                    body { font-family: Arial, sans-serif; padding: 20px; }
                    h1 { text-align: center; }
                    table { width: 100%%; border-collapse: collapse; margin-top: 20px; }
                    td, th { border: 1px solid #ccc; padding: 8px; text-align: left; }
                    .footer { margin-top: 30px; font-size: 12px; text-align: center; color: gray; }
                </style>
            </head>
            <body>
                <h1>CLÍNICA SANTA SOFIA</h1>
                <p><strong>Factura N°:</strong> %s<br>
                   <strong>Fecha:</strong> %s<br>
                   <strong>Cliente:</strong> %s</p>

                <table>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Total</th>
                    </tr>
                    <tr>
                        <td>%s</td>
                        <td>%s</td>
                        <td>$%s</td>
                    </tr>
                </table>

                <div class="footer">
                    Gracias por su compra. Esta factura ha sido generada automáticamente por el sistema.
                </div>
            </body>
            </html>
            """.formatted(transactionId, transactionId, date, clientName, productName, quantity, total);

            writer.write(htmlContent);
            writer.close();

            // Abrir automáticamente en el navegador
            File htmlFile = new File(fileName);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(htmlFile.toURI());
            }

            System.out.println("📄 Factura generada y abierta: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
