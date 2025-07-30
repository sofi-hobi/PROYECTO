package Arduino;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class pantallainicial1 extends JPanel {
    Pantalla2 frame2 = new Pantalla2();

    public JButton botonInicio;
    public JButton jButton1;
    public JButton jButton2;
    public JPanel jPanel1;
    public JLabel titleLabel;
    public JLabel etiquetaNumero;
    public JLabel logoLabel;

    // Colores del tema
    private final Color PRIMARY_COLOR = new Color(89, 187, 206);
    private final Color SECONDARY_COLOR = new Color(43, 132, 192);
    private final Color BACKGROUND_COLOR = new Color(215, 255, 255);
    private final Color TEXT_COLOR = new Color(44, 62, 80);
    private final Color SUCCESS_COLOR = new Color(150, 203, 114);
    private final Color DANGER_COLOR = new Color(215, 112, 100);

    public pantallainicial1() {
        this.initComponents();
    }

    public Pantalla2 getPantalla2() {
        return frame2;
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = createCenterPanel();
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

        JPanel panelTituloLogo = new JPanel(new BorderLayout());
        panelTituloLogo.setBackground(BACKGROUND_COLOR);

        titleLabel = new JLabel("Escuela Politécnica Nacional", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(SECONDARY_COLOR);
        panelTituloLogo.add(titleLabel, BorderLayout.CENTER);

        logoLabel = new JLabel();
        logoLabel.setPreferredSize(new Dimension(100, 80));
        logoLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        try {
            BufferedImage logoImg = ImageIO.read(new File("logo.png"));
            ImageIcon logoIcon = new ImageIcon(logoImg.getScaledInstance(60, 50, Image.SCALE_SMOOTH));
            logoLabel.setIcon(logoIcon);
        } catch (IOException e) {
            logoLabel.setText("LOGO");
            logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 10));
            logoLabel.setForeground(PRIMARY_COLOR);
            logoLabel.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        }

        panelTituloLogo.add(logoLabel, BorderLayout.EAST);
        topPanel.add(panelTituloLogo);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(new EmptyBorder(20, 50, 20, 50));

        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                new EmptyBorder(40, 40, 40, 40)
        ));

        etiquetaNumero = new JLabel("Número: No disponible", SwingConstants.CENTER);
        etiquetaNumero.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        etiquetaNumero.setForeground(TEXT_COLOR);
        etiquetaNumero.setAlignmentX(Component.CENTER_ALIGNMENT);
        etiquetaNumero.setBorder(new EmptyBorder(0, 0, 30, 0));
        mainContentPanel.add(etiquetaNumero);

        JPanel buttonPanel = createButtonPanel();
        mainContentPanel.add(buttonPanel);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(mainContentPanel);
        centerPanel.add(Box.createVerticalGlue());

        return centerPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);

        botonInicio = createStyledButton(" Iniciar Sistema", SUCCESS_COLOR);
        botonInicio.addActionListener(this::botonInicioActionPerformed);

        jButton2 = createStyledButton(" Menú de Comandos", PRIMARY_COLOR);
        jButton2.addActionListener(this::jButton2ActionPerformed);

        jButton1 = createStyledButton(" Salir", DANGER_COLOR);
        jButton1.addActionListener(e -> System.exit(0));

        // Botón "Acerca de"
        JButton acercaDeBtn = new JButton("Acerca de:");
        acercaDeBtn.setBorderPainted(false);
        acercaDeBtn.setContentAreaFilled(false);
        acercaDeBtn.setFocusPainted(false);
        acercaDeBtn.setForeground(Color.DARK_GRAY);
        acercaDeBtn.setFont(new Font("Arial", Font.ITALIC, 12));
        acercaDeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        acercaDeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        acercaDeBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                """
                Proyecto Arduino - Interfaz Gráfica
                Grupo 1 N
                Integrantes:
                - Andres Galarraga.
                - Kevin Calapi.
                - Sofía Churuchumbi.
                - Kevin Charanchi.
                - Alan Carvajal.
                """,
                "Acerca de",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        // Agregar todos los botones
        buttonPanel.add(botonInicio);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(jButton2);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        buttonPanel.add(jButton1);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(acercaDeBtn); // Agregado

        return buttonPanel;
    }

    private JButton createStyledButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 45));
        button.setPreferredSize(new Dimension(250, 45));

        Color hoverColor = baseColor.darker();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
        return button;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(new EmptyBorder(20, 0, 0, 0));

        JLabel statusLabel = new JLabel("Sistema listo para usar");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(127, 140, 141));
        bottomPanel.add(statusLabel);

        return bottomPanel;
    }

    private void botonInicioActionPerformed(ActionEvent evt) {
        System.out.println("Has corrido el sistema");

        JFrame frame = new JFrame("Interfaz con Arduino");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(frame2);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        SwingUtilities.getWindowAncestor(this).dispose();
    }

    private void jButton2ActionPerformed(ActionEvent evt) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        MenuComando menu = new MenuComando(parentFrame);
        menu.setVisible(true);
    }

    public void mostrarNumero(String numero) {
        if (numero != null && !numero.isEmpty()) {
            this.etiquetaNumero.setText("Número: " + numero);
            this.etiquetaNumero.setForeground(PRIMARY_COLOR);
        } else {
            this.etiquetaNumero.setText("Número: No disponible");
            this.etiquetaNumero.setForeground(new Color(127, 140, 141));
        }
    }

    public void setLogo(String rutaLogo) {
        try {
            BufferedImage logoImg = ImageIO.read(new File(rutaLogo));
            ImageIcon logoIcon = new ImageIcon(logoImg.getScaledInstance(80, 60, Image.SCALE_SMOOTH));
            logoLabel.setIcon(logoIcon);
            logoLabel.setText("");
        } catch (IOException e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }
    }
}
