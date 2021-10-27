package adapter;

import com.jfoenix.controls.JFXButton;
import controller.ControllerProdutoScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Produto;

import java.io.IOException;

import static utils.Helper.formataValor;

public class AdapterListProduto extends ListCell<Produto> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtId;

    @FXML
    Label txtDescricao;

    @FXML
    Label txtMarca;

    @FXML
    Label txtQtdEstoque;

    @FXML
    Label txtValor;

    @FXML
    Label txtCategoria;

    @FXML
    JFXButton btnEditarProduto;

    @FXML
    JFXButton btnExcluirProduto;

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
            txtDescricao.setText(produto.getNome() + " " + produto.getDescricao());
            txtMarca.setText(produto.getMarca());
            txtQtdEstoque.setText(String.valueOf(produto.getQtdEstoque()));
            txtValor.setText(formataValor(produto.getValor()));
            txtCategoria.setText("Controles");

            btnEditarProduto.setOnAction(actionEvent -> {
                ControllerProdutoScreen c = new ControllerProdutoScreen();
                c.abrirEditarProduto(produto);
            });

            btnExcluirProduto.setOnAction(actionEvent -> {

            });

            setText(null);
            setGraphic(gridPane);
        }
    }
}
