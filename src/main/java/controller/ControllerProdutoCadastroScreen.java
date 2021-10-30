package controller;

import adapter.AdapterListProduto;
import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import exceptions.CampoObrigatorioException;
import exceptions.ValorInvalidoException;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Categoria;
import model.Produto;
import service.CategoriaService;
import service.ProdutoService;
import utils.CurrencyField;
import utils.Helper;
import validate.Validate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static utils.Helper.abrirDialog;

public class ControllerProdutoCadastroScreen implements Initializable {

    List<Produto> produtos = new ArrayList<>();
    ProdutoService produtoService = new ProdutoService();
    CategoriaService categoriaService = new CategoriaService();
    Produto produto = new Produto();

    @FXML
    CurrencyField txtValor;

    @FXML
    JFXTextArea txtDescricao;

    @FXML
    JFXComboBox<Categoria> comboCategoria;

    //Botoes
    @FXML
    JFXButton btnSair;
    @FXML
    JFXButton btnSalvarProduto;
    @FXML
    JFXButton btnAddCategoria;

    //TextFields
    @FXML
    JFXTextField txtNome;
    @FXML
    JFXTextField txtMarca;
    @FXML
    JFXTextField txtQtdEstoque;
    @FXML
    JFXTextField txtCategoria;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAddCategoria.setGraphic(new ImageView(new Image("/icons/mais.png")));
        inicializaComboCategoria();
    }

    public void getInfoProduto(Produto produto) {
        this.produto = produto;
        txtNome.setText(produto.getNome());
        txtDescricao.setText(produto.getDescricao());
        txtMarca.setText(produto.getMarca());
        txtQtdEstoque.setText(produto.getQtdEstoque().toString());
        txtValor.setText(produto.getValor().toString());
    }

    public void persistirProduto() {
        try {
            popularProduto();
            if (this.produto.getId() != null) {
                produtoService.atualizaProduto(this.produto);
            } else {
                produtoService.cadastraProduto(this.produto);
            }
            fecharJanela();
            atualizaListaProdutos();
        }catch (ValorInvalidoException | CampoObrigatorioException e) {
            abrirDialog("Erro", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void popularProduto() throws ValorInvalidoException, CampoObrigatorioException {
        this.produto.setNome(txtNome.getText());
        this.produto.setDescricao(txtDescricao.getText());
        this.produto.setMarca(txtMarca.getText());
        this.produto.setValor(txtValor.getAmount());
        this.produto.setCategoria(comboCategoria.getSelectionModel().getSelectedItem());
        String qtdEstoque = txtQtdEstoque.getText();
        if (qtdEstoque.matches("^[0-9]+$")) {
            this.produto.setQtdEstoque(Integer.parseInt(qtdEstoque));
        } else {
            throw new ValorInvalidoException();
        }
        Validate.validaFormCadastroProduto(produto);
    }

    private void atualizaListaProdutos() {
        produtos = produtoService.buscaListaProdutos();
        ControllerProdutoScreen.listaProdutosStatic.getItems().clear();
        for (Produto p : produtos) {
            ControllerProdutoScreen.listaProdutosStatic.getItems().add(p);
        }
        ControllerProdutoScreen.listaProdutosStatic.setCellFactory(cliente -> new AdapterListProduto());
    }

    @FXML
    private void addNovaCategoria() {
        if (!comboCategoria.isDisabled()) {
            deslizaCategoriaBaixo();
            ImageView imgView = new ImageView(new Image("/icons/check.png"));
            new FadeIn(imgView).play();
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(300), imgView);
            rotateTransition.setFromAngle(45);
            rotateTransition.setToAngle(360);
            rotateTransition.play();
            btnAddCategoria.setGraphic(imgView);
            comboCategoria.setDisable(true);
        } else {
            deslizaCategoriaCima();
            ImageView imgView = new ImageView(new Image("/icons/mais.png"));
            new FadeIn(imgView).play();
            RotateTransition rotateTransition = new RotateTransition(Duration.millis(300), imgView);
            rotateTransition.setFromAngle(360);
            rotateTransition.setToAngle(90);
            rotateTransition.play();
            btnAddCategoria.setGraphic(imgView);
            comboCategoria.setDisable(false);

            String novaCat = txtCategoria.getText();

            if (!novaCat.isBlank()) {
                categoriaService.cadastrarCategoria(novaCat);
                inicializaComboCategoria();
            }
        }
        txtCategoria.clear();
    }

    private void deslizaCategoriaBaixo() {
        Path path = new Path();
        path.getElements().add(new MoveTo(120, 46));
        path.getElements().add(new LineTo(120, 70));

        PathTransition transition = new PathTransition();
        transition.setNode(txtCategoria);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    private void deslizaCategoriaCima() {
        Path path = new Path();
        path.getElements().add(new MoveTo(120, 70));
        path.getElements().add(new LineTo(120, 20));

        PathTransition transition = new PathTransition();
        transition.setNode(txtCategoria);
        transition.setDuration(Duration.millis(300));
        transition.setPath(path);
        transition.play();
    }

    private void inicializaComboCategoria() {
        comboCategoria.getItems().clear();
        comboCategoria.getItems().addAll(categoriaService.buscaTodasCategoiras());
        comboCategoria.getSelectionModel().selectLast();
    }

    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
