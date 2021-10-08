package controller;

import animatefx.animation.FadeInDown;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ControllerMainScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private JFXListView<Label> listViewMainMenu = new JFXListView<>();

    @FXML
    Pane paneTotalVendido, paneTotalPedidos, paneProdutoEstoque;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaResumo();
        inicializaMainMenu();
    }

    private void inicializaMainMenu() {
        Map<String, String> itemList = new HashMap<>();
        itemList.put("Vendas", "");
        itemList.put("Cadastro de Produtos", "");
        itemList.put("Cadastros de Clientes", "");
        itemList.put("Relatórios", "");

        for (String labelsItemList : itemList.keySet()) {
            Label itemListView = new Label(labelsItemList);
            itemListView.getStyleClass().add("label-style-jfx");
            listViewMainMenu.getItems().add(itemListView);
        }
    }

    public void clickItemList() {
        switch (listViewMainMenu.getSelectionModel().getSelectedItem().getText()) {
            case "Vendas":
                abreTela("/view/VendaScreen.fxml", null);
                break;

            case "Cadastro de Produtos":
                abreTela("/view/ProdutoScreen.fxml", null);
                break;

            case "Cadastros de Clientes":
                abreTela("/view/ClienteScreen.fxml", null);
                break;

            case "Relatórios":
                abreTela("/view/RelatorioScreen.fxml", null);
                break;
        }
    }

    @FXML
    public void abreTela(String caminho, JFXButton componente) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(caminho));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            //stage.getIcons().add(new Image(""));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            if (componente != null) {
                //a partir do componenete de layout recupero a janela a ser fechada
                Stage stage2 = (Stage) componente.getScene().getWindow();
                stage2.close();
            }

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

    public void inicializaResumo() {
        new FadeInDown(paneTotalVendido).setSpeed(0.5).play();
        new FadeInDown(paneTotalPedidos).setSpeed(0.5).play();
        new FadeInDown(paneProdutoEstoque).setSpeed(0.5).play();
    }

    @FXML
    private void sair() {
        System.exit(0);
    }
}
