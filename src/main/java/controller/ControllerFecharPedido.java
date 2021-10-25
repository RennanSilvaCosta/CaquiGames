package controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import exceptions.NenhumClienteSelecionadoException;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
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

import static util.Helper.abrirDialog;
import static util.Helper.formataValor;

public class ControllerFecharPedido implements Initializable {

    Funcionario func = UserSession.getFuncionario();
    static Pedido pedido;
    public static Label txtValorSubTotalStatic, txtValorTotalStatic, txtValorRecebidoStatic, txtValorDescontoStatic, txtValorTrocoStatic;
    public static double valorRecebido;

    PedidoService pedidoService = new PedidoService();
    List<Cliente> clientes = new ArrayList<>();
    ClienteService clienteService = new ClienteService();
    Cliente cliente;

    @FXML
    Pane paneValorTotalPedido, paneDesconto, paneValorRecebido, paneTroco, paneClienteSelecionado;

    @FXML
    Label txtValorTotal, txtCpfCliente, txtNomeCliente, txtEmailCliente, lblClienteSelecionado, txtValorDesconto, txtValorRecebido, txtValorSubTotal, txtValorTroco;

    @FXML
    JFXTextField txtAdicionarCliente;

    @FXML
    JFXButton btnFinalizarPedido, btnAdicionarCliente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaListaClientes();
        txtValorTrocoStatic = txtValorTroco;
        txtValorSubTotalStatic = txtValorSubTotal;
        txtValorTotalStatic = txtValorTotal;
        txtValorRecebidoStatic = txtValorRecebido;
        txtValorDescontoStatic = txtValorDesconto;
    }

    private void inicializaListaClientes() {
        clientes = clienteService.buscaTodosClientes();
        TextFields.bindAutoCompletion(txtAdicionarCliente, clientes).setPrefWidth(660);
    }

    public void getPedido(Pedido pedido) {
        this.pedido = pedido;
        String valor = formataValor(pedido.getValorTotal());
        txtValorTotalStatic.setText(valor);
        txtValorRecebidoStatic.setText(valor);
        txtValorSubTotalStatic.setText(valor);
    }

    @FXML
    private void finalizarVenda() {
        try {
            func = UserSession.getFuncionario();
            pedido.setFuncionario(func);
            pedido.setData(LocalDate.now());

            if (!(cliente == null)) {
                pedido.setCliente(cliente);
                pedidoService.salvarPedido(pedido);
                fecharJanela();
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
        if (!nomeCliente.equals("")) {
            paneClienteSelecionado.setVisible(true);
            lblClienteSelecionado.setVisible(true);

            new FadeIn(paneClienteSelecionado).play();
            new FadeIn(lblClienteSelecionado).play();

            cliente = new Cliente();
            for (Cliente c : clientes) {
                String cpfNome = c.getCpf() + " - " + c.getNome();
                if (cpfNome.equals(nomeCliente)) {
                    cliente = c;
                    break;
                }
            }
            txtCpfCliente.setText(cliente.getCpf());
            txtNomeCliente.setText(cliente.getNome());
            txtEmailCliente.setText(cliente.getEmail());
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
            c.getValorTotal(pedido.getValorTotal());
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
            c.setValoRecebido(pedido.getValorTotal());
            dialog.showAndWait();
            new FadeIn(txtValorRecebido).play();
            new FadeIn(txtValorTroco).play();
        } catch (IOException e) {
            abrirDialog("Ops", "Algo deu errado, tente novamente mais tarde!", Alert.AlertType.ERROR);
        }
    }

    public void setValorRecebido(double valor) {
        txtValorRecebidoStatic.setText(formataValor(valor));
        txtValorTrocoStatic.setText(formataValor(valor - pedido.getValorTotal()));
    }

    public void setValorDesconto(double valor) {
        String subTotal = formataValor(pedido.getValorTotal() - valor);
        txtValorSubTotalStatic.setText(subTotal);
        txtValorRecebidoStatic.setText(subTotal);
        txtValorDescontoStatic.setText(formataValor(valor));
    }

    @FXML
    private void deslizarPainelValorTotalCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneValorTotalPedido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelValorTotalBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneValorTotalPedido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelDescontoCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneDesconto);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelDescontoBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneDesconto);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelValorRecebidoCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneValorRecebido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelValorRecebidoBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneValorRecebido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelTrocoCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneTroco);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelTrocoBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneTroco);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
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
