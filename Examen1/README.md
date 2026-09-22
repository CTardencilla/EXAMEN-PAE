# Sistema de Registro y Consulta de Préstamos
### Examen PAE #1 - Universidad Americana (UAM)

Aplicación de escritorio desarrollada en **JavaFX 21** con **Java 21**, estructurada bajo el patrón arquitectónico **MVC (Modelo - Vista - Controlador)** utilizando **puro JavaFX (sin hojas de estilo CSS)** y persistencia observable en memoria.

---

## 📋 Módulos y Funcionalidades

### 1. Formulario #1: Registro de Préstamos (`registro-view.fxml` / `RegistroController.java`)
- **Controles nativos de JavaFX**:
  - `TextField` para el nombre o identificación del usuario/prestatario.
  - `ComboBox` editable precargado con recursos sugeridos (laptops, proyectores, libros, etc.) y opción de entrada libre.
  - `DatePicker` para la fecha de préstamo (inicializada con la fecha actual).
  - `DatePicker` para la fecha límite de devolución (sugerida a 7 días).
- **Validaciones implementadas**:
  - Nombre de usuario obligatorio y con longitud mínima de 3 caracteres.
  - Recurso a prestar obligatorio.
  - Fechas de préstamo y devolución obligatorias.
  - **Coherencia cronológica**: La fecha de devolución no puede ser anterior a la fecha de préstamo.
  - Notificaciones de error o confirmación mediante diálogos nativos `Alert` (`AlertUtils`).
- **Acciones**:
  - `Registrar Préstamo`: Valida, asigna ID incremental, almacena en `PrestamoService` y limpia campos.
  - `Limpiar Formulario`: Restablece los campos a sus valores por defecto.

### 2. Formulario #2: Consulta y Seguimiento (`consulta-view.fxml` / `ConsultaController.java`)
- **Panel de Métricas en Vivo**: Contadores para Total de Préstamos, Activos, Devueltos y Vencidos.
- **Búsqueda y Filtros en Tiempo Real**:
  - Campo de texto de búsqueda reactiva por usuario, recurso o ID (utilizando `FilteredList`).
  - Selector de estado (`Todos`, `Activo`, `Devuelto`, `Vencido`).
  - Botón para reiniciar filtros.
- **Tabla Dinámica (`TableView<Prestamo>`)**:
  - Columnas: ID, Usuario, Recurso, Fecha Préstamo (`dd/MM/yyyy`), Fecha Devolución (`dd/MM/yyyy`), Estado y Seguimiento temporal.
- **Acciones sobre Registros**:
  - `Marcar como Devuelto`: Valida selección previa, impide re-devoluciones y actualiza el estado.
  - `Eliminar Préstamo`: Remueve el registro tras diálogo modal de confirmación.

### 3. Navegación Principal e Integración (`main-view.fxml` / `MainController.java` / `App.java`)
- Navegación nativa con `BorderPane`, `StackPane` y botones superiores que permiten alternar fluidamente entre el Formulario de Registro y la Consulta.
- Sincronización automática de datos entre formularios mediante `ObservableList` en el singleton `PrestamoService`.
- **Cero dependencias CSS**: Construido enteramente con controles nativos Modena de JavaFX.

---

## 👥 Colaboradores del Proyecto

- **William** (`wigar2017@gmail.com`)
- **CTardencilla** (`203885228+CTardencilla@users.noreply.github.com`)
- **g-nzaan** (`216466001+g-nzaan@users.noreply.github.com`)
- **rafaelhs07** (`211024110+rafaelhs07@users.noreply.github.com`)

---

## 🚀 Compilación y Ejecución

Desde la carpeta `Examen1/`:

1. **Compilar el proyecto:**
   ```bash
   mvn clean compile
   ```

2. **Ejecutar las pruebas unitarias (12 tests):**
   ```bash
   mvn test
   ```

3. **Iniciar la aplicación:**
   ```bash
   mvn javafx:run
   ```
