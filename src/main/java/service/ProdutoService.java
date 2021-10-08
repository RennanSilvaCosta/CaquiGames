package service;

import model.Produto;
import dao.ProdutoDAO;

import java.util.List;

public class ProdutoService {

    private ProdutoDAO pr = new ProdutoDAO();

    public List<Produto> listarNomeProdutos() {
        return pr.listarNomesProdutos();
    }

}
