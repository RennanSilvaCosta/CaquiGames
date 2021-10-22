package controller;

import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import dto.FuncionarioDTO;
import exceptions.EmailInvalidoException;
import exceptions.SenhaInvalidaException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.FuncionarioService;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static util.Helper.abrirDialog;

public class ControllerLoginScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    FuncionarioService funcionarioService = new FuncionarioService();

    @FXML
    JFXButton btnSair, btnLogar;

    @FXML
    JFXTextField txtEmail;

    @FXML
    JFXPasswordField txtSenha;

    @FXML
    Label txtErrorEmail, txtErrorSenha;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void logar() {
        try {
            limparErrorLabels();
            FuncionarioDTO dto = new FuncionarioDTO();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();

            dto.setEmail(email);
            dto.setSenha(senha);

            funcionarioService.logarFuncionario(dto);
            abrirMainScreen();

        } catch (EmailInvalidoException e) {
            txtErrorEmail.setText(e.getMessage());
            new Shake(txtEmail).play();
        } catch (SenhaInvalidaException e) {
            txtErrorSenha.setText(e.getMessage());
            new Shake(txtSenha).play();
        } catch (NoResultException e) {
            abrirDialog("Credenciais Inválidas", "Email ou senha inválidos!",Alert.AlertType.ERROR);
        }
    }

    public void limparErrorLabels() {
        txtErrorEmail.setText("");
        txtErrorSenha.setText("");
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

    @FXML
    private void keyPressed(KeyEvent evt) {
        if (evt.getCode() == KeyCode.ENTER) {
            logar();
        }
    }

}
