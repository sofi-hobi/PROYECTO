package Arduino;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import javax.swing.*;

public class arduinoc {

    public static void main(String[] args) {
        String nombrePuerto = "COM12";
        DatabaseInitializer.initializeDatabase(); // <-- Llama a la inicialización de la base de datos

        // Instancia de la pantalla
        pantallainicial1 pantalla = new pantallainicial1();
        Pantalla2 pantalla2 = pantalla.getPantalla2();

        JFrame frame = new JFrame("Interfaz con Arduino");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(pantalla);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Buscar el puerto
        SerialPort puerto = null;
        for (SerialPort p : SerialPort.getCommPorts()) {
            if (p.getSystemPortName().equals(nombrePuerto)) {
                puerto = p;
                break;
            }
        }

        if (puerto == null) {
            System.out.println("⚠️ Puerto " + nombrePuerto + " no encontrado.");
            return;
        }

        System.out.println("🔌 Usando el puerto: " + puerto.getSystemPortName());

        puerto.setBaudRate(9600);

        if (!puerto.openPort()) {
            System.out.println("❌ No se pudo abrir el puerto " + nombrePuerto);
            return;
        }

        System.out.println("✅ Puerto abierto: " + nombrePuerto);
        System.out.println("⏳ Esperando datos...");

        try {
            InputStream entrada = puerto.getInputStream();
            StringBuilder buffer = new StringBuilder();

            while (true) {
                if (entrada.available() > 0) {
                    char c = (char) entrada.read();

                    if (c == '\n') {
                        String linea = buffer.toString().trim();
                        buffer.setLength(0);

                        if (!linea.isEmpty()) {
                            System.out.println("Código recibido: " + linea);
                            ArduinoDataDAO.insertCodigo(linea);

                            switch (linea) {
                                case "0x7":
                                    pantalla2.mostrarNumero("vol_down");
                                    break;
                                case "0x16":
                                    pantalla2.mostrarNumero("0");
                                    break;
                                case "0xC":
                                    pantalla2.mostrarNumero("1");
                                    break;
                                case "0x18":
                                    pantalla2.mostrarNumero("2");
                                    break;
                                case "0x5E":
                                    pantalla2.mostrarNumero("3");
                                    break;
                                case "0x8":
                                    pantalla2.mostrarNumero("4");
                                    break;
                                case "0x1C":
                                    pantalla2.mostrarNumero("5");
                                    break;
                                case "0x5A":
                                    pantalla2.mostrarNumero("6");
                                    break;
                                case "0x42":
                                    pantalla2.mostrarNumero("7");
                                    break;
                                case "0x52":
                                    pantalla2.mostrarNumero("8");
                                    break;
                                case "0x4A":
                                    pantalla2.mostrarNumero("9");
                                    break;
                                case "0x45":
                                    pantalla2.mostrarNumero("ch_back");
                                    break;
                                case "0x46":
                                    pantalla2.mostrarNumero("ch_act");
                                    break;
                                case "0x47":
                                    pantalla2.mostrarNumero("ch_next");
                                    break;
                                case "0x44":
                                    pantalla2.mostrarNumero("rep_back");
                                    break;
                                case "0x40":
                                    pantalla2.mostrarNumero("rep_next");
                                    break;
                                case "0x43":
                                    pantalla2.mostrarNumero("rep_skip");
                                    break;
                                case "0x15":
                                    pantalla2.mostrarNumero("vol_up");
                                    break;
                                case "0x19":
                                    SwingUtilities.invokeLater(() -> pantalla.botonInicio.doClick());
                                    break;
                                case "0xD":
                                    SwingUtilities.invokeLater(() -> pantalla.jButton2.doClick());
                                    break;
                                case "0x9":
                                    SwingUtilities.invokeLater(() -> pantalla.jButton1.doClick());
                                    break;
                                default:
                                    System.out.println("❓ Código no reconocido: " + linea);
                                    break;
                            }
                        }
                    } else {
                        buffer.append(c);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            puerto.closePort();
            System.out.println("🔌 Puerto cerrado.");
        }
    }
}

        // Scanner scanner = new Scanner(System.in);
        // System.out.println("⌨️ Ingrese los códigos (uno por línea, ej: 0x7):");

        // while (true) {
        //     String linea = scanner.nextLine().trim();

        //     if (!linea.isEmpty()) {
        //         System.out.println("Código recibido: " + linea);

        //         ArduinoDataDAO.insertCodigo(linea); //Agregado para la database

        //         switch (linea) {
        //             case "0x7":
        //                 System.out.println(" Acción: Ejecutar botón desde Arduino");
        //                 javax.swing.SwingUtilities.invokeLater(() -> {
        //                     pantalla.botonInicio.doClick();  // Simula el clic
        //                 });
        //                 break;

        //             case "0x16":
        //                 System.out.println("Numero 0 visto en pantalla");
        //                 pantalla.mostrarNumero("0");
        //                 break;
        //             case "0xC":
        //                 System.out.println("Numero 1 visto en pantalla");
        //                 pantalla.mostrarNumero("1");
        //                 break;
        //             case "0x18":
        //                 System.out.println("Numero 2 visto en pantalla");
        //                 pantalla.mostrarNumero("2");
        //                 break;
        //             case "0x5E":
        //                 System.out.println("Numero 3 visto en pantalla");
        //                 pantalla.mostrarNumero("3");
        //                 break;
        //             case "0x8":
        //                 System.out.println("Numero 4 visto en pantalla");
        //                 pantalla.mostrarNumero("4");
        //                 break;
        //             case "0x1C":
        //                 System.out.println("Numero 5 visto en pantalla");
        //                 pantalla.mostrarNumero("5");
        //                 break;
        //             case "0x5A":
        //                 System.out.println("Numero 6 visto en pantalla");
        //                 pantalla.mostrarNumero("6");
        //                 break;
        //             case "0x42":
        //                 System.out.println("Numero 7 visto en pantalla");
        //                 pantalla.mostrarNumero("7");
        //                 break;
        //             case "0x52":
        //                 System.out.println("Numero 8 visto en pantalla");
        //                 pantalla.mostrarNumero("8");
        //                 break;
        //             case "0x4A":
        //                 System.out.println("Numero 9 visto en pantalla");
        //                 pantalla.mostrarNumero("9");
        //                 break;
        //             default:
        //                 System.out.println("❓ Código no reconocido: " + linea);
        //                 break;
        //         }
        //     }
        // }
    
