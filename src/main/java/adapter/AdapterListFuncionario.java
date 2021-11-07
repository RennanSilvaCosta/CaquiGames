package adapter;

import com.jfoenix.controls.JFXButton;
import controller.ControllerFuncionarioScreen;
import controller.ControllerProdutoScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Funcionario;
import model.Produto;
import service.FuncionarioService;
import service.ProdutoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdapterListFuncionario extends ListCell<Funcionario> {

    ControllerFuncionarioScreen c = new ControllerFuncionarioScreen();
    List<Funcionario> funcionarios = new ArrayList<>();
    FuncionarioService funcionarioService = new FuncionarioService();

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtId;

    @FXML
    Label txtNome;

    @FXML
    Label txtCpf;

    @FXML
    Label txtEmail;

    @FXML
    Label txtTelefone;

    @FXML
    JFXButton btnEditar;

    @FXML
    JFXButton btnExcluir;

    @Override
    protected void updateItem(Funcionario funcionario, boolean empty) {
        super.updateItem(funcionario, empty);

        if (empty || funcionario == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/adapter/AdapterListaFuncionarios.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            btnEditar.setGraphic(new ImageView(new Image("/icons/btn_editar_cliente.png")));
            btnExcluir.setGraphic(new ImageView(new Image("/icons/btn_excluir_cliente.png")));

            txtId.setText(String.valueOf(funcionario.getId()));
            txtNome.setText(funcionario.getNome());
            txtCpf.setText(funcionario.getCpf());
            txtEmail.setText(funcionario.getEmail());
            txtTelefone.setText(funcionario.getTelefone());

            btnEditar.setOnAction(actionEvent -> {
                c.abrirEditarFuncionario(funcionario);
            });

            btnExcluir.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exclusão funcionario");
                alert.setHeaderText("Excluir funcionario");
                alert.setContentText("Você realmente deseja excluir este funcionario?");
                ButtonType btnSim = new ButtonType("SIM");
                ButtonType btnCancelar = new ButtonType("CANCELAR");
                alert.getButtonTypes().setAll(btnSim, btnCancelar);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == btnSim) {
                    funcionarioService.excluiFuncionario(funcionario.getCpf());
                    atualizarListafuncionario();
                }
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

    private void atualizarListafuncionario() {
        funcionarios = funcionarioService.buscaTodosFuncionarios();
        ControllerFuncionarioScreen.listaFuncionarioStatic.getItems().clear();
        for (Funcionario f : funcionarios) {
            ControllerFuncionarioScreen.listaFuncionarioStatic.getItems().add(f);
        }
        ControllerFuncionarioScreen.listaFuncionarioStatic.setCellFactory(cliente -> new AdapterListFuncionario());
    }
}
