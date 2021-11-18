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

public class ControllerFecharPedidoDinheiroScreen implements Initializable {

    Funcionario func = UserSession.getFuncionario();
    static Pedido pedido;
    public static Label txtValorSubTotalStatic, txtValorTotalStatic, txtValorRecebidoStatic, txtValorDescontoStatic, txtValorTrocoStatic;
    public static double valorRecebido = 0;

    PedidoService pedidoService = new PedidoService();
    List<Cliente> clientes = new ArrayList<>();
    ClienteService clienteService = new ClienteService();
    Cliente cliente = new Cliente();

    //TextFields
    @FXML
    JFXTextField txtAdicionarCliente;

    //Paineis
    @FXML
    Pane paneValorTotalPedido;
    @FXML
    Pane paneDesconto;
    @FXML
    Pane paneValorRecebido;
    @FXML
    Pane paneTroco;
    @FXML
    Pane paneClienteSelecionado;

    //Labels
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
    Label txtValorRecebido;
    @FXML
    Label txtValorSubTotal;
    @FXML
    Label txtValorTroco;

    //Botões
    @FXML
    JFXButton btnFinalizarPedido;
    @FXML
    JFXButton btnAdicionarCliente;
    @FXML
    JFXButton btnSair;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaListaClientes();
        txtValorTrocoStatic = txtValorTroco;
        txtValorSubTotalStatic = txtValorSubTotal;
        txtValorTotalStatic = txtValorTotal;
        txtValorRecebidoStatic = txtValorRecebido;
        txtValorDescontoStatic = txtValorDesconto;
        inicializaEfeitoDeslizaPainel();
    }

    private void inicializaListaClientes() {
        clientes = clienteService.buscaTodosClientes();
        TextFields.bindAutoCompletion(txtAdicionarCliente, clientes).setPrefWidth(660);
    }

    public void getPedido(Pedido pedido) {
        ControllerFecharPedidoDinheiroScreen.pedido = pedido;
        String valor = formataValor(pedido.getValorTotal());
        txtValorTotalStatic.setText(valor);
        txtValorRecebidoStatic.setText(valor);
        txtValorSubTotalStatic.setText(valor);
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
            new FadeIn(txtValorRecebido).play();
        } catch (IOException e) {
            abrirDialog("Ops", "Algo deu errado, tente novamente mais tarde!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void definirValorRecebido() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/dialog/DialogValorRecebido.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Valor recebido");

            ControllerDialogValorRecebido c = new ControllerDialogValorRecebido();
            c.setValoRecebido(pedido.getSubTotal());
            dialog.showAndWait();
            new FadeIn(txtValorRecebido).play();
            new FadeIn(txtValorTroco).play();
        } catch (IOException e) {
            abrirDialog("Ops", "Algo deu errado, tente novamente mais tarde!", Alert.AlertType.ERROR);
        }
    }

    public void setValorRecebido(double valor) {
        txtValorRecebidoStatic.setText(formataValor(valor));
        txtValorTrocoStatic.setText(formataValor(valor - pedido.getSubTotal()));
    }

    public void setValorDesconto(double desconto) {
        double subTotal = pedido.getValorTotal() - desconto;
        String subTotalString = formataValor(subTotal);
        pedido.setDesconto(desconto);
        pedido.setSubTotal(subTotal);
        txtValorSubTotalStatic.setText(subTotalString);
        txtValorRecebidoStatic.setText(subTotalString);
        txtValorDescontoStatic.setText(formataValor(desconto));
    }

    private void inicializaEfeitoDeslizaPainel() {
        paneValorTotalPedido.setOnMouseEntered(mouseEvent -> deslizaPainelCima(paneValorTotalPedido));
        paneValorTotalPedido.setOnMouseExited(mouseEvent -> deslizaPainelBaixo(paneValorTotalPedido));
        paneDesconto.setOnMouseEntered(mouseEvent -> deslizaPainelCima(paneDesconto));
        paneDesconto.setOnMouseExited(mouseEvent -> deslizaPainelBaixo(paneDesconto));
        paneValorRecebido.setOnMouseEntered(mouseEvent -> deslizaPainelCima(paneValorRecebido));
        paneValorRecebido.setOnMouseExited(mouseEvent -> deslizaPainelBaixo(paneValorRecebido));
        paneTroco.setOnMouseEntered(mouseEvent -> deslizaPainelCima(paneTroco));
        paneTroco.setOnMouseExited(mouseEvent -> deslizaPainelBaixo(paneTroco));
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) btnFinalizarPedido.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER || evt.getCode() == KeyCode.F3) {
            adicionarCliente();
        } else if (evt.getCode() == KeyCode.F8) {
            finalizarVenda();
        } else if (evt.getCode() == KeyCode.F10) {
            definirDesconto();
        } else if (evt.getCode() == KeyCode.F4) {
            definirValorRecebido();
        }
    }
}
