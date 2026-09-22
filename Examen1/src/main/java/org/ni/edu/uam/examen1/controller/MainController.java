package org.ni.edu.uam.examen1.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import org.ni.edu.uam.examen1.App;

import java.io.IOException;

/**
 * Controlador principal para navegación entre vistas en puro JavaFX.
 */
public class MainController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Button btnNavRegistro;

    @FXML
    private Button btnNavConsulta;

    private Node vistaRegistro;
    private Node vistaConsulta;
    private ConsultaController consultaController;

    @FXML
    public void initialize() {
        try {
            FXMLLoader loaderRegistro = new FXMLLoader(App.class.getResource("view/registro-view.fxml"));
            vistaRegistro = loaderRegistro.load();

            FXMLLoader loaderConsulta = new FXMLLoader(App.class.getResource("view/consulta-view.fxml"));
            vistaConsulta = loaderConsulta.load();
            consultaController = loaderConsulta.getController();

            mostrarRegistro();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void mostrarRegistro() {
        if (contentArea != null && vistaRegistro != null) {
            contentArea.getChildren().setAll(vistaRegistro);
            actualizarEstadoBotones(true);
        }
    }

    @FXML
    public void mostrarConsulta() {
        if (contentArea != null && vistaConsulta != null) {
            if (consultaController != null) {
                consultaController.actualizarMetricas();
            }
            contentArea.getChildren().setAll(vistaConsulta);
            actualizarEstadoBotones(false);
        }
    }

    private void actualizarEstadoBotones(boolean registroActivo) {
        if (btnNavRegistro != null && btnNavConsulta != null) {
            btnNavRegistro.setDisable(registroActivo);
            btnNavConsulta.setDisable(!registroActivo);
        }
    }
}
