package Arduino;
import java.util.Scanner;
import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import Arduino.pantallainicial1;
import Arduino.ArduinoDataDAO; //Agregado para la database
import Arduino.pantallainicial1;
public class arduinoc {

    public static void main(String[] args) {

        // Instancia de la pantalla (con el botón)
        pantallainicial1 pantalla = new pantallainicial1();
        Pantalla2 pantalla2 = pantalla.getPantalla2(); 
        javax.swing.JFrame frame = new javax.swing.JFrame("Interfaz con Arduino");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(pantalla);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        // Buscar el primer puerto disponible
        SerialPort[] puertos = SerialPort.getCommPorts();
        if (puertos.length == 0) {
            System.out.println("⚠️ No se encontraron puertos disponibles.");
            return;
        }
        SerialPort puerto = puertos[0];
        System.out.println("🔌 Usando el puerto: " + puerto.getSystemPortName());

        puerto.setBaudRate(9600);

        if (!puerto.openPort()) {
            System.out.println("❌ No se pudo abrir el puerto " + puerto.getSystemPortName());
            return;
        }

        System.out.println("✅ Puerto abierto: " + puerto.getSystemPortName());
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
                                case "0x7": pantalla2.mostrarNumero("vol_down"); break;
                                case "0x16": pantalla2.mostrarNumero("0"); break;
                                case "0xC": pantalla2.mostrarNumero("1"); break;
                                case "0x18": pantalla2.mostrarNumero("2"); break;
                                case "0x5E": pantalla2.mostrarNumero("3"); break;
                                case "0x8": pantalla2.mostrarNumero("4"); break;
                                case "0x1C": pantalla2.mostrarNumero("5"); break;
                                case "0x5A": pantalla2.mostrarNumero("6"); break;
                                case "0x42": pantalla2.mostrarNumero("7"); break;
                                case "0x52": pantalla2.mostrarNumero("8"); break;
                                case "0x4A": pantalla2.mostrarNumero("9"); break;
                                case "0x45": pantalla2.mostrarNumero("ch_back"); break;
                                case "0x46": pantalla2.mostrarNumero("ch_act"); break;
                                case "0x47": pantalla2.mostrarNumero("ch_next"); break;
                                case "0x44": pantalla2.mostrarNumero("rep_back"); break;
                                case "0x40": pantalla2.mostrarNumero("rep_next"); break;
                                case "0x43": pantalla2.mostrarNumero("rep_skip"); break;
                                case "0x15": pantalla2.mostrarNumero("vol_up"); break;
                                case "0x19": javax.swing.SwingUtilities.invokeLater(() -> pantalla.botonInicio.doClick()); break;
                                case "0xD": javax.swing.SwingUtilities.invokeLater(() -> pantalla.jButton2.doClick()); break;
                                case "0x9": javax.swing.SwingUtilities.invokeLater(() -> pantalla.jButton1.doClick()); break;
                                default: System.out.println("❓ Código no reconocido: " + linea); break;
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
            if (puerto != null && puerto.isOpen()) {
                puerto.closePort();
                System.out.println("🔌 Puerto cerrado.");
            }
        }
    }
}
        