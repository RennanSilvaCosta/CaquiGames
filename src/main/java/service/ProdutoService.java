package service;

import model.Produto;
import repository.ProdutoRepository;

import java.util.List;

public class ProdutoService {

    private ProdutoRepository pr = new ProdutoRepository();

    public List<Produto> listarNomeProdutos() {
        return pr.listarNomesProdutos();
    }

}
