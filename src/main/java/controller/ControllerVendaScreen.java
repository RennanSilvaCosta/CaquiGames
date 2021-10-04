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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.Produto;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerVendaScreen implements Initializable {

    @FXML
    private JFXButton btnFecharPedido;
    @FXML
    private AnchorPane anchorRoot;
    @FXML
    private StackPane parentContainer;
    @FXML
    private JFXListView<Produto> listaProdutos;
    @FXML
    private JFXTextField txtAdicionarProduto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaListaProdutos();

        Produto p1 = new Produto();
        Produto p2 = new Produto();
        Produto p3 = new Produto();

        p1.setDescricao("PS4 PRO");
        p1.setMarca("SONY");

        p2.setDescricao("NINTENDO SWITCH");
        p2.setMarca("NINTENDO");

        p3.setDescricao("XBOX ONE");
        p3.setMarca("MICROSOFT");

        TextFields.bindAutoCompletion(txtAdicionarProduto, p1, p2, p3);
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


    private void inicializaListaProdutos() {
        Produto produto = new Produto();
        Produto p1 = new Produto();

        produto.setDescricao("teste");
        produto.setQtdEstoque(145);
        produto.setValor(256.78);
        produto.setMarca("Sonye tes");

        p1.setDescricao("teste P#$d");
        p1.setQtdEstoque(4546546);
        p1.setValor(41.52);
        p1.setMarca("Intel");

        List<Produto> produtos = new ArrayList<>();

        produtos.add(produto);
        produtos.add(p1);

        for (Produto prods : produtos) {
            listaProdutos.getItems().add(prods);
            listaProdutos.setCellFactory(p -> new AdapterListProduto());
        }
    }


}
