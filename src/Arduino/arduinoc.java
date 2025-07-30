package Arduino;
import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import Arduino.pantallainicial1;
import Arduino.ArduinoDataDAO; //Agregado para la database
import Arduino.pantallainicial1;
public class arduinoc {

    public static void main(String[] args) {
        String nombrePuerto = "COM12";

        // Instancia de la pantalla (con el botón)
        pantallainicial1 pantalla = new pantallainicial1();
        Pantalla2 pantalla2 = pantalla.getPantalla2(); 
        javax.swing.JFrame frame = new javax.swing.JFrame("Interfaz con Arduino");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(pantalla);
        frame.setLocationRelativeTo(null);
        frame.pack();
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
                                    System.out.println("Bajar el Volumen Presionado");
                                    pantalla2.mostrarNumero("vol_down");
                                        
                                    break;

                                case "0x16":
                                
                                    System.out.println("Numero 0 visto en pantalla");
                                    pantalla2.mostrarNumero("0");
                                    break;
                                case "0xC":
                                
                                    System.out.println("Numero 1 visto en pantalla");
                                    pantalla2.mostrarNumero("1");
                                    break;
                                case "0x18":
                                
                                    System.out.println("Numero 2 visto en pantalla");
                                    pantalla2.mostrarNumero("2");
                                    break;
                                case "0x5E":
                                
                                    System.out.println("Numero3 visto en pantalla");
                                    pantalla2.mostrarNumero("3");                
                                    break;
                                case "0x8":
                                
                                    System.out.println("Numero 4 visto en pantalla");
                                    pantalla2.mostrarNumero("4");
                                    break;
                                 case "0x1C":
                                
                                    System.out.println("Numero 5 visto en pantalla");
                                    pantalla2.mostrarNumero("5");
                                    break;
                                 case "0x5A":
                                
                                    System.out.println("Numero 6 visto en pantalla");
                                    pantalla2.mostrarNumero("6");
                                    break;
                                 case "0x42":
                                
                                    System.out.println("Numero 7 visto en pantalla");
                                    pantalla2.mostrarNumero("7");
                                    break;
                                 case "0x52":
                                
                                    System.out.println("Numero 8 visto en pantalla");
                                    pantalla2.mostrarNumero("8");
                                    break;
                                case "0x4A":
                                
                                    System.out.println("Numero 9 visto en pantalla");
                                    pantalla2.mostrarNumero("9");
                                    break;
                                case "0x45":
                                    System.out.println("Volver al Canal Anterior");   
                                    pantalla2.mostrarNumero("ch_back");
                                     break;
                                case "0x46":
                                    System.out.println("Canal Actual");   
                                    pantalla2.mostrarNumero("ch_act");
                                     break;
                                case "0x47":
                                    System.out.println("Canal Proximo");   
                                    pantalla2.mostrarNumero("ch_next");
                                     break;
                                case "0x44":
                                    System.out.println("Retroceder Reproduccion");   
                                    pantalla2.mostrarNumero("rep_back");
                                     break;
                                case "0x40":
                                    System.out.println("Avanzar Reproduccion");   
                                    pantalla2.mostrarNumero("rep_next");
                                     break;
                                case "0x43":
                                    System.out.println("Saltar Reproduccion ");   
                                    pantalla2.mostrarNumero("rep_skip");
                                     break;
                                
                                case "0x15":
                                    System.out.println("Subir Volumen ");   
                                    pantalla2.mostrarNumero("vol_up");
                                     break;
                                case "0x19":
                                    System.out.println(" Acción: Ejecutar botón desde Arduino");
                                    // Simula un clic en el botón de la interfaz
                                    javax.swing.SwingUtilities.invokeLater(() -> {
                                        pantalla.botonInicio.doClick();  // Simula el clic
                                    });
                                     break;
                                case "0xD":
                                    System.out.println("Abrir menu de comandos ");
                                    javax.swing.SwingUtilities.invokeLater(() -> {
                                        pantalla.jButton2.doClick();  // Simula el clic
                                        
                                    });
                                    

                                     break;
                                case "0x9":
                                    System.out.println("Salir del Programa ");
                                    javax.swing.SwingUtilities.invokeLater(() -> {
                                        pantalla.jButton1.doClick();  // Simula el clic
                                    });
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
    
