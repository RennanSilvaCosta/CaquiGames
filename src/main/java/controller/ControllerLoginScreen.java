package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ControllerLoginScreen {

    @FXML
    JFXButton btnSair, btnLogar;

    @FXML
    JFXTextField txtEmail;

    @FXML
    JFXPasswordField txtSenha;


    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
