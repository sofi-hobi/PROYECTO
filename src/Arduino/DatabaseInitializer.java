package Arduino; // Asegúrate de que el paquete sea correcto

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    private static final String DB_URL = "jdbc:sqlite:./datos_arduino.db"; 

    public static void main(String[] args) {
        initializeDatabase();
    }

    public static void initializeDatabase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Conexión a la base de datos SQLite establecida.");

            createTables(conn); 

        } catch (SQLException e) {
            System.err.println("Error al conectar o inicializar la base de datos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Conexión a la base de datos SQLite cerrada.");
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }

    private static void createTables(Connection conn) {

        String sql = "CREATE TABLE IF NOT EXISTS registros_arduino (\n"
                   + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                   + " codigo_arduino TEXT NOT NULL,\n" 
                   + " marca_tiempo TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n"
                   + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'registros_arduino' creada o ya existe.");
        } catch (SQLException e) {
            System.err.println("Error al crear la tabla: " + e.getMessage());
        }
    }
}