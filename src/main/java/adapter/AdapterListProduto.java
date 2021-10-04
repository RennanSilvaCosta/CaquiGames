package adapter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import model.Produto;

import java.io.IOException;

public class AdapterListProduto extends ListCell<Produto> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtTitle, txtCategory, txtValue;


    @Override
    protected void updateItem(Produto produto, boolean empty) {
        super.updateItem(produto, empty);

        if (empty || produto == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/adapter/AdapterListLancamentos.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //setIconCategory(lancamento);
            // setBackgroundColor(lancamento);
            //txtCategory.setText(lancamento.getCategory());

            setText(null);
            setGraphic(gridPane);
        }
    }
}
