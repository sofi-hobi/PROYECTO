**MANUAL DE USUARIO**

**Nombre del proyecto:** Sistema de Monitoreo por Puerto Serial
**Plataforma:** Windows
**Lenguaje:** Java
**Entorno de desarrollo:** Visual Studio Code

---

### Requisitos del Sistema

* Sistema operativo: Windows 10 o superior
* Java JDK 8 o superior instalado
* Cable USB para conectar el dispositivo externo (Arduino)
* Biblioteca `jSerialComm-2.11.0.jar` incluida

---

### Instalación y Ejecución

1. **Descargar y descomprimir** el proyecto.
2. Abrir la carpeta `PROYECTO-main` en Visual Studio Code.
3. Asegurarse de tener configurado el entorno Java en VSCode.
4. Conectar el dispositivo Arduino por puerto USB.
5. Compilar y ejecutar el archivo principal (`MenuComando.java` o `arduinoc.java`).
6. El sistema comenzará a mostrar las pantallas de interacción, lectura de datos o comandos al Arduino.

---

### Uso del Sistema

* Al iniciar, se presentará una **pantalla inicial** donde se debe seleccionar una opción.
* Se pueden enviar comandos al Arduino desde el menú o visualizar datos.
* Los datos pueden incluir temperatura, humedad, o valores de sensores conectados.
* El sistema puede tener varias **pantallas secundarias** que gestionan entradas y salidas del hardware.

---

### Solución de Problemas

* Si no se detecta el Arduino, revisar el cable o puerto COM correcto.
* Verificar que el `jSerialComm.jar` está incluido en el classpath.
* Asegurarse de que el archivo `.java` se está ejecutando y no solo compilando.

---

### Información de Contacto

Para soporte o sugerencias, contactar al desarrollador del sistema.
