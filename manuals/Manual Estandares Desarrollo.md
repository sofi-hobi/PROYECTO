**MANUAL DE ESTÁNDARES Y DESARROLLO**

**Nombre del proyecto:** Sistema de Monitoreo por Puerto Serial
**Lenguaje:** Java SE
**Entorno:** Visual Studio Code
**Librería externa:** jSerialComm 2.11.0

---

### Lenguaje y Herramientas

* Lenguaje de programación: Java SE
* Entorno de desarrollo: Visual Studio Code (VSCode)
* Sistema operativo de desarrollo y ejecución: Windows
* Compilación: manual desde terminal o usando VSCode
* Librería para puerto serial: `jSerialComm`

---

### Estructura del Proyecto

```
PROYECTO-main/
├── src/Arduino/              # Código fuente principal en Java
├── bin/Arduino/              # Archivos compilados
├── lib/                      # Librerías externas (jSerialComm)
├── Design/                   # Diagramas UML
├── .vscode/                  # Configuraciones del entorno VSCode
├── README.md
└── logo.png
```

---

### Convenciones de Código

* Nombres de clases en **PascalCase**: `MenuComando`, `Pantalla2`
* Nombres de variables y métodos en **camelCase**: `leerPuerto`, `inicializarDatos`
* Comentarios:

  * `//` para explicaciones breves
  * `/** ... */` para documentación formal
* Clases divididas por funcionalidad:

  * Interfaz gráfica (pantallas)
  * Control y comunicación serial
  * Acceso a datos (DAO)

---

### Prácticas de Desarrollo

* No se utilizaron patrones de diseño formales
* Proyecto modularizado de forma básica
* Archivos organizados por tipo y función

---

### Recomendaciones para Desarrollo Futuro

* Implementar patrones como MVC para mejorar la escalabilidad
* Documentar cambios en un `CHANGELOG.md`
* Utilizar Git para control de versiones
* Incluir pruebas unitarias con JUnit

---

### Diagrama de Arquitectura

(Disponible en la carpeta `Design/` en formato `.plantuml`)

---

**Notas Finales**
Este manual sirve como referencia para futuros desarrolladores que deseen comprender o mejorar el sistema.
