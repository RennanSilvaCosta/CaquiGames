package controller;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Pedido;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ControllerPagamentoScreen implements Initializable {

    static Pedido pedido;

    @FXML
    private JFXButton btnDinheiro, btnCartaoCredito, btnSair;

    @FXML
    private AnchorPane container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btnDinheiro.setGraphic(new ImageView(new Image("/icons/dinheiro.png")));
        btnDinheiro.setGraphicTextGap(20.0);

        btnCartaoCredito.setGraphic(new ImageView(new Image("/icons/cartao-credito.png")));
        btnCartaoCredito.setGraphicTextGap(20.0);

    }

    @FXML
    private void abrirFinalizarPedidoDinheiro(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FecharPedidoDinheiroScreen.fxml"));
        Scene scene = btnDinheiro.getScene();
        root.translateXProperty().set(scene.getWidth());

        StackPane parentContainer = (StackPane) btnDinheiro.getScene().getRoot();

        pedido.setFormaPagamento("Dinheiro");
        ControllerFecharPedidoDinheiroScreen controller = new ControllerFecharPedidoDinheiroScreen();

        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(800), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(container);
        });
        controller.getPedido(pedido);
        timeline.play();
    }

    @FXML
    private void abrirFinalizarPedidoCartao(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FecharPedidoCartaoScreen.fxml"));
        Scene scene = btnDinheiro.getScene();
        root.translateXProperty().set(scene.getWidth());

        StackPane parentContainer = (StackPane) btnDinheiro.getScene().getRoot();

        pedido.setFormaPagamento("Cartão");
        pedido.setQtdParcelas(1);
        ControllerFecharPedidoCartaoScreen controller = new ControllerFecharPedidoCartaoScreen();

        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(800), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            parentContainer.getChildren().remove(container);
        });
        controller.getPedido(pedido);
        timeline.play();
    }

    @FXML
    private void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    public void getPedido(Pedido pedido) {
       this.pedido = pedido ;
    }

}
