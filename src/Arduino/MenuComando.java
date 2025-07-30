package Arduino;

import java.awt.*;
import javax.swing.*;

public class MenuComando extends JDialog {

    public MenuComando(JFrame parentFrame) {
        super(parentFrame, "Menú de Comandos", true);
        initUI();
    }

    private void initUI() {
        setSize(350, 280);
        setLocationRelativeTo(getParent());
        setLayout(new BorderLayout());

        // Colores 
        Color fondo = new Color(245, 240, 255);        // Lila claro
        Color azulPrimario = new Color(0, 191, 255);   // Azul celeste
        Color verdeControl = new Color(126, 217, 87);  // Verde lima
        Color rojoCerrar = new Color(255, 107, 107);   // Rojo suave
        Color textoOscuro = new Color(33, 33, 33);     // Gris oscuro

        // Panel principal
        JPanel commandPanel = new JPanel();
        commandPanel.setLayout(new BoxLayout(commandPanel, BoxLayout.Y_AXIS));
        commandPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(azulPrimario.darker(), 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        commandPanel.setBackground(fondo);

        // Botón: Historial de comandos
        JButton historialBtn = crearBotonEstilizado("Historial de comandos", azulPrimario, textoOscuro);
        historialBtn.addActionListener(e -> {
            HistoryDialog historyDialog = new HistoryDialog(getParentFrame()); // <--- CAMBIO AQUÍ
            historyDialog.setVisible(true);
        });


        // Botón: Simulador de Control
        // JButton simuladorBtn = crearBotonEstilizado("Simulador de Control", verdeControl, textoOscuro);
        // simuladorBtn.addActionListener(e -> {
        //     JOptionPane.showMessageDialog(this,
        //             "Simulador de control abierto (módulo en desarrollo).",
        //             "Simulador",
        //             JOptionPane.INFORMATION_MESSAGE);
        // });

        // Botón: Cerrar
        JButton cerrarBtn = crearBotonEstilizado("Cerrar", rojoCerrar, Color.WHITE);
        cerrarBtn.addActionListener(e -> dispose());

        // Agregar botones al panel
        commandPanel.add(historialBtn);
        commandPanel.add(Box.createRigidArea(new Dimension(0, 12)));
       // commandPanel.add(simuladorBtn);
       // commandPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        commandPanel.add(cerrarBtn);

        add(commandPanel, BorderLayout.CENTER);
    }

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

     private JFrame getParentFrame() {
        return (JFrame) SwingUtilities.getWindowAncestor(this)
     ;}
}

