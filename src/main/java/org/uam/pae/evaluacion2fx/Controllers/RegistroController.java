package org.uam.pae.evaluacion2fx.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.uam.pae.evaluacion2fx.Models.Cliente;
import org.uam.pae.evaluacion2fx.Models.ClienteRepository;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistroController {
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private ComboBox<String> cbTipoCliente;
    @FXML private ComboBox<String> cbCiudad;
    @FXML private DatePicker dpFechaNacimiento;

    @FXML private RadioButton rbCotizacion;
    @FXML private RadioButton rbSoporte;
    @FXML private RadioButton rbReclamo;
    @FXML private ToggleGroup tgSolicitud;

    @FXML private CheckBox chkInternet;
    @FXML private CheckBox chkTelefonia;
    @FXML private CheckBox chkTelevision;
    @FXML private CheckBox chkSoporteTecnico;

    @FXML private ImageView imgFoto;

    private String rutaFoto;

    @FXML
    private void initialize() {
        cbTipoCliente.setItems(FXCollections.observableArrayList("Natural", "Empresa"));
        cbCiudad.setItems(FXCollections.observableArrayList(
                "Managua", "León", "Granada", "Masaya", "Estelí", "Matagalpa"));
    }


    @FXML
    private void onSeleccionarFoto(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar fotografía");
        fc.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        File archivo = fc.showOpenDialog(obtenerStage());
        if (archivo != null) {
            rutaFoto = archivo.toURI().toString();
            imgFoto.setImage(new Image(rutaFoto));
        }
    }


    @FXML
    private void onGuardar(ActionEvent event) {
        String errores = validar();
        if (!errores.isEmpty()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Datos incompletos", errores);
            return;
        }

        Cliente c = new Cliente();
        c.setNombres(txtNombres.getText().trim());
        c.setApellidos(txtApellidos.getText().trim());
        c.setTipoCliente(cbTipoCliente.getValue());
        c.setCiudad(cbCiudad.getValue());
        c.setFechaNacimiento(dpFechaNacimiento.getValue());
        c.setTipoSolicitud(((RadioButton) tgSolicitud.getSelectedToggle()).getText());
        c.setServicios(serviciosSeleccionados());
        c.setRutaFoto(rutaFoto);
        ClienteRepository.get().getClientes().add(c);
        mostrarAlerta(Alert.AlertType.INFORMATION, "Registro exitoso",
                "Cliente " + c.getNombreCompleto() + " registrado correctamente.\n"
                        + "Total de clientes: " + ClienteRepository.get().getClientes().size());
        limpiarFormulario();
    }


    @FXML
    private void onLimpiar(ActionEvent event) {
        limpiarFormulario();
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        if (confirmar("Cancelar registro",
                "¿Desea salir? Los datos no guardados se perderán.")) {
            obtenerStage().close(); // la ventana principal sigue abierta debajo
        }
    }

    private String validar() {
        StringBuilder sb = new StringBuilder();
        if (txtNombres.getText().trim().isEmpty())   sb.append("- Ingrese los nombres.\n");
        if (txtApellidos.getText().trim().isEmpty()) sb.append("- Ingrese los apellidos.\n");
        if (cbTipoCliente.getValue() == null)        sb.append("- Seleccione el tipo de cliente.\n");
        if (cbCiudad.getValue() == null)             sb.append("- Seleccione la ciudad.\n");

        LocalDate fecha = dpFechaNacimiento.getValue();
        if (fecha == null)                           sb.append("- Seleccione la fecha de nacimiento.\n");
        else if (fecha.isAfter(LocalDate.now()))     sb.append("- La fecha no puede ser futura.\n");

        if (tgSolicitud.getSelectedToggle() == null) sb.append("- Seleccione el tipo de solicitud.\n");
        if (serviciosSeleccionados().isEmpty())      sb.append("- Marque al menos un servicio.\n");
        return sb.toString();
    }

    private List<String> serviciosSeleccionados() {
        List<String> lista = new ArrayList<>();
        for (CheckBox chk : List.of(chkInternet, chkTelefonia, chkTelevision, chkSoporteTecnico)) {
            if (chk.isSelected()) lista.add(chk.getText());
        }
        return lista;
    }

    private void limpiarFormulario() {
        txtNombres.clear();
        txtApellidos.clear();
        cbTipoCliente.getSelectionModel().clearSelection();
        cbCiudad.getSelectionModel().clearSelection();
        dpFechaNacimiento.setValue(null);
        tgSolicitud.selectToggle(null);
        chkInternet.setSelected(false);
        chkTelefonia.setSelected(false);
        chkTelevision.setSelected(false);
        chkSoporteTecnico.setSelected(false);
        imgFoto.setImage(null);
        rutaFoto = null;
        txtNombres.requestFocus();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(obtenerStage());
        alert.showAndWait();
    }

    private boolean confirmar(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.initOwner(obtenerStage());
        Optional<ButtonType> r = alert.showAndWait();
        return r.isPresent() && r.get() == ButtonType.OK;
    }

    private Stage obtenerStage() {
        return (Stage) txtNombres.getScene().getWindow();
    }
}
