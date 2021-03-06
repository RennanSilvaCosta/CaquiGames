package controller;

import adapter.AdapterListProduto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Produto;
import service.ProdutoService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerProdutoScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    ProdutoService produtoService = new ProdutoService();
    public static JFXListView<Produto> listaProdutosStatic;

    @FXML
    JFXListView<Produto> listaProdutos;

    @FXML
    JFXButton btnSair;
    @FXML
    JFXButton btnCadastrarNovoProduto;

    @FXML
    JFXTextField txtPesquisarProduto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaProdutosStatic = listaProdutos;
        inicializaListaProdutos();

        txtPesquisarProduto.textProperty().addListener((observableValue, s, t1) -> {
            List<Produto> pro = produtoService.listaProdutosPorNome(t1);
            listaProdutos.getItems().setAll(pro);
        });
    }

    public void inicializaListaProdutos() {
        ObservableList<Produto> observableArrayList = FXCollections.observableArrayList(produtoService.buscaListaProdutos());
        listaProdutos.setItems(observableArrayList);
        listaProdutos.setCellFactory(cliente -> new AdapterListProduto());
    }

    public void abrirCadastroProduto() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProdutoCadastroScreen.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                stage.setOpacity(0.7);
            });

            scene.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void abrirEditarProduto(Produto produto) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProdutoCadastroScreen.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            ControllerProdutoCadastroScreen controller = fxmlLoader.getController();
            controller.getInfoProduto(produto);

            scene.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });

            scene.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
                stage.setOpacity(0.7);
            });

            scene.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fecharJanela() {
        ControllerMainScreen c = new ControllerMainScreen();
        c.inicializaResumo();
        listaProdutosStatic.getItems().clear();
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
