package Arduino;

import java.awt.*;
import javax.swing.*;
import java.util.Map;
import java.util.Set; // Importación para usar Set en el Map.entrySet()

public class HistoryDialog extends JDialog {

    public HistoryDialog(JFrame parentFrame) {
        super(parentFrame, "Historial de Comandos Recibidos", true);
        initUI();
    }

    private void initUI() {
        setSize(400, 400); // Tamaño adecuado para el historial
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        // 🎨 Colores (puedes ajustar o reutilizar los de MenuComando si los haces públicos)
        Color fondo = new Color(245, 240, 255);
        Color azulPrimario = new Color(0, 191, 255);
        Color textoOscuro = new Color(33, 33, 33);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(fondo);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel("Resumen de Comandos Recibidos");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(azulPrimario.darker());
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel para mostrar los códigos y recuentos
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(fondo);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Espacio superior

        // Obtener los datos de la base de datos
        Map<String, Integer> codigoCounts = ArduinoDataDAO.getCodigoCounts();

        if (codigoCounts.isEmpty()) {
            JLabel noDataLabel = new JLabel("No hay comandos registrados aún.");
            noDataLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
            noDataLabel.setForeground(textoOscuro.darker());
            noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPanel.add(Box.createVerticalGlue()); // Para centrar verticalmente
            contentPanel.add(noDataLabel);
            contentPanel.add(Box.createVerticalGlue()); // Para centrar verticalmente
        } else {
            // Iterar sobre el mapa y añadir JLabels para cada código y su recuento
            for (Map.Entry<String, Integer> entry : codigoCounts.entrySet()) {
                String codigo = entry.getKey();
                int count = entry.getValue();

                JLabel entryLabel = new JLabel("  Código: " + codigo + "  -  Veces recibido: " + count);
                entryLabel.setFont(new Font("Monospaced", Font.PLAIN, 16)); // Fuente monospaced para códigos
                entryLabel.setForeground(textoOscuro);
                entryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
                contentPanel.add(entryLabel);
                contentPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Pequeño espacio entre entradas
            }
        }

        // Añadir el contentPanel dentro de un JScrollPane por si hay muchos datos
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Eliminar borde del scroll
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón de cerrar en la parte inferior
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

        add(mainPanel, BorderLayout.CENTER);
    }
}