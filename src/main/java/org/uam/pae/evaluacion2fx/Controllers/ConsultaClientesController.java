package org.uam.pae.evaluacion2fx.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.uam.pae.evaluacion2fx.Models.Cliente;
import org.uam.pae.evaluacion2fx.Repository.ClienteRepository;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConsultaClientesController {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String SIN_DATO = "-";

    @FXML private TableView<Cliente> tblConsulta;
    @FXML private TableColumn<Cliente, String> colNombreCliente;
    @FXML private TableColumn<Cliente, String> colTipoCliente;
    @FXML private TableColumn<Cliente, String> colCiudadCliente;
    @FXML private TableColumn<Cliente, String> colFechaNac;
    @FXML private TableColumn<Cliente, String> colTipoSol;
    @FXML private TableColumn<Cliente, String> colInteresCliente;

    @FXML private ImageView imgFoto;
    @FXML private Label lblNombre;
    @FXML private Label lblTipoCliente;
    @FXML private Label lblCiudad;
    @FXML private Label lblFechaNac;
    @FXML private Label lblTipoSolicitud;
    @FXML private Label lblServicios;
    @FXML private Label lblTotal;

    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        colNombreCliente.setCellValueFactory(d -> texto(d.getValue().getNombreCompleto()));
        colTipoCliente.setCellValueFactory(d -> texto(d.getValue().getTipoCliente()));
        colCiudadCliente.setCellValueFactory(d -> texto(d.getValue().getCiudad()));
        colFechaNac.setCellValueFactory(d -> texto(formatearFecha(d.getValue())));
        colTipoSol.setCellValueFactory(d -> texto(d.getValue().getTipoSolicitud()));
        colInteresCliente.setCellValueFactory(d -> texto(formatearServicios(d.getValue())));

        tblConsulta.setPlaceholder(new Label("Aún no hay clientes registrados."));
        tblConsulta.setItems(clientes);
        tblConsulta.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, actual) -> mostrarDetalle(actual));
        cargarClientes(ClienteRepository.get().getClientes());
    }


    public void cargarClientes(List<Cliente> lista) {
        clientes.setAll(lista == null ? List.of() : lista);
        lblTotal.setText("Clientes registrados: " + clientes.size());

        if (clientes.isEmpty()) {
            mostrarDetalle(null);
        } else {
            tblConsulta.getSelectionModel().selectFirst();
        }
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        cargarClientes(ClienteRepository.get().getClientes());
    }

    @FXML
    private void onCerrar(ActionEvent event) {
        ((Stage) tblConsulta.getScene().getWindow()).close();
    }

    private void mostrarDetalle(Cliente c) {
        if (c == null) {
            lblNombre.setText(SIN_DATO);
            lblTipoCliente.setText(SIN_DATO);
            lblCiudad.setText(SIN_DATO);
            lblFechaNac.setText(SIN_DATO);
            lblTipoSolicitud.setText(SIN_DATO);
            lblServicios.setText(SIN_DATO);
            imgFoto.setImage(null);
            return;
        }
        lblNombre.setText(c.getNombreCompleto());
        lblTipoCliente.setText(valor(c.getTipoCliente()));
        lblCiudad.setText(valor(c.getCiudad()));
        lblFechaNac.setText(formatearFecha(c));
        lblTipoSolicitud.setText(valor(c.getTipoSolicitud()));
        lblServicios.setText(formatearServicios(c));
        imgFoto.setImage(cargarFoto(c.getRutaFoto()));
    }

    private Image cargarFoto(String ruta) {
        if (ruta == null || ruta.isBlank()) return null;
        try {
            return new Image(ruta, true);
        } catch (Exception e) {
            return null; // la imagen ya no está disponible: se muestra el detalle sin foto
        }
    }

    private String formatearFecha(Cliente c) {
        return c.getFechaNacimiento() == null ? SIN_DATO : c.getFechaNacimiento().format(FORMATO_FECHA);
    }

    private String formatearServicios(Cliente c) {
        List<String> servicios = c.getServicios();
        return (servicios == null || servicios.isEmpty()) ? SIN_DATO : String.join(", ", servicios);
    }

    private String valor(String texto) {
        return (texto == null || texto.isBlank()) ? SIN_DATO : texto;
    }

    private SimpleStringProperty texto(String valor) {
        return new SimpleStringProperty(valor(valor));
    }
}
