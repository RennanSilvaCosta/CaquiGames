package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Pedido;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDialogParcela implements Initializable {

    public static Pedido pedido;

    @FXML
    JFXComboBox<Integer> comboParcelas = new JFXComboBox<>();

    @FXML
    JFXButton btnCancelar;
    @FXML
    JFXButton btnSalvar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int x=1; x<25; x++) {
            comboParcelas.getItems().add(x);
        }
    }

    @FXML
    private void salvarParcelas() {
        Integer numParcelas = comboParcelas.getSelectionModel().getSelectedItem();
        ControllerFecharPedidoCartaoScreen c = new ControllerFecharPedidoCartaoScreen();
        if (numParcelas == null) {
            pedido.setQtdParcelas(1);
        } else {
            pedido.setQtdParcelas(numParcelas);
            pedido.setSubTotal(pedido.getSubTotal() / numParcelas);
        }
        c.setParcelas(pedido);
        close();
    }

    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            salvarParcelas();
        }
    }

    @FXML
    private void close() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void getValorTotal(Pedido pedido) {
        ControllerDialogParcela.pedido = pedido;
    }
}
