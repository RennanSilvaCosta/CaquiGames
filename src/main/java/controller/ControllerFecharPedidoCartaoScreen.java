package controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import exceptions.NenhumClienteSelecionadoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Cliente;
import model.Funcionario;
import model.Pedido;
import org.controlsfx.control.textfield.TextFields;
import service.ClienteService;
import service.PedidoService;
import session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Helper.*;

public class ControllerFecharPedidoCartaoScreen implements Initializable {

    Funcionario func = UserSession.getFuncionario();
    static Pedido pedido;

    public static Label txtValorSubTotalStatic, txtValorTotalStatic, txtValorDescontoStatic, txtQtdParcelasStatic;

    PedidoService pedidoService = new PedidoService();
    List<Cliente> clientes = new ArrayList<>();
    ClienteService clienteService = new ClienteService();
    Cliente cliente = new Cliente();

    @FXML
    Pane paneValorTotalPedido;
    @FXML
    Pane paneDesconto;
    @FXML
    Pane paneQtdParcelas;
    @FXML
    Pane paneClienteSelecionado;

    @FXML
    Label txtValorTotal;
    @FXML
    Label txtCpfCliente;
    @FXML
    Label txtNomeCliente;
    @FXML
    Label txtEmailCliente;
    @FXML
    Label lblClienteSelecionado;
    @FXML
    Label txtValorDesconto;
    @FXML
    Label txtValorSubTotal;
    @FXML
    Label txtQtdParcelas;

    @FXML
    JFXTextField txtAdicionarCliente;

    @FXML
    JFXButton btnFinalizarPedido;
    @FXML
    JFXButton btnAdicionarCliente;
    @FXML
    JFXButton btnSair;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaListaClientes();
        txtValorTotalStatic = txtValorTotal;
        txtValorSubTotalStatic = txtValorSubTotal;
        txtValorDescontoStatic = txtValorDesconto;
        txtQtdParcelasStatic = txtQtdParcelas;
        inicializaEfeitoDeslizaPainel();
    }

    private void inicializaListaClientes() {
        clientes = clienteService.buscaTodosClientes();
        TextFields.bindAutoCompletion(txtAdicionarCliente, clientes).setPrefWidth(660);
    }

    public void getPedido(Pedido pedido) {
        ControllerFecharPedidoCartaoScreen.pedido = pedido;
        String valor = formataValor(pedido.getValorTotal());
        txtValorTotalStatic.setText(valor);
        txtValorSubTotalStatic.setText(valor + " x" + pedido.getQtdParcelas());
    }

    public void setParcelas(Pedido pedido) {
        int numParcelas = pedido.getQtdParcelas();
        txtQtdParcelasStatic.setText(String.valueOf(numParcelas));
        txtValorSubTotalStatic.setText(formataValor(pedido.getSubTotal()) + " x" + numParcelas);
    }

    public void setValorDesconto(double desconto) {
        double subTotal = pedido.getValorTotal() - desconto;
        pedido.setDesconto(desconto);
        pedido.setSubTotal(subTotal);
        txtValorSubTotalStatic.setText(formataValor(subTotal) + " x" + pedido.getQtdParcelas());
        txtValorDescontoStatic.setText(formataValor(desconto));
    }

    @FXML
    private void adicionarCliente() {
        String nomeCliente = txtAdicionarCliente.getText();
        if (!nomeCliente.isBlank()) {
            for (Cliente c : clientes) {
                String cpfNome = c.getCpf() + " - " + c.getNome();
                if (cpfNome.equals(nomeCliente)) {
                    cliente = c;
                    break;
                }
            }
            if (cliente.getId() != null) {
                paneClienteSelecionado.setVisible(true);
                lblClienteSelecionado.setVisible(true);

                new FadeIn(paneClienteSelecionado).play();
                new FadeIn(lblClienteSelecionado).play();

                txtCpfCliente.setText(cliente.getCpf());
                txtNomeCliente.setText(cliente.getNome());
                txtEmailCliente.setText(cliente.getEmail());
            }
        }
    }

    @FXML
    private void adicionarParcelas() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/dialog/DialogParcelas.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Parcelas");

            ControllerDialogParcela c = new ControllerDialogParcela();
            c.getValorTotal(pedido);
            dialog.showAndWait();
            new FadeIn(txtQtdParcelas).play();
        } catch (IOException e) {
            abrirDialog("Ops", "Algo deu errado, tente novamente mais tarde!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void definirDesconto() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/dialog/DialogDesconto.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Descontos");

            ControllerDialogDesconto c = new ControllerDialogDesconto();
            c.getValorTotal(pedido);
            dialog.showAndWait();
            new FadeIn(txtValorDesconto).play();
        } catch (IOException e) {
            abrirDialog("Ops", "Algo deu errado, tente novamente mais tarde!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void finalizarVenda() {
        ControllerMainScreen c = new ControllerMainScreen();
        try {
            func = UserSession.getFuncionario();
            pedido.setFuncionario(func);
            pedido.setData(LocalDate.now());

            if (cliente != null && cliente.getId() != null) {
                pedido.setCliente(cliente);
                pedidoService.salvarPedido(pedido);
                fecharJanela();
                c.inicializaResumo();
            } else {
                throw new NenhumClienteSelecionadoException();
            }
        } catch (NenhumClienteSelecionadoException e) {
            abrirDialog("Nenhum cliente selecinado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void inicializaEfeitoDeslizaPainel() {
        paneValorTotalPedido.setOnMouseEntered(mouseEvent -> deslizaPainelCima(paneValorTotalPedido));
        paneValorTotalPedido.setOnMouseExited(mouseEvent -> deslizaPainelBaixo(paneValorTotalPedido));
        paneDesconto.setOnMouseEntered(mouseEvent -> deslizaPainelCima(paneDesconto));
        paneDesconto.setOnMouseExited(mouseEvent -> deslizaPainelBaixo(paneDesconto));
        paneQtdParcelas.setOnMouseEntered(mouseEvent -> deslizaPainelCima(paneQtdParcelas));
        paneQtdParcelas.setOnMouseExited(mouseEvent -> deslizaPainelBaixo(paneQtdParcelas));
    }

    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER || evt.getCode() == KeyCode.F3) {
            adicionarCliente();
        } else if (evt.getCode() == KeyCode.F8) {
            finalizarVenda();
        } else if (evt.getCode() == KeyCode.F10) {
            definirDesconto();
        }
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) btnFinalizarPedido.getScene().getWindow();
        stage.close();
    }
}
