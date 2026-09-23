package org.uam.pae.evaluacion2fx.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class InicioSesionController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContrasena;

    @FXML
    private void iniciarSesion(ActionEvent event) throws IOException {
        validarInicioSesion();
    }

    @FXML
    private void salir(ActionEvent event) {
        confirmarSalida();
    }

    @FXML
    private void manejarTeclado(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) validarInicioSesion();
        else if (event.getCode() == KeyCode.ESCAPE) confirmarSalida();
    }

    private void validarInicioSesion() throws IOException {
        if (txtUsuario.getText().isBlank() || txtContrasena.getText().isBlank()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Datos incompletos", "Complete todos los campos.");
            return;
        }

        abrirVentanaPrincipal();
    }

    private void abrirVentanaPrincipal() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(InicioSesionController.class.getResource("/org/uam/pae/evaluacion2fx/ventana-principal.fxml"));

        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Ventana Principal");
        stage.setScene(scene);
        stage.show();

        Stage ventanaActual = (Stage) txtUsuario.getScene().getWindow();
        ventanaActual.close();
    }

    private void confirmarSalida() {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar salida");
        alerta.setHeaderText("¿Desea cerrar la aplicación?");
        alerta.setContentText("Seleccione Aceptar para salir.");

        ButtonType respuesta = alerta.showAndWait().get();

        if (respuesta == ButtonType.OK) {
            Platform.exit();
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}