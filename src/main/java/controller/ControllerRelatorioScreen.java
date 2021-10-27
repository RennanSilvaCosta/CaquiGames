package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRelatorioScreen implements Initializable {

    @FXML
    JFXButton btnRelatorioSintetico;
    @FXML
    JFXButton btnRelatorioAnalitico;
    @FXML
    JFXButton btnSair;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRelatorioSintetico.setGraphic(new ImageView(new Image("/icons/btn_relatorio_sintetico.png")));
        btnRelatorioAnalitico.setGraphic(new ImageView(new Image("/icons/btn_relatorio_analitico.png")));
    }

    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
