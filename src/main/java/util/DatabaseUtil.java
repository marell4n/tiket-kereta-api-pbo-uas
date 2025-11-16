/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author asus
 */
public class DatabaseUtil {
     // Atribut static connection
    private static Connection connection;

    // Properti koneksi (sesuai UML) - bisa di-hardcode untuk simulasi
    private static String dbUrl = "jdbc:mysql://localhost:3306/db_kereta";
    private static String dbUser = "root";
    private static String dbPass = "";

    /**
     * Method static untuk mendapatkan koneksi database.
     * Sesuai dengan diagram UML.
     * @return Connection object.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Simulasi koneksi. Ganti dengan driver dan URL database Anda.
                // Class.forName("com.mysql.cj.jdbc.Driver");
                // connection = DriverManager.getConnection(dbUrl, dbUser, dbPass);
                System.out.println("Simulasi koneksi database berhasil (connection object masih null).");
                // Untuk demo agar runnable tanpa setup DB, kita return null.
                return null;
            } catch (Exception e) {
                System.err.println("Koneksi database gagal: " + e.getMessage());
                return null;
            }
        }
        return connection;
    }
}
