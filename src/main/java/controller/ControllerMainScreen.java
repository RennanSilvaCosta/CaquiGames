package controller;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import exceptions.OpcaoInvalidaException;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import model.Funcionario;
import service.MainService;
import session.UserSession;
import utils.Helper;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import static utils.Helper.abrirDialog;

public class ControllerMainScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    public static Label txtTotalVendidoStatic;
    public static Label txtTotalPedidoStatic;
    public static Label txtProdutosEstoqueStatic;

    public static Pane paneTotalVendidoStatic;
    public static Pane paneTotalPedidosStatic;
    public static Pane paneProdutoEstoqueStatic;

    MainService mainService = new MainService();

    Funcionario func;

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
    Label txtDia;
    @FXML
    Label txtSaudacao;
    @FXML
    Label txtTotalVendido;
    @FXML
    Label txtTotalPedido;
    @FXML
    Label txtProdutosEstoque;
    @FXML
    Label txtNomeFuncionario;
    @FXML
    Label txtEmailFuncionario;

    @FXML
    JFXButton btnSair;

    @FXML
    Circle fotoPerfil;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaComponentesEstaticos();
        func = UserSession.getFuncionario();
        txtSaudacao.setText(txtSaudacao.getText() + func.getNome());
        txtNomeFuncionario.setText(func.getNome());
        txtEmailFuncionario.setText(func.getEmail());
        txtDia.setText(txtDia.getText() + Helper.converteDataParaString(LocalDate.now()));
        inicializaResumo();
        inicializaMainMenu();
    }

    private void inicializaMainMenu() {
        Map<String, String> itemList = new HashMap<>();
        itemList.put("Vendas", "icons/menu/icon-vendas-menu.png");
        itemList.put("Produtos", "icons/menu/icon-produto-menu.png");
        itemList.put("Clientes", "icons/menu/icon-cliente-menu.png");

        if (func.getPerfil().equals("ADM")) {
            itemList.put("Funcionarios", "icons/menu/icon-funcionario-menu.png");
            itemList.put("Relatórios", "icons/menu/icon-relatorio-menu.png");
        }

        for (String labelsItemList : itemList.keySet()) {
            Label itemListView = new Label(labelsItemList);
            itemListView.setGraphic(new ImageView(new Image(itemList.get(labelsItemList))));
            itemListView.setGraphicTextGap(20);
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

                case "Produtos":
                    abreTela("/view/ProdutoScreen.fxml", null);
                    break;

                case "Clientes":
                    abreTela("/view/ClienteScreen.fxml", null);
                    break;

                case "Relatórios":
                    abreTela("/view/RelatorioScreen.fxml", null);
                    break;

                case "Funcionarios":
                    abreTela("/view/FuncionarioScreen.fxml", null);
                    break;

                default:
                    throw new OpcaoInvalidaException();
            }
        } catch (OpcaoInvalidaException e) {
            abrirDialog("Algo deu errado!", e.getMessage(), Alert.AlertType.ERROR);
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
            abrirDialog("Algo deu errado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void inicializaComponentesEstaticos() {
        txtTotalVendidoStatic = txtTotalVendido;
        txtTotalPedidoStatic = txtTotalPedido;
        txtProdutosEstoqueStatic = txtProdutosEstoque;
        paneTotalVendidoStatic = paneTotalVendido;
        paneTotalPedidosStatic = paneTotalPedidos;
        paneProdutoEstoqueStatic = paneProdutoEstoque;
    }

    public void inicializaResumo() {
        new FadeIn(paneTotalVendidoStatic).setSpeed(0.5).play();
        new FadeIn(paneTotalPedidosStatic).setSpeed(0.5).play();
        new FadeIn(paneProdutoEstoqueStatic).setSpeed(0.5).play();

        txtTotalVendidoStatic.setText(Helper.formataValor(mainService.obterTotalVendido()));
        txtTotalPedidoStatic.setText(String.valueOf(mainService.obterTotalPedidos()));
        txtProdutosEstoqueStatic.setText(String.valueOf(mainService.obterTotalProdutos()));
    }

    @FXML
    private void deslizarPainelTotalVendidoCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(100, 75));
        path.getElements().add(new LineTo(100, 65));

        PathTransition transition = new PathTransition();
        transition.setNode(paneTotalVendido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelTotalVendidoBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(100, 65));
        path.getElements().add(new LineTo(100, 75));

        PathTransition transition = new PathTransition();
        transition.setNode(paneTotalVendido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelTotalPedidosCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(100, 75));
        path.getElements().add(new LineTo(100, 65));

        PathTransition transition = new PathTransition();
        transition.setNode(paneTotalPedidos);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelTotalPedidosBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(100, 65));
        path.getElements().add(new LineTo(100, 75));

        PathTransition transition = new PathTransition();
        transition.setNode(paneTotalPedidos);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelProdutoEstoqueCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(100, 75));
        path.getElements().add(new LineTo(100, 65));

        PathTransition transition = new PathTransition();
        transition.setNode(paneProdutoEstoque);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void deslizarPainelProdutoEstoqueBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(100, 65));
        path.getElements().add(new LineTo(100, 75));

        PathTransition transition = new PathTransition();
        transition.setNode(paneProdutoEstoque);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    @FXML
    private void sair() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sair");
        alert.setHeaderText("Fazer Logout");
        alert.setContentText("Você realmente deseja sair?");
        ButtonType btnSim = new ButtonType("SIM");
        ButtonType btnCancelar = new ButtonType("CANCELAR");
        alert.getButtonTypes().setAll(btnSim, btnCancelar);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == btnSim) {
            UserSession.cleanUserSession();
            abreTela("/view/LoginScreen.fxml", btnSair);
        }
    }
}
