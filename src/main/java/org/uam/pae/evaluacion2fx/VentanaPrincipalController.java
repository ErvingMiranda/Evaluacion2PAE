package org.uam.pae.evaluacion2fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class VentanaPrincipalController {

    // Contenedor principal
    @FXML private BorderPane bpPrincipal;

    // Elementos del MenuBar
    @FXML private MenuItem miRegistrar;
    @FXML private MenuItem miConsultar;
    @FXML private MenuItem miSalir;

    // Elementos del ToolBar
    @FXML private Button btnRegistrar;
    @FXML private Button btnConsultar;

    // Menú Contextual (Clic derecho)
    @FXML private ContextMenu cmOpciones;
    @FXML private MenuItem miContextAcercaDe;

    @FXML
    public void initialize() {
        // Habilitar que el panel principal detecte el teclado (para el evento KeyEvent)
        bpPrincipal.setFocusTraversable(true);
    }

    /**
     * Evento KeyEvent: Cierra la aplicación si se presiona la tecla ESC
     */
    @FXML
    private void manejarTeclado(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            confirmarSalida();
        }
    }

    /**
     * Abre la Ventana 3 (Registro) desarrollada por tu compañero.
     * Actualizado al nombre exacto: registro-cliente-view.fxml
     */
    @FXML
    private void abrirVentanaRegistro(ActionEvent event) {
        cargarVentana("/com/empresa/app/vistas/registro-cliente-view.fxml", "Registro de Clientes");
    }

    /**
     * Abre la Ventana 4 (Consulta) desarrollada por tu compañero.
     * Actualizado al nombre exacto: consulta-cliente-view.fxml
     */
    @FXML
    private void abrirVentanaConsulta(ActionEvent event) {
        cargarVentana("/com/empresa/app/vistas/consulta-cliente-view.fxml", "Consulta de Clientes");
    }

    /**
     * Muestra un Alert de Información (Cumple requisito de la rúbrica)
     */
    @FXML
    private void mostrarAcercaDe(ActionEvent event) {
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        alertInfo.setTitle("Acerca del Sistema");
        alertInfo.setHeaderText("Sistema de Gestión de Clientes");
        alertInfo.setContentText("Versión 1.0\nDesarrollado por el Equipo.");
        alertInfo.showAndWait();
    }

    /**
     * Acción vinculada al botón y menú de Salir.
     */
    @FXML
    private void salirAplicacion(ActionEvent event) {
        confirmarSalida();
    }

    /**
     * Muestra un Alert de Confirmación antes de cerrar (Cumple requisito de la rúbrica)
     */
    private void confirmarSalida() {
        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmacion.setTitle("Confirmar Salida");
        alertConfirmacion.setHeaderText("¿Está seguro que desea salir?");
        alertConfirmacion.setContentText("Se cerrará la aplicación.");

        Optional<ButtonType> resultado = alertConfirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Cierra la ventana actual (y la app)
            Stage stage = (Stage) bpPrincipal.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Método genérico para cargar ventanas y no repetir código.
     */
    private void cargarVentana(String rutaFxml, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            // Modality.APPLICATION_MODAL evita que el usuario toque la ventana de atrás hasta cerrar esta
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Error de Navegación");
            alertError.setHeaderText("No se pudo cargar la ventana");
            alertError.setContentText("Asegúrate de que la ruta y el nombre del archivo sean correctos.\nRuta buscada: " + rutaFxml + "\nError: " + e.getMessage());
            alertError.showAndWait();
            e.printStackTrace(); // Ayuda a ver el error exacto en la consola de IntelliJ
        }
    }
}