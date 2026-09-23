package org.uam.pae.evaluacion2fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioSesionApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InicioSesionApplication.class.getResource("/org/uam/pae/evaluacion2fx/inicio-sesion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Inicio de Sesión");
        stage.setScene(scene);
        stage.show();
    }
}