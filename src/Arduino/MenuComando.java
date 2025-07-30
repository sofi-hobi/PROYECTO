package Arduino;

import java.awt.*;
import javax.swing.*;

/**
 * Clase MenuComando
 * Representa una ventana modal tipo diálogo que actúa como menú principal de comandos.
 * Desde aquí se puede acceder a otras funciones como el historial de comandos.
 * 
 * Hereda de JDialog y se muestra centrado sobre el JFrame principal.
 */
public class MenuComando extends JDialog {

    /**
     * Constructor del menú de comandos.
     * 
     * @param parentFrame La ventana principal (JFrame) desde la que se invoca este diálogo.
     */
    public MenuComando(JFrame parentFrame) {
        super(parentFrame, "Menú de Comandos", true);
        initUI();
    }

    /**
     * Inicializa la interfaz gráfica del diálogo.
     * Crea botones y define su estilo y comportamiento.
     */
    private void initUI() {
        setSize(350, 280);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        // Definición de colores usados en el diseño
        Color fondo = new Color(245, 240, 255);        // Lila claro
        Color azulPrimario = new Color(0, 191, 255);   // Azul celeste
        Color verdeControl = new Color(126, 217, 87);  // Verde lima
        Color rojoCerrar = new Color(255, 107, 107);   // Rojo suave
        Color textoOscuro = new Color(33, 33, 33);     // Gris oscuro

        // Panel contenedor principal
        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
        commandPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(azulPrimario.darker(), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        commandPanel.setBackground(fondo);

        // Botón: Abrir historial de comandos
        JButton historialBtn = crearBotonEstilizado("Historial de comandos", azulPrimario, textoOscuro);
        historialBtn.addActionListener(e -> {
            HistoryDialog historyDialog = new HistoryDialog(getParentFrame());
            historyDialog.setVisible(true);
        });

        // Botón: Cerrar ventana del menú
        JButton cerrarBtn = crearBotonEstilizado("Cerrar", rojoCerrar, Color.WHITE);
        cerrarBtn.addActionListener(e -> dispose());

        // Agregar botones al panel
        commandPanel.add(historialBtn);
        commandPanel.add(Box.createRigidArea(new Dimension(0, 12))); // Espaciado vertical
        commandPanel.add(cerrarBtn);

        // Añadir el panel al centro del diálogo
        add(commandPanel, BorderLayout.CENTER);
    }

    /**
     * Crea un botón estilizado con colores personalizados.
     * 
     * @param texto Texto que se mostrará en el botón.
     * @param fondo Color de fondo del botón.
     * @param textoColor Color del texto del botón.
     * @return El botón estilizado creado.
     */
    private JButton crearBotonEstilizado(String texto, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setMaximumSize(new Dimension(280, 40));
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    /**
     * Devuelve el JFrame padre desde donde se abrió este JDialog.
     * 
     * @return La ventana JFrame principal asociada a este diálogo.
     */
    private JFrame getParentFrame() {
        return (JFrame) SwingUtilities.getWindowAncestor(this);
    }
}
