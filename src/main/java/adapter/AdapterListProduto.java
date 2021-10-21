package adapter;

import com.jfoenix.controls.JFXButton;
import controller.ControllerClienteScreen;
import controller.ControllerProdutoCadastroScreen;
import controller.ControllerProdutoScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Produto;
import util.Helper;

import java.io.IOException;

import static util.Helper.formataValor;

public class AdapterListProduto extends ListCell<Produto> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtId, txtDescricao, txtMarca, txtQtdEstoque, txtValor, txtCategoria;

    @FXML
    JFXButton btnEditarProduto, btnExcluirProduto;

    @Override
    protected void updateItem(Produto produto, boolean empty) {
        super.updateItem(produto, empty);

        if (empty || produto == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/adapter/AdapterListaProtudos.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            btnEditarProduto.setGraphic(new ImageView(new Image("/icons/btn_editar_cliente.png")));
            btnExcluirProduto.setGraphic(new ImageView(new Image("/icons/btn_excluir_cliente.png")));

            txtId.setText(String.valueOf(produto.getId()));
            txtDescricao.setText(produto.getNome() + " " +  produto.getDescricao());
            txtMarca.setText(produto.getMarca());
            txtQtdEstoque.setText(String.valueOf(produto.getQtdEstoque()));
            txtValor.setText(formataValor(produto.getValor()));
            txtCategoria.setText("Controles");

            btnEditarProduto.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ControllerProdutoScreen c = new ControllerProdutoScreen();
                    c.abrirEditarProduto(produto);
                }
            });

            setText(null);
            setGraphic(gridPane);
        }
    }
}
