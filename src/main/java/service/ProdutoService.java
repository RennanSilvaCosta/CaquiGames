package service;

import model.Produto;
import dao.ProdutoDAO;

import java.util.List;

public class ProdutoService {

    private ProdutoDAO produtoDAO = new ProdutoDAO();

    public void cadastraProduto(Produto produto) {
        produtoDAO.cadastraProduto(produto);
    }

    public List<Produto> buscaListaProdutos(String str) {
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

    public void excluiProduto(String descricao) {
        produtoDAO.deletaProduto(descricao);
    }

    public List<Produto> buscarProdutoParaVenda() {
        return produtoDAO.litarProdutosParaVenda();
    }
}
