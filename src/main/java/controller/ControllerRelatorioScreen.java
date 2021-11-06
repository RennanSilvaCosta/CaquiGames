package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utils.Helper.abrirDialog;

public class ControllerRelatorioScreen implements Initializable {

    @FXML
    JFXButton btnRelatorioSintetico;
    @FXML
    JFXButton btnRelatorioAnalitico;
    @FXML
    JFXButton btnSair;

    public static String tipoRelatorio = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnRelatorioSintetico.setGraphic(new ImageView(new Image("/icons/btn_relatorio_sintetico.png")));
        btnRelatorioAnalitico.setGraphic(new ImageView(new Image("/icons/btn_relatorio_analitico.png")));
    }

    @FXML
    private void criaRelatorioSintetico() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/dialog/DialogDefinePeriodo.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Periodo");
            dialog.showAndWait();
        }catch (IOException e) {
            abrirDialog("Algo deu errado!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void criaRelatorioAnalitico() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/view/dialog/DialogDefinePeriodo.fxml"));
            DialogPane dialogPane = fxmlLoader.load();
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Periodo");
            tipoRelatorio = "analitico";
            dialog.showAndWait();
        }catch (IOException e) {
            abrirDialog("Algo deu errado!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
