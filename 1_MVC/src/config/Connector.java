package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String URL = "jdbc:mysql://localhost:3306/recruit_db";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Jika menggunakan password di XAMPP, silakan diisi
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi Database Berhasil!");
            } catch (ClassNotFoundException | SQLException e) {
                System.err.println("Koneksi Gagal: " + e.getMessage());
            }
        }
        return connection;
    }
}