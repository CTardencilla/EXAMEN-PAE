# Examen PAE #1 - Base Inicial del Sistema de Préstamos
### Entregable: Persona 1 (Arquitectura, Modelos y Servicios)

Este repositorio contiene la **base arquitectónica inicial** del sistema de gestión de préstamos, configurado para **Java 21** y **JavaFX 21**, sirviendo como punto de partida para que los demás integrantes desarrollen las capas de formularios, validaciones de interfaz y estilos.

---

## 📦 Componentes y Entregables Desarrollados (Persona 1)

1. **Configuración de Construcción y Módulos**:
   - `pom.xml`: Configurado con Maven Compiler para **Java 21**, dependencias de **JavaFX 21** (`javafx-controls`, `javafx-fxml`) y **JUnit 5.12.1**.
   - `src/main/java/module-info.java`: Módulo Java configurado, abriendo el paquete `model` a `javafx.base` (para enlaces de propiedades en tablas) y exportando `model` y `service`.

2. **Capa de Modelo**:
   - `org.ni.edu.uam.examen1.model.EstadoPrestamo`: Enumeración con los estados del préstamo (`ACTIVO`, `DEVUELTO`, `VENCIDO`).
   - `org.ni.edu.uam.examen1.model.Prestamo`: Entidad de datos con ID, prestatario/usuario, recurso, fecha de préstamo (`LocalDate`), fecha de devolución (`LocalDate`), estado y métodos de negocio (cálculo de días restantes, validación de vencimiento y formateo `dd/MM/yyyy`).

3. **Capa de Servicio y Persistencia en Memoria**:
   - `org.ni.edu.uam.examen1.service.PrestamoService`:
     - Implementación con patrón **Singleton** para compartir el estado común de la aplicación.
     - Persistencia en memoria basada en **`ObservableList<Prestamo>`** (preparada para conectarse directamente a controles JavaFX como `TableView` o `ListView`).
     - **Carga de datos semilla iniciales** con préstamos en diferentes estados (activos, por vencer, vencidos y devueltos) para pruebas inmediatas.
     - Métodos de negocio: `registrarPrestamo()`, `marcarComoDevuelto()`, `eliminarPrestamo()` y conteo de métricas (`getCantidadActivos()`, `getCantidadDevueltos()`, `getCantidadVencidos()`, `getTotalPrestamos()`).

4. **Pruebas Unitarias**:
   - `src/test/java/org/ni/edu/uam/examen1/PrestamoServiceTest.java`: Conjunto de pruebas unitarias automatizadas con **JUnit 5** que verifican:
     - Carga correcta de datos semilla.
     - Registro y generación de IDs incrementales.
     - Validación de campos obligatorios vacíos o nulos.
     - Validación de coherencia de fechas (fecha de devolución no anterior a fecha de préstamo).
     - Actualización a estado devuelto.
     - Eliminación de registros.

---

## 📁 Estructura Actual de Archivos

```
Examen1/
├── pom.xml                                           # Configuración Java 21 / JavaFX 21
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── module-info.java                      # Descriptor de módulos
│   │       └── org/ni/edu/uam/examen1/
│   │           ├── model/
│   │           │   ├── EstadoPrestamo.java           # Enum de estados
│   │           │   └── Prestamo.java                 # Modelo de datos del préstamo
│   │           └── service/
│   │               └── PrestamoService.java          # Servicio y ObservableList en memoria
│   └── test/
│       └── java/org/ni/edu/uam/examen1/
│           └── PrestamoServiceTest.java              # Pruebas unitarias con JUnit 5
└── README.md
```

---

## 🧪 Verificación y Pruebas

Para compilar el proyecto y ejecutar las pruebas unitarias:

```bash
mvn clean test
```

Salida esperada:
```
[INFO] Running org.ni.edu.uam.examen1.PrestamoServiceTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```
