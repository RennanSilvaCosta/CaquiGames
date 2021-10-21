package controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLoginScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    JFXButton btnSair, btnLogar;

    @FXML
    JFXTextField txtEmail;

    @FXML
    JFXPasswordField txtSenha;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logar() {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
        if (email.equals("r@r.com") & senha.equals("123")) {
            abrirMainScreen();
        } else {
            new Shake(txtEmail).play();
            new Shake(txtSenha).play();
        }
    }

    private void abrirMainScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            //stage.getIcons().add(new Image(""));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            if (btnLogar != null) {
                //a partir do componenete de layout recupero a janela a ser fechada
                Stage stage2 = (Stage) btnLogar.getScene().getWindow();
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

    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
