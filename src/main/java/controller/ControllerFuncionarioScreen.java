package controller;

import adapter.AdapterListFuncionario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Funcionario;
import service.FuncionarioService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerFuncionarioScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    private FuncionarioService funcionarioService = new FuncionarioService();
    public static JFXListView<Funcionario> listaFuncionarioStatic;

    @FXML
    JFXTextField txtBuscaFuncionario;

    @FXML
    JFXButton btnSair;
    @FXML
    JFXButton btnCadastrarNovoFuncionario;

    @FXML
    JFXListView<Funcionario> listaFuncionario;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaFuncionarioStatic = listaFuncionario;
        inicializaListaFuncionario();

        txtBuscaFuncionario.textProperty().addListener((observableValue, s, t1) -> {
            List<Funcionario> pro = funcionarioService.buscaFuncionariosPorNome(t1);
            listaFuncionario.getItems().setAll(pro);
        });
    }

    @FXML
    public void abrirCadastroFuncionario() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FuncionarioCadastroScreen.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

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

    @FXML
    public void abrirEditarFuncionario(Funcionario funcionario) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/FuncionarioCadastroScreen.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.show();

            ControllerFuncionarioCadastroScreen controller = fxmlLoader.getController();
            controller.getInfoFuncionario(funcionario);

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

    public void inicializaListaFuncionario() {
        List<Funcionario> func = funcionarioService.buscaTodosFuncionarios();
        for (Funcionario f : func) {
            listaFuncionario.getItems().add(f);
        }
        listaFuncionario.setCellFactory(cliente -> new AdapterListFuncionario());
    }

    public void fecharJanela() {
        listaFuncionarioStatic.getItems().clear();
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
