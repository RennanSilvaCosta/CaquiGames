package adapter;

import com.jfoenix.controls.JFXButton;
import controller.ControllerClienteScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Cliente;
import service.ClienteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdapterListCliente extends ListCell<Cliente> {

    ControllerClienteScreen c = new ControllerClienteScreen();
    List<Cliente> clientes = new ArrayList<>();
    ClienteService clienteService = new ClienteService();

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtId;

    @FXML
    Label txtNome;

    @FXML
    Label txtCpf;

    @FXML
    Label txtEmail;

    @FXML
    Label txtTelefone;

    @FXML
    JFXButton btnEditar;

    @FXML
    JFXButton btnExcluir;

    @Override
    protected void updateItem(Cliente cliente, boolean empty) {
        super.updateItem(cliente, empty);

        if (empty || cliente == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/adapter/AdapterListaClientes.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            btnEditar.setGraphic(new ImageView(new Image("/icons/btn_editar_cliente.png")));
            btnExcluir.setGraphic(new ImageView(new Image("/icons/btn_excluir_cliente.png")));

            txtId.setText(String.valueOf(cliente.getId()));
            txtNome.setText(cliente.getNome());
            txtCpf.setText(cliente.getCpf());
            txtEmail.setText(cliente.getEmail());
            txtTelefone.setText(cliente.getCelular());

            btnEditar.setOnAction(actionEvent -> {
                c.abrirEditarCliente(cliente);
            });

            btnExcluir.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exclusão cliente");
                alert.setHeaderText("Excluir cliente");
                alert.setContentText("Você realmente deseja excluir este cliente?");
                ButtonType btnSim = new ButtonType("SIM");
                ButtonType btnCancelar = new ButtonType("CANCELAR");
                alert.getButtonTypes().setAll(btnSim, btnCancelar);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == btnSim) {
                    clienteService.excluiCliente(cliente.getCpf());
                    atualizarListaClientes();
                }
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

    private void atualizarListaClientes() {
        clientes = clienteService.buscaTodosClientes();
        ControllerClienteScreen.listaClienteStatic.getItems().clear();
        for (Cliente c : clientes) {
            ControllerClienteScreen.listaClienteStatic.getItems().add(c);
        }
        ControllerClienteScreen.listaClienteStatic.setCellFactory(cliente -> new AdapterListCliente());
    }
}
