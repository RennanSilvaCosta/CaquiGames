package service;

import dao.ProdutoDAO;
import model.Produto;

import java.util.List;

public class ProdutoService {

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void cadastraProduto(Produto produto) {
        produtoDAO.cadastraProduto(produto);
    }

    public List<Produto> listaProdutosPorNome(String str) {
        return produtoDAO.getProdutosPorNome(str);
    }

    public List<Produto> buscaListaProdutos() {
        return produtoDAO.listarNomesProdutos();
    }

    public List<Produto> buscaProdutosPelaMarca(String str) {
        return produtoDAO.getProdutosPorMarca(str);
    }

    public void atualizaProduto(Produto produto) {
        produtoDAO.editaProduto(produto);
    }

    public void excluiProduto(String nome) {
        produtoDAO.deletaProduto(nome);
    }

    public void subtraiEstoque(Produto produto) {
        produtoDAO.subtraiEstoque(produto);
    }

    public List<Produto> buscarProdutoParaVenda() {
        return produtoDAO.litarProdutosParaVenda();
    }
}
