package controller;

import adapter.AdapterListProduto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Produto;
import service.ProdutoService;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerProdutoScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    List<Produto> produtos = new ArrayList<>();
    ProdutoService produtoService = new ProdutoService();
    static JFXListView<Produto> listaProdutosStatic;

    @FXML
    JFXListView<Produto> listaProdutos;

    @FXML
    JFXButton btnSair, btnCadastrarNovoProduto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaProdutosStatic = listaProdutos;
        inicializaListaProdutos();
    }

    private void inicializaListaProdutos() {
        produtos = produtoService.buscaListaProdutos();
        for (Produto p : produtos) {
            listaProdutos.getItems().add(p);
        }
        listaProdutos.setCellFactory(cliente -> new AdapterListProduto());
    }

    public void abrirCadastroProduto() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ProdutoCadastroScreen.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            // stage.getIcons().add(new Image(""));
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

            scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setOpacity(1);
                }
            });
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
            // stage.getIcons().add(new Image(""));
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

            scene.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    stage.setOpacity(1);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fecharJanela() {
        listaProdutosStatic.getItems().clear();
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
