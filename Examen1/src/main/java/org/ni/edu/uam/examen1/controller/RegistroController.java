package org.ni.edu.uam.examen1.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ni.edu.uam.examen1.model.Prestamo;
import org.ni.edu.uam.examen1.service.PrestamoService;
import org.ni.edu.uam.examen1.util.AlertUtils;
import org.ni.edu.uam.examen1.util.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para el Formulario 1: Registro de Préstamos.
 * Gestiona controles y validaciones con puro JavaFX.
 */
public class RegistroController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private ComboBox<String> cmbRecurso;

    @FXML
    private DatePicker dpFechaPrestamo;

    @FXML
    private DatePicker dpFechaDevolucion;

    @FXML
    private Label lblMensaje;

    private final PrestamoService prestamoService = PrestamoService.getInstance();

    @FXML
    public void initialize() {
        // Cargar catálogo de recursos sugeridos
        cmbRecurso.setItems(FXCollections.observableArrayList(
                "Laptop Dell Latitude 5420",
                "Laptop HP ProBook 450",
                "Proyector Epson PowerLite HDMI",
                "Libro: Clean Code (Robert C. Martin)",
                "Libro: Patrones de Diseño (GoF)",
                "Tablet iPad Air 10.9\"",
                "Cámara DSLR Sony Alpha 4K",
                "Kit de Arduino Uno"
        ));
        cmbRecurso.setEditable(true);

        restablecerFechas();
    }

    private void restablecerFechas() {
        LocalDate hoy = LocalDate.now();
        dpFechaPrestamo.setValue(hoy);
        dpFechaDevolucion.setValue(hoy.plusDays(7));
    }

    @FXML
    private void onBtnRegistrarClick() {
        List<String> errores = validarFormulario();

        if (!errores.isEmpty()) {
            String mensaje = "• " + String.join("\n• ", errores);
            AlertUtils.mostrarAdvertencia("Error de Validación", "Por favor corrija los siguientes campos:", mensaje);
            if (lblMensaje != null) {
                lblMensaje.setText("Revise los campos señalados.");
            }
            return;
        }

        String usuario = txtUsuario.getText().trim();
        String recurso = cmbRecurso.getValue().trim();
        LocalDate fPrestamo = dpFechaPrestamo.getValue();
        LocalDate fDevolucion = dpFechaDevolucion.getValue();

        Prestamo nuevo = prestamoService.registrarPrestamo(usuario, recurso, fPrestamo, fDevolucion);

        AlertUtils.mostrarInformacion("Registro Exitoso", "Préstamo registrado con éxito",
                "ID Generado: #" + nuevo.getId() + "\n" +
                "Usuario: " + usuario + "\n" +
                "Recurso: " + recurso + "\n" +
                "Fecha de Devolución: " + DateUtils.formatear(fDevolucion));

        limpiarFormulario();
        if (lblMensaje != null) {
            lblMensaje.setText("✓ Préstamo #" + nuevo.getId() + " registrado exitosamente.");
        }
    }

    @FXML
    private void onBtnLimpiarClick() {
        limpiarFormulario();
        if (lblMensaje != null) {
            lblMensaje.setText("");
        }
    }

    private void limpiarFormulario() {
        txtUsuario.clear();
        cmbRecurso.getSelectionModel().clearSelection();
        cmbRecurso.getEditor().clear();
        restablecerFechas();
        txtUsuario.requestFocus();
    }

    public List<String> validarFormulario() {
        List<String> errores = new ArrayList<>();

        if (txtUsuario == null || txtUsuario.getText() == null || txtUsuario.getText().trim().isEmpty()) {
            errores.add("El nombre de usuario es obligatorio.");
        } else if (txtUsuario.getText().trim().length() < 3) {
            errores.add("El usuario debe tener al menos 3 caracteres.");
        }

        String recurso = cmbRecurso != null ? cmbRecurso.getValue() : null;
        if (recurso == null || recurso.trim().isEmpty()) {
            errores.add("Debe seleccionar o escribir un recurso a prestar.");
        }

        LocalDate fPrestamo = dpFechaPrestamo != null ? dpFechaPrestamo.getValue() : null;
        if (fPrestamo == null) {
            errores.add("La fecha de préstamo es obligatoria.");
        }

        LocalDate fDevolucion = dpFechaDevolucion != null ? dpFechaDevolucion.getValue() : null;
        if (fDevolucion == null) {
            errores.add("La fecha de devolución es obligatoria.");
        }

        if (fPrestamo != null && fDevolucion != null) {
            if (fDevolucion.isBefore(fPrestamo)) {
                errores.add("La fecha de devolución no puede ser anterior a la fecha de préstamo.");
            }
        }

        return errores;
    }
}
