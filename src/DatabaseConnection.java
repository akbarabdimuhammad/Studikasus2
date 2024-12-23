package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/memberdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root"; // Ganti dengan username MySQL Anda
    private static final String PASSWORD = ""; // Ganti dengan password MySQL Anda
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection connect() {
            try {
                // Daftarkan driver JDBC secara eksplisit
                Class.forName("com.mysql.cj.jdbc.Driver"); // Pastikan driver JDBC terdaftar
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                
                // Menambahkan log untuk memastikan koneksi berhasil
                System.out.println("Connected to the database successfully.");
                return connection;
            } catch (ClassNotFoundException | SQLException e) {
                // Log jika terjadi error
                logger.log(Level.SEVERE, "Failed to connect to the database", e);
                System.out.println("Error: " + e.getMessage());
                return null;
            }
        }
        
}
