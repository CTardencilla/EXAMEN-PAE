package org.ni.edu.uam.examen1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class FxmlResourcesTest {

    @Test
    @DisplayName("Debe existir la vista principal main-view.fxml")
    void testMainViewExists() {
        URL mainView = App.class.getResource("view/main-view.fxml");
        assertNotNull(mainView, "main-view.fxml debe estar en el classpath");
    }

    @Test
    @DisplayName("Debe existir la vista de registro registro-view.fxml")
    void testRegistroViewExists() {
        URL registroView = App.class.getResource("view/registro-view.fxml");
        assertNotNull(registroView, "registro-view.fxml debe estar en el classpath");
    }

    @Test
    @DisplayName("Debe existir la vista de consulta consulta-view.fxml")
    void testConsultaViewExists() {
        URL consultaView = App.class.getResource("view/consulta-view.fxml");
        assertNotNull(consultaView, "consulta-view.fxml debe estar en el classpath");
    }
}
