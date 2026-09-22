package org.ni.edu.uam.examen1.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.ni.edu.uam.examen1.model.EstadoPrestamo;
import org.ni.edu.uam.examen1.model.Prestamo;
import org.ni.edu.uam.examen1.service.PrestamoService;
import org.ni.edu.uam.examen1.util.AlertUtils;

/**
 * Controlador para el Formulario 2: Consulta y Seguimiento de Préstamos.
 * Implementa TableView, filtros dinámicos y acciones con puro JavaFX.
 */
public class ConsultaController {

    @FXML
    private TextField txtBuscar;

    @FXML
    private ComboBox<String> cmbFiltroEstado;

    @FXML
    private TableView<Prestamo> tblPrestamos;

    @FXML
    private TableColumn<Prestamo, Integer> colId;

    @FXML
    private TableColumn<Prestamo, String> colUsuario;

    @FXML
    private TableColumn<Prestamo, String> colRecurso;

    @FXML
    private TableColumn<Prestamo, String> colFechaPrestamo;

    @FXML
    private TableColumn<Prestamo, String> colFechaDevolucion;

    @FXML
    private TableColumn<Prestamo, String> colEstado;

    @FXML
    private TableColumn<Prestamo, String> colDias;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblActivos;

    @FXML
    private Label lblDevueltos;

    @FXML
    private Label lblVencidos;

    private final PrestamoService prestamoService = PrestamoService.getInstance();
    private FilteredList<Prestamo> listaFiltrada;

    @FXML
    public void initialize() {
        configurarColumnas();
        configurarFiltros();
        actualizarMetricas();

        // Actualizar métricas si la lista cambia
        prestamoService.getPrestamos().addListener((javafx.collections.ListChangeListener<Prestamo>) c -> {
            actualizarMetricas();
        });
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colRecurso.setCellValueFactory(new PropertyValueFactory<>("recurso"));
        colFechaPrestamo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaPrestamoStr()));
        colFechaDevolucion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaDevolucionStr()));
        colEstado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEstadoStr()));
        colDias.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiasRestantes()));
    }

    private void configurarFiltros() {
        cmbFiltroEstado.setItems(FXCollections.observableArrayList(
                "Todos",
                EstadoPrestamo.ACTIVO.getDescripcion(),
                EstadoPrestamo.DEVUELTO.getDescripcion(),
                EstadoPrestamo.VENCIDO.getDescripcion()
        ));
        cmbFiltroEstado.setValue("Todos");

        listaFiltrada = new FilteredList<>(prestamoService.getPrestamos(), p -> true);

        txtBuscar.textProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());
        cmbFiltroEstado.valueProperty().addListener((obs, oldVal, newVal) -> aplicarFiltros());

        SortedList<Prestamo> listaOrdenada = new SortedList<>(listaFiltrada);
        listaOrdenada.comparatorProperty().bind(tblPrestamos.comparatorProperty());
        tblPrestamos.setItems(listaOrdenada);
    }

    private void aplicarFiltros() {
        String texto = txtBuscar.getText() == null ? "" : txtBuscar.getText().toLowerCase().trim();
        String estado = cmbFiltroEstado.getValue();

        listaFiltrada.setPredicate(p -> {
            boolean coincideTexto = texto.isEmpty()
                    || p.getUsuario().toLowerCase().contains(texto)
                    || p.getRecurso().toLowerCase().contains(texto)
                    || String.valueOf(p.getId()).contains(texto);

            boolean coincideEstado = estado == null
                    || estado.equals("Todos")
                    || p.getEstado().getDescripcion().equalsIgnoreCase(estado);

            return coincideTexto && coincideEstado;
        });
    }

    @FXML
    private void onBtnLimpiarFiltrosClick() {
        txtBuscar.clear();
        cmbFiltroEstado.setValue("Todos");
    }

    @FXML
    private void onBtnMarcarDevueltoClick() {
        Prestamo seleccionado = tblPrestamos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            AlertUtils.mostrarAdvertencia("Atención", "Ningún elemento seleccionado",
                    "Seleccione un préstamo de la tabla para registrar su devolución.");
            return;
        }

        if (seleccionado.getEstado() == EstadoPrestamo.DEVUELTO) {
            AlertUtils.mostrarInformacion("Aviso", "Préstamo ya devuelto",
                    "El préstamo #" + seleccionado.getId() + " ya se encuentra devuelto.");
            return;
        }

        boolean confirmar = AlertUtils.mostrarConfirmacion("Confirmar Devolución",
                "¿Desea marcar este préstamo como devuelto?",
                "ID: #" + seleccionado.getId() + "\nUsuario: " + seleccionado.getUsuario() + "\nRecurso: " + seleccionado.getRecurso());

        if (confirmar) {
            prestamoService.marcarComoDevuelto(seleccionado);
            tblPrestamos.refresh();
            actualizarMetricas();
            AlertUtils.mostrarInformacion("Éxito", "Devolución completada",
                    "El recurso fue reincorporado exitosamente.");
        }
    }

    @FXML
    private void onBtnEliminarClick() {
        Prestamo seleccionado = tblPrestamos.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            AlertUtils.mostrarAdvertencia("Atención", "Ningún elemento seleccionado",
                    "Seleccione un préstamo de la tabla que desee eliminar.");
            return;
        }

        boolean confirmar = AlertUtils.mostrarConfirmacion("Confirmar Eliminación",
                "¿Está seguro de eliminar el registro de préstamo?",
                "ID: #" + seleccionado.getId() + "\nUsuario: " + seleccionado.getUsuario());

        if (confirmar) {
            prestamoService.eliminarPrestamo(seleccionado);
            actualizarMetricas();
            AlertUtils.mostrarInformacion("Eliminado", "Registro eliminado",
                    "El préstamo ha sido removido del sistema.");
        }
    }

    public void actualizarMetricas() {
        if (lblTotal != null) {
            lblTotal.setText(String.valueOf(prestamoService.getPrestamos().size()));
        }
        if (lblActivos != null) {
            lblActivos.setText(String.valueOf(prestamoService.getCantidadActivos()));
        }
        if (lblDevueltos != null) {
            lblDevueltos.setText(String.valueOf(prestamoService.getCantidadDevueltos()));
        }
        if (lblVencidos != null) {
            lblVencidos.setText(String.valueOf(prestamoService.getCantidadVencidos()));
        }
    }
}
