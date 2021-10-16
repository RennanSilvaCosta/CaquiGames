package controller;

import adapter.AdapterListCliente;
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
import model.Categoria;
import model.Cliente;
import model.Produto;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerProdutoScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    List<Produto> produtos = new ArrayList<>();

    @FXML
    JFXListView<Produto> listaProdutos;

    @FXML
    JFXButton btnSair, btnCadastrarNovoProduto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaListaProdutos();
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

    private void inicializaListaProdutos() {
        Categoria cat = new Categoria();
        cat.setId(5L);
        cat.setDescricao("Controles");

        Produto c = new Produto();
        Produto c1 = new Produto();

        c.setId(1L);
        c.setDescricao("Controle PS4 Dual shock");
        c.setMarca("Sony");
        c.setQtdEstoque(50);
        c.setValor(200.00);
        c.setCategoria(cat);

        c1.setId(2L);
        c1.setDescricao("Controle XBOX 360");
        c1.setMarca("Microsoft");
        c1.setQtdEstoque(12);
        c1.setValor(150.00);
        c1.setCategoria(cat);

        listaProdutos.getItems().add(c);
        listaProdutos.getItems().add(c1);
        listaProdutos.setCellFactory(cliente -> new AdapterListProduto());
    }

    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
