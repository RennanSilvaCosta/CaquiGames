package adapter;

import com.jfoenix.controls.JFXButton;
import controller.ControllerProdutoScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Produto;
import service.ProdutoService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static utils.Helper.formataValor;

public class AdapterListProduto extends ListCell<Produto> {

    ControllerProdutoScreen c = new ControllerProdutoScreen();
    List<Produto> produtos = new ArrayList<>();
    ProdutoService produtoService = new ProdutoService();

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
            txtCategoria.setText(produto.getCategoria().getDescricao());

            btnEditarProduto.setOnAction(actionEvent -> {
                c.abrirEditarProduto(produto);
            });

            btnExcluirProduto.setOnAction(actionEvent -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exclusão produto");
                alert.setHeaderText("Excluir produto");
                alert.setContentText("Você realmente deseja excluir este produto?");
                ButtonType btnSim = new ButtonType("SIM");
                ButtonType btnCancelar = new ButtonType("CANCELAR");
                alert.getButtonTypes().setAll(btnSim, btnCancelar);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == btnSim) {
                    ProdutoService ps = new ProdutoService();
                    ps.excluiProduto(produto.getNome());
                    atualizaListaProdutos();
                }
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

    private void atualizaListaProdutos() {
        produtos = produtoService.buscaListaProdutos();
        ControllerProdutoScreen.listaProdutosStatic.getItems().clear();
        for (Produto p : produtos) {
            ControllerProdutoScreen.listaProdutosStatic.getItems().add(p);
        }
        ControllerProdutoScreen.listaProdutosStatic.setCellFactory(cliente -> new AdapterListProduto());
    }
}
