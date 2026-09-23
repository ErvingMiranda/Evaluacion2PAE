package org.uam.pae.evaluacion2fx.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.uam.pae.evaluacion2fx.Repository.ClienteRepository;

import java.io.IOException;

public class VentanaPrincipalController {

    @FXML private Button btnRegistrar;

    @FXML
    private void onRegistrarCliente(ActionEvent event) {
        abrirVentana("/org/uam/pae/evaluacion2fx/registro-cliente-view.fxml", "Registro de Clientes");
    }

    @FXML
    private void onConsultarClientes(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/uam/pae/evaluacion2fx/consulta-clientes-view.fxml"));
        try {
            Parent root = loader.load();
            // Paso de datos a la vista de consulta
            ConsultaClientesController controlador = loader.getController();
            controlador.cargarClientes(ClienteRepository.get().getClientes());
            mostrar(root, "Consulta de Clientes");
        } catch (IOException e) {
            mostrarError("No se pudo abrir la consulta de clientes.", e);
        }
    }

    @FXML
    private void onSalir(ActionEvent event) {
        Platform.exit();
    }

    private void abrirVentana(String recurso, String titulo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(recurso));
            mostrar(root, titulo);
        } catch (IOException e) {
            mostrarError("No se pudo abrir la ventana: " + titulo, e);
        }
    }

    private void mostrar(Parent root, String titulo) {
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.initOwner(btnRegistrar.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private void mostrarError(String mensaje, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(mensaje);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
