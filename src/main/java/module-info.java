module org.uam.pae.evaluacion2fx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.uam.pae.evaluacion2fx to javafx.fxml;
    exports org.uam.pae.evaluacion2fx;
    exports org.uam.pae.evaluacion2fx.Controllers;
    opens org.uam.pae.evaluacion2fx.Controllers to javafx.fxml;
}