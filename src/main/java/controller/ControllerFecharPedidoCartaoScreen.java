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

public class ControllerFecharPedidoCartaoScreen implements Initializable {

    Funcionario func = UserSession.getFuncionario();
    static Pedido pedido;

    public static Label txtValorSubTotalStatic, txtValorTotalStatic, txtValorDescontoStatic, txtQtdParcelasStatic;

    PedidoService pedidoService = new PedidoService();
    List<Cliente> clientes = new ArrayList<>();
    ClienteService clienteService = new ClienteService();
    Cliente cliente;

    @FXML
    Pane paneValorTotalPedido, paneDesconto, paneQtdParcelas, paneClienteSelecionado;

    @FXML
    Label txtValorTotal, txtCpfCliente, txtNomeCliente, txtEmailCliente, lblClienteSelecionado, txtValorDesconto, txtValorSubTotal, txtQtdParcelas;

    @FXML
    JFXTextField txtAdicionarCliente;

    @FXML
    JFXButton btnFinalizarPedido, btnAdicionarCliente, btnSair;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaListaClientes();
        txtValorTotalStatic = txtValorTotal;
        txtValorSubTotalStatic = txtValorSubTotal;
        txtValorDescontoStatic = txtValorDesconto;
        txtQtdParcelasStatic = txtQtdParcelas;
    }

    private void inicializaListaClientes() {
        clientes = clienteService.buscaTodosClientes();
        TextFields.bindAutoCompletion(txtAdicionarCliente, clientes).setPrefWidth(660);
    }

    public void getPedido(Pedido pedido) {
        this.pedido = pedido;
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
    private void deslizarPainelQtdParcelasCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneQtdParcelas);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelQtdParcelasBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneQtdParcelas);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }
}
