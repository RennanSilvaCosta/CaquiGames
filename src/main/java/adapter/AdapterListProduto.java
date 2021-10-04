package adapter;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.Produto;

import java.io.IOException;

public class AdapterListProduto extends ListCell<Produto> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtIndice, txtNomeProduto, txtQuantidade, txtValorUnitario, txtValorTotal;

    @FXML
    JFXButton btnMenosQuantidade, btnMaisQuantidade;

    @Override
    protected void updateItem(Produto produto, boolean empty) {
        super.updateItem(produto, empty);

        if (empty || produto == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/adapter/AdapterListaProdutos.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            btnMenosQuantidade.setGraphic(new ImageView(new Image("/icons/negativo.png")));
            btnMaisQuantidade.setGraphic(new ImageView(new Image("/icons/mais.png")));

            txtIndice.setText("52");
            txtNomeProduto.setText(produto.getDescricao());
            txtQuantidade.setText(String.valueOf(22));
            txtValorUnitario.setText(produto.getValor().toString());
            txtValorTotal.setText(String.valueOf(2 * produto.getValor()));

            setText(null);
            setGraphic(gridPane);
        }
    }
}
