package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.CurrencyField;

import java.net.URL;
import java.util.ResourceBundle;

import static util.Helper.abrirDialog;

public class ControllerDialogValorRecebido implements Initializable {

    @FXML
    CurrencyField txtValorRecebido;

    @FXML
    JFXButton btnCancelar, btnSalvar;

    public static CurrencyField txtValorRecebidoStatic;
    public static double valorRecebido = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtValorRecebidoStatic = txtValorRecebido;
    }

    public void setValoRecebido(double valor) {
        valorRecebido = valor;
        txtValorRecebidoStatic.setAmount(valor);
    }

    @FXML
    private void salvarValorRecebido() {
        if (txtValorRecebido.getAmount() < valorRecebido) {
            abrirDialog("Valor inválido", "O valor recebido não pode ser menor que o valor total do pedido!", Alert.AlertType.ERROR);
        } else {
            ControllerFecharPedidoDinheiroScreen c = new ControllerFecharPedidoDinheiroScreen();
            c.setValorRecebido(txtValorRecebido.getAmount());
            close();
        }
    }

    @FXML
    private void close() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            salvarValorRecebido();
        }
    }

}
