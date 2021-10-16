package controller;

import adapter.AdapterListCliente;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import model.Cliente;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerClienteScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    private List<Cliente> clientes = new ArrayList<>();

    @FXML
    JFXButton btnSair, btnCadastrarNovoCliente;
    @FXML
    JFXListView<Cliente> listacliente;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializaListaCliente();
    }

    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void abrirCadastroCliente() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ClienteCadastroScreen.fxml"));
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            // stage.getIcons().add(new Image(""));
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

    private void inicializaListaCliente() {

        Cliente c = new Cliente();
        Cliente c1 = new Cliente();

        c.setId(50L);
        c.setNome("Ester Duarte Lopes");
        c.setCpf("475.785.142-85");
        c.setCelular("(11)97852-4511");
        c.setEmail("este@duarte.com");

        c1.setId(51L);
        c1.setNome("Rennan Silva Costa");
        c1.setCpf("114.789.123-33");
        c1.setCelular("(11)95214-5511");
        c1.setEmail("rennan@costa.com");

        listacliente.getItems().add(c);
        listacliente.getItems().add(c1);
        listacliente.setCellFactory(cliente -> new AdapterListCliente());
    }

}
