module org.ni.edu.uam.examen1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.ni.edu.uam.examen1 to javafx.fxml;
    opens org.ni.edu.uam.examen1.controller to javafx.fxml;
    opens org.ni.edu.uam.examen1.model to javafx.base;

    exports org.ni.edu.uam.examen1;
    exports org.ni.edu.uam.examen1.controller;
    exports org.ni.edu.uam.examen1.model;
    exports org.ni.edu.uam.examen1.service;
    exports org.ni.edu.uam.examen1.util;
}