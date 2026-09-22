package org.ni.edu.uam.examen1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

/**
 * Punto de entrada de la aplicación JavaFX en puro JavaFX (sin CSS externo).
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlLocation = App.class.getResource("view/main-view.fxml");
        if (fxmlLocation == null) {
            throw new IllegalStateException("No se pudo localizar el archivo view/main-view.fxml");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load(), 980, 660);

        stage.setTitle("Sistema de Gestión de Préstamos - UAM");
        stage.setMinWidth(850);
        stage.setMinHeight(550);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
