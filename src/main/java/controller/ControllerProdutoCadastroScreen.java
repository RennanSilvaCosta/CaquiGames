package controller;

import adapter.AdapterListProduto;
import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import dto.ProdutoValidaDTO;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
import validate.Validate;

import java.net.URL;
import java.util.*;

public class ControllerProdutoCadastroScreen implements Initializable {

    List<Produto> produtos = new ArrayList<>();
    ProdutoService produtoService = new ProdutoService();
    CategoriaService categoriaService = new CategoriaService();
    Produto produto = new Produto();

    Validate validate = new Validate();
    Map<String, String> errors = new HashMap<>();

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

    @FXML
    Label lblErrorProduto;
    @FXML
    Label lblErrorDescricao;
    @FXML
    Label lblErrorMarca;
    @FXML
    Label lblErrorValor;
    @FXML
    Label lblErrorEstoque;

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
        txtValor.setAmount(produto.getValor());
    }

    public void persistirProduto() {
        errors = validaFormularioCadastroProduto();
        if (errors.isEmpty()) {
            popularProduto();
            if (this.produto.getId() != null) {
                produtoService.atualizaProduto(this.produto);
            } else {
                produtoService.cadastraProduto(this.produto);
            }
            fecharJanela();
            atualizaListaProdutos();
        } else {
            setErrorMessages(errors);
        }
    }

    private void popularProduto() {
        this.produto.setNome(txtNome.getText());
        this.produto.setDescricao(txtDescricao.getText());
        this.produto.setMarca(txtMarca.getText());
        this.produto.setValor(txtValor.getAmount());
        this.produto.setCategoria(comboCategoria.getSelectionModel().getSelectedItem());
        this.produto.setQtdEstoque(Integer.parseInt(txtQtdEstoque.getText()));
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

    private Map<String, String> validaFormularioCadastroProduto() {
        ProdutoValidaDTO dto = new ProdutoValidaDTO();
        dto.setProduto(txtNome.getText());
        dto.setDescricao(txtDescricao.getText());
        dto.setMarca(txtMarca.getText());
        dto.setValor(txtValor.getAmount());
        dto.setEstoque(txtQtdEstoque.getText());
        return validate.validaFormularioCadastroProduto(dto);
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();
        limparLabelsErro();

        if (fields.contains("nomeProduto")) {
            lblErrorProduto.setText(errors.get("nomeProduto"));
            new Shake(txtNome).play();
        }
        if (fields.contains("descProduto")) {
            lblErrorDescricao.setText(errors.get("descProduto"));
            new Shake(txtDescricao).play();
        }
        if (fields.contains("marcaProduto")) {
            lblErrorMarca.setText(errors.get("marcaProduto"));
            new Shake(txtMarca).play();
        }
        if (fields.contains("valor")) {
            lblErrorValor.setText(errors.get("valor"));
            new Shake(txtValor).play();
        }
        if (fields.contains("estoque")) {
            lblErrorEstoque.setText(errors.get("estoque"));
            new Shake(txtQtdEstoque).play();
        }
    }

    private void limparLabelsErro() {
        lblErrorProduto.setText("");
        lblErrorDescricao.setText("");
        lblErrorMarca.setText("");
        lblErrorValor.setText("");
        lblErrorEstoque.setText("");
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
