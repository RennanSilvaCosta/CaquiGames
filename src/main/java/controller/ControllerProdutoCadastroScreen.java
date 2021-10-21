package controller;

import adapter.AdapterListProduto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import model.Categoria;
import model.Produto;
import service.ProdutoService;
import util.CurrencyField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControllerProdutoCadastroScreen implements Initializable {

    List<Produto> produtos = new ArrayList<>();
    ProdutoService produtoService = new ProdutoService();
    Categoria categoria = new Categoria();
    Produto produto = new Produto();

    @FXML
    JFXButton btnSair, btnSalvarProduto;

    @FXML
    CurrencyField txtValor;

    @FXML
    JFXTextField txtNome, txtMarca, txtQtdEstoque;

    @FXML
    JFXTextArea txtDescricao;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
        popularProduto();
        if (this.produto.getId() != null) {
            produtoService.atualizaProduto(this.produto);
        } else {
            produtoService.cadastraProduto(this.produto);
        }
        fecharJanela();
        atualizaListaProdutos();
    }

    private void popularProduto() {
        this.produto.setNome(txtNome.getText());
        this.produto.setDescricao(txtDescricao.getText());
        this.produto.setMarca(txtMarca.getText());
        this.produto.setQtdEstoque(Integer.parseInt(txtQtdEstoque.getText()));
        this.produto.setValor(txtValor.getAmount());
    }

    private void atualizaListaProdutos() {
        produtos = produtoService.buscaListaProdutos();
        ControllerProdutoScreen.listaProdutosStatic.getItems().clear();
        for (Produto p : produtos) {
            ControllerProdutoScreen.listaProdutosStatic.getItems().add(p);
        }
        ControllerProdutoScreen.listaProdutosStatic.setCellFactory(cliente -> new AdapterListProduto());
    }

    public void fecharJanela() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
