package controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.FuncionarioDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.FuncionarioService;
import validate.Validate;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import static utils.Helper.abrirDialog;

public class ControllerLoginScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    FuncionarioService funcionarioService = new FuncionarioService();
    Validate validate = new Validate();
    Map<String, String> errors = new HashMap<>();

    //TextFields
    @FXML
    JFXTextField txtEmail;

    //TextPassword
    @FXML
    JFXPasswordField txtSenha;

    //Botoes
    @FXML
    JFXButton btnSair;
    @FXML
    JFXButton btnLogar;

    //Labels
    @FXML
    Label lblErrorEmail;
    @FXML
    Label lblErrorSenha;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO document why this method is empty
    }

    public void logar() {
        try {
            limparLabelsErro();
            FuncionarioDTO dto = new FuncionarioDTO();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();

            dto.setEmail(email);
            dto.setSenha(senha);

            errors = validate.validaFormLogin(dto);

            if (errors.isEmpty()) {
                funcionarioService.logarFuncionario(dto);
                abrirMainScreen();
            } else {
                setErrorMessages(errors);
            }
        } catch (NoResultException e) {
            new Shake(txtEmail).play();
            new Shake(txtSenha).play();
            abrirDialog("Credenciais Inválidas", "Email ou senha inválidos!", Alert.AlertType.ERROR);
        }
    }

    private void abrirMainScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
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

            scene.setOnMouseReleased(mouseEvent -> stage.setOpacity(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        limparLabelsErro();

        if (fields.contains("email")) {
            lblErrorEmail.setText(errors.get("email"));
            new Shake(txtEmail).play();
        }
        if (fields.contains("senha")) {
            lblErrorSenha.setText(errors.get("senha"));
            new Shake(txtSenha).play();
        }
    }

    private void limparLabelsErro() {
        lblErrorEmail.setText("");
        lblErrorSenha.setText("");
    }

    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            logar();
        }
    }

}
