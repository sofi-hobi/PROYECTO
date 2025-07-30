package Arduino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase DatabaseInitializer
 * Se encarga de crear la base de datos SQLite y la tabla de registros si no existen.
 * Esta clase debe ser ejecutada al inicio del programa principal para asegurar que la base de datos esté lista.
 */
public class DatabaseInitializer {

    // Ruta de la base de datos SQLite (en la raíz del proyecto)
    private static final String DB_URL = "jdbc:sqlite:./datos_arduino.db";

    /**
     * Método que inicializa la base de datos.
     * - Establece conexión
     * - Crea las tablas necesarias si no existen
     * - Cierra la conexión
     */
    public static void initializeDatabase() {
        Connection conn = null;
        try {
            // Establece conexión con SQLite
            conn = DriverManager.getConnection(DB_URL);
            System.out.println("Conexión a la base de datos SQLite establecida.");

            // Crea la tabla registros_arduino si no existe
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

    /**
     * Método privado que crea la tabla `registros_arduino` si no existe.
     * 
     * @param conn Conexión activa a la base de datos.
     */
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
