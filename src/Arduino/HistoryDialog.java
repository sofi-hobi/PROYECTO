package Arduino;

import java.awt.*;
import java.util.Map;
import javax.swing.*;

/**
 * Clase HistoryDialog
 * Muestra una ventana modal con un resumen del historial de códigos IR recibidos,
 * junto con la cantidad de veces que se han recibido.
 * 
 * Este historial se obtiene desde una base de datos consultada mediante ArduinoDataDAO.
 */
public class HistoryDialog extends JDialog {

    /**
     * Constructor del diálogo de historial.
     * 
     * @param parentFrame Ventana principal desde donde se invoca el historial.
     */
    public HistoryDialog(JFrame parentFrame) {
        super(parentFrame, "Historial de Comandos Recibidos", true);
        initUI();
    }

    /**
     * Método privado que inicializa y organiza la interfaz gráfica del historial.
     */
    private void initUI() {
        setSize(400, 400);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        // 🎨 Colores personalizados para el diseño
        Color fondo = new Color(245, 240, 255);         // Lila claro
        Color azulPrimario = new Color(0, 191, 255);    // Azul celeste
        Color textoOscuro = new Color(33, 33, 33);      // Gris oscuro

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(fondo);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título
        JLabel titleLabel = new JLabel("Resumen de Comandos Recibidos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(azulPrimario.darker());
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel de contenido donde se listan los comandos
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(fondo);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Obtener datos desde la base de datos
        Map<String, Integer> codigoCounts = ArduinoDataDAO.getCodigoCounts();

        if (codigoCounts.isEmpty()) {
            // Mensaje si aún no hay datos
            JLabel noDataLabel = new JLabel("No hay comandos registrados aún.");
            noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noDataLabel.setForeground(textoOscuro.darker());
            noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(Box.createVerticalGlue());
            contentPanel.add(noDataLabel);
            contentPanel.add(Box.createVerticalGlue());
        } else {
            // Mostrar cada código recibido y su cantidad
            for (Map.Entry<String, Integer> entry : codigoCounts.entrySet()) {
                String codigo = entry.getKey();
                int count = entry.getValue();

                JLabel entryLabel = new JLabel("  Código: " + codigo + "  -  Veces recibido: " + count);
                entryLabel.setFont(new Font("Monospaced", Font.PLAIN, 16));
                entryLabel.setForeground(textoOscuro);
                entryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                contentPanel.add(entryLabel);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        // Scroll por si hay muchos registros
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeButton.setBackground(new Color(255, 107, 107)); // Rojo suave
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.setBackground(fondo);
        southPanel.add(closeButton);
        mainPanel.add(southPanel, BorderLayout.SOUTH);

        // Agregar panel principal al diálogo
        add(mainPanel, BorderLayout.CENTER);
    }
}
