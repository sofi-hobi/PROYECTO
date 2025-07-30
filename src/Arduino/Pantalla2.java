package Arduino;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Pantalla2 extends JPanel {
    private JLabel titleLabel;
    private JLabel instructionLabel;
    private JLabel statusLabel;
    private JLabel displayLabel;
    private JButton backButton;
    private JPanel keyDisplayPanel;

    // Colores del tema
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private final Color TEXT_COLOR = new Color(44, 62, 80);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color ACCENT_COLOR = new Color(155, 89, 182);
    private final Color CARD_COLOR = Color.WHITE;

    public Pantalla2() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(0, 0, 30, 0));

        titleLabel = new JLabel("Detector desde Arduino", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);

        panel.add(titleLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);

        instructionLabel = new JLabel("Esperando señal IR...", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        instructionLabel.setForeground(TEXT_COLOR);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(instructionLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        keyDisplayPanel = new JPanel(new BorderLayout());
        keyDisplayPanel.setBackground(ACCENT_COLOR);
        keyDisplayPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR.darker(), 2),
            new EmptyBorder(20, 20, 20, 20)
        ));
        keyDisplayPanel.setMaximumSize(new Dimension(300, 100));
        keyDisplayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        displayLabel = new JLabel("⏳ Aún no se ha detectado nada", SwingConstants.CENTER);
        displayLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        displayLabel.setForeground(Color.WHITE);
        keyDisplayPanel.add(displayLabel, BorderLayout.CENTER);

        statusLabel = new JLabel("Sistema activo - Listo para recibir señales", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(SUCCESS_COLOR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(keyDisplayPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(statusLabel);
        centerPanel.add(Box.createVerticalGlue());

        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        backButton = createStyledButton("🔙 Regresar al Menú Principal");
        backButton.addActionListener(evt -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        });
        bottomPanel.add(backButton);
        return bottomPanel;
    }
    public JButton getBackButton() {
    return backButton;
}
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 45));

        Color hoverColor = PRIMARY_COLOR.darker();
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    public void mostrarNumero(String numero) {
    keyDisplayPanel.removeAll();  // Limpiar contenido anterior

    if (numero != null && !numero.isEmpty()) {
        try {
            String rutaImagen = "imagenes_numeros/" + numero + ".png";  // Asegúrate de que esta carpeta exista
            ImageIcon icono = new ImageIcon(ImageIO.read(new File(rutaImagen))
                    .getScaledInstance(80, 100, Image.SCALE_SMOOTH));
            JLabel imagenLabel = new JLabel(icono);
            imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
            keyDisplayPanel.add(imagenLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            JLabel error = new JLabel("❌ Imagen no encontrada para: " + numero);
            error.setForeground(Color.WHITE);
            error.setHorizontalAlignment(SwingConstants.CENTER);
            keyDisplayPanel.add(error, BorderLayout.CENTER);
            System.err.println("Error cargando imagen para número '" + numero + "': " + e.getMessage());
        }
    } else {
        JLabel vacio = new JLabel("Número: No disponible");
        vacio.setForeground(Color.WHITE);
        vacio.setHorizontalAlignment(SwingConstants.CENTER);
        keyDisplayPanel.add(vacio, BorderLayout.CENTER);
    }

    // Cambiar color temporal para feedback
    keyDisplayPanel.setBackground(SUCCESS_COLOR);
    statusLabel.setText("✓ Señal recibida correctamente");
    statusLabel.setForeground(SUCCESS_COLOR);

    // Efecto visual: restaurar color después de 0.5s
    Timer timer = new Timer(500, e -> {
        keyDisplayPanel.setBackground(ACCENT_COLOR);
        statusLabel.setText("Sistema activo - Listo para recibir señales");
        statusLabel.setForeground(SUCCESS_COLOR);
    });
    timer.setRepeats(false);
    timer.start();

    keyDisplayPanel.revalidate();
    keyDisplayPanel.repaint();
    }
}
