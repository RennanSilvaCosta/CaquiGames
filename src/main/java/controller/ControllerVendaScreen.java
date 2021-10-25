package controller;

import adapter.AdapterListProdutoVenda;
import animatefx.animation.FadeInDown;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import exceptions.CarrinhoVazioException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import org.controlsfx.control.textfield.TextFields;
import service.ProdutoService;
import util.Helper;

import java.io.IOException;
import java.net.URL;
import java.util.*;

import static util.Helper.abrirDialog;
import static util.Helper.formataValor;

public class ControllerVendaScreen implements Initializable {

    ProdutoService ps = new ProdutoService();

    private List<Produto> produtos = new ArrayList<>();
    Set<ItemPedido> listaItens = new HashSet<>();

    public static double valorTotal = 0;
    public static Label txtValorTotalStatic;

    @FXML
    private Label txtQuantidadeItens, txtValorDesconto, txtValorTotal;
    @FXML
    private JFXButton btnFecharPedido, btnAdicionarProduto, btnSair;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane parentContainer;
    @FXML
    private JFXListView<ItemPedido> listaProdutos;
    @FXML
    private JFXTextField txtAdicionarProduto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtValorTotalStatic = txtValorTotal;
        inicializaListaProdutos();
    }

    @FXML
    private void fecharPedido() {
        try {
            if (!(listaItens.size() == 0)) {
                abrirFormaPagamento();
            } else {
                throw new CarrinhoVazioException();
            }
        } catch (IOException e) {
            abrirDialog("Ops","Algo deu errado, tente novamente mais tarde.", Alert.AlertType.ERROR);
        } catch (CarrinhoVazioException e) {
            abrirDialog("Carrinho vazio", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void abrirFormaPagamento() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/PagamentoScreen.fxml"));
        Scene scene = btnFecharPedido.getScene();

        ControllerPagamentoScreen controller = new ControllerPagamentoScreen();
        Pedido p = new Pedido();
        p.setValorTotal(valorTotal);
        p.setSubTotal(valorTotal);
        p.setQuantidadeItens(listaItens.size());
        p.setItens(listaItens);
        controller.getPedido(p);

        root.translateXProperty().set(scene.getWidth());
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(800), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(anchorRoot);
        });
        timeline.play();
    }

    @FXML
    private void adicionarItemCarrinho() {
        String nomeProduto = txtAdicionarProduto.getText();
        if (!nomeProduto.equals("")) {
            Produto produto = new Produto();

            for (Produto p : produtos) {
                if (p.getNome().equals(nomeProduto)) {
                    produto = p;
                    break;
                }
            }
            ItemPedido item = new ItemPedido(produto, 0.0, 1, produto.getValor());
            listaItens.add(item);

            listaProdutos.getItems().add(item);
            listaProdutos.setCellFactory(itemPedido -> new AdapterListProdutoVenda());
            txtAdicionarProduto.clear();

            new FadeInDown(txtQuantidadeItens).setSpeed(0.5).play();
            txtQuantidadeItens.setText(String.valueOf(listaProdutos.getItems().size()));
            defineValorTotal();
        }
    }

    private void inicializaListaProdutos() {
        produtos = ps.buscarProdutoParaVenda();
        TextFields.bindAutoCompletion(txtAdicionarProduto, produtos).setPrefWidth(660);
    }

    public void defineValorTotal() {
        double total = valorTotal;
        for (ItemPedido item : listaProdutos.getItems()) {
            total = item.getPreco() * item.getQuantidade();
        }
        valorTotal += total;
        new FadeInDown(txtValorTotal).setSpeed(0.5).play();
        txtValorTotalStatic.setText(formataValor(valorTotal));
    }

    public void fecharJanela() {
        valorTotal = 0;
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER || evt.getCode() == KeyCode.F3) {
            adicionarItemCarrinho();
        } else if (evt.getCode() == KeyCode.F8) {
            fecharPedido();
        }
    }
}
