package adapter;

import animatefx.animation.FadeInDown;
import com.jfoenix.controls.JFXButton;
import controller.ControllerVendaScreen;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import model.ItemPedido;

import java.io.IOException;

import static util.Helper.formataValor;

public class AdapterListProduto extends ListCell<ItemPedido> {

    private FXMLLoader mLLoader;

    @FXML
    private GridPane gridPane;

    @FXML
    Label txtIndice, txtNomeProduto, txtQuantidade, txtValorUnitario, txtValorTotal;

    @FXML
    JFXButton btnMenosQuantidade, btnMaisQuantidade;

    @Override
    protected void updateItem(ItemPedido item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/view/adapter/AdapterListaProdutosVenda.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            btnMenosQuantidade.setGraphic(new ImageView(new Image("/icons/negativo.png")));
            btnMaisQuantidade.setGraphic(new ImageView(new Image("/icons/mais.png")));

            txtIndice.setText(item.getProduto().getId().toString());
            txtNomeProduto.setText(item.getProduto().getDescricao());
            txtQuantidade.setText(item.getQuantidade().toString());
            txtValorUnitario.setText(formataValor(item.getPreco()));
            txtValorTotal.setText(formataValor(item.getPreco() * item.getQuantidade()));

            desabilitaBotaoMenosQtd(item.getQuantidade());

            btnMaisQuantidade.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    item.setQuantidade(item.getQuantidade() + 1);
                    txtQuantidade.setText(String.valueOf(item.getQuantidade()));
                    txtValorTotal.setText(formataValor(item.getPreco() * item.getQuantidade()));
                    desabilitaBotaoMenosQtd(item.getQuantidade());
                    ControllerVendaScreen.txtValorTotalStatic.setText(String.valueOf(formataValor(ControllerVendaScreen.valorTotal += item.getPreco())));
                    new FadeInDown(ControllerVendaScreen.txtValorTotalStatic).setSpeed(0.5).play();
                }
            });

            btnMenosQuantidade.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    item.setQuantidade(item.getQuantidade() - 1);
                    txtQuantidade.setText(String.valueOf(item.getQuantidade()));
                    txtValorTotal.setText(formataValor((item.getPreco() * item.getQuantidade())));
                    desabilitaBotaoMenosQtd(item.getQuantidade());
                    ControllerVendaScreen.txtValorTotalStatic.setText(String.valueOf(formataValor(ControllerVendaScreen.valorTotal -= item.getPreco())));
                    new FadeInDown(ControllerVendaScreen.txtValorTotalStatic).setSpeed(0.5).play();
                }
            });

            setText(null);
            setGraphic(gridPane);
        }
    }

    private void desabilitaBotaoMenosQtd(int quantidade) {
        btnMenosQuantidade.setDisable(quantidade == 1);
    }

}
