package Arduino;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ArduinoDataDAO {

    private static final String DB_URL = "jdbc:sqlite:./datos_arduino.db"; // Misma URL que en DatabaseInitializer

    /**
     * Inserta un nuevo registro de código de Arduino en la base de datos.
     * @param codigo El String del código recibido del Arduino (ej. "0x7").
     */
    public static void insertCodigo(String linea) {
        String sql = "INSERT INTO registros_arduino(codigo_arduino) VALUES(?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, linea); // Establece el String del código en el primer parámetro
            pstmt.executeUpdate();
            System.out.println(" Código '" + linea + "' guardado en la BD.");

        } catch (SQLException e) {
            System.err.println("X Error al guardar el código en la base de datos: " + e.getMessage());
        }
    }
    /**
     * Consulta la base de datos y devuelve el recuento de cada código registrado.
     * @return Un mapa donde la clave es el código (String) y el valor es el número de veces que apareció (Integer).
     */
    public static Map<String, Integer> getCodigoCounts() {
        Map<String, Integer> counts = new HashMap<>();
        String sql = "SELECT codigo_arduino, COUNT(*) as count FROM registros_arduino GROUP BY codigo_arduino ORDER BY count DESC";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String codigo = rs.getString("codigo_arduino");
                int count = rs.getInt("count");
                counts.put(codigo, count);
            }
            System.out.println("Historial de códigos recuperado de la BD.");

        } catch (SQLException e) {
            System.err.println("X Error al obtener el recuento de códigos: " + e.getMessage());
        }
        return counts;
    }



    /**
     * Método para conectar y obtener una conexión (útil si necesitas hacer más operaciones).
     * @return Objeto Connection si la conexión es exitosa, null en caso contrario.
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
        return conn;
    }

    /**
     * Método para cerrar una conexión a la base de datos.
     * @param conn La conexión a cerrar.
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println("Error al cerrar la conexión: " + ex.getMessage());
        }
    }
}