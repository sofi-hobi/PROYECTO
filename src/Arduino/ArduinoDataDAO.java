package Arduino;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase ArduinoDataDAO
 * Esta clase proporciona métodos estáticos para interactuar con la base de datos SQLite,
 * permitiendo guardar y consultar códigos recibidos desde Arduino.
 */
public class ArduinoDataDAO {

    // Ruta de la base de datos (debe coincidir con DatabaseInitializer)
    private static final String DB_URL = "jdbc:sqlite:./datos_arduino.db";

    /**
     * Inserta un nuevo código recibido desde Arduino en la tabla registros_arduino.
     *
     * @param linea Código recibido desde Arduino (ej. "0x7").
     */
    public static void insertCodigo(String linea) {
        String sql = "INSERT INTO registros_arduino(codigo_arduino) VALUES(?)";

        try (
            Connection conn = DriverManager.getConnection(DB_URL);
            PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, linea);
            pstmt.executeUpdate();
            System.out.println("✔ Código '" + linea + "' guardado en la BD.");
        } catch (SQLException e) {
            System.err.println("❌ Error al guardar el código en la base de datos: " + e.getMessage());
        }
    }

    /**
     * Recupera la cantidad de veces que ha sido recibido cada código desde Arduino.
     * 
     * @return Un mapa (Map) con los códigos como claves y la cantidad de repeticiones como valores.
     */
    public static Map<String, Integer> getCodigoCounts() {
        Map<String, Integer> counts = new HashMap<>();
        String sql = """
            SELECT codigo_arduino, COUNT(*) as count 
            FROM registros_arduino 
            GROUP BY codigo_arduino 
            ORDER BY count DESC
        """;

        try (
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)
        ) {
            while (rs.next()) {
                String codigo = rs.getString("codigo_arduino");
                int count = rs.getInt("count");
                counts.put(codigo, count);
            }
            System.out.println("📊 Historial de códigos recuperado correctamente.");
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener el recuento de códigos: " + e.getMessage());
        }

        return counts;
    }

    /**
     * Establece una conexión directa a la base de datos.
     * 
     * @return Un objeto Connection, o null si ocurre un error.
     */
    public static Connection connect() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos: " + e.getMessage());
            return null;
        }
    }

    /**
     * Cierra una conexión abierta a la base de datos.
     * 
     * @param conn La conexión activa que se desea cerrar.
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println("⚠️ Error al cerrar la conexión: " + ex.getMessage());
        }
    }
}