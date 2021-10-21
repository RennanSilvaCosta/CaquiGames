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
import javafx.util.Duration;
import model.Funcionario;
import model.ItemPedido;
import model.Pedido;
import session.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerPagamentoScreen implements Initializable {

    static Pedido pedido;

    @FXML
    private JFXButton btnDinheiro, btnCartaoCredito;

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
    private void abrirFinalizarPedido(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FecharPedidoScreen.fxml"));
        Scene scene = btnDinheiro.getScene();
        root.translateXProperty().set(scene.getWidth());

        StackPane parentContainer = (StackPane) btnDinheiro.getScene().getRoot();

        pedido.setFormaPagamento("Dinheiro");
        ControllerFecharPedido controller = new ControllerFecharPedido();

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

    public void getPedido(Pedido pedido) {
       this.pedido = pedido ;
    }

}
