package controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class ControllerClienteCadastroScreen {

    @FXML
    JFXButton btnSair;


    public void fechar() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

}
