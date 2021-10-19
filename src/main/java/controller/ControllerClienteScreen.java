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
import model.Endereco;
import service.ClienteService;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerClienteScreen implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    private List<Cliente> clientes = new ArrayList<>();

    private ClienteService clienteService = new ClienteService();

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

    @FXML
    public void abrirEditarCliente(Cliente cliente) {
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

            ControllerClienteCadastroScreen controller = fxmlLoader.getController();
            controller.getInfoCliente(cliente);

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
        clientes = clienteService.buscaTodosClientes();
        for (Cliente c : clientes) {
            listacliente.getItems().add(c);
        }
        listacliente.setCellFactory(cliente -> new AdapterListCliente());
    }

}
