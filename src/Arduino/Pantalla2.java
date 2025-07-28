package Arduino;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Pantalla2 extends JPanel implements KeyListener {
    private JLabel titleLabel;
    private JLabel instructionLabel;
    private JLabel pressedKeyLabel;
    private JLabel statusLabel;
    private JButton backButton;
    private JPanel centerPanel;
    private JPanel keyDisplayPanel;

    // Colores del tema (consistentes con pantallainicial1)
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private final Color TEXT_COLOR = new Color(44, 62, 80);
    private final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private final Color ACCENT_COLOR = new Color(155, 89, 182);
    private final Color CARD_COLOR = Color.WHITE;

    public Pantalla2() {
        this.initComponents();
        // Hacer el panel focusable para recibir eventos de teclado
        this.setFocusable(true);
        this.addKeyListener(this);
        this.requestFocusInWindow();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel superior con título
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Panel central con instrucciones y display
        centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        // Panel inferior con botón de regreso
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);

        // Asegurar que el panel reciba el foco
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                requestFocusInWindow();
            }
        });
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        // Título principal
        titleLabel = new JLabel("sDetector de Teclas", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(PRIMARY_COLOR);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);

        // Panel de instrucciones
        JPanel instructionPanel = createInstructionPanel();
        centerPanel.add(instructionPanel);

        centerPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Panel de visualización de teclas
        keyDisplayPanel = createKeyDisplayPanel();
        centerPanel.add(keyDisplayPanel);

        centerPanel.add(Box.createVerticalGlue());

        return centerPanel;
    }

    private JPanel createInstructionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            new EmptyBorder(25, 30, 25, 30)
        ));

        // Icono de instrucción
        JLabel iconLabel = new JLabel("⌨", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(iconLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Texto de instrucción principal
        instructionLabel = new JLabel("Presiona cualquier tecla", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        instructionLabel.setForeground(TEXT_COLOR);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(instructionLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Texto de instrucción secundaria
        JLabel subInstructionLabel = new JLabel("El sistema detectará automáticamente tu entrada", SwingConstants.CENTER);
        subInstructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subInstructionLabel.setForeground(new Color(127, 140, 141));
        subInstructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subInstructionLabel);

        return panel;
    }

    private JPanel createKeyDisplayPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);

        // Panel para mostrar la tecla presionada
        JPanel keyPanel = new JPanel(new BorderLayout());
        keyPanel.setBackground(ACCENT_COLOR);
        keyPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR.darker(), 2),
            new EmptyBorder(20, 20, 20, 20)
        ));
        keyPanel.setMaximumSize(new Dimension(300, 100));

        pressedKeyLabel = new JLabel("Esperando entrada...", SwingConstants.CENTER);
        pressedKeyLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        pressedKeyLabel.setForeground(Color.WHITE);
        keyPanel.add(pressedKeyLabel, BorderLayout.CENTER);

        panel.add(Box.createVerticalGlue());
        panel.add(keyPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Estado del sistema
        statusLabel = new JLabel("Sistema activo - Listo para detectar", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(SUCCESS_COLOR);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(statusLabel);

        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        // Botón para regresar
        backButton = createStyledButton("🔙 Regresar al Menú Principal");
        backButton.addActionListener(this::backButtonActionPerformed);
        bottomPanel.add(backButton);

        return bottomPanel;
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

        // Efectos hover
        Color hoverColor = PRIMARY_COLOR.darker();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private void backButtonActionPerformed(ActionEvent evt) {
        // Cerrar ventana actual y volver al menú principal
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window != null) {
            window.dispose();
        }
    }

    // Implementación de KeyListener
    @Override
    public void keyPressed(KeyEvent e) {
        updateKeyDisplay(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Opcional: manejar cuando se suelta la tecla
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Opcional: manejar caracteres tipados
    }

    private void updateKeyDisplay(KeyEvent e) {
        String keyText = KeyEvent.getKeyText(e.getKeyCode());
        char keyChar = e.getKeyChar();
        
        // Mostrar información de la tecla presionada
        if (Character.isLetterOrDigit(keyChar) || Character.isSpaceChar(keyChar)) {
            pressedKeyLabel.setText("Tecla: " + keyText + " ('" + keyChar + "')");
        } else {
            pressedKeyLabel.setText("Tecla: " + keyText);
        }

        // Cambiar el color del panel para dar feedback visual
        JPanel keyPanel = (JPanel) pressedKeyLabel.getParent();
        keyPanel.setBackground(SUCCESS_COLOR);
        
        // Actualizar estado
        statusLabel.setText("✓ Tecla detectada: " + keyText);
        statusLabel.setForeground(SUCCESS_COLOR);

        // Efecto de animación: volver al color original después de un tiempo
        Timer timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyPanel.setBackground(ACCENT_COLOR);
                statusLabel.setText("Sistema activo - Listo para detectar");
                statusLabel.setForeground(SUCCESS_COLOR);
            }
        });
        timer.setRepeats(false);
        timer.start();

        // Imprimir en consola (manteniendo funcionalidad original)
        System.out.println("Tecla presionada: " + keyText + " (Código: " + e.getKeyCode() + ")");
    }

    // Método para asegurar que el panel mantenga el foco
    @Override
    public void requestFocus() {
        super.requestFocus();
        this.requestFocusInWindow();
    }
}