package controller;

import animatefx.animation.FadeInDown;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import exceptions.OpcaoInvalidaException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Funcionario;
import session.UserSession;
import util.Helper;

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

    //Paineis
    @FXML
    Pane paneTotalVendido;
    @FXML
    Pane paneTotalPedidos;
    @FXML
    Pane paneProdutoEstoque;

    //Labels
    @FXML
    Label txtSaudacao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Funcionario func = UserSession.getFuncionario();
        txtSaudacao.setText(txtSaudacao.getText() + func.getNome());
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
        try {
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
                default:
                    throw new OpcaoInvalidaException();
            }
        } catch (OpcaoInvalidaException e) {
            Helper.abrirDialog("Ops", e.getMessage(), Alert.AlertType.ERROR);
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

            scene.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
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
