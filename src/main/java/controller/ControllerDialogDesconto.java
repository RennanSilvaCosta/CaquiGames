package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Pedido;
import utils.CurrencyField;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDialogDesconto implements Initializable {

    @FXML
    CurrencyField txtDescontoReais;

    @FXML
    JFXTextField txtDescontoPorcentagem;

    @FXML
    JFXButton btnSalvarDesconto;
    @FXML
    JFXButton btnCancelar;

    static Pedido pedido;
    static double valorPedido;
    static double valorDescontado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO document why this method is empty
    }

    @FXML
    private void calculaPorcentagemDesconto() {
        txtDescontoPorcentagem.setText(String.format("%,.2f", (txtDescontoReais.getAmount() / valorPedido) * 100) + "%");
    }

    @FXML
    private void salvarDesconto() {
        valorDescontado = txtDescontoReais.getAmount();
        if (pedido.getFormaPagamento().equals("Dinheiro")) {
            ControllerFecharPedidoDinheiroScreen c = new ControllerFecharPedidoDinheiroScreen();
            c.setValorDesconto(valorDescontado);
        } else {
            ControllerFecharPedidoCartaoScreen c = new ControllerFecharPedidoCartaoScreen();
            c.setValorDesconto(valorDescontado);
        }
        close();
    }

    @FXML
    private void close() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    public void getValorTotal(Pedido pedido) {
        ControllerDialogDesconto.pedido = pedido;
        valorPedido = pedido.getValorTotal();
    }

    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            salvarDesconto();
        }
    }
}
