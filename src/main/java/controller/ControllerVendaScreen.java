package controller;

import adapter.AdapterListProduto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.ItemPedido;
import model.Pedido;
import model.Produto;
import org.controlsfx.control.textfield.TextFields;
import service.ProdutoService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static util.Helper.formataValor;

public class ControllerVendaScreen implements Initializable {

    ProdutoService ps = new ProdutoService();

    private List<Produto> produtos = new ArrayList<>();

    double valorTotal = 0;

    @FXML
    private Label txtQuantidadeItens, txtValorDesconto, txtValorTotal = new Label();
    @FXML
    private JFXButton btnFecharPedido, btnAdicionarProduto;
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
        inicializaListaProdutos();
    }

    @FXML
    private void loadSecond(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/PagamentoScreen.fxml"));
        Scene scene = btnFecharPedido.getScene();

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
        Produto produto = new Produto();

        for (Produto p : produtos) {
            if (p.getDescricao().equals(nomeProduto)) {
                produto = p;
                break;
            }
        }

        Pedido p = new Pedido();
        ItemPedido item = new ItemPedido(p, produto, 0.0, 1, produto.getValor());

        listaProdutos.getItems().add(item);
        listaProdutos.setCellFactory(itemPedido -> new AdapterListProduto());
        txtAdicionarProduto.clear();

        txtQuantidadeItens.setText(String.valueOf(listaProdutos.getItems().size()));
        defineValorTotal();
    }

    private void inicializaListaProdutos() {
        produtos = ps.listarNomeProdutos();
        TextFields.bindAutoCompletion(txtAdicionarProduto, produtos);
    }

    public void defineValorTotal() {
        double total = valorTotal;
        for (ItemPedido item : listaProdutos.getItems()) {
            total = item.getPreco() * item.getQuantidade();
        }
        valorTotal += total;
        txtValorTotal.setText(formataValor(valorTotal));
    }

}
