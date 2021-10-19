package adapter;

import com.jfoenix.controls.JFXButton;
import controller.ControllerClienteScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import model.Cliente;

import java.io.IOException;

public class AdapterListCliente extends ListCell<Cliente> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtId, txtNome, txtCpf, txtEmail, txtTelefone;

    @FXML
    JFXButton btnEditar, btnExcluir;

    private double xOffset = 0;
    private double yOffset = 0;

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

            btnEditar.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ControllerClienteScreen c = new ControllerClienteScreen();
                    c.abrirEditarCliente(cliente);
                }
            });

            setText(null);
            setGraphic(gridPane);
        }
    }
}
