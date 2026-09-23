package org.uam.pae.evaluacion2fx.Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Almacén en memoria compartido entre ventanas (Registro -> Consulta)
public class ClienteRepository {
    private static final ClienteRepository INSTANCIA = new ClienteRepository();

    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();

    private ClienteRepository() { }

    public static ClienteRepository get() {
        return INSTANCIA;
    }

    public ObservableList<Cliente> getClientes() {
        return clientes;
    }
}
