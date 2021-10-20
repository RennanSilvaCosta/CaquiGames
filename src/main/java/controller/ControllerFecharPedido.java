package controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerFecharPedido implements Initializable {

    @FXML
    Pane paneValorTotalPedido, paneDesconto, paneValorRecebido, paneTroco;

    @FXML
    JFXButton btnFinalizarPedido;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void finalizarPedido() {
        Stage stage = (Stage) btnFinalizarPedido.getScene().getWindow();
        stage.close();
    }

    public void deslizarPainelValorTotalCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneValorTotalPedido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    public void deslizarPainelValorTotalBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneValorTotalPedido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    public void deslizarPainelDescontoCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneDesconto);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    public void deslizarPainelDescontoBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneDesconto);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    public void deslizarPainelValorRecebidoCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneValorRecebido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    public void deslizarPainelValorRecebidoBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneValorRecebido);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    public void deslizarPainelTrocoCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 70));
        path.getElements().add(new LineTo(110, 46));

        PathTransition transition = new PathTransition();
        transition.setNode(paneTroco);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    public void deslizarPainelTrocoBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(110, 46));
        path.getElements().add(new LineTo(110, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(paneTroco);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }


}
